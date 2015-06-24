package main.java.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.dao.AnswerDao;
import main.java.dao.SurveyDao;
import main.java.domain.Answer;
import main.java.domain.Question;
import main.java.domain.User;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Converts the raw data receiver from the corresponding DAO class to the format that is required by the rest service
 * 
 * @author Milamber
 *
 */
public class AnswerController {	
	/**
	 * Converts the JsonArray with question objects back to a List of questions and posts these to the database
	 * 
	 * @param json an Array of answers in Json format
	 */
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
	/**
	 * Creates a readable CSV file for all the answers to a survey
	 * 
	 * @param surveyId the id of the survey for which the consumer wants the results
	 * @return a CSV file containing the answers of requested survey
	 */
	public File getCSV(int surveyId) {
		final String FILE_PATH = "c:/NTR/CSV/";
		final String COMMA_DELIMITER = ";";
		final String NEW_LINE_SEPARATOR = "\n";	
		//List of question id's that the selected survey has, needed to create the columns of CSV
		List<Integer> questionIds = new SurveyDao().getQuestionIds(surveyId);
		List<Object[]> rows = new AnswerDao().getCSV(questionIds);		
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(FILE_PATH+surveyId+"_answers.csv");
			//creates the first line with column titles
			String FILE_HEADER = "userId";			
			for(int i : questionIds) {
				FILE_HEADER += COMMA_DELIMITER+i;
			}
			fileWriter.append(FILE_HEADER+NEW_LINE_SEPARATOR);
			//Iterates over the rows for the CSV
			for(Object[] row : rows) {
				String rowString = "";
				//Iterates over the items of each row and writes them to the CSV
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
