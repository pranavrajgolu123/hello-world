package com.fmlb.forsaos.client.application.diagnostics.status;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
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

public class IdCntrlPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private DiagNVMEModel diagNVMEModel;

	private String nvmeName;

	AppResources resources = GWT.create( AppResources.class );

	public IdCntrlPopup( UIComponentsUtil uiComponentsUtil, DiagNVMEModel diagNVMEModel, String nvmeName )
	{
		super( nvmeName + ": Properties ", "", "Close", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.diagNVMEModel = diagNVMEModel;
		this.nvmeName = nvmeName;
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialLabel nvmeInfoHeaderLbl = this.uiComponentsUtil.getLabel( "Id Control", "s12", resources.style().statusHeader() );

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		collection1.addStyleName( "smartLogDetails" );
		MaterialCollectionItem collectionItem1 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "PCI vendor ID", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getVid() ) );
		MaterialCollectionItem collectionItem2 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "PCI Subsystem Vendor ID", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getSsvid() ) );
		MaterialCollectionItem collectionItem3 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "IEEE OUI Identifier", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getIeee() ) );
		MaterialCollectionItem collectionItem4 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Maximum Data Transfer Size", String.valueOf( Math.pow( 2, diagNVMEModel.getNvmeIdControlModel().getMdts() ) ) );
		MaterialCollectionItem collectionItem5 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Controller ID", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getCntlid() ) );
		MaterialCollectionItem collectionItem6 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Version", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getVer() ) );
		MaterialCollectionItem collectionItem7 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Warning temperature", diagNVMEModel.getNvmeIdControlModel().getWctemp() + " K" );
		MaterialCollectionItem collectionItem8 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Critical temperature", diagNVMEModel.getNvmeIdControlModel().getCctemp() + " K" );
		//MaterialCollectionItem collectionItem9 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Total NVM Capacity", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getTnvmcap() ) );
		MaterialCollectionItem collectionItem10 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Submission Queue Entry (SQE) Size", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getSqes() ) );
		MaterialCollectionItem collectionItem11 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Completion Queue Entry (CQE) Size", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getCqes() ) );
		MaterialCollectionItem collectionItem12 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Volatile Write Cache", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getVwc() ) );
		MaterialCollectionItem collectionItem13 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "NVM Vendor Specific Commands", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getNvscc() ) );
		//MaterialCollectionItem collectionItem14 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Firmware Updates", String.valueOf( diagNVMEModel.getNvmeIdControlModel().getFrmw() ) );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		collection1.add( collectionItem3 );
		collection1.add( collectionItem4 );
		collection1.add( collectionItem5 );
		collection1.add( collectionItem6 );
		collection1.add( collectionItem7 );
		collection1.add( collectionItem8 );
		//collection1.add( collectionItem9 );
		collection1.add( collectionItem10 );
		collection1.add( collectionItem11 );
		collection1.add( collectionItem12 );
		collection1.add( collectionItem13 );
		//collection1.add( collectionItem14 );

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
