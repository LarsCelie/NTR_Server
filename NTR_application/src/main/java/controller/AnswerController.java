package main.java.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.dao.AnswerDao;
import main.java.dao.QuestionDao;
import main.java.dao.SurveyDao;
import main.java.domain.Answer;
import main.java.domain.Question;
import main.java.domain.User;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AnswerController {	
	public void postAnswers(JsonObject json) {
		System.out.println(json.toString());
		ArrayList<Answer> answers = new ArrayList<Answer>();
		User user = new Gson().fromJson(json.get("user"), User.class);
		JsonArray jsonAnswers = json.getAsJsonArray("answers");
		for(JsonElement i : jsonAnswers) {
			JsonObject item = (JsonObject)i;
			Answer answer = new Answer();
			answer.setUser(user);
			answer.setQuestion(new Gson().fromJson(item.get("question"), Question.class));
			answer.setAnswer(item.get("answer").getAsString());
			answers.add(answer);
		}
		for(Answer a : answers) {
			AnswerDao dao = new AnswerDao();
			dao.create(a);
		}
	}
	public List<Answer> getAnswers(int surveyId) {
		QuestionDao daoQuestion = new QuestionDao();
		AnswerDao daoAnswer = new AnswerDao();
		List<Answer> answers = new ArrayList<Answer>();
		for(Question q : daoQuestion.getQuestionBySurveyId(surveyId)) {
			for(Answer a : daoAnswer.getAnswersByQuestionId(q.getId())) {
				answers.add(a);
			}
		}
		return answers;
	}
	public File getCSV(int surveyId) {
		final String FILE_PATH = "c:/NTR/CSV/";
		final String COMMA_DELIMITER = ";";
		final String NEW_LINE_SEPARATOR = "\n";		
		List<Integer> questionIds = new SurveyDao().getQuestionIds(surveyId);
		List<Object[]> rows = new AnswerDao().getCSV(questionIds);		
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(FILE_PATH+surveyId+"_answers.csv");
			String FILE_HEADER = "userId";			
			for(int i : questionIds) {
				FILE_HEADER += COMMA_DELIMITER+i;
			}
			fileWriter.append(FILE_HEADER+NEW_LINE_SEPARATOR);
			for(Object[] row : rows) {
				String rowString = "";
				for(Object item : row) {
					rowString += item+COMMA_DELIMITER;
				}
				rowString = rowString.substring(0, rowString.length()-1);
				fileWriter.append(rowString+NEW_LINE_SEPARATOR);
			}
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new File(FILE_PATH+surveyId+"_answers.csv");
	}	
}
