/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.rest;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.directv.adminuserinterface.shared.ManagersId;

// TODO: Auto-generated Javadoc
/**
 * The Class CodeTableDaoImpl.
 */
public class CodeTableDaoImpl {

	/**
	 * Gets the max managers id.
	 *
	 * @return the max managers id
	 */
	@SuppressWarnings("unchecked")
	public long getMaxManagersId() {

		try {
			PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
			Query query = pm.newQuery(ManagersId.class);
			query.setOrdering("id desc");//Sorting in desc order based on id
			query.setRange(0, 1);//Getting only the 1st record
			List<ManagersId> resultList = (List<ManagersId>) query.execute();
			if (resultList != null && resultList.size() > 0) {
				return resultList.get(0).getId();//Getting the max id
			}
		} catch (Exception excep) {
			return 0;
		}
		return 0;
	}
}
