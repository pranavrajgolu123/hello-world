package com.fmlb.forsaos.client.application.management.networking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.ConfirmationPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.NetworkDeviceType;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.InterfaceModel;
import com.fmlb.forsaos.client.application.models.NetworkingModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.events.RowSelectEvent;
import gwt.material.design.client.data.events.RowSelectHandler;
import gwt.material.design.client.data.events.SelectAllEvent;
import gwt.material.design.client.data.events.SelectAllHandler;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class NetworkingDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<NetworkingModel> materialDataTable;

	private MaterialDataPager<NetworkingModel> pager;

	private ListDataSource<NetworkingModel> dataSource;

	private MaterialLabel networkingLabel;

	private MaterialLabel totalNetworkLabel;

	private int totalNetworks;

	private MaterialButton createNetworkBtn;

	private MaterialButton deleteMultipleNetworksIcon;

	MaterialSearch search;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private ConfirmPasswordPopup delNetworkingDevicePopup;

	private MaterialDropDown createDeviceDropDown;

	private MaterialLink createBridgeLink;

	private MaterialLink createBondLink;

	private MaterialLink createEthernetLink;

	private MaterialLink createVLANLink;

	private MaterialButton netApply;

	private ConfirmationPopup confirmationPopup;
	
	AppResources resources = GWT.create( AppResources.class );

	public NetworkingDataTable( int height, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createCreateNetworkBtn();
		createDeleteMultiItemsIcon();
		createNetApply();
		tableInit();
	}

	private void createNetApply()
	{
		netApply = new MaterialButton( "Apply" );
		netApply.addStyleName( resources.style().sendAlertBtn() );
		netApply.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				ArrayList<String> warningMsgList = new ArrayList<String>();
				warningMsgList.add( "Please make sure all configurations are correct. Do you want to apply?" );
				confirmationPopup = new ConfirmationPopup( "Apply Network", "Apply", warningMsgList, uiComponentsUtil, new Icommand()
				{
					@Override
					public void execute()
					{
						confirmationPopup.close();
						MaterialLoader.loading( true );
						CommonServiceProvider.getCommonService().applyNetwork( new ApplicationCallBack<Boolean>()
						{
							@Override
							public void onSuccess( Boolean result )
							{
								LoggerUtil.log( "NETWORK APPLY SUCCESSFUL" );
								MaterialLoader.loading( false );
							}

							@Override
							public void onFailure( Throwable caught )
							{
								LoggerUtil.log( "NETWORK APPLY FAIL" );
								MaterialLoader.loading( false );
								super.onFailureShowErrorPopup( caught, null );
							}
						} );
					}
				} );
				confirmationPopup.open();
			}
		} );
	}

	private void createSearchComponent()
	{
		search = new MaterialSearch();
		search.setBackgroundColor( Color.WHITE );
		search.setIconColor( Color.BLACK );
		search.setActive( true );
		search.setShadow( 1 );
		search.setDisplay( Display.INLINE );
		search.setWidth( "30" );
	}

	private void createCreateNetworkBtn()
	{
		createNetworkBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create Network" );
//		createNetworkBtn.setActivates( "createNetworkBtn" );
		createDeviceDropDown = new MaterialDropDown( "createDeviceDropDown" );
		createDeviceDropDown.setHover( true );
		createDeviceDropDown.setConstrainWidth( false );
		createDeviceDropDown.setBelowOrigin( true );
//		createNetworkBtn.add( createDeviceDropDown );

		createBridgeLink = getLink( "Bridge" );
		createBondLink = getLink( "Bond" );
		createEthernetLink = getLink( "Ethernet" );
		createVLANLink = getLink( "VLAN" );

		createDeviceDropDown.add( createBridgeLink );
		createDeviceDropDown.add( createBondLink );
		createDeviceDropDown.add( createEthernetLink );
		createDeviceDropDown.add( createVLANLink );

		/*createBridgeLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createNetworkBridgeBtnAction();

			}
		} );*/
		
		createNetworkBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				createNetworkBridgeBtnAction();
			}
		} );
	}

	private void createDeleteMultiItemsIcon()
	{
		deleteMultipleNetworksIcon = this.uiComponentsUtil.getDeleteMultipleDataItemIcon();
		deleteMultipleNetworksIcon.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				deleteMultipleNetworksBtnAction();
			}
		} );
	}

	private void createNetworkBridgeBtnAction()
	{

		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getPhysicalDeviceList( null, new ApplicationCallBack<List<InterfaceModel>>()
		{

			@Override
			public void onSuccess( List<InterfaceModel> result )
			{
				MaterialLoader.loading( false );
				CreateBridgePopup createNetworkPopup = new CreateBridgePopup( uiComponentsUtil, getUpdateIcommand(), result );
				createNetworkPopup.open();

			}
		} );

	}

	private void deleteNetworkingBtnAction( NetworkingModel networkingModel )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to delete " + networkingModel.getName() + "?" );
		delNetworkingDevicePopup = new ConfirmPasswordPopup( "Delete Device", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{

			@Override
			public void execute()
			{
				MaterialLoader.loading( true, delNetworkingDevicePopup.getBodyPanel() );
				CommonServiceProvider.getCommonService().deleteNetworkingDevice( networkingModel.getType(), networkingModel.getName(), new ApplicationCallBack<Boolean>()
				{

					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, delNetworkingDevicePopup.getBodyPanel() );
						MaterialToast.fireToast( networkingModel.getName() + " Deleted..!", "rounded" );
						delNetworkingDevicePopup.close();
						getUpdateIcommand().execute();
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

	private void deleteMultipleNetworksBtnAction()
	{
		List<NetworkingModel> selectedRowModels = materialDataTable.getSelectedRowModels( false );
		if ( selectedRowModels.size() > 0 )
		{
			/*
			DeleteLEMsPopUp deleteLEMsPopUp = new DeleteLEMsPopUp( selectedRowModels, this.uiComponentsUtil, getUpdateIcommand(), currentUser );
			deleteLEMsPopUp.open();
			*/}
		else
		{
			//this.uiComponentsUtil.getMaterialDialog( "Destroy LEMs", "Please select atleast one LEM", "Close" ).open();
		}

	}

	private void enableDisableDelIcon()
	{
		if ( materialDataTable.getSelectedRowModels( false ).size() > 0 )
		{
			deleteMultipleNetworksIcon.setEnabled( true );
			LoggerUtil.log( "enable delete button" );
		}
		else
		{
			deleteMultipleNetworksIcon.setEnabled( false );
			LoggerUtil.log( "disable delete button" );
		}
	}

	private void actionOnNetworkNameClick( NetworkingModel networkingModel )
	{
		if ( navigationCmd != null )
		{
			LoggerUtil.log( "fire navigation event" );
			navigationCmd.executeWithData( networkingModel );
		}

	}

	private void createPagination()
	{
		pager = new MaterialDataPager<NetworkingModel>( materialDataTable, dataSource );
		this.uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<NetworkingModel>();
		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					LoggerUtil.log( "attach lem data table" );
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					materialDataTable.setHeight( "calc(72vh - 50px)" );
					// materialDataTable.setTitle("LEMS");
					// materialDataTable.addStyleName("materialDataTableTabWorkStep");
					// materialDataTable.setMarginTop(64.00);
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					networkingLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Networks" );
					totalNetworkLabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalNetworks + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( networkingLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalNetworkLabel );
					materialDataTable.getScaffolding().getToolPanel().add( netApply );
					materialDataTable.getScaffolding().getToolPanel().add( createNetworkBtn );
					// commenting this as multiple deletes cannot be done
					//materialDataTable.getScaffolding().getToolPanel().add( deleteMultipleNetworksIcon );
					// materialDataTable.getScaffolding().getToolPanel().add(search);
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					// materialDataTable.getScaffolding().getTopPanel().add(search);
				}

			}
		} );

		materialDataTable.addColumn( new WidgetColumn<NetworkingModel, MaterialLabel>()
		{
			@Override
			public Comparator< ? super RowComponent<NetworkingModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}
			/*
			 * @Override public TextAlign textAlign() { return TextAlign.CENTER; }
			 */

			@Override
			public MaterialLabel getValue( NetworkingModel networkingModel )
			{
				MaterialLabel networkNameLabel = new MaterialLabel();
				networkNameLabel.setText( networkingModel.getName() );
				networkNameLabel.setTitle( networkingModel.getName() );
				networkNameLabel.setHoverable( true );
				networkNameLabel.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						actionOnNetworkNameClick( networkingModel );
					}
				} );
				return networkNameLabel;
			}

		}, "Name" );

		materialDataTable.addColumn( new TextColumn<NetworkingModel>()
		{
			@Override
			public Comparator< ? super RowComponent<NetworkingModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getInterfaces().size() - o2.getData().getInterfaces().size();
			}

			@Override
			public String getValue( NetworkingModel object )
			{
				String text = "NA";
				if ( object.getType().equals( NetworkDeviceType.BRIDGE ) )
				{
					text = "" + object.getInterfaces().size();
				}
				return text;
			}
		}, "Interfaces" );

		materialDataTable.addColumn( new TextColumn<NetworkingModel>()
		{
			@Override
			public Comparator< ? super RowComponent<NetworkingModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getType().compareTo( o2.getData().getType() );
			}

			@Override
			public String getValue( NetworkingModel object )
			{
				if ( object.getType().equals( NetworkDeviceType.BRIDGE ) )
				{
					return "Bridge";
				}
				else if ( object.getType().equals( NetworkDeviceType.BOND ) )
				{
					return "Bond";
				}
				else if ( object.getType().equals( NetworkDeviceType.ETHERNET ) )
				{
					return "Ethernet";
				}
				else if ( object.getType().equals( NetworkDeviceType.VLAN ) )
				{
					return "VLAN";
				}
				return object.getType().getValue();
			}
		}, "Type" );

		materialDataTable.addColumn( new WidgetColumn<NetworkingModel, MaterialButton>()
		{

			@Override
			public MaterialButton getValue( NetworkingModel object )
			{
				MaterialButton deleteBtn = new MaterialButton();
				deleteBtn.setTitle( "Delete" );
				deleteBtn.setIconType( IconType.DELETE );
				deleteBtn.setBackgroundColor( Color.BLUE );
				deleteBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						deleteNetworkingBtnAction( object );

					}
				} );
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addRowSelectHandler( new RowSelectHandler<NetworkingModel>()
		{

			@Override
			public void onRowSelect( RowSelectEvent<NetworkingModel> event )
			{
				enableDisableDelIcon();
			}

		} );
		materialDataTable.addSelectAllHandler( new SelectAllHandler<NetworkingModel>()
		{

			@Override
			public void onSelectAll( SelectAllEvent<NetworkingModel> event )
			{
				enableDisableDelIcon();
			}
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void tableInit()
	{
		dataSource = new ListDataSource<NetworkingModel>();
		List<NetworkingModel> result = new ArrayList<NetworkingModel>();
		dataSource.add( 0, result );
		totalNetworks = result.size();

		createTable();
		createPagination();
		materialRow.add( materialDataTable );
		add( materialRow );
	}

	public Icommand getUpdateIcommand()
	{
		Icommand icommand = new Icommand()
		{

			@Override
			public void execute()
			{
				MaterialLoader.loading( true );
				// getting only bridges at the moment
				List<NetworkDeviceType> deviceTypes = new ArrayList<>();
				deviceTypes.add( NetworkDeviceType.BRIDGE );
				deviceTypes.add( NetworkDeviceType.ETHERNET );
				CommonServiceProvider.getCommonService().getNetworkingDevice( deviceTypes, new ApplicationCallBack<List<NetworkingModel>>()
				{

					@Override
					public void onSuccess( List<NetworkingModel> networkDeviceModels )
					{
						MaterialLoader.loading( false );
						dataSource = new ListDataSource<NetworkingModel>();
						dataSource.add( 0, networkDeviceModels );
						totalNetworks = networkDeviceModels.size();
						totalNetworkLabel.setText( "Total Items: " + "(" + totalNetworks + ")" );

						pager.setDataSource( dataSource );
						uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

						materialDataTable.getView().setRedraw( true );
						materialDataTable.getView().refresh();

					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, caught.getMessage() );
					}
				} );

			}
		};
		return icommand;

	}

	private MaterialLink getLink( String text )
	{
		MaterialLink link = new MaterialLink();
		link.setWaves( WavesType.DEFAULT );
		link.setTextColor( Color.BLACK );
		link.setText( text );
		return link;
	}
}
