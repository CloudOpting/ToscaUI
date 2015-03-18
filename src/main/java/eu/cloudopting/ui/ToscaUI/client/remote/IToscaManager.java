package eu.cloudopting.ui.ToscaUI.client.remote;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;

import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.NodeTemplateType;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.NodeTypeName;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.OperationName;

/**
 * 
 * @author xeviscc
 *
 */
@TargetRestService("toscaManagerService")
public interface IToscaManager extends RestProxy {
	
	void getTosca(Callback<String> callback);
	
	void setTosca(String toscaXml, Callback<String> callback);

	void getDocumentationFromOperation(String definition, NodeTypeName nodeType,
			String interfaceName, OperationName operation, Callback<String> callback);

	void getVHostName(String definition, String serviceTemplate,
			NodeTemplateType nodeTemplateType, Callback<String> callback);

	void setVHostName(String vHostName, String definition,
			String serviceTemplate, NodeTemplateType nodeTemplateType, Callback<String> callback);

	void getSLA(String definition, String serviceTemplate,
			NodeTemplateType nodeTemplateType, String slaID, Callback<String> callback);

	void getSlaAvaliable(String definition, String serviceTemplate,
			NodeTemplateType nodeTemplateType, Callback<String> callback);

	void getInputParametersNeeded(String definition, NodeTypeName nodeType,
			OperationName operation, Callback<String> callback);

	void getInputParameter(String definition, NodeTypeName nodeType,
			OperationName operation, String inputParameterId, Callback<String> callback);

	void setInputParameter(String definition, NodeTypeName nodeType,
			OperationName operation, String inputParameterId, String value, Callback<String> callback);
	
}
