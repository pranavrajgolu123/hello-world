package com.fmlb.forsaos.client.application.settings.accounts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LoginHistoryModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;

public class LoginHistoryDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<LoginHistoryModel> materialDataTable;

	private MaterialDataPager<LoginHistoryModel> pager;

	private ListDataSource<LoginHistoryModel> dataSource;

	private MaterialLabel loginHistoryLabel;

	private MaterialLabel totalLoginlabel;

	private int totalLogins;

	private MaterialButton printBtn;

	private MaterialButton deleteMultipleLEMsIcon;

	MaterialSearch search;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	public LoginHistoryDataTable( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createPrintBtn();
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

	private void createPrintBtn()
	{
		printBtn = new MaterialButton( "Print" );
		printBtn.setIconType( IconType.PRINT );
		printBtn.setFloat( Float.RIGHT );
		printBtn.setTextColor( Color.BLUE );
		printBtn.setIconPosition( IconPosition.RIGHT );
		printBtn.setIconColor( Color.BLUE );

		printBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{

			}
		} );
	}

	private void createPagination()
	{
		pager = new MaterialDataPager<LoginHistoryModel>( materialDataTable, dataSource );
		this.uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<LoginHistoryModel>();
		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					LoggerUtil.log( "attach login data table start" );
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					materialDataTable.setHeight( "calc(72vh - 87px)" );
					// materialDataTable.setTitle("LEMS");
					// materialDataTable.addStyleName("materialDataTableTabWorkStep");
					// materialDataTable.setMarginTop(64.00);
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					loginHistoryLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Login History" );
					totalLoginlabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalLogins + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( loginHistoryLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalLoginlabel );
					//materialDataTable.getScaffolding().getToolPanel().add( printBtn );
					//materialDataTable.getScaffolding().getToolPanel().add( deleteMultipleLEMsIcon );
					// materialDataTable.getScaffolding().getToolPanel().add(search);
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					LoggerUtil.log( "attach login data table finished" );
					// materialDataTable.getScaffolding().getTopPanel().add(search);

					materialDataTable.getColumnMenuIcon().setVisible( false );
				}

			}
		} );

		materialDataTable.addColumn( new TextColumn<LoginHistoryModel>()
		{
			@Override
			public Comparator< ? super RowComponent<LoginHistoryModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}

			@Override
			public String getValue( LoginHistoryModel object )
			{

				return object.getName().toString();
			}
		}, "User" );

		materialDataTable.addColumn( new TextColumn<LoginHistoryModel>()
		{
			@Override
			public Comparator< ? super RowComponent<LoginHistoryModel>> sortComparator()
			{
				return ( o1, o2 ) -> String.valueOf( o1.getData().getTimepoint() ).compareTo( String.valueOf( o2.getData().getTimepoint() ) );
			}

			@Override
			public String getValue( LoginHistoryModel object )
			{
				return object.getDate();
			}
		}, "Date" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void generateData()
	{
		dataSource = new ListDataSource<LoginHistoryModel>();
		List<LoginHistoryModel> result = new ArrayList<LoginHistoryModel>();
		dataSource.add( 0, result );
		totalLogins = result.size();

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
				CommonServiceProvider.getCommonService().getLoginHistoryModels( ( String ) data, new ApplicationCallBack<List<LoginHistoryModel>>()
				{

					@Override
					public void onSuccess( List<LoginHistoryModel> loginHistoryModelsRes )
					{
						MaterialLoader.loading( false );
						dataSource = new ListDataSource<LoginHistoryModel>();
						dataSource.add( 0, loginHistoryModelsRes );
						totalLogins = loginHistoryModelsRes.size();
						if ( totalLoginlabel != null )
						{
							totalLoginlabel.setText( "Total Items: " + "(" + totalLogins + ")" );
						}

						pager.setDataSource( dataSource );
						uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

						materialDataTable.getView().setRedraw( true );
						materialDataTable.getView().refresh();
					}
				} );

			}
		};

		return icommand;

	}
}
