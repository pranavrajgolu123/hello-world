package com.fmlb.forsaos.client.application.settings.accounts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.models.ActiveDirUsersModel;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;

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

public class ActiveDirUsers extends MaterialPanel{

	private UIComponentsUtil uiComponentsUtil;
	
	private MaterialRow materialRow = new MaterialRow();

	private List<ActiveDirUsersModel> activeDirUsersModel = new ArrayList<ActiveDirUsersModel>();

	private MaterialDataTable<ActiveDirUsersModel> materialDataTable;

	private ListDataSource<ActiveDirUsersModel> dataSource;

	private MaterialLabel lemSnapshotLabel;

	private MaterialButton createSnapshotBtn;
	
	private MaterialSearch search;

	private MaterialNavBar navBarSearch;
	
	private MaterialLink searchLink;
	
	private int count=0;

	public ActiveDirUsers(UIComponentsUtil uiComponentsUtil)
	{
		this.uiComponentsUtil = uiComponentsUtil;
		initializeDataTable();
		//this.placeManager=placeManager;
	}

	AppResources resources = GWT.create(AppResources.class);


	private void initializeDataTable()
	{
		createSearchComponent();
		
		createCreateSnapshotBtn();

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
		//search.setGrid("13");
		search.setShadow(1);
		navBarSearch.add(search);
		
		add(navBarSearch);
		
	        //  Close Handler
	        search.addCloseHandler(event -> {
	            navBarSearch.setVisible(false);
	            MaterialToast.fireToast("Close Event was fired");
	            count--;
	        });

    }

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<ActiveDirUsersModel>();
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
					lemSnapshotLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Active Directory Users" );
					materialDataTable.getScaffolding().getInfoPanel().add( lemSnapshotLabel );
					//materialDataTable.getScaffolding().getToolPanel().add( createSnapshotBtn );
					materialDataTable.getScaffolding().getToolPanel().add( searchLink );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
				}
			}
		} );

		materialDataTable.addColumn( new TextColumn<ActiveDirUsersModel>()
		{
			@Override
			public Comparator< ? super RowComponent<ActiveDirUsersModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().compareToIgnoreCase( o2.getData().getName() );
			}

			@Override
			public String getValue( ActiveDirUsersModel object )
			{
				return object.getName();
			}
		}, "User Group" );
		
		
		materialDataTable.addColumn( new WidgetColumn<ActiveDirUsersModel, MaterialButton>()
		{

			@Override
			public TextAlign textAlign()
			{
				return TextAlign.CENTER;
			}

			@Override
			public MaterialButton getValue( ActiveDirUsersModel object )
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
		
		materialDataTable.addColumn( new WidgetColumn<ActiveDirUsersModel, MaterialButton>()
		{

			@Override
			public TextAlign textAlign()
			{
				return TextAlign.CENTER;
			}

			@Override
			public MaterialButton getValue( ActiveDirUsersModel object )
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

	private void createCreateSnapshotBtn()
	{

		createSnapshotBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create Active Directory" );
		createSnapshotBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				//createSnapshotBtnAction();

			}
		} );
	}
	
	private void generateData()
	{
		dataSource = new ListDataSource<ActiveDirUsersModel>();
		dataSource.add( 0, activeDirUsersModel );
	}
}
