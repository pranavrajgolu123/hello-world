package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class RollBackPopup extends CommonPopUp
{
	private IcommandWithData rollBackCmd;

	AppResources resources = GWT.create( AppResources.class );

	public RollBackPopup( IcommandWithData rollBackCmd )
	{
		super( "Rollback", "", "Submit", true );
		this.rollBackCmd = rollBackCmd;
		LoggerUtil.log( "initialize roll back popop" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow createlicenseRow = new MaterialRow();

		MaterialLabel rollbackLabel = new MaterialLabel( "Do you want to rollback to the previous version of IRIS?" );
		createlicenseRow.add( rollbackLabel );

		MaterialLabel rollbackLabel1 = new MaterialLabel( "This will reset the database to the state it was in before the update was applied." );
		createlicenseRow.add( rollbackLabel1 );

		MaterialLabel rollbackLabel2 = new MaterialLabel( "This will not delete LEMs or VMs" );
		rollbackLabel2.setTextColor( Color.RED_ACCENT_3 );
		createlicenseRow.add( rollbackLabel2 );

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
				// Here fire the API to rollback

				// on success
				MaterialLoader.loading( false, getBodyPanel() );
				if ( validate() )
				{
					close();
					MaterialToast.fireToast( "Rollbacked..!", "rounded" );
					if ( rollBackCmd != null )
					{
						rollBackCmd.executeWithData( true );
					}
				}

			}

		} );
	}
}
