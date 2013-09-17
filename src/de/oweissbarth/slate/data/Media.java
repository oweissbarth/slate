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


public class Media{
	private String name;
	private int id;
	private int storage;
	private int storageFormat; //0 = Tb, 1 = Gb, 2 = Mb, 3 = Kb, 4 = b 
	private short type; //0 = Tape, 1 = SD-Card, 2 = Compact Flash
	private String storageString;

	public Media(int id){
		this.id = id;
	}

	/**************************************************
							GETTER
	**************************************************/

	public String getName(){
		return this.name;
	}

	public int getId(){
		return this.id;
	}
	
	public int getStorage(){
		return this.storage;
	}
	
	public short getStorageFormat(){
		return (short)this.storageFormat;
	}

	public short getType(){
		return this.type;	
	}
	
	public String toString(){
		return this.id +"\t"+ this.name + "\t"+ this.storageString;
	}
	
	public String getStorageString(){
		return this.storageString;
	}
	
	public String getXML(){
		String xml="<Media>\n" + 
					"\t&mediaid:"+ this.id + "&\n"+
					"\t&medianame:"+ this.name + "&\n"+
					"\t&storage:" + this.storage + "," + this.storageFormat+"&\n"+
					"\t&type:" + this.type + "&\n"+
					"\t&storageString:"+this.storageString+"&\n"+
					"</Media>\n";
		return xml;
	}
	
	/*****************************************************
	 						SETTER
	 *****************************************************/
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setStorage(int storage){
		this.storage= storage;
	}
	
	public void setStorageFormat(int format){
		this.storageFormat = format;
	}
	
	public void setType(short type){
		this.type= type;
	}
	public void setStorageString(String string){
		this.storageString=string;
	}
}
