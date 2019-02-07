package com.fmlb.forsaos.client.application.lem.details;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.LEMSnapshotModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CreateSnapShotPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox name;

	private LEMModel lemModel;

	private IcommandWithData updateLEMSnapshotGridCmd;

	private List<String> partitions = new ArrayList<>();

	private MaterialComboBox<String> partitionsComboBox;

	public CreateSnapShotPopUp( LEMModel lemModel, UIComponentsUtil uiComponentsUtil, List<String> partitions, IcommandWithData updateLEMSnapshotGridCmd )
	{
		super( "Create Snapshot", "", "Create Snapshot", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.lemModel = lemModel;
		this.partitions = partitions;
		this.updateLEMSnapshotGridCmd = updateLEMSnapshotGridCmd;
		initialize();
		buttonAction();
	}

	@SuppressWarnings( "unchecked" )
	private void initialize()
	{
		MaterialRow createSnapRow = new MaterialRow();
		name = this.uiComponentsUtil.getTexBox( "Name", "", "s12" );
		name.addValidator( this.uiComponentsUtil.getInvalidCharacterValidator() );

		name.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				name.validate();
			}
		} );
		name.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				name.validate();
			}
		} );
		HashMap<String, String> partitionsMap = new HashMap<String, String>();
		partitionsMap.put( "None", "None" );
		if ( partitions != null && !partitions.isEmpty() )
		{
			for ( String partition : partitions )
			{
				partitionsMap.put( partition, partition );
			}
		}
		partitionsComboBox = ( MaterialComboBox<String> ) this.uiComponentsUtil.getComboBox( partitionsMap, "Select Path", "s12" );
		/*if ( partitions == null || partitions.isEmpty() )
		{
			partitionsComboBox.setErrorText( "No snapshot partition created." );
		}*/
		createSnapRow.add( name );
		createSnapRow.add( partitionsComboBox );
		getBodyPanel().add( createSnapRow );
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
					LoggerUtil.log( "create snapshot" );
					LEMSnapshotModel lemSnapshotModel = new LEMSnapshotModel();
					lemSnapshotModel.setId( null );
					lemSnapshotModel.setName( lemModel.getLemName() );
					lemSnapshotModel.setSnapshot_name( name.getValue() );
					if ( partitionsComboBox.getValue() != null && !partitionsComboBox.getValue().isEmpty() && !partitionsComboBox.getValue().get( 0 ).equalsIgnoreCase( "none" ) )
					{
						lemSnapshotModel.setPath( partitionsComboBox.getValue().get( 0 ) );
					}
					MaterialLoader.loading( true, getBodyPanel() );
					CommonServiceProvider.getCommonService().createLEMSnapshot( lemSnapshotModel, new ApplicationCallBack<Boolean>()
					{

						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							MaterialToast.fireToast( "Snapshot Created..!", "rounded" );
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
		if ( name.validate() )
		{
			valid = true;
		}
		else
		{
			name.validate();
		}
		return valid;

	}
}
