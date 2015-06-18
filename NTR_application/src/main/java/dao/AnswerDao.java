package main.java.dao;

import java.util.List;

import org.hibernate.SQLQuery;

import main.java.domain.Answer;

public class AnswerDao extends GenericDaoImpl<Answer> {
	public List<Answer> getAnswersByQuestionId(int questionId) {
		//TODO create method that returns all answers that match questionid 
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> getCSV(List<Integer> questionIds) {
		getSession().beginTransaction();
		String SQL = "select * from (select userid,questionid,answer from ntr_answer) PIVOT(max(answer) for questionid in(";
		for(int i : questionIds) {
			SQL += i+",";
		}
		SQL = SQL.substring(0, SQL.length()-1)+"))";
		SQLQuery SQLquery = getSession().createSQLQuery(SQL);
		List<Object[]> rows = SQLquery.list();
		return rows;
	}
}
