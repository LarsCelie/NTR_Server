package main.java.dao;

import java.util.List;

/** 
 * The interface offers a framework with the most basic DAO functions every DAO class should implement.
 * Generics are used where the specific type of that DAO would be.
 * 
 * @author Milamber
 *
 * @param <T> = the type that the implementation of this class handles
 */
public interface GenericDao<T> {
	void create(T t);
	T read(Integer id);
	List<T> readAll();
	void update(T t);
	void delete(T t);
}
