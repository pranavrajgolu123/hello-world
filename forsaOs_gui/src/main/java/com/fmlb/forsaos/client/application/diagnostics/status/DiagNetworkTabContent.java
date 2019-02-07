package com.fmlb.forsaos.client.application.diagnostics.status;

import com.fmlb.forsaos.client.application.models.DiagNetEthToolScanModel;
import com.fmlb.forsaos.client.application.models.DiagNetworkModel;
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

public class DiagNetworkTabContent extends MaterialPanel
{
	private AppResources resources = GWT.create( AppResources.class );

	private DiagNetworkModel diagNetworkModel;

	private UIComponentsUtil uiComponentsUtil;

	public DiagNetworkTabContent( UIComponentsUtil uiComponentsUtil )
	{
		this.uiComponentsUtil = uiComponentsUtil;
	}

	private MaterialRow createEthToolInfoCollection( DiagNetEthToolScanModel diagNetEthToolScanModel )
	{

		MaterialRow detailHeaderPanel = new MaterialRow();
		detailHeaderPanel.addStyleName( resources.style().statusHeader() );
		detailHeaderPanel.setGrid( "s12" );
		detailHeaderPanel.addStyleName( "headerPenalRow" );

		MaterialLabel interfaceDevHeaderLbl = this.uiComponentsUtil.getLabel( diagNetEthToolScanModel.getInterface_dev(), "s4", "" );

		MaterialLabel macAddrHeaderLbl = this.uiComponentsUtil.getLabel( diagNetEthToolScanModel.getMac_address(), "s4", "" );
		macAddrHeaderLbl.addStyleName( resources.style().upsMacAddressName() );

		MaterialLabel totalErrorsHeaderLbl = this.uiComponentsUtil.getLabel( "Total Error : ", "" );
		totalErrorsHeaderLbl.addStyleName( resources.style().upsTotalTxt() );

		MaterialLink totalErrorsValLink = this.uiComponentsUtil.getMaterialLink( String.valueOf( diagNetEthToolScanModel.getEthtool_total_errors() ) );
		totalErrorsValLink.addStyleName( resources.style().upsTotalCount() );
		//totalErrorsValLink.setGrid( "s1" );

		totalErrorsValLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				if ( diagNetEthToolScanModel.getEthtool_total_errors() > 0 )
				{
					NetworkErrorPopup networkErrorPopup = new NetworkErrorPopup( uiComponentsUtil, diagNetEthToolScanModel.getEthtool_error() );
					networkErrorPopup.open();
				}
			}
		} );

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		collection1.addStyleName( "NVMEDetails" );
		MaterialCollectionItem collectionItem1 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Version", diagNetEthToolScanModel.getEthtool_devinfo().getVersion() );
		MaterialCollectionItem collectionItem2 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Driver", diagNetEthToolScanModel.getEthtool_devinfo().getDriver() );
		MaterialCollectionItem collectionItem3 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Bus", diagNetEthToolScanModel.getEthtool_devinfo().getBus_info() );
		MaterialCollectionItem collectionItem4 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Firmware Veriosn", diagNetEthToolScanModel.getEthtool_devinfo().getFirmware_version() );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		collection1.add( collectionItem3 );
		collection1.add( collectionItem4 );

		MaterialRow row = new MaterialRow();
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s12" );
		column1.addStyleName( resources.style().NVMEdetailRow() );
		column1.add( collection1 );

		detailHeaderPanel.add( interfaceDevHeaderLbl );
		detailHeaderPanel.add( macAddrHeaderLbl );
		detailHeaderPanel.add( totalErrorsHeaderLbl );
		detailHeaderPanel.add( totalErrorsValLink );
		row.add( detailHeaderPanel );
		row.add( column1 );
		row.addStyleName( "NVMERow" );
		return row;
	}

	public void updateData( DiagNetworkModel diagNetworkModel )
	{
		clear();
		if ( diagNetworkModel != null )
		{
			this.diagNetworkModel = diagNetworkModel;
			for ( DiagNetEthToolScanModel diagNetEthToolScanModel : this.diagNetworkModel.getEthtool_scan() )
			{
				add( createEthToolInfoCollection( diagNetEthToolScanModel ) );
			}
		}
		else
		{
			add( uiComponentsUtil.getLabel( "No Network device found.", "s12" ) );
		}
	}

}
