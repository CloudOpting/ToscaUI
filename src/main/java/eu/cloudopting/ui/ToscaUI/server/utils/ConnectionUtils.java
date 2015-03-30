package eu.cloudopting.ui.ToscaUI.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author xeviscc
 *
 */
public class ConnectionUtils {

	/**
	 * Get the authorization string for basic authentication.
	 * @param user
	 * @param pass
	 * @return
	 */
	public static String getAuthString(String user, String pass) {
		String authString = user + ":" + pass;
		String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
		return authStringEnc;
	}

	/**
	 * Get the body content of an URLConnection in a String object.
	 * @param urlConnection
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromInputStream(InputStream is)
			throws IOException {
//		if(urlConnection==null) return "";
//
//		InputStream is = urlConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);

		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuffer sb = new StringBuffer();
		while ((numCharsRead = isr.read(charArray)) > 0) {
			sb.append(charArray, 0, numCharsRead);
		}
		
		if(LogsUtil.DEBUG_ENABLED) {
			System.out.println(sb.toString());
		}
		
		return sb.toString();
	}

	/**
	 * Utility to print all Header fields from a URLConnection
	 * @param urlConnection
	 */
	public static void printHeaderFields(URLConnection urlConnection) {
		if(urlConnection==null) return;

		for(String key : urlConnection.getHeaderFields().keySet()){
			System.out.println(key + ": " + urlConnection.getHeaderField(key));
		}
	}
	


	//

	/**
	 * Method to set the payload to the given URLConnection
	 * @param payload
	 * @param urlConnection
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static void setPayload(URLConnection urlConnection, String payload)
			throws UnsupportedEncodingException, IOException {
		urlConnection.setDoOutput(true);
		urlConnection.setRequestProperty("Accept", "application/json");
		urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

		OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
		writer.write(payload);
		writer.close();
	}
	
}
