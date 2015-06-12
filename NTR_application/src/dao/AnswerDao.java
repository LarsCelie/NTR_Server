package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DBUtil.DBUtil;
import main.java.domain.Answer;

public final class AnswerDao {
	public AnswerDao(){}
	public void addAnswer(Answer answer) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement("INSERT INTO NTR_ANSWER (ID,ANSWER,USERNAME_FK,QUESTIONID) VALUES (NTR_ANSWER_ID.nextval,?,?,?)");
			ps.setString(1, answer.getAnswer());
			ps.setString(2, answer.getUsername_fk());
			ps.setInt(3, answer.getQuestionId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(con);
		}		
	}
}
