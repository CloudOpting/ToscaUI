package eu.cloudopting.ui.ToscaUI.server.model;

import java.util.HashMap;
import java.util.Map;

public class Customizations {

	private Integer id;
	private StatusId statusId;
	private ApplicationId applicationId;
	private String customizationToscaFile;
	private String customizationCreation;
	private String customizationActivation;
	private String customizationDecommission;
	private String username;
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

	public ApplicationId getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(ApplicationId applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * 
	 * @return
	 * The customizationToscaFile
	 */
	public String getCustomizationToscaFile() {
		return customizationToscaFile;
	}

	/**
	 * 
	 * @param customizationToscaFile
	 * The customizationToscaFile
	 */
	public void setCustomizationToscaFile(String customizationToscaFile) {
		this.customizationToscaFile = customizationToscaFile;
	}

	/**
	 * 
	 * @return
	 * The customizationCreation
	 */
	public String getCustomizationCreation() {
		return customizationCreation;
	}

	/**
	 * 
	 * @param customizationCreation
	 * The customizationCreation
	 */
	public void setCustomizationCreation(String customizationCreation) {
		this.customizationCreation = customizationCreation;
	}

	/**
	 * 
	 * @return
	 * The customizationActivation
	 */
	public String getCustomizationActivation() {
		return customizationActivation;
	}

	/**
	 * 
	 * @param customizationActivation
	 * The customizationActivation
	 */
	public void setCustomizationActivation(String customizationActivation) {
		this.customizationActivation = customizationActivation;
	}

	/**
	 * 
	 * @return
	 * The customizationDecommission
	 */
	public String getCustomizationDecommission() {
		return customizationDecommission;
	}

	/**
	 * 
	 * @param customizationDecommission
	 * The customizationDecommission
	 */
	public void setCustomizationDecommission(String customizationDecommission) {
		this.customizationDecommission = customizationDecommission;
	}

	/**
	 * 
	 * @return
	 * The username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 * The username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
