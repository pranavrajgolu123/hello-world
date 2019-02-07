package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.AccountsModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Flex;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;

public class AccountsTabs extends MaterialPanel
{

	public UIComponentsUtil uiComponentsUtil;

	private AccountsModel accountModel;

	private CurrentUser currentUser;

	private IcommandWithData navigateCmd;

	//private UserAccountsTable userAccTab;

	private PlaceManager placeManager;

	public UserAccountsTabContent getUserAccTab()
	{
		return userAccTab;
	}

	public MyAccountTabContent getMyaccountTab()
	{
		return myaccountTab;
	}

	public UserGroupsTabContent getUserGroupTab()
	{
		return userGroupTab;
	}

	public PermissionsTable getPermissionTab()
	{
		return permissionTab;
	}

	public void setPermissionTab( PermissionsTable permissionTab )
	{
		this.permissionTab = permissionTab;
	}

	public LoginTabContent getLoginTab()
	{
		return loginTab;
	}

	public ActiveDirectoryTabContent getActiveDirTab()
	{
		return activeDirTab;
	}

	private UserAccountsTabContent userAccTab;

	private MyAccountTabContent myaccountTab;

	private UserGroupsTabContent userGroupTab;

	private PermissionsTable permissionTab;

	private LoginTabContent loginTab;

	private ActiveDirectoryTabContent activeDirTab;

	private MaterialTab accountConfTab;

	public AccountsTabs( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigateCmd )
	{

		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigateCmd = navigateCmd;

		add( createTabPanel() );

		myaccountTab = new MyAccountTabContent( this.uiComponentsUtil, this.currentUser, navigateCmd );
		myaccountTab.setId( "MyAccount" );

		userAccTab = new UserAccountsTabContent( this.uiComponentsUtil, null, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
			}
		} );
		userAccTab.setId( "UserAccount" );

		userGroupTab = new UserGroupsTabContent( this.uiComponentsUtil, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
				userAccTab.getLocalUserAccountDataTable().getUpdateIcommandWithData().executeWithData( null );
			}
		} );
		userGroupTab.setId( "UserGroups" );

		permissionTab = new PermissionsTable( this.uiComponentsUtil, null, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
				//MaterialLoader.loading( true );

			}
		} );
		permissionTab.setId( "Permissions" );

		activeDirTab = new ActiveDirectoryTabContent( this.uiComponentsUtil, null, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
			}
		} );
		activeDirTab.setId( "ActiveDirectory" );

		loginTab = new LoginTabContent( this.uiComponentsUtil, null, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
				//MaterialLoader.loading( true );
				//ShowAccountDetailsPresenterEvent.fire( AccountPresenter.this, ( UserAccountModel ) obj );

			}
		} );
		loginTab.setId( "LoginHistory" );

		MaterialRow tabContentRow = new MaterialRow();
		tabContentRow.add( myaccountTab );
		tabContentRow.add( userAccTab );
		tabContentRow.add( userGroupTab );
		tabContentRow.add( activeDirTab );
		//tabContentRow.add( permissionTab );
		tabContentRow.add( loginTab );
		add( tabContentRow );
	}

	AppResources resources = GWT.create( AppResources.class );

	private MaterialTab createTabPanel()
	{
		accountConfTab = new MaterialTab();
		accountConfTab.setBackgroundColor( Color.WHITE );
		accountConfTab.addStyleName( resources.style().account_tabs() );
		accountConfTab.setShadow( 1 );
		accountConfTab.setIndicatorColor( Color.TRANSPARENT );

		MaterialTabItem myAccountsTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		MaterialTabItem userAccountsTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		MaterialTabItem userGroupsTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		MaterialTabItem permissionsTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		MaterialTabItem activeDirectoryTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		MaterialTabItem loginHistoryTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );

		MaterialLink myAccountsTabLink = uiComponentsUtil.getTabLink( "My Account", "MyAccount", Color.BLACK );
		MaterialLink userAccountsTabLink = uiComponentsUtil.getTabLink( "User Account", "UserAccount", Color.BLACK );
		MaterialLink userGroupsTabLink = uiComponentsUtil.getTabLink( "User Groups", "UserGroups", Color.BLACK );
		MaterialLink permissionsTabLink = uiComponentsUtil.getTabLink( "Permissions", "Permissions", Color.BLACK );
		MaterialLink activeDirectoryTabLink = uiComponentsUtil.getTabLink( "Active Directory", "ActiveDirectory", Color.BLACK );
		MaterialLink loginHistoryTabLink = uiComponentsUtil.getTabLink( "Login History", "LoginHistory", Color.BLACK );

		myAccountsTab.add( myAccountsTabLink );
		userAccountsTab.add( userAccountsTabLink );
		userGroupsTab.add( userGroupsTabLink );
		permissionsTab.add( permissionsTabLink );
		activeDirectoryTab.add( activeDirectoryTabLink );
		loginHistoryTab.add( loginHistoryTabLink );

		myAccountsTab.setBackgroundColor( Color.BLUE );// by default selected
		myAccountsTabLink.setTextColor( Color.WHITE );
		myAccountsTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				myAccountsTab.setBackgroundColor( Color.BLUE );
				myAccountsTabLink.setTextColor( Color.WHITE );

				userAccountsTab.setBackgroundColor( Color.WHITE );
				userAccountsTabLink.setTextColor( Color.BLACK );

				userGroupsTab.setBackgroundColor( Color.WHITE );
				userGroupsTabLink.setTextColor( Color.BLACK );

				permissionsTab.setBackgroundColor( Color.WHITE );
				permissionsTabLink.setTextColor( Color.BLACK );

				activeDirectoryTab.setBackgroundColor( Color.WHITE );
				activeDirectoryTabLink.setTextColor( Color.BLACK );

				loginHistoryTab.setBackgroundColor( Color.WHITE );
				loginHistoryTabLink.setTextColor( Color.BLACK );

			}
		} );

		userAccountsTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				myAccountsTab.setBackgroundColor( Color.WHITE );
				myAccountsTabLink.setTextColor( Color.BLACK );

				userAccountsTab.setBackgroundColor( Color.BLUE );
				userAccountsTabLink.setTextColor( Color.WHITE );

				userGroupsTab.setBackgroundColor( Color.WHITE );
				userGroupsTabLink.setTextColor( Color.BLACK );

				permissionsTab.setBackgroundColor( Color.WHITE );
				permissionsTabLink.setTextColor( Color.BLACK );

				activeDirectoryTab.setBackgroundColor( Color.WHITE );
				activeDirectoryTabLink.setTextColor( Color.BLACK );

				loginHistoryTab.setBackgroundColor( Color.WHITE );
				loginHistoryTabLink.setTextColor( Color.BLACK );

				//userAccTab.getLocalUserAccountDataTable().getUpdateIcommandWithData().executeWithData( false );
			}
		} );

		userGroupsTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				myAccountsTab.setBackgroundColor( Color.WHITE );
				myAccountsTabLink.setTextColor( Color.BLACK );

				userAccountsTab.setBackgroundColor( Color.WHITE );
				userAccountsTabLink.setTextColor( Color.BLACK );

				userGroupsTab.setBackgroundColor( Color.BLUE );
				userGroupsTabLink.setTextColor( Color.WHITE );

				permissionsTab.setBackgroundColor( Color.WHITE );
				permissionsTabLink.setTextColor( Color.BLACK );

				activeDirectoryTab.setBackgroundColor( Color.WHITE );
				activeDirectoryTabLink.setTextColor( Color.BLACK );

				loginHistoryTab.setBackgroundColor( Color.WHITE );
				loginHistoryTabLink.setTextColor( Color.BLACK );

				//userGroupTab.getUserGroupsDataTable().getUpdateIcommandWithData().executeWithData( false );
			}
		} );

		permissionsTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				myAccountsTab.setBackgroundColor( Color.WHITE );
				myAccountsTabLink.setTextColor( Color.BLACK );

				userAccountsTab.setBackgroundColor( Color.WHITE );
				userAccountsTabLink.setTextColor( Color.BLACK );

				userGroupsTab.setBackgroundColor( Color.WHITE );
				userGroupsTabLink.setTextColor( Color.BLACK );

				permissionsTab.setBackgroundColor( Color.BLUE );
				permissionsTabLink.setTextColor( Color.WHITE );

				activeDirectoryTab.setBackgroundColor( Color.WHITE );
				activeDirectoryTabLink.setTextColor( Color.BLACK );

				loginHistoryTab.setBackgroundColor( Color.WHITE );
				loginHistoryTabLink.setTextColor( Color.BLACK );

				//				permissionTa

			}
		} );

		activeDirectoryTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				myAccountsTab.setBackgroundColor( Color.WHITE );
				myAccountsTabLink.setTextColor( Color.BLACK );

				userAccountsTab.setBackgroundColor( Color.WHITE );
				userAccountsTabLink.setTextColor( Color.BLACK );

				userGroupsTab.setBackgroundColor( Color.WHITE );
				userGroupsTabLink.setTextColor( Color.BLACK );

				permissionsTab.setBackgroundColor( Color.WHITE );
				permissionsTabLink.setTextColor( Color.BLACK );

				activeDirectoryTab.setBackgroundColor( Color.BLUE );
				activeDirectoryTabLink.setTextColor( Color.WHITE );

				loginHistoryTab.setBackgroundColor( Color.WHITE );
				loginHistoryTabLink.setTextColor( Color.BLACK );

				//activeDirTab.getActiveDirectoryDataTable().getUpdateIcommandWithData().executeWithData( false );

			}
		} );

		loginHistoryTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				myAccountsTab.setBackgroundColor( Color.WHITE );
				myAccountsTabLink.setTextColor( Color.BLACK );

				userAccountsTab.setBackgroundColor( Color.WHITE );
				userAccountsTabLink.setTextColor( Color.BLACK );

				userGroupsTab.setBackgroundColor( Color.WHITE );
				userGroupsTabLink.setTextColor( Color.BLACK );

				permissionsTab.setBackgroundColor( Color.WHITE );
				permissionsTabLink.setTextColor( Color.BLACK );

				activeDirectoryTab.setBackgroundColor( Color.WHITE );
				activeDirectoryTabLink.setTextColor( Color.BLACK );

				loginHistoryTab.setBackgroundColor( Color.BLUE );
				loginHistoryTabLink.setTextColor( Color.WHITE );

				//loginTab.getLoginHistoryDataTable().getUpdateIcommandWithData().executeWithData( null );

			}
		} );

		accountConfTab.add( myAccountsTab );
		accountConfTab.add( userAccountsTab );
		accountConfTab.add( userGroupsTab );
		//accountConfTab.add( permissionsTab );
		accountConfTab.add( activeDirectoryTab );
		accountConfTab.add( loginHistoryTab );

		return accountConfTab;
	}

	public void updateAllTabData()
	{
		/*int selTabIndex = accountConfTab.getTabIndex();
		if ( selTabIndex == 0 )
		{
		
		}
		if ( selTabIndex == 1 )
		{
			userAccTab.getLocalUserAccountDataTable().getUpdateIcommandWithData().executeWithData( false );
		}
		if ( selTabIndex == 2 )
		{
			userGroupTab.getUserGroupsDataTable().getUpdateIcommandWithData().executeWithData( false );
		}
		if ( selTabIndex == 3 )
		{
			activeDirTab.getActiveDirectoryDataTable().getUpdateIcommandWithData().executeWithData( false );
		}
		if ( selTabIndex == 4 )
		{
			loginTab.getLoginHistoryDataTable().getUpdateIcommandWithData().executeWithData( null );
		}*/
		myaccountTab.updateData( this.currentUser );
		userAccTab.getLocalUserAccountDataTable().getUpdateIcommandWithData().executeWithData( null );
		userGroupTab.getUserGroupsDataTable().getUpdateIcommandWithData().executeWithData( false );
		activeDirTab.getActiveDirectoryDataTable().getUpdateIcommandWithData().executeWithData( false );
		loginTab.getLoginHistoryDataTable().getUpdateIcommandWithData().executeWithData( null );

	}
}