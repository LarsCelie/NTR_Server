package main.java.controller;

import java.util.ArrayList;
import java.util.List;

import main.java.dao.AnswerDao;
import main.java.dao.QuestionDao;
import main.java.domain.Answer;
import main.java.domain.Question;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AnswerController {	
	public void postAnswers(JsonObject json) {
		System.out.println(json.toString());
		ArrayList<Answer> answers = new ArrayList<Answer>();
		String userID = json.get("username_fk").getAsString();
		JsonArray jsonAnswers = json.getAsJsonArray("answers");
		for(JsonElement i : jsonAnswers) {
			JsonObject item = (JsonObject)i;
			Answer answer = new Answer();
			answer.setUsername_fk(userID);
			answer.setQuestionId(item.get("question").getAsInt());
			answer.setAnswer(item.get("answer").getAsString());
			answers.add(answer);
		}
		//TODO look into possibility of hibernate creating array of objects
		for(Answer a : answers) {
			AnswerDao dao = new AnswerDao();
			dao.create(a);
		}
	}
	public List<Answer> getAnswers(int surveyId) {
		QuestionDao daoQuestion = new QuestionDao();
		AnswerDao daoAnswer = new AnswerDao();
		List<Answer> answers = null; //TODO check for correct initialization
		for(Question q : daoQuestion.getQuestionBySurveyId(surveyId)) {
			for(Answer a : daoAnswer.getAnswersByQuestionId(q.getId())) {
				answers.add(a);
			}
		}
		return answers;
	}
}
