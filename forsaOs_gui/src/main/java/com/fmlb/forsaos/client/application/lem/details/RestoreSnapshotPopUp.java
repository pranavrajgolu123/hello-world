package com.fmlb.forsaos.client.application.lem.details;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.LEMSnapshotModel;
import com.fmlb.forsaos.client.application.models.RestoreSnapshotModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class RestoreSnapshotPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private LEMSnapshotModel lemSnapshotModel;

	private MaterialLabel snapshotLabel;

	private MaterialLabel detachLabel;

	private IcommandWithData updateLEMSnapshotGridCmd;

	AppResources resources = GWT.create( AppResources.class );

	public RestoreSnapshotPopUp( LEMSnapshotModel lemSnapshotModel, UIComponentsUtil uiComponentsUtil, IcommandWithData updateLEMSnapshotGridCmd )
	{
		super( "Restore Snapshot", "", "Restore Snapshot", true );
		this.lemSnapshotModel = lemSnapshotModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateLEMSnapshotGridCmd = updateLEMSnapshotGridCmd;
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow restoreSnapRow = new MaterialRow();
		snapshotLabel = this.uiComponentsUtil.getLabel( "Restore Snapshot named '" + lemSnapshotModel.getSnapshot_name() + "'", "s12" );
		detachLabel = this.uiComponentsUtil.getLabel( "Note: Please power off or detach disk from the VM before restoring snapshot. Issuing a restore ignoring the latter will forcefully power off the VM to avoid any corruption to that disk.", "s12" );
		detachLabel.addStyleName( resources.style().restore_snapshot_alert_msg() );
		restoreSnapRow.add( snapshotLabel );
		restoreSnapRow.add( detachLabel );
		getBodyPanel().add( restoreSnapRow );
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
					LoggerUtil.log( "restore snapshot" );
					RestoreSnapshotModel restoreSnapshotModel = new RestoreSnapshotModel();
					restoreSnapshotModel.setName( lemSnapshotModel.getSnapshot_name() );
					MaterialLoader.loading( true, getBodyPanel() );
					CommonServiceProvider.getCommonService().restoreLEMSnapshot( restoreSnapshotModel, new ApplicationCallBack<Boolean>()
					{

						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							MaterialToast.fireToast( "Snapshot Restored..!", "rounded" );
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
		boolean valid = true;

		return valid;

	}
}
