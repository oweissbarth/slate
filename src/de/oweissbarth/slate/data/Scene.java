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

public class Scene{
	private LinkedList<Shot> shots = new LinkedList<Shot>();
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
