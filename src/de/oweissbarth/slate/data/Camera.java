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

public class Camera{
		private String name;
		private int id;
		private int[] availableFps;

		public Camera(int id){
			this.id = id;
		}
	

		/******************************************
							Getter
		******************************************/

		public String getName(){
			return this.name;
		}

		public int getId(){
			return this.id;
		}

		public int[] getAvailableFps(){
			return this.availableFps;		
		}
		
		public String getXML(){
			String fps ="";
			for(int i=0; i<this.availableFps.length; i++){
				fps+=i;
				if((i+1)!=this.availableFps.length)
					fps+=",";
			}
			String xml="<Camera>\n"+
						"\t&cameraid:"+  this.id + "&\n"+
						"\t&cameraname:" +this.name +"&\n"+
						"\t&availableFps:" + fps + "&\n"+
						"</Camera>\n";
			return xml;
		}
		
		public String toString(){
			return this.id+1+"\t"+this.name;
		}
		/**************************************************
		 					SETTER
		 **************************************************/
		
		public void setId(int id){
			this.id=id;
		}
		
		public void setName(String name){
			this.name = name;
		}
		
		public void setAvailableFps(int[] availableFps){
			this.availableFps = availableFps;
		}
}
