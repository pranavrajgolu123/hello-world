package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.ChangePasswordModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ErrorMessages;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class MyAccountTabContent extends MaterialPanel
{
	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	private MaterialLabel accountNameLabel;

	private MaterialTextBox oldPasswordBox;

	private MaterialTextBox newPasswordBox;

	private MaterialTextBox confirmNewPassword;

	private IcommandWithData navigationCmd;

	AppResources resources = GWT.create( AppResources.class );

	public MyAccountTabContent( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		userAccountLabel();
		add( modifyPasswordPanel() );
	}

	private void userAccountLabel()
	{
		MaterialRow accountPanel = new MaterialRow();
		accountPanel.addStyleName( resources.style().account_detail() );
		accountPanel.setGrid( "s12" );
		accountPanel.addStyleName( "account_header_penal" );

		MaterialRow accountNamePanel = new MaterialRow();
		accountNamePanel.addStyleName( resources.style().account_detail_row() );
		accountNamePanel.setGrid( "s12" );
		accountNamePanel.addStyleName( "account_header_penal" );

		MaterialLabel accountHeaderLabel = this.uiComponentsUtil.getLabel( "Account", "s12" );
		accountHeaderLabel.addStyleName( resources.style().detail_header() );
		accountHeaderLabel.setBorder( "2" );
		accountHeaderLabel.setGrid( "s12" );

		accountNameLabel = this.uiComponentsUtil.getLabel( "Account name:  " + currentUser.getUserName(), "s12" );
		accountNameLabel.addStyleName( resources.style().detail_header() );
		accountNameLabel.setBorder( "2" );
		accountNameLabel.setGrid( "s12" );

		accountPanel.add( accountHeaderLabel );
		accountNamePanel.add( accountNameLabel );

		add( accountPanel );
		add( accountNamePanel );
	}

	private MaterialPanel modifyPasswordPanel()
	{

		MaterialPanel modifyPasswordPanel = new MaterialPanel();
		MaterialLabel modifyPasswordHeader = this.uiComponentsUtil.getLabel( "Modify Password", "s12", resources.style().vm_setting_header() );
		modifyPasswordPanel.add( modifyPasswordHeader );

		MaterialRow modifyPasswordRow = new MaterialRow();
		modifyPasswordRow.addStyleName( resources.style().vm_setting_row() );

		oldPasswordBox = getPasswordBox( "Old Password" );
		oldPasswordBox.addValidator( uiComponentsUtil.getPasswordValidator( currentUser ) );
		newPasswordBox = getPasswordBox( "New Password" );
		confirmNewPassword = getPasswordBox( "Confirm New Password" );

		modifyPasswordRow.add( oldPasswordBox );
		modifyPasswordRow.add( newPasswordBox );
		modifyPasswordRow.add( confirmNewPassword );

		MaterialButton changePasswordBtn = getChangePasswordBtn();

		modifyPasswordRow.add( changePasswordBtn );
		modifyPasswordPanel.add( modifyPasswordRow );
		return modifyPasswordPanel;

	}

	private MaterialButton getChangePasswordBtn()
	{
		MaterialButton changePasswordBtn = new MaterialButton( "Change password" );
		changePasswordBtn.addStyleName( resources.style().change_password_btn() );
		changePasswordBtn.setFloat( Float.RIGHT );
		changePasswordBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				if ( validate() )
				{
					changePassword();
				}

			}
		} );
		return changePasswordBtn;
	}

	private boolean passwordsMatch()
	{
		if ( newPasswordBox.getValue().equals( confirmNewPassword.getValue() ) )
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	private boolean notSamePassword()
	{
		if ( !currentUser.getPassword().equals( newPasswordBox.getValue() ) && !currentUser.getPassword().equals( confirmNewPassword.getValue() ) )
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	private void changePassword()
	{
		MaterialLoader.loading( true );

		ChangePasswordModel changePasswordModel = new ChangePasswordModel( currentUser.getUserName(), confirmNewPassword.getValue().toString() );

		CommonServiceProvider.getCommonService().changePassword( changePasswordModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				MaterialToast.fireToast( " Password Changed..!", "rounded" );
				// Here we should logout the user and ask him to log in again
				if ( navigationCmd != null )
				{
					navigationCmd.executeWithData( null );
				}

			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );

	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( oldPasswordBox.validate() && newPasswordBox.validate() && confirmNewPassword.validate() )
		{
			valid = true;
			if ( oldPasswordBox.validate() )
			{
				valid = true;
			}
			else
			{
				valid = false;
				oldPasswordBox.setErrorText( ErrorMessages.INVALID_OLD_PASSWORD );
			}

			if ( passwordsMatch() )
			{
				valid = true;
			}
			else
			{
				valid = false;
				newPasswordBox.setErrorText( ErrorMessages.PASSWORDS_MISMATCH );
				confirmNewPassword.setErrorText( ErrorMessages.PASSWORDS_MISMATCH );
			}
			if ( notSamePassword() && oldPasswordBox.validate() )
			{
				valid = true;
			}
			else if ( oldPasswordBox.validate() && passwordsMatch() )
			{
				valid = false;
				newPasswordBox.setErrorText( ErrorMessages.NEW_PASSWORD_MATCH_OLD_PWD );
				confirmNewPassword.setErrorText( ErrorMessages.NEW_PASSWORD_MATCH_OLD_PWD );
			}
		}
		else
		{
			oldPasswordBox.validate();
			newPasswordBox.validate();
			confirmNewPassword.validate();
		}
		return valid;

	}

	private MaterialTextBox getPasswordBox( String label )
	{
		MaterialTextBox passwordBox = uiComponentsUtil.getDefaultPasswordBox( label, "" );
		passwordBox.setGrid( "s4" );
		return passwordBox;
	}

	public void updateData( CurrentUser currentUser )
	{
		this.currentUser = currentUser;
		accountNameLabel.setText( "Account name:  " + currentUser.getUserName() );
		accountNameLabel.setTitle( "Account name:  " + currentUser.getUserName() );
	}

}
