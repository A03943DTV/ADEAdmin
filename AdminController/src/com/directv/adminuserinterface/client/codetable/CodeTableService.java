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
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface CodeTableService.
 */
@RemoteServiceRelativePath("codetable")
public interface CodeTableService extends RemoteService {

	/**
	 * Adds the location.
	 *
	 * @param location the location
	 * @return the location
	 */
	Location addLocation(Location location);

	/**
	 * Adds the group.
	 *
	 * @param group the group
	 * @return the group
	 */
	Group addGroup(Group group);

	/**
	 * Adds the managers id.
	 *
	 * @param managersId the managers id
	 * @return the managers id
	 */
	ManagersId addManagersId(ManagersId managersId);

	/**
	 * Gets the managers ids list.
	 *
	 * @param location the location
	 * @return the managers ids list
	 */
	List<ManagersId> getManagersIdsList(String location);

	/**
	 * Adds the role.
	 *
	 * @param role the role
	 * @return the role
	 */
	Role addRole(Role role);

	/**
	 * Gets the roles list.
	 *
	 * @return the roles list
	 */
	List<Role> getRolesList();

	/**
	 * Gets the groups list.
	 *
	 * @return the groups list
	 */
	List<Group> getGroupsList();

	/**
	 * Gets the locations list.
	 *
	 * @param subOrganization the sub organization
	 * @return the locations list
	 */
	List<Location> getLocationsList(String subOrganization);

	/**
	 * Delete code table values.
	 */
	void deleteCodeTableValues();

	/**
	 * Adds the sub organization.
	 *
	 * @param subOrganization the sub organization
	 * @return the sub organization
	 */
	SubOrganization addSubOrganization(SubOrganization subOrganization);

	/**
	 * Gets the sub organizations list.
	 *
	 * @return the sub organizations list
	 */
	List<SubOrganization> getSubOrganizationsList();
}
