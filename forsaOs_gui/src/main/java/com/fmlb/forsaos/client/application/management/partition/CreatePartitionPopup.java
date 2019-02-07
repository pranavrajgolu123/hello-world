package com.fmlb.forsaos.client.application.management.partition;

import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.PartitionCreateModel;
import com.fmlb.forsaos.client.application.models.PartitionModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.PartitionType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class CreatePartitionPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox partitionName;

	private MaterialRow createPartitionRow;

	private IcommandWithData icommand;

	AppResources resources = GWT.create( AppResources.class );

	private MaterialPanel partitionTypePanel;

	private MaterialRadioButton snapshotPartition;

	private MaterialRadioButton blinkPartition;

	private List<PartitionModel> partitionModels;

	private MaterialLabel partitionType;

	private MaterialPanel partitionTypes;

	private MaterialIntegerBox startSector;

	private MaterialIntegerBox endSector;

	public CreatePartitionPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData icommand, List<PartitionModel> partitionModels )
	{
		super( "Create Partition", "", "Create Blink Partition", true );
		this.icommand = icommand;
		this.uiComponentsUtil = uiComponentsUtil;
		this.partitionModels = partitionModels;
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		createPartitionRow = new MaterialRow();

		partitionName = uiComponentsUtil.getTexBox( "Partition Name", "Enter Partition Name", "s6" );
		uiComponentsUtil.addTextBoxValidator( partitionName );

		partitionTypePanel = new MaterialPanel();
		partitionTypePanel.setGrid( "s6" );

		partitionTypes = new MaterialPanel();

		partitionType = new MaterialLabel( "Partition Type" );

		blinkPartition = new MaterialRadioButton( "partitionType", "Blink Partition" );
		blinkPartition.setValue( true );
		blinkPartition.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( blinkPartition.getValue() )
				{
					getButton().setText( "Create Blink Partition" );
				}
			}
		} );

		snapshotPartition = new MaterialRadioButton( "partitionType", "Snapshot Partition" );
		snapshotPartition.setValue( false );
		snapshotPartition.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( snapshotPartition.getValue() )
				{
					getButton().setText( "Create Snapshot Partition" );
				}
			}
		} );

		MaterialPanel filler = new MaterialPanel();
		filler.setGrid( "s12" );

		startSector = uiComponentsUtil.getIntegerBoxIncludeZero( "Allocation From", "Allocation From", "s6" );
		endSector = uiComponentsUtil.getIntegerBox( "Allocation To", "Allocation To", "s6" );

		createPartitionRow.add( partitionName );
		partitionTypePanel.add( partitionType );
		partitionTypes.add( blinkPartition );
		partitionTypes.add( snapshotPartition );
		partitionTypePanel.add( partitionTypes );
		createPartitionRow.add( partitionTypePanel );
		createPartitionRow.add( filler );
		createPartitionRow.add( startSector );
		createPartitionRow.add( endSector );

		getBodyPanel().add( createPartitionRow );
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
					MaterialLoader.loading( true, getBodyPanel() );
					PartitionType partitionType;
					if ( blinkPartition.getValue() )
					{
						partitionType = PartitionType.BLINK;
					}
					else
					{
						partitionType = PartitionType.SNAPSHOT;
					}
					CommonServiceProvider.getCommonService().createPartition( new PartitionCreateModel( startSector.getValue(), endSector.getValue(), partitionName.getValue(), true ), partitionType, new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							icommand.executeWithData( true );
						}

						@Override
						public void onFailure( Throwable caught )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							super.onFailureShowErrorPopup( caught, null );
						}
					} );
				}
			}
		} );
	}

	private boolean validateAllocation()
	{
		boolean isValid = true;
		boolean validStart = true;
		boolean validEnd = true;

		Integer start = startSector.getValue();
		Integer end = endSector.getValue();

		if ( start == 100 )
		{
			startSector.setErrorText( "Can not be equal to 100." );
			return false;
		}

		for ( PartitionModel partitionModel : partitionModels )
		{
			LoggerUtil.log( "Start " + start + " end " + end + " partition start " + partitionModel.getStart() + " partition end " + partitionModel.getStop() );
			if ( ( partitionModel.getStart() <= start ) && ( start < partitionModel.getStop() ) )
			{
				validStart = false;
			}
			if ( ( partitionModel.getStart() < end ) && ( end <= partitionModel.getStop() ) )
			{
				validEnd = false;
			}
		}

		if ( validStart )
		{
			startSector.clearErrorText();
			startSector.setSuccessText( "" );
		}
		else
		{
			startSector.setErrorText( "Allocation value overlaps with other partition." );
		}
		if ( validEnd )
		{
			endSector.clearErrorText();
			endSector.setSuccessText( "" );
		}
		else
		{
			endSector.setErrorText( "Allocation value overlaps with other partition." );
		}

		if ( validStart && validEnd )
		{
			isValid = true;
		}
		else
		{
			isValid = false;
		}

		LoggerUtil.log( "ValidStart " + validStart + " ValidEnd " + validEnd + " Valid " + isValid );

		return isValid;
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( partitionName.validate() && startSector.validate() && endSector.validate() && validateAllocation() )
		{
			valid = true;
		}
		return valid;
	}

}
