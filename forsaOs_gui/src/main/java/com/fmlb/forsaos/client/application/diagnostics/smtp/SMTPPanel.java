package com.fmlb.forsaos.client.application.diagnostics.smtp;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.ui.MaterialPanel;

public class SMTPPanel extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private DestinationDataTable destinationDataTable;

	private SMTPConfigurationDataTable smtpConfigDataTable;

	AppResources resources = GWT.create( AppResources.class );

	public SMTPPanel( UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.placeManager = placeManager;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		add( SMTPConfigDatatable() );
		add( SMTPAlertsDestinationTable() );

	}

	private MaterialPanel SMTPConfigDatatable()
	{

		smtpConfigDataTable = new SMTPConfigurationDataTable( this.uiComponentsUtil, placeManager, currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				// TODO Auto-generated method stub

			}
		} );

		return smtpConfigDataTable;

	}

	private MaterialPanel SMTPAlertsDestinationTable()
	{
		destinationDataTable = new DestinationDataTable( this.uiComponentsUtil, placeManager, currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				// TODO Auto-generated method stub

			}
		} );

		return destinationDataTable;

	}

	public void updateData()
	{
		smtpConfigDataTable.getUpdateIcommand().execute();
		destinationDataTable.getUpdateIcommand().execute();
	}
}
