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
 * The Class Location.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Location implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 312440159207568949L;

	/** The Constant DESCRIPTION_PARAM. */
	public static final String DESCRIPTION_PARAM = "description";

	/** The Constant SUB_ORG_PARAM. */
	public static final String SUB_ORG_PARAM = "subOrganization";

	/** The id. */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	/** The description. */
	@Persistent
	private String description;

	/** The sub organization. */
	private String subOrganization;

	/**
	 * Instantiates a new location.
	 */
	public Location() {

	}

	/**
	 * Instantiates a new location.
	 *
	 * @param id the id
	 * @param description the description
	 * @param subOrganization the sub organization
	 */
	public Location(Long id, String description, String subOrganization) {
		this.id = id;
		this.description = description;
		this.subOrganization = subOrganization;
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

}
