package com.fmlb.forsaos.client.application.vm.details;

import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.SizeBox;
import com.fmlb.forsaos.client.application.models.ExpandVMModel;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class ExpandVMPopup extends CommonPopUp
{

	private SizeBox sizeBox;

	private IcommandWithData updateVMLEMDataTblCmd;

	private VMModel VMModel;

	private List<LEMModel> lemModels;

	private UIComponentsUtil uiComponentsUtil;

	String formattedAvailableSize;

	private double availableSizeinBytes;

	private MaterialComboBox<LEMModel> lemDropDown;

	private MaterialCheckBox forceExpandCheckBox;

	HashMap<String, String> lemOptionMap = new HashMap<String, String>();

	private int counterForceExp = 0;

	AppResources resources = GWT.create( AppResources.class );

	private MaterialLabel currentSizeVal;

	private MaterialPanel forceExpandCheckBox_Panel;

	public ExpandVMPopup( String formattedAvailableSize, double availableSizeinBytes, VMModel VMModel, List<LEMModel> lemModels, UIComponentsUtil uiComponentsUtil, IcommandWithData updateVMLEMDataTblCmd )
	{
		super( "Expand LEM", "", "Expand LEM", true );
		this.formattedAvailableSize = formattedAvailableSize;
		this.availableSizeinBytes = availableSizeinBytes;
		this.VMModel = VMModel;
		this.lemModels = lemModels;
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateVMLEMDataTblCmd = updateVMLEMDataTblCmd;
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow vmDataTableHdrRow = new MaterialRow();
		MaterialLabel avlCntrSpcHdr = this.uiComponentsUtil.getLabel( "Available Size", "s4" );
		MaterialLabel vmNameHdr = this.uiComponentsUtil.getLabel( "VM Name", "s4" );
		MaterialLabel currentSizeHdr = this.uiComponentsUtil.getLabel( "LEM Size", "s4" );

		MaterialRow vmDataTableValRow = new MaterialRow();
		//MaterialLabel avlCntrSpcVal = this.uiComponentsUtil.getLabel( "new " + this.VMModel.getAvlCntrSpace(), "s4" );
		MaterialLabel avlCntrSpcVal = this.uiComponentsUtil.getLabel( this.formattedAvailableSize, "s4" );

		MaterialLabel vmNameVal = this.uiComponentsUtil.getLabel( this.VMModel.getName(), "s4" );

		MaterialRow newSize = new MaterialRow();
		newSize.addStyleName( resources.style().expand_size_row() );
		sizeBox = new SizeBox( "Size", "", "s6", "s8", this.availableSizeinBytes );
		sizeBox.getMemoryUnitComboBox().setSelectedIndex( 1 );
		sizeBox.getMemoryUnitComboBox().getListbox().remove( 3 );
		sizeBox.addStyleName( resources.style().expand_vm_size() );

		lemDropDown = ( MaterialComboBox<LEMModel> ) uiComponentsUtil.getAssisgnToLEMDropDown( lemModels, "Select LEM", "s6", false );
		lemDropDown.addStyleName( resources.style().expand_vm_select_lem() );
		lemDropDown.setSelectedIndex( 0 );
		lemDropDown.addValueChangeHandler( new ValueChangeHandler<List<LEMModel>>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<List<LEMModel>> event )
			{
				currentSizeVal.setText( Converter.getFormatKiBSize( lemDropDown.getValue().get( 0 ).getSize(), MemorySizeType.KiB.getValue() ) );

			}
		} );
		currentSizeVal = this.uiComponentsUtil.getLabel( Converter.getFormatKiBSize( lemModels.get( 0 ).getSize(), MemorySizeType.KiB.getValue() ), "s4" );

		//createAttachLEMRadioButtons();

		forceExpandCheckBox = new MaterialCheckBox( "Force Expand" );
		forceExpandCheckBox.addStyleName( resources.style().expand_vm_force_expand() );
		forceExpandCheckBox.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( !forceExpandCheckBox.getValue() )
				{
					counterForceExp = 0;
					forceExpandCheckBox_Panel.setVisible( false );
				}
				else if ( forceExpandCheckBox.getValue() )
				{
					counterForceExp = 1;
					forceExpandCheckBox_Panel.setVisible( true );
				}

			}
		} );

		attachforceExpandCheckBox_Panel();

		newSize.add( lemDropDown );
		newSize.add( sizeBox );
		newSize.add( forceExpandCheckBox );
		newSize.add( forceExpandCheckBox_Panel );
		forceExpandCheckBox_Panel.setVisible( false );
		vmDataTableHdrRow.add( avlCntrSpcHdr );
		vmDataTableHdrRow.add( vmNameHdr );
		vmDataTableHdrRow.add( currentSizeHdr );
		vmDataTableHdrRow.addStyleName( resources.style().popup_data_header() );
		vmDataTableHdrRow.addStyleName( "popup_data_header" );

		vmDataTableValRow.add( avlCntrSpcVal );
		vmDataTableValRow.add( vmNameVal );
		vmDataTableValRow.add( currentSizeVal );
		vmDataTableValRow.addStyleName( resources.style().popup_data_value() );
		vmDataTableValRow.addStyleName( "popup_data_value" );

		getBodyPanel().add( vmDataTableHdrRow );
		getBodyPanel().add( vmDataTableValRow );
		getBodyPanel().add( newSize );
	}

	private void attachforceExpandCheckBox_Panel()
	{

		forceExpandCheckBox_Panel = new MaterialPanel();

		MaterialLabel expandLable = new MaterialLabel( "Note : LEM expansion while VM is running can be dangerous and not recommended. Selecting force expand will expand the VM while IO is running on the LEM and will cause a slight freeze in IO transactions." );
		forceExpandCheckBox_Panel.add( expandLable );

		forceExpandCheckBox_Panel.addStyleName( resources.style().expand_vm_force_error_txt() );
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
					/*	if ( forceExpandCheckBox.getValue() && counterForceExp == 1 )
					{
						MaterialDialog materialDialog = uiComponentsUtil.getMaterialDialog( "Force Expand", "LEM expansion while VM is running can be dangerous and not recommended. Selecting force expand will expand the VM while IO is running on the LEM and will cause a slight freeze in IO transactions.", "Close", new Icommand()
						{
					
							@Override
							public void execute()
							{
								counterForceExp = 3;
					
							}
						} );
						materialDialog.open();
						return;
					}
					*/
					LoggerUtil.log( "expand lem" );
					ExpandVMModel expandVMModel = new ExpandVMModel( VMModel.getName(), sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), lemDropDown.getValue().get( 0 ).getLemName(), Double.valueOf( sizeBox.getSizeBox().getValue().toString() ).longValue(), forceExpandCheckBox.getValue() );
					MaterialLoader.loading( true, getBodyPanel() );
					CommonServiceProvider.getCommonService().expandVM( expandVMModel, new ApplicationCallBack<Boolean>()
					{

						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							MaterialToast.fireToast( lemDropDown.getValue().get( 0 ).getLemName() + " Expanded..!", "rounded" );
							if ( updateVMLEMDataTblCmd != null )
							{
								updateVMLEMDataTblCmd.executeWithData( Converter.converToBytes( sizeBox.getSizeBox().getValue(), sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString() ) );
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
		if ( sizeBox.validate() )
		{
			valid = true;
		}
		else
		{
			sizeBox.validate();
		}
		return valid;

	}

}
