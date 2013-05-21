package de.oweissbarth.slate;
public class Lens{
		
		private String name;

		private int id;		

		private boolean fixed;

		private  int focalLength;

		private int minFocalLength;
		private int maxFocalLength;


		public Lens(String name, int[] focalLength, int id){
			this.name = name;
			this.id = id;		

			if((focalLength.length) == 2){
				this.fixed = false;
				this.focalLength = 0;				
				this.minFocalLength = focalLength[0];
				this.maxFocalLength = focalLength[1];
			}else{
				this.fixed = true;
				this.minFocalLength = 0;
				this.maxFocalLength = 0;
				this.focalLength = focalLength[0];
			}	
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

}
