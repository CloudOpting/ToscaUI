package test;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.NodeTemplateType;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.NodeType;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.Operation;
import eu.cloudopting.ui.ToscaUI.server.model.SLA;
import eu.cloudopting.ui.ToscaUI.utils.IOUtils;

/**
 * 
 * @author xeviscc
 *
 */
public class ToscaManagerTest {

	public static void main(String[] args) throws Exception {
		/*
		 * VARIABLES
		 */
		String definition = "Clearo";
		String serviceTemplate = "Clearo";
		String interfaceName = "http://tempuri.org";
		String slaID = "BigCity";
		String toscaFile = IOUtils.readFile("C:\\Users\\a591584\\Desktop\\CloudOpting\\TOSCA_ClearoExample.xml", Charset.defaultCharset());
		String jsonTemplate = "{ \"tosca\" : \"" + StringEscapeUtils.escapeJavaScript(toscaFile) + "\"}";
		
		/*
		 * TESTING CODE
		 */
		//CREATE TOSCA MANAGER
		ToscaManager toscaManager = new ToscaManager();
		
		//GET AN OBJECT JSON THROUGHT THE JSON STRING
		JSONObject jsonObject = new JSONObject(jsonTemplate);
		
		//UNESCAPE IT FROM JAVASCRIPT
		String toscaFileFromJson = StringEscapeUtils.unescapeJavaScript(jsonObject.getString("tosca"));
		
		toscaManager.setTosca(toscaFileFromJson);

		toscaManager.getDocumentationFromOperation(definition, NodeType.VMhost, interfaceName, Operation.Install);
//				toscaUtil.getDocumentationFromOperation(definition, NodeType.Apache, interfaceName, Operation.Install);
//				toscaUtil.getDocumentationFromOperation(definition, NodeType.DockerContainer, interfaceName, Operation.Install);
//				toscaUtil.getDocumentationFromOperation(definition, NodeType.ApacheVirtualHost, interfaceName, Operation.Install);

//				toscaUtil.getHostName(definition, serviceTemplate, NodeTemplate.VMhost);
//				toscaUtil.getHostName(definition, serviceTemplate, NodeTemplate.Apache);
//				toscaUtil.getHostName(definition, serviceTemplate, NodeTemplate.DockerContainer);
		toscaManager.getVHostName(definition, serviceTemplate, NodeTemplateType.ApacheVirtualHost);
		toscaManager.setVHostName("HOSTNAME_TEST", definition, serviceTemplate, NodeTemplateType.ApacheVirtualHost);
		toscaManager.getVHostName(definition, serviceTemplate, NodeTemplateType.ApacheVirtualHost);
		toscaManager.getInputParametersNeeded(definition, NodeType.VMhost, Operation.Install);
		toscaManager.getSlaAvaliable(definition, serviceTemplate, NodeTemplateType.VMhost);
		
		System.out.println("-------------");

		toscaManager.getInputParameter(definition,  NodeType.VMhost, Operation.Install, "co:SLA");
		toscaManager.setInputParameter(definition,  NodeType.VMhost, Operation.Install, "co:SLA", "HOLA");
		toscaManager.getInputParameter(definition,  NodeType.VMhost, Operation.Install, "co:SLA");
		

		SLA sla = toscaManager.getSLA(definition, serviceTemplate, NodeTemplateType.VMhost, slaID);
//				toscaUtil.getSLA(definition, serviceTemplate, NodeTemplate.Apache, slaID);
//				toscaUtil.getSLA(definition, serviceTemplate, NodeTemplate.ApacheVirtualHost, slaID);
//				toscaUtil.getSLA(definition, serviceTemplate, NodeTemplate.DockerContainer, slaID);
		System.out.println(sla);

		//CREATE JSON WITH THE NEW TOSCA FILE
		String jsonCustomized = "{ \"tosca\" : \"" + StringEscapeUtils.escapeJavaScript(toscaManager.getTosca()) + "\"}";
		
		System.out.println("Template: \n" + jsonTemplate );
		System.out.println("Customized: \n" +  jsonCustomized );
	}
}
