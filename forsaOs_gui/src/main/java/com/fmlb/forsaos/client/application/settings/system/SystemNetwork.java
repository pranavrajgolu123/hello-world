package com.fmlb.forsaos.client.application.settings.system;

import java.util.Comparator;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import gwt.material.design.client.ui.MaterialPanel;



public class SystemNetwork extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;
	
	private PlaceManager placeManager;
	
	private CurrentUser currentUser;
	
	private IcommandWithData navigationCmd;

	
	AppResources resources = GWT.create( AppResources.class );
	
	public SystemNetwork(UIComponentsUtil uiComponentsUtil,PlaceManager placeManager, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		createDetailHeaderPanel();
		createDnsServerPanel();
		createNtpServerPanel();
	}
	
	
	
	private void createDetailHeaderPanel()
	{
      SystemNetworkRoutingTable srt = new SystemNetworkRoutingTable(uiComponentsUtil, placeManager, currentUser,navigationCmd);
      add(srt);
	}
	
	
	
	private void createDnsServerPanel()
	{
		
		SystemNetworkDnsServer systemNetworkDns = new SystemNetworkDnsServer(uiComponentsUtil, placeManager, currentUser);
		add(systemNetworkDns);
	}
	
	private void createNtpServerPanel()
	{
        SystemNetworkNtpServer systemNetworkNtp = new SystemNetworkNtpServer(uiComponentsUtil, placeManager, currentUser);
		add(systemNetworkNtp);
	}

}
