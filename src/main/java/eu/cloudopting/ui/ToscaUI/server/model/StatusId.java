package eu.cloudopting.ui.ToscaUI.server.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

public class StatusId {

	private Integer id;
	private Object applicationss;
	private Object customizationss;
	private String status;
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
	 * The applicationss
	 */
	public Object getApplicationss() {
		return applicationss;
	}

	/**
	 * 
	 * @param applicationss
	 * The applicationss
	 */
	public void setApplicationss(Object applicationss) {
		this.applicationss = applicationss;
	}

	/**
	 * 
	 * @return
	 * The customizationss
	 */
	public Object getCustomizationss() {
		return customizationss;
	}

	/**
	 * 
	 * @param customizationss
	 * The customizationss
	 */
	public void setCustomizationss(Object customizationss) {
		this.customizationss = customizationss;
	}

	/**
	 * 
	 * @return
	 * The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 * The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
