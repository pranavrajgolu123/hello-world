package com.fmlb.forsaos.client.application.diagnostics.status;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.DiagEthToolErrorModel;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;

public class NetworkErrorPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private DiagEthToolErrorModel diagEthToolErrorModel;

	AppResources resources = GWT.create( AppResources.class );

	public NetworkErrorPopup( UIComponentsUtil uiComponentsUtil, DiagEthToolErrorModel diagEthToolErrorModel )
	{
		super( "Errors", "", "Close", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.diagEthToolErrorModel = diagEthToolErrorModel;
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s6" );
		column1.addStyleName( resources.style().rtm_detail_list() );

		MaterialLabel receivedHeaderLbl = this.uiComponentsUtil.getLabel( "Received", "s12", resources.style().vm_setting_header() );

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		MaterialCollectionItem collectionItem1 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "CRC", this.diagEthToolErrorModel.getRx_crc_errors() );
		MaterialCollectionItem collectionItem2 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Missed", this.diagEthToolErrorModel.getRx_missed_errors() );
		MaterialCollectionItem collectionItem3 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Long Length", this.diagEthToolErrorModel.getRx_long_length_errors() );
		MaterialCollectionItem collectionItem4 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Short Length", this.diagEthToolErrorModel.getRx_short_length_errors() );
		MaterialCollectionItem collectionItem5 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Align", this.diagEthToolErrorModel.getRx_align_errors() );
		MaterialCollectionItem collectionItem6 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Errors", this.diagEthToolErrorModel.getRx_errors() );
		MaterialCollectionItem collectionItem7 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Length", this.diagEthToolErrorModel.getRx_length_errors() );
		MaterialCollectionItem collectionItem8 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Over", this.diagEthToolErrorModel.getRx_over_errors() );
		MaterialCollectionItem collectionItem9 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Frame", this.diagEthToolErrorModel.getRx_frame_errors() );
		MaterialCollectionItem collectionItem10 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "FIFO", this.diagEthToolErrorModel.getRx_fifo_errors() );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		collection1.add( collectionItem3 );
		collection1.add( collectionItem4 );
		collection1.add( collectionItem5 );
		collection1.add( collectionItem6 );
		collection1.add( collectionItem7 );
		collection1.add( collectionItem8 );
		collection1.add( collectionItem9 );
		collection1.add( collectionItem10 );

		column1.add( receivedHeaderLbl );
		column1.add( collection1 );

		MaterialColumn column2 = new MaterialColumn();
		column2.setGrid( "s6" );
		column2.addStyleName( resources.style().rtm_detail_list() );

		MaterialLabel transferredHeaderLbl = this.uiComponentsUtil.getLabel( "Transferred", "s12", resources.style().vm_setting_header() );

		MaterialCollection collection2 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		MaterialCollectionItem collectionItem2_1 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Aborted", this.diagEthToolErrorModel.getTx_aborted_errors() );
		MaterialCollectionItem collectionItem2_2 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Carrier", this.diagEthToolErrorModel.getTx_carrier_errors() );
		MaterialCollectionItem collectionItem2_3 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Window", this.diagEthToolErrorModel.getTx_window_errors() );
		MaterialCollectionItem collectionItem2_4 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Errors", this.diagEthToolErrorModel.getTx_errors() );
		MaterialCollectionItem collectionItem2_5 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "FIFO", this.diagEthToolErrorModel.getTx_fifo_errors() );
		MaterialCollectionItem collectionItem2_6 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "Heartbeat", this.diagEthToolErrorModel.getTx_heartbeat_errors() );

		collection2.add( collectionItem2_1 );
		collection2.add( collectionItem2_2 );
		collection2.add( collectionItem2_3 );
		collection2.add( collectionItem2_4 );
		collection2.add( collectionItem2_5 );
		collection2.add( collectionItem2_6 );

		column2.add( transferredHeaderLbl );
		column1.add( collection1 );
		column2.add( collection2 );

		MaterialRow row = new MaterialRow();

		row.add( column1 );
		row.add( column2 );

		getBodyPanel().add( row );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				close();
			}
		} );
	}

}
