package com.fmlb.forsaos.client.application.settings.accounts;

import java.util.Date;

import com.fmlb.forsaos.client.application.common.DatePicker;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LoginHistoryModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.json.client.JSONObject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;

public class LoginTabContent extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private PlaceManager placeManager;

	private MaterialDataTable<LoginHistoryModel> materialDataTable;

	private MaterialDataPager<LoginHistoryModel> pager;

	private ListDataSource<LoginHistoryModel> dataSource;

	private MaterialButton printBtn;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private MaterialRow materialRow = new MaterialRow();

	private LoginHistoryDataTable loginHistoryDataTable;

	private DatePicker fromDatePicker;

	private DatePicker toDatePicker;

	private LoginHistorySearchModel loginHistorySearchModel;

	private MaterialTextBox userNameTextBox;

	AppResources resources = GWT.create( AppResources.class );

	public LoginTabContent( UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{

		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		loginHistoryDataTable = new LoginHistoryDataTable( uiComponentsUtil, currentUser, navigationCmd );
		loginHistoryDataTable.getUpdateIcommandWithData().executeWithData( null );
		//add( searchLoginTable() );
		add( loginHistoryDataTable );
	}

	public MaterialPanel searchLoginTable()
	{
		MaterialPanel loginSearchPanel = new MaterialPanel();

		MaterialLabel searchLabel = this.uiComponentsUtil.getLabel( "Search Login History", "s12", resources.style().clone_Detail() );

		MaterialRow loginSearchPanelRow = new MaterialRow();
		loginSearchPanelRow.setGrid( "s12" );

		MaterialButton searchBtn = new MaterialButton( "Search" );
		searchBtn.setGrid( "s2" );

		searchBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				performSearch();

			}
		} );

		fromDatePicker = new DatePicker( "From", "s4" );
		toDatePicker = new DatePicker( "To", "s4" );

		fromDatePicker.getMaterialDatePicker().addValueChangeHandler( new ValueChangeHandler<Date>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<Date> event )
			{
				LoggerUtil.log( event.getValue().toString() );
				toDatePicker.getMaterialDatePicker().setDateMin( event.getValue() );

			}
		} );

		userNameTextBox = this.uiComponentsUtil.getTexBox( "User Name", "", "s2" );

		loginSearchPanelRow.add( fromDatePicker );
		loginSearchPanelRow.add( toDatePicker );
		loginSearchPanelRow.add( userNameTextBox );
		loginSearchPanelRow.add( searchBtn );

		loginSearchPanel.add( searchLabel );
		loginSearchPanel.add( loginSearchPanelRow );

		return loginSearchPanel;

	}

	public LoginHistoryDataTable getLoginHistoryDataTable()
	{
		return loginHistoryDataTable;
	}

	private void performSearch()
	{
		JSONObject queryObj = new JSONObject();
		if ( userNameTextBox.getValue().trim().isEmpty() && ( fromDatePicker.getMaterialDatePicker().getValue() == null || toDatePicker.getMaterialDatePicker().getValue() == null ) )
		{
			return;
		}
		if ( fromDatePicker.getMaterialDatePicker().getValue() != null && toDatePicker.getMaterialDatePicker().getValue() != null )
		{
			queryObj.put( "timestamp", RestCallUtil.getJSONNumber( new Double( toDatePicker.getMaterialDatePicker().getValue().getTime() ) ) );
		}
		if ( !userNameTextBox.getValue().trim().isEmpty() )
		{
			queryObj.put( "name", RestCallUtil.getJSONString( userNameTextBox.getValue() ) );

		}

		LoggerUtil.log( RestCallUtil.getQueryRequest( queryObj.toString() ) );
		getLoginHistoryDataTable().getUpdateIcommandWithData().executeWithData( RestCallUtil.getQueryRequest( queryObj.toString() ) );
	}

}
