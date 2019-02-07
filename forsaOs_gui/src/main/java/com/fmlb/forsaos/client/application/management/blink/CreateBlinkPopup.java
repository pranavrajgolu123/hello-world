package com.fmlb.forsaos.client.application.management.blink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CreateBlinkModel;
import com.fmlb.forsaos.client.application.models.ProgressModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CreateBlinkPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox blinkName;

	private IcommandWithData updateBlinkDataTable;

	private CreateBlinkModel createBlink;

	private MaterialRow createBlinkRow;

	AppResources resources = GWT.create( AppResources.class );

	private MaterialComboBox<String> partitionDropDown;

	double percent;

	String str;

	List<String> partitionNames = new ArrayList<>();

	HashMap<String, String> partitionDropDownOptionMap = new HashMap<String, String>();

	public CreateBlinkPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData updateBlinkDataTable, List<String> partitionNames )
	{
		super( "Create Blink", "", "Create Blink", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateBlinkDataTable = updateBlinkDataTable;
		this.partitionNames = partitionNames;
		initialize();
		buttonAction();
	}

	@SuppressWarnings( "unchecked" )
	private void initialize()
	{
		createBlinkRow = new MaterialRow();

		blinkName = uiComponentsUtil.getTexBox( "Blink Name", "Enter The Blink Name", "s6" );
		blinkName.addValidator( uiComponentsUtil.getTextBoxEmptyValidator() );
		blinkName.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				blinkName.validate();
			}
		} );
		blinkName.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				blinkName.validate();
			}
		} );

		Iterator<String> iter = partitionNames.iterator();

		while ( iter.hasNext() )
		{
			str = iter.next().toString();
			partitionDropDownOptionMap.put( str, str );
		}

		partitionDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( partitionDropDownOptionMap, "Partition Name", "s6" );

		if ( partitionNames == null || partitionNames.isEmpty() )
		{
			partitionDropDown.setErrorText( "No blink partition found." );
		}

		partitionDropDown.addValueChangeHandler( new ValueChangeHandler<List<String>>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<List<String>> event )
			{
				validatePartitionDropDown();
			}
		} );

		createBlinkRow.add( blinkName );

		createBlinkRow.add( partitionDropDown );

		getBodyPanel().add( createBlinkRow );
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
					createNewBlink();
				}
			}
		} );
	}

	private void getProgressOfBlink()
	{
		CommonServiceProvider.getCommonService().getProgress( new ApplicationCallBack<ProgressModel>()
		{
			@Override
			public void onSuccess( ProgressModel progress )
			{
				percent = progress.getProgress();
				LoggerUtil.log( Double.toString( percent ) );
				/*
				 * circEvents.addProgressHandler(event -> { int percent = (int)
				 * (event.getProgress()); circEvents.setText(percent + "%"); });
				 */
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );

	}

	private void createNewBlink()
	{
		MaterialLoader.loading( true, getBodyPanel() );
		createBlink = new CreateBlinkModel( blinkName.getValue().toString(), partitionDropDown.getValue().get( 0 ).toString() );
		CommonServiceProvider.getCommonService().createBlink( createBlink, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				getProgressOfBlink();
				close();
				LoggerUtil.log( "create Blink" );
				MaterialToast.fireToast( "Sent request to create " + createBlink.getName() + ".", "rounded" );
				if ( updateBlinkDataTable != null )
				{
					updateBlinkDataTable.executeWithData( true );
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
		if ( blinkName.validate() && validatePartitionDropDown() )
		{
			valid = true;
		}
		return valid;
	}

	private boolean validatePartitionDropDown()
	{
		boolean isValid = false;
		if ( partitionNames.isEmpty() || partitionDropDown.getValue() == null || partitionDropDown.getValue().get( 0 ).equalsIgnoreCase( "none" ) )
		{
			partitionDropDown.setErrorText( "No blink partition found." );
		}
		else
		{
			partitionDropDown.clearErrorText();
			partitionDropDown.setSuccessText( "" );
			isValid = true;
		}
		return isValid;
	}
}
