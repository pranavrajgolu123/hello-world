package com.fmlb.forsaos.client.application.diagnostics.status;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.RegisterUPSModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class RegisterUpsPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox upsName;

	private MaterialTextBox ip;

	private MaterialTextBox port;

	private IcommandWithData updateUpsTab;

	private RegisterUPSModel registerUps;

	AppResources resources = GWT.create( AppResources.class );

	public RegisterUpsPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData updateUpsTab )
	{
		super( "Register UPS", "", "Register UPS", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateUpsTab = updateUpsTab;
		LoggerUtil.log( "initialize ups register pop up" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow registerUPSRow = new MaterialRow();

		upsName = uiComponentsUtil.getTexBox( "UPS Name", "UPS Name", "s4" );

		ip = uiComponentsUtil.getIP4TextBox( "IP Address", "", "s4", true );

		port = uiComponentsUtil.getTexBox( "Port No", "Port No", "s4" );

		registerUPSRow.add( upsName );
		registerUPSRow.add( ip );
		registerUPSRow.add( port );

		getBodyPanel().add( registerUPSRow );
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
					createNewUpsName();
				}

			}
		} );
	}

	private void createNewUpsName()
	{
		registerUps = new RegisterUPSModel( upsName.getValue().toString(), port.getValue().toString(), ip.getValue().toString() );
		CommonServiceProvider.getCommonService().registerUPS( registerUps, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( true );
				close();
				LoggerUtil.log( "Register Ups" );
				MaterialToast.fireToast( registerUps.getName() + " Registered..!", "rounded" );
				if ( updateUpsTab != null )
				{
					updateUpsTab.executeWithData( true );
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
		if ( upsName.validate() && ip.validate() && port.validate() )
		{
			valid = true;
		}
		else
		{
			upsName.validate();
			ip.validate();
			port.validate();

		}
		return valid;

	}

}