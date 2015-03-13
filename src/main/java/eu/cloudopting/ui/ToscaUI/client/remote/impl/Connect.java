package eu.cloudopting.ui.ToscaUI.client.remote.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.RandomUtils;
import org.cruxframework.crux.core.server.rest.annotation.RestService;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.POST;
import org.cruxframework.crux.core.shared.rest.annotation.Path;
import org.cruxframework.crux.core.shared.rest.annotation.PathParam;
import org.cruxframework.crux.core.shared.rest.annotation.QueryParam;
import org.json.JSONException;
import org.json.JSONObject;



@RestService("connectService")
@Path("connect")
public class Connect {

	private String webPage = "http://localhost:8080/api/";
	private String authentication = "Basic YWRtaW46YWRtaW4=";
	private String cookie = "";

	@GET
	@Path("/")
	public String connect(@QueryParam("user") String user, @QueryParam("pass") String pass) {

		String result = "ERROR";
		try {
			String webPage = "http://localhost:8080";
			String name = user; //"admin";
			String password = pass; //"admin";

			String authString = name + ":" + password;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);

			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			authentication = "Basic " + authStringEnc;
			urlConnection.setRequestProperty("Authorization", authentication);
			//			urlConnection.setRequestProperty("Authorization", authentication);

			cookie = urlConnection.getHeaderField("Set-Cookie");
			
			result = cookie;
			/*
 // GET THE BODY OF THE REQUEST
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
			 */
			/*
 // PRINT ALL HEADER FIELDS			
			for(String key : urlConnection.getHeaderFields().keySet()){
				System.out.println(key + ": " + urlConnection.getHeaderField(key));
			}
			 */

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@GET
	@Path("applicationcreate")
	public String applicationCreate() throws MalformedURLException, IOException {
		String method = "application/create";

		return doCallToString(method);
	}

	@GET
	@Path("applcationlist")
	public String applicationList(
			@QueryParam("page") String page,
			@QueryParam("size") String size,
			@QueryParam("sortBy") String sortBy,
			@QueryParam("sortOrder") String sortOrder,
			@QueryParam("filter") String filter) throws MalformedURLException, IOException {

		String method = "application/list"
				+ "?page=" + page
				+ "&size=" + size
				+ "&sortBy=" + sortBy
				+ "&sortOrder=" + sortOrder
				+ "&filter=" + filter;

		return doCallToString(method);
	}

	@GET
	@Path("applcation/{id}")
	public String application(@PathParam("id") String id) throws MalformedURLException, IOException {

		String method = "application/" + id;

		return doCallToString(method);
	}

	@GET
	@Path("users/{user}")
	public String getUser(@PathParam("user") String user) throws MalformedURLException, IOException {

		String method = "users/" + user;

		String result = doCallToString(method);

		System.out.println(result);

		return result;
	}




	private String doCallToString(String method) throws MalformedURLException, IOException {

		String result = "ERROR";
		//		try {

		URL url = new URL(webPage + method);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", authentication);
		urlConnection.setRequestProperty("Cookie", cookie);

		InputStream is = urlConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);

		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuffer sb = new StringBuffer();
		while ((numCharsRead = isr.read(charArray)) > 0) {
			sb.append(charArray, 0, numCharsRead);
		}
		result =  sb.toString();
		//		} catch (MalformedURLException e) {
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}

		return result;
	}

	private String doCallToStringAndPayload(String method, String payload) throws MalformedURLException, IOException {

		String result = "ERROR";
		//		try {

		URL url = new URL(webPage + method);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", authentication);
		urlConnection.setRequestProperty("Cookie", cookie);

//		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
//		urlConnection.setRequestProperty("Accept", "application/json");
//		urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	    
		OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
	    writer.write(payload);
	    writer.close();
	    
		InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());

		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuffer sb = new StringBuffer();
		while ((numCharsRead = isr.read(charArray)) > 0) {
			sb.append(charArray, 0, numCharsRead);
		}
		result =  sb.toString();
		//		} catch (MalformedURLException e) {
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}

		return result;
	}


	//	public static void main(String[] args) throws Exception {
	//		
	//		
	//		Connect c = new Connect();
	//		String cookie = c.connect();
	//		
	//		String urlstr = "http://localhost:8080/api/application/list?";
	////		String urlstr = "http://localhost:8080/api/users/admin";
	//		
	//		String query = "page=1&size=10&sortBy=applicaitonName&sortOrder=ascfilter=a";
	//		
	//		try {
	//
	//			URL url = new URL(urlstr+query);
	//			URLConnection urlConnection = url.openConnection();
	//			urlConnection.setRequestProperty("Authorization", "Basic YWRtaW46YWRtaW4=");
	//			urlConnection.setRequestProperty("Cookie", cookie);
	//
	//			InputStream is = urlConnection.getInputStream();
	//			InputStreamReader isr = new InputStreamReader(is);
	//
	//			int numCharsRead;
	//			char[] charArray = new char[1024];
	//			StringBuffer sb = new StringBuffer();
	//			while ((numCharsRead = isr.read(charArray)) > 0) {
	//				sb.append(charArray, 0, numCharsRead);
	//			}
	//			System.out.println(sb.toString());
	//		} catch (MalformedURLException e) {
	//			e.printStackTrace();
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//	}

	@GET
	@Path("tosca")
	public String getTosca() throws MalformedURLException, IOException{

		String jsonObject = "{"
				+ "tosca: " + readFile("C:\\Users\\a591584\\Desktop\\CloudOpting\\TOSCA_ClearoExample.xml", Charset.defaultCharset())
				+ "}";

		return jsonObject;

	}

	@POST
	@Path("tosca/{id}")
	public String setTosca(@PathParam("id") String id, String json) throws MalformedURLException, IOException {

		String method = "application/create";

		connect("admin", "admin");
		String user = getUser("admin");
		
		String xmlFile = readFile("C:\\Users\\a591584\\Desktop\\CloudOpting\\TOSCA_ClearoExample.xml", Charset.defaultCharset());
		String toscaTemplate = readFile("C:\\Users\\a591584\\Desktop\\CloudOpting\\TOSCA_ClearoExample.xml", Charset.defaultCharset());
		int idXmlFile = RandomUtils.nextInt();
		int idFile = RandomUtils.nextInt();
		String applicationName = "Clearo";
		
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(user);
			json = "{"
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = doCallToStringAndPayload(method, json);

		System.out.println(result);

		return result;
	}

	static String readFile(String path, Charset encoding) 
			throws IOException 
			{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
			}

	public static void main(String[] args) throws Exception {
		Connect c= new Connect();
		c.setTosca("", "");
	}
}
