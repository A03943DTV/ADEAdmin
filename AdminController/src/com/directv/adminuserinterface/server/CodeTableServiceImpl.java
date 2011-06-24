/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server;

import java.util.List;

import com.directv.adminuserinterface.client.codetable.CodeTableService;
import com.directv.adminuserinterface.rest.GenericDaoImpl;
import com.directv.adminuserinterface.shared.Group;
import com.directv.adminuserinterface.shared.Location;
import com.directv.adminuserinterface.shared.ManagersId;
import com.directv.adminuserinterface.shared.Role;
import com.directv.adminuserinterface.shared.SubOrganization;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class CodeTableServiceImpl.
 */
public class CodeTableServiceImpl extends RemoteServiceServlet implements CodeTableService {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6153433142764180838L;

	/**
	 * Gets the generic dao impl.
	 *
	 * @return the generic dao impl
	 */
	public GenericDaoImpl getGenericDaoImpl() {

		return new GenericDaoImpl();
	}

	/**
	 * Overridden Method
	 * @param group
	 * @return
	 */
	@Override
	public Group addGroup(Group group) {

		return (Group) getGenericDaoImpl().add(group);
	}

	/**
	 * Overridden Method
	 * @param location
	 * @return
	 */
	@Override
	public Location addLocation(Location location) {

		return (Location) getGenericDaoImpl().add(location);
	}

	/**
	 * Overridden Method
	 * @param managersId
	 * @return
	 */
	@Override
	public ManagersId addManagersId(ManagersId managersId) {

		return (ManagersId) getGenericDaoImpl().add(managersId);
	}

	/**
	 * Overridden Method
	 * @param role
	 * @return
	 */
	@Override
	public Role addRole(Role role) {

		return (Role) getGenericDaoImpl().add(role);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<Group> getGroupsList() {

		return getGroupsList(null, null);
	}

	/**
	 * Gets the groups list.
	 *
	 * @param param the param
	 * @param value the value
	 * @return the groups list
	 */
	public List<Group> getGroupsList(String param, String value) {

		return getGenericDaoImpl().getList(Group.class, param, value);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<Location> getLocationsList(String subOrganization) {

		return getLocationsList(Location.SUB_ORG_PARAM, subOrganization);
	}

	/**
	 * Gets the locations list.
	 *
	 * @param param the param
	 * @param value the value
	 * @return the locations list
	 */
	public List<Location> getLocationsList(String param, String value) {

		return getGenericDaoImpl().getList(Location.class, param, value);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<ManagersId> getManagersIdsList(String location) {

		return getManagersIdsList(ManagersId.LOCATION_PARAM, location);
	}

	/**
	 * Gets the managers ids list.
	 *
	 * @param param the param
	 * @param value the value
	 * @return the managers ids list
	 */
	public List<ManagersId> getManagersIdsList(String param, String value) {

		return getGenericDaoImpl().getList(ManagersId.class, param, value);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<Role> getRolesList() {

		return getRolesList(null, null);
	}

	/**
	 * Gets the roles list.
	 *
	 * @param param the param
	 * @param value the value
	 * @return the roles list
	 */
	public List<Role> getRolesList(String param, String value) {

		return getGenericDaoImpl().getList(Role.class, param, value);
	}

	/**
	 * Overridden Method
	 */
	@Override
	public void deleteCodeTableValues() {

		getGenericDaoImpl().deleteCodeTable(Group.class);
		getGenericDaoImpl().deleteCodeTable(Location.class);
		getGenericDaoImpl().deleteCodeTable(Role.class);
		getGenericDaoImpl().deleteCodeTable(ManagersId.class);
	}

	/**
	 * Delete managers id data.
	 *
	 * @param managersId the managers id
	 */
	public void deleteManagersIdData(String managersId) {

		for (ManagersId managersIdObject : (List<ManagersId>) getGenericDaoImpl().getList(ManagersId.class, ManagersId.DESCRIPTION_PARAM, managersId)) {

			getGenericDaoImpl().remove(ManagersId.class, managersIdObject.getId());
		}
	}

	/**
	 * Overridden Method
	 * @param subOrganization
	 * @return
	 */
	@Override
	public SubOrganization addSubOrganization(SubOrganization subOrganization) {

		return (SubOrganization) getGenericDaoImpl().add(subOrganization);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<SubOrganization> getSubOrganizationsList() {
		return getSubOrganizationsList(null, null);
	}

	/**
	 * Gets the sub organizations list.
	 *
	 * @param param the param
	 * @param value the value
	 * @return the sub organizations list
	 */
	public List<SubOrganization> getSubOrganizationsList(String param, String value) {

		return getGenericDaoImpl().getList(SubOrganization.class, param, value);
	}

}
