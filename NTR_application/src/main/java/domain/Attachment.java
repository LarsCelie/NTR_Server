package main.java.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="NTR_ATTACHMENT")
public class Attachment {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="attachment_id")
	@SequenceGenerator(name="attachment_id",sequenceName="NTR_ATTACHMENT_ID",allocationSize=1)
	private int id;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "LOCATION")
	private String location;
	@ManyToOne
	@JoinColumn(name="QUESTIONID")
	private Question question;
	
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	

}