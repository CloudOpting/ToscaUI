package eu.cloudopting.ui.ToscaUI.client.remote.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xml.dtm.ref.DTMNodeList;
import org.cruxframework.crux.core.server.rest.annotation.RestService;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.POST;
import org.cruxframework.crux.core.shared.rest.annotation.Path;
import org.cruxframework.crux.core.shared.rest.annotation.QueryParam;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import eu.cloudopting.ui.ToscaUI.server.model.SLA;
import eu.cloudopting.ui.ToscaUI.utils.UniversalNamespaceResolver;

/**
 * 
 * @author xeviscc
 *
 */
@RestService("toscaManagerService")
@Path("toscaManager")
public class ToscaManager {

	private Document document;
	private static XPath xpath;

	/**
	 * [VMhost, DockerContainer, Apache, ApacheVirtualHost]
	 */
	public enum NodeType {
		VMhost,
		DockerContainer,
		Apache,
		ApacheVirtualHost
	}
	/**
	 * [VMhost, DockerContainer, Apache, ApacheVirtualHost]
	 */
	public enum NodeTemplateType {
		VMhost,
		DockerContainer,
		Apache,
		ApacheVirtualHost
	}
	/**
	 * [Install, Deploy]
	 */
	public enum Operation {
		Install,
		Deploy
	}

	//** PREPARED QUERIES **//

	/**
	 * GET_DOCUMENTATION_FROM_OPERATION prepared query needs 4 parameters to work. 
	 * 
	 * @param DefinitionId 
	 * @param NodeTypeName {@link NodeType}
	 * @param InterfaceName
	 * @param OperationName {@link Operation}
	 */
	private static String GET_DOCUMENTATION_FROM_OPERATION = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface[@name=\"%s\"]"
			+ "/Operation[@name=\"%s\"]"
			+ "/documentation";

	/**
	 * GET_VHOSTNAME prepared query needs 3 parameters to work. 
	 * 
	 * @param DefinitionId 
	 * @param ServiceTemplateId
	 * @param NodeTemplateType {@link NodeTemplateType}
	 */
	private static String GET_VHOSTNAME = "/Definitions[@id=\"%s\"]"
			+ "/ServiceTemplate[@id=\"%s\"]"
			+ "/TopologyTemplate"
			+ "/NodeTemplate[@type=\"%s\"]"
			+ "/Properties"
			+ "/ApacheVirtualHostproperties"
			+ "/VHostName";

	/**
	 * GET_SLA prepared query needs 4 parameters to work. 
	 * 
	 * @param DefinitionId 
	 * @param ServiceTemplateId
	 * @param NodeTemplateType {@link NodeTemplateType}
	 * @param SLAid
	 */
	private static String GET_SLA = "/Definitions[@id=\"%s\"]"
			+ "/ServiceTemplate[@id=\"%s\"]"
			+ "/TopologyTemplate"
			+ "/NodeTemplate[@type=\"%s\"]"
			+ "/Properties"
			+ "/VMhostProperties"
			+ "/SLAsProperties"
			+ "/SLA[@id=\"%s\"]";
	
	/**
	 * GET_INPUT_PARAMETERS_NEEDED prepared query needs 3 parameters to work. 
	 * 
	 * @param DefinitionId 
	 * @param NodeTypeName {@link NodeType}
	 * @param OperationName {@link Operation}
	 */
	private static String GET_INPUT_PARAMETERS_NEEDED = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface"
			+ "/Operation[@name=\"%s\"]"
			+ "/InputParameters"
			+ "/InputParameter"
			+ "/@type";
	
	/**
	 * Gets the Parameter for a specific id.
	 * 
	 * GET_INPUT_PARAMETER prepared query needs 4 parameters to work. 
	 * 
	 * @param DefinitionId 
	 * @param NodeTypeName {@link NodeType}
	 * @param OperationName {@link Operation}
	 * @param InputParameterId
	 */
	private static String GET_INPUT_PARAMETER = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface"
			+ "/Operation[@name=\"%s\"]"
			+ "/InputParameters"
			+ "/InputParameter[@type=\"%s\"]";
	
	/**
	 * GET_SLA_AVALIABLE prepared query needs 3 parameters to work. 
	 * 
	 * @param DefinitionId 
	 * @param ServiceTemplate
	 * @param NodeTemplateType {@link NodeTemplateType}
	 */
	private static String GET_SLA_AVALIABLE = "/Definitions[@id=\"%s\"]"
			+ "/ServiceTemplate[@id=\"%s\"]"
			+ "/TopologyTemplate"
			+ "/NodeTemplate[@type=\"%s\"]"
			+ "/Properties"
			+ "/VMhostProperties"
			+ "/SLAsProperties"
			+ "/SLA"
			+ "/@id";

	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#setTosca(java.lang.String)
	 */
	@POST
	@Path("/")
	public void setTosca(@QueryParam("toscaXml") String toscaXml) {
		try {
			//Parse the String to a Document
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
			document = db.parse(new InputSource(new StringReader(toscaXml)));
			//Initialize the xpath
			xpath = XPathFactory.newInstance().newXPath();
			//Set the needed namespaces
			xpath.setNamespaceContext(new UniversalNamespaceResolver(document, false));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#getTosca()
	 */
	@GET
	@Path("/")
	public String getTosca() {
		String result = "";
		try {
			
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	 
	        StringWriter sw = new StringWriter();
	        transformer.transform(new DOMSource(document), new StreamResult(sw));
	        
	        result = sw.toString();
	        
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return result;
	}

	//** EXPOSED QUERIES **//

	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#getDocumentationFromOperation(java.lang.String, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.NodeType, java.lang.String, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.Operation)
	 */
	@GET
	@Path("/getDocumentationFromOperation")
	public String getDocumentationFromOperation(
			@QueryParam("definition") String definition, 
			@QueryParam("nodeType") NodeType nodeType, 
			@QueryParam("interfaceName") String interfaceName, 
			@QueryParam("operation") Operation operation) 
	throws XPathExpressionException
	{
		return evaluateSingleValue(String.format(GET_DOCUMENTATION_FROM_OPERATION, definition, nodeType, interfaceName, operation));
	}

	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#getVHostName(java.lang.String, java.lang.String, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.NodeTemplateType)
	 */
	@GET
	@Path("/getVHostName")
	public String getVHostName(
			@QueryParam("definition") String definition, 
			@QueryParam("serviceTemplate") String serviceTemplate, 
			@QueryParam("nodeTemplateType") NodeTemplateType nodeTemplateType) 
	throws XPathExpressionException 
	{
		String result = evaluateSingleValue(String.format(GET_VHOSTNAME, definition, serviceTemplate, nodeTemplateType));
		return result;
	}
	
	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#setVHostName(java.lang.String, java.lang.String, java.lang.String, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.NodeTemplateType)
	 */
	@POST
	@Path("/setVHostName")
	public void setVHostName(
			@QueryParam("vHostName") String vHostName,
			@QueryParam("definition") String definition, 
			@QueryParam("serviceTemplate") String serviceTemplate, 
			@QueryParam("nodeTemplateType") NodeTemplateType nodeTemplateType) 
	throws XPathExpressionException 
	{
		insertSingleValue(String.format(GET_VHOSTNAME, definition, serviceTemplate, nodeTemplateType), vHostName);
	}

	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#getSLA(java.lang.String, java.lang.String, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.NodeTemplateType, java.lang.String)
	 */
	@GET
	@Path("/getSLA")
	public SLA getSLA(
			@QueryParam("definition") String definition, 
			@QueryParam("serviceTemplate") String serviceTemplate, 
			@QueryParam("nodeTemplateType") NodeTemplateType nodeTemplateType,
			@QueryParam("slaID") String slaID) 
	throws XPathExpressionException 
	{
		SLA sla = new SLA();
		String expression = String.format(GET_SLA, definition, serviceTemplate, nodeTemplateType, slaID);

		sla.numCpus = evaluateSingleValue(expression + "/NumCpus");
		sla.memory = evaluateSingleValue(expression + "/Memory");
		sla.price = evaluateSingleValue(expression + "/Price");
		sla.disk = evaluateSingleValue(expression + "/Disk");
		sla.chosen = evaluateSingleValue(expression + "/Chosen");

		return sla;
	}

	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#getSlaAvaliable(java.lang.String, java.lang.String, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.NodeTemplateType)
	 */
	@GET
	@Path("/getSlaAvaliable")
	public String getSlaAvaliable(
			@QueryParam("definition") String definition, 
			@QueryParam("serviceTemplate") String serviceTemplate, 
			@QueryParam("nodeTemplateType") NodeTemplateType nodeTemplateType) 
	throws XPathExpressionException 
	{
		return evaluateMultipleValues(String.format(GET_SLA_AVALIABLE, definition, serviceTemplate, nodeTemplateType));
	}
	
	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#getInputParametersNeeded(java.lang.String, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.NodeType, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.Operation)
	 */
	@GET
	@Path("/getInputParametersNeeded")
	public String getInputParametersNeeded(
			@QueryParam("definition") String definition, 
			@QueryParam("nodeType") NodeType nodeType, 
			@QueryParam("operation") Operation operation) 
	throws XPathExpressionException 
	{
		return evaluateSingleValue(String.format(GET_INPUT_PARAMETERS_NEEDED, definition, nodeType, operation));
	}

	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#getInputParameter(java.lang.String, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.NodeType, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.Operation, java.lang.String)
	 */
	@GET
	@Path("/getInputParameter")
	public String getInputParameter(
			@QueryParam("definition") String definition, 
			@QueryParam("nodeType") NodeType nodeType, 
			@QueryParam("operation") Operation operation,
			@QueryParam("inputParameterId") String inputParameterId) 
	throws XPathExpressionException 
	{
		return evaluateSingleValue(String.format(GET_INPUT_PARAMETER, definition, nodeType, operation, inputParameterId));
	}
	
	/* (non-Javadoc)
	 * @see eu.cloudopting.ui.ToscaUI.client.remote.impl.TEST#setInputParameter(java.lang.String, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.NodeType, eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaUtil.Operation, java.lang.String, java.lang.String)
	 */
	@POST
	@Path("/setInputParameter")
	public void setInputParameter(
			@QueryParam("definition") String definition, 
			@QueryParam("nodeType") NodeType nodeType, 
			@QueryParam("operation") Operation operation,
			@QueryParam("inputParameterId") String inputParameterId,
			@QueryParam("value") String value) 
	throws XPathExpressionException 
	{
		insertSingleValue(String.format(GET_INPUT_PARAMETER, definition, nodeType, operation, inputParameterId), value);
	}

	
	/*
	 * TOSCA UTILS
	 */
	
	/**
	 * Evaluates the expression and inserts the value in the Node.
	 * 
	 * @param expression XPath expression where the value has to be inserted.
	 * @param value Value that has to be inserted.
	 * @throws XPathExpressionException
	 */
	private void insertSingleValue(String expression, String value) throws XPathExpressionException {
		if(value != null) {
			Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
			node.setTextContent(value);
		}
	}

	/**
	 * Evaluates a XPath expression against the embedded document.
	 * @param expression XPath expression to be evaluated against the document
	 * @return String with the result of the evaluation
	 * @throws XPathExpressionException
	 */
	private String evaluateSingleValue(String expression) throws XPathExpressionException {
		System.out.println(expression);
		String result = (String) xpath.evaluate(expression, document, XPathConstants.STRING);
//		if(result.isEmpty()) {
//			Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
//			result = nodeToString(node);
//		}
		System.out.println(result.trim());
		return result.trim();
	}

	/**
	 * Evaluates a XPath expression against the embedded document.
	 * @param expression XPath expression to be evaluated against the document
	 * @return String with the results of the evaluation separated by comma (,)
	 * @throws XPathExpressionException
	 */
	private String evaluateMultipleValues(String expression) throws XPathExpressionException {
		System.out.println(expression);
		DTMNodeList nodeList = (DTMNodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
		String result = "";
		for(int i = 0; i < nodeList.getLength(); i++) {
			result += nodeList.item(i).getNodeValue() + ",";
		}
		System.out.println(result.trim());
		return result.trim();
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	private String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try { 
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.out.println("nodeToString Transformer Exception");
		} 
		return sw.toString();
	} 

}
