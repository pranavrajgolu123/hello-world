package com.fmlb.forsaos.client.application.management.blink.details;

import java.util.ArrayList;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.management.blink.RestoreBlinkPopup;
import com.fmlb.forsaos.client.application.models.BlinkModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.DeleteBlinkModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class BlinkDetails extends MaterialPanel
{

	private BlinkModel blinkModel;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	AppResources resources = GWT.create( AppResources.class );

	private IcommandWithData navigateToBlinkGrid;

	//	private MaterialLabel nameLable;

	private MaterialLabel timeLable;

	private MaterialLabel pathLable;

	private ConfirmPasswordPopup delteBlinkNameConfirmPasswordPopup;

	public BlinkDetails( BlinkModel blinkModel, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData navigateToBlinkGrid )
	{
		this.blinkModel = blinkModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigateToBlinkGrid = navigateToBlinkGrid;

		initialize();
	}

	private void initialize()
	{
		add( createBlinkPanel() );
	}

	private MaterialPanel createBlinkPanel()
	{
		MaterialPanel blinkDetailsPanel = new MaterialPanel();
		MaterialLabel blinkDetailsLabel = this.uiComponentsUtil.getLabel( this.blinkModel.getName(), "s12", resources.style().vm_setting_header() );
		blinkDetailsPanel.add( blinkDetailsLabel );
		blinkDetailsLabel.addStyleName( resources.style().blinkDetailsPanel() );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( resources.style().vm_setting_row() );

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );

		MaterialCollection collection2 = new MaterialCollection();
		collection2.addStyleName( resources.style().noMargin() );

		/*MaterialCollectionItem collectionItem1 = new MaterialCollectionItem();
		collectionItem1.add( this.uiComponentsUtil.getLabel( "Blink Name  :", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary1 = new MaterialCollectionSecondary();
		
		nameLable = this.uiComponentsUtil.getLabel( this.blinkModel.getName(), "", resources.style().displayInline() );
		collectionSecondary1.add( nameLable );
		collectionItem1.add( collectionSecondary1 );*/

		MaterialCollectionItem collectionItem2 = new MaterialCollectionItem();
		collectionItem2.add( this.uiComponentsUtil.getLabel( "Date Created", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary2 = new MaterialCollectionSecondary();

		timeLable = this.uiComponentsUtil.getLabel( this.blinkModel.getDate(), "", resources.style().displayInline() );
		collectionSecondary2.add( timeLable );
		collectionItem2.add( collectionSecondary2 );

		MaterialCollectionItem collectionItem3 = new MaterialCollectionItem();
		collectionItem3.add( this.uiComponentsUtil.getLabel( "Path", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary3 = new MaterialCollectionSecondary();

		pathLable = this.uiComponentsUtil.getLabel( this.blinkModel.getStore_path(), "", resources.style().displayInline() );
		collectionSecondary3.add( pathLable );
		collectionItem3.add( collectionSecondary3 );

		//		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		collection2.add( collectionItem3 );

		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s6" );
		column1.addStyleName( resources.style().lem_detail_column() );
		column1.add( collection1 );

		MaterialColumn column2 = new MaterialColumn();
		column2.addStyleName( resources.style().lem_detail_column() );
		column2.setGrid( "s6" );

		MaterialButton restoreBtn = new MaterialButton();
		restoreBtn.setTitle( "Restore Blink" );
		restoreBtn.setText( "Restore" );
		restoreBtn.setFloat( Float.RIGHT );
		restoreBtn.setIconPosition( IconPosition.RIGHT );
		restoreBtn.addStyleName( resources.style().restoreBtn() );
		restoreBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				RestoreBlinkPopup restoreBlink = new RestoreBlinkPopup( blinkModel, uiComponentsUtil );
				restoreBlink.open();

			}
		} );

		column2.add( collection2 );

		firstRow.add( column1 );
		firstRow.add( column2 );

		MaterialButton deleteBtn = new MaterialButton();
		deleteBtn.setTitle( "Delete" );
		deleteBtn.addStyleName( resources.style().lem_delete_btn() );
		deleteBtn.setFloat( Float.RIGHT );
		deleteBtn.setIconType( IconType.DELETE );
		deleteBtn.setIconPosition( IconPosition.RIGHT );
		deleteBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				ArrayList<String> warningMsgList = new ArrayList<String>();
				warningMsgList.add( "Do you want to delete blink " + blinkModel.getName() + " ?" );
				delteBlinkNameConfirmPasswordPopup = new ConfirmPasswordPopup( "Delete Blink", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
				{
					@Override
					public void execute()
					{
						LoggerUtil.log( "Delete Blink" );
						MaterialLoader.loading( true, delteBlinkNameConfirmPasswordPopup.getBodyPanel() );
						DeleteBlinkModel deleteBlinkModel = new DeleteBlinkModel( blinkModel.getName() );
						CommonServiceProvider.getCommonService().deleteBlink( deleteBlinkModel, new ApplicationCallBack<Boolean>()
						{
							@Override
							public void onSuccess( Boolean result )
							{
								MaterialLoader.loading( false, delteBlinkNameConfirmPasswordPopup.getBodyPanel() );
								delteBlinkNameConfirmPasswordPopup.close();
								MaterialToast.fireToast( deleteBlinkModel.getName() + " Deleted..!", "rounded" );
								navigateToBlinkGrid.executeWithData( true );
							}

							@Override
							public void onFailure( Throwable caught )
							{
								super.onFailureShowErrorPopup( caught, null );

							}
						} );

					}
				} );
				delteBlinkNameConfirmPasswordPopup.open();
			}
		} );

		blinkDetailsLabel.add( deleteBtn );
		blinkDetailsLabel.add( restoreBtn );

		blinkDetailsPanel.add( firstRow );
		blinkDetailsPanel.addStyleName( "blinkPanel" );

		return blinkDetailsPanel;
	}
}