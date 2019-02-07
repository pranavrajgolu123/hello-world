package com.fmlb.forsaos.client.application.management.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.REPOAdvancedParameterNFSModel;
import com.fmlb.forsaos.client.application.models.REPOMountInfoModel;
import com.fmlb.forsaos.client.application.models.REPONFSCreateModel;
import com.fmlb.forsaos.client.application.models.REPONFSEditModel;
import com.fmlb.forsaos.client.application.models.REPOParameterNFSModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.html.Option;

public class CreateNFSRepoPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox configurationName;

	private MaterialTextBox ipAddress;

	private MaterialComboBox<String> pathDropDown;

	private MaterialComboBox<String> protocolDropDown;

	private MaterialComboBox<String> purposeDropDown;

	private MaterialComboBox<String> nfsVersionDropDown;

	private MaterialComboBox<String> nfsCacheDropDown;

	private MaterialCheckBox advSettings;

	private MaterialCheckBox readOnly;

	private MaterialPanel advSettingsPanel;

	private MaterialPanel advSettingsOnClickpanel;

	private MaterialButton browseBtn;

	private MaterialTextBox portNumber;

	private IcommandWithData icommand;

	private REPONFSCreateModel repoMainModel;

	private Boolean isNew;

	AppResources resources = GWT.create( AppResources.class );

	public CreateNFSRepoPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData icommand, REPONFSCreateModel repoMainModel, Boolean isNew )
	{
		super( "NFS Configuration", "", "Create NFS Configuration", true );
		this.icommand = icommand;
		this.uiComponentsUtil = uiComponentsUtil;
		this.repoMainModel = repoMainModel;
		this.isNew = isNew;
		LoggerUtil.log( "initialize create Route popop" );
		initialize();
		buttonAction();
		this.getButtonRow().addStyleName( resources.style().repoPopupBtnRow() );
		if ( !isNew )
		{
			getButton().setText( "Update NFS Configuration" );
			getDeviceList( repoMainModel.getParameters().getIp() );
		}
		else
		{
			getButton().setText( "Create NFS Configuration" );
		}
	}

	@SuppressWarnings( "unchecked" )
	private void initialize()
	{

		MaterialRow createNFSRow = new MaterialRow();
		createNFSRow.addStyleName( resources.style().repoNfsPopupRow() );

		configurationName = uiComponentsUtil.getTexBox( "Configuration Name", "Enter Configuration Name", "s6" );
		uiComponentsUtil.addTextBoxValidator( configurationName );

		ipAddress = uiComponentsUtil.getIP4TextBox( "IP Address", "", "s6", true );

		HashMap<String, String> pathDropDownOptionMap = new HashMap<String, String>();

		pathDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( pathDropDownOptionMap, "Path", "s4" );

		browseBtn = new MaterialButton( "Find Paths" );
		browseBtn.setTitle( "Find NFS Paths" );
		browseBtn.setGrid( "s2" );
		browseBtn.addStyleName( resources.style().nfsPathLoadData() );
		//browseBtn.setIconType( IconType.OPEN_IN_BROWSER );
		browseBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				if ( ipAddress.validate() )
				{
					getDeviceList( ipAddress.getValue() );
				}
			}
		} );

		HashMap<String, String> protocolDropDownOptionMap = new HashMap<String, String>();
		protocolDropDownOptionMap.put( "Activate config", "Activate config" );
		protocolDropDownOptionMap.put( "Do not activate config", "Do not activate config" );

		protocolDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( protocolDropDownOptionMap, "Activate Protocol?", "s6" );

		HashMap<String, String> purposeDropDownOptionMap = new HashMap<String, String>();
		purposeDropDownOptionMap.put( "ISO Only", "ISO Only" );
		purposeDropDownOptionMap.put( "Blink Only", "Blink Only" );
		purposeDropDownOptionMap.put( "Both ISO and Blink", "Both ISO and Blink" );

		purposeDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( purposeDropDownOptionMap, "Purpose", "s6" );

		createAttachLEMRadioButtons();
		attachadvSettingsPanel();

		createNFSRow.add( configurationName );
		createNFSRow.add( ipAddress );
		createNFSRow.add( pathDropDown );
		createNFSRow.add( browseBtn );
		createNFSRow.add( protocolDropDown );
		createNFSRow.add( purposeDropDown );
		createNFSRow.add( advSettingsOnClickpanel );
		createNFSRow.add( advSettingsPanel );

		advSettingsPanel.setVisible( false );

		getBodyPanel().add( createNFSRow );
	}

	@SuppressWarnings( "unchecked" )
	private void attachadvSettingsPanel()
	{
		portNumber = uiComponentsUtil.getTexBox( "Port Number", "", "s6" );
		portNumber.setText( "2049" );
		portNumber.addStyleName( resources.style().repoPortNumber() );

		HashMap<String, String> nfsVersionDropDownOptionMap = new HashMap<String, String>();
		nfsVersionDropDownOptionMap.put( "1.0", "1.0" );
		nfsVersionDropDownOptionMap.put( "2.0", "2.0" );
		nfsVersionDropDownOptionMap.put( "3.0", "3.0" );
		nfsVersionDropDownOptionMap.put( "4.0", "4.0" );

		nfsVersionDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( nfsVersionDropDownOptionMap, "NFS Version", "s6" );

		HashMap<String, String> nfsCacheDropDownOptionMap = new HashMap<String, String>();
		nfsCacheDropDownOptionMap.put( "noac", "noac" );
		nfsCacheDropDownOptionMap.put( "ac", "ac" );

		nfsCacheDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( nfsCacheDropDownOptionMap, "NFS Cache", "s6" );

		readOnly = new MaterialCheckBox( "Read Only" );
		readOnly.setGrid( "12" );
		readOnly.addStyleName( resources.style().nfsReadOnly() );

		advSettingsPanel = new MaterialPanel();

		advSettingsPanel.add( portNumber );
		advSettingsPanel.add( nfsVersionDropDown );
		advSettingsPanel.add( nfsCacheDropDown );
		advSettingsPanel.add( readOnly );
		advSettingsPanel.addStyleName( resources.style().advanceSettingPanelBody() );
	}

	private void createAttachLEMRadioButtons()
	{

		advSettings = new MaterialCheckBox( "Advanced Settings" );

		advSettings.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( event.getValue() )
				{
					advSettingsPanel.setVisible( true );

				}
				else
				{
					advSettingsPanel.setVisible( false );

				}
			}
		} );

		advSettingsOnClickpanel = new MaterialPanel();
		advSettingsOnClickpanel.add( advSettings );
		advSettingsOnClickpanel.setGrid( "s12" );
		advSettingsOnClickpanel.addStyleName( resources.style().popup_checkbox_panel() );

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
					LoggerUtil.log( "NFS " );
					String automountValue = protocolDropDown.getValue().get( 0 );
					Boolean automount = automountValue.equalsIgnoreCase( "Activate config" ) ? true : false;
					String path = getSelectedDeviceList();
					REPOParameterNFSModel parameters = new REPOParameterNFSModel( ipAddress.getValue(), path, getAdvanced() );
					String purpose = getPurpose();
					REPONFSCreateModel createNfsRepoModel = new REPONFSCreateModel( null, configurationName.getValue(), "nfs", automount, null, parameters, purpose );
					if ( isNew )
					{
						createNFSRepo( createNfsRepoModel );
					}
					else
					{
						REPONFSEditModel editRepoMainModel = new REPONFSEditModel( repoMainModel.get_id(), createNfsRepoModel );
						editNFSRepo( editRepoMainModel );
					}
				}
			}
		} );
	}

	private void editNFSRepo( REPONFSEditModel editRepoMainModel )
	{
		CommonServiceProvider.getCommonService().updateConfigNFS( editRepoMainModel, new ApplicationCallBack<Boolean>()
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
				icommand.executeWithData( true );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createNFSRepo( REPONFSCreateModel repoModel )
	{
		CommonServiceProvider.getCommonService().addConfigNFS( repoModel, new ApplicationCallBack<Boolean>()
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
				icommand.executeWithData( true );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private REPOAdvancedParameterNFSModel getAdvanced()
	{
		if ( advSettings.getValue() )
		{
			REPOAdvancedParameterNFSModel advanced = new REPOAdvancedParameterNFSModel( getReadOnly(), nfsVersionDropDown.getSelectedValue().get( 0 ), nfsCacheDropDown.getSelectedValue().get( 0 ), portNumber.getValue() );
			return advanced;
		}
		return null;
	}

	private String getReadOnly()
	{
		return readOnly.getValue() ? "ro" : "rw";
	}

	private void getDeviceList( String ipAddress )
	{
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().discoverConfig( "{\"parameters\": { \"ip\": \"" + ipAddress + "\" },\"protocol\":\"nfs\"}", new ApplicationCallBack<REPOMountInfoModel[]>()
		{
			@Override
			public void onSuccess( REPOMountInfoModel[] result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				if ( result != null && result.length > 0 )
				{
					pathDropDown.clear();
					Option option = new Option();
					option.setValue( "All" );
					option.setText( "All" );
					option.setTitle( "All" );
					pathDropDown.add( option );

					for ( REPOMountInfoModel repoMountInfoModel : result )
					{
						option = new Option();
						option.setValue( repoMountInfoModel.getName() );
						option.setText( repoMountInfoModel.getName() );
						option.setTitle( repoMountInfoModel.getName() );
						pathDropDown.add( option );
					}
					setValues( isNew );
					validatePath();
				}
				else
				{
					pathDropDown.clear();
					validatePath();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				pathDropDown.clear();
				validatePath();
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private String getPurpose()
	{
		String purpose = "both";
		if ( purposeDropDown.getValue().get( 0 ).equalsIgnoreCase( "ISO Only" ) )
		{
			purpose = "iso";
		}
		else if ( purposeDropDown.getValue().get( 0 ).equalsIgnoreCase( "Blink Only" ) )
		{
			purpose = "blink";
		}
		return purpose;
	}

	private String getSelectedDeviceList()
	{
		if ( pathDropDown.getValue() == null || pathDropDown.getValue().size() == 0 || pathDropDown.getValue().get( 0 ).equalsIgnoreCase( "all" ) )
		{
			return null;
		}
		else
		{
			return pathDropDown.getValue().get( 0 );
		}
	}

	private void setValues( Boolean isNew )
	{
		if ( !isNew )
		{
			configurationName.setValue( repoMainModel.getName() );
			ipAddress.setValue( repoMainModel.getParameters().getIp() );
			if ( repoMainModel.getAutomount() )
			{
				protocolDropDown.setSingleValue( "Activate config" );
			}
			else
			{
				protocolDropDown.setSingleValue( "Do not activate config" );
			}
			if ( repoMainModel.getParameters() != null && repoMainModel.getParameters().getPath() != null )
			{
				pathDropDown.setSingleValue( repoMainModel.getParameters().getPath() );
			}
			else
			{
				pathDropDown.setSingleValue( "All" );
			}

			if ( repoMainModel.getPurpose().equalsIgnoreCase( "ISO Only" ) )
			{
				purposeDropDown.setSingleValue( "ISO Only" );
			}
			else if ( repoMainModel.getPurpose().equalsIgnoreCase( "Blink Only" ) )
			{
				purposeDropDown.setSingleValue( "Blink Only" );
			}
			else
			{
				purposeDropDown.setSingleValue( "Both ISO and Blink" );
			}

			if ( repoMainModel.getParameters() != null && repoMainModel.getParameters().getAdvanced() != null )
			{
				advSettings.setValue( true );
				advSettingsPanel.setVisible( true );
				readOnly.setValue( getReadOnlyStatus( repoMainModel.getParameters().getAdvanced().getRead_only() ) );
				nfsVersionDropDown.setSingleValue( repoMainModel.getParameters().getAdvanced().getVers() );
				nfsCacheDropDown.setSingleValue( repoMainModel.getParameters().getAdvanced().getCache() );
				portNumber.setValue( repoMainModel.getParameters().getAdvanced().getPort() );
			}
		}
	}

	private Boolean getReadOnlyStatus( String read_only )
	{
		return read_only.equals( "ro" ) ? true : false;
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( configurationName.validate() && ipAddress.validate() && validatePath() )
		{
			valid = true;
		}
		return valid;
	}

	public boolean validatePath()
	{
		if ( pathDropDown.getSelectedValue() != null && !pathDropDown.getSelectedValue().isEmpty() )
		{
			pathDropDown.clearErrorText();
			pathDropDown.setSuccessText( "" );
			return true;
		}
		else
		{
			pathDropDown.setErrorText( "No path found." );
			return false;
		}
	}
}
