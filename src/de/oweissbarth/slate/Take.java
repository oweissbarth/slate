package de.oweissbarth.slate;
import java.sql.Time;

public class Take{
	private int id;
	private Time duration;
	private boolean usable;
	private String comment;
	private Media media;

	public Take(int id) {
		this.id = id;
	}

	/**************************************************
						Getter
	**************************************************/
	
	public Time getDuration(){
		return this.duration;
	}

	public boolean getUsabale(){
		return this.usable;
	}

	public String getComment(){
		return this.comment;
	}
	
	public Media getMedia(){
		return this.media;
	}
	
	public String getXML(){
		String xml = "<Take>\n"+
						"\t&takeid:"+this.id +"&\n"+
						"\t&duration:" + this.duration + "&\n"+
						"\t&usable:" + this.usable + "&\n" +
						"\t&comment:" + this.comment + "&\n" +
						"\t&media:" + this.media.getId()  + "&\n" +
					"</Take>";
		
		return xml;
	}
	
	/******************************************************
	 						SETTER
	 ******************************************************/
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setDuration(Time duration){
		this.duration= duration;
	}
	
	public void setUsable(boolean usable){
		this.usable= usable;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public void setMedia(Media media){
		this.media = media;
	}
}


