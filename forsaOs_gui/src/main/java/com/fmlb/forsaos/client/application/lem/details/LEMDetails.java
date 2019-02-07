package com.fmlb.forsaos.client.application.lem.details;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.lem.DeleteLEMPopUp;
import com.fmlb.forsaos.client.application.models.CapacityChartModel;
import com.fmlb.forsaos.client.application.models.CloneLEMModel;
import com.fmlb.forsaos.client.application.models.CloneLEMandAssisgnToVMmodel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.models.RTMModel;
import com.fmlb.forsaos.client.application.models.UpdateLEMNameModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class LEMDetails extends MaterialPanel
{
	MaterialTextBox cloneLEMname;

	MaterialIntegerBox cloneQuantity;

	private MaterialComboBox< ? > cloneAssignToVm;

	private LEMModel lemModel;

	private UIComponentsUtil uiComponentsUtil;

	private RTMModel rtmModel;

	private CurrentUser currentUser;

	AppResources resources = GWT.create( AppResources.class );

	private IcommandWithData navigateToLEMGrid;

	private List<VMModel> vmModels;

	private MaterialLabel sizeLabel;

	private MaterialButton expandlemBtn;

	public LEMDetails( LEMModel lemModel, UIComponentsUtil uiComponentsUtil, RTMModel rtmModel, CurrentUser currentUser, IcommandWithData navigateToLEMGrid )
	{
		this.lemModel = lemModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.rtmModel = rtmModel;
		this.currentUser = currentUser;
		this.navigateToLEMGrid = navigateToLEMGrid;
		// add(createDetailPanel());
		MaterialTextBox lemName = this.uiComponentsUtil.getDetailNameEdit( lemModel.getLemName() );
		lemName.addValidator( uiComponentsUtil.getTextBoxEmptyValidator() );
		lemName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		lemName.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				lemName.validate();
			}
		} );
		lemName.addValueChangeHandler( new ValueChangeHandler<String>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				if ( !lemName.validate() )
				{
					return;
				}
				UpdateLEMNameModel updateLEMNameModel = new UpdateLEMNameModel( lemModel.getLemName(), event.getValue(), lemModel.getId() );
				CommonServiceProvider.getCommonService().updateLEMName( updateLEMNameModel, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						if ( result )
						{
							MaterialToast.fireToast( "LEM name updated to " + event.getValue() + "..!", "rounded" );
						}
						else
						{
							MaterialToast.fireToast( "Unable to update LEM name..!", "rounded" );
						}

					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );
					};
				} );
			}
		} );

		add( lemName );
		add( createDetailHeaderPanel() );
		add( createCollection() );
		// Commented as feature unavailable
		add( createSnapShotPanel() );
		add( createCloneLemPanel() );
	}

	private MaterialRow createCollection()
	{

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );

		MaterialCollection collection2 = new MaterialCollection();
		collection2.addStyleName( resources.style().noMargin() );

		MaterialCollectionItem collectionItem1 = new MaterialCollectionItem();
		collectionItem1.add( this.uiComponentsUtil.getLabel( "Size", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary1 = new MaterialCollectionSecondary();

		sizeLabel = this.uiComponentsUtil.getLabel( Converter.getFormatKiBSize( this.lemModel.getSize(), MemorySizeType.KiB.getValue() ), "", resources.style().displayInline() );
		collectionSecondary1.add( sizeLabel );
		collectionItem1.add( collectionSecondary1 );

		MaterialCollectionItem collectionItem2 = new MaterialCollectionItem();
		collectionItem2.add( this.uiComponentsUtil.getLabel( "Parent", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary2 = new MaterialCollectionSecondary();
		collectionSecondary2.add( this.uiComponentsUtil.getLabel( this.rtmModel.getName() + "", "", resources.style().displayInline() ) );
		collectionItem2.add( collectionSecondary2 );

		MaterialCollectionItem collectionItem3 = new MaterialCollectionItem();
		collectionItem3.add( this.uiComponentsUtil.getLabel( "LEM Type", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary3 = new MaterialCollectionSecondary();
		collectionSecondary3.add( this.uiComponentsUtil.getLabel( this.lemModel.getOsFl().getValue() + "", "", resources.style().displayInline() ) );
		collectionItem3.add( collectionSecondary3 );

		/*MaterialCollectionItem collectionItem4 = new MaterialCollectionItem();
		collectionItem4.add( this.uiComponentsUtil.getLabel( "Sector Size", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary4 = new MaterialCollectionSecondary();
		collectionSecondary4.add( this.uiComponentsUtil.getLabel( this.lemModel.getSectorSize() + "", "", resources.style().displayInline() ) );
		collectionItem4.add( collectionSecondary4 );*/

		MaterialCollectionItem collectionItem5 = new MaterialCollectionItem();
		collectionItem5.add( this.uiComponentsUtil.getLabel( "DNA Amplification", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary5 = new MaterialCollectionSecondary();
		collectionSecondary5.add( this.uiComponentsUtil.getLabel( this.lemModel.getDnaAmplificationPercent() + "%", "", resources.style().displayInline() ) );
		collectionItem5.add( collectionSecondary5 );

		MaterialCollectionItem collectionItem6 = new MaterialCollectionItem();
		collectionItem6.add( this.uiComponentsUtil.getLabel( "Mode", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary6 = new MaterialCollectionSecondary();
		collectionSecondary6.add( this.uiComponentsUtil.getLabel( this.lemModel.getMode() + " ", "", resources.style().displayInline() ) );
		collectionItem6.add( collectionSecondary6 );
		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		// Commented as feature unavailable
		//collection1.add( collectionItem3 );
		//		collection2.add( collectionItem4 );
		// Commented as feature unavailable
		//collection2.add( collectionItem5 );
		collection2.add( collectionItem6 );

		MaterialRow row = new MaterialRow();
		row.addStyleName( resources.style().lem_detail_row() );
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s6" );
		column1.addStyleName( resources.style().lem_detail_column() );
		column1.add( collection1 );

		MaterialColumn column2 = new MaterialColumn();
		column2.addStyleName( resources.style().lem_detail_column() );
		column2.setGrid( "s6" );
		column2.add( collection2 );
		row.add( column1 );
		row.add( column2 );
		return row;
	}

	private MaterialRow createDetailHeaderPanel()
	{
		MaterialRow detailHeaderPanel = new MaterialRow();
		detailHeaderPanel.addStyleName( resources.style().lemDetail() );
		detailHeaderPanel.setId( "lemdetailsChartButton" + Document.get().createUniqueId() );
		detailHeaderPanel.setGrid( "s12" );
		detailHeaderPanel.addStyleName( "detail_header_penal" );

		MaterialLabel detailLabel = this.uiComponentsUtil.getLabel( "Detail", "s12"/* , resources.style().lemDetail() */ );
		detailLabel.addStyleName( resources.style().detail_header() );
		detailLabel.setBorder( "2" );
		detailLabel.setGrid( "s6" );

		MaterialColumn actionItemCol = new MaterialColumn();
		actionItemCol.setGrid( "s4" );
		actionItemCol.addStyleName( resources.style().action_item_bar() );
		MaterialLabel switchLabel = this.uiComponentsUtil.getLabel( "Status", "" );
		switchLabel.setGrid( "s2" );
		switchLabel.addStyleName( resources.style().action_status() );
		MaterialSwitch statusSwitch = new MaterialSwitch();
		statusSwitch.setGrid( "s2" );
		statusSwitch.addStyleName( resources.style().action_status_switch() );
		// Commented as feature unavailable
		//actionItemCol.add( switchLabel );
		//actionItemCol.add( statusSwitch );
		statusSwitch.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				changeLEMStaus( event.getValue() );

			}
		} );
		expandlemBtn = new MaterialButton( "Expand" );
		expandlemBtn.addStyleName( resources.style().vm_expand_btn() );
		expandlemBtn.setTitle( "Expand LEM" );
		expandlemBtn.setIconPosition( IconPosition.RIGHT );
		expandlemBtn.addClickHandler( new ClickHandler()
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
						ExpandLEMPopUp expandLEMPopUp = new ExpandLEMPopUp( Converter.getFormatSize( capacityChartModel.getAvailableSize() ), capacityChartModel.getAvailableSize(), lemModel, uiComponentsUtil, getLemDataUpdateCmd() );
						expandLEMPopUp.open();

					}
				} );

			}
		} );

		// Commented this as fix for Bug
		//V410-476
		//EXPAND LEM option not working in GUI.
		/*if ( lemModel.getVMname().trim().equalsIgnoreCase( "" ) )
		{
			expandlemBtn.setEnabled( true );
		}
		else
		{
			expandlemBtn.setEnabled( false );
		}*/
		MaterialButton deletelemBtn = new MaterialButton( "", IconType.DELETE, ButtonType.FLAT );
		deletelemBtn.setTitle( "Delete LEM" );
		deletelemBtn.addStyleName( resources.style().lem_delete_btn() );
		deletelemBtn.setIconPosition( IconPosition.RIGHT );
		deletelemBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				/*
				 * DestroyLEMPopUp destroyLEMPopUp = new DestroyLEMPopUp(lemModel,
				 * uiComponentsUtil); destroyLEMPopUp.open();
				 */
				DeleteLEMPopUp deleteLEMPopUp = new DeleteLEMPopUp( lemModel, uiComponentsUtil, navigateToLEMGrid, currentUser );
				deleteLEMPopUp.open();

			}
		} );
		// Commented as feature unavailable
		actionItemCol.add( expandlemBtn );
		actionItemCol.add( deletelemBtn );
		detailHeaderPanel.add( detailLabel );
		detailHeaderPanel.add( actionItemCol );
		return detailHeaderPanel;
	}

	private MaterialPanel createSnapShotPanel()
	{
		LEMSnapshotDataTable lemSnapshotTable = new LEMSnapshotDataTable( lemModel, uiComponentsUtil, currentUser );
		lemSnapshotTable.getUpdateIcommandWithData().executeWithData( false );
		return lemSnapshotTable;
	}

	private MaterialPanel createCloneLemPanel()
	{

		MaterialPanel cloneLEMPanel = new MaterialPanel();
		MaterialLabel cloneLabel = this.uiComponentsUtil.getLabel( "Clone LEM", "s12", resources.style().clone_Detail() );

		MaterialRow cloneRow = new MaterialRow();
		cloneRow.addStyleName( resources.style().data_body_row() );
		MaterialButton cloneLEMBtn = new MaterialButton( "Clone LEM" );
		cloneLEMBtn.addStyleName( resources.style().clone_lem_btn() );
		//cloneLEMBtn.setType( ButtonType.FLAT );
		cloneLEMBtn.setIconPosition( IconPosition.RIGHT );
		cloneLEMBtn.setIconType( IconType.CONTENT_COPY );
		cloneLEMBtn.setGrid( "s2" );
		cloneLEMname = uiComponentsUtil.getTexBox( "Clone LEM", "Type Clone name", "s5" );
		cloneLEMname.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		cloneQuantity = uiComponentsUtil.getIntegerBox( "Quantity", "", "s2" );
		cloneQuantity.setValue( 1 );
		cloneQuantity.setMin( "1" );
		cloneRow.add( cloneLEMname );
		CommonServiceProvider.getCommonService().getVMsList( null, false, new ApplicationCallBack<List<VMModel>>()
		{
			@Override
			public void onSuccess( List<VMModel> vmModelsRes )
			{
				vmModels = vmModelsRes;
				cloneAssignToVm = uiComponentsUtil.getAssisgnToVMDropDown( vmModels, "Assign to VM", "s3" );
				cloneRow.add( cloneQuantity );
				cloneRow.add( cloneAssignToVm );
				cloneRow.add( cloneLEMBtn );
				cloneLEMPanel.add( cloneLabel );
				cloneLEMPanel.add( cloneRow );
			}
		} );

		cloneLEMBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				if ( validateCloneLEM() )
				{

					CloneLEMModel cloneLEMModel = new CloneLEMModel();
					cloneLEMModel.setClone_name( cloneLEMname.getValue() );
					cloneLEMModel.setInstances( cloneQuantity.getValue() );
					cloneLEMModel.setName( lemModel.getLemName() );
					List<VMModel> vmModels = ( List<VMModel> ) cloneAssignToVm.getValue();
					VMModel selcted = vmModels.get( 0 );
					if ( !selcted.getVmName().equalsIgnoreCase( "None" ) )
					{
						cloneLemAndAssisnToVm( cloneLEMModel, selcted );
					}
					else
					{
						cloneLEM( cloneLEMModel );
					}
				}

			}

		} );
		return cloneLEMPanel;
	}

	private void cloneLemAndAssisnToVm( CloneLEMModel cloneLEMModel, VMModel selcted )
	{
		List<CloneLEMModel> cloneLEMModelsList = new ArrayList<CloneLEMModel>();
		cloneLEMModelsList.add( cloneLEMModel );
		CloneLEMandAssisgnToVMmodel cloneLEMandAssisgnToVMmodel = new CloneLEMandAssisgnToVMmodel( cloneLEMModelsList, selcted.getVmName(), selcted.get_id().get$oid() );
		CommonServiceProvider.getCommonService().cloneLemAndAssisnToVm( cloneLEMandAssisgnToVMmodel, new ApplicationCallBack<Boolean>()
		{

			@Override
			public void onSuccess( Boolean result )
			{

				onCloneSucess();

			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );

			}
		} );
	}

	private void onCloneSucess()
	{
		MaterialLoader.loading( false );
		cloneLEMname.clear();
		cloneQuantity.setValue( 1 );
		cloneAssignToVm.setSelectedIndex( 0 );
		MaterialToast.fireToast( "Clones(s) Created..!", "rounded" );
	}

	private void cloneLEM( CloneLEMModel cloneLEMModel )
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().cloneLEM( cloneLEMModel, new ApplicationCallBack<Boolean>()
		{

			@Override
			public void onSuccess( Boolean result )
			{
				onCloneSucess();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );

			}
		} );
	}

	private void changeLEMStaus( boolean lemStatus )
	{
		if ( lemStatus )
		{
			LoggerUtil.log( "change LEM Status to online" );
		}
		else
		{
			LoggerUtil.log( "change LEM Status to offline" );
		}
	}

	public boolean validateCloneLEM()
	{
		boolean valid = false;
		if ( cloneLEMname.validate() && cloneQuantity.validate() )
		{
			valid = true;
		}
		else
		{
			cloneLEMname.validate();
			cloneQuantity.validate();
		}
		return valid;

	}

	private IcommandWithData getLemDataUpdateCmd()
	{

		IcommandWithData icommand = new IcommandWithData()
		{

			@Override
			public void executeWithData( Object newSize )
			{
				sizeLabel.setValue( Converter.getFormatSize( ( ( Double ) newSize ).longValue() ) );

			}
		};
		return icommand;
	}
}
