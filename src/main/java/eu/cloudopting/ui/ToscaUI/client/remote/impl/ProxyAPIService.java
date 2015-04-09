package eu.cloudopting.ui.ToscaUI.client.remote.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.ObjectMapper;
import org.cruxframework.crux.core.server.rest.annotation.RestService;
import org.cruxframework.crux.core.server.rest.spi.InternalServerErrorException;
import org.cruxframework.crux.core.shared.rest.annotation.DefaultValue;
import org.cruxframework.crux.core.shared.rest.annotation.FormParam;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.POST;
import org.cruxframework.crux.core.shared.rest.annotation.Path;
import org.cruxframework.crux.core.shared.rest.annotation.PathParam;
import org.cruxframework.crux.core.shared.rest.annotation.QueryParam;

import com.google.gwt.core.ext.typeinfo.NotFoundException;

import eu.cloudopting.ui.ToscaUI.server.model.Application;
import eu.cloudopting.ui.ToscaUI.server.model.ApplicationList;
import eu.cloudopting.ui.ToscaUI.server.utils.ConnectionUtils;
import eu.cloudopting.ui.ToscaUI.server.utils.IOUtils;

/**
 * 
 * @author xeviscc
 *
 */
@RestService("proxyAPIService")
@Path("proxyAPIService")
public class ProxyAPIService {
	
//	private static String baseURI = "http://localhost:8080";
	private static String restBaseURI = "http://localhost:8080/api/";
//	private static String authentication = ""; //For admin/admin is: Basic YWRtaW46YWRtaW4=
//	private CredentialsProvider provider;
//	private UsernamePasswordCredentials credentials; 
	private static CloseableHttpClient httpclient;
//	private static String cookie = "";
	
	/**
	 * USER_METHOD prepared method needs 1 parameters to work. 
	 * 
	 * @param UserId 
	 */
	private static String USER_METHOD = "users/%s";

	/**
	 * APPLICATION_METHOD prepared method needs 1 parameters to work. 
	 * 
	 * @param ApplicationId 
	 */
	private static String APPLICATION_METHOD = "application/%s";
	
	/**
	 * APPLICATION_CREATE_METHOD prepared method. 
	 * 
	 */
	private static String APPLICATION_CREATE_METHOD = "application/create";
	
	/**
	 * APPLICATION_CUSTOMIZATION_METHOD prepared method. 
	 * 
	 */
	private static String APPLICATION_CUSTOMIZATION_METHOD = "customization/create";

	
	
	/**
	 * APPLICATION_LIST_METHOD prepared method needs 5 parameters to work. 
	 * 
	 * @param page 
	 * @param size
	 * @param sortBy
	 * @param sortOrder
	 * @param filter
	 */
	private static String APPLICATION_LIST_METHOD = "application/list?page=%s&size=%s&sortBy=%s&sortOrder=%s&filter=%s";
	
	/**
	 * APPLICATION_LIST_METHOD prepared method does not need parameters to work. 
	 * 
	 */
	private static String APPLICATION_LIST_UNPAGINATED_METHOD = "application/listunpaginated";
	
	@GET
	@Path("connected")
	public Boolean connected() {
		return httpclient!=null;
	}
	
	@GET
	@Path("/")
	public Boolean connect(@QueryParam("user") String user, @QueryParam("pass") String pass) throws IOException {
			//Create the httpclient authenticated and needed for the session.
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, pass);
			provider.setCredentials(AuthScope.ANY, credentials);
			httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
			
			Application a = null;
			try {
				a = application("1");
			} catch (InternalServerErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(a!=null) return true;
			else return false;

//			//create a httpget to test the credentials
//	        HttpGet httpget = new HttpGet(baseURI);
//	        httpget.setHeader("Content-type", "application/json");
//	        //execute the url
//	        CloseableHttpResponse response = httpclient.execute(httpget);
//	        //Check if the response is OK, then proceed.
//	        try {
//	            System.out.println(response.getStatusLine());
//	            HttpEntity entity = response.getEntity();
//	            // do something useful with the response body
//	            // and ensure it is fully consumed
//	            EntityUtils.consume(entity);
//	        } finally {
//	        	response.close();
//	        }
	        
//			authentication = "Basic " + ConnectionUtils.getAuthString(user, pass);
//			URL url = new URL(baseURI);
//			URLConnection urlConnection = url.openConnection();
//			urlConnection.setRequestProperty("Authorization", authentication);
//
//
//			cookie = urlConnection.getHeaderField("Set-Cookie");
//			
//			if(LogsUtil.DEBUG_ENABLED){
//				System.out.println("CONNECTION:: ");
//				System.out.println("URL: " + baseURI);
//				System.out.println("Authorization: " + authentication);
//				System.out.println("Cookie: " + cookie);
//			}
//		
//		return cookie;
	}

//	@POST
//	@Path("application/create")
//	public String applicationCreate(@QueryParam("json") String json) throws MalformedURLException, IOException {
//		return doCallToStringAndPayload(APPLICATION_CREATE_METHOD, json);
//	}
	
	@POST
	@Path("application/create")
	public String applicationCreate(@QueryParam("name") String name, 
			@QueryParam("description") String description, 
			@QueryParam("fileName") String fileName) throws MalformedURLException, IOException {
		String json = createJsonCreate(name, description, fileName);		
		return doCallToStringAndPayload(APPLICATION_CREATE_METHOD, json);
	}
	
	public String createJsonCreate(String name, String description,
			String fileName) {

		String createNewApplicationJson = "";
		try {
			String xmlFile = IOUtils.readFile("src/test/resources/TOSCA_ClearoExample.xml", Charset.defaultCharset());
			xmlFile = xmlFile.replaceAll("Clearo", name);

			createNewApplicationJson = "{\n" +
					"\t\"statusId\": {\n" +
					"\t\t\"id\": 1\n" +
					"\t},\n" +
					"\t\"userId\": {\n" +
					"\t\t\"id\": 3\n" +
					"\t},\n" +
					"\t\"applicationName\": \"" + name + "\",\n" +
					"\t\"applicationDescription\": \"" + description + "\",\n" +
					"\t\"applicationToscaTemplate\": \"" + Base64.encodeBase64String(xmlFile.getBytes()) +  "\",\n" +
					"\t\"applicationVersion\": \"1\"\n" +
					"}";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createNewApplicationJson;
	}

	@POST
	@Path("customization/create")
	public String customizationCreate(@QueryParam("applicationId") String applicationId, 
			@FormParam("xmlFileBase64Encoded") String xmlFileBase64Encoded) throws MalformedURLException, IOException {
		String json = createJsonCustomization(applicationId, xmlFileBase64Encoded);
		return doCallToStringAndPayload(APPLICATION_CUSTOMIZATION_METHOD, json);
	}

	private String createJsonCustomization(String applicationId, String xmlFileBase64Encoded) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		
		//By default the creation is with status "Requested" --> 100;
	    String createNewCustomizationJson = 
	    		"{" +
	            "\"statusId\": { \"id\": 100 }," +
	            "\"applicationId\": { \"id\": " + applicationId + " }," +
	            "\"customizationToscaFile\": \"" + xmlFileBase64Encoded + "\"," +
	            "\"customizationCreation\": \"" + reportDate + "\"," +
	            "\"customizationActivation\": \"" + reportDate + "\"," +
	            "\"customizationDecommission\": \"" + reportDate + "\"," +
	            "\"username\": \"1\"" +
	            "}";
			
		return createNewCustomizationJson;
	}
	
	@GET
	@Path("applcation/list")
	public ApplicationList applicationList(
			@QueryParam("page") String page,
			@QueryParam("size") String size,
			@QueryParam("sortBy") String sortBy,
			@QueryParam("sortOrder") String sortOrder,
			@QueryParam("filter") @DefaultValue("") String filter) throws MalformedURLException, IOException, NotFoundException {
		if(filter==null) {
			filter ="";
		} else {
			filter = URLEncoder.encode(filter, "UTF-8");
		}
		String json = doCallToString(String.format(APPLICATION_LIST_METHOD, page, size, sortBy, sortOrder, filter));
		ObjectMapper mapper = new ObjectMapper();
		ApplicationList contentList = mapper.readValue(json, ApplicationList.class);
		return contentList;
	}
	
	@GET
	@Path("applcation/listunpaginated")
	public ApplicationList applicationListUnpaginated() throws MalformedURLException, IOException, NotFoundException {
		String json = doCallToString(APPLICATION_LIST_UNPAGINATED_METHOD);
		ObjectMapper mapper = new ObjectMapper();
		ApplicationList contentList = mapper.readValue(json, ApplicationList.class);
		return contentList;
	}

	@GET
	@Path("applcation/{id}")
	public Application application(@PathParam("id") String id) throws MalformedURLException, IOException, InternalServerErrorException, NotFoundException {
		String json = doCallToString(String.format(APPLICATION_METHOD, id));
		ObjectMapper mapper = new ObjectMapper();
		Application application = mapper.readValue(json, Application.class);
		return application;
	}

	@GET
	@Path("users/{user}")
	public String users(@PathParam("user") String user) throws MalformedURLException, IOException, NotFoundException {
		return doCallToString(String.format(USER_METHOD, user));
	}
	
	@GET
	@Path("file")
	public String sendFile(@QueryParam("fileName") String fileName) throws IOException{
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    String everything = "";
	    try { 
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	 
	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        } 
	        everything = sb.toString();
	    } finally { 
	        br.close();
	    } 
		//File f = new File(fileName);
//	    System.out.println("everything: " + everything);
		return everything;
	}

	/*
	* PRIVATE METHODS
	*/
	
	/**
	 * Does a GET call to a given string, setting the default authentication required.
	 * @param method
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws NotFoundException 
	 */
	private String doCallToString(String method) throws MalformedURLException, NotFoundException, InternalServerErrorException, IOException {
		System.out.println(restBaseURI+method);
		//Create the http post
        HttpGet httpget = new HttpGet(restBaseURI+method);
        httpget.setHeader("Content-type", "application/json");
        
        //execute the URL
        CloseableHttpResponse response = httpclient.execute(httpget);
        String message = "RARE";
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            message = ConnectionUtils.getStringFromInputStream(entity.getContent());
            if(response.getStatusLine().getStatusCode()==404)
            	throw new NotFoundException(message);
            if(response.getStatusLine().getStatusCode()==500)
            	throw new InternalServerErrorException(message);
        } finally {
        	response.close();
        }
        return message;
        
        
		//return ConnectionUtils.getBodyContent(createRESTConnection(method));
	}

	/**
	 * Does a POST call to a given string with a payload, setting the default authentication required.
	 * @param method
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private String doCallToStringAndPayload(String method, String payload) throws IOException {
		//Create the http post
        HttpPost httppost = new HttpPost(restBaseURI+method);
        httppost.setHeader("Content-type", "application/json");
        //Set the payload
        StringEntity se = new StringEntity(payload);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httppost.setEntity(se);        
        //execute the URL
        CloseableHttpResponse response = httpclient.execute(httppost);
        //get the data.
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            return ConnectionUtils.getStringFromInputStream(entity.getContent());
        } finally {
        	response.close();
        }
		
//		URLConnection urlConnection = createRESTConnection(method);
//		ConnectionUtils.setPayload(urlConnection, payload);
//		return ConnectionUtils.getBodyContent(urlConnection);
	}
	
//	/**
//	 * Method that creates a connection with the default authentication.
//	 * @param method
//	 * @return
//	 * @throws MalformedURLException
//	 * @throws IOException
//	 */
//	private URLConnection createRESTConnection(String method)
//			throws MalformedURLException, IOException {
//		//Build the URLConection
//		URL url = new URL(restBaseURI + method);
//		URLConnection urlConnection = url.openConnection();
////		urlConnection.setRequestProperty("Authorization", authentication);
////		urlConnection.setRequestProperty("Cookie", cookie);
////		if(LogsUtil.DEBUG_ENABLED){
////			System.out.println("REST CALL:: ");
////			System.out.println("URL: " + restBaseURI + method);
////			System.out.println("Authorization: " + authentication);
////			System.out.println("Cookie: " + cookie);
////		}
//		return urlConnection;
//	}


}
