package com.fmlb.forsaos.client.application.settings.accounts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.PermissionModel;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class PermissionsTable extends MaterialPanel{

	private UIComponentsUtil uiComponentsUtil;
	
	private MaterialRow materialRow = new MaterialRow();

	private List<PermissionModel> permissionModelTable= new ArrayList<PermissionModel>();

	private MaterialDataTable<PermissionModel> materialDataTable;

	private ListDataSource<PermissionModel> dataSource;

	private MaterialLabel permissionLabel;

	private MaterialButton createpermissionBtn;
	
	private MaterialSearch search;

	private MaterialNavBar navBarSearch;
	
	private MaterialLink searchLink;
	
	private int count = 0;
	
	private PlaceManager placeManager;

	private CurrentUser currentUser;
	
	private IcommandWithData navigationCmd;

	public PermissionsTable(UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd)
	{
	this.uiComponentsUtil = uiComponentsUtil;
	this.placeManager = placeManager;
	this.currentUser = currentUser;
	this.navigationCmd = navigationCmd;
		initializeDataTable();
		//this.placeManager=placeManager;
	}

	AppResources resources = GWT.create(AppResources.class);


	

	private void initializeDataTable()
	{
		createSearchComponent();
		
		createCreatePermissionBtn();

		generateData();
		createTable();
		//createPagination();
		materialRow.add( materialDataTable );
		add( materialRow );

	}
	private void createSearchComponent()
	{
		searchLink=new MaterialLink();
		searchLink.setIconType(IconType.SEARCH);
		searchLink.setIconColor(Color.BLACK);
		searchLink.setFloat(Float.RIGHT);
		searchLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(count == 0)
				{

				searchdata();
                 count++;
			    }
			}
		});
	}
	
    private void searchdata() {
    	
    	navBarSearch=new MaterialNavBar();
		//navBarSearch.setWidth("50");
		navBarSearch.setVisible(true);
		navBarSearch.setMarginTop(-100);
		search=new MaterialSearch();
		search.setPlaceholder("component");
		search.setBackgroundColor(Color.WHITE);
		search.setIconColor(Color.BLACK);
		search.setActive(true);
		//search.setGrid("50");
		search.setShadow(1);
		navBarSearch.add(search);
		
		add(navBarSearch);
		
	        //  Close Handler
	        search.addCloseHandler(event -> {
	           // navBar.setVisible(true);
	            navBarSearch.setVisible(false);
	            MaterialToast.fireToast("Close Event was fired");
	            count--;
	        });

    }
    
	private void createTable()
	{
		materialDataTable = new MaterialDataTable<PermissionModel>();
		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.addAttachHandler( new Handler()
		{
			

			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					materialDataTable.setHeight( "calc(20vh - 50px)" );
					// materialDataTable.setTitle("Snapshot");
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					permissionLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Permisions" );
					materialDataTable.getScaffolding().getInfoPanel().add( permissionLabel );
					materialDataTable.getScaffolding().getToolPanel().add( createpermissionBtn );
					materialDataTable.getScaffolding().getToolPanel().add( searchLink );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
				}
			}
		} );

		materialDataTable.addColumn( new TextColumn<PermissionModel>()
		{
			@Override
			public Comparator< ? super RowComponent<PermissionModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().compareToIgnoreCase( o2.getData().getName() );
			}

			@Override
			public String getValue( PermissionModel object )
			{
				return object.getName();
			}
		}, "User Group" );
		
		
		materialDataTable.addColumn( new WidgetColumn<PermissionModel, MaterialButton>()
		{

			@Override
			public TextAlign textAlign()
			{
				return TextAlign.CENTER;
			}

			@Override
			public MaterialButton getValue( PermissionModel object )
			{
				MaterialButton editBtn = uiComponentsUtil.getGridActionButton( "Edit", IconType.CHANGE_HISTORY );
				editBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
					}
				} );
				return editBtn;
			}
		}, "Edit" );
		
		materialDataTable.addColumn( new WidgetColumn<PermissionModel, MaterialButton>()
		{

			@Override
			public TextAlign textAlign()
			{
				return TextAlign.CENTER;
			}

			@Override
			public MaterialButton getValue( PermissionModel object )
			{
				MaterialButton deleteBtn = uiComponentsUtil.getGridActionButton( "Delete", IconType.CHANGE_HISTORY );
				deleteBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
					}
				} );
				return deleteBtn;
			}
		}, "Delete" );

	}

	private void createCreatePermissionBtn()
	{

		createpermissionBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create Permisions" );
		createpermissionBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				CreateGrpPermissionPop createPermission=new CreateGrpPermissionPop(uiComponentsUtil);
				createPermission.open();
				EditGrpPermission editPermission=new EditGrpPermission(uiComponentsUtil);
				editPermission.open();

			}
		} );
	}
	
	private void generateData()
	{
		dataSource = new ListDataSource<PermissionModel>();
		dataSource.add( 0, permissionModelTable );
	}
}
