package com.fmlb.forsaos.client.application.diagnostics.smtp;

import java.util.HashMap;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.UpdateSMTPConfigrationModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CreateSMTPConfigurationPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private Icommand updateEmailDataTable;

	private MaterialTextBox serverName;

	private MaterialTextBox smtpUsername;

	private MaterialTextBox smtpPassword;

	private UpdateSMTPConfigrationModel createSMTPModel;

	private MaterialIntegerBox portNumber;

	AppResources resources = GWT.create( AppResources.class );

	public CreateSMTPConfigurationPopUp( UIComponentsUtil uiComponentsUtil, Icommand updateEmailDataTable )
	{
		super( "Add SMTP Server", "", "ADD", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateEmailDataTable = updateEmailDataTable;
		LoggerUtil.log( "initialize add SMTP" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow createDirRow = new MaterialRow();

		serverName = uiComponentsUtil.getTexBox( "SMTP Server Name/IP Address", "Enter SMTP Server Name/IP Address", "s6" );
		serverName.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				serverName.validate();
			}
		} );
		serverName.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				serverName.validate();
			}
		} );

		HashMap<String, String> portDropDownOptionMap = new HashMap<String, String>();
		portDropDownOptionMap.put( "25", "25" );
		portDropDownOptionMap.put( "465", "465" );
		portDropDownOptionMap.put( "587", "587" );
		portDropDownOptionMap.put( "2525", "2525" );

		portNumber = uiComponentsUtil.getIntegerBox( "Port", "Enter Port Number", "s6" );

		smtpUsername = uiComponentsUtil.getTexBox( "SMTP Username", "Enter SMTP Username", "s6" );
		smtpUsername.addValidator( uiComponentsUtil.getTextBoxEmptyValidator() );
		smtpUsername.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				smtpUsername.validate();
			}
		} );
		smtpUsername.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				smtpUsername.validate();
			}
		} );
		smtpPassword = uiComponentsUtil.getDefaultPasswordBox( "SMTP Password", "s6" );
		smtpPassword.addValidator( uiComponentsUtil.getTextBoxEmptyValidator() );
		smtpPassword.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				smtpPassword.validate();
			}
		} );
		smtpPassword.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				smtpPassword.validate();
			}
		} );

		createDirRow.add( serverName );
		createDirRow.add( portNumber );
		createDirRow.add( smtpUsername );
		createDirRow.add( smtpPassword );

		getBodyPanel().add( createDirRow );
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
					createNewEmail();
				}
			}
		} );
	}

	private void createNewEmail()
	{
		//		createSMTPModel = new UpdateSMTPConfigrationModel( serverName.getValue().toString(), portDropDown.getValue().get( 0 ).toString(), smtpUsername.getValue().toString(), smtpPassword.getValue().toString() );
		createSMTPModel = new UpdateSMTPConfigrationModel( serverName.getValue().toString(), portNumber.getValue().toString(), smtpUsername.getValue().toString(), smtpPassword.getValue().toString() );
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().createSMTPConfiguration( createSMTPModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				LoggerUtil.log( "Add Email" );
				MaterialToast.fireToast( serverName.getValue() + " Added..!", "rounded" );
				if ( updateEmailDataTable != null )
				{
					updateEmailDataTable.execute();
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
		if ( serverName.validate() && smtpUsername.validate() && smtpPassword.validate() && portNumber.validate() )
		{
			valid = true;
		}
		else
		{
			smtpUsername.validate();
			smtpPassword.validate();
			serverName.validate();
			portNumber.validate();

		}
		return valid;

	}

}
