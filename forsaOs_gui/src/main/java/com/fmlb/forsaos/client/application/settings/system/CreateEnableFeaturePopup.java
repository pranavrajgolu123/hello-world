package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CreateEnableFeaturePopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox enterlicensekey;

	private IcommandWithData updateLicenseFeatureCmd;

	public CreateEnableFeaturePopup( UIComponentsUtil uiComponentsUtil, IcommandWithData updateLicenseFeatureCmd )
	{
		super( "Enable Feature", "", "Submit", true );
		this.uiComponentsUtil = uiComponentsUtil;
		LoggerUtil.log( "initialize enable feature popop" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow createlicenseRow = new MaterialRow();

		enterlicensekey = uiComponentsUtil.getTexBox( "Enter license key", "Enter license key", "s12" );

		createlicenseRow.add( enterlicensekey );

		getBodyPanel().add( createlicenseRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				MaterialLoader.loading( true, getBodyPanel() );
				// Here fire the API to enable a license feature
				
				// on success
				MaterialLoader.loading( false, getBodyPanel() );
				if ( validate() )
				{
					close();
					MaterialToast.fireToast( "Feature Enabled..!", "rounded" );
					if ( updateLicenseFeatureCmd != null )
					{
						updateLicenseFeatureCmd.executeWithData( true );
					}
				}

			}

		} );
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( enterlicensekey.validate() )
		{
			valid = true;
		}
		else
		{
			enterlicensekey.validate();
		}
		return valid;

	}
}
