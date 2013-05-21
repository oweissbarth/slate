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
				"\tid:" + this.id + "\n" +
				"\tname:" + this.name +"\n" +						
				"\tdescription:" + this.description + "\n" + 
				"\text:" + this.ext + ">\n";
		
		if(shots != null)
		for(Shot shot : shots){
			xml += shot.getXML();
		}
		
		xml +="</Scene>";
		
	return xml;
	}
}
