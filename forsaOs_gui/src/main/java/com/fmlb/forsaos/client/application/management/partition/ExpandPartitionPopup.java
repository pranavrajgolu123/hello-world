package com.fmlb.forsaos.client.application.management.partition;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ConfirmationPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.PartitionExpandModel;
import com.fmlb.forsaos.client.application.models.PartitionModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class ExpandPartitionPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialLabel partitionNameLabel;
	
	private MaterialLabel partitionNameValue;
	
	private MaterialLabel allocationLabel;

	private MaterialRow createPartitionRow;

	private IcommandWithData icommand;

	AppResources resources = GWT.create( AppResources.class );

	private List<PartitionModel> partitionModels;

	private MaterialIntegerBox endSector;

	private PartitionModel selectedPartition;

	private ConfirmationPopup confirmationPopup;

	public ExpandPartitionPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData icommand, List<PartitionModel> partitionModels, PartitionModel selectedPartition )
	{
		super( "Expand Partition", "", "Expand Partition", true );
		this.icommand = icommand;
		this.uiComponentsUtil = uiComponentsUtil;
		this.partitionModels = partitionModels;
		this.selectedPartition = selectedPartition;
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		createPartitionRow = new MaterialRow();

		partitionNameLabel = uiComponentsUtil.getLabel( "Partition Name:", "" );
		partitionNameLabel.addStyleName( resources.style().expandPartitionTxt() );
		partitionNameValue = uiComponentsUtil.getLabel( selectedPartition.getName(), "" );
		partitionNameValue.addStyleName( resources.style().expandPartitionName() );
		
		allocationLabel = uiComponentsUtil.getLabel( "Allocation To:", "" );
		allocationLabel.addStyleName( resources.style().expandAllocationTxt() );

		endSector = uiComponentsUtil.getIntegerBox( "", "", "" );
		endSector.setValue( selectedPartition.getStop() );
		endSector.addStyleName( resources.style().allocationInput() );

		createPartitionRow.add( partitionNameLabel );
		createPartitionRow.add( partitionNameValue );
		
		MaterialPanel filler = new MaterialPanel();
		filler.setGrid( "s12" );
		
		createPartitionRow.add( filler );
		createPartitionRow.add( allocationLabel );
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
					ArrayList<String> warningMsgList = new ArrayList<String>();
					warningMsgList.add( "Do you want to expand partition to specified value?" );
					confirmationPopup = new ConfirmationPopup( "Expand Partition", "Expand", warningMsgList, uiComponentsUtil, new Icommand()
					{
						@Override
						public void execute()
						{
							confirmationPopup.close();
							MaterialLoader.loading( true, getBodyPanel() );
							CommonServiceProvider.getCommonService().expandPartition( new PartitionExpandModel( selectedPartition.getName(), endSector.getValue() ), new ApplicationCallBack<Boolean>()
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
					} );
					confirmationPopup.open();
				}
			}
		} );
	}

	private boolean validateAllocation()
	{
		boolean isValid = true;
		boolean validEnd = true;

		Integer start = selectedPartition.getStart();
		Integer end = endSector.getValue();
		if ( end <= selectedPartition.getStop() )
		{
			endSector.setErrorText( "Please enter value higher than old allocation." );
			return false;
		}

		for ( PartitionModel partitionModel : partitionModels )
		{
			if ( selectedPartition.getName().equals( partitionModel.getName() ) )
			{
				continue;
			}
			LoggerUtil.log( "Start " + start + " end " + end + " partition start " + partitionModel.getStart() + " partition end " + partitionModel.getStop() );
			if ( ( partitionModel.getStart() < end ) && ( end <= partitionModel.getStop() ) )
			{
				validEnd = false;
			}
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

		if ( validEnd )
		{
			isValid = true;
		}
		else
		{
			isValid = false;
		}

		LoggerUtil.log( " ValidEnd " + validEnd + " Valid " + isValid );

		return isValid;
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( endSector.validate() && validateAllocation() )
		{
			valid = true;
		}
		return valid;
	}

}
