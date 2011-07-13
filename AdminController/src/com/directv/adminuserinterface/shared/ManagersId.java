/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

// TODO: Auto-generated Javadoc
/**
 * The Class ManagersId.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ManagersId implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 312440159207568949L;

	/** The Constant SUBORG_PARAM. */
	public static final String SUBORG_PARAM = "subOrganization";

	/** The Constant LOCATION_PARAM. */
	public static final String LOCATION_PARAM = "location";

	/** The Constant DESCRIPTION_PARAM. */
	public static final String DESCRIPTION_PARAM = "description";

	/** The id. */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	/** The description. */
	@Persistent
	private String description;

	/** The location. */
	@Persistent
	private String location;

	/** The sub organization. */
	@Persistent
	private String subOrganization;

	/**
	 * Instantiates a new managers id.
	 */
	public ManagersId() {

	}

	/**
	 * Instantiates a new managers id.
	 *
	 * @param id the id
	 * @param description the description
	 * @param subOrganization the sub organization
	 * @param location the location
	 */
	public ManagersId(Long id, String description, String subOrganization, String location) {
		this.id = id;
		this.description = description;
		this.subOrganization = subOrganization;
		this.location = location;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the sub organization.
	 *
	 * @return the sub organization
	 */
	public String getSubOrganization() {
		return subOrganization;
	}

	/**
	 * Sets the sub organization.
	 *
	 * @param subOrganization the new sub organization
	 */
	public void setSubOrganization(String subOrganization) {
		this.subOrganization = subOrganization;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

}
