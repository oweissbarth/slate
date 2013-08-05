/*******************************************************************************
 * Copyright 2013 Oliver Weissbarth
 * 
 * This file is part of Slate.
 * 
 *     Slate is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *      Slate is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with  Slate.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.oweissbarth.slate.data;
import java.util.LinkedList;

import android.util.Log;

public class Equipment{
	private LinkedList<Camera> cameras = new LinkedList<Camera>();
	private int currentCameraIndex;
	private LinkedList<Lens> lenses = new LinkedList<Lens>();
	private int currentLensIndex;
	private LinkedList<Media> media = new LinkedList<Media>();
	private int currentMediaIndex;

	public Equipment(){
		this.currentCameraIndex=-1;
		this.currentLensIndex=-1;
		this.currentMediaIndex=-1;
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
		return this.cameras.get(id);
	}
	
	public Lens getLensById(int id){
		return this.lenses.get(id);
		
	}
	
	public Media getMediaById(int id){
		return this.media.get(id);
	}
	
	public String[] getCameraList(){
		Log.d("ListCameras", cameras.toString() );
		String[] cameraList= new String[cameras.size()];
		int i=0;
		for(Camera camera: cameras){
			cameraList[i]=camera.toString();
			i++;
		}
		return cameraList;
	
	}
	
	public String[] getLensList(){
		String[] lensList= new String[lenses.size()];
		int i=0;
		for(Lens lens: lenses){
			lensList[i]=lens.toString();
			i++;
		}
		return lensList;
	
	}
	
	public String[] getMediaList(){
		String[] mediaList= new String[media.size()];
		int i=0;
		for(Media medium: media){
			mediaList[i]=medium.toString();
			i++;
		}
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
		currentLensIndex++;
		this.lenses.add(lens);
	}
	public Lens addLens(){
		currentLensIndex++;
		Lens lens = new Lens(currentLensIndex);
		this.lenses.add(lens);
		return lens;
	}

	public void addCamera(Camera camera){
		currentCameraIndex++;
		this.cameras.add(camera);
	}
	public Camera addCamera(){
		currentCameraIndex++;
		Camera camera = new Camera(currentCameraIndex);
		this.cameras.add(camera);
		return camera;
	}

	public void addMedia(Media media){
		currentMediaIndex++;
		this.media.add(media);
	}
	public Media addMedia(){
		currentMediaIndex++;
		Media media = new Media(currentMediaIndex);
		this.media.add(media);
		return media;
	}
}
