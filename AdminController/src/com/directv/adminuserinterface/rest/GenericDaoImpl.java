/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.rest;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericDaoImpl.
 */
public class GenericDaoImpl {

	/**
	 * Adds the.
	 *
	 * @param object the object
	 * @return the object
	 */
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
	 * Gets the.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param id the id
	 * @return the t
	 */
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
	 * Gets the list.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param param the param
	 * @param value the value
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> clazz, String param, String value) {

		List<T> listNew = new ArrayList<T>();
		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		String query = "select from " + clazz.getName();
		if (param != null && value != null) {
			query = query + " where " + param + " == '" + value + "'";
		}
		List<T> list = (List<T>) pm.newQuery(query).execute();
		if (list != null && list.size() > 0) {
			listNew.addAll(list);//To prevent Serialization exception at RunTime
		}
		return listNew;
	}

	/**
	 * Gets the not equal list.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param param the param
	 * @param value the value
	 * @return the not equal list
	 */
	@SuppressWarnings("unchecked")
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
	 * Delete code table.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 */
	@SuppressWarnings("unchecked")
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
