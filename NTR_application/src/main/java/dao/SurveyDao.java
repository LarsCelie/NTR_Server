package main.java.dao;

import java.util.List;

import org.hibernate.Query;

import main.java.domain.Survey;

public class SurveyDao extends GenericDaoImpl<Survey>{
	@SuppressWarnings("unchecked")
	public List<Integer> getQuestionIds(int surveyId) {
		getSession().beginTransaction();
		Query query = getSession().createQuery("select id from Question where survey.id =:surveyid").setParameter("surveyid", surveyId);
		return query.list();
	}
}
