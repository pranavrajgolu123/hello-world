package com.fmlb.forsaos.client.application.lem.details;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.LEMSnapshotModel;
import com.fmlb.forsaos.client.application.models.PromoteSnapshotModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class PromoteSnapshotPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private IcommandWithData updateLEMSnapshotGridCmd;

	private LEMSnapshotModel lemSnapshotModel;

	private MaterialLabel promoteSnapshotLabel;

	private MaterialTextBox lemName;

	public PromoteSnapshotPopUp( LEMSnapshotModel lemSnapshotModel, UIComponentsUtil uiComponentsUtil, IcommandWithData updateLEMSnapshotGridCmd )
	{
		super( "Promote Snapshot", "", "Promote Snapshot", true );
		this.lemSnapshotModel = lemSnapshotModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateLEMSnapshotGridCmd = updateLEMSnapshotGridCmd;
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow promoteSnapRow = new MaterialRow();
		promoteSnapshotLabel = this.uiComponentsUtil.getLabel( "Promote Snapshot named '" + lemSnapshotModel.getSnapshot_name() + "'", "s12" );
		lemName = this.uiComponentsUtil.getTexBox( "LEM Name", "", "s12" );
		this.uiComponentsUtil.addTextBoxValidator( lemName );
		promoteSnapRow.add( promoteSnapshotLabel );
		promoteSnapRow.add( lemName );
		getBodyPanel().add( promoteSnapRow );
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
					LoggerUtil.log( "promote snapshot" );
					PromoteSnapshotModel promoteSnapshotModel = new PromoteSnapshotModel( lemSnapshotModel.getSnapshot_name(), lemName.getValue() );
					MaterialLoader.loading( true, getBodyPanel() );
					CommonServiceProvider.getCommonService().promoteLEMSnapshot( promoteSnapshotModel, new ApplicationCallBack<Boolean>()
					{

						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							MaterialToast.fireToast( "Snapshot Promoted..!", "rounded" );
							if ( updateLEMSnapshotGridCmd != null )
							{
								updateLEMSnapshotGridCmd.executeWithData( true );
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

			}
		} );
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( lemName.validate() )
		{
			valid = true;
		}
		return valid;

	}
}
