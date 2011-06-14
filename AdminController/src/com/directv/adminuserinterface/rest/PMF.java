/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.rest;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PMF.
 */
public class PMF {

	/** The Constant pmfInstance. */
	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	/**
	 * Gets the persistence manager factory.
	 *
	 * @return the persistence manager factory
	 */
	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}
}
