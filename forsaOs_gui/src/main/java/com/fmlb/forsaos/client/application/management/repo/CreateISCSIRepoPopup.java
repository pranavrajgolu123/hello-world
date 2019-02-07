package com.fmlb.forsaos.client.application.management.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.REPOAdvancedParameterISCSIModel;
import com.fmlb.forsaos.client.application.models.REPOISCSICreateModel;
import com.fmlb.forsaos.client.application.models.REPOISCSIEditModel;
import com.fmlb.forsaos.client.application.models.REPOParameterISCSIModel;
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
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.html.Option;

@SuppressWarnings( "unchecked" )
public class CreateISCSIRepoPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private IcommandWithData updateUserDataTableCmd;

	private MaterialComboBox<String> IQN;

	private MaterialComboBox<String> CHAPIN;

	private MaterialComboBox<String> activateProtocol;

	private MaterialComboBox<String> purpose;

	private MaterialTextBox configName;

	private MaterialTextBox iPAddress;

	private MaterialButton scanIQNBtn;

	private MaterialTextBox userName;

	private MaterialTextBox userPassword;

	private MaterialCheckBox showpassword;

	private MaterialCheckBox readOnlysettings;

	private MaterialCheckBox advancedSettings;

	private MaterialRow createISCSIRow;

	private MaterialRow yesCHAPINRow;

	private REPOISCSICreateModel repoMainModel;

	private Boolean isNew;

	AppResources resources = GWT.create( AppResources.class );

	public CreateISCSIRepoPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData updateUserDataTableCmd, REPOISCSICreateModel repoMainModel, Boolean isNew )
	{

		super( "ISCSI Configuration", "", "Create ISCSI Configuration", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateUserDataTableCmd = updateUserDataTableCmd;
		this.repoMainModel = repoMainModel;
		this.isNew = isNew;
		LoggerUtil.log( "initialize create schedule" );
		yesCHAPIN();
		IQNcomboBOx();
		CHAPINComboBox();
		initialize();
		buttonAction();
		if ( !isNew )
		{
			getButton().setText( "Update ISCSI Configuration" );
			getDeviceList( repoMainModel.getParameters().getIp() );
		}
		else
		{
			getButton().setText( "Create ISCSI Configuration" );
		}
	}

	private void initialize()
	{

		createISCSIRow = new MaterialRow();

		configName = uiComponentsUtil.getTexBox( "Configuration Name", "Type Name", "s6" );
		uiComponentsUtil.addTextBoxValidator( configName );

		iPAddress = uiComponentsUtil.getIP4TextBox( "IP Address", "", "s4", true );

		scanIQNBtn = new MaterialButton( "Scan IQN" );
		scanIQNBtn.setGrid( "s2" );
		scanIQNBtn.setMarginTop( 21 );
		scanIQNBtn.addStyleName( resources.style().pathLoadData() );
		scanIQNBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				if ( iPAddress.validate() )
				{
					getDeviceList( iPAddress.getValue() );
				}
			}
		} );

		HashMap<String, String> activateProtocolOptionMap = new HashMap<String, String>();
		activateProtocolOptionMap.put( "Activate config", "Activate config" );
		activateProtocolOptionMap.put( "Do not activate config", "Do not activate config" );
		activateProtocol = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( activateProtocolOptionMap, "Activate Protocol?", "s6" );

		HashMap<String, String> purposeOptionMap = new HashMap<String, String>();
		purposeOptionMap.put( "Both ISO and Blink", "NO" );
		purposeOptionMap.put( "ISO Only", "ISO Only" );
		purposeOptionMap.put( "Blink Only", "Blink Only" );
		purpose = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( purposeOptionMap, "Purpose", "s6" );

		advancedSettings = new MaterialCheckBox( "Advanced Settings" );
		advancedSettings.addStyleName( resources.style().popup_checkbox_panel() );
		readOnlysettings = new MaterialCheckBox( "ReadOnly" );
		readOnlysettings.addStyleName( resources.style().iscsiAdvanceSettingPanelBody() );
		readOnlysettings.setVisible( false );

		createISCSIRow.add( configName );
		createISCSIRow.add( iPAddress );
		createISCSIRow.add( scanIQNBtn );
		createISCSIRow.add( IQN );
		createISCSIRow.add( CHAPIN );
		yesCHAPINRow.setVisible( false );
		createISCSIRow.add( yesCHAPINRow );
		createISCSIRow.add( activateProtocol );
		createISCSIRow.add( purpose );
		createISCSIRow.add( advancedSettings );
		createISCSIRow.add( readOnlysettings );

		getBodyPanel().add( createISCSIRow );

		advancedSettings.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( event.getValue() )
				{
					readOnlysettings.setVisible( true );
				}
				else
				{
					readOnlysettings.setVisible( false );
				}
			}
		} );

	}

	private void yesCHAPIN()
	{
		yesCHAPINRow = new MaterialRow();
		yesCHAPINRow.addStyleName( "iscsiRow" );
		userName = uiComponentsUtil.getTexBox( "User Name", "Username", "s6" );
		userName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		userName.addStyleName( resources.style().iscsiUserName() );

		userPassword = uiComponentsUtil.getDefaultPasswordBox( "User Password", "s6" );
		userPassword.addValidator( uiComponentsUtil.getTextBoxEmptyValidator() );
		userPassword.addStyleName( resources.style().iscsiUserPassword() );

		showpassword = new MaterialCheckBox( "Show Password" );
		userPassword.add( showpassword );
		showpassword.addStyleName( resources.style().showPassword() );

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

		yesCHAPINRow.add( userName );
		yesCHAPINRow.add( userPassword );
	}

	private void CHAPINComboBox()
	{
		HashMap<String, String> CHAPINOptionMap = new HashMap<String, String>();
		CHAPINOptionMap.put( "NO", "NO" );
		CHAPINOptionMap.put( "YES", "YES" );
		CHAPIN = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( CHAPINOptionMap, "CHAPIN", "s6" );
		CHAPIN.addValueChangeHandler( new ValueChangeHandler<List<String>>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<List<String>> event )
			{

				if ( event.getValue().get( 0 ).equalsIgnoreCase( "Yes" ) )
				{

					yesCHAPINRow.setVisible( true );
				}

				if ( event.getValue().get( 0 ).equalsIgnoreCase( "No" ) )
				{

					yesCHAPINRow.setVisible( false );
				}

			}
		} );
	}

	private void IQNcomboBOx()
	{
		HashMap<String, String> IQNOptionMap = new HashMap<String, String>();
		IQN = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( IQNOptionMap, "IQN", "s6" );
	}

	private void getDeviceList( String ipAddress )
	{
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().discoverIscsiConfig( ipAddress, new ApplicationCallBack<List<String>>()
		{
			@Override
			public void onSuccess( List<String> result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				if ( result != null && result.size() > 0 )
				{
					IQN.clear();

					for ( String iqn : result )
					{
						Option option = new Option();
						option.setValue( iqn );
						option.setText( iqn );
						option.setTitle( iqn );
						IQN.add( option );
					}
					setValues( isNew );
					validateIQN();
				}
				else
				{
					IQN.clear();
					validateIQN();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				IQN.clear();
				validateIQN();
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void setValues( Boolean isNew )
	{
		if ( !isNew )
		{
			configName.setValue( repoMainModel.getName() );
			iPAddress.setValue( repoMainModel.getParameters().getIp() );
			if ( repoMainModel.getAutomount() )
			{
				activateProtocol.setSingleValue( "Activate config" );
			}
			else
			{
				activateProtocol.setSingleValue( "Do not activate config" );
			}
			if ( repoMainModel.getParameters() != null && repoMainModel.getParameters().getIqn() != null )
			{
				IQN.setSingleValue( repoMainModel.getParameters().getIqn() );
			}

			if ( repoMainModel.getPurpose().equalsIgnoreCase( "ISO Only" ) )
			{
				purpose.setSingleValue( "ISO Only" );
			}
			else if ( repoMainModel.getPurpose().equalsIgnoreCase( "Blink Only" ) )
			{
				purpose.setSingleValue( "Blink Only" );
			}
			else
			{
				purpose.setSingleValue( "Both ISO and Blink" );
			}

			if ( repoMainModel.getParameters() != null && repoMainModel.getParameters().getUsr() != null )
			{
				CHAPIN.setSingleValue( "YES" );
				yesCHAPINRow.setVisible( true );

				userName.setValue( repoMainModel.getParameters().getUsr() );
				userPassword.setValue( repoMainModel.getParameters().getPwd() );
			}
			else
			{
				CHAPIN.setSingleValue( "NO" );
				yesCHAPINRow.setVisible( false );
			}

			if ( repoMainModel.getParameters() != null && repoMainModel.getParameters().getAdvanced() != null )
			{
				advancedSettings.setValue( true );
				readOnlysettings.setVisible( true );
				readOnlysettings.setValue( getReadOnlyStatus( repoMainModel.getParameters().getAdvanced().getRead_only() ) );
			}
			else
			{
				advancedSettings.setValue( false );
				readOnlysettings.setVisible( false );
			}
		}
	}

	private Boolean getReadOnlyStatus( String read_only )
	{
		return read_only.equals( "ro" ) ? true : false;
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
					LoggerUtil.log( "ISCSI " );
					String automountValue = activateProtocol.getValue().get( 0 );
					Boolean automount = automountValue.equalsIgnoreCase( "Activate config" ) ? true : false;
					String usrValue = null;
					String pwdValue = null;	
					if ( CHAPIN.getSelectedValue().get( 0 ).equalsIgnoreCase( "yes" ) )
					{
						usrValue = userName.getValue();
						pwdValue = userPassword.getValue();
					}

					REPOParameterISCSIModel parameters = new REPOParameterISCSIModel( usrValue, pwdValue, iPAddress.getValue(), IQN.getSelectedValue().get( 0 ), getAdvanced() );
					String selPurpose = getPurpose();
					REPOISCSICreateModel createISCSIRepoModel = new REPOISCSICreateModel( null, configName.getValue(), "iscsi", automount, null, parameters, selPurpose );
					if ( isNew )
					{
						createISCSIRepo( createISCSIRepoModel );
					}
					else
					{
						REPOISCSIEditModel editRepoMainModel = new REPOISCSIEditModel( repoMainModel.get_id(), createISCSIRepoModel );
						editISCSIRepo( editRepoMainModel );
					}
				}
			}
		} );
	}

	private void editISCSIRepo( REPOISCSIEditModel editRepoMainModel )
	{
		CommonServiceProvider.getCommonService().updateConfigISCSI( editRepoMainModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				updateUserDataTableCmd.executeWithData( true );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				updateUserDataTableCmd.executeWithData( true );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createISCSIRepo( REPOISCSICreateModel repoModel )
	{
		CommonServiceProvider.getCommonService().addConfigISCSI( repoModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				updateUserDataTableCmd.executeWithData( true );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				updateUserDataTableCmd.executeWithData( true );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private REPOAdvancedParameterISCSIModel getAdvanced()
	{
		if ( advancedSettings.getValue() )
		{
			REPOAdvancedParameterISCSIModel advanced = new REPOAdvancedParameterISCSIModel( getReadOnly() );
			return advanced;
		}
		return null;
	}

	private String getReadOnly()
	{
		return readOnlysettings.getValue() ? "ro" : "rw";
	}

	private String getPurpose()
	{
		String selectedPurpose = "both";
		if ( purpose.getValue().get( 0 ).equalsIgnoreCase( "ISO Only" ) )
		{
			selectedPurpose = "iso";
		}
		else if ( purpose.getValue().get( 0 ).equalsIgnoreCase( "Blink Only" ) )
		{
			selectedPurpose = "blink";
		}
		return selectedPurpose;
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( configName.validate() && iPAddress.validate() && validateIQN() )
		{
			if ( CHAPIN.getSelectedValue().get( 0 ).equalsIgnoreCase( "yes" ) )
			{
				if ( userName.validate() && userPassword.validate() )
				{
					valid = true;
				}
			}
			else
			{
				valid = true;
			}
		}
		return valid;
	}

	public boolean validateIQN()
	{
		if ( IQN.getSelectedValue() != null && !IQN.getSelectedValue().isEmpty() )
		{
			IQN.setSuccessText( "" );
			IQN.clearErrorText();
			return true;
		}
		else
		{
			IQN.setErrorText( "No IQN found." );
			return false;
		}
	}
}
