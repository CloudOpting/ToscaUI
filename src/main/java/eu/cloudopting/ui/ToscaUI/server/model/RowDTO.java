package eu.cloudopting.ui.ToscaUI.server.model;

import java.io.Serializable;

public class RowDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6369298529119791729L;
	
	private String id;
	private String instance;
	private String status;
	
	public RowDTO(){}
	
	public RowDTO(String instance, String status){
		this.instance = instance;
		this.status = status;
	}
	
	public RowDTO(String id, String instance, String status){
		this.id = id;
		this.instance = instance;
		this.status = status;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
