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
	public <T> T get(Class<T> clazz, String id) {

		T object = null;
		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		try {
			object = (T) pm.getObjectById(clazz, id);
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
}