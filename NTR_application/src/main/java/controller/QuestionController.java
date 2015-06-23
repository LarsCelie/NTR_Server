package main.java.controller;

import java.util.List;

import org.hibernate.HibernateException;

import main.java.dao.QuestionDao;
import main.java.domain.Question;

public class QuestionController {

	public List<Question> getQuestionsBySurvey(int id) {
		try {
			List<Question> questions = new QuestionDao().getQuestionBySurveyId(id);
			return questions;
		}catch(HibernateException e) {
			return null;
		}
	}
}
