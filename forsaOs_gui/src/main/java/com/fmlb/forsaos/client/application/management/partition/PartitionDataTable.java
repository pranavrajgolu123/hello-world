package com.fmlb.forsaos.client.application.management.partition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.PartitionModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.shared.application.utility.PartitionType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.ListDataSource;
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
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class PartitionDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private List<PartitionModel> partitionModels = new ArrayList<PartitionModel>();

	private MaterialDataTable<PartitionModel> materialDataTable;

	private MaterialDataPager<PartitionModel> pager;

	private ListDataSource<PartitionModel> dataSource;

	private MaterialLabel partitionLabel;

	private MaterialLabel totalPatitionlabel;

	private int totalPartitions;

	private MaterialButton createPartitionBtn;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	AppResources resources = GWT.create( AppResources.class );

	public PartitionDataTable( int height, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.placeManager = placeManager;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createCreateRepoBtn();
		generateData();
	}

	private void createCreateRepoBtn()
	{
		createPartitionBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Add Partition" );
		createPartitionBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				int allocatedPercentile = 0;
				for ( PartitionModel partitionModel : partitionModels )
				{
					allocatedPercentile = allocatedPercentile + ( partitionModel.getStop() - partitionModel.getStart() );
				}
				if ( allocatedPercentile == 100 )
				{
					List<String> errorMessages = new ArrayList<String>();
					errorMessages.add( "No space available to create new partition." );

					ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
					errorPopup.open();
				}
				else
				{
					CreatePartitionPopup createPartitionPopup = new CreatePartitionPopup( uiComponentsUtil, getUpdateIcommandWithData(), partitionModels );
					createPartitionPopup.open();
				}
			}
		} );
	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<PartitionModel>();
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
					materialDataTable.setHeight( "calc(72vh - 50px)" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					partitionLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Partitions" );
					totalPatitionlabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalPartitions + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( partitionLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalPatitionlabel );
					materialDataTable.getScaffolding().getToolPanel().add( createPartitionBtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					materialDataTable.addStyleName( "datatablebottompanel" );
				}

			}
		} );

		materialDataTable.addColumn( new TextColumn<PartitionModel>()
		{
			@Override
			public Comparator< ? super RowComponent<PartitionModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().compareToIgnoreCase( o2.getData().getName() );
			}

			@Override
			public String getValue( PartitionModel partitionModel )
			{
				return partitionModel.getName();
			}
		}, "Name" );

		materialDataTable.addColumn( new TextColumn<PartitionModel>()
		{
			@Override
			public Comparator< ? super RowComponent<PartitionModel>> sortComparator()
			{
				return ( o1, o2 ) -> ( o1.getData().getStop() - o1.getData().getStart() ) - ( o2.getData().getStop() - o2.getData().getStart() );
			}

			@Override
			public String getValue( PartitionModel partitionModel )
			{
				Integer allocation = partitionModel.getStop() - partitionModel.getStart();
				return allocation.toString() + "(" + partitionModel.getStart() + " - " + partitionModel.getStop() + ")";
			}
		}, "Allocated(%)" );

		materialDataTable.addColumn( new TextColumn<PartitionModel>()
		{
			@Override
			public Comparator< ? super RowComponent<PartitionModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getMount_point().compareToIgnoreCase( o2.getData().getMount_point() );
			}

			@Override
			public String getValue( PartitionModel partitionModel )
			{
				return partitionModel.getMount_point();
			}
		}, "Mount Point" );

		materialDataTable.addColumn( new TextColumn<PartitionModel>()
		{
			@Override
			public Comparator< ? super RowComponent<PartitionModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getType() - o2.getData().getType();
			}

			@Override
			public String getValue( PartitionModel partitionModel )
			{
				if ( partitionModel.getType() == 0 )
				{
					return PartitionType.PERSISTENT.toString();
				}
				else if ( partitionModel.getType() == 1 )
				{
					return PartitionType.BLINK.toString();
				}
				else
				{
					return PartitionType.SNAPSHOT.toString();
				}
			}
		}, "Type" );

		materialDataTable.addColumn( new WidgetColumn<PartitionModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( PartitionModel partitionModel )
			{
				MaterialButton expandBtn = new MaterialButton();
				expandBtn.setTitle( "Expand" );
				expandBtn.setText( "Expand" );
				//expandBtn.setBackgroundColor( Color.BLUE );
				expandBtn.addStyleName( resources.style().sendAlertBtn() );
				expandBtn.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						actionOnPartitionNameClick( partitionModel );
					}
				} );
				return expandBtn;
			}
		}, "Expand" );

		materialDataTable.addColumn( new WidgetColumn<PartitionModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( PartitionModel partitionModel )
			{
				MaterialButton deleteBtn = new MaterialButton();
				deleteBtn.setTitle( "Delete" );
				deleteBtn.setIconType( IconType.DELETE );
				deleteBtn.setBackgroundColor( Color.BLUE );
				if ( partitionModel.getType() == PartitionType.PERSISTENT.getType() )
				{
					deleteBtn.setEnabled( false );
				}
				else
				{
					deleteBtn.addClickHandler( new ClickHandler()
					{
						@Override
						public void onClick( ClickEvent event )
						{
							event.stopPropagation();
							deletePartition( partitionModel );
						}
					} );
				}
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addRowSelectHandler( new RowSelectHandler<PartitionModel>()
		{
			@Override
			public void onRowSelect( RowSelectEvent<PartitionModel> event )
			{
				//				enableDisableDelIcon();
			}
		} );
		materialDataTable.addSelectAllHandler( new SelectAllHandler<PartitionModel>()
		{
			@Override
			public void onSelectAll( SelectAllEvent<PartitionModel> event )
			{
				//				enableDisableDelIcon();
			}
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setUseStickyHeader( true );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void generateData()
	{
		LoggerUtil.log( "generate data REPO" );
		dataSource = new ListDataSource<PartitionModel>();
		List<PartitionModel> result = new ArrayList<PartitionModel>();
		dataSource.add( 0, result );
		totalPartitions = 0;
		createTable();
		pager = new MaterialDataPager<PartitionModel>( materialDataTable, dataSource );
		materialDataTable.add( pager );
		materialRow.add( materialDataTable );
		materialDataTable.getView().setRedraw( true );
		materialDataTable.getView().refresh();
		add( materialRow );

	}

	public IcommandWithData getUpdateIcommandWithData()
	{
		IcommandWithData icommand = new IcommandWithData()
		{
			@Override
			public void executeWithData( Object data )
			{
				MaterialLoader.loading( true );
				LoggerUtil.log( "fromhere" );
				updateREPOGrid( ( boolean ) data );
			}
		};
		return icommand;
	}

	private void updateREPOGrid( Boolean addDelayFl )
	{
		CommonServiceProvider.getCommonService().listPartition( addDelayFl, new ApplicationCallBack<List<PartitionModel>>()
		{
			@Override
			public void onSuccess( List<PartitionModel> result )
			{
				MaterialLoader.loading( false );
				LoggerUtil.log( "fromhere - closed" );
				partitionModels = result;
				materialDataTable.setRowData( 0, partitionModels );
				totalPartitions = partitionModels.size();
				dataSource = new ListDataSource<PartitionModel>();
				dataSource.add( 0, result );
				pager.setDataSource( dataSource );
				materialDataTable.getView().setRedraw( true );
				materialDataTable.getView().refresh();
				totalPatitionlabel.setText( "Total Items: " + "(" + totalPartitions + ")" );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				LoggerUtil.log( "fromhere - closed" );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void deletePartition( PartitionModel partitionModel )
	{
		DeletePartitionPopup deletePartitionPopup = new DeletePartitionPopup( uiComponentsUtil, getUpdateIcommandWithData(), partitionModel, currentUser );
		deletePartitionPopup.open();
	}

	private void actionOnPartitionNameClick( PartitionModel partitionModel )
	{
		ExpandPartitionPopup expandPartitionPopup = new ExpandPartitionPopup( uiComponentsUtil, getUpdateIcommandWithData(), partitionModels, partitionModel );
		expandPartitionPopup.open();
	}
}
