package main.java.dao;

import java.sql.Date;
import java.util.Calendar;
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

	@SuppressWarnings("unchecked")
	public List<Survey> getAvailableSurveysByResearch(int id) {
		getSession().beginTransaction();
		Query query = getSession().createQuery("from Survey where :date between beginDate and endDate and research.id = :id").setParameter("date", new Date(Calendar.getInstance().getTimeInMillis())).setParameter("id", id);
		return query.list();
	}
}
