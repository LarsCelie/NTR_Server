package main.java.domain;

import java.util.ArrayList;
import java.util.Date;

public class Survey{

    //Variables
    private int id;
    private String name;
    private Date beginDate;
    private Date endDate;
    private String status;
    private ArrayList<Question> questions = new ArrayList<>();

    // Constructor
    public Survey() {

    }
    // Methods

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

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }


    public String toString() {
        return name + " heeft "  +  questions.size() + " vragen";
    }
}
