package eu.cloudopting.ui.ToscaUI.server.model;

import java.io.Serializable;

public class RowDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6369298529119791729L;
	
	private String idApplication;
	private String idCustomization;
	private String instance;
	private String status;
	
	public RowDTO(){}
	
	public RowDTO(String instance, String status){
		this.instance = instance;
		this.status = status;
	}
	
	public RowDTO(String idApplication, String idCustomization, String instance, String status){
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
}
