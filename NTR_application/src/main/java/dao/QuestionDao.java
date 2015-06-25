package main.java.dao;

import java.util.List;

import org.hibernate.Query;

import main.java.domain.Question;

public class QuestionDao extends GenericDaoImpl<Question>{
	@SuppressWarnings("unchecked")
	public List<Question> getQuestionBySurveyId(int surveyId) {
		getSession().beginTransaction();
		Query query = getSession().createQuery("from Question where survey.id =:id").setParameter("id", surveyId);
		return query.list();
	}
}