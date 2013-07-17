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
package de.oweissbarth.slate.data;

import java.util.LinkedList;

public class Project{
	
	private String name;
	private String director;
	private LinkedList<Scene> scenes = new LinkedList<Scene>();
	private int numberOfScenes;
	private Equipment equipment;

	public Project(){
		this.numberOfScenes = 0;
		this.equipment = new Equipment();
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
		String xml ="<Project>\n";
			xml+="\t&Projectname:" + this.name +"&\n"+
				"\t&director:" + this.director + "&\n";
			xml+=this.equipment.getXML()+"\n";
		if(scenes!=null)
		for(Scene scene : scenes){
			xml += scene.getXML();
		}
		xml += "</Project>";
		
	return xml;
	}
	
	public String[] getSceneList(){
		String[] scenesList= new String[scenes.size()+1];
		int i=0;
		for(Scene scene: scenes){
			scenesList[i]=scene.toString();
			i++;
		}
		scenesList[i]="Add Scene";
		return scenesList;
	}
	
	public Scene getSceneById(int id){
		return scenes.get(id);
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
	
	public void deleteScene(int id){
		this.numberOfScenes--;
		scenes.remove(id);
	}
	
	public Equipment addEquipment(){
		equipment = new Equipment();
		return equipment;
	}

}
