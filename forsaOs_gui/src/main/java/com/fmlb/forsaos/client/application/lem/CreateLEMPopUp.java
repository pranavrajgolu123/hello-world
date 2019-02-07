package com.fmlb.forsaos.client.application.lem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.SizeBox;
import com.fmlb.forsaos.client.application.models.CreateLEMandAssisgnToVMmodel;
import com.fmlb.forsaos.client.application.models.CreateMultiLEMModel;
import com.fmlb.forsaos.client.application.models.LEMJSONModel;
import com.fmlb.forsaos.client.application.models.OSFlagType;
import com.fmlb.forsaos.client.application.models.RTMModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CreateLEMPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox lenName;

	private MaterialIntegerBox quantity;

	//private MaterialIntegerBox size;

	//private MaterialComboBox< ? > memorySizeType;

	private SizeBox sizeBox;

	private MaterialComboBox< ? > sectorSizeDropDown;

	private MaterialComboBox< ? > osSystemFlag;

	private MaterialComboBox< ? > assignToVM;

	private IcommandWithData updateLEMDataTableCmd;

	private List<VMModel> vmModels;

	private double availableSizeinBytes;

	private MaterialRow createLEMRow;

	public CreateLEMPopUp( String formattedAvailableSize, double availableSizeinBytes, UIComponentsUtil uiComponentsUtil, IcommandWithData updateLEMDataTableCmd, List<VMModel> vmModels )
	{
		super( "Create LEM", " ( Available space: " + formattedAvailableSize + " )", "Create LEM", true );
		this.availableSizeinBytes = availableSizeinBytes;
		this.updateLEMDataTableCmd = updateLEMDataTableCmd;
		this.vmModels = vmModels;
		this.uiComponentsUtil = uiComponentsUtil;
		LoggerUtil.log( "initialize create lem popop" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		createLEMRow = new MaterialRow();

		lenName = uiComponentsUtil.getTexBox( "LEM Name", "", "s6" );
		lenName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		lenName.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				lenName.validate();
			}
		} );
		lenName.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				lenName.validate();
			}
		} );

		quantity = uiComponentsUtil.getIntegerBox( "Quantity", "", "s6" );
		quantity.setMin( "1" );
		quantity.setValue( 1 );
		quantity.addValueChangeHandler( new ValueChangeHandler<Integer>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<Integer> event )
			{
				sizeBox.setMultiplicationFactor( quantity.getValue() );
				sizeBox.validate();
			}
		} );
		// size = uiComponentsUtil.getIntegerBox( "Size", "", "s3" );

		// memorySizeType = uiComponentsUtil.createMemorySizeTypeDropDown( "", "s3" );
		// memorySizeType.setHideSearch( true );

		sizeBox = new SizeBox( "Size", "", "s6", "s8", this.availableSizeinBytes );
		sizeBox.getMemoryUnitComboBox().setSelectedIndex( 1 );
		sizeBox.getMemoryUnitComboBox().getListbox().remove( 3 );

		sectorSizeDropDown = createSectorSizeDropDown( "Sector size", "s6" );
		sectorSizeDropDown.setHideSearch( true );

		osSystemFlag = createOSSystemFlagDropDown( "Operating system flag", "s6" );

		assignToVM = this.uiComponentsUtil.getAssisgnToVMDropDown( this.vmModels, "Assign to VM", "s6" );
		assignToVM.setLabel( "Assign to VM" );

		createLEMRow.add( lenName );
		createLEMRow.add( quantity );
		//		createLEMRow.add( size );
		//		createLEMRow.add( memorySizeType );
		createLEMRow.add( sizeBox );
		//		createLEMRow.add( sectorSizeDropDown );
		createLEMRow.add( osSystemFlag );
		createLEMRow.add( assignToVM );
		getBodyPanel().add( createLEMRow );
	}

	private MaterialComboBox< ? > createOSSystemFlagDropDown( String label, String colSpec )
	{
		HashMap<String, String> osSystemFlagOptionMap = new HashMap<String, String>();
		osSystemFlagOptionMap.put( OSFlagType.EXT2.getValue(), OSFlagType.EXT2.getValue() );
		osSystemFlagOptionMap.put( OSFlagType.EXT3.getValue(), OSFlagType.EXT3.getValue() );
		osSystemFlagOptionMap.put( OSFlagType.EXT4.getValue(), OSFlagType.EXT4.getValue() );
		osSystemFlagOptionMap.put( OSFlagType.FAT16.getValue(), OSFlagType.FAT16.getValue() );
		osSystemFlagOptionMap.put( OSFlagType.FAT32.getValue(), OSFlagType.FAT32.getValue() );
		osSystemFlagOptionMap.put( OSFlagType.HFS.getValue(), OSFlagType.HFS.getValue() );
		osSystemFlagOptionMap.put( OSFlagType.HFSPLUS.getValue(), OSFlagType.HFSPLUS.getValue() );
		osSystemFlagOptionMap.put( OSFlagType.HFSJPLUS.getValue(), OSFlagType.HFSJPLUS.getValue() );
		osSystemFlagOptionMap.put( OSFlagType.NTFS.getValue(), OSFlagType.NTFS.getValue() );
		osSystemFlagOptionMap.put( OSFlagType.ZFS.getValue(), OSFlagType.ZFS.getValue() );
		MaterialComboBox< ? > osSystemFlag = uiComponentsUtil.getComboBox( osSystemFlagOptionMap, label, colSpec );
		return osSystemFlag;
	}

	private MaterialComboBox< ? > createSectorSizeDropDown( String label, String colSpec )
	{
		// this data needs to be fetched
		HashMap<String, String> sectorSizeMap = new HashMap<String, String>();
		sectorSizeMap.put( "4096", "4096" );
		MaterialComboBox< ? > sectorSizeListBox = uiComponentsUtil.getComboBox( sectorSizeMap, label, colSpec );
		return sectorSizeListBox;
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
					CommonServiceProvider.getCommonService().getRTM( null, new ApplicationCallBack<RTMModel>()
					{

						@Override
						public void onFailure( Throwable caught )
						{
							super.onFailureShowErrorPopup( caught, "Unable to get RTM details" );
						}

						@Override
						public void onSuccess( RTMModel result )
						{
							if ( result != null )
							{
								createLEM( result );
							}
							else
							{
								MaterialLoader.loading( false, getBodyPanel() );
								ArrayList<String> errorMsgs = new ArrayList<>();
								errorMsgs.add( "Unable to get RTM details." );
								ErrorPopup errorPopup = new ErrorPopup( errorMsgs, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
								errorPopup.open();
							}
						}
					} );

				}
			}
		} );
	}

	private void createLEM( RTMModel rtmModel )
	{
		List<VMModel> vmModels = ( List<VMModel> ) assignToVM.getValue();
		VMModel selcted = vmModels.get( 0 );
		if ( !selcted.getVmName().equalsIgnoreCase( "None" ) )
		{
			LoggerUtil.log( "create lem with VM" );
			Integer quantityValue = Integer.valueOf( quantity.getValue() );
			LEMJSONModel[] lemjsonModelArray = new LEMJSONModel[quantityValue];
			for ( int i = 0; i < quantityValue; i++ )
			{
				String lemName = lenName.getValue();
				if ( quantityValue > 1 )
				{
					lemName = lemName + "_" + ( i );
				}
				LEMJSONModel lemjsonModel = new LEMJSONModel( lemName, sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), Double.valueOf( sizeBox.getSizeBox().getValue().toString() ).longValue(), rtmModel.getName() );
				lemjsonModelArray[i] = lemjsonModel;
			}
			CreateLEMandAssisgnToVMmodel createLEMandAssisgnToVMmodel = new CreateLEMandAssisgnToVMmodel( lemjsonModelArray, selcted.getVmName(), selcted.get_id().get$oid() );
			CommonServiceProvider.getCommonService().createLemAndAssisnToVm( createLEMandAssisgnToVMmodel, new ApplicationCallBack<Boolean>()
			{
				@Override
				public void onSuccess( Boolean result )
				{
					MaterialLoader.loading( false, getBodyPanel() );
					close();
					MaterialToast.fireToast( "LEM(s) Created..!", "rounded" );
					updateLEMDataTableCmd.executeWithData( true );
				}

				@Override
				public void onFailure( Throwable caught )
				{
					super.onFailureShowErrorPopup( caught, null );
				}
			} );
		}
		else if ( selcted.getVmName().equalsIgnoreCase( "None" ) && quantity != null && quantity.getValue() == 1 )
		{
			LoggerUtil.log( "create lem" );
			LEMJSONModel lemjsonModel = new LEMJSONModel( lenName.getValue(), sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), Long.valueOf( sizeBox.getSizeBox().getValue().toString() ), rtmModel.getName() );
			if((sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString() == "MB"  && sizeBox.getSizeBox().getValue() < 8)  || 
					(sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString() == "MB"  && sizeBox.getSizeBox().getValue() > 8388608)) {
				ArrayList<String> errorMsgs = new ArrayList<>();
				errorMsgs.add( "Lem Size Cannot Less Than 8 MB." );
				ErrorPopup errorPopup = new ErrorPopup( errorMsgs, ApplicationConstants.WARNING_POPUP_HEADER, "Lem Size", ApplicationConstants.CLOSE, true );
				errorPopup.open();
				MaterialLoader.loading( false, getBodyPanel() );
			
			}
			else if(sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString() == "TB"  && sizeBox.getSizeBox().getValue() > 8)  {
				ArrayList<String> errorMsgs = new ArrayList<>();
				errorMsgs.add( "Lem Size Cannot More Than 8 TB." );
				ErrorPopup errorPopup = new ErrorPopup( errorMsgs, ApplicationConstants.WARNING_POPUP_HEADER, "Lem Size", ApplicationConstants.CLOSE, true );
				errorPopup.open();
				MaterialLoader.loading( false, getBodyPanel() );
			}
			else if(sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString() == "GB"  && sizeBox.getSizeBox().getValue() > 8192)  {
				ArrayList<String> errorMsgs = new ArrayList<>();
				errorMsgs.add( "Lem Size Cannot More Than 8192 GB." );
				ErrorPopup errorPopup = new ErrorPopup( errorMsgs, ApplicationConstants.WARNING_POPUP_HEADER, "Lem Size", ApplicationConstants.CLOSE, true );
				errorPopup.open();
				MaterialLoader.loading( false, getBodyPanel() );
			}
			else {
			CommonServiceProvider.getCommonService().createLem( lemjsonModel, new ApplicationCallBack<Boolean>()
			{
				@Override
				public void onSuccess( Boolean result )
				{
					MaterialLoader.loading( false, getBodyPanel() );
					close();
					MaterialToast.fireToast( lemjsonModel.getName() + " Created..!", "rounded" );
					if ( updateLEMDataTableCmd != null )
					{
						updateLEMDataTableCmd.executeWithData( true );
					}
				}

				@Override
				public void onFailure( Throwable caught )
				{
					super.onFailureShowErrorPopup( caught, "Unable to create LEM" );
				}
			} );
			}
		}
		else if ( selcted.getVmName().equalsIgnoreCase( "None" ) && quantity.getValue() >= 1 )
		{
			LoggerUtil.log( "create multi lem" );
			Integer quantityValue = Integer.valueOf( quantity.getValue() );
			LEMJSONModel[] lemjsonModelArray = new LEMJSONModel[quantityValue];
			for ( int i = 0; i < quantityValue; i++ )
			{
				String lemName = lenName.getValue();
				if ( quantityValue > 1 )
				{
					lemName = lemName + "_" + ( i );
				}
				LEMJSONModel lemjsonModel = new LEMJSONModel( lemName, sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), Long.valueOf( sizeBox.getSizeBox().getValue().toString() ), rtmModel.getName() );
				lemjsonModelArray[i] = lemjsonModel;
			}
			CreateMultiLEMModel createMultiLEMModel = new CreateMultiLEMModel();
			createMultiLEMModel.setMulti( lemjsonModelArray );

			CommonServiceProvider.getCommonService().createMultiLem( createMultiLEMModel, new ApplicationCallBack<Boolean>()
			{
				@Override
				public void onSuccess( Boolean result )
				{
					MaterialLoader.loading( false, getBodyPanel() );
					close();
					MaterialToast.fireToast( "LEM(s) Created..!", "rounded" );
					if ( updateLEMDataTableCmd != null )
					{
						updateLEMDataTableCmd.executeWithData( true );
					}
				}

				@Override
				public void onFailure( Throwable caught )
				{
					super.onFailureShowErrorPopup( caught, "Unable to create LEM(s)" );
				}
			} );
		}
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( lenName.validate() && quantity.validate() && sizeBox.validate() )
		{
			valid = true;
		}
		else
		{
			lenName.validate();
			quantity.validate();
			sizeBox.validate();
		}
		return valid;

	}
}
