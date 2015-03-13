package eu.cloudopting.ui.ToscaUI.client.remote;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;

@TargetRestService("otherService")
public interface IOther extends RestProxy {
	
	void applicationCreate(String jsonMap, Callback<String> callback);
	
	void applicationList(String jsonMap, Callback<String> callback);
		
	void application(String jsonMap, Callback<String> callback);
	
}
