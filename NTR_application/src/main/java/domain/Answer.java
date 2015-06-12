package main.java.domain;

public class Answer {
	
	private int id;
	private String answer;
	private User user;
	private Question question;
	private int userId, questionId;
	private String username_fk;
	
	public Answer() {
		
	}
	
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

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername_fk() {
		return username_fk;
	}

	public void setUsername_fk(String username_fk) {
		this.username_fk = username_fk;
	}
}
