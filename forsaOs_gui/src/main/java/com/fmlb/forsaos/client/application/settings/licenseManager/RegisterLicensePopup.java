package com.fmlb.forsaos.client.application.settings.licenseManager;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.RegisterLicenseModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class RegisterLicensePopup extends CommonPopUp
{
	private MaterialTextBox registerName;

	private MaterialTextBox company_name;

	private MaterialTextBox key_value;

	private UIComponentsUtil uiComponentsUtil;

	private IcommandWithData registerLicenseCmd;

	private MaterialRow createLicenseRow;

	public RegisterLicensePopup( String headerTitle, String btnTxt, UIComponentsUtil uiComponentsUtil, IcommandWithData registerLicenseCmd )
	{
		super( headerTitle, "", btnTxt, true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.registerLicenseCmd = registerLicenseCmd;

		LoggerUtil.log( "initialize create License" );

		initialize();
		buttonAction();

	}

	private void initialize()
	{

		createLicenseRow = new MaterialRow();

		registerName = uiComponentsUtil.getTexBox( "License Name", "Enter License Name", "s6" );

		key_value = uiComponentsUtil.getTexBox( "License Key", "xxxxx-xxxxx-xxxxx-xxxxx-xxxxx", "s12" );

		company_name = uiComponentsUtil.getTexBox( "Company Name", "Enter Company Name", "s6" );

		createLicenseRow.add( registerName );
		createLicenseRow.add( company_name );
		createLicenseRow.add( key_value );

		getBodyPanel().add( createLicenseRow );
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
					registerLicense();
				}
			}
		} );
	}

	private void registerLicense()
	{

		RegisterLicenseModel registerLicenseModel = new RegisterLicenseModel( registerName.getValue(), key_value.getValue(), company_name.getValue() );
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().registerLicense( registerLicenseModel, new ApplicationCallBack<Boolean>()
		{

			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( registerLicenseModel.getName() + " License Registerd..!", "rounded" );
				if ( registerLicenseCmd != null )
				{
					registerLicenseCmd.executeWithData( true );
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
		if ( registerName.validate() && company_name.validate() && key_value.validate() )
		{
			valid = true;
		}
		else
		{
			registerName.validate();
			company_name.validate();
			key_value.validate();
		}
		return valid;

	}

}
