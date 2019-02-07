package com.fmlb.forsaos.client.application.management.networking;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.CreateBridgeModel;
import com.fmlb.forsaos.client.application.models.CreateBridgeModelJSON;
import com.fmlb.forsaos.client.application.models.CreateEthernetModel;
import com.fmlb.forsaos.client.application.models.CreateEthernetModelJSON;
import com.fmlb.forsaos.client.application.models.InterfaceModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.server.application.utility.ErrorMessages;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.constants.CheckBoxType;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CreateBridgePopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox bridgeName;

	private MaterialLabel interfacesLabel;

	private MaterialLabel interfaceErrorLabel;

	private Icommand updateNetworkataTableCmd;

	private MaterialRow createBridgeRow;

	private List<InterfaceModel> physicalDeviceList;

	private List<MaterialCheckBox> physicalDeviceCheckBoxList = new ArrayList<>();

	private int counter = 0;

	AppResources resources = GWT.create( AppResources.class );

	public CreateBridgePopup( UIComponentsUtil uiComponentsUtil, Icommand updateNetworkataTableCmd, List<InterfaceModel> physicalDeviceList )
	{
		super( "Create Network", "", "Create Network", true );
		this.updateNetworkataTableCmd = updateNetworkataTableCmd;
		this.uiComponentsUtil = uiComponentsUtil;
		this.physicalDeviceList = physicalDeviceList;
		LoggerUtil.log( "initializeCreate Network popop" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		createBridgeRow = new MaterialRow();

		bridgeName = uiComponentsUtil.getTexBox( "Bridge Name", "", "s12" );

		interfacesLabel = new MaterialLabel( "Select Interfaces" );
		interfacesLabel.setGrid( "s12" );
		interfacesLabel.addStyleName( resources.style().bridgeSelectInterfacesPenal() );

		interfaceErrorLabel = uiComponentsUtil.getErrorLabel( ErrorMessages.SELECT_ATLEAST_ONE_INTERFACE, "s12" );
		interfaceErrorLabel.addStyleName( resources.style().bridgeSelectInterfacesError() );

		createBridgeRow.add( bridgeName );

		createBridgeRow.add( interfacesLabel );
		createBridgeRow.add( interfaceErrorLabel );

		if ( physicalDeviceList != null && !physicalDeviceList.isEmpty() )
		{
			for ( InterfaceModel interfaceModel : physicalDeviceList )
			{
				MaterialCheckBox temp = new MaterialCheckBox( interfaceModel.getName(), CheckBoxType.FILLED );
				temp.setGrid( "s6" );
				temp.addStyleName( resources.style().bridgeSelectInterfacesCheckbox() );
				createBridgeRow.add( temp );
				physicalDeviceCheckBoxList.add( temp );
			}

		}
		getBodyPanel().add( createBridgeRow );
	}

	private List<String> getSelectedPhysicalDevices()
	{
		List<String> interfaces = new ArrayList<>();
		if ( physicalDeviceList != null && !physicalDeviceList.isEmpty() )
		{
			for ( MaterialCheckBox chckBox : physicalDeviceCheckBoxList )
			{
				if ( chckBox.getValue() )
				{
					interfaces.add( chckBox.getText() );
				}

			}
		}
		return interfaces;
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
					List<String> selectedPhysicalDevices = getSelectedPhysicalDevices();
					List<CreateEthernetModel> createEthernetModelList = new ArrayList<CreateEthernetModel>();
					int selectedInterfacesSize = selectedPhysicalDevices.size();
					LoggerUtil.log( "selected interface size " + selectedInterfacesSize );
					for ( String physicalDevice : selectedPhysicalDevices )
					{
						CreateEthernetModel createEthernetModel = new CreateEthernetModel();
						createEthernetModel.setName( physicalDevice );
						CreateEthernetModelJSON createEthernetModelJSON = new CreateEthernetModelJSON( createEthernetModel );
						createEthernetModelList.add( createEthernetModel );
						counter = 0;
						LoggerUtil.log( "counter " + counter );
						MaterialLoader.loading( true, getBodyPanel() );
						CommonServiceProvider.getCommonService().createEthernet( createEthernetModelJSON, new ApplicationCallBack<Boolean>()
						{

							@Override
							public void onSuccess( Boolean result )
							{
								LoggerUtil.log( "ethnernet sucess " + counter );
								if ( result != null )
								{
									counter++;
									if ( counter == selectedInterfacesSize )
									{
										LoggerUtil.log( "ALL ETHERNET DEVICES ARE REGISTERED" );
										createBridge();
									}
								}
								else
								{
									LoggerUtil.log( "unable to register interface STOP HERE" );
									MaterialLoader.loading( false, getBodyPanel() );
									ArrayList<String> errorMsgs = new ArrayList<>();
									errorMsgs.add( "Unable to register interface" );
									ErrorPopup errorPopup = new ErrorPopup( errorMsgs, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
									errorPopup.open();
								}
							}

							@Override
							public void onFailure( Throwable caught )
							{
								if ( caught instanceof FBException )
								{
									FBException fbException = ( FBException ) caught;
									LoggerUtil.log( "create ethernet on failure mesg " + fbException.getErrorMessage() );
									if ( !fbException.getErrorMessage().equalsIgnoreCase( ErrorMessages.DUPLICATE_DB_RECORD ) )
									{
										super.onFailureShowErrorPopup( caught, null );
									}
								}
								counter++;
								if ( counter == selectedInterfacesSize )
								{
									createBridge();
								}
							}
						} );
					}

				}
			}

		} );
	}

	private void createBridge()
	{
		LoggerUtil.log( "create bridge " + counter );
		LoggerUtil.log( "START TO GREAT BRIDGE" );
		CreateBridgeModel createBridgeModel = new CreateBridgeModel( getSelectedPhysicalDevices() );
		createBridgeModel.setName( bridgeName.getValue() );
		CreateBridgeModelJSON createBridgeModelJSON = new CreateBridgeModelJSON( createBridgeModel );
		MaterialLoader.loading( true, getBodyPanel() );
		LoggerUtil.log( "FIRE CERATE BRIDGE API" );
		CommonServiceProvider.getCommonService().createBridge( createBridgeModelJSON, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				if ( result != null && result )
				{
					LoggerUtil.log( "BRIDGE CREATION SUCCESSFUL" );
					MaterialToast.fireToast( createBridgeModel.getName() + " Created..!", "rounded" );
					MaterialLoader.loading( false, getBodyPanel() );
					close();
					if ( updateNetworkataTableCmd != null )
					{
						updateNetworkataTableCmd.execute();
					}
				}
				else
				{
					MaterialLoader.loading( false, getBodyPanel() );
					ArrayList<String> errorMsgs = new ArrayList<>();
					errorMsgs.add( "Unable to create bridge" );
					ErrorPopup errorPopup = new ErrorPopup( errorMsgs, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
					errorPopup.open();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;

		if ( getSelectedPhysicalDevices().size() > 0 )
		{
			interfaceErrorLabel.setVisible( false );
		}
		else
		{
			interfaceErrorLabel.setVisible( true );
		}
		if ( bridgeName.validate() && getSelectedPhysicalDevices().size() > 0 )
		{
			valid = true;
		}
		else
		{
			bridgeName.validate();
		}

		return valid;

	}
}
