package test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.RandomUtils;
import org.cruxframework.crux.core.server.rest.spi.InternalServerErrorException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.NotFoundException;

import eu.cloudopting.ui.ToscaUI.client.remote.impl.ProxyAPIService;
import eu.cloudopting.ui.ToscaUI.server.model.Application;
import eu.cloudopting.ui.ToscaUI.server.model.ApplicationList;
import eu.cloudopting.ui.ToscaUI.server.utils.IOUtils;

/**
 * 
 * @author xeviscc
 *
 */
public class ProxyAPITest {
	
	/*
	 * VARIABLES
	 */
	String user = "admin";
	String password = "admin";
	String xmlFile;
	String toscaTemplate;
	int idXmlFile = RandomUtils.nextInt();
	int idFile = RandomUtils.nextInt();
	String applicationName = "Clearo";
	
	@Before
	public void initFiles() throws IOException {
		/*
		 * VARIABLES
		 */
		xmlFile = IOUtils.readFile("src/test/resources/TOSCA_ClearoExample.xml", Charset.defaultCharset());
		toscaTemplate = IOUtils.readFile("src/test/resources/TOSCA_ClearoExample.xml", Charset.defaultCharset());
	}

//	@Test
	public void testCreateApplicationWithExample() throws IOException, JSONException, NotFoundException {
		initFiles();
		
		//CREATE INSTANCE
		ProxyAPIService c = new ProxyAPIService();
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
	
//	@Test
	public void testCreateNewApplicationRawJson() throws MalformedURLException, IOException {
		
	    String createNewApplicationJson = "{\n" +
	            "\t\"statusId\": {\n" +
	            "\t\t\"id\": 1\n" +
	            "\t},\n" +
	            "\t\"userId\": {\n" +
	            "\t\t\"id\": 3\n" +
	            "\t},\n" +
	            "\t\"applicationName\": \"test app xml Base64\",\n" +
	            "\t\"applicationDescription\": \"test description xml\",\n" +
	            "\t\"applicationToscaTemplate\": \"" + Base64.encodeBase64String(xmlFile.getBytes()) +  "\",\n" +
	            "\t\"applicationVersion\": \"1\"\n" +
	            "}";
	    
		//CREATE INSTANCE
		ProxyAPIService c = new ProxyAPIService();
		//CONNECT
		c.connect(user, password);
		//CREATE THE APPLICATION
		String out = c.applicationCreate(createNewApplicationJson);
		
		System.out.println("End test: " + out);

	}
	
//	@Test
	public void testCreateNewCustomizationRawJson() throws MalformedURLException, IOException {

	    String createNewCustomizationJson = "{\n" +
	            "\t\"statusId\": {\n" +
	            "\t\t\"id\": 1\n" +
	            "\t},\n" +
	            "\t\"applicationId\":{\n" +
	            "\t\t\"id\": 181\n" +
	            "\t},\n" +
	            "\t\"customizationToscaFile\": \"" + Base64.encodeBase64String(xmlFile.getBytes()) + "\",\n" +
	            "\t\"customizationCreation\": \"2015-02-15\",\n" +
	            "\t\"customizationActivation\": \"2015-02-15\",\n" +
	            "\t\"customizationDecommission\": \"2015-02-15\",\n" +
	            "\t\"username\": \"1\"\n" +
	            "}";
		
		//CREATE INSTANCE
		ProxyAPIService c = new ProxyAPIService();
		//CONNECT
		c.connect(user, password);
		//CREATE THE APPLICATION
		String out = c.customizationCreate(createNewCustomizationJson);
		
		System.out.println("End test: " + out);

	}
	
//	@Test
	public void testApplicationList() {
		
		//CREATE INSTANCE
		ProxyAPIService c = new ProxyAPIService();

		try {
			//CONNECT
			c.connect(user, password);
			//CREATE THE APPLICATION
			ApplicationList contentList = c.applicationList("1", "3", "applicationName", "asc", "a");
			System.out.println(contentList.getNumberOfElements());
			
		} catch (NotFoundException e) {
			System.out.println(e);
			fail();
		} catch (MalformedURLException e) {
			System.out.println(e);
			fail();
		} catch (InternalServerErrorException e) {
			System.out.println(e);
			fail();
		} catch (IOException e) {
			System.out.println(e);
			fail();
		}
		
	}
	
	@Test
	public void testApplication() {
		//CREATE INSTANCE
		ProxyAPIService c = new ProxyAPIService();

		//CONNECT
		try {
			c.connect(user, password);
			Application application = c.application("1");
			System.out.println(application.getApplicationName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalServerErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
