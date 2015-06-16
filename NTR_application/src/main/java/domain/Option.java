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
@Table(name = "NTR_OPTION")
public class Option{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="option_id")
	@SequenceGenerator(name="option_id",sequenceName="NTR_OPTION_ID",allocationSize=1)
    private int id;
	@Column(name = "VALUE")
    private String value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}    
}
