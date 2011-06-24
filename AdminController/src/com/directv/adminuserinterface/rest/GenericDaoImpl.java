/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.rest;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericDaoImpl.
 */
public class GenericDaoImpl implements GenericDao {

	/**
	 * Overridden Method
	 * @param object
	 * @return
	 */
	@Override
	public Object add(Object object) {

		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		try {
			pm.makePersistent(object);
		} catch (Exception excep) {
			throw new RuntimeException(excep);
		} finally {
			pm.close();
		}
		return object;
	}

	/**
	 * Overridden Method
	 * @param <T>
	 * @param clazz
	 * @param id
	 * @return
	 */
	@Override
	public <T> T get(Class<T> clazz, Object id) {

		T object = null;
		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		try {
			if (id instanceof String) {
				object = (T) pm.getObjectById(clazz, id.toString());
			} else if (id instanceof Long) {
				object = (T) pm.getObjectById(clazz, new Long(id.toString()));
			}
		} catch (Exception excep) {
			throw new RuntimeException(excep);
		} finally {
			pm.close();
		}
		return object;
	}

	/**
	 * Overridden Method
	 * @param <T>
	 * @param clazz
	 * @param param
	 * @param value
	 * @return
	 */
	@Override
	public <T> List<T> getList(Class<T> clazz, String param, String value) {

		return getList(clazz, param, value, null, null);
	}

	/**
	 * Overridden Method
	 * @param <T>
	 * @param clazz
	 * @param param1
	 * @param value1
	 * @param param2
	 * @param value2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getList(Class<T> clazz, String param1, String value1, String param2, String value2) {

		List<T> listNew = new ArrayList<T>();
		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		String query = "select from " + clazz.getName();
		if (param1 != null && value1 != null) {
			query = query + " where " + param1 + " == '" + value1 + "'";
		}
		if (param2 != null && value2 != null) {
			query = query + " && " + param2 + " == '" + value2 + "'";
		}
		List<T> list = (List<T>) pm.newQuery(query).execute();
		if (list != null && list.size() > 0) {
			listNew.addAll(list);//To prevent Serialization exception at RunTime
		}
		return listNew;
	}

	/**
	 * Overridden Method
	 * @param <T>
	 * @param clazz
	 * @param param
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getNotEqualList(Class<T> clazz, String param, String value) {

		List<T> listNew = new ArrayList<T>();
		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		String query = "select from " + clazz.getName();
		if (param != null && value != null) {
			query = query + " where " + param + " != '" + value + "'";
		}
		List<T> list = (List<T>) pm.newQuery(query).execute();
		if (list != null && list.size() > 0) {
			listNew.addAll(list);//To prevent Serialization exception at RunTime
		}
		return listNew;
	}

	/**
	 * Overridden Method
	 * @param <T>
	 * @param clazz
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> void deleteCodeTable(Class<T> clazz) {

		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		String query = "select from " + clazz.getName();
		List<T> list = (List<T>) pm.newQuery(query).execute();
		for (T object : list) {
			try {
				pm.currentTransaction().begin();
				pm.deletePersistent(object);
				pm.currentTransaction().commit();
			} catch (Exception ex) {
				pm.currentTransaction().rollback();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * Removes the.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param id the id
	 */
	public <T> void remove(Class<T> clazz, Object id) {

		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		T object = null;
		try {
			pm.currentTransaction().begin();
			if (id instanceof String) {
				object = pm.getObjectById(clazz, id.toString());
			} else if (id instanceof Long) {
				object = pm.getObjectById(clazz, new Long(id.toString()));
			}
			pm.deletePersistent(object);
			pm.currentTransaction().commit();

		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}
	}
}
