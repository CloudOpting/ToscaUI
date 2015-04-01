package eu.cloudopting.ui.ToscaUI.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Content {

	private Integer id;
	private List<Object> applicationMedias = new ArrayList<Object>();
	private List<Object> customizationss = new ArrayList<Object>();
	private StatusId statusId;
	private UserId userId;
	private String applicationName;
	private String applicationDescription;
	private String applicationToscaTemplate;
	private String applicationVersion;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 * The id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 * The applicationMedias
	 */
	public List<Object> getApplicationMedias() {
		return applicationMedias;
	}

	/**
	 * 
	 * @param applicationMedias
	 * The applicationMedias
	 */
	public void setApplicationMedias(List<Object> applicationMedias) {
		this.applicationMedias = applicationMedias;
	}

	/**
	 * 
	 * @return
	 * The customizationss
	 */
	public List<Object> getCustomizationss() {
		return customizationss;
	}

	/**
	 * 
	 * @param customizationss
	 * The customizationss
	 */
	public void setCustomizationss(List<Object> customizationss) {
		this.customizationss = customizationss;
	}

	/**
	 * 
	 * @return
	 * The statusId
	 */
	public StatusId getStatusId() {
		return statusId;
	}

	/**
	 * 
	 * @param statusId
	 * The statusId
	 */
	public void setStatusId(StatusId statusId) {
		this.statusId = statusId;
	}

	/**
	 * 
	 * @return
	 * The userId
	 */
	public UserId getUserId() {
		return userId;
	}

	/**
	 * 
	 * @param userId
	 * The userId
	 */
	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * @return
	 * The applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * 
	 * @param applicationName
	 * The applicationName
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * 
	 * @return
	 * The applicationDescription
	 */
	public String getApplicationDescription() {
		return applicationDescription;
	}

	/**
	 * 
	 * @param applicationDescription
	 * The applicationDescription
	 */
	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}

	/**
	 * 
	 * @return
	 * The applicationToscaTemplate
	 */
	public String getApplicationToscaTemplate() {
		return applicationToscaTemplate;
	}

	/**
	 * 
	 * @param applicationToscaTemplate
	 * The applicationToscaTemplate
	 */
	public void setApplicationToscaTemplate(String applicationToscaTemplate) {
		this.applicationToscaTemplate = applicationToscaTemplate;
	}

	/**
	 * 
	 * @return
	 * The applicationVersion
	 */
	public String getApplicationVersion() {
		return applicationVersion;
	}

	/**
	 * 
	 * @param applicationVersion
	 * The applicationVersion
	 */
	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}








