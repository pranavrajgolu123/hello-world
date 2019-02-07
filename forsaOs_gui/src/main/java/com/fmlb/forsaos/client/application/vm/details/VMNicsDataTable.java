package com.fmlb.forsaos.client.application.vm.details;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.BridgeJSONModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.NicModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONObject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.events.RowSelectEvent;
import gwt.material.design.client.data.events.RowSelectHandler;
import gwt.material.design.client.data.events.SelectAllEvent;
import gwt.material.design.client.data.events.SelectAllHandler;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class VMNicsDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private List<NicModel> nicModels = new ArrayList<NicModel>();

	private MaterialDataTable<NicModel> materialDataTable;

	private MaterialLabel nicsLabel;

	private MaterialButton createNICBtn;

	MaterialSearch search;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private VMModel vmModel;

	private List<BridgeJSONModel> bridgeJSONModels;
	
	AppResources resources = GWT.create( AppResources.class );

	public VMNicsDataTable( VMModel vmModel, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.vmModel = vmModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.placeManager = placeManager;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createcreateNICBtn();
		generateData();
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

	private void createcreateNICBtn()
	{
		createNICBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Add NIC" );
		createNICBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createNICBtnAction();

			}
		} );
	}

	private void createNICBtnAction()
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getAllBridges( new ApplicationCallBack<List<BridgeJSONModel>>()
		{
			@Override
			public void onSuccess( List<BridgeJSONModel> result )
			{
				MaterialLoader.loading( false );
				bridgeJSONModels = result;
				if ( result == null || result.isEmpty() )
				{
					List<String> errorMessages = new ArrayList<String>();
					errorMessages.add( "Bridges are not configured." );
					ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
					errorPopup.open();
				}
				else
				{
					AddNICPopup addNICPopup = new AddNICPopup( uiComponentsUtil, getUpdateIcommand(), bridgeJSONModels, vmModel );
					addNICPopup.open();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void deleteNICBtnAction( NicModel nicModel )
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().detachNetFromVm( vmModel, nicModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				if ( result )
				{
					updateNicsDataTable();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<NicModel>();
		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					materialDataTable.setShadow( 1 );
					materialDataTable.setHeight( "100%" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					nicsLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "NICS" );
					materialDataTable.getScaffolding().getInfoPanel().add( nicsLabel );
					materialDataTable.getScaffolding().getToolPanel().add( createNICBtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getColumnMenuIcon().setVisible( false );
					materialDataTable.addStyleName( resources.style().vmDetailsGridLastTd() );
					updateNicsDataTable();
				}
			}
		} );
		
		materialDataTable.addColumn( new TextColumn<NicModel>()
		{
			@Override
			public String width()
			{
				return "35%";
			}
			@Override
			public Comparator< ? super RowComponent<NicModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().compareToIgnoreCase( o2.getData().getName() );
			}

			@Override
			public String getValue( NicModel object )
			{
				return object.getName();
			}
		}, "Name" );

		materialDataTable.addColumn( new TextColumn<NicModel>()
		{
			@Override
			public String width()
			{
				return "30%";
			}
			@Override
			public Comparator< ? super RowComponent<NicModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().get_interface().toString().compareToIgnoreCase( o2.getData().get_interface().toString() );
			}

			@Override
			public String getValue( NicModel object )
			{
				return object.get_interface().toString();
			}
		}, "Bridge" );

		materialDataTable.addColumn( new TextColumn<NicModel>()
		{
			@Override
			public String width()
			{
				return "30%";
			}
			@Override
			public Comparator< ? super RowComponent<NicModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getType().toString().compareToIgnoreCase( o2.getData().getType().toString() );
			}

			@Override
			public String getValue( NicModel object )
			{
				return object.getType().toString();
			}
		}, "Type" );

		materialDataTable.addColumn( new WidgetColumn<NicModel, MaterialButton>()
		{
			@Override
			public String width()
			{
				return "5%";
			}
			@Override
			public MaterialButton getValue( NicModel object )
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
						if ( vmModel.getStatus() == 2 || vmModel.getStatus() == 3 )
						{
							MaterialToast.fireToast( "VM must be powered off to remove NIC.", "rounded" );
						}
						else
						{
							deleteNICBtnAction( object );
						}

					}
				} );
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void generateData()
	{
		nicModels.clear();
		createTable();
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
				updateNicsDataTable();
			}
		};
		return icommand;
	}

	private void updateNicsDataTable()
	{
		nicModels.clear();
		materialDataTable.setRowData( 0, nicModels );
		materialDataTable.getView().setRedraw( true );
		materialDataTable.getView().refresh();
		JSONObject queryObj = new JSONObject();
		queryObj.put( "name", RestCallUtil.getJSONString( vmModel.getName() ) );
		CommonServiceProvider.getCommonService().getVMsList( RestCallUtil.getQueryRequest( queryObj.toString() ), false, new ApplicationCallBack<List<VMModel>>()
		{
			@Override
			public void onSuccess( List<VMModel> result )
			{
				if ( !result.isEmpty() )
				{
					vmModel = result.get( 0 );
					for ( String netId : vmModel.getNetworks() )
					{
						CommonServiceProvider.getCommonService().getLvnet( netId, new ApplicationCallBack<NicModel>()
						{
							@Override
							public void onSuccess( NicModel result )
							{
								if ( result != null )
								{
									nicModels.add( result );
									materialDataTable.setRowData( 0, nicModels );
									materialDataTable.getView().setRedraw( true );
									materialDataTable.getView().refresh();
								}
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

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}
}
