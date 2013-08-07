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
import java.sql.Time;

public class Take{
	private int id;
	private Time duration;
	private boolean usable;
	private String comment;
	private Media media;

	public Take(int id) {
		this.id = id;
	}

	/**************************************************
						Getter
	**************************************************/
	public int getID(){
		return this.id;
	}
	
	public Time getDuration(){
		return this.duration;
	}

	public boolean getUsabale(){
		return this.usable;
	}

	public String getComment(){
		return this.comment;
	}
	
	public Media getMedia(){
		return this.media;
	}
	
	public String getXML(){
		String xml = "<Take>\n"+
						"\t&takeid:"+this.id +"&\n"+
						"\t&duration:" + this.duration + "&\n"+
						"\t&usable:" + this.usable + "&\n" +
						"\t&comment:" + this.comment + "&\n" +
						"\t&media:" + this.media.getId()  + "&\n" +
					"</Take>";
		
		return xml;
	}
	
	/******************************************************
	 						SETTER
	 ******************************************************/
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setDuration(Time duration){
		this.duration= duration;
	}
	
	public void setUsable(boolean usable){
		this.usable= usable;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public void setMedia(Media media){
		this.media = media;
	}
}


