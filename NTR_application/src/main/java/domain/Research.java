package main.java.domain;

import java.util.ArrayList;
import java.util.Date;

public class Research {
	private int id;
	private String name;
	private Date beginDate;
	private Date endDate;
	private String status;
	private ArrayList<Survey> surveys = new ArrayList<>();

	public Research() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(ArrayList<Survey> surveys) {
		this.surveys = surveys;
	}

	public void addSurvey(Survey survey) {
		surveys.add(survey);
	}

	public void removeSurvey(Survey survey) {
		surveys.remove(survey);
	}
}
