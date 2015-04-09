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
//		c.applicationCreate(json);
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
//		String out = c.applicationCreate("name", "description", "fileName");
		//String out = c.applicationCreate("test app xml Base64", "test description xml", "fileName", Base64.encodeBase64String(xmlFile.getBytes()));
		
//		System.out.println("End test: " + out);

	}
	
//	@Test
	public void testCreateNewCustomizationRawJson() throws MalformedURLException, IOException {
		//CREATE INSTANCE
		ProxyAPIService c = new ProxyAPIService();
		//CONNECT
		c.connect(user, password);
		//CREATE THE APPLICATION
		String out = c.customizationCreate("181", Base64.encodeBase64String(xmlFile.getBytes()));
		
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
	
	public static void main(String[] args) throws Exception {
//		String base = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCjxEZWZpbml0aW9ucyBpZD0iQ2xlYXJvIiBuYW1lPSIiIHRhcmdldE5hbWVzcGFjZT0iaHR0cDovL3RlbXB1cmkub3JnIg0KCXhtbG5zPSJodHRwOi8vZG9jcy5vYXNpcy1vcGVuLm9yZy90b3NjYS9ucy8yMDExLzEyIiB4bWxuczp4bWw9Imh0dHA6Ly93d3cudzMub3JnL1hNTC8xOTk4L25hbWVzcGFjZSINCgl4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIg0KCXhtbG5zOmNvPSJodHRwOi8vZG9jcy5vYXNpcy1vcGVuLm9yZy90b3NjYS9ucy8yMDExLzEyL0Nsb3VkT3B0aW5nVHlwZXMiDQoJeHNpOnNjaGVtYUxvY2F0aW9uPSJodHRwOi8vZG9jcy5vYXNpcy1vcGVuLm9yZy90b3NjYS9ucy8yMDExLzEyIFRPU0NBLXYxLjAueHNkIA0KCWh0dHA6Ly9kb2NzLm9hc2lzLW9wZW4ub3JnL3Rvc2NhL25zLzIwMTEvMTIvQ2xvdWRPcHRpbmdUeXBlcyAuL1R5cGVzL0Nsb3VkT3B0aW5nVHlwZXMueHNkIj4NCgk8Tm9kZVR5cGUgbmFtZT0iVk1ob3N0Ij4NCgkJPGRvY3VtZW50YXRpb24+VGhpcyBpcyB0aGUgVk0gZGVzY3JpcHRpb24sIHdlIG5lZWQgdG8gY29sbGVjdCB0aGUgU0xBDQoJCQlpbmZvcm1hdGlvbiAodGhhdCBpcyB0aGUgc2V0IG9mIENQVStSQU0rRElTSykgdGhhdCB0aGUgVk0gbmVlZCB0bw0KCQkJaGF2ZSBmb3IgdGhlIHNlcnZpY2UgKHRoaXMgaW5mb3JtYXRpb24gaXMganVzdCBhIGxhYmVsIGZvciB0aGUgZW5kDQoJCQl1c2VyIGJ1dCB0cmFuc2xhdGUgdG8gZGF0YSBmb3IgdGhlIHN5c3RlbSkNCgkJPC9kb2N1bWVudGF0aW9uPg0KCQk8UHJvcGVydGllc0RlZmluaXRpb24gZWxlbWVudD0iY286Vk1ob3N0UHJvcGVydGllcyINCgkJCXR5cGU9ImNvOnRWTWhvc3RQcm9wZXJ0aWVzIiAvPg0KCQk8SW50ZXJmYWNlcz4NCgkJCTxJbnRlcmZhY2UgbmFtZT0iaHR0cDovL3RlbXB1cmkub3JnIj4NCgkJCQk8T3BlcmF0aW9uIG5hbWU9Ikluc3RhbGwiPg0KCQkJCQk8ZG9jdW1lbnRhdGlvbj5UaGUgcGFyYW1ldGVycyB0byBhc2sgdG8gdGhlIGVuZCB1c2VyIHRvIGV4ZWN1dGUgdGhlDQoJCQkJCQkiaW5zdGFsbCIgb3BlcmF0aW9uIG9mIHRoaXMgbm9kZQ0KCQkJCQk8L2RvY3VtZW50YXRpb24+DQoJCQkJCTxJbnB1dFBhcmFtZXRlcnM+DQoJCQkJCQk8SW5wdXRQYXJhbWV0ZXIgbmFtZT0iY286U0xBLmNvOkNob3NlbiIgdHlwZT0iY286U0xBIiAvPg0KCQkJCQk8L0lucHV0UGFyYW1ldGVycz4NCgkJCQk8L09wZXJhdGlvbj4NCgkJCTwvSW50ZXJmYWNlPg0KCQk8L0ludGVyZmFjZXM+DQoJPC9Ob2RlVHlwZT4NCgk8Tm9kZVR5cGUgbmFtZT0iRG9ja2VyQ29udGFpbmVyIj4NCgkJPGRvY3VtZW50YXRpb24+VGhpcyBpcyB0aGUgRG9ja2VyIENvbnRhaW5lciAodGhlIERvY2tlciBob3N0IGlzDQoJCQlhbHJlYWR5IGluc3RhbGxlZCBpbiB0aGUgVk0gaW1hZ2UpDQoJCTwvZG9jdW1lbnRhdGlvbj4NCgkJPFByb3BlcnRpZXNEZWZpbml0aW9uIGVsZW1lbnQ9IiIgdHlwZT0iIiAvPg0KCTwvTm9kZVR5cGU+DQoJPE5vZGVUeXBlIG5hbWU9IkFwYWNoZSI+DQoJCTxkb2N1bWVudGF0aW9uPlRoaXMgaXMgdGhlIEFwYWNoZSBzZXJ2ZXIgKHdlIHNob3VsZCBub3QgYXNrIGFueXRoaW5nDQoJCQl0byB0aGUgZW5kIHVzZXIgYWJvdXQgYXBhY2hlLCBidXQgd2UgbmVlZCB0byBzZXQgdGhlIHByb3BlcnRpZXMpDQoJCTwvZG9jdW1lbnRhdGlvbj4NCgkJPFByb3BlcnRpZXNEZWZpbml0aW9uIGVsZW1lbnQ9IiIgdHlwZT0iIiAvPg0KCTwvTm9kZVR5cGU+DQoJPE5vZGVUeXBlIG5hbWU9IkFwYWNoZVZpcnR1YWxIb3N0Ij4NCgkJPGRvY3VtZW50YXRpb24+VGhpcyBpcyB0aGUgQXBhY2hlIFZpcnR1YWwgSG9zdCBhbmQgaGVyZSB3ZSBoYXZlIHRoaW5ncw0KCQkJdG8gYXNrIHRvIHRoZSB1c2VyDQoJCTwvZG9jdW1lbnRhdGlvbj4NCgkJPFByb3BlcnRpZXNEZWZpbml0aW9uIGVsZW1lbnQ9IiIgdHlwZT0iIiAvPg0KCQk8SW50ZXJmYWNlcz4NCgkJCTxJbnRlcmZhY2UgbmFtZT0iaHR0cDovL3RlbXB1cmkub3JnIj4NCgkJCQk8T3BlcmF0aW9uIG5hbWU9Ikluc3RhbGwiPg0KCQkJCQk8SW5wdXRQYXJhbWV0ZXJzPg0KCQkJCQkJPElucHV0UGFyYW1ldGVyIG5hbWU9IlZIb3N0TmFtZSIgdHlwZT0ieHM6c3RyaW5nIiAvPg0KCQkJCQk8L0lucHV0UGFyYW1ldGVycz4NCgkJCQk8L09wZXJhdGlvbj4NCgkJCTwvSW50ZXJmYWNlPg0KCQk8L0ludGVyZmFjZXM+DQoJPC9Ob2RlVHlwZT4NCgk8Tm9kZVR5cGUgbmFtZT0iTXlTUUwiPg0KCQk8ZG9jdW1lbnRhdGlvbj5UaGlzIGlzIHRoZSBNeVNRTCBlbmdpbmUNCgkJPC9kb2N1bWVudGF0aW9uPg0KCQk8UHJvcGVydGllc0RlZmluaXRpb24gZWxlbWVudD0iIiB0eXBlPSIiIC8+DQoJCTxJbnRlcmZhY2VzPg0KCQkJPEludGVyZmFjZSBuYW1lPSJodHRwOi8vdGVtcHVyaS5vcmciPg0KCQkJCTxPcGVyYXRpb24gbmFtZT0iSW5zdGFsbCI+DQoJCQkJCTxJbnB1dFBhcmFtZXRlcnM+DQoJCQkJCQk8SW5wdXRQYXJhbWV0ZXIgbmFtZT0icm9vdF9wYXNzd29yZCIgdHlwZT0ieHM6c3RyaW5nIiAvPg0KCQkJCQk8L0lucHV0UGFyYW1ldGVycz4NCgkJCQk8L09wZXJhdGlvbj4NCgkJCTwvSW50ZXJmYWNlPg0KCQk8L0ludGVyZmFjZXM+DQoJPC9Ob2RlVHlwZT4NCgk8Tm9kZVR5cGUgbmFtZT0iTXlTUUxEYXRhYmFzZSI+DQoJCTxkb2N1bWVudGF0aW9uPlRoaXMgaXMgdGhlIE15U1FMIGVuZ2luZQ0KCQk8L2RvY3VtZW50YXRpb24+DQoJCTxQcm9wZXJ0aWVzRGVmaW5pdGlvbiBlbGVtZW50PSIiIHR5cGU9IiIgLz4NCgkJPEludGVyZmFjZXM+DQoJCQk8SW50ZXJmYWNlIG5hbWU9Imh0dHA6Ly90ZW1wdXJpLm9yZyI+DQoJCQkJPE9wZXJhdGlvbiBuYW1lPSJJbnN0YWxsIj4NCgkJCQkJPElucHV0UGFyYW1ldGVycz4NCgkJCQkJCTxJbnB1dFBhcmFtZXRlciBuYW1lPSJkYlBhc3N3b3JkIiB0eXBlPSJ4czpzdHJpbmciIC8+DQoJCQkJCTwvSW5wdXRQYXJhbWV0ZXJzPg0KCQkJCTwvT3BlcmF0aW9uPg0KCQkJPC9JbnRlcmZhY2U+DQoJCTwvSW50ZXJmYWNlcz4NCgk8L05vZGVUeXBlPg0KCTxTZXJ2aWNlVGVtcGxhdGUgaWQ9IkNsZWFybyIgbmFtZT0iIg0KCQlzdWJzdGl0dXRhYmxlTm9kZVR5cGU9IlFOYW1lIiB0YXJnZXROYW1lc3BhY2U9Imh0dHA6Ly90ZW1wdXJpLm9yZyI+DQoNCg0KCQk8VG9wb2xvZ3lUZW1wbGF0ZT4NCgkJCTxkb2N1bWVudGF0aW9uIHhtbDpsYW5nPSIiIHNvdXJjZT0iaHR0cDovL3RlbXB1cmkub3JnIiAvPg0KCQkJPE5vZGVUZW1wbGF0ZSBpZD0iQ2xlYXJvVk0iIG1heEluc3RhbmNlcz0iMSINCgkJCQltaW5JbnN0YW5jZXM9IjEiIG5hbWU9IiIgdHlwZT0iVk1ob3N0Ij4NCgkJCQk8ZG9jdW1lbnRhdGlvbiB4bWw6bGFuZz0iIiBzb3VyY2U9Imh0dHA6Ly90ZW1wdXJpLm9yZyIgLz4NCgkJCQk8UHJvcGVydGllcz4NCgkJCQkJPGRvY3VtZW50YXRpb24geG1sOmxhbmc9IiIgc291cmNlPSJodHRwOi8vdGVtcHVyaS5vcmciIC8+DQoJCQkJCTxjbzpWTWhvc3RQcm9wZXJ0aWVzPg0KCQkJCQkJPGNvOlNMQXNQcm9wZXJ0aWVzPg0KCQkJCQkJCTxjbzpTTEEgTmFtZT0iQmlnIENpdHkiIGlkPSJCaWdDaXR5Ij4NCgkJCQkJCQkJPGNvOk51bUNwdXM+MjwvY286TnVtQ3B1cz4NCgkJCQkJCQkJPGNvOk1lbW9yeT4yPC9jbzpNZW1vcnk+DQoJCQkJCQkJCTxjbzpQcmljZT4xMDAwMDwvY286UHJpY2U+DQoJCQkJCQkJCTxjbzpEaXNrPjEwPC9jbzpEaXNrPg0KCQkJCQkJCQk8Y286Q2hvc2VuPmZhbHNlPC9jbzpDaG9zZW4+DQoJCQkJCQkJPC9jbzpTTEE+DQoJCQkJCQkJPGNvOlNMQSBOYW1lPSJTbWFsbCBDaXR5IiBpZD0iU21hbGxDaXR5Ij4NCgkJCQkJCQkJPGNvOk51bUNwdXM+MTwvY286TnVtQ3B1cz4NCgkJCQkJCQkJPGNvOk1lbW9yeT4xPC9jbzpNZW1vcnk+DQoJCQkJCQkJCTxjbzpQcmljZT41MDAwPC9jbzpQcmljZT4NCgkJCQkJCQkJPGNvOkRpc2s+NTwvY286RGlzaz4NCgkJCQkJCQkJPGNvOkNob3Nlbj50cnVlPC9jbzpDaG9zZW4+DQoJCQkJCQkJPC9jbzpTTEE+DQoJCQkJCQkJPGNvOlNMQSBOYW1lPSJSZWdpb24iIGlkPSJSZWdpb24iPg0KCQkJCQkJCQk8Y286TnVtQ3B1cz40PC9jbzpOdW1DcHVzPg0KCQkJCQkJCQk8Y286TWVtb3J5PjQ8L2NvOk1lbW9yeT4NCgkJCQkJCQkJPGNvOlByaWNlPjIwMDAwPC9jbzpQcmljZT4NCgkJCQkJCQkJPGNvOkRpc2s+MjA8L2NvOkRpc2s+DQoJCQkJCQkJCTxjbzpDaG9zZW4+ZmFsc2U8L2NvOkNob3Nlbj4NCgkJCQkJCQk8L2NvOlNMQT4NCgkJCQkJCQk8Y286dm1JbWFnZT48L2NvOnZtSW1hZ2U+DQoJCQkJCQk8L2NvOlNMQXNQcm9wZXJ0aWVzPg0KCQkJCQk8L2NvOlZNaG9zdFByb3BlcnRpZXM+DQoJCQkJPC9Qcm9wZXJ0aWVzPg0KCQkJPC9Ob2RlVGVtcGxhdGU+DQoJCQk8Tm9kZVRlbXBsYXRlIHR5cGU9IkRvY2tlckNvbnRhaW5lciIgaWQ9IkNsZWFyb0FwYWNoZURDIj48L05vZGVUZW1wbGF0ZT4NCgkJCTxOb2RlVGVtcGxhdGUgdHlwZT0iQXBhY2hlIiBpZD0iQ2xlYXJvQXBhY2hlIj4NCgkJCQk8UHJvcGVydGllcz4NCgkJCQkJPGRvY3VtZW50YXRpb24+PC9kb2N1bWVudGF0aW9uPg0KCQkJCQk8Y286QXBhY2hlUHJvcGVydGllcz4NCgkJCQkJCTxjbzpkZWZhdWx0X21vZHM+dHJ1ZTwvY286ZGVmYXVsdF9tb2RzPg0KCQkJCQkJPGNvOnNlcnZlcm5hbWU+ZnFkbjwvY286c2VydmVybmFtZT4NCgkJCQkJCTxjbzpsb2dfZm9ybWF0cz57IHZob3N0X2NvbW1vbiA9PiAnJXYgJWggJWwgJXUgJXQgXCIlclwiICU+cyAlYicgfTwvY286bG9nX2Zvcm1hdHM+DQoJCQkJCTwvY286QXBhY2hlUHJvcGVydGllcz4NCgkJCQk8L1Byb3BlcnRpZXM+DQoJCQk8L05vZGVUZW1wbGF0ZT4NCgkJCTxOb2RlVGVtcGxhdGUgdHlwZT0iQXBhY2hlVmlydHVhbEhvc3QiIGlkPSJDbGVhcm9BcGFjaGVWSCI+DQoJCQkJPFByb3BlcnRpZXM+DQoJCQkJCTxjbzpBcGFjaGVWaXJ0dWFsSG9zdFByb3BlcnRpZXM+DQoJCQkJCQk8Y286Vkhvc3ROYW1lPjw/Z2V0SW5wdXQgVkhvc3ROYW1lPz4NCgkJCQkJCTwvY286Vkhvc3ROYW1lPg0KCQkJCQkJPGNvOmFsaWFzZXM+PC9jbzphbGlhc2VzPg0KCQkJCQkJPGNvOmRlZmF1bHRfdmhvc3Q+ZmFsc2U8L2NvOmRlZmF1bHRfdmhvc3Q+DQoJCQkJCQk8Y286ZG9jcm9vdD48L2NvOmRvY3Jvb3Q+DQoJCQkJCQk8Y286ZGlyZWN0b3JpZXM+PC9jbzpkaXJlY3Rvcmllcz4NCgkJCQkJCTxjbzpsb2dfbGV2ZWw+PC9jbzpsb2dfbGV2ZWw+DQoJCQkJCQk8Y286b3B0aW9ucz48L2NvOm9wdGlvbnM+DQoJCQkJCQk8Y286cG9ydD48L2NvOnBvcnQ+DQoJCQkJCQk8Y286cHJveHlfcGFzcz48L2NvOnByb3h5X3Bhc3M+DQoJCQkJCQk8Y286cmVkaXJlY3Rfc291cmNlPjwvY286cmVkaXJlY3Rfc291cmNlPg0KCQkJCQkJPGNvOnJlZGlyZWN0X2Rlc3Q+PC9jbzpyZWRpcmVjdF9kZXN0Pg0KCQkJCQkJPGNvOnJlZGlyZWN0X3N0YXR1cz48L2NvOnJlZGlyZWN0X3N0YXR1cz4NCgkJCQkJCTxjbzpyZXdyaXRlcz48L2NvOnJld3JpdGVzPg0KCQkJCQkJPGNvOnNldGVudj48L2NvOnNldGVudj4NCgkJCQkJCTxjbzpzc2w+ZmFsc2U8L2NvOnNzbD4NCgkJCQkJCTxjbzpzZXJ2ZXJhZG1pbj48L2NvOnNlcnZlcmFkbWluPg0KCQkJCQk8L2NvOkFwYWNoZVZpcnR1YWxIb3N0UHJvcGVydGllcz4NCgkJCQk8L1Byb3BlcnRpZXM+DQoJCQk8L05vZGVUZW1wbGF0ZT4NCgkJCTxOb2RlVGVtcGxhdGUgdHlwZT0iQXBhY2hlVmlydHVhbEhvc3QiIGlkPSJDbGVhcm9BcGFjaGVWSDIiPg0KCQkJCTxQcm9wZXJ0aWVzPg0KCQkJCQk8Y286QXBhY2hlVmlydHVhbEhvc3RQcm9wZXJ0aWVzPg0KCQkJCQkJPGNvOlZIb3N0TmFtZT48P2dldElucHV0IFZIb3N0TmFtZT8+DQoJCQkJCQk8L2NvOlZIb3N0TmFtZT4NCgkJCQkJCTxjbzphbGlhc2VzPjwvY286YWxpYXNlcz4NCgkJCQkJCTxjbzpkZWZhdWx0X3Zob3N0PmZhbHNlPC9jbzpkZWZhdWx0X3Zob3N0Pg0KCQkJCQkJPGNvOmRvY3Jvb3Q+PC9jbzpkb2Nyb290Pg0KCQkJCQkJPGNvOmRpcmVjdG9yaWVzPjwvY286ZGlyZWN0b3JpZXM+DQoJCQkJCQk8Y286bG9nX2xldmVsPjwvY286bG9nX2xldmVsPg0KCQkJCQkJPGNvOm9wdGlvbnM+PC9jbzpvcHRpb25zPg0KCQkJCQkJPGNvOnBvcnQ+PC9jbzpwb3J0Pg0KCQkJCQkJPGNvOnByb3h5X3Bhc3M+PC9jbzpwcm94eV9wYXNzPg0KCQkJCQkJPGNvOnJlZGlyZWN0X3NvdXJjZT48L2NvOnJlZGlyZWN0X3NvdXJjZT4NCgkJCQkJCTxjbzpyZWRpcmVjdF9kZXN0PjwvY286cmVkaXJlY3RfZGVzdD4NCgkJCQkJCTxjbzpyZWRpcmVjdF9zdGF0dXM+PC9jbzpyZWRpcmVjdF9zdGF0dXM+DQoJCQkJCQk8Y286cmV3cml0ZXM+PC9jbzpyZXdyaXRlcz4NCgkJCQkJCTxjbzpzZXRlbnY+PC9jbzpzZXRlbnY+DQoJCQkJCQk8Y286c3NsPmZhbHNlPC9jbzpzc2w+DQoJCQkJCQk8Y286c2VydmVyYWRtaW4+PC9jbzpzZXJ2ZXJhZG1pbj4NCgkJCQkJPC9jbzpBcGFjaGVWaXJ0dWFsSG9zdFByb3BlcnRpZXM+DQoJCQkJPC9Qcm9wZXJ0aWVzPg0KCQkJPC9Ob2RlVGVtcGxhdGU+DQoJCQk8Tm9kZVRlbXBsYXRlIHR5cGU9IkRvY2tlckNvbnRhaW5lciIgaWQ9IkNsZWFyb015U1FMREMiPg0KCQkJCTxQcm9wZXJ0aWVzPg0KCQkJCQk8ZG9jdW1lbnRhdGlvbj48L2RvY3VtZW50YXRpb24+DQoJCQkJCTxjbzpEb2NrZXJDb250YWluZXJQcm9wZXJ0aWVzPg0KCQkJCQkJPGNvOmZyb20+PC9jbzpmcm9tPg0KCQkJCQkJPGNvOmVudHJ5cG9pbnQ+PC9jbzplbnRyeXBvaW50Pg0KCQkJCQkJPGNvOmNtZD48L2NvOmNtZD4NCgkJCQkJPC9jbzpEb2NrZXJDb250YWluZXJQcm9wZXJ0aWVzPg0KCQkJCTwvUHJvcGVydGllcz4NCgkJCTwvTm9kZVRlbXBsYXRlPg0KCQkJPE5vZGVUZW1wbGF0ZSB0eXBlPSJNeVNRTCIgaWQ9IkNsZWFyb015U1FMIj4NCgkJCQk8UHJvcGVydGllcz4NCgkJCQkJPGRvY3VtZW50YXRpb24+PC9kb2N1bWVudGF0aW9uPg0KCQkJCQk8Y286TXlTUUxQcm9wZXJ0aWVzPg0KCQkJCQkJPGNvOnJvb3RfcGFzc3dvcmQ+PD9nZXRJbnB1dCByb290X3Bhc3N3b3JkPz4NCgkJCQkJCTwvY286cm9vdF9wYXNzd29yZD4NCgkJCQkJCTxjbzpyZW1vdmVfZGVmYXVsdF9hY2NvdW50cz48L2NvOnJlbW92ZV9kZWZhdWx0X2FjY291bnRzPg0KCQkJCQkJPGNvOm92ZXJyaWRlX29wdGlvbnM+PC9jbzpvdmVycmlkZV9vcHRpb25zPg0KCQkJCQk8L2NvOk15U1FMUHJvcGVydGllcz4NCgkJCQk8L1Byb3BlcnRpZXM+DQoJCQk8L05vZGVUZW1wbGF0ZT4NCgkJCTxOb2RlVGVtcGxhdGUgdHlwZT0iTXlTUUxEYXRhYmFzZSIgaWQ9IkNsZWFyb015U1FMREIiPg0KCQkJCTxQcm9wZXJ0aWVzPg0KCQkJCQk8ZG9jdW1lbnRhdGlvbj48L2RvY3VtZW50YXRpb24+DQoJCQkJCTxjbzpNeVNRTERhdGFiYXNlUHJvcGVydGllcz4NCgkJCQkJCTxjbzpkYk5hbWU+PC9jbzpkYk5hbWU+DQoJCQkJCQk8Y286ZGJVc2VyPjwvY286ZGJVc2VyPg0KCQkJCQkJPGNvOmRiUGFzc3dvcmQ+PD9nZXRJbnB1dCBkYlBhc3N3b3JkPz4NCgkJCQkJCTwvY286ZGJQYXNzd29yZD4NCgkJCQkJCTxjbzpjb25uZWN0aW9uSG9zdD48L2NvOmNvbm5lY3Rpb25Ib3N0Pg0KCQkJCQkJPGNvOmdyYW50PjwvY286Z3JhbnQ+DQoJCQkJCQk8Y286c3FsPjwvY286c3FsPg0KCQkJCQkJPGNvOmltcG9ydF90aW1lb3V0PjwvY286aW1wb3J0X3RpbWVvdXQ+DQoJCQkJCQk8Y286Y2hhcnNldD48L2NvOmNoYXJzZXQ+DQoJCQkJCQk8Y286Y29sbGF0ZT48L2NvOmNvbGxhdGU+DQoJCQkJCTwvY286TXlTUUxEYXRhYmFzZVByb3BlcnRpZXM+DQoJCQkJPC9Qcm9wZXJ0aWVzPg0KCQkJPC9Ob2RlVGVtcGxhdGU+DQoJCQk8UmVsYXRpb25zaGlwVGVtcGxhdGUgdHlwZT0iUU5hbWUiIGlkPSJpZHZhbHVlMCI+DQoJCQkJPFNvdXJjZUVsZW1lbnQgcmVmPSJDbGVhcm9WTSIgLz4NCgkJCQk8VGFyZ2V0RWxlbWVudCByZWY9IkNsZWFyb0FwYWNoZURDIiAvPg0KCQkJPC9SZWxhdGlvbnNoaXBUZW1wbGF0ZT4NCgkJCTxSZWxhdGlvbnNoaXBUZW1wbGF0ZSB0eXBlPSJRTmFtZSIgaWQ9ImlkdmFsdWUwIj4NCgkJCQk8U291cmNlRWxlbWVudCByZWY9IkNsZWFyb0FwYWNoZURDIiAvPg0KCQkJCTxUYXJnZXRFbGVtZW50IHJlZj0iQ2xlYXJvQXBhY2hlIiAvPg0KCQkJPC9SZWxhdGlvbnNoaXBUZW1wbGF0ZT4NCgkJCTxSZWxhdGlvbnNoaXBUZW1wbGF0ZSB0eXBlPSJRTmFtZSIgaWQ9ImlkdmFsdWUwIj4NCgkJCQk8U291cmNlRWxlbWVudCByZWY9IkNsZWFyb0FwYWNoZSIgLz4NCgkJCQk8VGFyZ2V0RWxlbWVudCByZWY9IkNsZWFyb0FwYWNoZVZIIiAvPg0KCQkJPC9SZWxhdGlvbnNoaXBUZW1wbGF0ZT4NCgkJCTxSZWxhdGlvbnNoaXBUZW1wbGF0ZSB0eXBlPSJRTmFtZSIgaWQ9ImlkdmFsdWUwIj4NCgkJCQk8U291cmNlRWxlbWVudCByZWY9IkNsZWFyb1ZNIiAvPg0KCQkJCTxUYXJnZXRFbGVtZW50IHJlZj0iQ2xlYXJvTXlTUUxEQyIgLz4NCgkJCTwvUmVsYXRpb25zaGlwVGVtcGxhdGU+DQoJCQk8UmVsYXRpb25zaGlwVGVtcGxhdGUgdHlwZT0iUU5hbWUiIGlkPSJpZHZhbHVlMCI+DQoJCQkJPFNvdXJjZUVsZW1lbnQgcmVmPSJDbGVhcm9NeVNRTERDIiAvPg0KCQkJCTxUYXJnZXRFbGVtZW50IHJlZj0iQ2xlYXJvTXlTUUwiIC8+DQoJCQk8L1JlbGF0aW9uc2hpcFRlbXBsYXRlPg0KCQkJPFJlbGF0aW9uc2hpcFRlbXBsYXRlIHR5cGU9IlFOYW1lIiBpZD0iaWR2YWx1ZTAiPg0KCQkJCTxTb3VyY2VFbGVtZW50IHJlZj0iQ2xlYXJvTXlTUUwiIC8+DQoJCQkJPFRhcmdldEVsZW1lbnQgcmVmPSJDbGVhcm9NeVNRTERCIiAvPg0KCQkJPC9SZWxhdGlvbnNoaXBUZW1wbGF0ZT4NCgkJPC9Ub3BvbG9neVRlbXBsYXRlPg0KCQk8UGxhbnMgdGFyZ2V0TmFtZXNwYWNlPSJodHRwOi8vdGVtcHVyaS5vcmciPg0KCQkJPFBsYW4gaWQ9ImlkdmFsdWU1IiBuYW1lPSIiIHBsYW5MYW5ndWFnZT0iaHR0cDovL3RlbXB1cmkub3JnIg0KCQkJCXBsYW5UeXBlPSJodHRwOi8vdGVtcHVyaS5vcmciPg0KCQkJCTxkb2N1bWVudGF0aW9uIHhtbDpsYW5nPSIiIHNvdXJjZT0iaHR0cDovL3RlbXB1cmkub3JnIiAvPg0KCQkJCTxQcmVjb25kaXRpb24gZXhwcmVzc2lvbkxhbmd1YWdlPSJodHRwOi8vdGVtcHVyaS5vcmciIC8+DQoJCQkJPElucHV0UGFyYW1ldGVycz4NCgkJCQkJPElucHV0UGFyYW1ldGVyIG5hbWU9IiIgcmVxdWlyZWQ9InllcyIgdHlwZT0iIiAvPg0KCQkJCTwvSW5wdXRQYXJhbWV0ZXJzPg0KCQkJCTxPdXRwdXRQYXJhbWV0ZXJzPg0KCQkJCQk8T3V0cHV0UGFyYW1ldGVyIG5hbWU9IiIgcmVxdWlyZWQ9InllcyIgdHlwZT0iIiAvPg0KCQkJCTwvT3V0cHV0UGFyYW1ldGVycz4NCgkJCQk8UGxhbk1vZGVsPg0KCQkJCQk8ZG9jdW1lbnRhdGlvbiB4bWw6bGFuZz0iIiBzb3VyY2U9Imh0dHA6Ly90ZW1wdXJpLm9yZyIgLz4NCgkJCQk8L1BsYW5Nb2RlbD4NCgkJCTwvUGxhbj4NCgkJPC9QbGFucz4NCgk8L1NlcnZpY2VUZW1wbGF0ZT4NCgk8Tm9kZVR5cGVJbXBsZW1lbnRhdGlvbiBub2RlVHlwZT0iQXBhY2hlVmlydHVhbEhvc3QiIG5hbWU9IkFwYWNoZVZpcnR1YWxIb3N0SW1wbCI+DQoJCTxJbXBsZW1lbnRhdGlvbkFydGlmYWN0cz4NCgkJCTxJbXBsZW1lbnRhdGlvbkFydGlmYWN0IGFydGlmYWN0VHlwZT0iUHVwcGV0VGVtcGxhdGUiDQoJCQkJYXJ0aWZhY3RSZWY9IkFwYWNoZVZpcnR1YWxIb3N0UFQiPg0KCQkJPC9JbXBsZW1lbnRhdGlvbkFydGlmYWN0Pg0KCQk8L0ltcGxlbWVudGF0aW9uQXJ0aWZhY3RzPjwvTm9kZVR5cGVJbXBsZW1lbnRhdGlvbj4NCgk8Tm9kZVR5cGVJbXBsZW1lbnRhdGlvbiBub2RlVHlwZT0iQXBhY2hlIiBuYW1lPSJBcGFjaGVJbXBsIj48L05vZGVUeXBlSW1wbGVtZW50YXRpb24+DQoJPEFydGlmYWN0VHlwZSBuYW1lPSJQdXBwZXRUZW1wbGF0ZSI+PC9BcnRpZmFjdFR5cGU+DQoJPEFydGlmYWN0VGVtcGxhdGUgdHlwZT0iUHVwcGV0VGVtcGxhdGUiIGlkPSJBcGFjaGVWaXJ0dWFsSG9zdFBUIj48L0FydGlmYWN0VGVtcGxhdGU+DQo8L0RlZmluaXRpb25zPg==";
//		String decoded = new String(Base64.decodeBase64(base));
//		System.out.println(decoded);
	
		ProxyAPIService p = new ProxyAPIService();
		p.createJsonCreate("HOLA", "DESC", "FILE BAME");
	}
}
