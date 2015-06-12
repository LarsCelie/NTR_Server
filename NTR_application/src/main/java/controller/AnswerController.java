package main.java.controller;

import java.util.ArrayList;

import main.java.domain.Answer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dao.AnswerDao;

public class AnswerController {
	public AnswerController(){}
	
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
		for(Answer a : answers) {
			AnswerDao dao = new AnswerDao();
			dao.addAnswer(a);
		}
	}
}
