package com.fmlb.forsaos.client.application.management.networking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.InterfaceModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
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
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class InterfacesDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private List<InterfaceModel> interfaceModels = new ArrayList<InterfaceModel>();

	private MaterialDataTable<InterfaceModel> materialDataTable;

	private MaterialDataPager<InterfaceModel> pager;

	private ListDataSource<InterfaceModel> dataSource;

	private MaterialLabel interfaceLabel;

	private MaterialLabel totalInterfacesLabel;

	private int totalInterfaces;

	private MaterialButton createInterfaceBtn;

	private MaterialButton deleteMultipleInterfacesIcon;

	MaterialSearch search;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private HashMap<String, HashMap<String, VMModel>> vmLemMap = new HashMap<String, HashMap<String, VMModel>>();

	public InterfacesDataTable( int height, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigationCmd )
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
		tableInit();
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
		createInterfaceBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create Interface" );
		createInterfaceBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createInterfaceBtnAction();

			}
		} );
	}

	private void createDeleteMultiItemsIcon()
	{
		deleteMultipleInterfacesIcon = this.uiComponentsUtil.getDeleteMultipleDataItemIcon();
		deleteMultipleInterfacesIcon.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				deleteMultipleInterfacesBtnAction();
			}
		} );
	}

	private void createInterfaceBtnAction()
	{/*

		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getPhysicalDeviceList( null, new ApplicationCallBack<List<String>>()
		{

			@Override
			public void onSuccess( List<String> result )
			{
				CreateNetworkPopup createNetworkPopup = new CreateNetworkPopup( uiComponentsUtil, getUpdateIcommand(), result );
				createNetworkPopup.open();

			}

			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				List<String> interfaces = new ArrayList<>();
				interfaces.add( "enp1s0f0" );
				interfaces.add( "enp1s0f1" );
				interfaces.add( "enp1s0f2" );
				interfaces.add( "enp1s0f3" );
				CreateNetworkPopup createNetworkPopup = new CreateNetworkPopup( uiComponentsUtil, getUpdateIcommand(), interfaces );
				createNetworkPopup.open();

			};
		} );

	*/}

	private void deleteNetworkingBtnAction( InterfaceModel InterfaceModel )
	{
		/*
		DeleteLEMPopUp deleteLEMPopUp = new DeleteLEMPopUp( InterfaceModel, this.uiComponentsUtil, getUpdateIcommand(), currentUser );
		deleteLEMPopUp.open();
		*/}

	private void deleteMultipleInterfacesBtnAction()
	{
		List<InterfaceModel> selectedRowModels = materialDataTable.getSelectedRowModels( false );
		if ( selectedRowModels.size() > 0 )
		{
			/*
			DeleteLEMsPopUp deleteLEMsPopUp = new DeleteLEMsPopUp( selectedRowModels, this.uiComponentsUtil, getUpdateIcommand(), currentUser );
			deleteLEMsPopUp.open();
			*/}
		else
		{
			this.uiComponentsUtil.getMaterialDialog( "Destroy LEMs", "Please select atleast one LEM", "Close",null ).open();
		}

	}

	private void enableDisableDelIcon()
	{
		if ( materialDataTable.getSelectedRowModels( false ).size() > 0 )
		{
			deleteMultipleInterfacesIcon.setEnabled( true );
			LoggerUtil.log( "enable delete button" );
		}
		else
		{
			deleteMultipleInterfacesIcon.setEnabled( false );
			LoggerUtil.log( "disable delete button" );
		}
	}

	private void actionOnInterfaceNameClick( InterfaceModel InterfaceModel )
	{
		//DummyData.setInterfaceModel( InterfaceModel );
		// LoggerUtil.log( InterfaceModel.getDisplayName() );
		if ( navigationCmd != null )
		{
			navigationCmd.executeWithData( InterfaceModel );
		}

	}

	private void createPagination()
	{
		pager = new MaterialDataPager<InterfaceModel>( materialDataTable, dataSource );
		this.uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<InterfaceModel>();
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
					materialDataTable.setHeight( "calc(15vh - 50px)" );
					// materialDataTable.setTitle("LEMS");
					// materialDataTable.addStyleName("materialDataTableTabWorkStep");
					// materialDataTable.setMarginTop(64.00);
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					interfaceLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Interfaces" );
					totalInterfacesLabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalInterfaces + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( interfaceLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalInterfacesLabel );
					materialDataTable.getScaffolding().getToolPanel().add( createInterfaceBtn );
					materialDataTable.getScaffolding().getToolPanel().add( deleteMultipleInterfacesIcon );
					// materialDataTable.getScaffolding().getToolPanel().add(search);
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					// materialDataTable.getScaffolding().getTopPanel().add(search);
				}

			}
		} );

		materialDataTable.addColumn( new WidgetColumn<InterfaceModel, MaterialLabel>()
		{
			@Override
			public Comparator< ? super RowComponent<InterfaceModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}
			/*
			 * @Override public TextAlign textAlign() { return TextAlign.CENTER; }
			 */

			@Override
			public MaterialLabel getValue( InterfaceModel interfaceModel )
			{
				MaterialLabel networkNameLabel = new MaterialLabel();
				networkNameLabel.setText( interfaceModel.getName() );
				networkNameLabel.setTitle( interfaceModel.getName() );
				networkNameLabel.setHoverable( true );
				networkNameLabel.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						actionOnInterfaceNameClick( interfaceModel );
					}
				} );
				return networkNameLabel;
			}

		}, "Name" );

		materialDataTable.addColumn( new TextColumn<InterfaceModel>()
		{
			@Override
			public Comparator< ? super RowComponent<InterfaceModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getType().compareTo( o2.getData().getType() );
			}

			@Override
			public String getValue( InterfaceModel object )
			{
				return object.getType();
			}
		}, "Type" );

		materialDataTable.addColumn( new WidgetColumn<InterfaceModel, MaterialButton>()
		{
			/*
			 * @Override public TextAlign textAlign() { return TextAlign.CENTER; }
			 */

			@Override
			public MaterialButton getValue( InterfaceModel object )
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
						deleteNetworkingBtnAction( object );

					}
				} );
				// badge.setLayoutPosition(Style.Position.RELATIVE);
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addRowSelectHandler( new RowSelectHandler<InterfaceModel>()
		{

			@Override
			public void onRowSelect( RowSelectEvent<InterfaceModel> event )
			{
				enableDisableDelIcon();
			}

		} );
		materialDataTable.addSelectAllHandler( new SelectAllHandler<InterfaceModel>()
		{

			@Override
			public void onSelectAll( SelectAllEvent<InterfaceModel> event )
			{
				enableDisableDelIcon();
			}
		} );

		// materialDataTable.setVisibleRange(0, 2001);
		materialDataTable.addStyleName( "datatablebottompanel" );
		// materialDataTable.setRowData(0, InterfaceModels);
		materialDataTable.setSelectionType( SelectionType.MULTIPLE );
	}

	private void tableInit()
	{
		dataSource = new ListDataSource<InterfaceModel>();
		List<InterfaceModel> result = new ArrayList<InterfaceModel>();
		dataSource.add( 0, result );
		totalInterfaces = result.size();

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
				CommonServiceProvider.getCommonService().getPhysicalDeviceList( null, new ApplicationCallBack<List<InterfaceModel>>()
				{

					@Override
					public void onSuccess( List<InterfaceModel> models )
					{
						interfaceModels = models;
						MaterialLoader.loading( false );
						dataSource = new ListDataSource<InterfaceModel>();
						dataSource.add( 0, models );
						totalInterfaces = models.size();
						totalInterfacesLabel.setText( "Total Items: " + "(" + totalInterfaces + ")" );

						pager.setDataSource( dataSource );
						uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

						materialDataTable.getView().setRedraw( true );
						materialDataTable.getView().refresh();

					}

					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, caught.getMessage() );

					};
				} );

			}
		};
		return icommand;

	}

}
