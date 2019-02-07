package com.fmlb.forsaos.client.application.eventlog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.EventLogRequestModel;
import com.fmlb.forsaos.client.application.models.EventLogResponseModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.CommonUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.i18n.client.DateTimeFormat;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;

public class EventLogDataTable extends MaterialPanel
{
	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<EventLogResponseModel> materialDataTable;

	private MaterialDataPager<EventLogResponseModel> pager;

	private ListDataSource<EventLogResponseModel> dataSource;

	private List<EventLogResponseModel> eventLogResponseModels;

	private MaterialLabel eventLogLabel;

	private MaterialLabel eventLogRecordlabel;

	private int totalEventLog;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private int counter;

	private MaterialTextBox searchTextBox;

	private MaterialButton searchButton;

	private MaterialButton clearButton;

	private MaterialListBox levelFilter;

	private MaterialListBox serviceFilter;

	private MaterialDatePicker fromFilter;

	private MaterialDatePicker toFilter;

	private MaterialLabel errorLabel;

	private MaterialPanel dateFilterPanel;

	private MaterialPanel filterPanel;

	AppResources resources = GWT.create( AppResources.class );

	public EventLogDataTable( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.counter = 0;
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		generateData();
		materialRow.add( filterPanel );
		materialRow.add( materialDataTable );
		add( materialRow );
	}

	private void createSearchComponent()
	{
		filterPanel = new MaterialPanel();
		filterPanel.addStyleName( resources.style().eventFilterPenal() );

		fromFilter = uiComponentsUtil.getDatePicker( "From Date" );
		fromFilter.addStyleName( "eventDateRow" );

		toFilter = uiComponentsUtil.getDatePicker( "To Date" );
		toFilter.addStyleName( "eventDateRow" );

		errorLabel = uiComponentsUtil.getLabel( "Error", null );
		errorLabel.setVisible( false );
		errorLabel.addStyleName( resources.style().eventErrorMsg() );

		dateFilterPanel = new MaterialPanel();
		dateFilterPanel.addStyleName( resources.style().dateToFilterPanel() );

		dateFilterPanel.add( fromFilter );
		dateFilterPanel.add( toFilter );
		dateFilterPanel.add( errorLabel );

		serviceFilter = uiComponentsUtil.getListBox( CommonUtil.getEventLogServices() );
		serviceFilter.setPlaceholder( "Service" );
		serviceFilter.addStyleName( "eventDropdown" );
		levelFilter = uiComponentsUtil.getListBox( CommonUtil.getEventLogLevels() );
		levelFilter.setPlaceholder( "Level" );
		levelFilter.addStyleName( "eventDropdown" );

		searchTextBox = new MaterialTextBox();
		searchTextBox.setWidth( "30" );
		searchTextBox.setLabel( "Keyword" );
		searchTextBox.addStyleName( "eventKeywordPenal" );
		searchTextBox.setPlaceholder( "Filter by keyword in description" );

		searchButton = new MaterialButton( "", IconType.SEARCH, ButtonType.FLAT );
		searchButton.addStyleName( resources.style().eventBtn() );
		searchButton.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				Boolean validateDateFilter = validateDateFilter();
				if ( validateDateFilter || !serviceFilter.getValue().equalsIgnoreCase( "all" ) || !levelFilter.getValue().equalsIgnoreCase( "all" ) || ( searchTextBox.getValue() != null && !searchTextBox.getValue().isEmpty() ) )
				{
					updateDataTable( validateDateFilter );
				}
				else
				{
					MaterialToast.fireToast( "Please select filter.", "rounded" );
				}
			}
		} );
		searchButton.setWidth( "30" );

		clearButton = new MaterialButton( "", IconType.CLEAR, ButtonType.FLAT );
		clearButton.addStyleName( resources.style().eventBtn() );
		clearButton.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				fromFilter.clear();
				toFilter.clear();
				errorLabel.setText( "" );
				errorLabel.setVisible( false );
				serviceFilter.setSelectedIndex( 0 );
				levelFilter.setSelectedIndex( 0 );
				searchTextBox.clear();

				updateDataTable( false );
			}
		} );
		clearButton.setWidth( "30" );

		filterPanel.add( dateFilterPanel );
		filterPanel.add( serviceFilter );
		filterPanel.add( levelFilter );
		filterPanel.add( searchTextBox );
		filterPanel.add( searchButton );
		filterPanel.add( clearButton );
	}

	private Boolean validateDateFilter()
	{
		Boolean isValid = false;
		Date fromDateValue = fromFilter.getDate();
		Date toDateValue = toFilter.getDate();
		if ( fromDateValue == null && toDateValue == null )
		{
			errorLabel.setVisible( false );
			return isValid;
		}
		if ( fromDateValue == null && toDateValue != null )
		{
			errorLabel.setText( "Please select from date." );
			errorLabel.setVisible( true );
			isValid = false;
			return isValid;
		}
		if ( fromDateValue != null && toDateValue == null )
		{
			errorLabel.setText( "Please select to date." );
			errorLabel.setVisible( true );
			isValid = false;
			return isValid;
		}
		if ( ( fromDateValue != null && toDateValue != null ) && ( fromDateValue.getTime() > toDateValue.getTime() ) )
		{
			errorLabel.setText( "Please select valid date range." );
			errorLabel.setVisible( true );
			isValid = false;
			return isValid;
		}
		if ( ( fromDateValue != null && toDateValue != null ) && ( fromDateValue.getTime() < toDateValue.getTime() ) )
		{
			errorLabel.setVisible( false );
			isValid = true;
		}
		return isValid;
	}

	private void createPagination()
	{
		pager = new MaterialDataPager<EventLogResponseModel>( materialDataTable, dataSource )
		{
			@Override
			protected void updateUi()
			{
				super.updateUi();
				pager.getActionsPanel().getIconNext().setEnabled( true );
				//				String actionLabel = pager.getActionsPanel().getActionLabel().getText();
				//				pager.getActionsPanel().getActionLabel().setText( actionLabel + " fetched" );

			}
		};
		this.uiComponentsUtil.getPaginationOptions2( materialDataTable, pager );
		pager.getActionsPanel().getIconNext().addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				int limit = pager.getLimit();
				int totalRows = pager.getTotalRows();
				int totalPages = pager.getTotalPages();
				int currentPage = pager.getCurrentPage();
				if ( currentPage == totalPages || totalRows == limit )
				{
					MaterialLoader.loading( true );
					counter++;

					String service = serviceFilter.getValue().equalsIgnoreCase( "all" ) ? null : serviceFilter.getValue();
					String level = levelFilter.getValue().equalsIgnoreCase( "all" ) ? null : levelFilter.getValue();
					String keyword = ( searchTextBox.getValue() != null && !searchTextBox.getValue().isEmpty() ) ? searchTextBox.getValue() : null;

					Long timeStart = null;
					Long timeStop = null;
					if ( validateDateFilter() )
					{
						timeStart = fromFilter.getDate().getTime() / 1000;
						timeStop = toFilter.getDate().getTime() / 1000;
					}
					EventLogRequestModel eventLogRequestModel = new EventLogRequestModel( CommonUtil.EVENT_LOG_FETCH_COUNT, true, true, service, null, level, timeStart, timeStop, ( counter * CommonUtil.EVENT_LOG_FETCH_COUNT ), keyword );
					CommonServiceProvider.getCommonService().getEventLog( eventLogRequestModel, new ApplicationCallBack<List<EventLogResponseModel>>()
					{
						@Override
						public void onSuccess( List<EventLogResponseModel> result )
						{
							eventLogResponseModels.addAll( result );
							dataSource.add( counter, result );
							totalEventLog = eventLogResponseModels.size();
							if ( eventLogRecordlabel != null )
							{
								eventLogRecordlabel.setText( "Total Records: " + "(" + totalEventLog + ")" );
							}
							materialDataTable.getView().setRedraw( true );
							materialDataTable.getView().refresh();
							pager.gotoPage( currentPage + 1 );
							MaterialLoader.loading( false );
						}

						@Override
						public void onFailure( Throwable caught )
						{
							MaterialLoader.loading( false );
							super.onFailureShowErrorPopup( caught, null );
						}
					} );
				}
			}
		} );
		pager.getActionsPanel().getIconPrev().addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				LoggerUtil.log( "" + pager.getCurrentPage() + "  " + pager.getTotalPages() );
			}
		} );
	}

	private void createTable()
	{
		DateTimeFormat displayFormat = DateTimeFormat.getFormat( CommonUtil.DATE_TIME_FORMAT );
		DateTimeFormat systemDateFormat = DateTimeFormat.getFormat( CommonUtil.SYS_DATE_TIME_FORMAT );
		
		materialDataTable = new MaterialDataTable<EventLogResponseModel>();
		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					LoggerUtil.log( "attach event log data table start" );
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					materialDataTable.setHeight( "calc(72vh - 87px)" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					eventLogLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Event Log" );
					eventLogRecordlabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Records: " + "(" + totalEventLog + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( eventLogLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( eventLogRecordlabel );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					materialDataTable.getColumnMenuIcon().setVisible( false );
					LoggerUtil.log( "attach event log data table finished" );
				}

			}
		} );

		materialDataTable.addColumn( new TextColumn<EventLogResponseModel>()
		{
			@Override
			public Comparator< ? super RowComponent<EventLogResponseModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getTimestamp().compareToIgnoreCase( o2.getData().getTimestamp() );
			}

			@Override
			public String getValue( EventLogResponseModel object )
			{
				return displayFormat.format( systemDateFormat.parse( object.getTimestamp() ) );
			}
		}, "TIMESTAMP" );

		materialDataTable.addColumn( new TextColumn<EventLogResponseModel>()
		{
			@Override
			public Comparator< ? super RowComponent<EventLogResponseModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getLevel().compareToIgnoreCase( o2.getData().getLevel() );
			}

			@Override
			public String getValue( EventLogResponseModel object )
			{
				return object.getLevel();
			}
		}, "LEVEL" );

		materialDataTable.addColumn( new TextColumn<EventLogResponseModel>()
		{
			@Override
			public Comparator< ? super RowComponent<EventLogResponseModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getService_name().compareToIgnoreCase( o2.getData().getService_name() );
			}

			@Override
			public String getValue( EventLogResponseModel object )
			{
				return object.getService_name();
			}
		}, "SERVICE NAME" );

		materialDataTable.addColumn( new TextColumn<EventLogResponseModel>()
		{
			@Override
			public Comparator< ? super RowComponent<EventLogResponseModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getMethod().compareToIgnoreCase( o2.getData().getMethod() );
			}

			@Override
			public String getValue( EventLogResponseModel object )
			{
				return object.getMethod();
			}
		}, "METHOD" );

		materialDataTable.addColumn( new TextColumn<EventLogResponseModel>()
		{
			@Override
			public Comparator< ? super RowComponent<EventLogResponseModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getDesc().compareToIgnoreCase( o2.getData().getDesc() );
			}

			@Override
			public String getValue( EventLogResponseModel object )
			{
				return object.getDesc();
			}
		}, "DESCRIPTION" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void generateData()
	{
		dataSource = new ListDataSource<EventLogResponseModel>();
		List<EventLogResponseModel> result = new ArrayList<EventLogResponseModel>();
		dataSource.add( 0, result );
		totalEventLog = result.size();

		createTable();
		createPagination();
	}

	public IcommandWithData getUpdateIcommandWithData()
	{
		IcommandWithData icommand = new IcommandWithData()
		{
			@Override
			public void executeWithData( Object data )
			{
				updateDataTable( false );
			}
		};
		return icommand;
	}

	private void updateDataTable( Boolean validateDateFilter )
	{
		MaterialLoader.loading( true );
		String service = serviceFilter.getValue().equalsIgnoreCase( "all" ) ? null : serviceFilter.getValue();
		String level = levelFilter.getValue().equalsIgnoreCase( "all" ) ? null : levelFilter.getValue();
		String keyword = ( searchTextBox.getValue() != null && !searchTextBox.getValue().isEmpty() ) ? searchTextBox.getValue() : null;
		Long timeStart = null;
		Long timeStop = null;
		if ( validateDateFilter )
		{
			timeStart = fromFilter.getDate().getTime() / 1000;
			timeStop = toFilter.getDate().getTime() / 1000;
		}
		EventLogRequestModel eventLogRequestModel = new EventLogRequestModel( CommonUtil.EVENT_LOG_FETCH_COUNT, true, true, service, null, level, timeStart, timeStop, null, keyword );
		CommonServiceProvider.getCommonService().getEventLog( eventLogRequestModel, new ApplicationCallBack<List<EventLogResponseModel>>()
		{
			@Override
			public void onSuccess( List<EventLogResponseModel> result )
			{
				eventLogResponseModels = result;

				dataSource = new ListDataSource<EventLogResponseModel>();
				dataSource.add( 0, eventLogResponseModels );
				totalEventLog = eventLogResponseModels.size();
				if ( eventLogRecordlabel != null )
				{
					eventLogRecordlabel.setText( "Total Records: " + "(" + totalEventLog + ")" );
				}

				pager.setDataSource( dataSource );
				uiComponentsUtil.getPaginationOptions2( materialDataTable, pager );

				materialDataTable.getView().setRedraw( true );
				materialDataTable.getView().refresh();

				MaterialLoader.loading( false );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}
}
