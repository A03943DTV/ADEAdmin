/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.codetable;

import java.util.List;

import com.directv.adminuserinterface.shared.Group;
import com.directv.adminuserinterface.shared.Location;
import com.directv.adminuserinterface.shared.ManagersId;
import com.directv.adminuserinterface.shared.Role;
import com.directv.adminuserinterface.shared.SubOrganization;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface CodeTableServiceAsync.
 */
public interface CodeTableServiceAsync {

	/**
	 * Adds the location.
	 *
	 * @param location the location
	 * @param callback the callback
	 */
	void addLocation(Location location, AsyncCallback<Location> callback);

	/**
	 * Gets the locations list.
	 *
	 * @param subOrganization the sub organization
	 * @param callback the callback
	 * @return the locations list
	 */
	void getLocationsList(String subOrganization, AsyncCallback<List<Location>> callback);

	/**
	 * Adds the group.
	 *
	 * @param group the group
	 * @param callback the callback
	 */
	void addGroup(Group group, AsyncCallback<Group> callback);

	/**
	 * Gets the groups list.
	 *
	 * @param callback the callback
	 * @return the groups list
	 */
	void getGroupsList(AsyncCallback<List<Group>> callback);

	/**
	 * Adds the managers id.
	 *
	 * @param managersId the managers id
	 * @param callback the callback
	 */
	void addManagersId(ManagersId managersId, AsyncCallback<ManagersId> callback);

	/**
	 * Gets the managers ids list.
	 *
	 * @param location the location
	 * @param callback the callback
	 * @return the managers ids list
	 */
	void getManagersIdsList(String location, AsyncCallback<List<ManagersId>> callback);

	/**
	 * Adds the role.
	 *
	 * @param role the role
	 * @param callback the callback
	 */
	void addRole(Role role, AsyncCallback<Role> callback);

	/**
	 * Gets the roles list.
	 *
	 * @param callback the callback
	 * @return the roles list
	 */
	void getRolesList(AsyncCallback<List<Role>> callback);

	/**
	 * Delete code table values.
	 *
	 * @param asyncCallback the async callback
	 */
	void deleteCodeTableValues(AsyncCallback<Void> asyncCallback);

	/**
	 * Adds the sub organization.
	 *
	 * @param subOrganization the sub organization
	 * @param asyncCallback the async callback
	 */
	void addSubOrganization(SubOrganization subOrganization, AsyncCallback<SubOrganization> asyncCallback);

	/**
	 * Gets the sub organizations list.
	 *
	 * @param callback the callback
	 * @return the sub organizations list
	 */
	void getSubOrganizationsList(AsyncCallback<List<SubOrganization>> callback);

}
