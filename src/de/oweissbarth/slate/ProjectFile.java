package de.oweissbarth.slate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import android.content.Context;
import android.util.Log;

public class ProjectFile {
	
	
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
	
	public static boolean save(Project project, Context context){
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
	    Log.d("File", "Generated XML!");
	    out.write(xml.getBytes());
	    Log.d("File", "Wrote Data..");
	    out.flush();
	    out.close();
	    }catch(Exception e){
	    	Log.d("File", "Error while writing File");
	    	return false;
	    }
	    return true;
	}
	
	public static Project load(String name, Context context){
		
		File dir = (context.getExternalFilesDir(null));
	    File file = new File(dir, name + ".slate");
	    String slate= null;
	    if (!file.exists ()) {
	    	return null;
	    }
	    try{
	    	InputStream in = new FileInputStream(file);
	    	
	    	InputStreamReader inputreader = new InputStreamReader(in);
	    	BufferedReader reader = new BufferedReader(inputreader);

	    	String line;
	    	do{
	    		line = reader.readLine();
	    		slate +="\n" + line;
	    	}while(line != null);
	    	reader.close();
	    	
	    	
	    }catch(Exception e){
	    	return null;
	    }
	    return parseDotSlate(slate);
	}
	
	
	public static Project parseDotSlate(String input){
		Token[] t = Token.partitionDotSlate(input);
		
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
		
		for(int i=0; (i< t.length)&&(valid); i++){
			switch(state){
				case 1:		if(t[i].getId()==0001){
								state=2;
								project = new Project();
							}else{
								valid=false;
							}
				case 2:		if(t[i].getId()==0101){
							project.setName(t[i].getValue());
							state=3;
							}else{
								valid=false;
							}
				
				case 3:		if(t[i].getId()==0102){
								project.setName(t[i].getValue());
								state=4;
							}else{
								valid=false;
							}
				
				case 4:		if(t[i].getId()==0002){
								scene=project.addScene();
								state=6;
							}else if(t[i].getId()==0005){
								equipment=project.addEquipment();
								state=30;
							}else if(t[i].getId()==0006){
								state=5;
							}else{
								valid=false;
							}
				
				case 5:		if(valid)
								return project;
				
				case 6:		if(t[i].getId()==0201){
								scene.setId(Integer.parseInt(t[i].getValue()));
								state=7;
							}else{
								valid=false;
							}
				
				case 7:		if(t[i].getId()==0202){
								scene.setName(t[i].getValue());
								state=8;
							}else{
								valid=false;
							}
							
				case 8:		if(t[i].getId()==0203){
								scene.setDescription(t[i].getValue());
								state=9;
							}else{
								valid=false;
							}
				
				case 9:		if(t[i].getId()==0204){
								scene.setExt(Boolean.parseBoolean(t[i].getValue()));
								state=10;
							}else{
								valid= false;
							}
				
				case 10:	if(t[i].getId()==0007){
								state=11;
							}else if(t[i].getId()==0003){
								shot = scene.addShot();
								state=12;
							}else{
								valid=false;
							}
				
				case 11:	if(t[i].getId()==0006){
								state=5;
							}else if(t[i].getId()==0002){
								scene=project.addScene();
								state= 6;
							}else{
								valid=false;
							}
				
				case 12:	if(t[i].getId()==0301){
								shot.setId((t[i].getValue()).charAt(0));
								state=13;
							}else{
								valid=false;
							}
				
				case 13:	if(t[i].getId()==0302){ 
								shot.setLens(equipment.getLensById(Integer.parseInt(t[i].getValue())));
								state=14;
							}else{
								valid=false;
							}
				
				case 14:	if(t[i].getId()==0303){
								shot.setFps(Integer.parseInt(t[i].getValue()));
								state=15;
							}else{
								valid=false;
							}
				
				case 15:	if(t[i].getId()==0304){
								shot.setFocalLength(Integer.parseInt(t[i].getValue()));
								state=16;
							}else{
								valid= false;
							}
				
				case 16:	if(t[i].getId()==0305){ 
								shot.setCamera(equipment.getCameraById(Integer.parseInt(t[i].getValue())));
								state=17;
							}else{
								valid=false;
							}
				
				case 17:	if(t[i].getId()==8){
								state=18;
							}else if(t[i].getId()==0004){
								take=shot.addTake();
								state=19;
							}else{
								valid=false;
							}
				
				case 18:	if(t[i].getId()==0007){
								state=11;
							}else if(t[i].getId()==0003){
								shot=scene.addShot();
								state=12;
							}else{
								valid=false;
							}
				
				case 19:	if(t[i].getId()==0401){
								take.setId(Integer.parseInt(t[i].getValue()));
								state=20;
							}else{
								valid=false;
							}
				case 20:	if(t[i].getId()==0402){
								take.setDuration(Time.valueOf(t[i].getValue()));
								state=21;
							}else{
								valid= false;
							}
				
				case 21:	if(t[i].getId()==0403){
								take.setUsable(Boolean.parseBoolean(t[i].getValue()));
								state= 22;
							}else{
								valid=false;
							}
				
				case 22:	if(t[i].getId()==0404){
								take.setComment(t[i].getValue());
								state=23;
							}else{
								valid= false;
							}
				
				case 23:	if(t[i].getId()==0405){
								take.setMedia(equipment.getMediaById(Integer.parseInt(t[i].getValue())));
								state =24;
							}else{
								valid=false;
							}
				
				case 24:	if(t[i].getId()==9){
								state=25;
							}else{
								valid=false;
							}
				
				case 25:	if(t[i].getId()==0004){
								take=shot.addTake();
								state=19;
							}else if(t[i].getId()==8){
								state=18;
							}else{
								valid=false;
							}
				
				case 30:	if(t[i].getId()==0012){
								lens = equipment.addLens();
								state= 43;
							}else if(t[i].getId()==0013){
								media = equipment.addMedia();
								state=37;
							}else if(t[i].getId()==0011){
								camera = equipment.addCamera();
								state=32;
							}else if(t[i].getId()==0010){
								state=31;
							}else{
								valid= false;
							}
				
				case 31:	if(t[i].getId()==0006){
								state=5;
							}else if(t[i].getId()==0002){
								scene = project.addScene();
								state=6;
							}else{
								valid= false;
							}
				
				case 32:	if(t[i].getId()==0501){
								camera.setId(Integer.parseInt(t[i].getValue()));
								state=33;
							}else{
								valid=false;
							}
				
				case 33:	if(t[i].getId()==0502){
								camera.setName(t[i].getValue());
								state=34;
							}else{
								valid=false;
							}
				
				case 34:	if(t[i].getId()==0503){
								String[] array = t[i].getValue().split(",");
								int[] fps= new int[array.length];
								for(int x=0; x < array.length; i++){
									fps[x] = Integer.parseInt(array[x]);
								}
								camera.setAvailableFps(fps);
								state= 35;
							}else{
								valid=false;
							}
				
				case 35:	if(t[i].getId()==00014){
								state=36;
							}else{
								valid=false;
							}
				
				case 36:	if(t[i].getId()==0011){
								camera = equipment.addCamera();
								state=32;
							}else if(t[i].getId()==0012){
								lens = equipment.addLens();
								state=43;
							}else if(t[i].getId()==0013){
								media= equipment.addMedia();
								state=37;
							}else if(t[i].getId()== 0010){
								state=31;
							}else{
								valid= false;
							}
				
				case 37:	if(t[i].getId()==0701){
								media.setId(Integer.parseInt(t[i].getValue()));
								state=38;
							}else{
								valid=false;
							}
				
				case 38:	if(t[i].getId()==0702){
								media.setName(t[i].getValue());
								state=39;
							}else{
								valid= false;
							}
				
				case 39:	if(t[i].getId()==0703){
								media.setStorage(Integer.parseInt(t[i].getValue()));
								state=40;
							}else{
								valid= false;
							}
				
				case 40:	if(t[i].getId()==0704){
								media.setType(Short.parseShort(t[i].getValue()));
								state=41;
							}else{
								valid=false;
							}
				
				case 41:	if(t[i].getId()==0015){
								state=42;
							}else{
								valid=false;
							}
				
				case 42:	if(t[i].getId()==0011){
								camera = equipment.addCamera();
								state=32;
							}else if(t[i].getId()==0012){
								lens = equipment.addLens();
								state=43;
							}else if(t[i].getId()==0013){
								media= equipment.addMedia();
								state=37;
							}else if(t[i].getId()== 0010){
								state=31;
							}else{
								valid= false;
							}
				
				case 43:	if(t[i].getId()==0601){
								lens.setId(Integer.parseInt(t[i].getValue()));
								state=44;
							}else{
								valid=false;
							}
				
				case 44:	if(t[i].getId()==0602){
								lens.setName(t[i].getValue());
								state= 45;
							}else{
								valid=false;
							}
				
				case 45:	if(t[i].getId()==0603){
								lens.setFixed(Boolean.parseBoolean(t[i].getValue()));
								state=46;
							}else{
								valid=false;
							}
				
				case 46:	if(t[i].getId()==0604){
								lens.setFocalLength(Integer.parseInt(t[i].getValue()));
								state=47;
							}else{
								valid=false;
							}
				
				case 47:	if(t[i].getId()==0605){
								lens.setMinFocalLength(Integer.parseInt(t[i].getValue()));
								state=48;
							}else{
								valid=false;
							}
				
				case 48:	if(t[i].getId()==0606){
								lens.setMaxFocalLength(Integer.parseInt(t[i].getValue()));
								state=49;
							}else{
								valid=false;
							}
				
				case 49:	if(t[i].getId()==0016){
								state=50;
							}else{
								valid=false;
							}
				
				case 50:	if(t[i].getId()==0011){
								camera = equipment.addCamera();
								state=32;
							}else if(t[i].getId()==0012){
								lens = equipment.addLens();
								state=43;
							}else if(t[i].getId()==0013){
								media= equipment.addMedia();
								state=37;
							}else if(t[i].getId()== 0010){
								state=31;
							}else{
								valid= false;
							}
			}
		}if(!valid)
			project= null;
		
		return project;
	}

		
		
		
		
}
	
	

