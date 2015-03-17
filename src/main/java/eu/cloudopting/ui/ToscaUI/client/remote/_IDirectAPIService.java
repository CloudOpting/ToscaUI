package eu.cloudopting.ui.ToscaUI.client.remote;

import java.net.URLConnection;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetEndPoint;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.HeaderParam;
import org.cruxframework.crux.core.shared.rest.annotation.Path;

/**
 * This interface goes direct to the RESP API of the middleware.
 * @author xeviscc
 * 
 * This interface is not used.
 *
 */
@TargetEndPoint("http://localhost:8080")
@Path("/")
public interface _IDirectAPIService extends RestProxy {

	@GET
	@Path("/")
	void connect(
			@HeaderParam("Authorization") String basicAuth,
			Callback<URLConnection> callback);
	
}
