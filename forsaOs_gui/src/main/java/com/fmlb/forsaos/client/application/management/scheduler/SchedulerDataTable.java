package com.fmlb.forsaos.client.application.management.scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.SchedulerModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONObject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.component.RowComponent;
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

public class SchedulerDataTable extends MaterialPanel
{
	private UIComponentsUtil uiComponentsUtil;

	@SuppressWarnings( "unused" )
	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData updateSchedulerTableCmd;

	private MaterialButton createSchedulerActBtn;

	private MaterialDataPager<SchedulerModel> pager;

	private MaterialDataTable<SchedulerModel> materialDataTable;

	private MaterialLabel currentSchedulerLabel;

	private MaterialRow materialRow = new MaterialRow();

	private ListDataSource<SchedulerModel> dataSource;

	private ConfirmPasswordPopup delteSchedulerConfirmPasswordPopup;

	private MaterialLabel totalSchedulerlabel;

	private int totalScheduler;

	public SchedulerDataTable( UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData updateSchedulerTableCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		this.updateSchedulerTableCmd = updateSchedulerTableCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSchedulerBtn();
		generateData();
	}

	private void createSchedulerBtn()
	{
		createSchedulerActBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create Scheduler" );
		createSchedulerActBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				createSchedulerActBtn();
			}
		} );
	}

	private void createSchedulerActBtn()
	{
		JSONObject queryObj = new JSONObject();
		queryObj.put( "type", RestCallUtil.getJSONNumber( 1d ) );
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getPartition( RestCallUtil.getQueryRequest( queryObj.toString() ), new ApplicationCallBack<List<String>>()
		{
			@Override
			public void onSuccess( List<String> result )
			{
				MaterialLoader.loading( false );
				CreateSchedulerPopUp createSchedulerPopUp = new CreateSchedulerPopUp( uiComponentsUtil, getUpdateIcommandWithData(), currentUser, result );
				createSchedulerPopUp.open();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createPagination()
	{
		pager = new MaterialDataPager<SchedulerModel>( materialDataTable, dataSource );
		this.uiComponentsUtil.getPaginationOptions( materialDataTable, pager );
	}

	private void deleteSchedulertnAction( SchedulerModel schedulerModel )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to delete Scheduler  ' " + schedulerModel.getName() + "' " + " ?" );
		delteSchedulerConfirmPasswordPopup = new ConfirmPasswordPopup( "Delete Scheduler", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				LoggerUtil.log( "Delete Scheduler" );
				MaterialLoader.loading( true, delteSchedulerConfirmPasswordPopup.getBodyPanel() );
				SchedulerModel deleteSchedulerModel = new SchedulerModel( schedulerModel.getName() );
				CommonServiceProvider.getCommonService().deleteScheduler( deleteSchedulerModel, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, delteSchedulerConfirmPasswordPopup.getBodyPanel() );
						delteSchedulerConfirmPasswordPopup.close();
						MaterialToast.fireToast( schedulerModel.getName() + " deleted..!", "rounded" );
						getUpdateIcommandWithData().executeWithData( true );
					}

					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false, delteSchedulerConfirmPasswordPopup.getBodyPanel() );
						super.onFailureShowErrorPopup( caught, null );
					}
				} );
			}
		} );
		delteSchedulerConfirmPasswordPopup.open();
	}

	private void actionOnCurrentSchedNameClick( SchedulerModel schedulerModel )
	{
		LoggerUtil.log( schedulerModel.getName() );
		if ( updateSchedulerTableCmd != null )
		{
		}
	}

	private void pauseScheduler( SchedulerModel schedulerModel, MaterialButton enableButton )
	{
		SchedulerModel pauseSchedulerModel = new SchedulerModel( schedulerModel.getName(), false );
		CommonServiceProvider.getCommonService().pauseScheduler( pauseSchedulerModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				enableButton.setIconType( IconType.PLAY_ARROW );
				enableButton.setIconColor( Color.RED );
				MaterialToast.fireToast( schedulerModel.getName() + " scheduler disabled..!", "rounded" );
				if ( getUpdateIcommandWithData() != null )
				{
					getUpdateIcommandWithData().executeWithData( true );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void resumeScheduler( SchedulerModel schedulerModel, MaterialButton enableButton )
	{
		SchedulerModel resumeSchedulerModel = new SchedulerModel( schedulerModel.getName() );
		CommonServiceProvider.getCommonService().resumeScheduler( resumeSchedulerModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				enableButton.setIconType( IconType.PAUSE );
				enableButton.setIconColor( Color.BLACK );
				MaterialToast.fireToast( schedulerModel.getName() + " scheduler enabled..!", "rounded" );
				if ( getUpdateIcommandWithData() != null )
				{
					getUpdateIcommandWithData().executeWithData( true );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<SchedulerModel>();
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
					currentSchedulerLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Schedules" );
					totalSchedulerlabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalScheduler + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( currentSchedulerLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalSchedulerlabel );
					materialDataTable.getScaffolding().getToolPanel().add( createSchedulerActBtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
				}
			}
		} );

		materialDataTable.addColumn( new WidgetColumn<SchedulerModel, MaterialLabel>()
		{
			@Override
			public Comparator< ? super RowComponent<SchedulerModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}

			@Override
			public MaterialLabel getValue( SchedulerModel schedulerModel )
			{
				MaterialLabel schedulerNameLabel = new MaterialLabel();
				schedulerNameLabel.setText( schedulerModel.getName() );
				schedulerNameLabel.setTitle( schedulerModel.getName() );
				schedulerNameLabel.setHoverable( true );
				schedulerNameLabel.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						LoggerUtil.log( "schedule name clicked handler" );
						event.stopPropagation();
						actionOnCurrentSchedNameClick( schedulerModel );
					}
				} );
				return schedulerNameLabel;
			}
		}, "Name" );

		materialDataTable.addColumn( new TextColumn<SchedulerModel>()
		{
			@Override
			public Comparator< ? super RowComponent<SchedulerModel>> sortComparator()
			{
				return ( o1, o2 ) -> String.valueOf( o1.getData().getTimestamp() ).compareTo( String.valueOf( o2.getData().getTimestamp() ) );
			}

			@Override
			public String getValue( SchedulerModel object )
			{
				return object.getDate();
			}
		}, "Scheduled Time" );

		materialDataTable.addColumn( new WidgetColumn<SchedulerModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( SchedulerModel schedulerModel )
			{
				MaterialButton enableButton = new MaterialButton();
				if ( schedulerModel.getStatus() != 1 )
				{
					enableButton.setIconType( IconType.PAUSE );
					enableButton.setIconColor( Color.BLACK );
					enableButton.setTitle( "Disable" );
				}
				else
				{
					enableButton.setIconType( IconType.PLAY_ARROW );
					enableButton.setIconColor( Color.BLACK );
					enableButton.setTitle( "Enable" );
				}
				enableButton.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						if ( schedulerModel.getStatus() != 1 )
						{
							pauseScheduler( schedulerModel, enableButton );
						}
						else
						{
							resumeScheduler( schedulerModel, enableButton );
						}
					}
				} );
				return enableButton;
			}
		}, "Enable" );

		materialDataTable.addColumn( new WidgetColumn<SchedulerModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( SchedulerModel object )
			{
				MaterialButton deleteBtn = new MaterialButton();
				deleteBtn.setTitle( "Delete" );
				deleteBtn.setIconType( IconType.DELETE );
				deleteBtn.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						deleteSchedulertnAction( object );
					}
				} );
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

	}

	private void generateData()
	{
		LoggerUtil.log( "generate data scheduler" );

		dataSource = new ListDataSource<SchedulerModel>();
		List<SchedulerModel> result = new ArrayList<SchedulerModel>();
		dataSource.add( 0, result );
		totalScheduler = result.size();

		createTable();
		createPagination();
		materialRow.add( materialDataTable );
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
				CommonServiceProvider.getCommonService().getScheduler( RestCallUtil.ALL_REQUEST_DATA, ( boolean ) data, new ApplicationCallBack<List<SchedulerModel>>()
				{
					@Override
					public void onSuccess( List<SchedulerModel> schedulerModel )
					{
						MaterialLoader.loading( false );
						dataSource = new ListDataSource<SchedulerModel>();
						dataSource.add( 0, schedulerModel );

						totalScheduler = schedulerModel.size();
						totalSchedulerlabel.setText( "Total Items: " + "(" + totalScheduler + ")" );

						pager.setDataSource( dataSource );
						uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

						materialDataTable.getView().setRedraw( true );
						materialDataTable.getView().refresh();
					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );
					}
				} );
			}
		};
		return icommand;
	}
}
