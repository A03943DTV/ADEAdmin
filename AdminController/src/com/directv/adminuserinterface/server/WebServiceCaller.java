/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.AdminException;

// TODO: Auto-generated Javadoc
/**
 * The Class WebServiceCaller.
 */
public class WebServiceCaller {

	/**
	 * Call rest service.
	 *
	 * @param paramUrl the param url
	 * @return the string
	 * @throws AdminException 
	 */
	public static String callRestService(String paramUrl) throws AdminException {

		String jsonData = null;
		try {

			URL url = new URL(paramUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty(AdminConstants.CONTENT_TYPE, AdminConstants.JSON_CONTENT_TYPE);
			urlConnection.setRequestMethod(AdminConstants.GET_METHOD);
			urlConnection.setReadTimeout(90000);
			urlConnection.setDoOutput(false);

			int statusCode = urlConnection.getResponseCode();
			if (statusCode != 200) {
				System.out.println("Invalid HTTP response status code " + statusCode + " from web service server.");
			}
			InputStream in = urlConnection.getInputStream();
			BufferedReader d = new BufferedReader(new InputStreamReader(in));
			jsonData = d.readLine();

		} catch (MalformedURLException e) {
			throw new AdminException("Web service error : " + e.getMessage());
		} catch (ProtocolException e) {
			throw new AdminException("Web service error : " + e.getMessage());
		} catch (IOException e) {
			throw new AdminException("Web service error : " + e.getMessage());
		}
		return jsonData;
	}
}
