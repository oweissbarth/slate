package de.oweissbarth.slate.data;

import java.util.ArrayList;

import android.util.Log;

final class Token {
	private final int id;
	private final String value;
	
	
	public Token(int id, String value){
		this.id = id;
		this.value= value;
	}
	
	
	public String getValue(){
		return this.value;
	}
	
	public int getId(){
		return this.id;
	}
	
	
	public static Token[] partitionDotSlate(String dotSlate1){
		//dotSlate = dotSlate.replaceAll("\n", "").replaceAll(" ", "").replaceAll("\t", "");
		String dotSlate = dotSlate1.trim();
		for(int x = 0; x<dotSlate.length(); x++){
			Log.d("Parsing", x+" :"+dotSlate.charAt(x));
		}
		if(!dotSlate.substring(0, 13).equals("---&slate-ver")){
			Log.d("File", "Error while Parsing File: Could not identify Header. Exspected '---&slate-ver' but found " + dotSlate.substring(0,12));
		}
		int fileVersion = Integer.parseInt(dotSlate.substring(13, 17));
		
		char[] input = dotSlate.substring(21).toCharArray();
		ArrayList<Token> tokenList = new ArrayList<Token>();
		
		String value ="";
		int id=-1;
		for(int x = 0; x<input.length; x++){
			Log.d("Parsing", x+" :"+input[x]);
		}
		Log.d("File", "Start calculating ids");
		
		boolean newToken = false;
		for(int i=0; i < input.length; i++){
			Log.d("Parsing:", i+"/"+input.length +  "-->" + input[i]);
			switch(input[i]){
				case '<' : newToken = true;
							i++;
							value = "";
							boolean close=false;
							if(input[i]=='/'){
								close=true;
								i++;
							}
							while((input[i]!='>')&&(i<input.length)){
								value+=input[i];
								i++;
							}
							if(close){
								id =getTagID("close" + value);
								Log.d("Parsing", "getTag: "+ value + " id ="+ id);
							}else{
								id =getTagID(value);
								Log.d("Parsing", "getTag: "+ value + " id ="+ id);
							}
							break;
							
				
							
				case '&' : newToken = true;
							i++;
							value="";
							String identifier="";
							while((input[i]!=':')&&(i<input.length)){
								identifier+=input[i];
								i++;
							}
							i++;
							while((input[i]!='&')&&(i<input.length)){
								
								value+=input[i];
								i++;
							}
							id= getTagID(identifier);
							Log.d("Parsing", "getTag: "+ identifier + " id ="+ id+ ", value="+ value);
							break;
							

				default : 	newToken = false;
							while((input[i]!='&') && (input[i]!='<') && (i < input.length))
								i++;
							i--;break;
			
			}
			if(newToken)
			tokenList.add(new Token(id, value));	
		}
		Log.d("File", "Finished partioning File!");
		return tokenList.toArray(new Token[tokenList.size()]);
	}
		
		
		
	private static int getTagID(String identifier){
		//TAGS
		if (identifier.equals("Project"))
			return 1;
		if(identifier.equals("Scene"))
			return 2;
		if(identifier.equals("Shot"))
			return 3;
		if(identifier.equals("Take"))
			return 4;
		if(identifier.equals("Equipment"))
			return 5;
		if (identifier.equals("closeProject"))
			return 6;
		if(identifier.equals("closeScene"))
			return 7;
		if(identifier.equals("closeShot"))
			return 8;
		if(identifier.equals("closeTake"))
			return 9;
		if(identifier.equals("closeEquipment"))
			return 10;
		if(identifier.equals("Camera"))
			return 11;
		if(identifier.equals("Lens"))
			return 12;
		if(identifier.equals("Media"))
			return 13;
		if(identifier.equals("closeCamera"))
			return 14;
		if(identifier.equals("closeMedia"))
			return 15;
		if(identifier.equals("closeLens"))
			return 16;
		
		//PROJECT
		if(identifier.equals("Projectname"))
			return 101;
		if(identifier.equals("director"))
			return 102;
			
		//SCENE	
		if(identifier.equals("sceneid"))
			return 201;
		if(identifier.equals("scenename"))
			return 202;
		if(identifier.equals("description"))
			return 203;
		if(identifier.equals("ext"))
			return 204;
		
		//SHOT
		if(identifier.equals("shotid"))
			return 301;
		if(identifier.equals("lens"))
			return 302;
		if(identifier.equals("fps"))
			return 303;
		if(identifier.equals("focalLength"))
			return 304;
		if(identifier.equals("camera"))
			return 305;
		
		//TAKE
		if(identifier.equals("takeid"))
			return 401;
		if(identifier.equals("duration"))
			return 402;
		if(identifier.equals("usable"))
			return 403;
		if(identifier.equals("comment"))
			return 404;
		if(identifier.equals("media"))
			return 405;
		
		//CAMERA
		if(identifier.equals("cameraID"))
			return 501;
		if(identifier.equals("cameraname"))
			return 502;
		if(identifier.equals("availableFps"))
			return 503;
		
		//LENS
		if(identifier.equals("lensid"))
			return 601;
		if(identifier.equals("lensname"))
			return 602;
		if(identifier.equals("fixed"))
			return 603;
		if(identifier.equals("lensfocalLength"))
			return 604;
		if(identifier.equals("minFocalLength"))
			return 605;
		if(identifier.equals("maxFocalLength"))
			return 606;
		
		//MEDIA
		if(identifier.equals("mediaid"))
			return 701;
		if(identifier.equals("medianame"))
			return 702;
		if(identifier.equals("storage"))
			return 703;
		if(identifier.equals("type"))
			return 704;
		
		return -1;
	}
}