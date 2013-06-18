package de.oweissbarth.slate.data;

import java.util.ArrayList;

public class Scene{
	private ArrayList<Shot> shots = new ArrayList<Shot>();
	private int numberOfShots;
	private int id;
	private String name;
	private String description;
	private boolean ext;
	
	public Scene(int id){
		this.id = id;
		this.numberOfShots=0;
	}
	
	/**************************************************** 
	 						GETTER
	 ****************************************************/
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public int getID(){
		return this.id;
	}
	
	public boolean getExt(){
		return this.ext;
	}
	
	public Shot[] getShots(){
		int size = shots.size();
		return shots.toArray(new Shot[size]);
	}
	
	public String[] getShotList(){
		String[] shotList= new String[shots.size()+1];
		int i=0;
		for(Shot shot: shots){
			shotList[i]=shot.toString();
			i++;
		}
		shotList[i]="Add Shot";
		return shotList;
	
	}
	
	public Shot getShotById(int id){
		return this.shots.get(id);
	}
	
	public String getXML(){
		String xml = "<Scene>\n" + 
				"\t&sceneid:" + this.id + "&\n" +
				"\t&scenename:" + this.name +"&\n" +						
				"\t&description:" + this.description + "&\n" + 
				"\t&ext:" + this.ext + "&\n";
		
		if(shots != null)
		for(Shot shot : shots){
			xml += shot.getXML();
		}
		
		xml +="</Scene>\n";
		
	return xml;
	}
	
	public String toString(){
		return this.id + "\t" +  this.name;
	}
	
	
	/******************************************************
	 						SETTER
	 *****************************************************/
	
	public Shot addShot(){
		numberOfShots++;
		Shot shot = new Shot(numberOfShots);
		this.shots.add(shot);
		return shot;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name= name;
	}
	
	public void setDescription(String description){
		this.description= description;
	}
	
	public void setExt(boolean ext){
		this.ext= ext;
	}


}
