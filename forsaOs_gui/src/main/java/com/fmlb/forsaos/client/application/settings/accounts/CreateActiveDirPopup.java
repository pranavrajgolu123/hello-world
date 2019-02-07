package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CreateActiveDirectoryModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CreateActiveDirPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox domainName;

	private MaterialTextBox ip;

	private MaterialTextBox adUsername;

	private MaterialTextBox adPassword;

	private IcommandWithData updateActiveDirDataTableCmd;

	private CurrentUser currentUser;

	AppResources resources = GWT.create( AppResources.class );

	public CreateActiveDirPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData updateUserDataTableCmd, CurrentUser currentUser )
	{
		super( "Join Active Directory", "", "Join Active Directory", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateActiveDirDataTableCmd = updateUserDataTableCmd;
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow createActiveDirRow = new MaterialRow();

		domainName = uiComponentsUtil.getTexBox( "Domain Name", "Domain", "s6" );

		ip = uiComponentsUtil.getIP4TextBox( "Machine IP", "", "s6", true );

		adUsername = uiComponentsUtil.getTexBox( "Active Directory Username", "", "s6" );

		adPassword = uiComponentsUtil.getDefaultPasswordBox( "Active Directory Password", "s6" );

		createActiveDirRow.add( domainName );
		createActiveDirRow.add( ip );
		createActiveDirRow.add( adUsername );
		createActiveDirRow.add( adPassword );

		getBodyPanel().add( createActiveDirRow );
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
		CreateActiveDirectoryModel createActiveDirectoryModel = new CreateActiveDirectoryModel( domainName.getValue().toString(), ip.getValue().toString(), adUsername.getValue().toString(), adPassword.getValue().toString() );

		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().createActiveDirectory( createActiveDirectoryModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( createActiveDirectoryModel.getName() + " Created..!", "rounded" );
				if ( updateActiveDirDataTableCmd != null )
				{
					updateActiveDirDataTableCmd.executeWithData( true );
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
		if ( domainName.validate() && ip.validate() && adUsername.validate() && adPassword.validate() )
		{
			valid = true;
		}
		else
		{
			domainName.validate();
			ip.validate();
			adUsername.validate();
			adPassword.validate();
		}
		return valid;

	}

}
