package de.oweissbarth.slate.data;
import java.util.*;

import android.util.Log;

public class Equipment{
	private ArrayList<Camera> cameras = new ArrayList<Camera>();
	private int numberOfCameras;
	private ArrayList<Lens> lenses = new ArrayList<Lens>();
	private int numberOfLenses;
	private ArrayList<Media> media = new ArrayList<Media>();
	private int numberOfMedia;

	public Equipment(){
		this.numberOfCameras=0;
		this.numberOfLenses=0;
		this.numberOfMedia=0;
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
	
	public Camera getCameraById(int id){
		Camera[] cameras = getAvailableCameras();
		for(Camera camera: cameras)
			if(camera.getId()==id)
				return camera;
		return null;
	}
	
	public Lens getLensById(int id){
		Lens[] lenses = getAvailableLenses();
		for(Lens lens: lenses)
			if(lens.getId()==id)
				return lens;
		return null;
	}
	
	public Media getMediaById(int id){
		Media[] media = getAvailableMedia();
		for(Media medium: media)
			if(medium.getId()==id)
				return medium;
		return null;
	}
	
	public String[] getCameraList(){
		Log.d("ListCameras", cameras.toString() );
		String[] cameraList= new String[cameras.size()+1];
		int i=0;
		for(Camera camera: cameras){
			cameraList[i]=camera.toString();
			i++;
		}
		cameraList[i]="Add Camera";
		return cameraList;
	
	}
	
	public String[] getLensList(){
		String[] lensList= new String[lenses.size()+1];
		int i=0;
		for(Lens lens: lenses){
			lensList[i]=lens.toString();
			i++;
		}
		lensList[i]="Add Lens";
		return lensList;
	
	}
	
	public String[] getMediaList(){
		String[] mediaList= new String[media.size()+1];
		int i=0;
		for(Media medium: media){
			mediaList[i]=medium.toString();
			i++;
		}
		mediaList[i]="Add Camera";
		return mediaList;
	
	}
	
	public String getXML(){
		String xml= "<Equipment>\n"; 
					for(Media media: this.media){
						xml+= media.getXML();
					}
					for(Camera camera: this.cameras){
						xml+= camera.getXML();
					}
					for(Lens lens: this.lenses){
						xml+= lens.getXML();
					}
				xml+="</Equipment>";
		return xml;
	}

	/*************************************************
						Setter
	*************************************************/

	public void addLens(Lens lens){
		numberOfLenses++;
		this.lenses.add(lens);
	}
	public Lens addLens(){
		numberOfLenses++;
		Lens lens = new Lens(numberOfLenses);
		this.lenses.add(lens);
		return lens;
	}

	public void addCamera(Camera camera){
		numberOfCameras++;
		this.cameras.add(camera);
	}
	public Camera addCamera(){
		numberOfCameras++;
		Camera camera = new Camera(numberOfCameras);
		this.cameras.add(camera);
		return camera;
	}

	public void addMedia(Media media){
		numberOfMedia++;
		this.media.add(media);
	}
	public Media addMedia(){
		numberOfMedia++;
		Media media = new Media(numberOfMedia);
		this.media.add(media);
		return media;
	}
}
