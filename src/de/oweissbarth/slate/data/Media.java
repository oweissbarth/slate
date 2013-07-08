package de.oweissbarth.slate.data;
public class Media{
	private String name;
	private int id;
	private int storage;
	private int storageFormat; //0 = Tb, 1 = Gb, 2 = Mb, 3 = Kb, 4 = b 
	private short type; //0 = Tape, 1 = SD-Card, 2 = Compact Flash

	public Media(int id){
		this.id = id;
	}

	/**************************************************
							GETTER
	**************************************************/

	public String getName(){
		return this.name;
	}

	public int getId(){
		return this.id;
	}
	
	public int getStorage(){
		return this.storage;
	}
	
	public int getStorageFormat(){
		return this.storageFormat;
	}

	public short getType(){
		return this.type;	
	}
	
	public String toString(){
		return this.name+"\t"; 
	}
	
	public String getXML(){
		String xml="<Media>" + 
					"\t&mediaid:"+ this.id + "&\n"+
					"\t&medianame:"+ this.name + "&\n"+
					"\t&storage:" + this.storage + "," + this.storageFormat+"&\n"+
					"\t&type:" + this.type + "&\n"+
					"</Media>";
		return xml;
	}
	
	/*****************************************************
	 						SETTER
	 *****************************************************/
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setStorage(int storage){
		this.storage= storage;
	}
	
	public void setStorageFormat(int format){
		this.storageFormat = format;
	}
	
	public void setType(short type){
		this.type= type;
	}
}
