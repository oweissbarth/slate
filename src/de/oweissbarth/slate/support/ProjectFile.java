/*******************************************************************************
 * Copyright 2013 Oliver Weissbarth
 * 
 * This file is part of Slate.
 * 
 *     Slate is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *      Slate is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with  Slate.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.oweissbarth.slate.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import de.oweissbarth.slate.data.Camera;
import de.oweissbarth.slate.data.Equipment;
import de.oweissbarth.slate.data.Lens;
import de.oweissbarth.slate.data.Media;
import de.oweissbarth.slate.data.Project;
import de.oweissbarth.slate.data.Scene;
import de.oweissbarth.slate.data.Shot;
import de.oweissbarth.slate.data.Take;

public class ProjectFile {
	public static  Project project;
	
	
	public static String[] listProjects(Context context){
		String[] files =  ProjectFile.listProjectFiles(context);
		for(int i=0; i<files.length; i++){
			files[i] = files[i].replaceAll(".slate", "");
			Log.d("File", files[i]);
		}
		return files;
	}
	
	
	
	public static String[] listProjectFiles(Context context){
		String[] files = null;
		File directory = context.getExternalFilesDir(null);
		Log.d("File", "Directory: " + directory.toString());
		if((directory).isDirectory()){
			files = directory.list();
			Log.d("File", "is directory? " + String.valueOf(directory.isDirectory()));
			Log.d("File", String.valueOf(files));
		}if(files== null){
			files = new String[]{};
		}return files;
	}
	
	public static void saveIfNecessary(Context context){
		ActivityManager am = (ActivityManager)context.getSystemService(Activity.ACTIVITY_SERVICE);
		String classname = am.getRunningTasks(1).get(0).topActivity.getClassName();
		Log.d("Save if necessary", "Active Class now is : " + classname);
		if(!(classname.contains("de.oweissbarth.slate")))
			save(context);
	}
	
	public static boolean save(Context context){
		Project project = ProjectFile.project;
		
		File dir = (context.getExternalFilesDir(null));
	    File file = new File(dir, project.getName() + ".slate");
	    if (file.exists ()) {
	    	Log.d("File", "File already exists...\n Overwriting..");
	    	file.delete();
	    }
	    
	    Log.d("File", "DestionationFile is: "+ file.toString());
	    
	    try{
	    FileOutputStream out = new FileOutputStream(file);
	    Log.d("File", "Opened Stream \n Generating XML...");
	    String xml = "---&slate-ver0001&---\n" + project.getXML();
	    for(int i = 0; i<xml.length(); i++){
			Log.d("Parsing", i+" :"+xml.charAt(i));
		}
	    Log.d("File", "Generated XML!");
	    out.write(xml.getBytes());
	    Log.d("File", "Wrote Data..");
	    out.flush();
	    out.close();
	    }catch(Exception e){
	    	Log.d("File", "Error while writing File");
	    	return false;
	    }
	    Toast savedNotification = Toast.makeText(context, "saved File", Toast.LENGTH_SHORT);
		savedNotification.show();
	    return true;
	}
	
	public static Project load(String name, Context context, ProgressDialog progress){
		
		File dir = (context.getExternalFilesDir(null));
	    File file = new File(dir, name + ".slate");
	    String slate= "";
	    int numberOfLines=0;
	    if (!file.exists ()) {
	    	return null;
	    }
	    try{
	    	InputStream in = new FileInputStream(file);
	    	
	    	InputStreamReader inputreader = new InputStreamReader(in);
	    	BufferedReader reader = new BufferedReader(inputreader);

	    	String line;
	    	while((line=reader.readLine())!=null){
	    		numberOfLines++;
	    		slate +=line;
	    	}
	    	reader.close();
	    	
	    	
	    }catch(Exception e){
	    	return null;
	    }
	    progress.setMax(2*numberOfLines);
	    return parseDotSlate(slate, progress);
	}
	
	
	public static Project parseDotSlate(String input, ProgressDialog progress){
		Token[] t = Token.partitionDotSlate(input, progress);
		
		if(t==null)
			Log.d("Parsing", "Got Null Pointer");
		
		int state = 0;
		boolean valid = true;
		Project project = null;
		Scene scene = null;
		Shot shot = null;
		Take take = null;
		Equipment equipment = null;
		Camera camera = null;
		Lens lens = null;
		Media media = null;
		
		Log.d("Parsing", "Start Finite State Maschine with number of Token: " + t.length);
		for(int i=0; (i< t.length)&&(valid); i++){
			Log.d("Parsing", "Parsing at state =" + state + "with token " + t[i].getId());
			progress.incrementProgressBy(1);
			switch(state){
				case 0:		if(t[i].getId()==1){
								state=1;
								project = new Project();
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				case 1:		if(t[i].getId()==101){
							project.setName(t[i].getValue());
							state=101;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 101:	if(t[i].getId()==102){
								project.setDirector(t[i].getValue());
								state=102;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 102:	if(t[i].getId()==2){
								scene=project.addScene();
								state=2;
							}else if(t[i].getId()==5){
								equipment=project.addEquipment();
								state=5;
							}else if(t[i].getId()==6){
								state=6;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
							
				case 10:	if(t[i].getId()==2){
							scene=project.addScene();
							state=2;
							}else if(t[i].getId()==6){
								state=6;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
							
				case 6:		if(valid){
								Log.d("Parsing", "reached final state");
								return project;
							}return null;
				
				case 2:		if(t[i].getId()==201){
								scene.setId(Integer.parseInt(t[i].getValue()));
								state=201;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 201:	if(t[i].getId()==202){
								scene.setName(t[i].getValue());
								state=202;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
							
				case 202:		if(t[i].getId()==203){
								scene.setDescription(t[i].getValue());
								state=203;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 203:		if(t[i].getId()==204){
								scene.setExt(Boolean.parseBoolean(t[i].getValue()));
								state=204;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
				
				case 204:	if(t[i].getId()==7){
								state=7;
							}else if(t[i].getId()==3){
								shot = scene.addShot();
								state=3;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 7:		if(t[i].getId()==6){
								state=6;
							}else if(t[i].getId()==2){
								scene=project.addScene();
								state= 2;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 3:		if(t[i].getId()==301){
								shot.setId((t[i].getValue()).charAt(0));
								state=301;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 301:	if(t[i].getId()==302){ 
								shot.setLens(equipment.getLensById(Integer.parseInt(t[i].getValue())));
								state=302;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 302:	if(t[i].getId()==303){
								shot.setFps(Integer.parseInt(t[i].getValue()));
								state=303;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 303:	if(t[i].getId()==304){
								shot.setFocalLength(Integer.parseInt(t[i].getValue()));
								state=304;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
				
				case 304:	if(t[i].getId()==305){ 
								shot.setCamera(equipment.getCameraById(Integer.parseInt(t[i].getValue())));
								state=305;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 305:	if(t[i].getId()==306){
								shot.setFieldSize(Short.parseShort(t[i].getValue()));
								state=306;
							}else{
								valid=false;reportError(state, t[i].getId());
							}
							
				case 306:	if(t[i].getId()==307){
								shot.setCameraMotion(Short.parseShort(t[i].getValue()));
								state=307;
							}else{
								valid=false;reportError(state, t[i].getId());
							}
					
				case 307:	if(t[i].getId()==8){
								state=8;
							}else if(t[i].getId()==4){
								take=shot.addTake();
								state=4;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 4:		if(t[i].getId()==401){
								take.setId(Integer.parseInt(t[i].getValue()));
								state=401;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				case 401:	if(t[i].getId()==402){
								take.setDuration(Time.valueOf(t[i].getValue()));
								state=402;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
				
				case 402:	if(t[i].getId()==403){
								take.setUsable(Boolean.parseBoolean(t[i].getValue()));
								state=403;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 403:	if(t[i].getId()==404){
								take.setComment(t[i].getValue());
								state=404;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
				
				case 404:	if(t[i].getId()==405){
								take.setMedia(equipment.getMediaById(Integer.parseInt(t[i].getValue())));
								state =405;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 405:	if(t[i].getId()==9){
								state=9;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 9:	if(t[i].getId()==4){
								take=shot.addTake();
								state=4;
							}else if(t[i].getId()==8){
								state=8;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 5:	if(t[i].getId()==12){
								lens = equipment.addLens();
								state= 12;
							}else if(t[i].getId()==13){
								media = equipment.addMedia();
								state=13;
							}else if(t[i].getId()==11){
								camera = equipment.addCamera();
								state=11;
							}else if(t[i].getId()==10){
								state=10;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
				
				case 11:	if(t[i].getId()==501){
								camera.setId(Integer.parseInt(t[i].getValue()));
								state=501;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 501:	if(t[i].getId()==502){
								camera.setName(t[i].getValue());
								state=502;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 502:	if(t[i].getId()==503){
								String[] array = t[i].getValue().split(",");
								int[] fps= new int[array.length];
								for(int x=0; x < array.length; x++){
									fps[x] = Integer.parseInt(array[x]);
									Log.d("Parsing", "Found fps " + fps[x] + "at array position " +x);
								}
								camera.setAvailableFps(fps);
								state= 503;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 503:	if(t[i].getId()==14){
								state=14;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 14:	if(t[i].getId()==11){
								camera = equipment.addCamera();
								state=11;
							}else if(t[i].getId()==12){
								lens = equipment.addLens();
								state=12;
							}else if(t[i].getId()==13){
								media= equipment.addMedia();
								state=13;
							}else if(t[i].getId()== 10){
								state=10;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
				
				case 13:	if(t[i].getId()==701){
								media.setId(Integer.parseInt(t[i].getValue()));
								state=701;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 701:	if(t[i].getId()==702){
								media.setName(t[i].getValue());
								state=702;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
				
				case 702:	if(t[i].getId()==703){
								media.setStorage(Integer.parseInt(t[i].getValue().split(",")[0]));
								media.setStorageFormat(Integer.parseInt(t[i].getValue().split(",")[1]));
								state=703;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
				
				case 703:	if(t[i].getId()==704){
								media.setType(Short.parseShort(t[i].getValue()));
								state=704;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 704:	if(t[i].getId()==15){
								state=15;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 15:	if(t[i].getId()==11){
								camera = equipment.addCamera();
								state=11;
							}else if(t[i].getId()==12){
								lens = equipment.addLens();
								state=12;
							}else if(t[i].getId()==13){
								media= equipment.addMedia();
								state=13;
							}else if(t[i].getId()== 10){
								state=10;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
				
				case 12:	if(t[i].getId()==601){
								lens.setId(Integer.parseInt(t[i].getValue()));
								state=601;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 601:	if(t[i].getId()==602){
								lens.setName(t[i].getValue());
								state= 602;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 602:	if(t[i].getId()==603){
								lens.setFixed(Boolean.parseBoolean(t[i].getValue()));
								state=603;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 603:	if(t[i].getId()==604){
								lens.setFocalLength(Integer.parseInt(t[i].getValue()));
								state=604;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 604:	if(t[i].getId()==605){
								lens.setMinFocalLength(Integer.parseInt(t[i].getValue()));
								state=605;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 605:	if(t[i].getId()==606){
								lens.setMaxFocalLength(Integer.parseInt(t[i].getValue()));
								state=606;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 606:	if(t[i].getId()==16){
								state=16;
							}else{
								valid=false;reportError(state, t[i].getId());
							}break;
				
				case 16:	if(t[i].getId()==11){
								camera = equipment.addCamera();
								state=11;
							}else if(t[i].getId()==12){
								lens = equipment.addLens();
								state=12;
							}else if(t[i].getId()==13){
								media= equipment.addMedia();
								state=13;
							}else if(t[i].getId()== 10){
								state=10;
							}else{
								valid= false;reportError(state, t[i].getId());
							}break;
			}//end of switch
		}if(!valid){ //End of for
			Log.d("Parsing", "Error while parsing");
			project= null;
		}
		return project;
	}//End of Method


	private static void reportError(int state, int tokenid){
		Log.d("Parsing", "Error while parsing at state " +state+ " left with code "+tokenid);
	}

		
		
		
		
}//End of Class
	
	

