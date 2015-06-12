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
		ArrayList<Answer> answers = new ArrayList<Answer>();
		int userID = json.get("userId").getAsInt();
		JsonArray jsonAnswers = json.getAsJsonArray("answers");
		for(JsonElement i : jsonAnswers) {
			JsonObject item = (JsonObject)i;
			Answer answer = new Answer();
			answer.setUserId(userID);
			answer.setQuestionId(item.get("question").getAsInt());
			answer.setAnswer(item.get("answer").getAsString());
			answers.add(answer);
		}
		for(Answer a : answers) {
			AnswerDao.addAnswer(a);
		}
	}
}
