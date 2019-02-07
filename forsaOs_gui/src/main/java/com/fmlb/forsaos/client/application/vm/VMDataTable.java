package com.fmlb.forsaos.client.application.vm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmationPopup;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CapacityChartModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.VMState;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
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
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class VMDataTable extends MaterialPanel
{

	private static final String activator = "vmMultiSettings";

	private static final String activatorItemLevel = "vmMultiSettings";

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private List<VMModel> vmModels = new ArrayList<VMModel>();

	private MaterialDataTable<VMModel> materialDataTable;

	private MaterialDataPager<VMModel> pager;

	private ListDataSource<VMModel> dataSource;

	private MaterialLabel vmLabel;

	private MaterialLabel totalVMlabel;

	private int totalVMs;

	private MaterialButton createVMBtn;

	private MaterialButton settingsMultipleVMsIcon;

	private MaterialDropDown settingsMultipleVMsDropDown;

	MaterialSearch search;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private ConfirmationPopup confirmationPopup;

	AppResources resources = GWT.create( AppResources.class );

	public VMDataTable( int height, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.placeManager = placeManager;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createCreateVMBtn();
		createSettingsMultiItemDropDown();
		createSettingsMultiItemsIcon();
		generateData();
	}

	private void createSettingsMultiItemDropDown()
	{
		settingsMultipleVMsDropDown = getVMSettingsDropDown( activator );
		settingsMultipleVMsDropDown.addSelectionHandler( new SelectionHandler<Widget>()
		{
			@Override
			public void onSelection( SelectionEvent<Widget> event )
			{
				enableDisableVMPageAction();
			}
		} );
	}

	private void enableDisableVMPageAction()
	{
		List<VMModel> selectedRowModels = materialDataTable.getSelectedRowModels( true );
		boolean allRunning = true;
		boolean allPaused = true;
		boolean allOff = true;

		for ( VMModel vmModel : selectedRowModels )
		{
			LoggerUtil.log( "Status - --- - -- - - -- -  " + vmModel.getStatus() );
			//running
			if ( vmModel.getStatus() == 2 )
			{
				allPaused = false;
				allOff = false;
			}
			//paused
			else if ( vmModel.getStatus() == 3 )
			{
				allRunning = false;
				allOff = false;
			}
			//shutdown
			else
			{
				allRunning = false;
				allPaused = false;
			}
		}
		LoggerUtil.log( "allRunning --  " + allRunning + "    allPaused --  " + allPaused + "    allOff --  " + allOff );

		if ( allRunning )
		{
			settingsMultipleVMsDropDown.getWidget( 0 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 1 ).setVisible( true );
			settingsMultipleVMsDropDown.getWidget( 2 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 3 ).setVisible( true );
			settingsMultipleVMsDropDown.getWidget( 4 ).setVisible( true );
		}
		else if ( allPaused )
		{
			settingsMultipleVMsDropDown.getWidget( 0 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 1 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 2 ).setVisible( true );
			settingsMultipleVMsDropDown.getWidget( 3 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 4 ).setVisible( false );
		}
		else if ( allOff )
		{
			settingsMultipleVMsDropDown.getWidget( 0 ).setVisible( true );
			settingsMultipleVMsDropDown.getWidget( 1 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 2 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 3 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 4 ).setVisible( false );
		}
		else
		{
			settingsMultipleVMsDropDown.getWidget( 0 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 1 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 2 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 3 ).setVisible( false );
			settingsMultipleVMsDropDown.getWidget( 4 ).setVisible( false );
		}
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

	private void createCreateVMBtn()
	{
		createVMBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create VM" );
		createVMBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createVMBtnAction();
			}
		} );
	}

	private void createSettingsMultiItemsIcon()
	{
		settingsMultipleVMsIcon = this.uiComponentsUtil.getSettingsMultipleItemIcon();
		settingsMultipleVMsIcon.setActivates( activator );
		settingsMultipleVMsIcon.add( settingsMultipleVMsDropDown );

	}

	private void createVMBtnAction()
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getCapacityChartData( new ApplicationCallBack<CapacityChartModel>()
		{

			@Override
			public void onSuccess( CapacityChartModel capacityChartModel )
			{
				MaterialLoader.loading( false );
				CreateVMPopup createVMPopup = new CreateVMPopup( Converter.getFormatSize( capacityChartModel.getAvailableSize() ), capacityChartModel.getAvailableSize(), uiComponentsUtil, getUpdateIcommandWithData(), vmModels );
				createVMPopup.open();

			}
		} );

	}

	private void deleteVMBtnAction( List<VMModel> vmModels )
	{
		DeleteVMPopUp deleteVMPopUp = new DeleteVMPopUp( vmModels, uiComponentsUtil, getUpdateIcommandWithData(), currentUser );
		deleteVMPopUp.open();
	}

	private void enableDisableDelIcon()
	{
		if ( materialDataTable.getSelectedRowModels( false ).size() > 0 )
		{
			settingsMultipleVMsIcon.setEnabled( true );
			LoggerUtil.log( "enable delete button" );
		}
		else
		{
			settingsMultipleVMsIcon.setEnabled( false );
			LoggerUtil.log( "disable delete button" );
		}
	}

	private void actionOnVMNameClick( VMModel vmModel )
	{
		LoggerUtil.log( vmModel.getVmName() );
		if ( navigationCmd != null )
		{
			navigationCmd.executeWithData( vmModel );
		}

	}

	private void createPagination()
	{
		pager = new MaterialDataPager<VMModel>( materialDataTable, dataSource );
		this.uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<VMModel>();
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
					vmLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "VM" );
					totalVMlabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalVMs + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( vmLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalVMlabel );
					materialDataTable.getScaffolding().getToolPanel().add( createVMBtn );
					materialDataTable.getScaffolding().getToolPanel().add( settingsMultipleVMsIcon );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
				}

			}
		} );

		materialDataTable.addColumn( new WidgetColumn<VMModel, MaterialButton>()
		{
			@Override
			public String width()
			{
				// TODO Auto-generated method stub
				return "5%";
			}

			@Override
			public MaterialButton getValue( VMModel vmModel )
			{
				MaterialButton power = new MaterialButton();
				power.setTitle( "Power" );
				if ( vmModel.getStatus() == 2 )
				{
					power.setIconType( IconType.POWER_SETTINGS_NEW );
					power.setIconColor( Color.WHITE );
					power.setBackgroundColor( Color.GREEN );
					power.setBorder( "NONE" );
				}
				else if ( vmModel.getStatus() == 3 )
				{
					power.setIconType( IconType.PAUSE );
					power.setIconColor( Color.WHITE );
					power.setBackgroundColor( Color.ORANGE_ACCENT_3 );
					power.setBorder( "NONE" );
				}
				else
				{
					power.setIconType( IconType.POWER_SETTINGS_NEW );
					power.setIconColor( Color.WHITE );
					power.setBackgroundColor( Color.GREY );
					power.setBorder( "NONE" );
				}

				MaterialDropDown settingsItemLevelDropDown = uiComponentsUtil.getVMSettingsDropDown( activatorItemLevel, false );
				settingsItemLevelDropDown.addStyleName( resources.style().power_action_dropdown() );

				settingsItemLevelDropDown.addSelectionHandler( new SelectionHandler<Widget>()
				{
					@Override
					public void onSelection( SelectionEvent<Widget> callback )
					{
						onItemPowerIconClick( vmModel, ( ( MaterialLink ) callback.getSelectedItem() ).getText(), power, settingsItemLevelDropDown );
					}
				} );

				power.add( settingsItemLevelDropDown );
				power.setActivates( activatorItemLevel );
				power.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						uiComponentsUtil.enableDisablePowerIconMenus( vmModel, settingsItemLevelDropDown );
					}
				} );
				return power;
			}
		}, "" );

		materialDataTable.addColumn( new WidgetColumn<VMModel, MaterialLabel>()
		{
			@Override
			public Comparator< ? super RowComponent<VMModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getVmName().toString().compareToIgnoreCase( o2.getData().getVmName().toString() );
			}

			@Override
			public MaterialLabel getValue( VMModel vmModel )
			{
				MaterialLabel vmNameLabel = new MaterialLabel();
				vmNameLabel.setText( vmModel.getVmName() );
				vmNameLabel.setTitle( vmModel.getVmName() );
				vmNameLabel.setHoverable( true );
				vmNameLabel.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						LoggerUtil.log( "lem name clicked handler" );
						event.stopPropagation();
						actionOnVMNameClick( vmModel );
					}
				} );
				return vmNameLabel;
			}

		}, "Name" );

		materialDataTable.addColumn( new TextColumn<VMModel>()
		{
			@Override
			public Comparator< ? super RowComponent<VMModel>> sortComparator()
			{
				return ( o1, o2 ) -> String.valueOf( o1.getData().getNoOfCores() ).compareToIgnoreCase( String.valueOf( o2.getData().getNoOfCores() ) );
			}

			@Override
			public String getValue( VMModel object )
			{
				return String.valueOf( object.getNoOfCores() );
			}
		}, "Cores" );

		materialDataTable.addColumn( new TextColumn<VMModel>()
		{
			@Override
			public Comparator< ? super RowComponent<VMModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getMemorySize().compareTo( o2.getData().getMemorySize() );
			}

			@Override
			public String getValue( VMModel object )
			{
				return Converter.getFormatKiBSize( object.getMemorySize(), MemorySizeType.KiB.getValue() );
			}
		}, "Memory" );

		materialDataTable.addColumn( new TextColumn<VMModel>()
		{
			@Override
			public Comparator< ? super RowComponent<VMModel>> sortComparator()
			{
				return ( o1, o2 ) -> String.valueOf( o1.getData().getNoOfLEMs() ).compareTo( String.valueOf( o2.getData().getNoOfLEMs() ) );
			}

			@Override
			public String getValue( VMModel object )
			{
				return String.valueOf( object.getDisks().size() );
			}
		}, "LEMS" );

		materialDataTable.addColumn( new WidgetColumn<VMModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( VMModel vmModel )
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
						List<VMModel> vmModels = new ArrayList<VMModel>();
						vmModels.add( vmModel );
						deleteVMBtnAction( vmModels );
					}
				} );
				// badge.setLayoutPosition(Style.Position.RELATIVE);
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addRowSelectHandler( new RowSelectHandler<VMModel>()
		{

			@Override
			public void onRowSelect( RowSelectEvent<VMModel> event )
			{
				enableDisableDelIcon();
				enableDisableVMPageAction();
			}

		} );
		materialDataTable.addSelectAllHandler( new SelectAllHandler<VMModel>()
		{

			@Override
			public void onSelectAll( SelectAllEvent<VMModel> event )
			{
				enableDisableDelIcon();
				enableDisableVMPageAction();
			}
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setSelectionType( SelectionType.MULTIPLE );
	}

	private void generateData()
	{
		LoggerUtil.log( "generate data LEM" );
		dataSource = new ListDataSource<VMModel>();
		List<VMModel> result = new ArrayList<VMModel>();
		dataSource.add( 0, result );
		totalVMs = result.size();

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
				updateVMDatatable( data );
			}
		};
		return icommand;
	}

	private void updateVMDatatable( Object data )
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getVMsList( null, ( boolean ) data, new ApplicationCallBack<List<VMModel>>()
		{
			@Override
			public void onSuccess( List<VMModel> result )
			{
				vmModels = result;
				MaterialLoader.loading( false );
				dataSource = new ListDataSource<VMModel>();
				dataSource.add( 0, result );
				totalVMs = result.size();
				totalVMlabel.setText( "Total Items: " + "(" + totalVMs + ")" );

				pager.setDataSource( dataSource );
				uiComponentsUtil.getPaginationOptions( materialDataTable, pager );

				materialDataTable.getView().setRedraw( true );
				materialDataTable.getView().refresh();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			};
		} );
	}

	private void onItemPowerIconClick( VMModel vmModel, String selectedAction, MaterialButton power, MaterialDropDown settingsItemLevelDropDown )
	{
		CommonServiceProvider.getCommonService().changeVMState( vmModel, VMState.getVMState( selectedAction ), new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialToast.fireToast( vmModel.getName() + "'s status updated to " + selectedAction, "rounded" );
				if ( selectedAction.equalsIgnoreCase( "start" ) || selectedAction.equalsIgnoreCase( "resume" ) )
				{
					power.setIconType( IconType.POWER_SETTINGS_NEW );
					power.setIconColor( Color.WHITE );
					power.setBackgroundColor( Color.GREEN );
					power.setBorder( "NONE" );
					vmModel.setStatus( 2 );
				}
				else if ( selectedAction.equalsIgnoreCase( "pause" ) )
				{
					power.setIconType( IconType.PAUSE );
					power.setIconColor( Color.WHITE );
					power.setBackgroundColor( Color.ORANGE_ACCENT_3 );
					power.setBorder( "NONE" );
					vmModel.setStatus( 3 );
				}
				else
				{
					power.setIconType( IconType.POWER_SETTINGS_NEW );
					power.setIconColor( Color.WHITE );
					power.setBackgroundColor( Color.GREY );
					power.setBorder( "NONE" );
					vmModel.setStatus( 4 );
				}
				uiComponentsUtil.enableDisablePowerIconMenus( vmModel, settingsItemLevelDropDown );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, "Unable to change status of " + vmModel.getName() + " to " + selectedAction );
			}
		} );
	}

	public MaterialDropDown getVMSettingsDropDown( String activatorStr )
	{
		MaterialDropDown materialDropDown = new MaterialDropDown( activatorStr );
		materialDropDown.setStyleName( activatorStr );
		materialDropDown.setConstrainWidth( false );
		materialDropDown.setBelowOrigin( true );

		MaterialLink start = this.uiComponentsUtil.getMaterialLink( "Start", new MaterialIcon( IconType.PLAY_CIRCLE_FILLED ) );
		MaterialLink pause = this.uiComponentsUtil.getMaterialLink( "Pause", new MaterialIcon( IconType.PAUSE ) );
		MaterialLink resume = this.uiComponentsUtil.getMaterialLink( "Resume", new MaterialIcon( IconType.PLAY_CIRCLE_OUTLINE ) );
		MaterialLink shutdown = this.uiComponentsUtil.getMaterialLink( "Shutdown", new MaterialIcon( IconType.STOP ) );
		MaterialLink powerOff = this.uiComponentsUtil.getMaterialLink( "Power Off", new MaterialIcon( IconType.POWER_SETTINGS_NEW ) );
		MaterialLink delete = this.uiComponentsUtil.getMaterialLink( "Delete", new MaterialIcon( IconType.DELETE ) );

		start.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				onMultiSelectAction( "start" );
			}
		} );

		pause.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				onMultiSelectAction( "pause" );
			}
		} );

		resume.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				onMultiSelectAction( "resume" );
			}
		} );

		shutdown.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				ArrayList<String> warningMsgList = new ArrayList<String>();
				warningMsgList.add( "Are you sure you want to send shutdown signal for " + getSelectVMNames() + "?" );
				confirmationPopup = new ConfirmationPopup( "Shutdown VM(s)", "Shutdown", warningMsgList, uiComponentsUtil, new Icommand()
				{
					@Override
					public void execute()
					{
						confirmationPopup.close();
						onMultiSelectAction( "shutdown" );
					}
				} );
				confirmationPopup.open();
			}
		} );

		powerOff.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				ArrayList<String> warningMsgList = new ArrayList<String>();
				warningMsgList.add( "Are you sure you want to send power off signal for " + getSelectVMNames() + "?" );
				confirmationPopup = new ConfirmationPopup( "Power Off VM(s)", "Power Off", warningMsgList, uiComponentsUtil, new Icommand()
				{
					@Override
					public void execute()
					{
						confirmationPopup.close();
						onMultiSelectAction( "power off" );
					}
				} );
				confirmationPopup.open();
			}
		} );

		delete.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				deleteVMBtnAction( materialDataTable.getSelectedRowModels( true ) );
			}
		} );

		materialDropDown.add( start );
		materialDropDown.add( pause );
		materialDropDown.add( resume );
		materialDropDown.add( shutdown );
		materialDropDown.add( powerOff );
		materialDropDown.add( delete );

		return materialDropDown;
	}

	private int counter = 0;

	private String getSelectVMNames()
	{
		String vmNames = "";
		for ( VMModel vmModel : materialDataTable.getSelectedRowModels( true ) )
		{
			vmNames = vmNames + vmModel.getName() + ", ";
		}
		return vmNames.substring( 0, vmNames.length() - 2 );
	}

	private void onMultiSelectAction( String selectedAction )
	{
		LoggerUtil.log( "selectedAction - --- - -- - - -- -  " + selectedAction );
		int vmCount = materialDataTable.getSelectedRowModels( true ).size();
		counter = 0;
		MaterialLoader.loading( true );
		for ( VMModel vmModel : materialDataTable.getSelectedRowModels( true ) )
		{
			CommonServiceProvider.getCommonService().changeVMState( vmModel, VMState.getVMState( selectedAction ), new ApplicationCallBack<Boolean>()
			{
				@Override
				public void onSuccess( Boolean result )
				{
					if ( selectedAction.equalsIgnoreCase( "shutdown" ) || selectedAction.equalsIgnoreCase( "power off" ) )
					{
						MaterialToast.fireToast( vmModel.getName() + " signalled for " + selectedAction, "rounded" );
					}
					else
					{
						MaterialToast.fireToast( vmModel.getName() + "'s status updated to " + selectedAction, "rounded" );
					}
					enableDisableVMPageAction();
					if ( ++counter == vmCount )
					{
						MaterialLoader.loading( false );
						updateVMDatatable( true );
					}
				}

				@Override
				public void onFailure( Throwable caught )
				{
					MaterialToast.fireToast( "Unable to change status of " + vmModel.getName() + " to " + selectedAction, "rounded" );
					//					super.onFailureShowErrorPopup( caught, "Unable to change status of " + vmModel.getName() + " to " + selectedAction );
					if ( ++counter == vmCount )
					{
						MaterialLoader.loading( false );
						updateVMDatatable( true );
					}
				}
			} );
		}
	}
}