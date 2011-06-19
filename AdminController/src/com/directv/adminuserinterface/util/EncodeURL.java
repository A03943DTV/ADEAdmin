/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.util;

// TODO: Auto-generated Javadoc
/**
 * The Class EncodeURL.
 */
public class EncodeURL {

	/**
	 * Encode value.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String encodeValue(String value) {

		if (value == null)
			return null;

		if (value.contains("%")) {
			value = value.replaceAll("%", "%25");
		}
		if (value.contains(";")) {
			value = value.replaceAll(";", "%3B");
		}
		if (value.contains("?")) {
			value = value.replaceAll("?", "%3F");
		}
		if (value.contains("/")) {
			value = value.replaceAll("/", "%2F");
		}
		if (value.contains(":")) {
			value = value.replaceAll(":", "	%3A");
		}
		if (value.contains("#")) {
			value = value.replaceAll("#", "%23");
		}
		if (value.contains("&")) {
			value = value.replaceAll("&", "%26");
		}
		if (value.contains("=")) {
			value = value.replaceAll("=", "%3D");
		}
		if (value.contains("+")) {
			value = value.replaceAll("+", "%2B");
		}
		if (value.contains("$")) {
			value = value.replaceAll("$", "%24");
		}
		if (value.contains(",")) {
			value = value.replaceAll(",", "%2C");
		}
		if (value.contains(" ")) {
			value = value.replaceAll(" ", "+");
		}
		if (value.contains("<")) {
			value = value.replaceAll("<", "%3C");
		}
		if (value.contains(">")) {
			value = value.replaceAll(">", "%3E");
		}
		if (value.contains("~")) {
			value = value.replaceAll("~", "	%7E");
		}
		return value;
	}
}
