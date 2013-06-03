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

		public int getId(){
			return this.id;
		}
		
		public String getName(){
			return this.name;
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
