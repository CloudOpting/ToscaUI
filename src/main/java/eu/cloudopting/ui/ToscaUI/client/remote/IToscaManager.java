package eu.cloudopting.ui.ToscaUI.client.remote;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;

import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.NodeTemplateType;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.NodeType;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.Operation;

/**
 * 
 * @author xeviscc
 *
 */
@TargetRestService("toscaManagerService")
public interface IToscaManager extends RestProxy {
	
	void getTosca(Callback<String> callback);
	
	void setTosca(String toscaXml, Callback<String> callback);

	void getDocumentationFromOperation(String definition, NodeType nodeType,
			String interfaceName, Operation operation, Callback<String> callback);

	void getVHostName(String definition, String serviceTemplate,
			NodeTemplateType nodeTemplateType, Callback<String> callback);

	void setVHostName(String vHostName, String definition,
			String serviceTemplate, NodeTemplateType nodeTemplateType, Callback<String> callback);

	void getSLA(String definition, String serviceTemplate,
			NodeTemplateType nodeTemplateType, String slaID, Callback<String> callback);

	void getSlaAvaliable(String definition, String serviceTemplate,
			NodeTemplateType nodeTemplateType, Callback<String> callback);

	void getInputParametersNeeded(String definition, NodeType nodeType,
			Operation operation, Callback<String> callback);

	void getInputParameter(String definition, NodeType nodeType,
			Operation operation, String inputParameterId, Callback<String> callback);

	void setInputParameter(String definition, NodeType nodeType,
			Operation operation, String inputParameterId, String value, Callback<String> callback);
	
}
