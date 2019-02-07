package com.fmlb.forsaos.client.application.vm;

import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.models.BridgeJSONModel;
import com.fmlb.forsaos.client.application.models.ComboBoxModel;
import com.fmlb.forsaos.client.application.models.LVNetCreateRequestModel;
import com.fmlb.forsaos.client.application.models.NameModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;

public class NetworkAdaptersPanel extends Composite
{
	AppResources resources = GWT.create( AppResources.class );

	private MaterialPanel panel;

	private MaterialIcon deleteRecord;

	private List<BridgeJSONModel> bridgeJSONModels;

	private UIComponentsUtil uiComponentsUtil;

	private String label;

	private MaterialComboBox< ? extends ComboBoxModel> bridgeComboBox;

	private CreateVMPopup createVMPopup;

	private int index;

	private String vmName;

	private LVNetCreateRequestModel lvNetCreateRequestModel = null;

	public NetworkAdaptersPanel( List<BridgeJSONModel> bridgeJSONModels, UIComponentsUtil uiComponentsUtil, CreateVMPopup createVMPopup, int index, String vmName )
	{
		super();
		this.uiComponentsUtil = uiComponentsUtil;
		this.bridgeJSONModels = bridgeJSONModels;
		int nextInt = Random.nextInt( 1000 );
		LoggerUtil.log( "nextInt :: " + nextInt );
		this.label = "VNET" + nextInt;
		this.createVMPopup = createVMPopup;
		this.index = index;
		this.vmName = vmName;
		initialize();
		initWidget( panel );
	}

	private void initialize()
	{
		panel = new MaterialPanel();
		panel.addStyleName( resources.style().create_VM_Network_body() );
		bridgeComboBox = uiComponentsUtil.getComboBoxModelDropDown( bridgeJSONModels, label, "s11" );
		bridgeComboBox.addStyleName( "create_VM_network_bridgeComboBox" );
		bridgeComboBox.setHideSearch( true );
		bridgeComboBox.addSelectionHandler( selectionEvent -> {
			BridgeJSONModel singleValue = ( BridgeJSONModel ) bridgeComboBox.getSingleValue();
			LoggerUtil.log( singleValue.getName() );
			if ( singleValue.getName().equalsIgnoreCase( "none" ) )
			{
				bridgeComboBox.setErrorText( "Please select bridge." );
			} else {
				bridgeComboBox.clearErrorText();
				bridgeComboBox.setSuccessText( "" );
			}
			deleteNetworkAdapter( singleValue );
			lvNetCreateRequestModel = new LVNetCreateRequestModel( vmName + "_" + label, "lbridge", singleValue.getName() );
			CommonServiceProvider.getCommonService().createAdapter( lvNetCreateRequestModel, new ApplicationCallBack<Boolean>()
			{
				@Override
				public void onSuccess( Boolean result )
				{

				}

				@Override
				public void onFailure( Throwable caught )
				{
					createVMPopup.deleteNetworkAdapter( index );
					super.onFailureShowErrorPopup( caught, null );
				}
			} );
		} );

		deleteRecord = new MaterialIcon( IconType.DELETE, Color.BLUE );
		deleteRecord.addStyleName( resources.style().create_vm_netwrok_delete() );
		deleteRecord.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				createVMPopup.deleteNetworkAdapter( index );
				deleteNetworkAdapter( null );
			}
		} );
		panel.add( bridgeComboBox );
		panel.add( deleteRecord );
	}

	private void deleteNetworkAdapter( BridgeJSONModel singleValue )
	{
		if ( lvNetCreateRequestModel != null )
		{
			if ( singleValue != null && lvNetCreateRequestModel.getName().equalsIgnoreCase( singleValue.getName() ) )
			{
				return;
			}
			else
			{
				CommonServiceProvider.getCommonService().deleteAdapter( new NameModel( lvNetCreateRequestModel.getName() ), new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{

					}

					@Override
					public void onFailure( Throwable caught )
					{
						bridgeComboBox.setSingleValue( null );
						super.onFailureShowErrorPopup( caught, null );
					}
				} );
			}
		}
	}

	public AppResources getResources()
	{
		return resources;
	}

	public void setResources( AppResources resources )
	{
		this.resources = resources;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel( String label )
	{
		this.label = label;
		bridgeComboBox.setLabel( this.label );
	}

	public void setIndex( int index )
	{
		this.index = index;
	}

	public MaterialComboBox< ? extends ComboBoxModel> getBridgeComboBox()
	{
		return bridgeComboBox;
	}

	public void setBridgeComboBox( MaterialComboBox< ? extends ComboBoxModel> bridgeComboBox )
	{
		this.bridgeComboBox = bridgeComboBox;
	}

	public LVNetCreateRequestModel getLvNetCreateRequestModel()
	{
		return lvNetCreateRequestModel;
	}

	public void setLvNetCreateRequestModel( LVNetCreateRequestModel lvNetCreateRequestModel )
	{
		this.lvNetCreateRequestModel = lvNetCreateRequestModel;
	}
}
