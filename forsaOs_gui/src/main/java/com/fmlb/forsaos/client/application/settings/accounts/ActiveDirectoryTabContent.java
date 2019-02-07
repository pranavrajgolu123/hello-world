package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.ui.MaterialPanel;

public class ActiveDirectoryTabContent extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private ActiveDirectoryDataTable activeDirectoryDataTable;
	
	AppResources resources = GWT.create( AppResources.class );

	public ActiveDirectoryTabContent( UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{

		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		activeDirectoryDataTable=new ActiveDirectoryDataTable( uiComponentsUtil, currentUser );
		activeDirectoryDataTable.getUpdateIcommandWithData().executeWithData( false );
		add( activeDirectoryDataTable );

	}

	public ActiveDirectoryDataTable getActiveDirectoryDataTable()
	{
		return activeDirectoryDataTable;
	}
	
	

}
