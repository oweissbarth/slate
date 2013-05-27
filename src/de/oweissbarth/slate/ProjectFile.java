package de.oweissbarth.slate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class ProjectFile {
	public static String[] listProjects(Context context){
		String[] files =  ProjectFile.listProjectFiles(context);
		for(String filename : files){
			filename.replace(".slate", "");
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
	    String xml= null;
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
	    		xml +="\n" + line;
	    	}while(line != null);
	    	
	    	
	    }catch(Exception e){
	    	return null;
	    }
	    return (new Project("test", "test"));//parseXML(xml);
	}
	
	

		
		
		
		
	}
	
	

