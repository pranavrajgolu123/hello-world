package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.ExtendExpiryModel;
import com.fmlb.forsaos.client.application.models.UserAccountModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class ExtendExpiryTokenPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private IcommandWithData updateUserDataTableCmd;

	@SuppressWarnings( "unused" )
	private CurrentUser currentUser;

	AppResources resources = GWT.create( AppResources.class );

	private MaterialIntegerBox month;

	private MaterialIntegerBox day;

	private MaterialIntegerBox hour;

	private UserAccountModel accountModel;

	private MaterialRow creatNewUserRow;

	public ExtendExpiryTokenPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData updateUserDataTableCmd, CurrentUser currentUser, UserAccountModel accountModel )
	{
		super( "Extend Token", "", "Extend Token", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateUserDataTableCmd = updateUserDataTableCmd;
		this.currentUser = currentUser;
		this.accountModel = accountModel;
		LoggerUtil.log( "initialize create new user" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		creatNewUserRow = new MaterialRow();

		month = uiComponentsUtil.getIntegerBoxIncludeNullAndZero( "Month", "Month", "s4" );
		day = uiComponentsUtil.getIntegerBoxIncludeNullAndZero( "Day", "Day", "s4" );
		hour = uiComponentsUtil.getIntegerBoxIncludeNullAndZero( "Hour", "Hour", "s4" );
		month.setMin( "0" );
		day.setMin( "0" );
		hour.setMin( "0" );

		creatNewUserRow.add( month );
		creatNewUserRow.add( day );
		creatNewUserRow.add( hour );

		getBodyPanel().add( creatNewUserRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				if ( validate() )
				{
					createNewUser();
				}
			}
		} );
	}

	private void createNewUser()
	{
		Integer monthValue = null;
		Integer dayValue = null;
		Integer hourValue = null;

		monthValue = month.getValue();
		dayValue = day.getValue();
		hourValue = hour.getValue();

		ExtendExpiryModel expiryModel = new ExtendExpiryModel( accountModel.getName(), accountModel.getId(), monthValue, dayValue, hourValue );

		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().extendExpiry( expiryModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( accountModel.getName() + " updated..!", "rounded" );
				if ( updateUserDataTableCmd != null )
				{
					updateUserDataTableCmd.executeWithData( null );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( month.validate() && day.validate() && hour.validate() )
		{
			valid = true;
		}
		return valid;
	}
}
