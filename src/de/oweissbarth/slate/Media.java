package de.oweissbarth.slate;
public class Media{
	private String name;
	private int id;
	private int storage;
	private short type; //0 = Tape, 1 = SD-Card, 2 = Compact Flash

	public Media(String name, int storage, short type, int id){
		this.name = name;
		this.storage = storage;
		this.id = id;
		this.type = type;
	}

	/**************************************************
						Getter
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
}
