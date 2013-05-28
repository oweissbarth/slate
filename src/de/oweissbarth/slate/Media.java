package de.oweissbarth.slate;
public class Media{
	private String name;
	private int id;
	private int storage;
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

	public short getType(){
		return this.type;	
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
	
	public void setType(short type){
		this.type= type;
	}
}
