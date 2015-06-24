package main.java.dao;

import java.util.List;

import org.hibernate.SQLQuery;

import main.java.domain.Answer;

/**
 * Contains the more complex database calls for the Answer object.
 * @see GenericDaoImpl
 * 
 * @author Milamber
 * 
 */
public class AnswerDao extends GenericDaoImpl<Answer> {
	
	/**
	 * Creates a List of object arrays containing the answers to a survey in a readable format
	 * 
	 * @param questionIds used to define the columns of the query
	 * @return a list of object arrays which represent the rows and fields that the query returns
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getCSV(List<Integer> questionIds) {
		getSession().beginTransaction();
		//a PIVOT statement is used to get a readable result, this only works on an Oracle database
		String SQL = "select * from (select userid,questionid,answer from ntr_answer) PIVOT(max(answer) for questionid in(";
		//creates a column for each question id the survey contains
		for(int i : questionIds) {
			SQL += i+",";
		}
		SQL = SQL.substring(0, SQL.length()-1)+"))";
		SQLQuery SQLquery = getSession().createSQLQuery(SQL);
		List<Object[]> rows = SQLquery.list();
		return rows;
	}
}
