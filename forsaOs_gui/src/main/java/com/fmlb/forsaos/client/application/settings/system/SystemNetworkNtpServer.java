package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.incubator.client.alert.Alert;


public class SystemNetworkNtpServer extends MaterialPanel
{


	private UIComponentsUtil uiComponentsUtil;
	
   private PlaceManager placeManager;
	
	private CurrentUser currentUser;
	
	 private MaterialTextBox ntpAddress ;
	 
	 AppResources resources = GWT.create( AppResources.class );
	 
  
	public SystemNetworkNtpServer(UIComponentsUtil uiComponentsUtil,PlaceManager placeManager, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		add(createNtpServerPanel());
	}
	
	
	private MaterialPanel createNtpServerPanel()
	{
		MaterialPanel ntpPanel = new MaterialPanel();
		MaterialLabel ntpLabel = this.uiComponentsUtil.getLabel( "System NTP Server", "s12", resources.style().vm_setting_header() );
		ntpPanel.add( ntpLabel );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( resources.style().vm_setting_row() );

		MaterialPanel dnsBoxwrapper =new MaterialPanel();
		dnsBoxwrapper.setGrid( "s6" );
		
        ntpAddress = uiComponentsUtil.getTexBox( "", "Enter NTP Server Address", "s6" );
 
		firstRow.add( dnsBoxwrapper );
		firstRow.add( ntpAddress);

		
		MaterialButton updateNtpbtn = new MaterialButton("Update NTP Server");
		updateNtpbtn.setWaves(WavesType.DEFAULT);
		updateNtpbtn.setTitle( "Update NTP Server" );
		updateNtpbtn.setMargin(19);
	
		updateNtpbtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				
			}
		} );

		firstRow.add(updateNtpbtn );
		ntpPanel.add( firstRow );
		return ntpPanel;
	}
}
