package com.fmlb.forsaos.client.application.vm.details;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CPUModel;
import com.fmlb.forsaos.client.application.models.CloneVMModel;
import com.fmlb.forsaos.client.application.models.ComboBoxModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.models.SoundDeviceModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.models.VideoDeviceModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.CheckBoxType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.FieldType;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

//ExpandLEMPopUp
public class VMDetailsSettings extends MaterialPanel
{

	MaterialTextBox cloneVMname;

	MaterialIntegerBox cloneQuantity;

	private VMModel vmModel;

	private UIComponentsUtil uiComponentsUtil;

	private VMNicsDataTable nicsDataTable;

	private VMlemsDataTable vMlemsDataTable;

	private VMExternalStorageDataTable vmExternalStorageDataTable;

	private VMIsoMountDataTable vmIsoMountDataTable;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private ArrayList<VideoDeviceModel> videoDeviceModels;

	private ArrayList<CPUModel> cpuModels;

	private MaterialRadioButton vnc;

	private MaterialRadioButton spice;

	private MaterialLabel spiceLabel;

	private List<LEMModel> lemModels = new ArrayList<>();

	private MaterialLabel memoryLabel;

	AppResources resources = GWT.create( AppResources.class );

	public VMDetailsSettings( VMModel vmModel, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		this.vmModel = vmModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		add( createDetailHeaderPanel() );
		add( createInformationDataPanel() );
		add( createNICsDataTable() );
		add( createLEMsDataTable() );
		add( createvmExternalStorageDataTable() );
		add( createISOMountPathDataTable() );
		add( createSettingPanel() );
		add( createCloneVMPanel() );
	}

	private MaterialRow createDetailHeaderPanel()
	{
		MaterialRow detailHeaderPanel = new MaterialRow();
		detailHeaderPanel.addStyleName( resources.style().VM_Detail() );
		detailHeaderPanel.setId( "informationPanel" + Document.get().createUniqueId() );
		detailHeaderPanel.setGrid( "s12" );
		detailHeaderPanel.addStyleName( "detail_header_penal" );

		MaterialLabel detailLabel = this.uiComponentsUtil.getLabel( "Information", "s12"/* , resources.style().lemDetail() */ );
		detailLabel.addStyleName( resources.style().detail_header() );
		detailLabel.setBorder( "2" );
		detailLabel.setGrid( "s9" );

		populateLEMModelsMap();
		/*MaterialButton expandVMBtn = getExpandVMBtn();
		expandVMBtn.setGrid( "s2" );
		enableDisableExpandVmBtn( expandVMBtn );*/

		MaterialColumn actionItemCol = new MaterialColumn();
		actionItemCol.setGrid( "s1" );
		actionItemCol.addStyleName( resources.style().action_item_bar() );
		MaterialButton editVMbtn = new MaterialButton( "", IconType.EDIT, ButtonType.FLAT );
		editVMbtn.addStyleName( resources.style().vm_information_edit() );
		editVMbtn.setTitle( "Edit VM" );
		editVMbtn.setIconPosition( IconPosition.RIGHT );
		editVMbtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				EditVMPopup editVMPopup = new EditVMPopup( uiComponentsUtil, vmModel );
				editVMPopup.open();
			}
		} );

		actionItemCol.add( editVMbtn );
		detailHeaderPanel.add( detailLabel );
		//		detailHeaderPanel.add( expandVMBtn );
		detailHeaderPanel.add( actionItemCol );
		return detailHeaderPanel;
	}

	private MaterialPanel createNICsDataTable()
	{
		nicsDataTable = new VMNicsDataTable( vmModel, this.uiComponentsUtil, placeManager, currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				// TODO Auto-generated method stub

			}
		} );
		nicsDataTable.addStyleName( "vm_data_row" );
		return nicsDataTable;
	}

	private MaterialPanel createLEMsDataTable()
	{
		vMlemsDataTable = new VMlemsDataTable( vmModel, this.uiComponentsUtil, placeManager, currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				// TODO Auto-generated method stub

			}
		} );
		vMlemsDataTable.addStyleName( "vm_data_row" );
		return vMlemsDataTable;
	}

	private MaterialPanel createvmExternalStorageDataTable()
	{
		vmExternalStorageDataTable = new VMExternalStorageDataTable( vmModel, uiComponentsUtil, placeManager, currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				// TODO Auto-generated method stub

			}
		} );
		vmExternalStorageDataTable.addStyleName( "vm_data_row" );
		return vmExternalStorageDataTable;
	}

	private MaterialPanel createISOMountPathDataTable()
	{
		vmIsoMountDataTable = new VMIsoMountDataTable( vmModel, this.uiComponentsUtil, placeManager, currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				// TODO Auto-generated method stub

			}
		} );
		vmIsoMountDataTable.addStyleName( "vm_data_row" );
		return vmIsoMountDataTable;
	}

	private MaterialRow createInformationDataPanel()
	{
		/*MaterialRow infoRow = new MaterialRow();
		MaterialColumn col1 = new MaterialColumn();
		col1.setGrid( "s6" );
		MaterialColumn col2 = new MaterialColumn();
		col2.setGrid( "s6" );
		
		col1.add( this.uiComponentsUtil.getLabel( "CPU Cores", "s4" ) );
		col1.add( this.uiComponentsUtil.getLabel( String.valueOf( vmModel.getCores() ), "s8" ) );
		col2.add( this.uiComponentsUtil.getLabel( "Memory", "s2" ) );
		col2.add( this.uiComponentsUtil.getLabel( Converter.getFormatKiBSize( vmModel.getMemory(), MemorySizeType.KiB.getValue() ), "s10" ) );
		infoRow.add( col1 );
		infoRow.add( col2 );
		infoRow.addStyleName( resources.style().lem_detail_row() );
		return infoRow;*/

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );

		MaterialCollectionItem collectionItem1 = new MaterialCollectionItem();
		collectionItem1.setGrid( "s6" );
		collectionItem1.add( this.uiComponentsUtil.getLabel( "CPU Cores", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary1 = new MaterialCollectionSecondary();

		collectionSecondary1.add( this.uiComponentsUtil.getLabel( String.valueOf( vmModel.getCores() ), "", resources.style().displayInline() ) );
		collectionItem1.add( collectionSecondary1 );

		MaterialCollectionItem collectionItem2 = new MaterialCollectionItem();
		collectionItem2.setGrid( "s6" );
		collectionItem2.add( this.uiComponentsUtil.getLabel( "Memory", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary2 = new MaterialCollectionSecondary();
		memoryLabel = this.uiComponentsUtil.getLabel( Converter.getFormatKiBSize( vmModel.getMemory(), MemorySizeType.KiB.getValue() ), "", resources.style().displayInline() );
		collectionSecondary2.add( memoryLabel );
		collectionItem2.add( collectionSecondary2 );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );

		MaterialRow row = new MaterialRow();
		row.addStyleName( resources.style().vm_information_row() );
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s12" );
		column1.addStyleName( resources.style().vm_setting_inform_datail_column() );
		column1.add( collection1 );
		row.add( column1 );
		return row;

	}

	private MaterialPanel createSettingPanel()
	{
		MaterialPanel settingsPanel = new MaterialPanel();
		MaterialLabel settingsLabel = this.uiComponentsUtil.getLabel( "Settings", "s12", resources.style().vm_setting_header() );
		settingsPanel.add( settingsLabel );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( resources.style().vm_setting_row() );

		MaterialPanel checkBoxwrapper = new MaterialPanel();
		checkBoxwrapper.setGrid( "s6" );
		MaterialCheckBox enableBootMenuCheckBox = new MaterialCheckBox( "Enable boot menu", CheckBoxType.FILLED );
		checkBoxwrapper.add( enableBootMenuCheckBox );
		enableBootMenuCheckBox.addStyleName( resources.style().vm_enable_boot() );
		//enableBootMenuCheckBox.setText( "Enable boot menu", Direction.RTL );
		LoggerUtil.log( enableBootMenuCheckBox.getTextDirection().toString() );
		ArrayList<SoundDeviceModel> soundDeviceModels = new ArrayList<>();
		soundDeviceModels.add( new SoundDeviceModel( "sound device 1", "id1" ) );
		MaterialComboBox< ? extends ComboBoxModel> soundDeviceDropDown = uiComponentsUtil.getComboBoxModelDropDownWithoutNone( soundDeviceModels, "Sound Device", "s6" );
		soundDeviceDropDown.setFieldType( FieldType.ALIGNED_LABEL );
		soundDeviceDropDown.setLabel( "Sound Device" );
		soundDeviceDropDown.addStyleName( resources.style().vm_details_setting_row() );

		MaterialComboBox< ? extends ComboBoxModel> cpuModelsDropDown = uiComponentsUtil.getComboBoxModelDropDown( getCpuModels(), "CPU Model", "s6" );
		cpuModelsDropDown.setFieldType( FieldType.ALIGNED_LABEL );
		cpuModelsDropDown.setLabel( "CPU Model" );
		cpuModelsDropDown.addStyleName( resources.style().vm_details_setting_row() );
		cpuModelsDropDown.setSelectedIndex( getSelectedCpuModel() );
		cpuModelsDropDown.setHideSearch( true );
		cpuModelsDropDown.addValueChangeHandler( valueChangeEvent -> {
			List< ? extends ComboBoxModel> value = valueChangeEvent.getValue();
			String prevSelection;
			if ( value.get( 0 ).getDisplayName().equalsIgnoreCase( "none" ) )
			{
				prevSelection = "None";
				vmModel.setCpu_model( "" );
			}
			else
			{
				CPUModel cpuModel = ( CPUModel ) value.get( 0 );
				prevSelection = vmModel.getCpu_model();
				vmModel.setCpu_model( cpuModel.getName() );
			}

			CommonServiceProvider.getCommonService().updateVMDetails( vmModel, "cpumodel", new ApplicationCallBack<Boolean>()
			{
				@Override
				public void onSuccess( Boolean result )
				{

				}

				@Override
				public void onFailure( Throwable caught )
				{
					vmModel.setCpu_model( prevSelection );
					cpuModelsDropDown.setSelectedIndex( getSelectedCpuModel() );
					super.onFailureShowErrorPopup( caught, null );
				}
			} );
		} );

		MaterialComboBox< ? extends ComboBoxModel> videoDeviceDropDown = uiComponentsUtil.getComboBoxModelDropDownWithoutNone( getVideoModels(), "Video Device", "s6" );
		videoDeviceDropDown.setFieldType( FieldType.ALIGNED_LABEL );
		videoDeviceDropDown.setLabel( "Video Device" );
		videoDeviceDropDown.addStyleName( resources.style().vm_details_setting_row() );
		videoDeviceDropDown.setSelectedIndex( getSelectedVideoModel() );
		videoDeviceDropDown.setHideSearch( true );
		videoDeviceDropDown.addValueChangeHandler( valueChangeEvent -> {
			VideoDeviceModel videoDeviceModel = ( VideoDeviceModel ) videoDeviceDropDown.getSingleValue();
			String prevSelection = vmModel.getVideo_type();
			vmModel.setVideo_type( videoDeviceModel.getName() );
			CommonServiceProvider.getCommonService().updateVMDetails( vmModel, "videotype", new ApplicationCallBack<Boolean>()
			{
				@Override
				public void onSuccess( Boolean result )
				{

				}

				@Override
				public void onFailure( Throwable caught )
				{
					vmModel.setVideo_type( prevSelection );
					videoDeviceDropDown.setSelectedIndex( getSelectedVideoModel() );
					super.onFailureShowErrorPopup( caught, null );
				}
			} );
		} );

		//		firstRow.add( checkBoxwrapper );
		//		firstRow.add( soundDeviceDropDown );
		firstRow.add( cpuModelsDropDown );
		firstRow.add( videoDeviceDropDown );
		firstRow.add( getGraphicDriverSelection() );
		settingsPanel.add( firstRow );
		return settingsPanel;
	}

	private MaterialPanel getGraphicDriverSelection()
	{
		MaterialPanel graphicDriverSelection = new MaterialPanel();
		graphicDriverSelection.setGrid( "s6" );
		graphicDriverSelection.addStyleName( resources.style().graphic_Driver_Selection() );

		MaterialLabel graphicDriver = new MaterialLabel( "Graphic Driver" );
		graphicDriver.setGrid( "s3" );

		vnc = new MaterialRadioButton( "graphic_driver", "VNC" );
		vnc.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( vnc.getValue() )
				{
					updateGraphicDriver( "vnc" );
					spiceLabel.setVisible( false );
				}
			}

		} );
		MaterialPanel vncPanel = new MaterialPanel();
		vncPanel.add( vnc );
		vncPanel.setGrid( "s2" );
		vncPanel.addStyleName( resources.style().vnc_Panel() );

		spice = new MaterialRadioButton( "graphic_driver", "SPICE" );
		spice.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( spice.getValue() )
				{
					//					MaterialToast.fireToast( "Spice can be used only with external viewer.", "rounded" );
					updateGraphicDriver( "spice" );
					spiceLabel.setVisible( true );
				}
			}
		} );
		MaterialPanel spicePanel = new MaterialPanel();
		spiceLabel = new MaterialLabel( "Spice can be used only with external remote viewer." );
		spiceLabel.setFontSize( "0.9em" );
		spiceLabel.setTextColor( Color.RED );
		spicePanel.add( spice );
		spicePanel.add( spiceLabel );
		spicePanel.setGrid( "s6" );
		spiceLabel.addStyleName( resources.style().vm_details_setting_spiceLabel() );

		graphicDriverSelection.add( graphicDriver );
		graphicDriverSelection.add( vncPanel );
		graphicDriverSelection.add( spicePanel );

		setGraphicDriver();

		return graphicDriverSelection;
	}

	private void updateGraphicDriver( String graphic_driver )
	{
		String previousSelection = vmModel.getGraphic_driver();
		vmModel.setGraphic_driver( graphic_driver );
		CommonServiceProvider.getCommonService().updateVMDetails( vmModel, "graphicdriver", new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
			}

			@Override
			public void onFailure( Throwable caught )
			{
				vmModel.setGraphic_driver( previousSelection );
				setGraphicDriver();
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void setGraphicDriver()
	{
		if ( vmModel.getGraphic_driver().equalsIgnoreCase( "vnc" ) )
		{
			vnc.setValue( true );
			spiceLabel.setVisible( false );
		}
		else if ( vmModel.getGraphic_driver().equalsIgnoreCase( "spice" ) )
		{
			spice.setValue( true );
			spiceLabel.setVisible( true );
		}
		else
		{
			vnc.setValue( false );
			spice.setValue( false );
			spiceLabel.setVisible( false );
		}
	}

	private ArrayList<CPUModel> getCpuModels()
	{
		cpuModels = new ArrayList<CPUModel>();
		cpuModels.add( new CPUModel( "Haswell-noTSX", "1" ) );
		return cpuModels;
	}

	private int getSelectedCpuModel()
	{
		int index = 0;
		if ( vmModel.getCpu_model().equalsIgnoreCase( "" ) )
		{
			return index;
		}
		for ( CPUModel cpuModel : cpuModels )
		{
			if ( vmModel.getCpu_model().equalsIgnoreCase( cpuModel.getName() ) )
			{
				return index;
			}
			index++;
		}
		return -1;
	}

	private int getSelectedVideoModel()
	{
		int index = 0;
		for ( VideoDeviceModel videoDeviceModel : videoDeviceModels )
		{
			if ( vmModel.getVideo_type().equalsIgnoreCase( videoDeviceModel.getName() ) )
			{
				return index;
			}
			index++;
		}
		return -1;
	}

	private ArrayList<VideoDeviceModel> getVideoModels()
	{
		videoDeviceModels = new ArrayList<>();
		videoDeviceModels.add( new VideoDeviceModel( "cirrus", "1" ) );
		videoDeviceModels.add( new VideoDeviceModel( "qxl", "2" ) );
		videoDeviceModels.add( new VideoDeviceModel( "vga", "3" ) );
		videoDeviceModels.add( new VideoDeviceModel( "vmvga", "4" ) );
		return videoDeviceModels;
	}

	private MaterialPanel createCloneVMPanel()
	{

		MaterialPanel cloneVMPanel = new MaterialPanel();
		MaterialLabel cloneLabel = this.uiComponentsUtil.getLabel( "Clone VM", "s12", resources.style().vm_setting_header() );

		MaterialRow cloneRow = new MaterialRow();
		cloneRow.addStyleName( resources.style().vm_details_clone_vm() );
		cloneRow.addStyleName( "vm_details_clone_vm" );
		//cloneRow.addStyleName( resources.style().data_body_row() );
		MaterialButton cloneVMBtn = new MaterialButton( "Clone VM" );
		cloneVMBtn.addStyleName( resources.style().clone_lem_btn() );
		//cloneVMBtn.setType( ButtonType.FLAT );
		cloneVMBtn.setIconPosition( IconPosition.RIGHT );
		cloneVMBtn.setIconType( IconType.CONTENT_COPY );
		cloneVMBtn.setGrid( "s2" );
		cloneVMname = uiComponentsUtil.getTexBox( "Clone VM", "Type Clone name", "s5" );
		cloneVMname.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		cloneQuantity = uiComponentsUtil.getIntegerBox( "Quantity", "", "s5" );
		cloneQuantity.setValue( 1 );
		cloneQuantity.setMin( "1" );
		cloneRow.add( cloneVMname );
		cloneRow.add( cloneQuantity );
		cloneRow.add( cloneVMBtn );
		cloneVMPanel.add( cloneLabel );
		cloneVMPanel.add( cloneRow );
		cloneVMBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				if ( validateCloneVM() )
				{

					CloneVMModel cloneVMModel = new CloneVMModel();
					cloneVMModel.setClone_name( cloneVMname.getValue() );
					cloneVMModel.setInstances( cloneQuantity.getValue() );
					cloneVMModel.setName( vmModel.getVmName() );
					cloneVM( cloneVMModel );
				}

			}

		} );
		return cloneVMPanel;
	}

	private void onCloneSucess()
	{
		MaterialLoader.loading( false );
		cloneVMname.clear();
		cloneQuantity.setValue( 1 );
		MaterialToast.fireToast( "Clones(s) Created..!", "rounded" );
	}

	private void cloneVM( CloneVMModel cloneVMModel )
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().cloneVM( cloneVMModel, new ApplicationCallBack<Boolean>()
		{

			@Override
			public void onSuccess( Boolean result )
			{
				onCloneSucess();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );

			}
		} );
	}

	private boolean validateCloneVM()
	{
		boolean valid = false;
		if ( cloneVMname.validate() && cloneQuantity.validate() )
		{
			valid = true;
		}
		else
		{
			cloneVMname.validate();
			cloneQuantity.validate();
		}
		return valid;

	}

	private void populateLEMModelsMap()
	{
		if ( vmModel.getDisks() != null && !vmModel.getDisks().isEmpty() )
		{
			CommonServiceProvider.getCommonService().getLems( null, false, new ApplicationCallBack<List<LEMModel>>()
			{
				@Override
				public void onSuccess( List<LEMModel> result )
				{
					MaterialLoader.loading( true );
					lemModels.clear();
					List<LEMModel> assignedLEMModels = new ArrayList<LEMModel>();

					for ( LEMModel lemModel : result )
					{
						for ( String lemId : vmModel.getDisks() )
						{
							if ( lemModel.getId().equals( lemId ) )
							{
								assignedLEMModels.add( lemModel );
								break;
							}
						}
					}
					lemModels.addAll( assignedLEMModels );
					MaterialLoader.loading( false );
				}

				@Override
				public void onFailure( Throwable caught )
				{
					super.onFailureShowErrorPopup( caught, null );
				}
			} );
		}

	}

}