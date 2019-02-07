package com.fmlb.forsaos.client.application.lem.details;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.LEMSnapshotDeleteModel;
import com.fmlb.forsaos.client.application.models.LEMSnapshotModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONObject;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
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
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class LEMSnapshotDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<LEMSnapshotModel> materialDataTable;

	private MaterialDataPager<LEMSnapshotModel> pager;

	private ListDataSource<LEMSnapshotModel> dataSource;

	private MaterialLabel lemSnapshotLabel;

	private MaterialLabel totalLEMSanpshotslabel;

	private int totalSnapshots;

	private MaterialButton createSnapshotBtn;

	private MaterialButton deleteMultipleSnapshotsIcon;

	private LEMModel lemModel;

	private CurrentUser currentUser;

	private List<LEMSnapshotModel> lemSnapshotModels = new ArrayList<LEMSnapshotModel>();

	private ConfirmPasswordPopup delteSnapshotConfirmPasswordPopup;

	AppResources resources = GWT.create( AppResources.class );

	public LEMSnapshotDataTable( LEMModel lemModel, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.lemModel = lemModel;
		this.currentUser = currentUser;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createCreateSnapshotBtn();
		createDeleteMultiItemsIcon();
		generateData();
	}

	private void createPagination()
	{
		pager = new MaterialDataPager<LEMSnapshotModel>( materialDataTable, dataSource );
		this.uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<LEMSnapshotModel>();
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
					lemSnapshotLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "LEM Snapshots" );
					materialDataTable.getScaffolding().getInfoPanel().add( lemSnapshotLabel );
					materialDataTable.getScaffolding().getToolPanel().add( createSnapshotBtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getColumnMenuIcon().setVisible( false );
					materialDataTable.setRowData( 0, lemSnapshotModels );
				}
			}
		} );

		materialDataTable.addColumn( new TextColumn<LEMSnapshotModel>()
		{
			@Override
			public Comparator< ? super RowComponent<LEMSnapshotModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getSnapshot_name().compareToIgnoreCase( o2.getData().getSnapshot_name() );
			}

			@Override
			public String getValue( LEMSnapshotModel object )
			{
				return object.getSnapshot_name();
			}
		}, "Name" );

		materialDataTable.addColumn( new TextColumn<LEMSnapshotModel>()
		{
			@Override
			public Comparator< ? super RowComponent<LEMSnapshotModel>> sortComparator()
			{
				return ( o1, o2 ) -> String.valueOf( o1.getData().getTimestamp() ).compareTo( String.valueOf( o2.getData().getTimestamp() ) );

			}

			@Override
			public String getValue( LEMSnapshotModel object )
			{
				return object.getDate();

			}
		}, "Date Created" );

		materialDataTable.addColumn( new WidgetColumn<LEMSnapshotModel, MaterialButton>()
		{

			@Override
			public TextAlign textAlign()
			{
				return TextAlign.CENTER;
			}

			@Override
			public MaterialButton getValue( LEMSnapshotModel object )
			{
				MaterialButton restoreBtn = uiComponentsUtil.getGridActionButton( "Restore", IconType.RESTORE );
				restoreBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						restoreSnapshotBtnAction( object );
					}
				} );
				return restoreBtn;
			}
		}, "Restore" );

		materialDataTable.addColumn( new WidgetColumn<LEMSnapshotModel, MaterialButton>()
		{
			@Override
			public TextAlign textAlign()
			{
				return TextAlign.CENTER;
			}

			@Override
			public MaterialButton getValue( LEMSnapshotModel object )
			{
				MaterialButton promoteBtn = uiComponentsUtil.getGridActionButton( "Promote", IconType.ARROW_UPWARD );
				promoteBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						promoteSnapshotBtnAction( object );

					}
				} );
				return promoteBtn;
			}
		}, "Promote" );

		materialDataTable.addColumn( new WidgetColumn<LEMSnapshotModel, MaterialButton>()
		{
			@Override
			public TextAlign textAlign()
			{
				return TextAlign.CENTER;
			}

			@Override
			public MaterialButton getValue( LEMSnapshotModel object )
			{
				MaterialButton editBtn = uiComponentsUtil.getGridActionButton( "Edit", IconType.EDIT );
				editBtn.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						editSnapshotBtnAction( object );

					}
				} );
				return editBtn;
			}
		}, "Edit" );

		materialDataTable.addColumn( new WidgetColumn<LEMSnapshotModel, MaterialButton>()
		{
			@Override
			public TextAlign textAlign()
			{
				return TextAlign.CENTER;
			}

			@Override
			public MaterialButton getValue( LEMSnapshotModel object )
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
						deleteSnapshotBtnAction( object );

					}
				} );
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addRowSelectHandler( new RowSelectHandler<LEMSnapshotModel>()
		{

			@Override
			public void onRowSelect( RowSelectEvent<LEMSnapshotModel> event )
			{
				enableDisableDelIcon();
			}

		} );
		materialDataTable.addSelectAllHandler( new SelectAllHandler<LEMSnapshotModel>()
		{

			@Override
			public void onSelectAll( SelectAllEvent<LEMSnapshotModel> event )
			{
				enableDisableDelIcon();
			}
		} );

		// materialDataTable.setVisibleRange(0, 2001);
		materialDataTable.addStyleName( "datatablebottompanel" );
		// materialDataTable.setRowData(0, lemSnapshotModels);
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void createCreateSnapshotBtn()
	{

		createSnapshotBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create Snapshot" );
		createSnapshotBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				createSnapshotBtnAction();

			}
		} );
	}

	private void createDeleteMultiItemsIcon()
	{
		deleteMultipleSnapshotsIcon = this.uiComponentsUtil.getDeleteMultipleDataItemIcon();
		deleteMultipleSnapshotsIcon.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				deleteMultipleSnapshotsBtnAction();
			}
		} );
	}

	private void enableDisableDelIcon()
	{
		if ( materialDataTable.getSelectedRowModels( false ).size() > 0 )
		{
			deleteMultipleSnapshotsIcon.setEnabled( true );
			LoggerUtil.log( "enable delete button" );
		}
		else
		{
			deleteMultipleSnapshotsIcon.setEnabled( false );
			LoggerUtil.log( "disable delete button" );
		}
	}

	private void createSnapshotBtnAction()
	{
		JSONObject queryObj = new JSONObject();
		queryObj.put( "type", RestCallUtil.getJSONNumber( 2d ) );
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getManagementPartitions( RestCallUtil.getQueryRequest( queryObj.toString() ), new ApplicationCallBack<List<String>>()
		{

			@Override
			public void onSuccess( List<String> result )
			{
				MaterialLoader.loading( false );
				CreateSnapShotPopUp createSnapShotPopUp = new CreateSnapShotPopUp( lemModel, uiComponentsUtil, result, getUpdateIcommandWithData() );
				createSnapShotPopUp.open();

			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );

			}
		} );

	}

	private void restoreSnapshotBtnAction( LEMSnapshotModel lemSnapshotModel )
	{
		RestoreSnapshotPopUp restoreSnapshotPopUp = new RestoreSnapshotPopUp( lemSnapshotModel, uiComponentsUtil, getUpdateIcommandWithData() );
		restoreSnapshotPopUp.open();
	}

	private void promoteSnapshotBtnAction( LEMSnapshotModel lemSnapshotModel )
	{
		PromoteSnapshotPopUp promoteSnapshotPopUp = new PromoteSnapshotPopUp( lemSnapshotModel, uiComponentsUtil, getUpdateIcommandWithData() );
		promoteSnapshotPopUp.open();
	}

	private void deleteSnapshotBtnAction( LEMSnapshotModel lemSnapshotModel )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to delete snapshot " + lemSnapshotModel.getSnapshot_name() + " ?" );
		delteSnapshotConfirmPasswordPopup = new ConfirmPasswordPopup( "Delete Snapshot", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{

			@Override
			public void execute()
			{
				// TODO Auto-generated method stub
				LoggerUtil.log( "Delete Snapshot" );
				MaterialLoader.loading( true, delteSnapshotConfirmPasswordPopup.getBodyPanel() );
				LEMSnapshotDeleteModel deleteSnapshotModel = new LEMSnapshotDeleteModel( lemSnapshotModel.getSnapshot_name() );
				CommonServiceProvider.getCommonService().deleteLemSnapshot( deleteSnapshotModel, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						delteSnapshotConfirmPasswordPopup.close();
						MaterialLoader.loading( false, delteSnapshotConfirmPasswordPopup.getBodyPanel() );
						MaterialToast.fireToast( lemSnapshotModel.getSnapshot_name() + " Deleted..!", "rounded" );
						getUpdateIcommandWithData().executeWithData( true );
					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );

					}
				} );

			}
		} );
		delteSnapshotConfirmPasswordPopup.open();
	}

	private void editSnapshotBtnAction( LEMSnapshotModel lemSnapshotModel )
	{
		RenameSnapshotPopUp renameSnapshotPopUp = new RenameSnapshotPopUp( lemModel, lemSnapshotModel, uiComponentsUtil, getUpdateIcommandWithData() );
		renameSnapshotPopUp.open();
	}

	private void deleteMultipleSnapshotsBtnAction()
	{
		List<LEMSnapshotModel> selectedRowModels = materialDataTable.getSelectedRowModels( false );
		if ( selectedRowModels.size() > 0 )
		{
			// need to create multiple snapshots popup
		}
	}

	private void generateData()
	{
		dataSource = new ListDataSource<LEMSnapshotModel>();
		List<LEMSnapshotModel> result = new ArrayList<LEMSnapshotModel>();
		dataSource.add( 0, result );
		totalSnapshots = result.size();

		createTable();
		materialRow.add( materialDataTable );
		materialRow.addStyleName( resources.style().margin_5() );
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

				JSONObject queryObj = new JSONObject();
				queryObj.put( "lem_id", RestCallUtil.getJSONString( lemModel.getId() ) );

				CommonServiceProvider.getCommonService().getLEMSnapshots( RestCallUtil.getQueryRequest( queryObj.toString() ), ( boolean ) data, new ApplicationCallBack<List<LEMSnapshotModel>>()
				{

					@Override
					public void onSuccess( List<LEMSnapshotModel> lemSnapshotModelsRes )
					{

						MaterialLoader.loading( false );
						lemSnapshotModels.clear();
						lemSnapshotModels.addAll( lemSnapshotModelsRes );
						materialDataTable.setRowData( 0, lemSnapshotModels );
						materialDataTable.getView().setRedraw( true );
						materialDataTable.getView().refresh();

					}
				} );

			}
		};

		return icommand;

	}

}
