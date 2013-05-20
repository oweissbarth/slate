package de.oweissbarth.slate;
public class Project{
	
	private String name;
	private String director;
	private Scene[] scenes;
	private Equipment equipment;

	public Project(String name, String director){
		this.name = name;
		this.director= director;
		this.scenes = null;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDirector(){
		return this.director;
	}
	
	public Scene[] getScenes(){
		return this.scenes;
	}
	
	public String getXML(){
		String xml ="<Project\n" +
				"\t" + this.name +"\n"+
				"\t" + this.director + "\n"+
			">\n";
		for(Scene scene : scenes){
			xml += scene.getXML();
		}
		xml += "</Project>";
		
	return xml;
	}

}
