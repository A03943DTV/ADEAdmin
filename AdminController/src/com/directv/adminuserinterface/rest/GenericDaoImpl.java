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
	 * Gets the list.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> clazz) {

		List<T> listNew = new ArrayList<T>();
		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		String query = "select from " + clazz.getName();
		List<T> list = (List<T>) pm.newQuery(query).execute();
		if (list != null && list.size() > 0) {
			listNew.addAll(list);//To prevent Serialization exception at RunTime
		}
		return listNew;
	}

	/**
	 * Delete code table objects.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 */
	@SuppressWarnings("unchecked")
	public <T> void deleteCodeTableObjects(Class<T> clazz) {

		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		String query = "select from " + clazz.getName();
		for (T data : (List<T>) pm.newQuery(query).execute()) {

			try {
				pm.currentTransaction().begin();
				pm.deletePersistent(data);
				pm.currentTransaction().commit();

			} catch (Exception ex) {
				pm.currentTransaction().rollback();
				throw new RuntimeException(ex);
			}
		}
	}
}
