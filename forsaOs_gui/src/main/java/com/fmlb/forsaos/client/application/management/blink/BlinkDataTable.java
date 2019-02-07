package com.fmlb.forsaos.client.application.management.blink;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.BlinkModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.DeleteBlinkModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.shared.application.utility.PartitionType;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONObject;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class BlinkDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<BlinkModel> materialDataTable;

	private MaterialDataPager<BlinkModel> pager;

	private ListDataSource<BlinkModel> dataSource;

	private MaterialLabel blinkLabel;

	private MaterialLabel totalBlinkLable;

	private int totalBlinks;

	private MaterialButton createBlinkBtn;

	MaterialSearch search;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private ConfirmPasswordPopup delteBlinkNameConfirmPasswordPopup;

	List<String> partitionNames = new ArrayList<>();

	public BlinkDataTable( int height, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createcreateBlinkBtn();
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

	private void createcreateBlinkBtn()
	{
		createBlinkBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create Blink" );
		createBlinkBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createBlinkBtnAction();

			}
		} );
	}

	private void createBlinkBtnAction()
	{
		CreateBlinkPopup createBlinkPopup = new CreateBlinkPopup( uiComponentsUtil, getUpdateIcommandWithData(), partitionNames );
		createBlinkPopup.open();

	}

	private void deleteBlinkBtnAction( BlinkModel blinkModel )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to delete blink " + blinkModel.getName() + "?" );
		delteBlinkNameConfirmPasswordPopup = new ConfirmPasswordPopup( "Delete Blink", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				LoggerUtil.log( "Delete Blink" );
				MaterialLoader.loading( true, delteBlinkNameConfirmPasswordPopup.getBodyPanel() );
				DeleteBlinkModel deleteBlinkModel = new DeleteBlinkModel( blinkModel.getName() );
				CommonServiceProvider.getCommonService().deleteBlink( deleteBlinkModel, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, delteBlinkNameConfirmPasswordPopup.getBodyPanel() );
						delteBlinkNameConfirmPasswordPopup.close();
						MaterialToast.fireToast( deleteBlinkModel.getName() + " Deleted..!", "rounded" );
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
		delteBlinkNameConfirmPasswordPopup.open();
	}

	private void restoreBlinkAction( BlinkModel blinkModel )
	{
		RestoreBlinkPopup restoreBlink = new RestoreBlinkPopup( blinkModel, uiComponentsUtil );
		restoreBlink.open();
	}

	private void actionOnBlinkNameClick( BlinkModel blinkModel )
	{
		LoggerUtil.log( blinkModel.getName() );
		if ( navigationCmd != null )
		{
			navigationCmd.executeWithData( blinkModel );
		}

	}

	private void createPagination()
	{
		pager = new MaterialDataPager<BlinkModel>( materialDataTable, dataSource );
		this.uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<BlinkModel>();
		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					LoggerUtil.log( "Attach blink data table" );
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					materialDataTable.setHeight( "calc(72vh - 50px)" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					blinkLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Blinks" );
					totalBlinkLable = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalBlinks + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( blinkLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalBlinkLable );
					materialDataTable.getScaffolding().getToolPanel().add( createBlinkBtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );

				}

			}
		} );

		materialDataTable.addColumn( new WidgetColumn<BlinkModel, MaterialLabel>()
		{
			@Override
			public Comparator< ? super RowComponent<BlinkModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}

			@Override
			public MaterialLabel getValue( BlinkModel blinkModel )
			{
				MaterialLabel blinkNameLable = new MaterialLabel();
				blinkNameLable.setText( blinkModel.getName() );
				blinkNameLable.setTitle( blinkModel.getName() );
				blinkNameLable.setHoverable( true );
				blinkNameLable.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						LoggerUtil.log( "Blink name clicked handler" );
						event.stopPropagation();
						actionOnBlinkNameClick( blinkModel );
					}
				} );
				return blinkNameLable;
			}

		}, "Name" );

		materialDataTable.addColumn( new TextColumn<BlinkModel>()
		{
			@Override
			public Comparator< ? super RowComponent<BlinkModel>> sortComparator()
			{
				return ( o1, o2 ) -> String.valueOf( o1.getData().getTimestamp() ).compareTo( String.valueOf( o2.getData().getTimestamp() ) );
			}

			@Override
			public String getValue( BlinkModel object )
			{
				return object.getDate();
			}
		}, "Date Created" );

		materialDataTable.addColumn( new WidgetColumn<BlinkModel, MaterialButton>()
		{

			@Override
			public MaterialButton getValue( BlinkModel object )
			{
				MaterialButton restoreBtn = new MaterialButton();
				restoreBtn.setTitle( "Restore Blink" );
				restoreBtn.setIconType( IconType.RESTORE );
				restoreBtn.setBackgroundColor( Color.BLUE );
				restoreBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						restoreBlinkAction( object );

					}
				} );

				return restoreBtn;
			}
		}, "Restore" );

		materialDataTable.addColumn( new WidgetColumn<BlinkModel, MaterialButton>()
		{

			@Override
			public MaterialButton getValue( BlinkModel blinkModel )
			{
				MaterialButton deleteBtn = new MaterialButton();
				deleteBtn.setTitle( "Delete" );
				deleteBtn.setIconType( IconType.DELETE );
				deleteBtn.setBackgroundColor( Color.BLUE );
				if ( blinkModel.getType() == PartitionType.BLINK.getType() )
				{
					deleteBtn.addClickHandler( new ClickHandler()
					{
						@Override
						public void onClick( ClickEvent event )
						{
							event.stopPropagation();
							deleteBlinkBtnAction( blinkModel );
						}
					} );
				}
				else
				{
					deleteBtn.setEnabled( false );
				}
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

	}

	private void generateData()
	{
		dataSource = new ListDataSource<BlinkModel>();
		List<BlinkModel> result = new ArrayList<BlinkModel>();
		dataSource.add( 0, result );

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
				CommonServiceProvider.getCommonService().getBlink( null, new ApplicationCallBack<List<BlinkModel>>()
				{
					@Override
					public void onSuccess( List<BlinkModel> result )
					{
						MaterialLoader.loading( false );
						dataSource = new ListDataSource<BlinkModel>();
						dataSource.add( 0, result );

						totalBlinks = result.size();
						if ( totalBlinkLable != null )
						{
							totalBlinkLable.setText( "Total Items: " + "(" + totalBlinks + ")" );
						}

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

				JSONObject queryObj = new JSONObject();
				queryObj.put( "type", RestCallUtil.getJSONNumber( 1d ) );
				CommonServiceProvider.getCommonService().getPartition( RestCallUtil.getQueryRequest( queryObj.toString() ), new ApplicationCallBack<List<String>>()
				{
					@Override
					public void onSuccess( List<String> result )
					{
						partitionNames.addAll( result );
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
