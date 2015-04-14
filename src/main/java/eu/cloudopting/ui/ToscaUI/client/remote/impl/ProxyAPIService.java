package eu.cloudopting.ui.ToscaUI.client.remote.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

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
import org.cruxframework.crux.core.server.rest.spi.NotFoundException;
import org.cruxframework.crux.core.shared.rest.annotation.DefaultValue;
import org.cruxframework.crux.core.shared.rest.annotation.FormParam;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.POST;
import org.cruxframework.crux.core.shared.rest.annotation.Path;
import org.cruxframework.crux.core.shared.rest.annotation.PathParam;
import org.cruxframework.crux.core.shared.rest.annotation.QueryParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.cloudopting.ui.ToscaUI.server.model.Application;
import eu.cloudopting.ui.ToscaUI.server.model.ApplicationId;
import eu.cloudopting.ui.ToscaUI.server.model.ApplicationList;
import eu.cloudopting.ui.ToscaUI.server.model.Customizations;
import eu.cloudopting.ui.ToscaUI.server.model.StatusId;
import eu.cloudopting.ui.ToscaUI.server.model.UserId;
import eu.cloudopting.ui.ToscaUI.server.utils.ConnectionUtils;

/**
 * 
 * @author xeviscc
 *
 */
@RestService("proxyAPIService")
@Path("proxyAPIService")
public class ProxyAPIService {

	private static final Logger log = Logger.getLogger("ProxyAPIService");
	//	private static String baseURI = "http://localhost:8080";

	//External resource
//	private static String restBaseURI = "http://cloudopting1.cloudapp.net:8081/cloudopting/api";
	//Internal resource
	private static String restBaseURI = "http://172.17.0.59:8080/cloudopting/api/";
	//Development resource 
//	private static String restBaseURI = "http://localhost:8080/api/";

	private static CloseableHttpClient httpclient;

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
	 * CUSTOMIZATION_METHOD prepared method needs 1 parameters to work. 
	 * 
	 * @param CustomizationId 
	 */
	private static String CUSTOMIZATION_METHOD = "customization/%s";

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
		log.info("Sarting connect.");
		Boolean result = false; 
		
		//Create the httpclient authenticated and needed for the session.
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, pass);
		provider.setCredentials(AuthScope.ANY, credentials);
		httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

		Application a = null;
		try {
			a = application("1");
		} catch (Exception e) {
			log.info("The connection failed!!");
			httpclient = null;
		}
		
		if( a != null ) result = true;
		
		log.info("Ending connect.");
		return result;
	}
	
	@SuppressWarnings("unused")
	private void oldConnect(){
		/*
		//create a httpget to test the credentials
        HttpGet httpget = new HttpGet(baseURI);
        httpget.setHeader("Content-type", "application/json");
        //execute the url
        CloseableHttpResponse response = httpclient.execute(httpget);
        //Check if the response is OK, then proceed.
        try {
            log.fine(response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        } finally {
        	response.close();
        }

		authentication = "Basic " + ConnectionUtils.getAuthString(user, pass);
		URL url = new URL(baseURI);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", authentication);


		cookie = urlConnection.getHeaderField("Set-Cookie");

		if(LogsUtil.DEBUG_ENABLED){
			log.fine("CONNECTION:: ");
			log.fine("URL: " + baseURI);
			log.fine("Authorization: " + authentication);
			log.fine("Cookie: " + cookie);
		}

	return cookie;
	 */
	}

	//	@POST
	//	@Path("uploadFile")
	//	public Boolean uploadFile(@FormParam("textUpload") InputStream textUpload) {
	//		log.fine("HOLA");
	//		return true;
	//	}

	@POST
	@Path("application/create")
	public String applicationCreate(@QueryParam("name") String name, 
			@QueryParam("description") String description, 
			@QueryParam("fileName") String fileName) throws MalformedURLException, IOException {
		log.info("Starting applicationCreate.");
		String json = createJsonCreate(name, description, fileName);		
		String result = doCallToStringAndPayload(APPLICATION_CREATE_METHOD, json);
		log.info("Ending applicationCreate.");
		return result;
	}

	@POST
	@Path("customization/create")
	public String customizationCreate(@QueryParam("applicationId") String applicationId, 
			@FormParam("xmlFileBase64Encoded") String xmlFileBase64Encoded) throws MalformedURLException, IOException {
		log.info("Starting customizationCreate.");
		String json = createJsonCustomization(applicationId, xmlFileBase64Encoded);
		String result =  doCallToStringAndPayload(APPLICATION_CUSTOMIZATION_METHOD, json);
		log.info("Ending customizationCreate.");
		return result;
	}

	@GET
	@Path("applcation/list")
	public ApplicationList applicationList(
			@QueryParam("page") String page,
			@QueryParam("size") String size,
			@QueryParam("sortBy") String sortBy,
			@QueryParam("sortOrder") String sortOrder,
			@QueryParam("filter") @DefaultValue("") String filter) throws MalformedURLException, IOException, NotFoundException {
		log.info("Starting applicationList.");
		if(filter==null) {
			filter ="";
		} else {
			filter = URLEncoder.encode(filter, "UTF-8");
		}
		String json = doCallToString(String.format(APPLICATION_LIST_METHOD, page, size, sortBy, sortOrder, filter));
		ObjectMapper mapper = new ObjectMapper();
		ApplicationList contentList = mapper.readValue(json, ApplicationList.class);
		log.info("Ending applicationList.");
		return contentList;
	}

	@GET
	@Path("applcation/listunpaginated")
	public ApplicationList applicationListUnpaginated() throws MalformedURLException, IOException, NotFoundException {
		log.info("Starting applicationListUnpaginated.");
		String json = doCallToString(APPLICATION_LIST_UNPAGINATED_METHOD);
		ObjectMapper mapper = new ObjectMapper();
		ApplicationList contentList = mapper.readValue(json, ApplicationList.class);
		log.info("Ending applicationListUnpaginated.");
		return contentList;
	}

	@GET
	@Path("applcation/{id}")
	public Application application(@PathParam("id") String id) throws MalformedURLException, IOException, InternalServerErrorException, NotFoundException {
		log.info("Starting application.");
		String json = doCallToString(String.format(APPLICATION_METHOD, id));
		ObjectMapper mapper = new ObjectMapper();
		Application application = mapper.readValue(json, Application.class);
		log.info("Ending application.");
		return application;
	}

	@GET
	@Path("customization/{id}")
	public Customizations customization(@PathParam("id") String id) throws MalformedURLException, IOException, InternalServerErrorException, NotFoundException {
		log.info("Starting customization.");
		String json = doCallToString(String.format(CUSTOMIZATION_METHOD, id));
		ObjectMapper mapper = new ObjectMapper();
		Customizations customization = mapper.readValue(json, Customizations.class);
		log.info("Ending customization.");
		return customization;
	}

	@GET
	@Path("users/{user}")
	public String users(@PathParam("user") String user) throws MalformedURLException, IOException, NotFoundException {
		log.info("Starting users.");
		String result = doCallToString(String.format(USER_METHOD, user));
		log.info("Ending users.");
		return result;
	}

	@GET
	@Path("file")
	public String sendFile(@QueryParam("fileName") String fileName) throws IOException{
		log.info("Starting sendFile.");
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
		log.fine(everything);
		log.info("Ending sendFile.");
		return everything;
	}

	/*
	 * PRIVATE METHODS
	 */
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param fileName
	 * @return
	 */
	private String createJsonCreate(String name, String description,
			String fileName) {
		log.info("Starting createJsonCreate.");
		
		//FIXME: HARDCODED!!
		String tosca = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48RGVmaW5pdGlvbnMgeG1sbnM9Imh0dHA6Ly9kb2NzLm9hc2lzLW9wZW4ub3JnL3Rvc2NhL25zLzIwMTEvMTIiIHhtbG5zOmNvPSJodHRwOi8vZG9jcy5vYXNpcy1vcGVuLm9yZy90b3NjYS9ucy8yMDExLzEyL0Nsb3VkT3B0aW5nVHlwZXMiIHhtbG5zOnhzaT0iaHR0cDovL3d3dy53My5vcmcvMjAwMS9YTUxTY2hlbWEtaW5zdGFuY2UiIGlkPSJUZXN0QXBwIiBuYW1lPSIiIHRhcmdldE5hbWVzcGFjZT0iaHR0cDovL3RlbXB1cmkub3JnIiB4bWxuczp4bWw9Imh0dHA6Ly93d3cudzMub3JnL1hNTC8xOTk4L25hbWVzcGFjZSIgeHNpOnNjaGVtYUxvY2F0aW9uPSJodHRwOi8vZG9jcy5vYXNpcy1vcGVuLm9yZy90b3NjYS9ucy8yMDExLzEyIFRPU0NBLXYxLjAueHNkICAgaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvdG9zY2EvbnMvMjAxMS8xMi9DbG91ZE9wdGluZ1R5cGVzIC4vVHlwZXMvQ2xvdWRPcHRpbmdUeXBlcy54c2QiPg0KCTxOb2RlVHlwZSBuYW1lPSJWTWhvc3QiPg0KCQk8ZG9jdW1lbnRhdGlvbj5UaGlzIGlzIHRoZSBWTSBkZXNjcmlwdGlvbiwgd2UgbmVlZCB0byBjb2xsZWN0IHRoZSBTTEENCgkJCWluZm9ybWF0aW9uICh0aGF0IGlzIHRoZSBzZXQgb2YgQ1BVK1JBTStESVNLKSB0aGF0IHRoZSBWTSBuZWVkIHRvDQoJCQloYXZlIGZvciB0aGUgc2VydmljZSAodGhpcyBpbmZvcm1hdGlvbiBpcyBqdXN0IGEgbGFiZWwgZm9yIHRoZSBlbmQNCgkJCXVzZXIgYnV0IHRyYW5zbGF0ZSB0byBkYXRhIGZvciB0aGUgc3lzdGVtKQ0KCQk8L2RvY3VtZW50YXRpb24+DQoJCTxQcm9wZXJ0aWVzRGVmaW5pdGlvbiBlbGVtZW50PSJjbzpWTWhvc3RQcm9wZXJ0aWVzIiB0eXBlPSJjbzp0Vk1ob3N0UHJvcGVydGllcyIvPg0KCQk8SW50ZXJmYWNlcz4NCgkJCTxJbnRlcmZhY2UgbmFtZT0iaHR0cDovL3RlbXB1cmkub3JnIj4NCgkJCQk8T3BlcmF0aW9uIG5hbWU9Ikluc3RhbGwiPg0KCQkJCQk8ZG9jdW1lbnRhdGlvbj5UaGUgcGFyYW1ldGVycyB0byBhc2sgdG8gdGhlIGVuZCB1c2VyIHRvIGV4ZWN1dGUgdGhlDQoJCQkJCQkiaW5zdGFsbCIgb3BlcmF0aW9uIG9mIHRoaXMgbm9kZQ0KCQkJCQk8L2RvY3VtZW50YXRpb24+DQoJCQkJCTxJbnB1dFBhcmFtZXRlcnM+DQoJCQkJCQk8SW5wdXRQYXJhbWV0ZXIgbmFtZT0iY286U0xBLmNvOkNob3NlbiIgdHlwZT0iY286U0xBIj5CaWdDaXR5PC9JbnB1dFBhcmFtZXRlcj4NCgkJCQkJPC9JbnB1dFBhcmFtZXRlcnM+DQoJCQkJPC9PcGVyYXRpb24+DQoJCQk8L0ludGVyZmFjZT4NCgkJPC9JbnRlcmZhY2VzPg0KCTwvTm9kZVR5cGU+DQoJPE5vZGVUeXBlIG5hbWU9IkRvY2tlckNvbnRhaW5lciI+DQoJCTxkb2N1bWVudGF0aW9uPlRoaXMgaXMgdGhlIERvY2tlciBDb250YWluZXIgKHRoZSBEb2NrZXIgaG9zdCBpcw0KCQkJYWxyZWFkeSBpbnN0YWxsZWQgaW4gdGhlIFZNIGltYWdlKQ0KCQk8L2RvY3VtZW50YXRpb24+DQoJCTxQcm9wZXJ0aWVzRGVmaW5pdGlvbiBlbGVtZW50PSIiIHR5cGU9IiIvPg0KCTwvTm9kZVR5cGU+DQoJPE5vZGVUeXBlIG5hbWU9IkFwYWNoZSI+DQoJCTxkb2N1bWVudGF0aW9uPlRoaXMgaXMgdGhlIEFwYWNoZSBzZXJ2ZXIgKHdlIHNob3VsZCBub3QgYXNrIGFueXRoaW5nDQoJCQl0byB0aGUgZW5kIHVzZXIgYWJvdXQgYXBhY2hlLCBidXQgd2UgbmVlZCB0byBzZXQgdGhlIHByb3BlcnRpZXMpDQoJCTwvZG9jdW1lbnRhdGlvbj4NCgkJPFByb3BlcnRpZXNEZWZpbml0aW9uIGVsZW1lbnQ9IiIgdHlwZT0iIi8+DQoJPC9Ob2RlVHlwZT4NCgk8Tm9kZVR5cGUgbmFtZT0iQXBhY2hlVmlydHVhbEhvc3QiPg0KCQk8ZG9jdW1lbnRhdGlvbj5UaGlzIGlzIHRoZSBBcGFjaGUgVmlydHVhbCBIb3N0IGFuZCBoZXJlIHdlIGhhdmUgdGhpbmdzDQoJCQl0byBhc2sgdG8gdGhlIHVzZXINCgkJPC9kb2N1bWVudGF0aW9uPg0KCQk8UHJvcGVydGllc0RlZmluaXRpb24gZWxlbWVudD0iIiB0eXBlPSIiLz4NCgkJPEludGVyZmFjZXM+DQoJCQk8SW50ZXJmYWNlIG5hbWU9Imh0dHA6Ly90ZW1wdXJpLm9yZyI+DQoJCQkJPE9wZXJhdGlvbiBuYW1lPSJJbnN0YWxsIj4NCgkJCQkJPElucHV0UGFyYW1ldGVycz4NCgkJCQkJCTxJbnB1dFBhcmFtZXRlciBuYW1lPSJWSG9zdE5hbWUiIHR5cGU9InhzOnN0cmluZyIvPg0KCQkJCQk8L0lucHV0UGFyYW1ldGVycz4NCgkJCQk8L09wZXJhdGlvbj4NCgkJCTwvSW50ZXJmYWNlPg0KCQk8L0ludGVyZmFjZXM+DQoJPC9Ob2RlVHlwZT4NCgk8Tm9kZVR5cGUgbmFtZT0iTXlTUUwiPg0KCQk8ZG9jdW1lbnRhdGlvbj5UaGlzIGlzIHRoZSBNeVNRTCBlbmdpbmUNCgkJPC9kb2N1bWVudGF0aW9uPg0KCQk8UHJvcGVydGllc0RlZmluaXRpb24gZWxlbWVudD0iIiB0eXBlPSIiLz4NCgkJPEludGVyZmFjZXM+DQoJCQk8SW50ZXJmYWNlIG5hbWU9Imh0dHA6Ly90ZW1wdXJpLm9yZyI+DQoJCQkJPE9wZXJhdGlvbiBuYW1lPSJJbnN0YWxsIj4NCgkJCQkJPElucHV0UGFyYW1ldGVycz4NCgkJCQkJCTxJbnB1dFBhcmFtZXRlciBuYW1lPSJyb290X3Bhc3N3b3JkIiB0eXBlPSJ4czpzdHJpbmciLz4NCgkJCQkJPC9JbnB1dFBhcmFtZXRlcnM+DQoJCQkJPC9PcGVyYXRpb24+DQoJCQk8L0ludGVyZmFjZT4NCgkJPC9JbnRlcmZhY2VzPg0KCTwvTm9kZVR5cGU+DQoJPE5vZGVUeXBlIG5hbWU9Ik15U1FMRGF0YWJhc2UiPg0KCQk8ZG9jdW1lbnRhdGlvbj5UaGlzIGlzIHRoZSBNeVNRTCBlbmdpbmUNCgkJPC9kb2N1bWVudGF0aW9uPg0KCQk8UHJvcGVydGllc0RlZmluaXRpb24gZWxlbWVudD0iIiB0eXBlPSIiLz4NCgkJPEludGVyZmFjZXM+DQoJCQk8SW50ZXJmYWNlIG5hbWU9Imh0dHA6Ly90ZW1wdXJpLm9yZyI+DQoJCQkJPE9wZXJhdGlvbiBuYW1lPSJJbnN0YWxsIj4NCgkJCQkJPElucHV0UGFyYW1ldGVycz4NCgkJCQkJCTxJbnB1dFBhcmFtZXRlciBuYW1lPSJkYlBhc3N3b3JkIiB0eXBlPSJ4czpzdHJpbmciLz4NCgkJCQkJPC9JbnB1dFBhcmFtZXRlcnM+DQoJCQkJPC9PcGVyYXRpb24+DQoJCQk8L0ludGVyZmFjZT4NCgkJPC9JbnRlcmZhY2VzPg0KCTwvTm9kZVR5cGU+DQoJPFNlcnZpY2VUZW1wbGF0ZSBpZD0iVGVzdEFwcCIgbmFtZT0iIiBzdWJzdGl0dXRhYmxlTm9kZVR5cGU9IlFOYW1lIiB0YXJnZXROYW1lc3BhY2U9Imh0dHA6Ly90ZW1wdXJpLm9yZyI+DQoNCg0KCQk8VG9wb2xvZ3lUZW1wbGF0ZT4NCgkJCTxkb2N1bWVudGF0aW9uIHNvdXJjZT0iaHR0cDovL3RlbXB1cmkub3JnIiB4bWw6bGFuZz0iIi8+DQoJCQk8Tm9kZVRlbXBsYXRlIGlkPSJUZXN0QXBwVk0iIG1heEluc3RhbmNlcz0iMSIgbWluSW5zdGFuY2VzPSIxIiBuYW1lPSIiIHR5cGU9IlZNaG9zdCI+DQoJCQkJPGRvY3VtZW50YXRpb24gc291cmNlPSJodHRwOi8vdGVtcHVyaS5vcmciIHhtbDpsYW5nPSIiLz4NCgkJCQk8UHJvcGVydGllcz4NCgkJCQkJPGRvY3VtZW50YXRpb24gc291cmNlPSJodHRwOi8vdGVtcHVyaS5vcmciIHhtbDpsYW5nPSIiLz4NCgkJCQkJPGNvOlZNaG9zdFByb3BlcnRpZXM+DQoJCQkJCQk8Y286U0xBc1Byb3BlcnRpZXM+DQoJCQkJCQkJPGNvOlNMQSBOYW1lPSJCaWcgQ2l0eSIgaWQ9IkJpZ0NpdHkiPg0KCQkJCQkJCQk8Y286TnVtQ3B1cz4yPC9jbzpOdW1DcHVzPg0KCQkJCQkJCQk8Y286TWVtb3J5PjI8L2NvOk1lbW9yeT4NCgkJCQkJCQkJPGNvOlByaWNlPjEwMDAwPC9jbzpQcmljZT4NCgkJCQkJCQkJPGNvOkRpc2s+MTA8L2NvOkRpc2s+DQoJCQkJCQkJCTxjbzpDaG9zZW4+ZmFsc2U8L2NvOkNob3Nlbj4NCgkJCQkJCQk8L2NvOlNMQT4NCgkJCQkJCQk8Y286U0xBIE5hbWU9IlNtYWxsIENpdHkiIGlkPSJTbWFsbENpdHkiPg0KCQkJCQkJCQk8Y286TnVtQ3B1cz4xPC9jbzpOdW1DcHVzPg0KCQkJCQkJCQk8Y286TWVtb3J5PjE8L2NvOk1lbW9yeT4NCgkJCQkJCQkJPGNvOlByaWNlPjUwMDA8L2NvOlByaWNlPg0KCQkJCQkJCQk8Y286RGlzaz41PC9jbzpEaXNrPg0KCQkJCQkJCQk8Y286Q2hvc2VuPnRydWU8L2NvOkNob3Nlbj4NCgkJCQkJCQk8L2NvOlNMQT4NCgkJCQkJCQk8Y286U0xBIE5hbWU9IlJlZ2lvbiIgaWQ9IlJlZ2lvbiI+DQoJCQkJCQkJCTxjbzpOdW1DcHVzPjQ8L2NvOk51bUNwdXM+DQoJCQkJCQkJCTxjbzpNZW1vcnk+NDwvY286TWVtb3J5Pg0KCQkJCQkJCQk8Y286UHJpY2U+MjAwMDA8L2NvOlByaWNlPg0KCQkJCQkJCQk8Y286RGlzaz4yMDwvY286RGlzaz4NCgkJCQkJCQkJPGNvOkNob3Nlbj5mYWxzZTwvY286Q2hvc2VuPg0KCQkJCQkJCTwvY286U0xBPg0KCQkJCQkJCTxjbzp2bUltYWdlLz4NCgkJCQkJCTwvY286U0xBc1Byb3BlcnRpZXM+DQoJCQkJCTwvY286Vk1ob3N0UHJvcGVydGllcz4NCgkJCQk8L1Byb3BlcnRpZXM+DQoJCQk8L05vZGVUZW1wbGF0ZT4NCgkJCTxOb2RlVGVtcGxhdGUgaWQ9IlRlc3RBcHBBcGFjaGVEQyIgdHlwZT0iRG9ja2VyQ29udGFpbmVyIi8+DQoJCQk8Tm9kZVRlbXBsYXRlIGlkPSJUZXN0QXBwQXBhY2hlIiB0eXBlPSJBcGFjaGUiPg0KCQkJCTxQcm9wZXJ0aWVzPg0KCQkJCQk8ZG9jdW1lbnRhdGlvbi8+DQoJCQkJCTxjbzpBcGFjaGVQcm9wZXJ0aWVzPg0KCQkJCQkJPGNvOmRlZmF1bHRfbW9kcz50cnVlPC9jbzpkZWZhdWx0X21vZHM+DQoJCQkJCQk8Y286c2VydmVybmFtZT5mcWRuPC9jbzpzZXJ2ZXJuYW1lPg0KCQkJCQkJPGNvOmxvZ19mb3JtYXRzPnsgdmhvc3RfY29tbW9uID0mZ3Q7ICcldiAlaCAlbCAldSAldCBcIiVyXCIgJSZndDtzICViJyB9PC9jbzpsb2dfZm9ybWF0cz4NCgkJCQkJPC9jbzpBcGFjaGVQcm9wZXJ0aWVzPg0KCQkJCTwvUHJvcGVydGllcz4NCgkJCTwvTm9kZVRlbXBsYXRlPg0KCQkJPE5vZGVUZW1wbGF0ZSBpZD0iVGVzdEFwcEFwYWNoZVZIIiB0eXBlPSJBcGFjaGVWaXJ0dWFsSG9zdCI+DQoJCQkJPFByb3BlcnRpZXM+DQoJCQkJCTxjbzpBcGFjaGVWaXJ0dWFsSG9zdFByb3BlcnRpZXM+DQoJCQkJCQk8Y286Vkhvc3ROYW1lPg0KPD9nZXRJbnB1dCBWSG9zdE5hbWU/Pg0KCQkJCQkJPC9jbzpWSG9zdE5hbWU+DQoJCQkJCQk8Y286YWxpYXNlcy8+DQoJCQkJCQk8Y286ZGVmYXVsdF92aG9zdD5mYWxzZTwvY286ZGVmYXVsdF92aG9zdD4NCgkJCQkJCTxjbzpkb2Nyb290Lz4NCgkJCQkJCTxjbzpkaXJlY3Rvcmllcy8+DQoJCQkJCQk8Y286bG9nX2xldmVsLz4NCgkJCQkJCTxjbzpvcHRpb25zLz4NCgkJCQkJCTxjbzpwb3J0Lz4NCgkJCQkJCTxjbzpwcm94eV9wYXNzLz4NCgkJCQkJCTxjbzpyZWRpcmVjdF9zb3VyY2UvPg0KCQkJCQkJPGNvOnJlZGlyZWN0X2Rlc3QvPg0KCQkJCQkJPGNvOnJlZGlyZWN0X3N0YXR1cy8+DQoJCQkJCQk8Y286cmV3cml0ZXMvPg0KCQkJCQkJPGNvOnNldGVudi8+DQoJCQkJCQk8Y286c3NsPmZhbHNlPC9jbzpzc2w+DQoJCQkJCQk8Y286c2VydmVyYWRtaW4vPg0KCQkJCQk8L2NvOkFwYWNoZVZpcnR1YWxIb3N0UHJvcGVydGllcz4NCgkJCQk8L1Byb3BlcnRpZXM+DQoJCQk8L05vZGVUZW1wbGF0ZT4NCgkJCTxOb2RlVGVtcGxhdGUgaWQ9IlRlc3RBcHBBcGFjaGVWSDIiIHR5cGU9IkFwYWNoZVZpcnR1YWxIb3N0Ij4NCgkJCQk8UHJvcGVydGllcz4NCgkJCQkJPGNvOkFwYWNoZVZpcnR1YWxIb3N0UHJvcGVydGllcz4NCgkJCQkJCTxjbzpWSG9zdE5hbWU+DQo8P2dldElucHV0IFZIb3N0TmFtZT8+DQoJCQkJCQk8L2NvOlZIb3N0TmFtZT4NCgkJCQkJCTxjbzphbGlhc2VzLz4NCgkJCQkJCTxjbzpkZWZhdWx0X3Zob3N0PmZhbHNlPC9jbzpkZWZhdWx0X3Zob3N0Pg0KCQkJCQkJPGNvOmRvY3Jvb3QvPg0KCQkJCQkJPGNvOmRpcmVjdG9yaWVzLz4NCgkJCQkJCTxjbzpsb2dfbGV2ZWwvPg0KCQkJCQkJPGNvOm9wdGlvbnMvPg0KCQkJCQkJPGNvOnBvcnQvPg0KCQkJCQkJPGNvOnByb3h5X3Bhc3MvPg0KCQkJCQkJPGNvOnJlZGlyZWN0X3NvdXJjZS8+DQoJCQkJCQk8Y286cmVkaXJlY3RfZGVzdC8+DQoJCQkJCQk8Y286cmVkaXJlY3Rfc3RhdHVzLz4NCgkJCQkJCTxjbzpyZXdyaXRlcy8+DQoJCQkJCQk8Y286c2V0ZW52Lz4NCgkJCQkJCTxjbzpzc2w+ZmFsc2U8L2NvOnNzbD4NCgkJCQkJCTxjbzpzZXJ2ZXJhZG1pbi8+DQoJCQkJCTwvY286QXBhY2hlVmlydHVhbEhvc3RQcm9wZXJ0aWVzPg0KCQkJCTwvUHJvcGVydGllcz4NCgkJCTwvTm9kZVRlbXBsYXRlPg0KCQkJPE5vZGVUZW1wbGF0ZSBpZD0iVGVzdEFwcE15U1FMREMiIHR5cGU9IkRvY2tlckNvbnRhaW5lciI+DQoJCQkJPFByb3BlcnRpZXM+DQoJCQkJCTxkb2N1bWVudGF0aW9uLz4NCgkJCQkJPGNvOkRvY2tlckNvbnRhaW5lclByb3BlcnRpZXM+DQoJCQkJCQk8Y286ZnJvbS8+DQoJCQkJCQk8Y286ZW50cnlwb2ludC8+DQoJCQkJCQk8Y286Y21kLz4NCgkJCQkJPC9jbzpEb2NrZXJDb250YWluZXJQcm9wZXJ0aWVzPg0KCQkJCTwvUHJvcGVydGllcz4NCgkJCTwvTm9kZVRlbXBsYXRlPg0KCQkJPE5vZGVUZW1wbGF0ZSBpZD0iVGVzdEFwcE15U1FMIiB0eXBlPSJNeVNRTCI+DQoJCQkJPFByb3BlcnRpZXM+DQoJCQkJCTxkb2N1bWVudGF0aW9uLz4NCgkJCQkJPGNvOk15U1FMUHJvcGVydGllcz4NCgkJCQkJCTxjbzpyb290X3Bhc3N3b3JkPg0KPD9nZXRJbnB1dCByb290X3Bhc3N3b3JkPz4NCgkJCQkJCTwvY286cm9vdF9wYXNzd29yZD4NCgkJCQkJCTxjbzpyZW1vdmVfZGVmYXVsdF9hY2NvdW50cy8+DQoJCQkJCQk8Y286b3ZlcnJpZGVfb3B0aW9ucy8+DQoJCQkJCTwvY286TXlTUUxQcm9wZXJ0aWVzPg0KCQkJCTwvUHJvcGVydGllcz4NCgkJCTwvTm9kZVRlbXBsYXRlPg0KCQkJPE5vZGVUZW1wbGF0ZSBpZD0iVGVzdEFwcE15U1FMREIiIHR5cGU9Ik15U1FMRGF0YWJhc2UiPg0KCQkJCTxQcm9wZXJ0aWVzPg0KCQkJCQk8ZG9jdW1lbnRhdGlvbi8+DQoJCQkJCTxjbzpNeVNRTERhdGFiYXNlUHJvcGVydGllcz4NCgkJCQkJCTxjbzpkYk5hbWUvPg0KCQkJCQkJPGNvOmRiVXNlci8+DQoJCQkJCQk8Y286ZGJQYXNzd29yZD4NCjw/Z2V0SW5wdXQgZGJQYXNzd29yZD8+DQoJCQkJCQk8L2NvOmRiUGFzc3dvcmQ+DQoJCQkJCQk8Y286Y29ubmVjdGlvbkhvc3QvPg0KCQkJCQkJPGNvOmdyYW50Lz4NCgkJCQkJCTxjbzpzcWwvPg0KCQkJCQkJPGNvOmltcG9ydF90aW1lb3V0Lz4NCgkJCQkJCTxjbzpjaGFyc2V0Lz4NCgkJCQkJCTxjbzpjb2xsYXRlLz4NCgkJCQkJPC9jbzpNeVNRTERhdGFiYXNlUHJvcGVydGllcz4NCgkJCQk8L1Byb3BlcnRpZXM+DQoJCQk8L05vZGVUZW1wbGF0ZT4NCgkJCTxSZWxhdGlvbnNoaXBUZW1wbGF0ZSBpZD0iaWR2YWx1ZTAiIHR5cGU9IlFOYW1lIj4NCgkJCQk8U291cmNlRWxlbWVudCByZWY9IlRlc3RBcHBWTSIvPg0KCQkJCTxUYXJnZXRFbGVtZW50IHJlZj0iVGVzdEFwcEFwYWNoZURDIi8+DQoJCQk8L1JlbGF0aW9uc2hpcFRlbXBsYXRlPg0KCQkJPFJlbGF0aW9uc2hpcFRlbXBsYXRlIGlkPSJpZHZhbHVlMCIgdHlwZT0iUU5hbWUiPg0KCQkJCTxTb3VyY2VFbGVtZW50IHJlZj0iVGVzdEFwcEFwYWNoZURDIi8+DQoJCQkJPFRhcmdldEVsZW1lbnQgcmVmPSJUZXN0QXBwQXBhY2hlIi8+DQoJCQk8L1JlbGF0aW9uc2hpcFRlbXBsYXRlPg0KCQkJPFJlbGF0aW9uc2hpcFRlbXBsYXRlIGlkPSJpZHZhbHVlMCIgdHlwZT0iUU5hbWUiPg0KCQkJCTxTb3VyY2VFbGVtZW50IHJlZj0iVGVzdEFwcEFwYWNoZSIvPg0KCQkJCTxUYXJnZXRFbGVtZW50IHJlZj0iVGVzdEFwcEFwYWNoZVZIIi8+DQoJCQk8L1JlbGF0aW9uc2hpcFRlbXBsYXRlPg0KCQkJPFJlbGF0aW9uc2hpcFRlbXBsYXRlIGlkPSJpZHZhbHVlMCIgdHlwZT0iUU5hbWUiPg0KCQkJCTxTb3VyY2VFbGVtZW50IHJlZj0iVGVzdEFwcFZNIi8+DQoJCQkJPFRhcmdldEVsZW1lbnQgcmVmPSJUZXN0QXBwTXlTUUxEQyIvPg0KCQkJPC9SZWxhdGlvbnNoaXBUZW1wbGF0ZT4NCgkJCTxSZWxhdGlvbnNoaXBUZW1wbGF0ZSBpZD0iaWR2YWx1ZTAiIHR5cGU9IlFOYW1lIj4NCgkJCQk8U291cmNlRWxlbWVudCByZWY9IlRlc3RBcHBNeVNRTERDIi8+DQoJCQkJPFRhcmdldEVsZW1lbnQgcmVmPSJUZXN0QXBwTXlTUUwiLz4NCgkJCTwvUmVsYXRpb25zaGlwVGVtcGxhdGU+DQoJCQk8UmVsYXRpb25zaGlwVGVtcGxhdGUgaWQ9ImlkdmFsdWUwIiB0eXBlPSJRTmFtZSI+DQoJCQkJPFNvdXJjZUVsZW1lbnQgcmVmPSJUZXN0QXBwTXlTUUwiLz4NCgkJCQk8VGFyZ2V0RWxlbWVudCByZWY9IlRlc3RBcHBNeVNRTERCIi8+DQoJCQk8L1JlbGF0aW9uc2hpcFRlbXBsYXRlPg0KCQk8L1RvcG9sb2d5VGVtcGxhdGU+DQoJCTxQbGFucyB0YXJnZXROYW1lc3BhY2U9Imh0dHA6Ly90ZW1wdXJpLm9yZyI+DQoJCQk8UGxhbiBpZD0iaWR2YWx1ZTUiIG5hbWU9IiIgcGxhbkxhbmd1YWdlPSJodHRwOi8vdGVtcHVyaS5vcmciIHBsYW5UeXBlPSJodHRwOi8vdGVtcHVyaS5vcmciPg0KCQkJCTxkb2N1bWVudGF0aW9uIHNvdXJjZT0iaHR0cDovL3RlbXB1cmkub3JnIiB4bWw6bGFuZz0iIi8+DQoJCQkJPFByZWNvbmRpdGlvbiBleHByZXNzaW9uTGFuZ3VhZ2U9Imh0dHA6Ly90ZW1wdXJpLm9yZyIvPg0KCQkJCTxJbnB1dFBhcmFtZXRlcnM+DQoJCQkJCTxJbnB1dFBhcmFtZXRlciBuYW1lPSIiIHJlcXVpcmVkPSJ5ZXMiIHR5cGU9IiIvPg0KCQkJCTwvSW5wdXRQYXJhbWV0ZXJzPg0KCQkJCTxPdXRwdXRQYXJhbWV0ZXJzPg0KCQkJCQk8T3V0cHV0UGFyYW1ldGVyIG5hbWU9IiIgcmVxdWlyZWQ9InllcyIgdHlwZT0iIi8+DQoJCQkJPC9PdXRwdXRQYXJhbWV0ZXJzPg0KCQkJCTxQbGFuTW9kZWw+DQoJCQkJCTxkb2N1bWVudGF0aW9uIHNvdXJjZT0iaHR0cDovL3RlbXB1cmkub3JnIiB4bWw6bGFuZz0iIi8+DQoJCQkJPC9QbGFuTW9kZWw+DQoJCQk8L1BsYW4+DQoJCTwvUGxhbnM+DQoJPC9TZXJ2aWNlVGVtcGxhdGU+DQoJPE5vZGVUeXBlSW1wbGVtZW50YXRpb24gbmFtZT0iQXBhY2hlVmlydHVhbEhvc3RJbXBsIiBub2RlVHlwZT0iQXBhY2hlVmlydHVhbEhvc3QiPg0KCQk8SW1wbGVtZW50YXRpb25BcnRpZmFjdHM+DQoJCQk8SW1wbGVtZW50YXRpb25BcnRpZmFjdCBhcnRpZmFjdFJlZj0iQXBhY2hlVmlydHVhbEhvc3RQVCIgYXJ0aWZhY3RUeXBlPSJQdXBwZXRUZW1wbGF0ZSI+DQoJCQk8L0ltcGxlbWVudGF0aW9uQXJ0aWZhY3Q+DQoJCTwvSW1wbGVtZW50YXRpb25BcnRpZmFjdHM+DQo8L05vZGVUeXBlSW1wbGVtZW50YXRpb24+DQoJPE5vZGVUeXBlSW1wbGVtZW50YXRpb24gbmFtZT0iQXBhY2hlSW1wbCIgbm9kZVR5cGU9IkFwYWNoZSIvPg0KCTxBcnRpZmFjdFR5cGUgbmFtZT0iUHVwcGV0VGVtcGxhdGUiLz4NCgk8QXJ0aWZhY3RUZW1wbGF0ZSBpZD0iQXBhY2hlVmlydHVhbEhvc3RQVCIgdHlwZT0iUHVwcGV0VGVtcGxhdGUiLz4NCjwvRGVmaW5pdGlvbnM+DQo=";
		String xmlTosca = new String(Base64.decodeBase64(tosca));
		xmlTosca = xmlTosca.replaceAll("TestApp", name);

		Application a = new Application();
		StatusId statusId = new StatusId();
		statusId.setId(1);
		a.setStatusId(statusId);
		UserId userId = new UserId();
		userId.setId(3);
		a.setUserId(userId);
		a.setApplicationDescription(description);
		a.setApplicationName(name);
		a.setApplicationToscaTemplate(Base64.encodeBase64String(xmlTosca.getBytes()));
		a.setApplicationVersion("1");

		Gson gson = new GsonBuilder().create();
		String gsonApplication = gson.toJson(a);


		//		String createNewApplicationJson = "{\n" +
		//				"\t\"statusId\": {\n" +
		//				"\t\t\"id\": 1\n" +
		//				"\t},\n" +
		//				"\t\"userId\": {\n" +
		//				"\t\t\"id\": 3\n" +
		//				"\t},\n" +
		//				"\t\"applicationName\": \"" + name + "\",\n" +
		//				"\t\"applicationDescription\": \"" + description + "\",\n" +
		//				"\t\"applicationToscaTemplate\": \"" + Base64.encodeBase64String(xmlTosca.getBytes()) +  "\",\n" +
		//				"\t\"applicationVersion\": \"1\"\n" +
		//				"}";
		//		
		//		System.out.println(gsonApplication);
		//		System.out.println(createNewApplicationJson);

		log.fine(gsonApplication);
		log.info("Ending createJsonCreate.");
		return gsonApplication;
	}
	
	/**
	 * 
	 * @param applicationId
	 * @param xmlFileBase64Encoded
	 * @return
	 */
	private String createJsonCustomization(String applicationId, String xmlFileBase64Encoded) {
		log.info("Starting createJsonCustomization.");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);


		Customizations c = new Customizations();
		StatusId statusId = new StatusId();
		statusId.setId(100);
		c.setStatusId(statusId);
		ApplicationId applicaitonId = new ApplicationId();
		applicaitonId.setId(Integer.valueOf(applicationId));
		c.setApplicationId(applicaitonId);
		c.setCustomizationActivation(reportDate);
		c.setCustomizationCreation(reportDate);
		c.setCustomizationDecommission(reportDate);
		c.setCustomizationToscaFile(xmlFileBase64Encoded);
		c.setUsername("1");

		Gson gson = new GsonBuilder().create();
		String gsonCustomizations = gson.toJson(c);

		//		//By default the creation is with status "Requested" --> 100;
		//	    String createNewCustomizationJson = 
		//	    		"{" +
		//	            "\"statusId\": { \"id\": 100 }," +
		//	            "\"applicationId\": { \"id\": " + applicationId + " }," +
		//	            "\"customizationToscaFile\": \"" + xmlFileBase64Encoded + "\"," +
		//	            "\"customizationCreation\": \"" + reportDate + "\"," +
		//	            "\"customizationActivation\": \"" + reportDate + "\"," +
		//	            "\"customizationDecommission\": \"" + reportDate + "\"," +
		//	            "\"username\": \"1\"" +
		//	            "}";
		//		
		//		System.out.println(gsonCustomizations);
		//		System.out.println(createNewCustomizationJson);

		log.fine(gsonCustomizations);
		log.info("Ending createJsonCustomization.");
		return gsonCustomizations;
	}
	
	/**
	 * Does a GET call to a given string, setting the default authentication required.
	 * @param method
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws NotFoundException 
	 */
	private String doCallToString(String method) throws MalformedURLException, NotFoundException, InternalServerErrorException, IOException {
		log.info("Starting doCallToString.");
		log.fine(restBaseURI+method);
		String message = "null";
		//Create the http post
		HttpGet httpget = new HttpGet(restBaseURI+method);
		httpget.setHeader("Content-type", "application/json");

		//execute the URL
		CloseableHttpResponse response = httpclient.execute(httpget);
		
		try {
			log.info(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			message = ConnectionUtils.getStringFromInputStream(entity.getContent());
			if(response.getStatusLine().getStatusCode()==404)
				throw new NotFoundException(message);
			if(response.getStatusLine().getStatusCode()==500)
				throw new InternalServerErrorException(message);
		} finally {
			response.close();
		}
		log.fine(message);
		log.info("Ending doCallToString.");
		return message;

	}

	/**
	 * Does a POST call to a given string with a payload, setting the default authentication required.
	 * @param method
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private String doCallToStringAndPayload(String method, String payload) throws IOException {
		log.info("Starting doCallToStringAndPayload.");
		log.fine(restBaseURI+method);
		String message = "null";
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
			log.info(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			message = ConnectionUtils.getStringFromInputStream(entity.getContent());
		} finally {
			response.close();
		}
		log.fine(message);
		log.info("Ending doCallToStringAndPayload.");
		return message;
	}
}
