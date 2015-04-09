package eu.cloudopting.ui.ToscaUI.server.model;

import java.util.HashMap;
import java.util.Map;

public class Sort {

	private String direction;
	private String property;
	private Boolean ignoreCase;
	private String nullHandling;
	private Boolean ascending;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * 
	 * @param direction
	 * The direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * 
	 * @return
	 * The property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * 
	 * @param property
	 * The property
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 
	 * @return
	 * The ignoreCase
	 */
	public Boolean getIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * 
	 * @param ignoreCase
	 * The ignoreCase
	 */
	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	/**
	 * 
	 * @return
	 * The nullHandling
	 */
	public String getNullHandling() {
		return nullHandling;
	}

	/**
	 * 
	 * @param nullHandling
	 * The nullHandling
	 */
	public void setNullHandling(String nullHandling) {
		this.nullHandling = nullHandling;
	}

	/**
	 * 
	 * @return
	 * The ascending
	 */
	public Boolean getAscending() {
		return ascending;
	}

	/**
	 * 
	 * @param ascending
	 * The ascending
	 */
	public void setAscending(Boolean ascending) {
		this.ascending = ascending;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
