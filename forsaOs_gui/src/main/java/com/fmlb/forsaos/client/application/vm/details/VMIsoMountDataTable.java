package com.fmlb.forsaos.client.application.vm.details;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.ISOMountPathModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.application.vm.AttachISOPopup;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.fmlb.forsaos.shared.application.utility.ErrorCodes;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONObject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
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
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class VMIsoMountDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<ISOMountPathModel> materialDataTable;

	private MaterialLabel isoLabel;

	private MaterialButton addISO;

	MaterialSearch search;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private VMModel vmModel;

	private List<ISOMountPathModel> isoMountPathModels = new ArrayList<ISOMountPathModel>();
	
	AppResources resources = GWT.create( AppResources.class );

	public VMIsoMountDataTable( VMModel vmModel, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
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
		createAddISOBtn();
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

	private void createAddISOBtn()
	{
		addISO = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Add ISO" );
		addISO.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				addISOBtnAction();
			}
		} );
	}

	private void addISOBtnAction()
	{
		AttachISOPopup attachISOPopup = new AttachISOPopup( null, true, 0, null, vmModel, true, getUpdateIcommand() );
		attachISOPopup.open();
	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<ISOMountPathModel>();
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
					isoLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "ISO Mount Path" );
					materialDataTable.getScaffolding().getInfoPanel().add( isoLabel );
					materialDataTable.getScaffolding().getToolPanel().add( addISO );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getColumnMenuIcon().setVisible( false );
					materialDataTable.addStyleName( resources.style().vmDetailsGridLastTd() );
					updateISODataTable( true );
				}

			}
		} );

		materialDataTable.addColumn( new WidgetColumn<ISOMountPathModel, MaterialLabel>()
		{
			@Override
			public String width()
			{
				return "90%";
			}

			@Override
			public Comparator< ? super RowComponent<ISOMountPathModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getPath().toString().compareToIgnoreCase( o2.getData().getPath().toString() );
			}

			@Override
			public MaterialLabel getValue( ISOMountPathModel ISOMountPathModel )
			{
				MaterialLabel lemNameLabel = new MaterialLabel();
				lemNameLabel.setText( ISOMountPathModel.getPath() );
				lemNameLabel.setTitle( ISOMountPathModel.getPath() );
				return lemNameLabel;
			}

		}, "Name" );

		materialDataTable.addColumn( new WidgetColumn<ISOMountPathModel, MaterialButton>()
		{
			@Override
			public String width()
			{
				return "10%";
			}
			@Override
			public MaterialButton getValue( ISOMountPathModel object )
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
						deleteISOBtnAction( object );

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
		//Dev start
		MaterialLoader.loading( true );
		isoMountPathModels.clear();
		createTable();
		materialRow.add( materialDataTable );
		add( materialRow );
		MaterialLoader.loading( false );

	}

	private void deleteISOBtnAction( ISOMountPathModel isoMountPathModel )
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().attachOrDetachISO( vmModel, isoMountPathModel.getPath(), false, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				if ( result )
				{
					updateISODataTable( false );
				}
				else
				{
					super.onFailureShowErrorPopup( new FBException( "Unable to detach ISO to the VM.", ErrorCodes.GENERIC_ERROR ), null );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	public Icommand getUpdateIcommand()
	{
		Icommand icommand = new Icommand()
		{

			@Override
			public void execute()
			{
				updateISODataTable( false );
			}
		};
		return icommand;

	}

	private void updateISODataTable( boolean firstLoad )
	{
		isoMountPathModels.clear();

		if ( firstLoad )
		{
			for ( String cdrom : vmModel.getCdroms() )
			{
				ISOMountPathModel isoMountPathModel = new ISOMountPathModel( cdrom );
				isoMountPathModels.add( isoMountPathModel );
			}
			materialDataTable.setRowData( 0, isoMountPathModels );
			materialDataTable.getView().setRedraw( true );
			materialDataTable.getView().refresh();
			return;
		}

		JSONObject queryObj = new JSONObject();
		queryObj.put( "name", RestCallUtil.getJSONString( vmModel.getName() ) );
		CommonServiceProvider.getCommonService().getVMsList( RestCallUtil.getQueryRequest( queryObj.toString() ), true, new ApplicationCallBack<List<VMModel>>()
		{
			@Override
			public void onSuccess( List<VMModel> result )
			{
				if ( !result.isEmpty() )
				{
					vmModel = result.get( 0 );
					for ( String cdrom : vmModel.getCdroms() )
					{
						ISOMountPathModel isoMountPathModel = new ISOMountPathModel( cdrom );
						isoMountPathModels.add( isoMountPathModel );
					}
					materialDataTable.setRowData( 0, isoMountPathModels );
					materialDataTable.getView().setRedraw( true );
					materialDataTable.getView().refresh();
				}
			}
		} );
	}
}
