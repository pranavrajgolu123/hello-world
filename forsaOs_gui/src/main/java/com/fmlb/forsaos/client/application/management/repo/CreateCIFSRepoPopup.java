package com.fmlb.forsaos.client.application.management.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.REPOAdvancedParameterCIFSModel;
import com.fmlb.forsaos.client.application.models.REPOCIFSCreateModel;
import com.fmlb.forsaos.client.application.models.REPOCIFSEditModel;
import com.fmlb.forsaos.client.application.models.REPOMountInfoModel;
import com.fmlb.forsaos.client.application.models.REPOParameterCIFSModel;
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
import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.html.Option;

public class CreateCIFSRepoPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox configurationName;

	private MaterialTextBox ipAddress;

	private MaterialTextBox userName;

	private MaterialTextBox userPassword;

	private MaterialCheckBox showpassword;

	private MaterialTextBox domain;

	private MaterialComboBox<String> pathDropDown;

	private MaterialComboBox<String> protocolDropDown;

	private MaterialComboBox<String> purposeDropDown;

	private MaterialComboBox<String> cifsVersionDropDown;

	private MaterialComboBox<String> cifsCacheDropDown;

	private MaterialComboBox<String> securityModeDropDown;

	private MaterialCheckBox advSettings;

	private MaterialCheckBox readOnly;

	private MaterialPanel advSettingsPanel;

	private MaterialPanel advSettingsOnClickpanel;

	private MaterialButton browseBtn;

	private MaterialTextBox portNumber;

	private IcommandWithData icommand;

	private REPOCIFSCreateModel repoMainModel;

	private Boolean isNew;

	AppResources resources = GWT.create( AppResources.class );

	public CreateCIFSRepoPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData icommand, REPOCIFSCreateModel repoMainModel, Boolean isNew )
	{
		super( "CIFS Configuration", "", "Create CIFS Configuration", true );
		this.icommand = icommand;
		this.uiComponentsUtil = uiComponentsUtil;
		this.repoMainModel = repoMainModel;
		this.isNew = isNew;
		initialize();
		buttonAction();
		this.getButtonRow().addStyleName( resources.style().repoPopupBtnRow() );
		if ( !isNew )
		{
			getButton().setText( "Update CIFS Configuration" );
			getDeviceList( repoMainModel.getParameters().getIp(), repoMainModel.getParameters().getUsr(), repoMainModel.getParameters().getPwd() );
		}
		else
		{
			getButton().setText( "Create CIFS Configuration" );
		}
	}

	@SuppressWarnings( "unchecked" )
	private void initialize()
	{

		getBodyPanel().setHeight( "" );

		MaterialRow createNFSRow = new MaterialRow();
		createNFSRow.addStyleName( resources.style().cifsRow() );

		configurationName = uiComponentsUtil.getTexBox( "Configuration Name", "Enter Configuration Name", "s6" );
		uiComponentsUtil.addTextBoxValidator( configurationName );

		ipAddress = uiComponentsUtil.getIP4TextBox( "IP Address", "", "s6", true );

		userName = uiComponentsUtil.getTexBox( "User Name", "Enter User Name", "s6" );
		uiComponentsUtil.addTextBoxValidator( userName );
		userPassword = uiComponentsUtil.getDefaultPasswordBox( "User Password", "s6" );
		uiComponentsUtil.addEmptyTextValidation( userPassword );
		userPassword.addStyleName( resources.style().cifsPopupErrorMsg() );

		showpassword = new MaterialCheckBox( "Show Password" );
		showpassword.addStyleName( resources.style().showPassword() );
		userPassword.add( showpassword );
		showpassword.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( event.getValue() )
				{
					userPassword.setType( InputType.TEXT );
				}
				else
				{
					userPassword.setType( InputType.PASSWORD );
				}
			}
		} );

		domain = uiComponentsUtil.getTexBox( "Domain(Optional)", "Enter Domain", "s6" );

		HashMap<String, String> pathDropDownOptionMap = new HashMap<String, String>();

		pathDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( pathDropDownOptionMap, "Path", "s4" );

		browseBtn = new MaterialButton( "Find Paths" );
		browseBtn.setTitle( "Find CIFS Paths" );
		browseBtn.setGrid( "s2" );
		browseBtn.addStyleName( resources.style().pathLoadData() );
		//browseBtn.setIconType( IconType.OPEN_IN_BROWSER );
		browseBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				if ( ipAddress.validate() && userName.validate() && userPassword.validate() )
				{
					getDeviceList( ipAddress.getValue(), userName.getValue(), userPassword.getValue() );
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
		createNFSRow.add( userName );
		createNFSRow.add( userPassword );
		createNFSRow.add( domain );
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
		HashMap<String, String> securityModeDropDownOptionMap = new HashMap<String, String>();
		securityModeDropDownOptionMap.put( "krb5", "krb5" );
		securityModeDropDownOptionMap.put( "krb5i", "krb5i" );
		securityModeDropDownOptionMap.put( "ntlssp", "ntlssp" );
		securityModeDropDownOptionMap.put( "ntlsspi", "ntlsspi" );
		securityModeDropDownOptionMap.put( "ntlmv", "ntlmv" );
		securityModeDropDownOptionMap.put( "ntlmv2i", "ntlmv2i" );

		securityModeDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( securityModeDropDownOptionMap, "Security Mode", "s6" );

		portNumber = uiComponentsUtil.getTexBox( "Port Number", "", "s6" );
		portNumber.setText( "445" );
		portNumber.addStyleName( resources.style().repoPortNumber() );

		HashMap<String, String> cifsVersionDropDownOptionMap = new HashMap<String, String>();
		cifsVersionDropDownOptionMap.put( "1.0", "1.0" );
		cifsVersionDropDownOptionMap.put( "2.0", "2.0" );
		cifsVersionDropDownOptionMap.put( "3.0", "3.0" );
		cifsVersionDropDownOptionMap.put( "4.0", "4.0" );

		cifsVersionDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( cifsVersionDropDownOptionMap, "CIFS Version", "s6" );

		HashMap<String, String> cifsCacheDropDownOptionMap = new HashMap<String, String>();
		cifsCacheDropDownOptionMap.put( "None", "None" );
		cifsCacheDropDownOptionMap.put( "Strict", "Strict" );
		cifsCacheDropDownOptionMap.put( "Loose", "Loose" );

		cifsCacheDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( cifsCacheDropDownOptionMap, "CIFS Cache", "s6" );

		readOnly = new MaterialCheckBox( "Read Only" );
		readOnly.addStyleName( resources.style().repoCifsReadOnly() );

		advSettingsPanel = new MaterialPanel();

		advSettingsPanel.add( securityModeDropDown );
		advSettingsPanel.add( portNumber );
		advSettingsPanel.add( cifsVersionDropDown );
		advSettingsPanel.add( cifsCacheDropDown );
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
				boolean activedomain = event.getValue();
				if ( activedomain )
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
					LoggerUtil.log( "CIFS " );
					String automountValue = protocolDropDown.getValue().get( 0 );
					Boolean automount = automountValue.equalsIgnoreCase( "Activate config" ) ? true : false;
					String path = getSelectedDeviceList();

					REPOParameterCIFSModel parameters = new REPOParameterCIFSModel( userName.getValue(), userPassword.getValue(), ipAddress.getValue(), domain.getValue(), path, getAdvanced() );

					String purpose = getPurpose();

					REPOCIFSCreateModel createCifsRepoModel = new REPOCIFSCreateModel( null, configurationName.getValue(), "cifs", automount, null, parameters, purpose );
					if ( isNew )
					{
						createCIFSRepo( createCifsRepoModel );
					}
					else
					{
						REPOCIFSEditModel editRepoMainModel = new REPOCIFSEditModel( repoMainModel.get_id(), createCifsRepoModel );
						editCIFSRepo( editRepoMainModel );
					}
				}
			}
		} );
	}

	private void editCIFSRepo( REPOCIFSEditModel editRepoMainModel )
	{
		CommonServiceProvider.getCommonService().updateConfigCIFS( editRepoMainModel, new ApplicationCallBack<Boolean>()
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

	private void createCIFSRepo( REPOCIFSCreateModel repoModel )
	{
		CommonServiceProvider.getCommonService().addConfigCIFS( repoModel, new ApplicationCallBack<Boolean>()
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

	private REPOAdvancedParameterCIFSModel getAdvanced()
	{
		if ( advSettings.getValue() )
		{
			REPOAdvancedParameterCIFSModel advanced = new REPOAdvancedParameterCIFSModel( getReadOnly(), cifsVersionDropDown.getSelectedValue().get( 0 ), cifsCacheDropDown.getSelectedValue().get( 0 ), portNumber.getValue(), securityModeDropDown.getSelectedValue().get( 0 ) );
			return advanced;
		}
		return null;
	}

	private String getReadOnly()
	{
		return readOnly.getValue() ? "ro" : "rw";
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

	private void getDeviceList( String ipAddress, String usr, String pwd )
	{
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().discoverConfig( "{\"parameters\": { \"ip\": \"" + ipAddress + "\", \"pwd\": \"" + pwd + "\", \"usr\": \"" + usr + "\" },\"protocol\":\"cifs\"}", new ApplicationCallBack<REPOMountInfoModel[]>()
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

	private void setValues( Boolean isNew )
	{
		if ( !isNew )
		{
			configurationName.setValue( repoMainModel.getName() );
			ipAddress.setValue( repoMainModel.getParameters().getIp() );
			userName.setValue( repoMainModel.getParameters().getUsr() );
			userPassword.setValue( repoMainModel.getParameters().getPwd() );
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
				cifsVersionDropDown.setSingleValue( repoMainModel.getParameters().getAdvanced().getVers() );
				cifsCacheDropDown.setSingleValue( repoMainModel.getParameters().getAdvanced().getCache() );
				portNumber.setValue( repoMainModel.getParameters().getAdvanced().getPort() );
				securityModeDropDown.setSingleValue( repoMainModel.getParameters().getAdvanced().getSec() );
			}
		}
	}

	private Boolean getReadOnlyStatus( String read_only )
	{
		return read_only.equals( "ro" ) ? true : false;
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

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( configurationName.validate() && ipAddress.validate() && userName.validate() && userPassword.validate() & validatePath() )
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
			pathDropDown.setErrorText( "No paths found." );
			return false;
		}
	}
}
