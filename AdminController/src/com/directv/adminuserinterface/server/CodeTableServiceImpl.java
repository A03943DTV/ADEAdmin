/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.server;

import java.util.List;

import com.directv.adminuserinterface.client.codetable.CodeTableService;
import com.directv.adminuserinterface.rest.GenericDaoImpl;
import com.directv.adminuserinterface.shared.Group;
import com.directv.adminuserinterface.shared.Location;
import com.directv.adminuserinterface.shared.ManagersId;
import com.directv.adminuserinterface.shared.Role;
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

		return getGenericDaoImpl().getList(Group.class, null, null);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<Location> getLocationsList() {

		return getGenericDaoImpl().getList(Location.class, null, null);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<ManagersId> getManagersIdsList(String location) {

		return getGenericDaoImpl().getList(ManagersId.class, ManagersId.LOCATION_PARAM, location);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<Role> getRolesList() {

		return getGenericDaoImpl().getList(Role.class, null, null);
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
}
