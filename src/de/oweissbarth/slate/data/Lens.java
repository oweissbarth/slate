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
