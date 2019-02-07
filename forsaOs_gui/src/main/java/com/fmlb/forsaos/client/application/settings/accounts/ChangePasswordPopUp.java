package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.ChangePasswordModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.UserAccountModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ErrorMessages;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class ChangePasswordPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox password;

	private MaterialTextBox confirmPassword;

	private UserAccountModel userModel;

	private IcommandWithData updateLEMDataTableCmd;

	private CurrentUser currentUser;

	AppResources resources = GWT.create( AppResources.class );

	public ChangePasswordPopUp( UserAccountModel userModel, UIComponentsUtil uiComponentsUtil, IcommandWithData updateLEMDataTableCmd, CurrentUser currentUser )
	{
		super( "Change password", "", "Change Password", true );

		this.userModel = userModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateLEMDataTableCmd = updateLEMDataTableCmd;
		this.currentUser = currentUser;
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow passWordRow = new MaterialRow();

		password = uiComponentsUtil.getDefaultPasswordBox( "New Password", "s6" );
		confirmPassword = uiComponentsUtil.getDefaultPasswordBox( "Confirm Password", "s6" );

		passWordRow.add( password );
		passWordRow.add( confirmPassword );

		getBodyPanel().add( passWordRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			public void execute()
			{

				if ( validate() )
				{
					changePassword();
				}
			}
		} );
	}

	private void changePassword()
	{
		MaterialLoader.loading( true, getBodyPanel() );

		ChangePasswordModel changePasswordModel = new ChangePasswordModel( userModel.getName().toString(), confirmPassword.getValue().toString() );

		CommonServiceProvider.getCommonService().changePassword( changePasswordModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( " Password Changed..!", "rounded" );
				if ( updateLEMDataTableCmd != null )
				{
					updateLEMDataTableCmd.executeWithData( null );
				}

			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );

	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( password.validate() && confirmPassword.validate() )
		{
			valid = true;
			if ( passwordsMatch() )
			{
				valid = true;
			}
			else
			{
				valid = false;
				password.setErrorText( ErrorMessages.PASSWORDS_MISMATCH );
				confirmPassword.setErrorText( ErrorMessages.PASSWORDS_MISMATCH );
			}
		}
		else
		{
			password.validate();
			confirmPassword.validate();
		}
		return valid;

	}

	private boolean passwordsMatch()
	{
		if ( password.getValue().equals( confirmPassword.getValue() ) )
		{
			return true;
		}
		else
		{
			return false;
		}

	}

}
