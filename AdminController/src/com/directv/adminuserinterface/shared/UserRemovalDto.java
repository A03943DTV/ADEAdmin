/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class UserRemovalDto.
 */
public class UserRemovalDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1104342444127211404L;

	/** The removed user index. */
	private List<Integer> removedUserIndex = new ArrayList<Integer>();

	/** The error message list. */
	private List<String> errorMessageList = new ArrayList<String>();

	/**
	 * Gets the removed user index.
	 *
	 * @return the removed user index
	 */
	public List<Integer> getRemovedUserIndex() {
		return removedUserIndex;
	}

	/**
	 * Sets the removed user index.
	 *
	 * @param removedUserIndex the new removed user index
	 */
	public void setRemovedUserIndex(List<Integer> removedUserIndex) {
		this.removedUserIndex = removedUserIndex;
	}

	/**
	 * Gets the error message list.
	 *
	 * @return the error message list
	 */
	public List<String> getErrorMessageList() {
		return errorMessageList;
	}

	/**
	 * Sets the error message list.
	 *
	 * @param errorMessageList the new error message list
	 */
	public void setErrorMessageList(List<String> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}

}
