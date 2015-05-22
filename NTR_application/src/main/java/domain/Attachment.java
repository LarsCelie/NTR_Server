package main.java.domain;

public class Attachment {
	private int id;
	private String type;
	private String location;

	public Attachment() {

	}

	public int getID() {
		return id;
	}

	public void setID(int ID) {
		this.id = ID;
	}

	public String getTYPE() {
		return type;
	}

	public void setTYPE(String TYPE) {
		this.type = TYPE;
	}

	public String getLOCATION() {
		return location;
	}

	public void setLOCATION(String LOCATION) {
		this.location = LOCATION;
	}

}