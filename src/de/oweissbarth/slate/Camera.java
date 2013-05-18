package de.oweissbarth.slate;
import java.util.*;

public class Camera{
		private String name;
		private int id;
		private int[] availableFps;

		public Camera(String name, int[] availableFps, int id){
			this.name = name;
			this.id = id;
			this.availableFps = availableFps;
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
}
