package de.oweissbarth.slate.data;

import java.util.ArrayList;

public class Shot{
		private char id;
		private Lens lens;
		private int fps;
		private int focalLength;
		private Camera camera;

		private ArrayList<Take> takes = new ArrayList<Take>();
		private int numberOfTakes;
		
		public Shot(int idcode){
			this.id = (char)(idcode + 97);
			this.numberOfTakes=0;
		}

		
		/***************************************************
		 						GETTER
		 **************************************************/
		public int getFps(){
			return this.fps;
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
		
		public Take[] getTakes(){
			int size = takes.size();
			return takes.toArray(new Take[size]);
		}
		
		public String getXML(){
			String xml = "<Shot>\n" + 
							"\t&shotid:"+this.id +"&\n"+
							"\t&fps:" + this.fps + "&\n"+
							"\t&focalLength:" + this.focalLength + "&\n"+
							"\t&camera:" + this.camera.getId() + "&\n" +
							"\t&lens:" + this.lens.getId() + "&\n";
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
		
		
		/*****************************************************
		 						SETTER
		 *****************************************************/
		public Take addTake(){
			this.numberOfTakes++;
			Take take = new Take(this.numberOfTakes);
			this.takes.add(take);
			return take;
		}
		
		public void setId(char id){
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
}

