package eu.cloudopting.ui.ToscaUI.client.remote;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;

/**
 * This interface acts as a proxy to the REST API of the middleware.
 * @author xeviscc
 *
 */
@TargetRestService("proxyAPIService")
public interface IProxyAPIService extends RestProxy {
	
	void connect(String user, String pass, Callback<Boolean> callback);
	
	void applicationCreate(String json, Callback<String> callback);
	
	void applicationList(
			String page,
			String size,
			String sortBy,
			String sortOrder,
			String filter,
			Callback<String> callback);
		
	void application(String id, Callback<String> callback);
	
	void users(String user, Callback<String> callback);
	
}
