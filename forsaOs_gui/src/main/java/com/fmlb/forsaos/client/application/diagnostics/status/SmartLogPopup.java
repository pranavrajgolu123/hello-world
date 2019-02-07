package com.fmlb.forsaos.client.application.diagnostics.status;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.DiagNVMEModel;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;

public class SmartLogPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private DiagNVMEModel diagNVMEModel;

	AppResources resources = GWT.create( AppResources.class );

	public SmartLogPopup( UIComponentsUtil uiComponentsUtil, DiagNVMEModel diagNVMEModel )
	{
		super( "Smart Log", "", "Close", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.diagNVMEModel = diagNVMEModel;
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialLabel nvmeInfoHeaderLbl = this.uiComponentsUtil.getLabel( "Smart Log", "s12", resources.style().statusHeader() );

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		collection1.addStyleName( "smartLogDetails" );
		MaterialCollectionItem collectionItem1 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Read Data", Converter.getFormatSize( diagNVMEModel.getNvmeSmartLogModel().getData_units_read() ) );
		MaterialCollectionItem collectionItem2 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Write Data", Converter.getFormatSize( diagNVMEModel.getNvmeSmartLogModel().getData_units_written() ) );
		MaterialCollectionItem collectionItem3 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Used Percentage", diagNVMEModel.getNvmeSmartLogModel().getPercent_used() + " %" );
		MaterialCollectionItem collectionItem4 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Temperature", diagNVMEModel.getNvmeSmartLogModel().getTemperature() + " K" );
		MaterialCollectionItem collectionItem5 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Media Errors", String.valueOf( diagNVMEModel.getNvmeSmartLogModel().getMedia_errors() ) );
		MaterialCollectionItem collectionItem6 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Power On", diagNVMEModel.getNvmeSmartLogModel().getPower_on_hours() + " Hours" );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		collection1.add( collectionItem3 );
		collection1.add( collectionItem4 );
		collection1.add( collectionItem5 );
		collection1.add( collectionItem6 );

		MaterialRow row = new MaterialRow();
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s12" );
		column1.addStyleName( resources.style().no_padding() );
		column1.add( collection1 );

		//row.add( nvmeInfoHeaderLbl );
		row.add( column1 );

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
