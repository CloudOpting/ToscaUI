package eu.cloudopting.ui.ToscaUI.server.model;

import java.util.HashMap;
import java.util.Map;

public class UserId {

	private String createdBy;
	private Long createdDate;
	private String lastModifiedBy;
	private Long lastModifiedDate;
	private Integer id;
	private String login;
	private String firstName;
	private String lastName;
	private String email;
	private Boolean activated;
	private String langKey;
	private String activationKey;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * 
	 * @param createdBy
	 * The createdBy
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * 
	 * @return
	 * The createdDate
	 */
	public Long getCreatedDate() {
		return createdDate;
	}

	/**
	 * 
	 * @param createdDate
	 * The createdDate
	 */
	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 
	 * @return
	 * The lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * 
	 * @param lastModifiedBy
	 * The lastModifiedBy
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * 
	 * @return
	 * The lastModifiedDate
	 */
	public Long getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * 
	 * @param lastModifiedDate
	 * The lastModifiedDate
	 */
	public void setLastModifiedDate(Long lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

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
	 * The login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * 
	 * @param login
	 * The login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * 
	 * @return
	 * The firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName
	 * The firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 
	 * @return
	 * The lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName
	 * The lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 
	 * @return
	 * The email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 * The email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return
	 * The activated
	 */
	public Boolean getActivated() {
		return activated;
	}

	/**
	 * 
	 * @param activated
	 * The activated
	 */
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	/**
	 * 
	 * @return
	 * The langKey
	 */
	public String getLangKey() {
		return langKey;
	}

	/**
	 * 
	 * @param langKey
	 * The langKey
	 */
	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	/**
	 * 
	 * @return
	 * The activationKey
	 */
	public String getActivationKey() {
		return activationKey;
	}

	/**
	 * 
	 * @param activationKey
	 * The activationKey
	 */
	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
