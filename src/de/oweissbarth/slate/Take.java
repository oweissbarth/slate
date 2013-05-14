import java.sql.Time;

public class Take{
	private Time duration;
	private boolean usable;
	private String comment;
	private Media media;

	public Take(Time duration, boolean usable, String comment, Media media) {
		this.duration = duration;
		this.usable = usable;
		this.comment = comment;
		this.media = media;
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
}


