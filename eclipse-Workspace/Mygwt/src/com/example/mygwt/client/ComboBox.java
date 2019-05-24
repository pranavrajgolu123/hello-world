package com.example.mygwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;

public class ComboBox  extends DialogBox implements ClickHandler {  
    public ComboBox() {  
        super(true);  
        setText("Sample DialogBox");  
  
        Button closeButton = new Button("Close", this);  
        HTML msg = new HTML("A Custom dialog box.",true);  
  
        DockPanel dock = new DockPanel();  
        dock.setSpacing(6);  
        Image image = new Image();  
        image.setUrl("/images/gwt-dialogbox1");  
        dock.add(image, DockPanel.CENTER);  
        dock.add(closeButton, DockPanel.SOUTH);  
        dock.add(msg, DockPanel.NORTH);  
  
        dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_CENTER);  
        dock.setWidth("100%");  
        setWidget(dock);  
    }  
  
    @Override  
    public void onClick(ClickEvent event) {  
        hide();  
    }  
}  
