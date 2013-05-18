package de.oweissbarth.slate;
import java.util.*;

public class Equipment{
	private ArrayList<Camera> cameras = new ArrayList<Camera>();
	private ArrayList<Lens> lenses = new ArrayList<Lens>();
	private ArrayList<Media> media = new ArrayList<Media>();

	public Equipment(){

	}

	/*************************************************
						Getter
	*************************************************/

	public Lens[] getAvailableLenses(){
		int size = lenses.size();
		Lens[] alens = lenses.toArray(new Lens[size]);		
		return alens;
	}

	public Camera[] getAvailableCameras(){
		int size = cameras.size();
		Camera[] acam = cameras.toArray(new Camera[size]);		
		return acam;
	}

	public Media[] getAvailableMedia(){
		int size = media.size();
		Media[] amedia = media.toArray(new Media[size]);		
		return amedia;
	}

	/*************************************************
						Setter
	*************************************************/

	public void addLens(Lens lens){
		this.lenses.add(lens);
	}

	public void addCamera(Camera camera){
		this.cameras.add(camera);
	}

	public void addMedia(Media media){
		this.media.add(media);
	}
}
