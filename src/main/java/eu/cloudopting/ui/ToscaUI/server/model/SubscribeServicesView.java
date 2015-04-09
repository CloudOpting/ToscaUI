package eu.cloudopting.ui.ToscaUI.server.model;

import java.util.ArrayList;
import java.util.List;

public class SubscribeServicesView {

	private List<String> listItems;
	private List<String> listOSs;
	private List<String> listCSSs;
	private List<String> listCPUs;
	private List<SLA> listSLAs;
	
	public List<String> getListItems() {
		if (listItems==null) listItems = new ArrayList<String>();
		return listItems;
	}
	public void setListItems(List<String> listItems) {
		this.listItems = listItems;
	}
	public List<String> getListOSs() {
		if (listOSs==null) listOSs = new ArrayList<String>();
		return listOSs;
	}
	public void setListOSs(List<String> listOSs) {
		this.listOSs = listOSs;
	}
	public List<String> getListCSSs() {
		if (listCSSs==null) listCSSs = new ArrayList<String>();
		return listCSSs;
	}
	public void setListCSSs(List<String> listCSSs) {
		this.listCSSs = listCSSs;
	}
	public List<String> getListCPUs() {
		if (listCPUs==null) listCPUs = new ArrayList<String>();
		return listCPUs;
	}
	public void setListCPUs(List<String> listCPUs) {
		this.listCPUs = listCPUs;
	}
	public List<SLA> getListSLAs() {
		if (listSLAs==null) listSLAs = new ArrayList<SLA>();
		return listSLAs;
	}
	public void setListSLAs(List<SLA> listSLAs) {
		this.listSLAs = listSLAs;
	}
	
	
}
