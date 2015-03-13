package eu.cloudopting.ui.ToscaUI.client.controller;

import org.jgroups.protocols.FlowControl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;

public class Index implements EntryPoint, ValueChangeHandler<String> {

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
	}
//    public void onModuleLoad() {
//        History.addValueChangeHandler(this);
//        if (History.getToken().isEmpty()) History.newItem("index");
//        Composite c = new Login(); 
//        FlowControl.go(c);
//    }
//
//    public void onValueChange(ValueChangeEvent<String> e) {
//        FlowControl.go(History.getToken());
//    }
}
