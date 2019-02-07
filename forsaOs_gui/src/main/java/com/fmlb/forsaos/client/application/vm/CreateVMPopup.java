package com.fmlb.forsaos.client.application.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.SizeBox;
import com.fmlb.forsaos.client.application.models.BridgeJSONModel;
import com.fmlb.forsaos.client.application.models.CloneLEMModel;
import com.fmlb.forsaos.client.application.models.LEMJSONModel;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.NameModel;
import com.fmlb.forsaos.client.application.models.RTMModel;
import com.fmlb.forsaos.client.application.models.VMCreateAttachLemModel;
import com.fmlb.forsaos.client.application.models.VMCreateCloneLemModel;
import com.fmlb.forsaos.client.application.models.VMCreateModel;
import com.fmlb.forsaos.client.application.models.VMCreateWithNewLemModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.base.validator.Validator;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CreateVMPopup extends CommonPopUp
{
	private List<LEMModel> lemModels = new ArrayList<LEMModel>();

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow createVMRow;

	private MaterialTextBox vmName;

	private MaterialIntegerBox quantity;

	// private MaterialIntegerBox size;

	private SizeBox sizeBox;

	private SizeBox newLEMsizeBox;

	//private MaterialComboBox< ? > memorySizeType;

	private MaterialIntegerBox cpuCores;

	//	private MaterialIntegerBox spicePort;

	private MaterialRadioButton newLem;
	//	private MaterialCheckBox newLem;

	private MaterialPanel newLemRadioPanel;

	private MaterialRadioButton attachLem;

	private MaterialPanel attachLemRadioPanel;

	private MaterialRadioButton cloneLem;

	private MaterialPanel cloneLemRadioPanel;

	private MaterialPanel radioPanel;

	private MaterialComboBox<LEMModel> cloneSource;

	private MaterialTextBox clonedLEM;

	private MaterialPanel cloneLemPanel;

	private MaterialComboBox<LEMModel> selectUnAssignedLEM;

	private MaterialPanel existingLemPanel;

	private MaterialTextBox newLemName;

	// private MaterialIntegerBox lemSize;

	// private MaterialComboBox< ? > lemMemorySizeType;

	private MaterialComboBox< ? > sectorSizeDropDown;

	private MaterialPanel newLemPanel;

	private MaterialPanel attachLemMainPanel;

	private MaterialPanel attachNetworksPanel;

	private MaterialPanel attachIsoPanel;

	private MaterialLabel networkAdpt;

	private MaterialLabel isoMountPaths;

	private MaterialButton addNetwork;

	private MaterialButton addISO;

	private IcommandWithData updateVMDataTableCmd;

	private List<VMModel> vmModels;

	private Set<String> assignedLemIds = new HashSet<String>();

	private double availableSizeinBytes;

	private List<BridgeJSONModel> bridgeJSONModels;

	private List<NetworkAdaptersPanel> networkAdaptersPanels;

	private List<ISOMountPathPanel> isoMountPathPanels = new ArrayList<ISOMountPathPanel>();;

	private boolean createVMSuccess = false;

	AppResources resources = GWT.create( AppResources.class );

	public CreateVMPopup( String formattedAvailableSize, double availableSizeinBytes, UIComponentsUtil uiComponentsUtil, IcommandWithData updateVMDataTableCmd, List<VMModel> vmModels )
	{
		super( "Create VM", " ( Available space: " + formattedAvailableSize + " )", "Create VM", true );
		this.availableSizeinBytes = availableSizeinBytes;
		this.updateVMDataTableCmd = updateVMDataTableCmd;
		this.uiComponentsUtil = uiComponentsUtil;
		this.vmModels = vmModels;
		this.getButtonRow().addStyleName( resources.style().repoPopupBtnRow() );

		LoggerUtil.log( "Disk" );
		for ( VMModel vmModel : vmModels )
		{
			for ( String disk : vmModel.getDisks() )
			{
				assignedLemIds.add( disk );
				LoggerUtil.log( disk );
			}
		}

		LoggerUtil.log( "initialize create VM popop" );

		initialize();
		buttonAction();
		initializeLEMPanel();
		initializeNetworkPanel();
		initializeISOPanel();

		quantity.setEnabled( false );

		CommonServiceProvider.getCommonService().getLems( null, false, new ApplicationCallBack<List<LEMModel>>()
		{
			@Override
			public void onSuccess( List<LEMModel> result )
			{
				lemModels = result;
				for ( LEMModel lemModel : result )
				{
					LoggerUtil.log( "read  " + lemModel.getId() );
					if ( !assignedLemIds.contains( lemModel.getId() ) )
					{
						LoggerUtil.log( "added  " + lemModel.getId() );
						selectUnAssignedLEM.addItem( lemModel.getLemName(), lemModel );
					}
					cloneSource.addItem( lemModel.getLemName(), lemModel );
				}
			}
		} );

		addCloseHandler( new CloseHandler<Boolean>()
		{
			@Override
			public void onClose( CloseEvent<Boolean> event )
			{
				deleteAdapters();
			}
		} );
	}

	private void initialize()
	{

		createVMRow = new MaterialRow();
		createVMRow.addStyleName( resources.style().createVmPopupRow() );

		vmName = uiComponentsUtil.getTexBox( "VM Name", "", "s6" );
		vmName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		vmName.addValidator( getDuplicateVMNamesValidator() );
		vmName.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				vmName.validate();
			}
		} );
		vmName.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				vmName.validate();
			}
		} );

		quantity = uiComponentsUtil.getIntegerBox( "Quantity", "", "s6" );
		quantity.setMin( "1" );
		quantity.setValue( 1 );
		quantity.setMax( "1" );
		quantity.setAutoValidate( true );

		cpuCores = uiComponentsUtil.getIntegerBox( "CPU Cores", "", "s6" );
		cpuCores.setMin( "1" );
		cpuCores.setValue( 1 );
		cpuCores.setAutoValidate( true );

		sizeBox = new SizeBox( "Memory", "", "s6", "s8", this.availableSizeinBytes );
		sizeBox.getSizeBox().removeValidator( sizeBox.getSizeValidator() );
		sizeBox.getSizeBox().setMin( "0.0" );
		sizeBox.getSizeBox().setValue( 1.0 );
		sizeBox.getMemoryUnitComboBox().setSelectedIndex( 1 );
		sizeBox.getMemoryUnitComboBox().getListbox().remove( 3 );
		sizeBox.getMemoryUnitComboBox().getListbox().remove( 2 );

		/*spicePort = uiComponentsUtil.getIntegerBox( "Graphic Port ( 5900 to 6000 )", "", "s6" );
		spicePort.setMin( "5900" );*/

		/*size = uiComponentsUtil.getIntegerBox( "Memory", "", "s3" );
		size.setMin( "0" );
		size.setValue( 1 );
		size.setAutoValidate( true );
		
		memorySizeType = uiComponentsUtil.createMemorySizeTypeDropDown( "", "s3" );
		memorySizeType.getListbox().remove( 3 );
		memorySizeType.setHideSearch( true );*/

		//Attach LEM
		createAttachLEMRadioButtons();

		createVMRow.add( vmName );
		createVMRow.add( quantity );
		createVMRow.add( cpuCores );
		//createVMRow.add( size );
		//createVMRow.add( memorySizeType );
		createVMRow.add( sizeBox );

		//		createVMRow.add( spicePort );
		getBodyPanel().add( createVMRow );
	}

	private void initializeLEMPanel()
	{
		attachLemMainPanel = new MaterialPanel();
		//Radio buttons
		//		createVMRow.add( radioPanel );
		attachLemMainPanel.add( radioPanel );

		//New Lem
		addNewLemPanel();
		//		createVMRow.add( newLemPanel );
		attachLemMainPanel.add( newLemPanel );
		newLemPanel.setVisible( false );

		//Exising Lem
		attachExistingLemPanel();
		//		createVMRow.add( existingLemPanel );
		attachLemMainPanel.add( existingLemPanel );
		existingLemPanel.setVisible( false );

		//clone Lem
		attachCloneLemPanel();
		//		createVMRow.add( cloneLemPanel );
		attachLemMainPanel.add( cloneLemPanel );
		cloneLemPanel.setVisible( false );

		createVMRow.add( attachLemMainPanel );
	}

	private void initializeISOPanel()
	{
		addISO = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Add" );
		addISO.addStyleName( resources.style().create_VM_add_network_icon() );

		CreateVMPopup createVMPopup = this;
		addISO.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				AttachISOPopup attachISOPopup = new AttachISOPopup( createVMPopup, true, -1, isoMountPathPanels, null, false, null );
				attachISOPopup.open();
			}
		} );

		isoMountPaths = new MaterialLabel( "ISO MOUNT PATHS" );
		isoMountPaths.addStyleName( resources.style().create_VM_add_network() );

		attachIsoPanel = new MaterialPanel();
		attachIsoPanel.setGrid( "s12" );
		attachIsoPanel.addStyleName( resources.style().create_VM_Network_penal() );

		attachIsoPanel.add( isoMountPaths );
		attachIsoPanel.add( addISO );

		createVMRow.add( attachIsoPanel );
	}

	public void setISOPath( String isoPath )
	{
		ISOMountPathPanel isoMountPathPanel = new ISOMountPathPanel( uiComponentsUtil, this, isoMountPathPanels.size(), vmName.getValue(), isoPath );
		attachIsoPanel.add( isoMountPathPanel );
		isoMountPathPanels.add( isoMountPathPanel );
	}

	public void editISOPath( int index )
	{
		AttachISOPopup attachISOPopup = new AttachISOPopup( this, false, index, isoMountPathPanels, null, false, null );
		attachISOPopup.open();
	}

	public void deleteISOPanel( int index )
	{
		attachIsoPanel.remove( isoMountPathPanels.get( index ) );
		isoMountPathPanels.remove( index );
		int i = 0;
		for ( ISOMountPathPanel isoMountPathPanel : isoMountPathPanels )
		{
			isoMountPathPanel.setIndex( i++ );
		}
	}

	public void updateISOPath( String isoPath, int index )
	{
		ISOMountPathPanel isoMountPathPanel = isoMountPathPanels.get( index );
		isoMountPathPanel.setLabel( isoPath );
	}

	private void initializeNetworkPanel()
	{
		// fire get network device list
		networkAdaptersPanels = new ArrayList<NetworkAdaptersPanel>();
		CommonServiceProvider.getCommonService().getAllBridges( new ApplicationCallBack<List<BridgeJSONModel>>()
		{
			@Override
			public void onSuccess( List<BridgeJSONModel> result )
			{
				bridgeJSONModels = result;
				/*if ( result == null || result.isEmpty() )
				{
					List<String> errorMessages = new ArrayList<String>();
					errorMessages.add( "Unable to get Bridge List." );
					ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
					errorPopup.open();
				}*/
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
		attachNetworksPanel = new MaterialPanel();
		attachNetworksPanel.setGrid( "s12" );
		attachNetworksPanel.addStyleName( resources.style().create_VM_Network_penal() );
		/*addNetwork = new MaterialIcon( IconType.ADD, Color.BLUE );*/
		addNetwork = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Add" );
		addNetwork.addStyleName( resources.style().create_VM_add_network_icon() );
		networkAdpt = new MaterialLabel( "NETWORK ADAPTERS" );
		networkAdpt.addStyleName( resources.style().create_VM_add_network() );

		CreateVMPopup createVMPopup = this;
		addNetwork.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				if ( vmName.validate() )
				{
					if ( bridgeJSONModels != null && !bridgeJSONModels.isEmpty() )
					{
						NetworkAdaptersPanel networkAdaptersPanel = new NetworkAdaptersPanel( bridgeJSONModels, uiComponentsUtil, createVMPopup, networkAdaptersPanels.size(), vmName.getValue() );
						attachNetworksPanel.add( networkAdaptersPanel );
						networkAdaptersPanels.add( networkAdaptersPanel );
					}
					else
					{
						List<String> errorMessages = new ArrayList<String>();
						errorMessages.add( "Bridges are not configured." );
						ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
						errorPopup.open();
					}
				}
			}
		} );

		attachNetworksPanel.add( networkAdpt );
		attachNetworksPanel.add( addNetwork );

		createVMRow.add( attachNetworksPanel );
	}

	public void deleteNetworkAdapter( int index )
	{
		attachNetworksPanel.remove( networkAdaptersPanels.get( index ) );
		networkAdaptersPanels.remove( index );
	}

	@SuppressWarnings( "unchecked" )
	private void attachExistingLemPanel()
	{
		selectUnAssignedLEM = ( MaterialComboBox<LEMModel> ) uiComponentsUtil.getAssisgnToLEMDropDown( lemModels, "", "s6", true );
		selectUnAssignedLEM.addStyleName( resources.style().create_vm_ael_dropdown() );
		selectUnAssignedLEM.setAutoValidate( true );

		existingLemPanel = new MaterialPanel();
		existingLemPanel.add( selectUnAssignedLEM );
		existingLemPanel.addStyleName( resources.style().popup_checkbox_panel_body() );
	}

	@SuppressWarnings( "unchecked" )
	private void attachCloneLemPanel()
	{
		cloneSource = ( MaterialComboBox<LEMModel> ) uiComponentsUtil.getAssisgnToLEMDropDown( lemModels, "", "s6", true );
		cloneSource.addStyleName( resources.style().padding_left_0() );

		clonedLEM = uiComponentsUtil.getTexBox( "", "", "s6" );
		clonedLEM.addStyleName( resources.style().padding_left_0() );
		clonedLEM.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );

		cloneLemPanel = new MaterialPanel();
		cloneLemPanel.add( cloneSource );
		cloneLemPanel.add( clonedLEM );
		cloneLemPanel.addStyleName( resources.style().popup_checkbox_panel_body() );
	}

	private void addNewLemPanel()
	{
		newLemName = uiComponentsUtil.getTexBox( "New LEM Name", "", "s4" );
		newLemName.addStyleName( resources.style().padding_left_0() );
		newLemName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		newLemName.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				newLemName.validate();
			}
		} );
		newLemName.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				newLemName.validate();
			}
		} );

		newLEMsizeBox = new SizeBox( "Size", "", "s5", "s6", this.availableSizeinBytes );
		newLEMsizeBox.getSizeBox().setMin( "1.0" );
		newLEMsizeBox.getSizeBox().setValue( 1.0 );
		newLEMsizeBox.getMemoryUnitComboBox().setSelectedIndex( 1 );
		newLEMsizeBox.getMemoryUnitComboBox().getListbox().remove( 3 );
		newLEMsizeBox.getMemoryUnitComboBox().setGrid( "s6" );

		/*lemSize = uiComponentsUtil.getIntegerBox( "New LEM Size", "", "s3" );
		lemSize.addStyleName( resources.style().padding_left_0() );
		lemSize.setMin( "1" );
		lemSize.setValue( 1 );
		lemSize.setAutoValidate( true );
		
		lemMemorySizeType = uiComponentsUtil.createMemorySizeTypeDropDown( "", "s2" );
		lemMemorySizeType.setSelectedIndex( 1 );
		lemMemorySizeType.addStyleName( resources.style().padding_left_0() );
		lemMemorySizeType.setHideSearch( true );*/

		sectorSizeDropDown = createSectorSizeDropDown( "Sector size", "s3" );
		sectorSizeDropDown.setHideSearch( true );
		newLemPanel = new MaterialPanel();

		newLemPanel.add( newLemName );
		//newLemPanel.add( lemSize );
		//newLemPanel.add( lemMemorySizeType );
		newLemPanel.add( newLEMsizeBox );
		newLemPanel.add( sectorSizeDropDown );
		newLemPanel.addStyleName( resources.style().popup_checkbox_panel_body() );
	}

	private void switchLEMPanel( String switchTo )
	{
		if ( switchTo.equalsIgnoreCase( "ATTACH" ) )
		{
			newLemPanel.setVisible( false );
			existingLemPanel.setVisible( true );
			cloneLemPanel.setVisible( false );
		}
		else if ( switchTo.equalsIgnoreCase( "CLONE" ) )
		{
			newLemPanel.setVisible( false );
			existingLemPanel.setVisible( false );
			cloneLemPanel.setVisible( true );
		}
		else
		{
			newLemPanel.setVisible( true );
			existingLemPanel.setVisible( false );
			cloneLemPanel.setVisible( false );
		}
	}

	private void createAttachLEMRadioButtons()
	{
		radioPanel = new MaterialPanel();
		radioPanel.setGrid( "s12" );
		radioPanel.addStyleName( resources.style().popup_checkbox_panel() );

		newLem = new MaterialRadioButton( "attachLemOptions", "New LEM" );
		if ( availableSizeinBytes <= 0 )
		{
			newLem.setEnabled( false );
		}
		//		newLem = new MaterialCheckBox( "New Lem" );
		newLem.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				switchLEMPanel( "NEW" );
			}
		} );
		newLemRadioPanel = new MaterialPanel();
		newLemRadioPanel.add( newLem );
		newLemRadioPanel.setGrid( "s3" );

		attachLem = new MaterialRadioButton( "attachLemOptions", "Attach Existing LEM" );
		attachLem.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				switchLEMPanel( "ATTACH" );
			}
		} );
		attachLemRadioPanel = new MaterialPanel();
		attachLemRadioPanel.add( attachLem );
		attachLemRadioPanel.setGrid( "s4" );

		cloneLem = new MaterialRadioButton( "attachLemOptions", "Clone Existing LEM" );
		cloneLem.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				switchLEMPanel( "CLONE" );
			}
		} );

		cloneLemRadioPanel = new MaterialPanel();
		cloneLemRadioPanel.add( cloneLem );
		attachLemRadioPanel.setGrid( "s4" );

		radioPanel.add( newLemRadioPanel );
		radioPanel.add( attachLemRadioPanel );
		radioPanel.add( cloneLemRadioPanel );
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
					LoggerUtil.log( "create VM" );

					NameModel[] networks = null;
					if ( networkAdaptersPanels != null && networkAdaptersPanels.size() > 0 )
					{
						networks = new NameModel[networkAdaptersPanels.size()];
						int i = 0;
						for ( NetworkAdaptersPanel networkAdaptersPanel : networkAdaptersPanels )
						{
							NameModel nameModel = new NameModel( networkAdaptersPanel.getLvNetCreateRequestModel().getName() );
							networks[i++] = nameModel;
						}
					}

					String[] cdroms = null;
					if ( isoMountPathPanels != null && isoMountPathPanels.size() > 0 )
					{
						cdroms = new String[isoMountPathPanels.size()];
						int i = 0;
						for ( ISOMountPathPanel isPanel : isoMountPathPanels )
						{
							String isoPath = isPanel.getLabel();
							String cdrom = new String( isoPath );
							cdroms[i++] = cdrom;
						}
					}

					if ( newLem.getValue() )
					{
						createVmWithNewLem( networks, cdroms );
					}
					else if ( attachLem.getValue() )
					{
						createVMAttachLem( networks, cdroms );
					}
					else if ( cloneLem.getValue() )
					{
						createVMCloneLem( networks, cdroms );
					}
					else
					{
						createVMOnly( networks, cdroms );
					}
				}
			}
		} );
	}

	private void createVMCloneLem( NameModel[] networks, String[] cdroms )
	{
		LEMModel lemModel = cloneSource.getSelectedValue().get( 0 );
		CloneLEMModel cloneLEMModel = new CloneLEMModel( lemModel.getLemName(), clonedLEM.getValue(), 1 );
		CloneLEMModel[] cloneLEMModels = new CloneLEMModel[1];
		cloneLEMModels[0] = cloneLEMModel;
		//		String spicePortValue = ( spicePort.getValue() == null ) ? null : spicePort.getValue().toString();
		String spicePortValue = null;
		VMCreateCloneLemModel vmCreateCloneLemModel = new VMCreateCloneLemModel( Long.valueOf( cpuCores.getValue() ), vmName.getValue(), Double.valueOf( sizeBox.getSizeBox().getValue().toString() ).longValue(), sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), spicePortValue, cloneLEMModels, networks, cdroms );
		CommonServiceProvider.getCommonService().createVMCloneLem( vmCreateCloneLemModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				createVMSuccess = true;
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( vmCreateCloneLemModel.getName() + " Created..!", "rounded" );
				if ( updateVMDataTableCmd != null )
				{
					updateVMDataTableCmd.executeWithData( true );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createVMAttachLem( NameModel[] networks, String[] cdroms )
	{
		LEMModel lemModel = selectUnAssignedLEM.getValue().get( 0 );
		LEMJSONModel lemjsonModel = new LEMJSONModel( lemModel.getId(), lemModel.getLemName(), null, null, null );
		LEMJSONModel[] lemjsonModels = new LEMJSONModel[1];
		lemjsonModels[0] = lemjsonModel;
		//		String spicePortValue = ( spicePort.getValue() == null ) ? null : spicePort.getValue().toString();
		String spicePortValue = null;
		VMCreateAttachLemModel vmCreateAttachLemModel = new VMCreateAttachLemModel( Long.valueOf( cpuCores.getValue() ), vmName.getValue(), Double.valueOf( sizeBox.getSizeBox().getValue().toString() ).longValue(), sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), spicePortValue, lemjsonModels, networks, cdroms );
		CommonServiceProvider.getCommonService().createVMAttachLem( vmCreateAttachLemModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				createVMSuccess = true;
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( vmCreateAttachLemModel.getName() + " Created..!", "rounded" );
				if ( updateVMDataTableCmd != null )
				{
					updateVMDataTableCmd.executeWithData( true );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createVMOnly( NameModel[] networks, String[] cdroms )
	{
		//		String spicePortValue = ( spicePort.getValue() == null ) ? null : spicePort.getValue().toString();
		String spicePortValue = null;
		VMCreateModel createVMModel = new VMCreateModel( Long.valueOf( cpuCores.getValue() ), vmName.getValue(), Double.valueOf( sizeBox.getSizeBox().getValue().toString() ).longValue(), sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), spicePortValue, networks, cdroms );
		CommonServiceProvider.getCommonService().createVM( createVMModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				createVMSuccess = true;
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( createVMModel.getName() + " Created..!", "rounded" );
				if ( updateVMDataTableCmd != null )
				{
					updateVMDataTableCmd.executeWithData( true );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createVmWithNewLem( NameModel[] networks, String[] cdroms )
	{
		CommonServiceProvider.getCommonService().getRTM( null, new ApplicationCallBack<RTMModel>()
		{
			@Override
			public void onSuccess( RTMModel result )
			{
				if ( result != null )
				{
					String rtm_name = result.getName();
					LEMJSONModel lemjsonModel = new LEMJSONModel( newLemName.getValue(), newLEMsizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), Double.valueOf( newLEMsizeBox.getSizeBox().getValue().toString() ).longValue(), rtm_name );
					LEMJSONModel[] lemjsonModels = new LEMJSONModel[1];
					lemjsonModels[0] = lemjsonModel;
					//					String spicePortValue = ( spicePort.getValue() == null ) ? null : spicePort.getValue().toString();
					String spicePortValue = null;
					VMCreateWithNewLemModel vmCreateWithNewLemModel = new VMCreateWithNewLemModel( Long.valueOf( cpuCores.getValue() ), vmName.getValue(), Long.valueOf( sizeBox.getSizeBox().getValue().toString() ), sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), spicePortValue, lemjsonModels, networks, cdroms );
					CommonServiceProvider.getCommonService().createVMWithLem( vmCreateWithNewLemModel, new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{
							createVMSuccess = true;
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							MaterialToast.fireToast( vmCreateWithNewLemModel.getName() + " Created..!", "rounded" );
							if ( updateVMDataTableCmd != null )
							{
								updateVMDataTableCmd.executeWithData( true );
							}
						}

						@Override
						public void onFailure( Throwable caught )
						{
							super.onFailureShowErrorPopup( caught, null );
						}
					} );
				}
				else
				{
					MaterialLoader.loading( false, getBodyPanel() );
					MaterialDialog materialDialog = uiComponentsUtil.getMaterialDialog( "Error..!", "Unable to get RTM details-..!", "Close", null );
					materialDialog.open();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, "Unable to get RTM details..!" );
			}
		} );
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( vmName.validate() && quantity.validate() && sizeBox.validate() )
		{
			valid = true;
			if ( newLem.getValue() )
			{
				if ( newLemName.validate() && newLEMsizeBox.validate() )
				{
					valid = true;
				}
				else
				{
					valid = false;
				}
			}
			if ( attachLem.getValue() )
			{
				if ( selectUnAssignedLEM.validate() )
				{
					valid = true;
				}
				else
				{
					valid = false;
				}
			}
			if ( cloneLem.getValue() )
			{
				if ( cloneSource.validate() && clonedLEM.validate() )
				{
					valid = true;
				}
				else
				{
					valid = false;
				}
			}
			if ( networkAdaptersPanels != null && networkAdaptersPanels.size() > 0 )
			{
				for ( NetworkAdaptersPanel networkAdaptersPanel : networkAdaptersPanels )
				{
					if ( networkAdaptersPanel.getBridgeComboBox().getSelectedValue() == null || networkAdaptersPanel.getBridgeComboBox().getSelectedValue().get( 0 ) == null || networkAdaptersPanel.getBridgeComboBox().getSelectedValue().get( 0 ).getDisplayName().equalsIgnoreCase( "none" ) )
					{
						networkAdaptersPanel.getBridgeComboBox().setErrorText( "Please select bridge." );
						valid = false;
						break;
					}
					else
					{
						networkAdaptersPanel.getBridgeComboBox().clearErrorText();
						networkAdaptersPanel.getBridgeComboBox().setSuccessText( "" );
						valid = true;
					}
				}
			}
		}
		else
		{
			vmName.validate();
			quantity.validate();
			sizeBox.validate();
			if ( newLem.getValue() )
			{
				newLemName.validate();
				newLEMsizeBox.validate();
			}
			if ( attachLem.getValue() )
			{
				selectUnAssignedLEM.validate();
			}
			if ( cloneLem.getValue() )
			{
				cloneSource.validate();
				clonedLEM.validate();
			}
		}
		return valid;

	}

	private void deleteAdapters()
	{
		if ( !createVMSuccess )
		{
			if ( networkAdaptersPanels != null && networkAdaptersPanels.size() > 0 )
			{
				for ( NetworkAdaptersPanel networkAdaptersPanel : networkAdaptersPanels )
				{
					CommonServiceProvider.getCommonService().deleteAdapter( new NameModel( networkAdaptersPanel.getLvNetCreateRequestModel().getName() ), new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{

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
	}

	public Validator<String> getDuplicateVMNamesValidator()
	{
		Validator<String> emptyValidator = new Validator<String>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<String> editor, String value )
			{
				EditorError editorError = new EditorError()
				{

					@Override
					public void setConsumed( boolean consumed )
					{

					}

					@Override
					public boolean isConsumed()
					{
						return false;
					}

					@Override
					public Object getValue()
					{
						return null;
					}

					@Override
					public Object getUserData()
					{
						return null;
					}

					@Override
					public String getPath()
					{
						return null;
					}

					@Override
					public String getMessage()
					{
						return "Duplicate name! Use unique name for VM.";
					}

					@Override
					public Editor< ? > getEditor()
					{
						return null;
					}

					@Override
					public String getAbsolutePath()
					{
						return null;
					}
				};

				List<EditorError> editorErrorsList = new ArrayList<>();
				editorErrorsList.add( editorError );

				if ( checkDuplicateVmNames( value ) )
				{
					return editorErrorsList;
				}
				else
				{
					return null;
				}
			}
		};
		return emptyValidator;
	}

	private boolean checkDuplicateVmNames( String value )
	{
		for ( VMModel vmModel : vmModels )
		{
			if ( value.equalsIgnoreCase( vmModel.getName() ) )
			{
				return true;
			}
		}
		return false;
	}
}
