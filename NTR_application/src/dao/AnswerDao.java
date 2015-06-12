package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DBUtil.DBUtil;
import main.java.domain.Answer;

public final class AnswerDao {
	private AnswerDao(){}
	public static void addAnswer(Answer answer) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement("INSERT INTO NTR_ANSWER (ID,ANSWER,USERID,QUESTIONID) VALUES (NTR_ANSWER_ID.nextval,?,?,?)");
			ps.setString(1, answer.getAnswer());
			ps.setInt(2, answer.getUserId());
			ps.setInt(3, answer.getQuestionId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(con);
		}		
	}
}
