package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;

import gwt.material.design.client.ui.MaterialPanel;

public class UserGroupsTabContent extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	private UserGroupsDataTable userGroupsDataTable;
	
	private IcommandWithData updateUserAccountTabCommand;

	public UserGroupsTabContent( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData updateUserAccountTabCommand )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.updateUserAccountTabCommand = updateUserAccountTabCommand;
		initializeDataTable();
		add( userGroupsDataTable );
	}

	private void initializeDataTable()
	{
		userGroupsDataTable = new UserGroupsDataTable( uiComponentsUtil, currentUser, updateUserAccountTabCommand );
		userGroupsDataTable.getUpdateIcommandWithData().executeWithData( false );
	}

	public UserGroupsDataTable getUserGroupsDataTable()
	{
		return userGroupsDataTable;
	}

}
