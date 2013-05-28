package de.oweissbarth.slate;

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
	
	
	public static Token[] partitionDotSlate(String xml){
		
		if(!xml.substring(0, 12).equals("---&slate-ver")){
			Log.d("File", "Error while Parsing File: Could not identify Header. Exspected '---&slate-ver' but found " + xml.substring(0,12));
		}
		int fileVersion = Integer.parseInt(xml.substring(12, 16));
		
		char[] input = xml.substring(17).replaceAll("\n", "").replaceAll(" ", "").replaceAll("\t", "").toCharArray();
		ArrayList<Token> tokenList = new ArrayList<Token>();
		
		String value ="";
		int id=-1;
		
		for(int i=0; i < input.length; i++){
			switch(input[i]){
				case '<' : value = "";
							i++;
							boolean close=false;
							if(input[i+1]=='/'){
								close=true;
								i++;
							}
							while((input[i]!='&')){
								value+=input[i];
								i++;
							}
							if(close)
								id =getTagID("close" + value );
							else
								id =getTagID(value);
							break;
							
				
							
				case '&' : value="";
							String identifier="";
							i++;
							while(input[i]!=':'){
								identifier+=input[i];
								i++;
							}while(input[i]!='&'){
								value+=input[i];
								i++;
							}
							id= getTagID(identifier);
							break;
							

				default : 	while((input[i]!='&') && (input[i]!='<'))
								i++;
			
			}tokenList.add(new Token(id, value));	
		}
		Log.d("File", "Finished partioning File!");
		return (Token[])tokenList.toArray();
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
		if(identifier.equals("camera"))
			return 11;
		if(identifier.equals("lens"))
			return 12;
		if(identifier.equals("media"))
			return 13;
		if(identifier.equals("closecamera"))
			return 14;
		if(identifier.equals("closemedia"))
			return 15;
		if(identifier.equals("closelens"))
			return 16;
		
		//PROJECT
		if(identifier.equals("Projectname"))
			return 0101;
		if(identifier.equals("director"))
			return 0102;
			
		//SCENE	
		if(identifier.equals("Sceneid"))
			return 0201;
		if(identifier.equals("scenename"))
			return 0202;
		if(identifier.equals("description"))
			return 0203;
		if(identifier.equals("ext"))
			return 0204;
		
		//SHOT
		if(identifier.equals("shotid"))
			return 0301;
		if(identifier.equals("lens"))
			return 0302;
		if(identifier.equals("fps"))
			return 0303;
		if(identifier.equals("focalLength"))
			return 0304;
		if(identifier.equals("camera"))
			return 0305;
		
		//TAKE
		if(identifier.equals("takeid"))
			return 0401;
		if(identifier.equals("duration"))
			return 0402;
		if(identifier.equals("usable"))
			return 0403;
		if(identifier.equals("comment"))
			return 0404;
		if(identifier.equals("media"))
			return 0405;
		
		//CAMERA
		if(identifier.equals("cameraID"))
			return 0501;
		if(identifier.equals("cameraname"))
			return 0502;
		if(identifier.equals("availableFps"))
			return 0503;
		
		//LENS
		if(identifier.equals("lensid"))
			return 0601;
		if(identifier.equals("lensname"))
			return 0602;
		if(identifier.equals("fixed"))
			return 0603;
		if(identifier.equals("lensfocalLength"))
			return 0604;
		if(identifier.equals("minFocalLength"))
			return 0605;
		if(identifier.equals("maxFocalLength"))
			return 0606;
		
		//MEDIA
		if(identifier.equals("mediaid"))
			return 0701;
		if(identifier.equals("medianame"))
			return 0702;
		if(identifier.equals("storage"))
			return 0703;
		if(identifier.equals("type"))
			return 0704;
		
		return -1;
	}
}