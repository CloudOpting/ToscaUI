package eu.cloudopting.ui.ToscaUI.client.remote.impl;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;

@TargetRestService("toscaProviderService")
public interface ToscaProviderService extends RestProxy{
	
	void getToscaFile(Callback<Object> callback);

	void setToscaFile(Object object, Callback<Object> callback);
}
