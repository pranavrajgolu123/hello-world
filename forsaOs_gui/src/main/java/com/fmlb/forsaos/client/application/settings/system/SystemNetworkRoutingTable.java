package com.fmlb.forsaos.client.application.settings.system;

import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.RoutingModel;
import com.fmlb.forsaos.client.application.settings.system.CreateRoutePopup;
import com.fmlb.forsaos.client.application.models.RoutingModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class SystemNetworkRoutingTable extends MaterialPanel
{


	private UIComponentsUtil uiComponentsUtil;
	
    private PlaceManager placeManager;
	
	private CurrentUser currentUser;
	 
    private MaterialButton createRouteBtn;
	
	private MaterialButton createSearchBtn;
	 
     private MaterialSearch routeSearch;
     
     private MaterialNavBar routeNavBarSearch;
     
     private int routeSearchCount = 0;
     
     private MaterialLabel isoLabel;
     

 	private MaterialDataPager<RoutingModel> pager;

 	private ListDataSource<RoutingModel> dataSource;

 	private MaterialDataTable<RoutingModel> RoutingTable;
 	
 	private MaterialDataTable<RoutingModel> RoutingTable1;
 	
 	private IcommandWithData navigationCmd;
     

	 
  
	public SystemNetworkRoutingTable(UIComponentsUtil uiComponentsUtil,PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		add(createRoutingTablePanel());
		createCreateRouteBtn();
		createCreateSearchBtn();
	}
	private void createRouteBtnAction()
	{

		CreateRoutePopup createRoutePopup = new CreateRoutePopup( this.uiComponentsUtil ,getUpdateIcommand());
		createRoutePopup.open();
	}
	
	
	private void createCreateRouteBtn()
	{
		createRouteBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create Route" );
		createRouteBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createRouteBtnAction();

			}
		} );
	}
	
	private void searchRoute()
	{
		
	    routeNavBarSearch=new MaterialNavBar();
		routeNavBarSearch.setVisible(true);
		routeNavBarSearch.setMarginTop(-1);
	    routeSearch =new MaterialSearch();
		routeSearch.setPlaceholder("Search Routing Address");
		routeSearch.setBackgroundColor(Color.BLUE_LIGHTEN_5);
		routeSearch.setIconColor(Color.BLACK);
		routeSearch.setActive(true);
		routeSearch.setShadow(1);
		routeNavBarSearch.add(routeSearch);
		
		add(routeNavBarSearch);
		
	        //  Close Handler
		routeSearch.addCloseHandler(event -> {
	           // navBar.setVisible(true);
	            routeNavBarSearch.setVisible(false);
	            MaterialToast.fireToast("Routing Search Closes");
	            routeSearchCount--;
	        });
		
		routeSearch.addSearchFinishHandler(event -> {
            
            MaterialToast.fireToast("Route Search Finished");
        });

	}
	
	private void createCreateSearchBtn()
	{
		createSearchBtn = new MaterialButton();
		createSearchBtn.setTitle( "Search Route" );
		createSearchBtn.setIconType( IconType.SEARCH );
		createSearchBtn.setIconColor(Color.BLUE_GREY_DARKEN_4);
		createSearchBtn.setBackgroundColor( Color.WHITE );
		
		createSearchBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
               if(routeSearchCount == 0)
                 {
				    searchRoute();
				    routeSearchCount++;
                 }
			}
		} );
		
		
	}
	
	private MaterialDataTable<RoutingModel> createRoutingTablePanel()
	{

		RoutingTable = new MaterialDataTable<RoutingModel>();
		RoutingTable.addStyleName( "datatablebottompanel" );
		RoutingTable.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					RoutingTable.setShadow( 1 );
					RoutingTable.setRowHeight( 10 );
					RoutingTable.setHeight( "calc(30vh - 20px)" );
					RoutingTable.getTableIcon().setVisible( false );
					RoutingTable.getScaffolding().getInfoPanel().clear();
					isoLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Routing Table" );
					RoutingTable.getScaffolding().getInfoPanel().add( isoLabel );
					RoutingTable.getScaffolding().getToolPanel().add( createRouteBtn );
					RoutingTable.getScaffolding().getToolPanel().add( createSearchBtn );
					RoutingTable.getStretchIcon().setVisible( false );
					RoutingTable.getColumnMenuIcon().setVisible( false );
				}

			}
		} );
		
		RoutingTable.addColumn(new TextColumn<RoutingModel>() {
            @Override
            public Comparator<? super RowComponent<RoutingModel>> sortComparator() {
                return (o1, o2) -> o1.getData().getDestination().compareToIgnoreCase(o2.getData().getDestination());
            }
            @Override
            public String getValue(RoutingModel object) {
                return object.getDestination();
            }
        }, "Destination");
		
		RoutingTable.addColumn(new TextColumn<RoutingModel>() {
            @Override
            public Comparator<? super RowComponent<RoutingModel>> sortComparator() {
                return (o1, o2) -> o1.getData().getSubnetmask().compareToIgnoreCase(o2.getData().getSubnetmask());
            }
            @Override
            public String getValue(RoutingModel object) {
                return object.getSubnetmask();
            }
        }, "Mask");

		RoutingTable.addColumn(new TextColumn<RoutingModel>() {
            @Override
            public Comparator<? super RowComponent<RoutingModel>> sortComparator() {
                return (o1, o2) -> o1.getData().getGateway().compareToIgnoreCase(o2.getData().getGateway());
            }
            @Override
            public String getValue(RoutingModel object) {
                return object.getGateway();
            }
        }, "Gateway");
		
		

		RoutingTable.addColumn( new WidgetColumn<RoutingModel, MaterialButton>()
		{
			/*
			 * @Override public TextAlign textAlign() { return TextAlign.CENTER; }
			 */

			@Override
			public MaterialButton getValue(RoutingModel object )
			{
				MaterialButton deleteBtn = new MaterialButton();
				deleteBtn.setTitle( "Delete" );
				deleteBtn.setIconType( IconType.DELETE );
				// deleteBtn.setText("badge " + object.getId());
				deleteBtn.setBackgroundColor( Color.BLUE );
				deleteBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						//deleteVMBtnAction( object );

					}
				} );
				// badge.setLayoutPosition(Style.Position.RELATIVE);
				return deleteBtn;
			}
		}, "Delete" );

      return RoutingTable;
	}
	
	public Icommand getUpdateIcommand()
	{
		Icommand icommand = new Icommand()
		{

			@Override
			public void execute()
			{
			MaterialLoader.loading( true );
				//RoutingModel createRoutingodel = new RoutingModel( destination.getValue(), subnetmask.getValue(), gateway.getValue());
			/*	CommonServiceProvider.getCommonService().getRouteList( null, new AsyncCallback<List<RoutingModel>>()
				{
					@Override
					public void onSuccess( List<RoutingModel> result )
					{
						MaterialLoader.loading( false );
						
					}

					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false );
						Window.alert( caught.getMessage() );
					}
				} );*/

			}
		};
		return icommand;
  
	}

}
