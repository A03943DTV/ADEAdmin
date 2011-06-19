/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.rest;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface GenericDao.
 */
public interface GenericDao {

	/**
	 * Adds the.
	 *
	 * @param object the object
	 * @return the object
	 */
	Object add(Object object);

	/**
	 * Delete code table.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 */
	<T> void deleteCodeTable(Class<T> clazz);

	/**
	 * Gets the not equal list.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param param the param
	 * @param value the value
	 * @return the not equal list
	 */
	<T> List<T> getNotEqualList(Class<T> clazz, String param, String value);

	/**
	 * Gets the list.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param param the param
	 * @param value the value
	 * @return the list
	 */
	<T> List<T> getList(Class<T> clazz, String param, String value);

	/**
	 * Gets the.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param id the id
	 * @return the t
	 */
	<T> T get(Class<T> clazz, Object id);

}
