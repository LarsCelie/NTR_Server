package main.java.dao;

import org.hibernate.Query;

import main.java.domain.User;

public class UserDao extends GenericDaoImpl<User> {
	public User getUserByUsername(String username) {
		getSession().beginTransaction();
		Query query = getSession().createQuery("select u from User as u where u.username=:username").setParameter("username", username);
		User user = (User)query.uniqueResult();
		return user;
	}
}