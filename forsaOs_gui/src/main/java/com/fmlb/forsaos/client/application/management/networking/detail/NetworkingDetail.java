package com.fmlb.forsaos.client.application.management.networking.detail;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.IPType;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.NetworkDeviceType;
import com.fmlb.forsaos.client.application.models.CreateNetworkModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.InterfaceModel;
import com.fmlb.forsaos.client.application.models.NetworkingModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.CheckBoxType;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class NetworkingDetail extends MaterialPanel
{

	private CreateNetworkModel networkModel;

	private UIComponentsUtil uiComponentsUtil;

	AppResources resources = GWT.create( AppResources.class );

	private IcommandWithData navigateToNetworkingGrid;

	private ConfirmPasswordPopup delNetworkingDevicePopup;

	private CurrentUser currentUser;

	private MaterialCollectionSecondary ipV4StatusCollectn;

	private MaterialCollectionSecondary ipV6StatusCollectn;

	private MaterialLabel ipV4StatusLbl;

	private MaterialLabel ipV6StatusLbl;

	private NetworkingModel networkingModel;

	private List<MaterialCheckBox> interfacesCheckboxList;

	public NetworkingDetail( CreateNetworkModel updateNetworkModel, NetworkingModel networkingModel, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigateToNetworkingGrid )
	{
		this.networkModel = updateNetworkModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigateToNetworkingGrid = navigateToNetworkingGrid;
		this.networkingModel = networkingModel;
		MaterialTextBox networkModelName = this.uiComponentsUtil.getDetailNameEdit( updateNetworkModel.getName() );
		networkModelName.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
			}
		} );

		add( networkModelName );
		add( createDetailHeaderPanel() );
		add( createCollection() );
	}

	private MaterialRow createCollection()
	{

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );

		MaterialCollection collection2 = new MaterialCollection();
		collection2.addStyleName( resources.style().noMargin() );

		MaterialCollectionItem collectionItem1 = new MaterialCollectionItem();
		collectionItem1.add( this.uiComponentsUtil.getLabel( "IPv4", "", resources.style().displayInline() ) );
		ipV4StatusCollectn = new MaterialCollectionSecondary();
		ipV4StatusCollectn.getElement().getStyle().setCursor( Cursor.POINTER );
		ipV4StatusLbl = this.uiComponentsUtil.getLabel( "Automatic", "", resources.style().displayInline() );
		ipV4StatusCollectn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				UpdateNetworkingDetailV4Popup updateNetworkingDetailPopup = new UpdateNetworkingDetailV4Popup( networkModel, uiComponentsUtil, new Icommand()
				{

					@Override
					public void execute()
					{
						updateNetworkDetails();

					}
				}, IPType.IP_V4 );
				updateNetworkingDetailPopup.open();

			}
		} );
		ipV4StatusCollectn.add( ipV4StatusLbl );
		collectionItem1.add( ipV4StatusCollectn );

		MaterialCollectionItem collectionItem2 = new MaterialCollectionItem();
		collectionItem2.add( this.uiComponentsUtil.getLabel( "IPv6", "", resources.style().displayInline() ) );
		ipV6StatusCollectn = new MaterialCollectionSecondary();
		ipV6StatusLbl = this.uiComponentsUtil.getLabel( "Automatic", "", resources.style().displayInline() );
		ipV6StatusCollectn.getElement().getStyle().setCursor( Cursor.POINTER );
		ipV6StatusCollectn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				UpdateNetworkingDetailV6Popup updateNetworkingDetailPopup = new UpdateNetworkingDetailV6Popup( networkModel, uiComponentsUtil, new Icommand()
				{
					@Override
					public void execute()
					{
						updateNetworkDetails();
					}
				}, IPType.IP_V6 );
				updateNetworkingDetailPopup.open();
			}
		} );
		ipV6StatusCollectn.add( ipV6StatusLbl );
		collectionItem2.add( ipV6StatusCollectn );

		MaterialCollectionItem collectionItem3 = new MaterialCollectionItem();
		collectionItem3.add( this.uiComponentsUtil.getLabel( "MAC", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary3 = new MaterialCollectionSecondary();
		collectionSecondary3.add( this.uiComponentsUtil.getLabel( "", "", resources.style().displayInline() ) );
		collectionItem3.add( collectionSecondary3 );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		// commenting this as MAC is not being passed in list bridges
		//collection2.add( collectionItem3 );

		MaterialRow row = new MaterialRow();
		row.addStyleName( resources.style().lem_detail_row() );
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s6" );
		column1.addStyleName( resources.style().lem_detail_column() );
		column1.add( collection1 );

		MaterialColumn column2 = new MaterialColumn();
		column2.addStyleName( resources.style().lem_detail_column() );
		column2.setGrid( "s6" );
		column2.add( collection2 );
		row.add( column1 );
		changeIPStatusLbl();

		if ( networkingModel.getType().equals( NetworkDeviceType.BRIDGE ) )
		{
			getInterfacesAndCreateList( collection2 );
			row.add( column2 );
		}
		return row;
	}

	private void getInterfacesAndCreateList( MaterialCollection collection2 )
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getPhysicalDeviceList( null, new ApplicationCallBack<List<InterfaceModel>>()
		{
			@Override
			public void onSuccess( List<InterfaceModel> result )
			{
				MaterialLoader.loading( false );
				createInterfacesList( collection2, result, false );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				createInterfacesList( collection2, null, true );
			}
		} );
	}

	private void updateInterfaceList()
	{
		MaterialLoader.loading( true );
		List<String> interfaces = new ArrayList<String>();
		LoggerUtil.log( "" + interfacesCheckboxList.size() );
		for ( MaterialCheckBox interf : interfacesCheckboxList )
		{
			if ( interf.getValue() )
			{
				interfaces.add( interf.getText() );
			}
		}
		
		CreateNetworkModel networkModelNew = new CreateNetworkModel( networkingModel.getName(), null, null, null, null, null, null, null, interfaces, NetworkDeviceType.BRIDGE );
		CommonServiceProvider.getCommonService().updateNetworkDevice( networkModelNew, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				MaterialToast.fireToast( "Updated interface list successfully.", "rounded" );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createInterfacesList( MaterialCollection collection2, List<InterfaceModel> interfacesFetched, boolean failed )
	{
		MaterialCollectionItem collectionItem4 = new MaterialCollectionItem();
		MaterialPanel interfacePanel = new MaterialPanel();
		if ( failed )
		{
			collectionItem4.add( uiComponentsUtil.getLabel( "Unable to fetch interfaces.", "s12" ) );
			if ( networkingModel.getInterfaces() == null || networkingModel.getInterfaces().isEmpty() )
			{
				collectionItem4.add( uiComponentsUtil.getLabel( "No interfaces attached.", "s12" ) );
			}
			else
			{
				interfacesCheckboxList = new ArrayList<MaterialCheckBox>();
				for ( String inter : networkingModel.getInterfaces() )
				{
					MaterialCheckBox interfaceCheckbox = new MaterialCheckBox( inter, CheckBoxType.FILLED );
					interfaceCheckbox.setValue( true );
					interfacePanel.add( interfaceCheckbox );
					interfacesCheckboxList.add( interfaceCheckbox );
				}
			}
		}
		else
		{
			interfacesCheckboxList = new ArrayList<MaterialCheckBox>();
			for ( InterfaceModel interfaceModel : interfacesFetched )
			{
				MaterialCheckBox interfaceCheckbox = new MaterialCheckBox( interfaceModel.getName() );
				if ( networkingModel.getInterfaces().contains( interfaceModel.getName() ) )
				{
					interfaceCheckbox.setValue( true );
				}
				interfacePanel.add( interfaceCheckbox );
				interfacesCheckboxList.add( interfaceCheckbox );
			}
		}
		MaterialButton updateInterfaces = new MaterialButton( "Update" );
		updateInterfaces.addStyleName( resources.style().networkUpdateBtn() );
		updateInterfaces.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				updateInterfaceList();
			}
		} );

		collectionItem4.add( this.uiComponentsUtil.getLabel( "Interfaces", "", resources.style().InterfacesHeaderTxt() ) );
		collectionItem4.add( interfacePanel );
		interfacePanel.setGrid( "s12" );
		interfacePanel.addStyleName( resources.style().InterfacesCheckBox() );
		collectionItem4.add( updateInterfaces );
		collection2.add( collectionItem4 );
	}

	private MaterialRow createDetailHeaderPanel()
	{
		MaterialRow detailHeaderPanel = new MaterialRow();
		detailHeaderPanel.addStyleName( resources.style().lemDetail() );
		detailHeaderPanel.setId( "lemdetailsChartButton" + Document.get().createUniqueId() );
		detailHeaderPanel.setGrid( "s12" );
		detailHeaderPanel.addStyleName( "detail_header_penal" );

		MaterialLabel detailLabel = this.uiComponentsUtil.getLabel( "Details", "s12"/* , resources.style().lemDetail() */ );
		detailLabel.addStyleName( resources.style().detail_header() );
		detailLabel.setBorder( "2" );
		detailLabel.setGrid( "s6" );

		MaterialColumn actionItemCol = new MaterialColumn();
		actionItemCol.setGrid( "s4" );
		actionItemCol.addStyleName( resources.style().action_item_bar() );
		MaterialButton deleteNetworkBtn = new MaterialButton( "", IconType.DELETE, ButtonType.FLAT );
		deleteNetworkBtn.setTitle( "Delete LEM" );
		deleteNetworkBtn.addStyleName( resources.style().lem_delete_btn() );
		deleteNetworkBtn.setIconPosition( IconPosition.RIGHT );
		deleteNetworkBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				deleteNetworkingBtnAction();
			}
		} );
		// Commented as feature unavailable
		//actionItemCol.add(expandlemBtn);
		actionItemCol.add( deleteNetworkBtn );
		detailHeaderPanel.add( detailLabel );
		detailHeaderPanel.add( actionItemCol );
		return detailHeaderPanel;
	}

	private void deleteNetworkingBtnAction()
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to delete " + networkModel.getName() + " ?" );
		delNetworkingDevicePopup = new ConfirmPasswordPopup( "Delete Device", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{

			@Override
			public void execute()
			{
				MaterialLoader.loading( true, delNetworkingDevicePopup.getBodyPanel() );
				CommonServiceProvider.getCommonService().deleteNetworkingDevice( networkModel.getType(), networkModel.getName(), new ApplicationCallBack<Boolean>()
				{

					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, delNetworkingDevicePopup.getBodyPanel() );
						delNetworkingDevicePopup.close();
						MaterialToast.fireToast( networkModel.getName() + " Deleted..!", "rounded" );
						if ( navigateToNetworkingGrid != null )
						{
							navigateToNetworkingGrid.executeWithData( true );
						}
					}

					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false, delNetworkingDevicePopup.getBodyPanel() );
						super.onFailureShowErrorPopup( caught, null );

					}
				} );
			}
		} );
		delNetworkingDevicePopup.open();

	}

	private void changeIPStatusLbl()
	{
		if ( networkModel.getDhcp4() )
		{
			ipV4StatusLbl.setText( "Automatic" );
			ipV4StatusLbl.setTitle( "Automatic" );
		}
		else
		{
			ipV4StatusLbl.setText( "Manual" );
			ipV4StatusLbl.setTitle( "Manual" );
		}
		if ( networkModel.getDhcp6() )
		{
			ipV6StatusLbl.setText( "Automatic" );
			ipV6StatusLbl.setTitle( "Automatic" );
		}
		else
		{
			ipV6StatusLbl.setText( "Manual" );
			ipV6StatusLbl.setTitle( "Manual" );
		}
	}

	private void updateNetworkDetails()
	{
		MaterialLoader.loading( true );
		LoggerUtil.log( "updateNetworkdetails()   --  open" );
		CommonServiceProvider.getCommonService().getNetworkDeviceCommonProperties( networkModel.getName(), networkModel.getType(), new ApplicationCallBack<CreateNetworkModel>()
		{
			@Override
			public void onSuccess( CreateNetworkModel updateNetworkModel )
			{
				LoggerUtil.log( "updateNetworkdetails()   --  close" );
				networkModel = updateNetworkModel;
				changeIPStatusLbl();
				MaterialLoader.loading( false );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				LoggerUtil.log( "updateNetworkdetails()   --  close" );
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );

			}
		} );
	}
}
