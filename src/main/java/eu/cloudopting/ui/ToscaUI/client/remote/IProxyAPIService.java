package eu.cloudopting.ui.ToscaUI.client.remote;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;

import eu.cloudopting.ui.ToscaUI.server.model.Application;
import eu.cloudopting.ui.ToscaUI.server.model.ApplicationList;

/**
 * This interface acts as a proxy to the REST API of the middleware.
 * @author xeviscc
 *
 */
@TargetRestService("proxyAPIService")
public interface IProxyAPIService extends RestProxy {
	
	void connected(Callback<Boolean> callback);
	
	void connect(String user, String pass, Callback<Boolean> callback);
	
//	void applicationCreate(String json, Callback<String> callback);
	
	void applicationCreate(String name, String description, String fileName, Callback<String> callback);
	
	void applicationList(
			String page,
			String size,
			String sortBy,
			String sortOrder,
			String filter,
			Callback<ApplicationList> callback);
	
	void applicationListUnpaginated(Callback<ApplicationList> callback);
		
	void application(String id, Callback<Application> callback);
	
	void users(String user, Callback<String> callback);
	
	void sendFile(String fileName, Callback<String> callback);
	
	void customizationCreate(String applicationId, String xmlFileBase64Encoded, Callback<String> callback);
	
}
