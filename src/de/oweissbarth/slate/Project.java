package de.oweissbarth.slate;

import java.util.ArrayList;

public class Project{
	
	private String name;
	private String director;
	private ArrayList<Scene> scenes = new ArrayList<Scene>();
	private int numberOfScenes;
	private Equipment equipment;

	public Project(){
		this.numberOfScenes = 0;
	}
	
	
	/*************************************************
	 					GETTER
	 *************************************************/
	public String getName(){
		return this.name;
	}
	
	public String getDirector(){
		return this.director;
	}
	
	public Scene[] getScenes(){
		int size = scenes.size();
		return scenes.toArray(new Scene[size]);
	}
	
	public Equipment getEquipment(){
		return this.equipment;
	}
	
	public String getXML(){
		String xml ="<Project>\n" +
				"\t&Projectname:" + this.name +"&\n"+
				"\t&director:" + this.director + "&\n";
		if(scenes!=null)
		for(Scene scene : scenes){
			xml += scene.getXML();
		}
		xml += "</Project>";
		
	return xml;
	}
	
	/****************************************************
	 					SETTER
	 ***************************************************/
	
	public void setName(String name){
		this.name= name;
	}
	
	public void setDirector(String director){
		this.director = director;
	}
	
	public Scene addScene(){
		this.numberOfScenes++;
		Scene scene = new Scene(this.numberOfScenes);
		scenes.add(scene);
		return scene;
	}
	
	public Equipment addEquipment(){
		equipment = new Equipment();
		return equipment;
	}

}
