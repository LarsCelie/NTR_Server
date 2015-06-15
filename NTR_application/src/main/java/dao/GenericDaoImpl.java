package main.java.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * 
 * @author Milamber
 *
 * @param <T> the type that the extendsion of this class handles
 * 
 * offers the basic implementation of most basic DAO functions. 
 * later classes can extend this one and where needed add more functionality or override the standard
 * extending classes are needed for every type the user wishes to write to the database because an implementation of the variable T is required
 * 
 */
@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
	
	private SessionFactory sessionFactory;
	
	/**
	 * convenience method so that not every function needs to write out the procedures to get a session object
	 * 
	 * @return return a session object created with the SessionFactory object that is created on initialization of the class
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	/**
	 * convenience method so that not every function needs to write out the procedures to get a session object
	 * 
	 * @param sessionFactory stores the SessionFactory object that is created on class initialization
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	private Class<T> type;
	protected Class<T> getType() {
		return this.type;
	}
	protected String getClassName() {
		return type.getName();
	}
	
	/**
	 * Constructor initializes the SessionFactory and sets the type variable.
	 * In expanding classes the user should not override this constructor because basic functions will no longer work.
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class)pt.getActualTypeArguments()[0];
		Configuration config = new Configuration().configure();
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		setSessionFactory(config.configure().buildSessionFactory(serviceRegistry));		
	}
	
	/**
	 * deletes the given object from the database
	 * 
	 * @param obj = the object that is to be deleted from the database
	 */
	@Override
	public void delete(T obj) {
		Transaction transaction = getSession().beginTransaction();
		getSession().delete(obj);
		transaction.commit();
	}
	
	/**
	 * returns an instance of the object with given primary key from the database
	 * 
	 * @param i = the primary key Id of the object user wishes to read
	 * @return = an instance of the object the user searched for
	 */
	@Override
	public T read(Integer i) {
		Transaction transaction = getSession().beginTransaction();
		T result = (T)getSession().get(type, i);
		transaction.commit();
		return result;
	}
	
	/**
	 * updates the given object in the database
	 * 
	 * @param obj the object that is to be updated in the database
	 */
	@Override
	public void update(T obj) {
		Transaction transaction = getSession().beginTransaction();
		getSession().update(obj);
		transaction.commit();
	}
	
	/**
	 * returns a list with all the instances of an object found in the database
	 * 
	 * @return = a list of all the objects found in the database
	 */
	@Override
	public List<T> readAll() {
		Transaction transaction = getSession().beginTransaction();
		List<T> result = getSession().createCriteria(type).list();
		transaction.commit();
		return result;
	}
	
	/**
	 * adds the given object to the database
	 * 
	 * @param obj = the object the user wishes to add to the database
	 */
	@Override
	public void create(T obj) {
		Transaction transaction = getSession().beginTransaction();
		getSession().save(obj);
		transaction.commit();
	}	
}
