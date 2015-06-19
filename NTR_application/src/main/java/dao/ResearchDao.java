package main.java.dao;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;

import main.java.domain.Research;

public class ResearchDao extends GenericDaoImpl<Research>{

	@SuppressWarnings("unchecked")
	public List<Research> getAllAvailableResearches() {
		getSession().beginTransaction();
		Query query = getSession().createQuery("from Research where :date between beginDate and endDate").setParameter("date", new Date(Calendar.getInstance().getTimeInMillis()));
		return query.list();
	}

	public Research load(int researchid) {
		getSession().beginTransaction();
		return (Research) getSession().load(Research.class, researchid);
	}
}
