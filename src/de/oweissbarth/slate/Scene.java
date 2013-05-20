package de.oweissbarth.slate;
public class Scene{
	private Shot[] shots;
	private short id;
	private String name;
	private String description;
	private boolean ext;
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public short getID(){
		return this.id;
	}
	
	public boolean getExt(){
		return this.ext;
	}
	
	public Shot[] getShots(){
		return this.shots;
	}
	
	public String getXML(){
		String xml = "<Scene\n" + 
				"\t" + this.id + "\n" +
				"\t" + this.name +"\n" +						
				"\t" + this.description + "\n" + 
				"\t" + this.ext + "\n";
		
		for(Shot shot : shots){
			xml += shot.getXML();
		}
		
		xml +="</Scene>";
		
	return xml;
	}
}
