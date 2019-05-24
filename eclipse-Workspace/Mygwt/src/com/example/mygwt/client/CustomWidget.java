package com.example.mygwt.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class CustomWidget extends Composite{
	
	 private TextBox textBox = new TextBox();
	 private String value;
	 
	 HorizontalPanel panel = new HorizontalPanel();
	public CustomWidget() {
		panel.add(textBox);
		initWidget(panel);
		textBox.setText("Pranav");
	
		value=textBox.getText().toString();
		System.out.print(value);
	}
	
	
	 
}
