package de.oweissbarth.slate;
public class Shot{
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
							"\t" + this.fps + "\n"+
							"\t" + this.focalLength + "\n"+
							"\t" + this.camera.getId() + "\n" +
							"\t" + this.lens.getId() + "\n";
			for(Take take : takes){
				xml += take.getXML();
			}
			xml += "</Shot>";
			
		return xml;
		}
}

