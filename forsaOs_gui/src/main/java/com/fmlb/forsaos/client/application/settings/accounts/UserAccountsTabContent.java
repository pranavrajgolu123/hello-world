package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;

public class UserAccountsTabContent extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private LocalUserAccountDataTable localUserAccountDataTable;

	private MaterialPanel adminAccountPnl;

	AppResources resources = GWT.create( AppResources.class );

	public UserAccountsTabContent( UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
		initializeAdminAccountSetting();
		add( localUserAccountDataTable );
		//add( adminAccountPnl );
	}

	private void initializeDataTable()
	{
		localUserAccountDataTable = new LocalUserAccountDataTable( uiComponentsUtil, currentUser );
		localUserAccountDataTable.getUpdateIcommandWithData().executeWithData( null );
	}

	private void initializeAdminAccountSetting()
	{
		adminAccountPnl = new MaterialPanel();

		MaterialRow adminAccntHeaderRow = new MaterialRow();
		adminAccntHeaderRow.addStyleName( resources.style().lemDetail() );
		adminAccntHeaderRow.setGrid( "s12" );

		MaterialRow adminAccntDetailRow = new MaterialRow();
		adminAccntDetailRow.addStyleName( resources.style().lem_detail_row() );
		adminAccntDetailRow.setGrid( "s12" );

		MaterialLabel adminAccntSetngLbl = this.uiComponentsUtil.getLabel( "Admin Account Settings", "s12" );
		adminAccntSetngLbl.addStyleName( resources.style().detail_header() );
		adminAccntSetngLbl.setBorder( "2" );

		MaterialLabel lockoutLbl = this.uiComponentsUtil.getLabel( "LockOut: ", "" );
		lockoutLbl.setGrid( "s1" );

		MaterialSwitch statusSwitch = new MaterialSwitch();
		statusSwitch.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				statusSwitchAction();

			}
		} );
		statusSwitch.setGrid( "s1" );

		MaterialLabel spacerLbl = this.uiComponentsUtil.getLabel( "", "" );
		spacerLbl.setGrid( "s12" );

		MaterialLabel noticeLabel = this.uiComponentsUtil.getLabel( "Notice: ", "s1" );

		MaterialLabel noticeDesc = this.uiComponentsUtil.getLabel( " This feature will only lockout the DEFAULT \"admin\" account. To prevent a custom user from accessing the system, remove them from their user group, or delete their account.", "s11" );

		adminAccntHeaderRow.add( adminAccntSetngLbl );

		adminAccntDetailRow.add( lockoutLbl );
		adminAccntDetailRow.add( statusSwitch );
		adminAccntDetailRow.add( spacerLbl );
		adminAccntDetailRow.add( noticeLabel );
		adminAccntDetailRow.add( noticeDesc );
		adminAccountPnl.add( adminAccntHeaderRow );
		adminAccountPnl.add( adminAccntDetailRow );

	}

	public LocalUserAccountDataTable getLocalUserAccountDataTable()
	{
		return localUserAccountDataTable;
	}

	private void statusSwitchAction()
	{

	}

}
