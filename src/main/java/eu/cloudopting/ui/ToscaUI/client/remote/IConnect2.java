package eu.cloudopting.ui.ToscaUI.client.remote;

import java.net.URLConnection;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetEndPoint;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.HeaderParam;
import org.cruxframework.crux.core.shared.rest.annotation.Path;


@TargetEndPoint("http://localhost:8080")
@Path("/")
public interface IConnect2 extends RestProxy {

	@GET
	@Path("/")
	void connect(
			@HeaderParam("Authorization") String basicAuth,
			Callback<URLConnection> callback);
	
}
