package de.oweissbarth.slate;
import java.util.*;

public class Camera{
		private String name;
		private int id;
		private int[] availableFps;

		public Camera(int id){
			this.id = id;
		}
	
		public String toString(){
			String string = "("+this.id+")"+ " " + this.name + "\n" + "available fps : " + Arrays.toString(this.availableFps);
			return string;
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
