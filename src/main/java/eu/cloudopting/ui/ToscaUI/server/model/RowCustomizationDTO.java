package eu.cloudopting.ui.ToscaUI.server.model;

import java.io.Serializable;

public class RowCustomizationDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6369298529119791729L;
	
	private String idApplication;
	private String idCustomization;
	private String applicationName;
	private String instance;
	private String status;
	
	public RowCustomizationDTO(){}
	
	public RowCustomizationDTO(String instance, String status){
		this.instance = instance;
		this.status = status;
	}
	
	public RowCustomizationDTO(String idApplication, String idCustomization, String applicationName, String instance, String status){
		this.applicationName = applicationName;
		this.idApplication = idApplication;
		this.idCustomization = idCustomization;
		this.instance = instance;
		this.status = status;
	}
	

	public String getIdApplication() {
		return idApplication;
	}

	public void setIdApplication(String idApplication) {
		this.idApplication = idApplication;
	}

	public String getIdCustomization() {
		return idCustomization;
	}

	public void setIdCustomization(String idCustomization) {
		this.idCustomization = idCustomization;
	}

	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}	
}
