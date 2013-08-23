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

public class Shot{
		private int id;
		private Lens lens;
		private int fps;
		private int focalLength;
		private short fieldSize;
		private short cameraMotion;
		private Camera camera;

		private LinkedList<Take> takes = new LinkedList<Take>();
		private int numberOfTakes;
		
		public Shot(int id){
			this.id =id;
			this.numberOfTakes=0;
		}

		
		/***************************************************
		 						GETTER
		 **************************************************/
		public int getFps(){
			return this.fps;
		}
		
		public int getID(){
			return this.id;
		}
		
		public int getFocalLength(){
			return this.focalLength;
		}
		
		public Camera getCamera(){
			return this.camera;
		}
		
		public Lens getLens(){
			return this.lens;
		}
		
		public short getFieldSize(){
			return this.fieldSize;
		}

		public short getCameraMotion(){
			return this.cameraMotion;
		}
		
		public Take[] getTakes(){
			int size = takes.size();
			return takes.toArray(new Take[size]);
		}
		
		public String getXML(){
			String xml = "<Shot>\n" + 
							"\t&shotid:"+this.id +"&\n"+
							"\t&lens:" + this.lens.getId() + "&\n"+
							"\t&fps:" + this.fps + "&\n"+
							"\t&focalLength:" + this.focalLength + "&\n"+
							"\t&camera:" + this.camera.getId() + "&\n" +
							"\t&fieldSize:"+ this.fieldSize + "&\n"+
							"\t&cameraMotion:"+ this.cameraMotion + "&\n";
			if(takes != null)
			for(Take take : takes){
				xml += take.getXML();
			}
			xml += "</Shot>";
			
		return xml;
		}
		
		public String[] getTakeList(){
			String[] takeList= new String[takes.size()+1];
			int i=0;
			for(Take take: takes){
				takeList[i]=take.toString();
				i++;
			}
			takeList[i]="Add Take";
			return takeList;
		
		}
		
		public Take getTakeById(int id){
			return this.takes.get(id);
		}
		
		/*****************************************************
		 						SETTER
		 *****************************************************/
		public Take addTake(){
			Take take = new Take(this.numberOfTakes);
			this.takes.add(take);
			this.numberOfTakes++;
			return take;
		}
		
		public void deleteTake(int id){
			this.takes.remove(id);
			this.numberOfTakes=0;
			for(Take take: this.takes){
				take.setId(this.numberOfTakes);
				this.numberOfTakes++;
			}
		}
		
		public void setId(int id){
			this.id = id;
		}
		
		public void setCamera(Camera camera){
			this.camera = camera;
		}
		
		public void setLens(Lens lens){
			this.lens=lens;
		}
		
		public void setFps(int fps){
			this.fps= fps;
		}
		
		public void setFocalLength(int focalLength){
			this.focalLength= focalLength;
		}
		
		public void setFieldSize(short fieldSize){
			this.fieldSize= fieldSize;
		}
		
		public void setCameraMotion(short cameraMotion){
			this.cameraMotion=cameraMotion;
		}
}

