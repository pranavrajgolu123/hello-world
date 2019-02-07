package com.fmlb.forsaos.client.application.management.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.REPOLocalCreateModel;
import com.fmlb.forsaos.client.application.models.REPOLocalEditModel;
import com.fmlb.forsaos.client.application.models.REPOMountInfoModel;
import com.fmlb.forsaos.client.application.models.REPOParameterLocalModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.google.gwt.core.client.GWT;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.html.Option;

public class CreateLocalRepoPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox configurationName;

	private HashMap<String, String> deviceDropDownOptionMap;

	private MaterialComboBox<String> fileSystemDropDown;

	private HashMap<String, String> fileSystemDropDownOptionMap;

	private MaterialComboBox<String> deviceDropDown;

	private MaterialComboBox<String> protocolDropDown;

	private MaterialComboBox<String> purposeDropDown;

	private REPOMountInfoModel[] repoMountInfoModels = null;

	private IcommandWithData icommand;

	private REPOLocalCreateModel repoMainModel;

	private Boolean isNew;

	AppResources resources = GWT.create( AppResources.class );

	public CreateLocalRepoPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData icommand, REPOLocalCreateModel repoMainModel, Boolean isNew )
	{
		super( "Local Configuration", "", "Create Local Configuration", true );
		this.icommand = icommand;
		this.uiComponentsUtil = uiComponentsUtil;
		this.repoMainModel = repoMainModel;
		this.isNew = isNew;
		initialize();
		getDeviceList();
		buttonAction();
		if ( isNew )
		{
			getButton().setText( "Create Local Configuration" );
		}
		else
		{
			getButton().setText( "Update Local Configuration" );
		}
	}

	private void setValues( Boolean isNew )
	{
		if ( !isNew )
		{
			configurationName.setValue( repoMainModel.getName() );
			if ( repoMainModel.getAutomount() )
			{
				protocolDropDown.setSingleValue( "Activate config" );
			}
			else
			{
				protocolDropDown.setSingleValue( "Do not activate config" );
			}
			fileSystemDropDown.setSingleValue( repoMainModel.getParameters().getFs() );
			if ( repoMainModel.getParameters() != null && repoMainModel.getParameters().getDevices_list() != null )
			{
				if ( repoMainModel.getParameters().getDevices_list().length == repoMountInfoModels.length )
				{
					deviceDropDown.setSingleValue( "All" );
				}
				else if ( repoMainModel.getParameters().getDevices_list().length == 1 )
				{
					deviceDropDown.setSingleValue( repoMainModel.getParameters().getDevices_list()[0] );
				}
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
		}
	}

	private void getDeviceList()
	{
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().discoverConfig( "{\"protocol\":\"local\"}", new ApplicationCallBack<REPOMountInfoModel[]>()
		{
			@Override
			public void onSuccess( REPOMountInfoModel[] result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				repoMountInfoModels = result;
				if ( result != null && result.length > 0 )
				{
					deviceDropDown.clear();
					Option option = new Option();
					option.setValue( "All" );
					option.setText( "All" );
					option.setTitle( "All" );
					deviceDropDown.add( option );

					for ( REPOMountInfoModel repoMountInfoModel : result )
					{
						option = new Option();
						option.setValue( repoMountInfoModel.getSource() );
						option.setText( repoMountInfoModel.getSource() );
						option.setTitle( repoMountInfoModel.getSource() );
						deviceDropDown.add( option );
					}
					setValues( isNew );
					validateDeviceDropDown();
				}
				else
				{
					deviceDropDown.clear();
					validateDeviceDropDown();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				deviceDropDown.clear();
				validateDeviceDropDown();
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	@SuppressWarnings( "unchecked" )
	private void initialize()
	{

		MaterialRow createLocalRow = new MaterialRow();

		configurationName = uiComponentsUtil.getTexBox( "Configuration Name", "Enter Configuration Name", "s6" );
		uiComponentsUtil.addTextBoxValidator( configurationName );

		deviceDropDownOptionMap = new HashMap<String, String>();

		deviceDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( deviceDropDownOptionMap, "Device", "s6" );

		fileSystemDropDownOptionMap = new HashMap<String, String>();
		fileSystemDropDownOptionMap.put( "EXT2", "EXT2" );
		fileSystemDropDownOptionMap.put( "EXT3", "EXT3" );
		fileSystemDropDownOptionMap.put( "EXT4", "EXT4" );
		fileSystemDropDownOptionMap.put( "FAT", "FAT" );
		fileSystemDropDownOptionMap.put( "VFAT", "VFAT" );
		fileSystemDropDownOptionMap.put( "NTFS", "NTFS" );

		fileSystemDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( fileSystemDropDownOptionMap, "File System", "s6" );

		HashMap<String, String> protocolDropDownOptionMap = new HashMap<String, String>();
		protocolDropDownOptionMap.put( "Activate config", "Activate config" );
		protocolDropDownOptionMap.put( "Do not activate config", "Do not activate config" );

		protocolDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( protocolDropDownOptionMap, "Activate Protocol?", "s6" );

		HashMap<String, String> purposeDropDownOptionMap = new HashMap<String, String>();
		purposeDropDownOptionMap.put( "ISO Only", "ISO Only" );
		purposeDropDownOptionMap.put( "Blink Only", "Blink Only" );
		purposeDropDownOptionMap.put( "Both ISO and Blink", "Both ISO and Blink" );

		purposeDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( purposeDropDownOptionMap, "Purpose", "s6" );

		createLocalRow.add( configurationName );
		createLocalRow.add( deviceDropDown );
		createLocalRow.add( fileSystemDropDown );
		createLocalRow.add( protocolDropDown );
		createLocalRow.add( purposeDropDown );

		getBodyPanel().add( createLocalRow );
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
					LoggerUtil.log( "Local" );
					String automountValue = protocolDropDown.getValue().get( 0 );
					Boolean automount = automountValue.equalsIgnoreCase( "Activate config" ) ? true : false;
					String[] devices_list = getSelectedDeviceList();
					REPOParameterLocalModel parameters = new REPOParameterLocalModel( fileSystemDropDown.getValue().get( 0 ), devices_list );
					String purpose = getPurpose();
					REPOLocalCreateModel createLocalRepoModel = new REPOLocalCreateModel( null, configurationName.getValue(), "local", automount, null, parameters, purpose );
					if ( isNew )
					{
						createLocalRepo( createLocalRepoModel );
					}
					else
					{
						REPOLocalEditModel editRepoMainModel = new REPOLocalEditModel( repoMainModel.get_id(), createLocalRepoModel );
						editLocalRepo( editRepoMainModel );
					}
				}
			}
		} );
	}

	private void editLocalRepo( REPOLocalEditModel editRepoMainModel )
	{
		CommonServiceProvider.getCommonService().updateConfigLocal( editRepoMainModel, new ApplicationCallBack<Boolean>()
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

	private void createLocalRepo( REPOLocalCreateModel repoModel )
	{
		CommonServiceProvider.getCommonService().addConfigLocal( repoModel, new ApplicationCallBack<Boolean>()
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

	private String[] getSelectedDeviceList()
	{
		String[] selectedDevices = null;
		if ( deviceDropDown.getValue().get( 0 ).equalsIgnoreCase( "all" ) )
		{
			selectedDevices = new String[repoMountInfoModels.length];
			int i = 0;
			for ( REPOMountInfoModel repoMountInfoModel : repoMountInfoModels )
			{
				selectedDevices[i++] = repoMountInfoModel.getSource();
			}
		}
		else
		{
			selectedDevices = new String[1];
			selectedDevices[0] = deviceDropDown.getValue().get( 0 );
		}
		return selectedDevices;
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;

		if ( configurationName.validate() && validateDeviceDropDown() )
		{
			valid = true;
		}
		return valid;
	}

	public boolean validateDeviceDropDown()
	{
		if ( deviceDropDown.getSelectedValue() != null && !deviceDropDown.getSelectedValue().isEmpty() )
		{
			deviceDropDown.clearErrorText();
			deviceDropDown.setSuccessText( "" );
			return true;
		}
		else
		{
			deviceDropDown.setErrorText( "No device found." );
			return false;
		}
	}
}
