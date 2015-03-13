package eu.cloudopting.ui.ToscaUI.client.remote;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetEndPoint;
import org.cruxframework.crux.core.client.rest.RestProxy.UseJsonP;
import org.cruxframework.crux.core.shared.rest.annotation.CookieParam;
import org.cruxframework.crux.core.shared.rest.annotation.DELETE;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.HeaderParam;
import org.cruxframework.crux.core.shared.rest.annotation.POST;
import org.cruxframework.crux.core.shared.rest.annotation.Path;
import org.cruxframework.crux.core.shared.rest.annotation.PathParam;

/**
 * 
 * @author xeviscc
 *
 */
@TargetEndPoint("http://localhost:8080")
@Path("api")
//@CorsSupport(allowOrigin={"localhost", "127.0.0.1"}, maxAge=15)
@UseJsonP
public interface IApi extends RestProxy {
	
	@GET
	@Path("account")
	void getAccount(
			@HeaderParam("Authorization") String basicAuth,
			@CookieParam("Cookie") String cookie,
			Callback<String> callback);

	@POST
	@Path("account")
	void postAccount(String value, Callback<String> callback);
	
	@POST
	@Path("account/change_password")
	void changePassword(Callback<String> callback);
	
	@GET
	@Path("account/sessions")
	void getSessions(Callback<String> callback);
	
	@DELETE
	@Path("account/sessions/{series}")
	void deleteSession(@PathParam("series") String series, Callback<String> callback);
	
	@GET
	@Path("users/{login}")
	void login(@PathParam("login") String login, Callback<String> callback);

	@POST
	@Path("authentication")
	void authentication(Callback<String> callback);

}
