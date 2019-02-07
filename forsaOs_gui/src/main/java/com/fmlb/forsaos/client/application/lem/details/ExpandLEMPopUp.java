package com.fmlb.forsaos.client.application.lem.details;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.SizeBox;
import com.fmlb.forsaos.client.application.models.ExpandLEMModel;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class ExpandLEMPopUp extends CommonPopUp
{
	private SizeBox sizeBox;

	private IcommandWithData updateLEMDataCmd;

	private LEMModel lemModel;

	private UIComponentsUtil uiComponentsUtil;

	String formattedAvailableSize;

	private double availableSizeinBytes;

	AppResources resources = GWT.create( AppResources.class );

	public ExpandLEMPopUp( String formattedAvailableSize, double availableSizeinBytes, LEMModel lemModel, UIComponentsUtil uiComponentsUtil, IcommandWithData updateLEMDataCmd )
	{
		super( "Expand LEM", "", "Expand LEM", true );
		//super( "Expand LEM LEM", " ( Available space: " + formattedAvailableSize + " )", "Expand LEM LEM", true );
		LoggerUtil.log( "expand lem 1" );
		this.formattedAvailableSize = formattedAvailableSize;
		this.availableSizeinBytes = availableSizeinBytes;
		this.lemModel = lemModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateLEMDataCmd = updateLEMDataCmd;
		LoggerUtil.log( "expand lem 2" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow lemDataTableHdrRow = new MaterialRow();
		MaterialLabel avlCntrSpcHdr = this.uiComponentsUtil.getLabel( "Available Size", "s4" );
		MaterialLabel lemNameHdr = this.uiComponentsUtil.getLabel( "LEM Name", "s4" );
		MaterialLabel currentSizeHdr = this.uiComponentsUtil.getLabel( "Current Size", "s4" );

		MaterialRow lemDataTableValRow = new MaterialRow();
		//MaterialLabel avlCntrSpcVal = this.uiComponentsUtil.getLabel( "new " + this.lemModel.getAvlCntrSpace(), "s4" );
		MaterialLabel avlCntrSpcVal = this.uiComponentsUtil.getLabel( this.formattedAvailableSize, "s4" );

		MaterialLabel lemNameVal = this.uiComponentsUtil.getLabel( this.lemModel.getLemName(), "s4" );
		MaterialLabel currentSizeVal = this.uiComponentsUtil.getLabel( Converter.getFormatKiBSize( this.lemModel.getSize(), MemorySizeType.KiB.getValue() ), "s4" );

		MaterialRow newSize = new MaterialRow();
		sizeBox = new SizeBox( "Size", "", "s6", "s8", this.availableSizeinBytes );
		sizeBox.getMemoryUnitComboBox().setSelectedIndex( 1 );
		sizeBox.getMemoryUnitComboBox().getListbox().remove( 3 );
		newSize.add( sizeBox );
		newSize.addStyleName( "expand_lem_size_row" );

		lemDataTableHdrRow.add( avlCntrSpcHdr );
		lemDataTableHdrRow.add( lemNameHdr );
		lemDataTableHdrRow.add( currentSizeHdr );
		lemDataTableHdrRow.addStyleName( resources.style().popup_data_header() );
		lemDataTableHdrRow.addStyleName( "popup_data_header" );

		lemDataTableValRow.add( avlCntrSpcVal );
		lemDataTableValRow.add( lemNameVal );
		lemDataTableValRow.add( currentSizeVal );
		lemDataTableValRow.addStyleName( resources.style().popup_data_value() );
		lemDataTableValRow.addStyleName( "popup_data_header" );

		getBodyPanel().add( lemDataTableHdrRow );
		getBodyPanel().add( lemDataTableValRow );
		getBodyPanel().add( newSize );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				if ( validate() )
				{
					LoggerUtil.log( "expand lem" );
					ExpandLEMModel expandLEMModel = new ExpandLEMModel( lemModel.getLemName(), sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString(), Double.valueOf( sizeBox.getSizeBox().getValue().toString() ).longValue() );
					MaterialLoader.loading( true, getBodyPanel() );
					CommonServiceProvider.getCommonService().expandLEM( expandLEMModel, new ApplicationCallBack<Boolean>()
					{

						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							MaterialToast.fireToast( "LEM Expanded..!", "rounded" );
							if ( updateLEMDataCmd != null )
							{
								updateLEMDataCmd.executeWithData( Converter.converToBytes( sizeBox.getSizeBox().getValue(), sizeBox.getMemoryUnitComboBox().getSelectedValue().get( 0 ).toString() ) );
							}
						}

						@Override
						public void onFailure( Throwable caught )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							super.onFailureShowErrorPopup( caught, null );

						}
					} );
				}
			}
		} );
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( sizeBox.validate() )
		{
			valid = true;
		}
		else
		{
			sizeBox.validate();
		}
		return valid;

	}
}