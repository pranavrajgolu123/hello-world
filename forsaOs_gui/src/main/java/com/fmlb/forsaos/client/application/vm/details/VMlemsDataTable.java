package com.fmlb.forsaos.client.application.vm.details;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CapacityChartModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONObject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class VMlemsDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<LEMModel> materialDataTable;

	private MaterialLabel lemLabel;

	private MaterialButton addLEM;

	private MaterialButton expandVMBtn;

	MaterialSearch search;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private VMModel vmModel;

	private List<LEMModel> lemModels = new ArrayList<LEMModel>();

	private List<LEMModel> lemAllModels = new ArrayList<LEMModel>();

	AppResources resources = GWT.create( AppResources.class );

	public VMlemsDataTable( VMModel vmModel, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
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
		expandVMBtn = getExpandVMBtn();
		expandVMBtn.setGrid( "s2" );
		enableDisableExpandVmBtn( expandVMBtn );
		createAddLEMBtn();
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

	private void createAddLEMBtn()
	{
		addLEM = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Add LEM" );
		addLEM.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				addLEMBtnAction();

			}
		} );
	}

	private void addLEMBtnAction()
	{
		List<LEMModel> filteredLemModels = new ArrayList<LEMModel>();
		List<String> lemIds = new ArrayList<String>();
		for ( LEMModel lemModel : lemModels )
		{
			lemIds.add( lemModel.getId() );
		}
		for ( LEMModel lemModel : lemAllModels )
		{
			if ( !lemIds.contains( lemModel.getId() ) )
			{
				filteredLemModels.add( lemModel );
			}
		}

		AddLEMPopup addLEMPopup = new AddLEMPopup( vmModel, uiComponentsUtil, getUpdateIcommand(), filteredLemModels );
		addLEMPopup.open();
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
					materialDataTable.setShadow( 1 );
					materialDataTable.setHeight( "100%" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					lemLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "LEMS" );
					materialDataTable.getScaffolding().getInfoPanel().add( lemLabel );
					materialDataTable.getScaffolding().getToolPanel().add( addLEM );
					materialDataTable.getScaffolding().getToolPanel().add( expandVMBtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getColumnMenuIcon().setVisible( false );
					materialDataTable.setRowData( 0, lemModels );
					materialDataTable.addStyleName( resources.style().vmDetailsGridLastTd() );
				}

			}
		} );

		materialDataTable.addColumn( new WidgetColumn<LEMModel, MaterialLabel>()
		{
			@Override
			public String width()
			{
				return "35%";
			}

			@Override
			public Comparator< ? super RowComponent<LEMModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getLemName().toString().compareToIgnoreCase( o2.getData().getLemName().toString() );
			}

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
					}
				} );
				return lemNameLabel;
			}

		}, "Name" );

		materialDataTable.addColumn( new TextColumn<LEMModel>()
		{
			@Override
			public String width()
			{
				return "60%";
			}

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

		materialDataTable.addColumn( new WidgetColumn<LEMModel, MaterialButton>()
		{
			@Override
			public String width()
			{
				return "5%";
			}

			@Override
			public MaterialButton getValue( LEMModel object )
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
						deleteLEMBtnAction( object );

					}
				} );
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void generateData()
	{
		CommonServiceProvider.getCommonService().getLems( null, false, new ApplicationCallBack<List<LEMModel>>()
		{
			@Override
			public void onSuccess( List<LEMModel> result )
			{
				MaterialLoader.loading( true );
				lemAllModels.clear();
				lemAllModels.addAll( result );

				lemModels.clear();
				List<LEMModel> newlemModels = new ArrayList<LEMModel>();

				for ( LEMModel lemModel : result )
				{
					for ( String lemId : vmModel.getDisks() )
					{
						if ( lemModel.getId().equals( lemId ) )
						{
							newlemModels.add( lemModel );
							break;
						}
					}
				}
				lemModels.addAll( newlemModels );
				createTable();
				materialRow.add( materialDataTable );
				add( materialRow );
				MaterialLoader.loading( false );
			}
		} );
	}

	private void deleteLEMBtnAction( LEMModel lemModel )
	{
		UnAssisgnLEM unAssisgnLEM = new UnAssisgnLEM( uiComponentsUtil, getUpdateIcommand(), vmModel, lemModel );
		unAssisgnLEM.open();
	}

	public Icommand getUpdateIcommand()
	{
		Icommand icommand = new Icommand()
		{

			@Override
			public void execute()
			{
				MaterialLoader.loading( true );
				JSONObject queryObj = new JSONObject();
				queryObj.put( "name", RestCallUtil.getJSONString( vmModel.getName() ) );
				CommonServiceProvider.getCommonService().getVMsList( RestCallUtil.getQueryRequest( queryObj.toString() ), false, new ApplicationCallBack<List<VMModel>>()
				{
					@Override
					public void onSuccess( List<VMModel> result )
					{
						if ( !result.isEmpty() )
						{
							vmModel = result.get( 0 );
							CommonServiceProvider.getCommonService().getLems( null, false, new ApplicationCallBack<List<LEMModel>>()
							{
								@Override
								public void onSuccess( List<LEMModel> result )
								{
									MaterialLoader.loading( true );
									lemModels.clear();
									List<LEMModel> newlemModels = new ArrayList<LEMModel>();

									for ( LEMModel lemModel : result )
									{
										for ( String lemId : vmModel.getDisks() )
										{
											if ( lemModel.getId().equals( lemId ) )
											{
												newlemModels.add( lemModel );
												break;
											}
										}
									}
									lemModels.addAll( newlemModels );
									materialDataTable.setRowData( 0, lemModels );
									materialDataTable.getView().setRedraw( true );
									materialDataTable.getView().refresh();
									enableDisableExpandVmBtn( expandVMBtn );
									MaterialLoader.loading( false );
								}

								@Override
								public void onFailure( Throwable caught )
								{
									super.onFailureShowErrorPopup( caught, null );
								}
							} );

						}
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

	private void enableDisableExpandVmBtn( MaterialButton expandVMBtn )
	{
		if ( vmModel.getDisks() != null && !vmModel.getDisks().isEmpty() )
		{
			expandVMBtn.setEnabled( true );
		}
		else
		{
			expandVMBtn.setEnabled( false );
			expandVMBtn.setTooltip( "Attach LEM to expand VM" );
		}
	}

	private MaterialButton getExpandVMBtn()
	{
		MaterialButton expandVMBtn = new MaterialButton( "Expand" );
		expandVMBtn.addStyleName( resources.style().vm_expand_btn() );
		expandVMBtn.getElement().getStyle().setMarginRight( 1, Unit.PX );
		expandVMBtn.setTitle( "Expand LEM" );
		expandVMBtn.setIconPosition( IconPosition.RIGHT );
		expandVMBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{

				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().getCapacityChartData( new ApplicationCallBack<CapacityChartModel>()
				{

					@Override
					public void onSuccess( CapacityChartModel capacityChartModel )
					{
						MaterialLoader.loading( false );
						ExpandVMPopup expandVMPopup = new ExpandVMPopup( Converter.getFormatSize( capacityChartModel.getAvailableSize() ), capacityChartModel.getAvailableSize(), vmModel, lemModels, uiComponentsUtil, getVMDataUpdateCmd() );
						expandVMPopup.open();

					}
				} );

			}
		} );
		return expandVMBtn;
	}

	private IcommandWithData getVMDataUpdateCmd()
	{

		IcommandWithData icommand = new IcommandWithData()
		{

			@Override
			public void executeWithData( Object newSize )
			{
				// memoryLabel.setValue( Converter.getFormatSize( ( Double ) newSize ) );
				getUpdateIcommand().execute();

			}
		};
		return icommand;
	}
}
