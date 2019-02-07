package com.fmlb.forsaos.client.application.diagnostics.status;

import java.util.List;

import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.models.DiagNVMEInfoModel;
import com.fmlb.forsaos.client.application.models.DiagNVMEModel;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class DiagNVMETabContent extends MaterialPanel
{
	private AppResources resources = GWT.create( AppResources.class );

	private List<DiagNVMEModel> diagNVMEModelList;

	private UIComponentsUtil uiComponentsUtil;

	public DiagNVMETabContent( UIComponentsUtil uiComponentsUtil )
	{
		this.uiComponentsUtil = uiComponentsUtil;
	}

	private MaterialRow createNVMEInfoCollection( DiagNVMEModel diagNVMEModel )
	{
		DiagNVMEInfoModel diagNVMEInfoModel = diagNVMEModel.getNvmeInfoModel();
		String productName = "";
		if ( diagNVMEInfoModel.getDevicePath().contains( "/" ) )
		{
			String[] splitTokens = diagNVMEInfoModel.getDevicePath().split( "/" );
			productName = splitTokens[splitTokens.length - 1];
		}
		final String nvmeName = productName;

		MaterialRow detailHeaderPanel = new MaterialRow();
		detailHeaderPanel.addStyleName( resources.style().lemDetail() );
		detailHeaderPanel.setGrid( "s12" );
		detailHeaderPanel.addStyleName( "NVMEdetailHeaderPenal" );

		MaterialLabel nvmeInfoHeaderLbl = this.uiComponentsUtil.getLabel( productName, "s8", "" );

		MaterialLink idCntrlHeaderLink = this.uiComponentsUtil.getMaterialLink( "Properties" );
		idCntrlHeaderLink.setGrid( "s1" );
		idCntrlHeaderLink.addStyleName( resources.style().idControlBtn() );
		idCntrlHeaderLink.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				IdCntrlPopup idCntrlPopup = new IdCntrlPopup( uiComponentsUtil, diagNVMEModel, nvmeName );
				idCntrlPopup.open();

			}
		} );

		MaterialLink smartLogInfoHeaderLink = this.uiComponentsUtil.getMaterialLink( "Smart Log" );
		smartLogInfoHeaderLink.setGrid( "s1" );
		smartLogInfoHeaderLink.addStyleName( resources.style().smartLogBtn() );

		smartLogInfoHeaderLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				SmartLogPopup smartLogPopup = new SmartLogPopup( uiComponentsUtil, diagNVMEModel );
				smartLogPopup.open();

			}
		} );
		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		collection1.addStyleName( "NVMEDetails" );
		MaterialCollectionItem collectionItem1 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Model Number", diagNVMEInfoModel.getModelNumber() );
		MaterialCollectionItem collectionItem2 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Physical Size", Converter.getFormatSize( diagNVMEInfoModel.getPhysicalSize() ) );
		MaterialCollectionItem collectionItem3 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Serial Number", diagNVMEInfoModel.getSerialNumber() );
		MaterialCollectionItem collectionItem4 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Used", Converter.getFormatSize( diagNVMEInfoModel.getUsedBytes() ) );
		MaterialCollectionItem collectionItem5 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Device Path", diagNVMEInfoModel.getDevicePath() );
		MaterialCollectionItem collectionItem6 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Maximium LBA", String.valueOf( diagNVMEInfoModel.getMaximumLBA() ) );
		MaterialCollectionItem collectionItem7 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Firmware", diagNVMEInfoModel.getFirmware() );
		MaterialCollectionItem collectionItem8 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Sector Size", String.valueOf( diagNVMEInfoModel.getSectorSize() ) );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		collection1.add( collectionItem3 );
		collection1.add( collectionItem4 );
		collection1.add( collectionItem5 );
		collection1.add( collectionItem6 );
		collection1.add( collectionItem7 );
		collection1.add( collectionItem8 );

		MaterialRow row = new MaterialRow();
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s12" );
		column1.addStyleName( resources.style().NVMEdetailRow() );
		column1.add( collection1 );

		detailHeaderPanel.add( nvmeInfoHeaderLbl );
		detailHeaderPanel.add( idCntrlHeaderLink );
		detailHeaderPanel.add( smartLogInfoHeaderLink );
		row.add( detailHeaderPanel );
		row.add( column1 );
		row.addStyleName( "NVMERow" );
		return row;
	}

	public void updateData( List<DiagNVMEModel> diagNVMEModelList )
	{
		clear();
		if ( diagNVMEModelList != null && !diagNVMEModelList.isEmpty() )
		{
			this.diagNVMEModelList = diagNVMEModelList;
			for ( DiagNVMEModel diagNVMEModel : this.diagNVMEModelList )
			{
				add( createNVMEInfoCollection( diagNVMEModel ) );
			}
		}
		else
		{
			add( uiComponentsUtil.getLabel( "No NVME device found.", "s12" ) );
		}
	}

}
