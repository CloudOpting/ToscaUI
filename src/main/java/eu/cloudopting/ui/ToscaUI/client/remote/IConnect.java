package eu.cloudopting.ui.ToscaUI.client.remote;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;

@TargetRestService("connectService")
public interface IConnect extends RestProxy {
	
	void connect(String user, String pass, Callback<String> callback);
	
	void applicationCreate(Callback<String> callback);
	
	void applicationList(
			String page,
			String size,
			String sortBy,
			String sortOrder,
			String filter,
			Callback<String> callback);
		
	void application(String id, Callback<String> callback);
	
	void getUser(String user, Callback<String> callback);


}
