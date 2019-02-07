package com.fmlb.forsaos.client.application.lem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CapacityChartModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
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

public class LEMDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<LEMModel> materialDataTable;

	private MaterialDataPager<LEMModel> pager;

	private ListDataSource<LEMModel> dataSource;

	private MaterialLabel lemLabel;

	private MaterialLabel totalLEMlabel;

	private int totalLems;

	private MaterialButton createLEMBtn;

	private MaterialButton deleteMultipleLEMsIcon;

	MaterialSearch search;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private HashMap<String, HashMap<String, VMModel>> vmLemMap = new HashMap<String, HashMap<String, VMModel>>();

	public LEMDataTable( int height, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createCreateLEMBtn();
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

	private void createCreateLEMBtn()
	{
		createLEMBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create LEM" );
		createLEMBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createLemBtnAction();

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

	private void createLemBtnAction()
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getVMsList( null, false, new ApplicationCallBack<List<VMModel>>()
		{
			@Override
			public void onSuccess( List<VMModel> vmModels )
			{
				CommonServiceProvider.getCommonService().getCapacityChartData( new ApplicationCallBack<CapacityChartModel>()
				{

					@Override
					public void onSuccess( CapacityChartModel capacityChartModel )
					{
						MaterialLoader.loading( false );
						CreateLEMPopUp createLEMPopUp1 = new CreateLEMPopUp( Converter.getFormatSize( capacityChartModel.getAvailableSize() ), capacityChartModel.getAvailableSize(), uiComponentsUtil, getUpdateIcommandWithData(), vmModels );
						createLEMPopUp1.open();

					}
				} );

			}
		} );

	}

	private void deleteLEMBtnAction( LEMModel lemModel )
	{
		DeleteLEMPopUp deleteLEMPopUp = new DeleteLEMPopUp( lemModel, this.uiComponentsUtil, getUpdateIcommandWithData(), currentUser );
		deleteLEMPopUp.open();
	}

	private void deleteMultipleLEMSBtnAction()
	{
		List<LEMModel> selectedRowModels = materialDataTable.getSelectedRowModels( false );
		if ( selectedRowModels.size() > 0 )
		{
			DeleteLEMsPopUp deleteLEMsPopUp = new DeleteLEMsPopUp( selectedRowModels, this.uiComponentsUtil, getUpdateIcommandWithData(), currentUser );
			deleteLEMsPopUp.open();
		}
		else
		{
			this.uiComponentsUtil.getMaterialDialog( "Destroy LEMs", "Please select atleast one LEM", "Close", null ).open();
		}

	}

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

	private void actionOnLEMNameClick( LEMModel lemModel )
	{
		//DummyData.setLEMModel( lemModel );
		LoggerUtil.log( lemModel.getLemName() );
		if ( navigationCmd != null )
		{
			navigationCmd.executeWithData( lemModel );
		}

	}

	private void createPagination()
	{
		pager = new MaterialDataPager<LEMModel>( materialDataTable, dataSource );
		this.uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<LEMModel>();
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
					materialDataTable.setHeight( "calc(72vh - 50px)" );
					// materialDataTable.setTitle("LEMS");
					// materialDataTable.addStyleName("materialDataTableTabWorkStep");
					// materialDataTable.setMarginTop(64.00);
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					lemLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "LEMS" );
					totalLEMlabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalLems + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( lemLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalLEMlabel );
					materialDataTable.getScaffolding().getToolPanel().add( createLEMBtn );
					materialDataTable.getScaffolding().getToolPanel().add( deleteMultipleLEMsIcon );
					// materialDataTable.getScaffolding().getToolPanel().add(search);
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					// materialDataTable.getScaffolding().getTopPanel().add(search);
				}

			}
		} );

		materialDataTable.addColumn( new WidgetColumn<LEMModel, MaterialLabel>()
		{
			@Override
			public Comparator< ? super RowComponent<LEMModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getLemName().toString().compareToIgnoreCase( o2.getData().getLemName().toString() );
			}
			/*
			 * @Override public TextAlign textAlign() { return TextAlign.CENTER; }
			 */

			@Override
			public MaterialLabel getValue( LEMModel lemModel )
			{
				MaterialLabel lemNameLabel = new MaterialLabel();
				lemNameLabel.setText( lemModel.getLemName() );
				lemNameLabel.setTitle( lemModel.getLemName() );
				lemNameLabel.setHoverable( true );
				lemNameLabel.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						LoggerUtil.log( "lem name clicked handler" );
						event.stopPropagation();
						actionOnLEMNameClick( lemModel );
					}
				} );
				return lemNameLabel;
			}

		}, "Name" );

		materialDataTable.addColumn( new TextColumn<LEMModel>()
		{
			@Override
			public Comparator< ? super RowComponent<LEMModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getVMname().toString().compareToIgnoreCase( o2.getData().getVMname().toString() );
			}

			@Override
			public String getValue( LEMModel object )
			{
				HashMap<String, VMModel> vmIdModelMap = vmLemMap.get( object.getId() );
				String vms = "";
				if ( vmIdModelMap != null )
				{
					for ( Entry<String, VMModel> vmIdModel : vmIdModelMap.entrySet() )
					{
						vms = vms + vmIdModel.getValue().getName() + ", ";
					}
					vms = vms.substring( 0, vms.length() - 2 );
				}
				object.setVMname( vms );
				return vms;
			}
		}, "VM" );

		materialDataTable.addColumn( new TextColumn<LEMModel>()
		{
			@Override
			public Comparator< ? super RowComponent<LEMModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getSize().compareTo( o2.getData().getSize() );
			}

			@Override
			public String getValue( LEMModel object )
			{
				return Converter.getFormatKiBSize( object.getSize(), MemorySizeType.KiB.getValue() );
			}
		}, "Size" );

		/*materialDataTable.addColumn( new TextColumn<LEMModel>()
		{
			@Override
			public Comparator< ? super RowComponent<LEMModel>> sortComparator()
			{
				return ( o1, o2 ) -> String.valueOf( o1.getData().getNoOfsnapShots() ).compareTo( String.valueOf( o2.getData().getNoOfsnapShots() ) );
			}
		
			@Override
			public String getValue( LEMModel object )
			{
				//TODO this data is not being send by webservice
				//return String.valueOf( object.getNoOfsnapShots() );
				return "-";
			}
		}, "Snapshots" );*/

		materialDataTable.addColumn( new WidgetColumn<LEMModel, MaterialButton>()
		{
			/*
			 * @Override public TextAlign textAlign() { return TextAlign.CENTER; }
			 */

			@Override
			public MaterialButton getValue( LEMModel object )
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
						deleteLEMBtnAction( object );

					}
				} );
				// badge.setLayoutPosition(Style.Position.RELATIVE);
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addRowSelectHandler( new RowSelectHandler<LEMModel>()
		{

			@Override
			public void onRowSelect( RowSelectEvent<LEMModel> event )
			{
				enableDisableDelIcon();
			}

		} );
		materialDataTable.addSelectAllHandler( new SelectAllHandler<LEMModel>()
		{

			@Override
			public void onSelectAll( SelectAllEvent<LEMModel> event )
			{
				enableDisableDelIcon();
			}
		} );

		// materialDataTable.setVisibleRange(0, 2001);
		materialDataTable.addStyleName( "datatablebottompanel" );
		// materialDataTable.setRowData(0, lemModels);
		materialDataTable.setSelectionType( SelectionType.MULTIPLE );
	}

	private void generateData()
	{
		dataSource = new ListDataSource<LEMModel>();
		List<LEMModel> result = new ArrayList<LEMModel>();
		dataSource.add( 0, result );
		totalLems = result.size();

		createTable();
		createPagination();
		materialRow.add( materialDataTable );
		add( materialRow );
		/*CommonServiceProvider.getCommonService().getLems( null, new ApplicationCallBack<List<LEMModel>>()
		{
			@Override
			public void onSuccess( List<LEMModel> result )
			{
				LoggerUtil.log( "data received LEM" );
				dataSource = new ListDataSource<LEMModel>();
				dataSource.add( 0, result );
				totalLems = result.size();
		
				createTable();
				createPagination();
				materialRow.add( materialDataTable );
				add( materialRow );
			}
		
			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				Window.alert( caught.getMessage() );
			}
		} );*/
	}

	public IcommandWithData getUpdateIcommandWithData()
	{
		IcommandWithData icommand = new IcommandWithData()
		{

			@Override
			public void executeWithData( Object data )
			{
				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().getLems( null, ( boolean ) data, new ApplicationCallBack<List<LEMModel>>()
				{

					@Override
					public void onSuccess( List<LEMModel> lemModels )
					{
						CommonServiceProvider.getCommonService().getVMsList( RestCallUtil.VM_LEM_MAPPING, false, new ApplicationCallBack<List<VMModel>>()
						{
							@Override
							public void onSuccess( List<VMModel> vmModels )
							{
								if ( vmLemMap != null )
								{
									vmLemMap.clear();
								}
								for ( VMModel vmModel : vmModels )
								{
									for ( String disk : vmModel.getDisks() )
									{
										HashMap<String, VMModel> vmIDModelMapping = vmLemMap.get( disk );
										if ( vmIDModelMapping == null )
										{
											vmIDModelMapping = new HashMap<String, VMModel>();
										}
										vmIDModelMapping.put( vmModel.get_id().get$oid(), vmModel );
										vmLemMap.put( disk, vmIDModelMapping );
									}
								}

								MaterialLoader.loading( false );
								dataSource = new ListDataSource<LEMModel>();
								dataSource.add( 0, lemModels );
								totalLems = lemModels.size();
								totalLEMlabel.setText( "Total Items: " + "(" + totalLems + ")" );

								pager.setDataSource( dataSource );
								uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

								materialDataTable.getView().setRedraw( true );
								materialDataTable.getView().refresh();
							}
						} );
					}
				} );

			}
		};

		return icommand;

	}
}
