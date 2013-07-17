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
public class Lens{
		
		private String name;

		private int id;		

		private boolean fixed;

		private  int focalLength;

		private int minFocalLength;
		private int maxFocalLength;


		public Lens(int id){
			this.id = id;
		}

		/***********************************************
							Getter
		***********************************************/		

		public boolean getFixed(){
			return this.fixed;
		}

		public int[] getFocalLength(){
			if(fixed){			
				return (new int[] {this.focalLength});		
			}else
				return (new int[] {this.minFocalLength, this.maxFocalLength});	
		}
		
		public int getMinFocalLength(){
			return this.minFocalLength;
		}
		
		public int getMaxFocalLength(){
			return this.maxFocalLength;
		}

		public int getId(){
			return this.id;
		}
		
		public String getName(){
			return this.name;
		}
		
		public String getXML(){
			String xml="<Lens>\n"+
						"\t&lensid:" + this.id +"&\n"+
						"\t&lensname:" + this.name +"&\n"+
						"\t&fixed:"+	this.fixed +"&\n"+
						"\t&focalLength:" + this.focalLength +"&\n"+
						"\t&minfocalLength:" + this.minFocalLength +"&\n"+
						"\t&maxfocalLength:" + this.maxFocalLength +"&\n"+
						"<Lens>";
			return xml;
		}
		
		
		/****************************************************
		 						SETTER
		 ****************************************************/
		public void setId(int id){
			this.id = id;
		}
		
		
		public void setFocalLength(int focalLength){
			this.focalLength= focalLength;
		}
		
		public void setName(String name){
			this.name = name;
		}
		
		public void setFixed(boolean fixed){
			this.fixed = fixed;
		}
		
		public void setMinFocalLength(int minFocalLength){
			this.minFocalLength = minFocalLength;
		}
		
		public void setMaxFocalLength(int maxFocalLength){
			this.maxFocalLength=maxFocalLength;
		}

}
