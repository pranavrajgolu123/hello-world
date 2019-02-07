package com.fmlb.forsaos.client.application.lem.details;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.LEMSnapshotModel;
import com.fmlb.forsaos.client.application.models.UpdateLEMSnapshotModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class RenameSnapshotPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private LEMSnapshotModel lemSnapshotModel;

	private MaterialTextBox newName;

	private MaterialLabel lemLabel;

	private MaterialLabel currentSnapShotLabel;

	private MaterialLabel lemNameLabel;

	private MaterialLabel currentSnapShotNameLabel;

	private IcommandWithData updateLEMSnapshotGridCmd;

	private LEMModel lemodel;

	AppResources resources = GWT.create( AppResources.class );

	public RenameSnapshotPopUp( LEMModel lemmodel, LEMSnapshotModel lemSnapshotModel, UIComponentsUtil uiComponentsUtil, IcommandWithData updateLEMSnapshotGridCmd )
	{
		super( "Rename Snapshot", "", "Rename Snapshot", true );
		this.lemSnapshotModel = lemSnapshotModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.lemodel = lemmodel;
		this.updateLEMSnapshotGridCmd = updateLEMSnapshotGridCmd;
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow currentDataLabelRow = new MaterialRow();
		MaterialRow currentDataRow = new MaterialRow();
		MaterialRow newDataRow = new MaterialRow();

		lemLabel = this.uiComponentsUtil.getLabel( "LEM", "s6" );
		currentSnapShotLabel = this.uiComponentsUtil.getLabel( "Current snapshot name", "s6" );
		lemNameLabel = this.uiComponentsUtil.getLabel( lemodel.getLemName(), "s6" );
		currentSnapShotNameLabel = this.uiComponentsUtil.getLabel( this.lemSnapshotModel.getSnapshot_name(), "s6" );
		currentDataLabelRow.add( lemLabel );
		currentDataLabelRow.add( currentSnapShotLabel );
		currentDataLabelRow.addStyleName( resources.style().popup_data_header() );
		currentDataRow.add( lemNameLabel );
		currentDataRow.add( currentSnapShotNameLabel );
		currentDataRow.addStyleName( resources.style().popup_data_value() );

		newName = this.uiComponentsUtil.getTexBox( "New name", "Type new name", "s12" );
		newDataRow.add( newName );
		newDataRow.addStyleName( "edit_snapshot_name_row" );
		getBodyPanel().add( currentDataLabelRow );
		getBodyPanel().add( currentDataRow );
		getBodyPanel().add( newDataRow );
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
					LoggerUtil.log( "rename snapshot" );
					UpdateLEMSnapshotModel updateLEMSnapshotModel = new UpdateLEMSnapshotModel();
					updateLEMSnapshotModel.setName( lemSnapshotModel.getSnapshot_name() );
					updateLEMSnapshotModel.setNew_name( newName.getValue() );
					MaterialLoader.loading( true, getBodyPanel() );
					CommonServiceProvider.getCommonService().updateLEMSnapshot( updateLEMSnapshotModel, new ApplicationCallBack<Boolean>()
					{

						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							MaterialToast.fireToast( "Snapshot Renamed..!", "rounded" );
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
		if ( newName.validate() )
		{
			valid = true;
		}
		else
		{
			newName.validate();
		}
		return valid;

	}
}
