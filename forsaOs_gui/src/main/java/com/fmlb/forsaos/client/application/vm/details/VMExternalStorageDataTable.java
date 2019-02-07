package com.fmlb.forsaos.client.application.vm.details;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.StorageModel;
import com.fmlb.forsaos.client.application.models.StorageModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

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
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class VMExternalStorageDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<StorageModel> materialDataTable;

	private MaterialDataPager<StorageModel> pager;

	private ListDataSource<StorageModel> dataSource;

	private MaterialLabel externalStorageLabel;

	private MaterialButton addExternalStorageBtn;

	private MaterialButton deleteMultipleLEMsIcon;

	MaterialSearch search;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private VMModel vmModel;

	private List<StorageModel> externalStorageModels = new ArrayList<StorageModel>();

	public VMExternalStorageDataTable( VMModel vmModel, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
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
		createAddExternalStorageBtn();
		createDeleteMultiItemsIcon();
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

	private void createAddExternalStorageBtn()
	{
		addExternalStorageBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "VM Add External Storage" );
		addExternalStorageBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				addISOBtnAction();

			}
		} );
	}

	private void createDeleteMultiItemsIcon()
	{
		deleteMultipleLEMsIcon = this.uiComponentsUtil.getDeleteMultipleDataItemIcon();
		deleteMultipleLEMsIcon.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				deleteMultipleLEMSBtnAction();
			}
		} );
	}

	private void addISOBtnAction()
	{
		VMAddExtStrgPopup vmAddExtStrgPopup=new VMAddExtStrgPopup( uiComponentsUtil, getUpdateIcommand(), externalStorageModels, vmModel );
		vmAddExtStrgPopup.open();
		
		/*
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getVMsList( null, new AsyncCallback<List<VMModel>>()
		{
			@Override
			public void onSuccess( List<VMModel> result )
			{
				MaterialLoader.loading( false );
				CreateLEMPopUp createLEMPopUp1 = new CreateLEMPopUp( "24.98 GB", uiComponentsUtil, getUpdateIcommand(), result );
				createLEMPopUp1.open();
		
			}
		
			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				Window.alert( caught.getMessage() );
			}
		} );
		
		*/}

	private void deleteNICBtnAction( StorageModel StorageModel )
	{
		/*
		DeleteLEMPopUp deleteLEMPopUp = new DeleteLEMPopUp( StorageModel, this.uiComponentsUtil, getUpdateIcommand(), currentUser );
		deleteLEMPopUp.open();
		*/}

	private void deleteMultipleLEMSBtnAction()
	{
		/*
		List<StorageModel> selectedRowModels = materialDataTable.getSelectedRowModels( false );
		if ( selectedRowModels.size() > 0 )
		{
			DeleteLEMsPopUp deleteLEMsPopUp = new DeleteLEMsPopUp( selectedRowModels, this.uiComponentsUtil, getUpdateIcommand(), currentUser );
			deleteLEMsPopUp.open();
		}
		else
		{
			this.uiComponentsUtil.getMaterialDialog( "Destroy LEMs", "Please select atleast one LEM", "Close" ).open();
		}
		
		*/}

	private void enableDisableDelIcon()
	{
		if ( materialDataTable.getSelectedRowModels( false ).size() > 0 )
		{
			deleteMultipleLEMsIcon.setEnabled( true );
			LoggerUtil.log( "enable delete button" );
		}
		else
		{
			deleteMultipleLEMsIcon.setEnabled( false );
			LoggerUtil.log( "disable delete button" );
		}
	}

	private void actionOnNICSlotClick( StorageModel StorageModel )
	{
		/*
		//DummyData.setLEMModel( StorageModel );
		LoggerUtil.log( StorageModel.getSlot() );
		if ( navigationCmd != null )
		{
			navigationCmd.executeWithData( StorageModel );
		}
		
		*/}

	private void createPagination()
	{
		pager = new MaterialDataPager<StorageModel>( materialDataTable, dataSource );
		this.uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<StorageModel>();
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
					materialDataTable.setHeight( "calc(25vh - 50px)" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					externalStorageLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "External storage" );
					materialDataTable.getScaffolding().getInfoPanel().add( externalStorageLabel );
					materialDataTable.getScaffolding().getToolPanel().add( addExternalStorageBtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getColumnMenuIcon().setVisible( false );
					materialDataTable.setRowData( 0, externalStorageModels );
				}

			}
		} );

		materialDataTable.addColumn( new WidgetColumn<StorageModel, MaterialLabel>()
		{

			@Override
			public String width()
			{
				// TODO Auto-generated method stub
				return "90%";
			}

			@Override
			public Comparator< ? super RowComponent<StorageModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}
			/*
			 * @Override public TextAlign textAlign() { return TextAlign.CENTER; }
			 */

			@Override
			public MaterialLabel getValue( StorageModel StorageModel )
			{
				MaterialLabel extStorageLabel = new MaterialLabel();
				extStorageLabel.setText( StorageModel.getName() );
				extStorageLabel.setTitle( StorageModel.getName() );
				extStorageLabel.setHoverable( true );
				extStorageLabel.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						LoggerUtil.log( "lem name clicked handler" );
						event.stopPropagation();
						//actionOnLEMNameClick( StorageModel );
					}
				} );
				return extStorageLabel;
			}

		}, "Name" );

		materialDataTable.addColumn( new WidgetColumn<StorageModel, MaterialButton>()
		{
			/*
			 * @Override public TextAlign textAlign() { return TextAlign.CENTER; }
			 */

			@Override
			public MaterialButton getValue( StorageModel object )
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
						deleteExternalStorageBtnAction( object );

					}
				} );
				// badge.setLayoutPosition(Style.Position.RELATIVE);
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addRowSelectHandler( new RowSelectHandler<StorageModel>()
		{

			@Override
			public void onRowSelect( RowSelectEvent<StorageModel> event )
			{
				enableDisableDelIcon();
			}

		} );
		materialDataTable.addSelectAllHandler( new SelectAllHandler<StorageModel>()
		{

			@Override
			public void onSelectAll( SelectAllEvent<StorageModel> event )
			{
				enableDisableDelIcon();
			}
		} );

		// materialDataTable.setVisibleRange(0, 2001);
		materialDataTable.addStyleName( "datatablebottompanel" );
		// materialDataTable.setRowData(0, lemModels);
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void generateData()
	{
		
	}

	private void deleteExternalStorageBtnAction( StorageModel StorageModel )
	{
		/*
		DeleteLEMPopUp deleteLEMPopUp = new DeleteLEMPopUp( StorageModel, this.uiComponentsUtil, getUpdateIcommand(), currentUser );
		deleteLEMPopUp.open();
		*/}

	public Icommand getUpdateIcommand()
	{
		Icommand icommand = new Icommand()
		{

			@Override
			public void execute()
			{
				/*
				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().getLems( null, new AsyncCallback<List<StorageModel>>()
				{
					@Override
					public void onSuccess( List<StorageModel> result )
					{
						MaterialLoader.loading( true );
						isoMountPathModels.clear();
						isoMountPathModels.addAll( result );
						materialDataTable.setRowData( 0, isoMountPathModels );
						materialDataTable.getView().setRedraw( true );
						materialDataTable.getView().refresh();
						MaterialLoader.loading( false );
					}
				
					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false );
						Window.alert( caught.getMessage() );
					}
				} );
				
				*/}
		};
		return icommand;

	}
}
