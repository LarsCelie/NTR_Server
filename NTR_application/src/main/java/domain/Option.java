package main.java.domain;

public class Option{
    private int id;
    private String content;
    private String value;
    private boolean selected = false;

    public Option() {
    }
    
	public Option(int id, String content, String value, boolean selected) {
		super();
		this.id = id;
		this.content = content;
		this.value = value;
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
    
    
}
