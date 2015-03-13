package eu.cloudopting.ui.ToscaUI.client.remote.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.cruxframework.crux.core.server.rest.annotation.RestService;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.POST;
import org.cruxframework.crux.core.shared.rest.annotation.Path;
import org.cruxframework.crux.core.shared.rest.annotation.PathParam;
import org.cruxframework.crux.core.shared.rest.annotation.QueryParam;

import eu.cloudopting.ui.ToscaUI.utils.ConnectionUtils;
import eu.cloudopting.ui.ToscaUI.utils.IOUtils;

/**
 * 
 * @author xeviscc
 *
 */
@RestService("proxyAPIService")
@Path("proxyAPI")
public class ProxyAPI {
	
	private String baseURI = "http://localhost:8080";
	private String restBaseURI = "http://localhost:8080/api/";
	private String authentication = ""; //For admin/admin is: Basic YWRtaW46YWRtaW4=
	private String cookie = "";
	
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
	 * APPLICATION_LIST_METHOD prepared method needs 5 parameters to work. 
	 * 
	 * @param page 
	 * @param size
	 * @param sortBy
	 * @param sortOrder
	 * @param filter
	 */
	private static String APPLICATION_LIST_METHOD = "application/list?page=%s&size=%s&sortBy=%s&sortOrder=%s&filter=%s";

	@GET
	@Path("/")
	public void connect(@QueryParam("user") String user, @QueryParam("pass") String pass) {
		try {
			authentication = "Basic " + ConnectionUtils.getAuthString(user, pass);
			
			URL url = new URL(baseURI);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", authentication);

			cookie = urlConnection.getHeaderField("Set-Cookie");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Path("application/create")
	public String applicationCreate(@QueryParam("json") String json) throws MalformedURLException, IOException {
		return doCallToStringAndPayload(APPLICATION_CREATE_METHOD, json);
	}

	@GET
	@Path("applcationlist")
	public String applicationList(
			@QueryParam("page") String page,
			@QueryParam("size") String size,
			@QueryParam("sortBy") String sortBy,
			@QueryParam("sortOrder") String sortOrder,
			@QueryParam("filter") String filter) throws MalformedURLException, IOException {
		return doCallToString(String.format(APPLICATION_LIST_METHOD, page, size, sortBy, sortOrder, filter));
	}

	@GET
	@Path("applcation/{id}")
	public String application(@PathParam("id") String id) throws MalformedURLException, IOException {
		String jsonObject = "{"
				+ "tosca: " + IOUtils.readFile("C:\\Users\\a591584\\Desktop\\CloudOpting\\TOSCA_ClearoExample.xml", Charset.defaultCharset())
				+ "}";

		return jsonObject;
		//return doCallToString(String.format(APPLICATION_METHOD, id));
	}

	@GET
	@Path("users/{user}")
	public String users(@PathParam("user") String user) throws MalformedURLException, IOException {
		return doCallToString(String.format(USER_METHOD, user));
	}


	//PRIVATE METHODS

	/**
	 * Does the call to a given string, setting the default authentication and cookie required.
	 * @param method
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private String doCallToString(String method) throws MalformedURLException, IOException {
		return ConnectionUtils.getBodyContent(createRESTConnection(method));
	}

	/**
	 * Does the call to a given string with a payload, setting the default authentication and cookie required.
	 * @param method
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private String doCallToStringAndPayload(String method, String payload) throws MalformedURLException, IOException {
		URLConnection urlConnection = createRESTConnection(method);
		ConnectionUtils.setPayload(urlConnection, payload);
		return ConnectionUtils.getBodyContent(urlConnection);
	}
	
	/**
	 * Method that creates a connection with the default authentication.
	 * @param method
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private URLConnection createRESTConnection(String method)
			throws MalformedURLException, IOException {
		URL url = new URL(restBaseURI + method);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", authentication);
		urlConnection.setRequestProperty("Cookie", cookie);
		return urlConnection;
	}

}
