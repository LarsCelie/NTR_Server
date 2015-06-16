package main.java.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NTR_ANSWER")
public class Answer {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="answer_id")
	@SequenceGenerator(name="answer_id", sequenceName="NTR_ANSWER_ID", allocationSize=1)
	private int id;
	@Column(name="ANSWER")
	private String answer;
	@OneToOne
	@JoinColumn(name = "USERID") //TODO change code and databse so that pk is used for reference
	private User user;
	@ManyToOne
	@JoinColumn(name = "QUESTIONID")
	private Question question;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
}
