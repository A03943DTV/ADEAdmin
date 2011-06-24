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
 * The Class SubOrganization.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class SubOrganization implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4593057493406171615L;

	/** The Constant DESCRIPTION_PARAM. */
	public static final String DESCRIPTION_PARAM = "description";

	/** The id. */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	/** The description. */
	@Persistent
	private String description;

	/**
	 * Instantiates a new sub organization.
	 */
	public SubOrganization() {

	}

	/**
	 * Instantiates a new sub organization.
	 *
	 * @param id the id
	 * @param description the description
	 */
	public SubOrganization(Long id, String description) {
		this.id = id;
		this.description = description;
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
}
