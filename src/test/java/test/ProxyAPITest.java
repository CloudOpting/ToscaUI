package test;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.json.JSONObject;

import eu.cloudopting.ui.ToscaUI.client.remote.impl.ProxyAPI;
import eu.cloudopting.ui.ToscaUI.utils.IOUtils;

/**
 * 
 * @author xeviscc
 *
 */
public class ProxyAPITest {

	public static void main(String[] args) throws Exception {
		/*
		 * VARIABLES
		 */
		String user = "admin";
		String password = "admin";
		String xmlFile = StringEscapeUtils.escapeJavaScript(
				IOUtils.readFile("C:\\Users\\a591584\\Desktop\\CloudOpting\\TOSCA_ClearoExample.xml", Charset.defaultCharset())
				);
		String toscaTemplate = StringEscapeUtils.escapeJavaScript(
				IOUtils.readFile("C:\\Users\\a591584\\Desktop\\CloudOpting\\TOSCA_ClearoExample.xml", Charset.defaultCharset())
				);
		int idXmlFile = RandomUtils.nextInt();
		int idFile = RandomUtils.nextInt();
		String applicationName = "Clearo";
		
		/*
		 * TESTING CODE
		 */
		//CREATE INSTANCE
		ProxyAPI c = new ProxyAPI();
		//CONNECT
		c.connect(user, password);
		//GET THE USER
		String userJson = c.users(user);

		//CREATE THE JSON TO CREATE AN APPLICAITON
		JSONObject jsonObject = new JSONObject(userJson);
		String json = "{"
				+" \"id\": "+idFile+","
				+" \"applicationName\": \""+applicationName+"\","
				+" \"applicationDescription\": \"\","
				+" \"applicationToscaTemplate\": {"+toscaTemplate+"},"
				+" \"applicationVersion\": \"\","
				+" \"customizations\": {"
				+"  \"id\": "+idXmlFile+","
				+"  \"customizationToscaFile\": {"+xmlFile+"},"
				+"  \"customizationCreation\": \"\","
				+"  \"customizationActivation\": \"\","
				+"  \"customizationDecommission\": \"\","
				+"  \"username\": \""+jsonObject.getString("login")+"\","
				+"  \"applicationId\": \"Applications\","
				+"  \"statusId\": {"
				+"   \"id\": 0,"
				+"   \"status\": \"\","
				+"   \"customizations\": \"Customizations\","
				+"   \"applications\": \"Applications\""
				+"  }"
				+" },"
				+" \"applicationMedia\": {"
				+"  \"id\": 0,"
				+"  \"mediaContent\": ["
				+"  \"\""
				+"  ],"
				+"  \"applicationId\": \"Applications\""
				+" },"
				+" \"statusId\": {"
				+"  \"id\": 0,"
				+"  \"status\": \"\","
				+"  \"customizations\": {"
				+"   \"id\": "+idXmlFile+","
				+"   \"customizationToscaFile\": {"+xmlFile+"},"
				+"   \"customizationCreation\": \"\","
				+"   \"customizationActivation\": \"\","
				+"   \"customizationDecommission\": \"\","
				+"   \"username\": \""+jsonObject.getString("login")+"\","
				+"   \"applicationId\": \"Applications\","
				+"   \"statusId\": \"Status\""
				+"  },"
				+"  \"applications\": \"Applications\""
				+" },"
				+" \"userId\": {"
				+"  \"createdBy\": \""+jsonObject.getString("createdBy")+"\","
				+"  \"createdDate\": \""+jsonObject.getString("createdDate")+"\","
				+"  \"lastModifiedBy\": \""+jsonObject.getString("lastModifiedBy")+"\","
				+"  \"lastModifiedDate\": \""+jsonObject.getString("lastModifiedDate")+"\","
				+"  \"id\": "+jsonObject.getString("id")+","
				+"  \"login\": \""+jsonObject.getString("login")+"\","
				+"  \"firstName\": \""+jsonObject.getString("firstName")+"\","
				+"  \"lastName\": \""+jsonObject.getString("lastName")+"\","
				+"  \"email\": \""+jsonObject.getString("email")+"\","
				+"  \"activated\": "+jsonObject.getString("activated")+","
				+"  \"langKey\": \""+jsonObject.getString("langKey")+"\","
				+"  \"activationKey\": \""+jsonObject.getString("activationKey")+"\""
				+" }"
				+"}";
		
		System.out.println(json);
		
		//CREATE APPLICAITON
		c.applicationCreate(json);
	}
	
}
