package com.fmlb.forsaos.client.application.settings.system;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.LicenseFeaturesModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
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

public class LicenseFeatureDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<LicenseFeaturesModel> materialDataTable;

	private List<LicenseFeaturesModel> licenseFeaturesModels = new ArrayList<>();

	private MaterialLabel licenseLabel;

	private MaterialLabel totalLicenseLabel;

	private int totalLicenses;

	private MaterialButton createEnableFeatureBtn;

	MaterialSearch search;

	public LicenseFeatureDataTable( UIComponentsUtil uiComponentsUtil )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createCreateEnableFeatureBtn();
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

	private void createCreateEnableFeatureBtn()
	{
		createEnableFeatureBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Enable Feature" );
		createEnableFeatureBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createEnableFeatureBtnAction();

			}
		} );
	}

	private void createEnableFeatureBtnAction()
	{
		CreateEnableFeaturePopup createEnableFeaturePopup = new CreateEnableFeaturePopup( this.uiComponentsUtil, getUpdateIcommandWithData() );
		createEnableFeaturePopup.open();
	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<LicenseFeaturesModel>();
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
					materialDataTable.setHeight( "calc(30vh - 20px)" );
					// materialDataTable.setTitle("LEMS");
					// materialDataTable.addStyleName("materialDataTableTabWorkStep");
					// materialDataTable.setMarginTop(64.00);
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					licenseLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "License Features" );
					totalLicenseLabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalLicenses + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( licenseLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalLicenseLabel );
					materialDataTable.getScaffolding().getToolPanel().add( createEnableFeatureBtn );
					// materialDataTable.getScaffolding().getToolPanel().add(search);
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					// materialDataTable.getScaffolding().getTopPanel().add(search);

					materialDataTable.getColumnMenuIcon().setVisible( false );

				}

			}
		} );

		materialDataTable.addColumn( new TextColumn<LicenseFeaturesModel>()
		{
			@Override
			public Comparator< ? super RowComponent<LicenseFeaturesModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getFeature().compareToIgnoreCase( o2.getData().getFeature() );
			}

			@Override
			public String getValue( LicenseFeaturesModel object )
			{
				return object.getFeature();
			}
		}, "Feature" );

		materialDataTable.addColumn( new TextColumn<LicenseFeaturesModel>()
		{
			@Override
			public Comparator< ? super RowComponent<LicenseFeaturesModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getStatus().compareToIgnoreCase( o2.getData().getStatus() );
			}

			@Override
			public String getValue( LicenseFeaturesModel object )
			{
				return object.getStatus();
			}
		}, "Status" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void generateData()
	{
		List<LicenseFeaturesModel> result = new ArrayList<LicenseFeaturesModel>();
		totalLicenses = result.size();
		createTable();
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

				// Fire API to get license details
				List<LicenseFeaturesModel> featuresModels = new ArrayList<>();
				LicenseFeaturesModel licenseFeaturesModel1 = new LicenseFeaturesModel( "33rkaufkjasfkass", "Enabled" );
				LicenseFeaturesModel licenseFeaturesModel2 = new LicenseFeaturesModel( "33rkaufkjasfkass", "Disabled" );
				featuresModels.add( licenseFeaturesModel1 );
				featuresModels.add( licenseFeaturesModel2 );
				// on data receive
				MaterialLoader.loading( false );
				licenseFeaturesModels.clear();
				licenseFeaturesModels.addAll( licenseFeaturesModels );
				materialDataTable.setRowData( 0, featuresModels );
				materialDataTable.getView().setRedraw( true );
				materialDataTable.getView().refresh();
				MaterialLoader.loading( false );
			}
		};

		return icommand;

	}

}
