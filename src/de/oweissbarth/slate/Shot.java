package de.oweissbarth.slate;
public class Shot{
		private char id;
		private Lens lens;
		private int fps;
		private int focalLength;
		private Camera camera;

		private Take[] takes;

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
		
		public String getXML(){
			String xml = "<Shot\n" + 
							"\t6shotid:"+this.id +"\n"+
							"\t&fps:" + this.fps + "\n"+
							"\t&focalLength:" + this.focalLength + "\n"+
							"\t&camera:" + this.camera.getId() + "\n" +
							"\t&lens:" + this.lens.getId() + ">\n";
			if(takes != null)
			for(Take take : takes){
				xml += take.getXML();
			}
			xml += "</Shot>";
			
		return xml;
		}
}

