package com.fmlb.forsaos.client.application.rtm;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.lem.details.LEMDetailsChart;
import com.fmlb.forsaos.client.application.models.CapacityChartModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.RTMModel;
import com.fmlb.forsaos.client.application.models.UpdateRTMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ErrorMessages;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import gwt.material.design.client.constants.ButtonType;
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
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

@SuppressWarnings( "deprecation" )
public class RTMPresenter extends Presenter<RTMPresenter.MyView, RTMPresenter.MyProxy> implements RTMUiHandlers
{
	interface MyView extends View, HasUiHandlers<RTMUiHandlers>
	{
		public MaterialRow getRTMView();
	}

	@NameToken( NameTokens.RTM )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<RTMPresenter>
	{
	}

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	JavaScriptObject lemDetailChart = null;

	AppResources resources = GWT.create( AppResources.class );

	private Icommand initializeRTMCmd;

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_RTM = new Type<RevealContentHandler< ? >>();

	@Override
	protected void onReveal()
	{
		super.onReveal();
		LoggerUtil.log( "on reveal rtm presenter" );
	}

	private void dataUpdate()
	{
		initializeRTMCmd.execute();
		BreadcrumbEvent.fire( RTMPresenter.this, "Virtualization >> RTM", NameTokens.RTM );
	}

	private void initializeRTMCmd()
	{
		initializeRTMCmd = new Icommand()
		{

			@Override
			public void execute()
			{
				isRTMInitialized();

			}
		};
	}

	private void isRTMInitialized()
	{
		getView().getRTMView().clear();
		CommonServiceProvider.getCommonService().getRTM( null, new ApplicationCallBack<RTMModel>()
		{

			@Override
			public void onSuccess( RTMModel result )
			{
				if ( result != null )
				{
					attachRTMDetailsPanel( result );
				}
				else
				{
					createRTMPanel();
				}
				MaterialLoader.loading( false );
			}
		} );

	}

	protected void createRTMPanel()
	{
		MaterialPanel createRTMPanel = new MaterialPanel();
		createRTMPanel.setGrid( "s12" );
		createRTMPanel.addStyleName( resources.style().create_rtm_panel() );
		MaterialTextBox rtmTxtBox = uiComponentsUtil.getTexBox( "", "RTM Name", "s10" );
		rtmTxtBox.addStyleName( resources.style().create_rtm_filed() );

		rtmTxtBox.addKeyUpHandler( new KeyUpHandler()
		{

			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				checkInputText( rtmTxtBox );

			}
		} );
		rtmTxtBox.addValueChangeHandler( new ValueChangeHandler<String>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				checkInputText( rtmTxtBox );

			}
		} );
		MaterialColumn buttonWrapper = new MaterialColumn();
		buttonWrapper.setGrid( "s2" );
		MaterialButton createLink = uiComponentsUtil.getDataTableCreateDataItemButton( "Create RTM" );
		createLink.addStyleName( resources.style().create_rtm_btn() );
		buttonWrapper.add( createLink );
		MaterialLabel errorLabel = new MaterialLabel( ErrorMessages.INPUT_RTM_NAME );
		errorLabel.addStyleName( resources.style().rtm_error_label() );
		errorLabel.setGrid( "s12" );
		errorLabel.setVisible( false );

		createLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				LoggerUtil.log( "create button clicked" );
				if ( rtmTxtBox.getText() != null && !rtmTxtBox.getText().isEmpty() )
				{
					if ( checkInputText( rtmTxtBox ) )
					{
						errorLabel.setVisible( false );
						createRTM( rtmTxtBox.getText(), errorLabel );
					}
					else
					{
						rtmTxtBox.setErrorText( ErrorMessages.INVALID_TEXT_INPUT );
					}
				}
				else
				{
					errorLabel.setVisible( true );
				}
			}
		} );

		createRTMPanel.add( rtmTxtBox );
		createRTMPanel.add( buttonWrapper );
		createRTMPanel.add( errorLabel );

		getView().getRTMView().add( createRTMPanel );
	}

	protected void createRTM( String rtmName, MaterialLabel errorLabel )
	{
		CommonServiceProvider.getCommonService().createRTM( rtmName, new ApplicationCallBack<Boolean>()
		{

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, "RTM Creation Failed" );
			}

			@Override
			public void onSuccess( Boolean result )
			{
				if ( result )
				{
					initializeRTMCmd.execute();
					//isRTMInitialized();
				}
			}
		} );
	}

	protected void attachRTMDetailsPanel( RTMModel rtmModel )
	{
		MaterialPanel rtmDetailsPanel = new MaterialPanel();
		rtmDetailsPanel.setGrid( "s12" );
		rtmDetailsPanel.addStyleName( resources.style().rtm_detail_penal() );

		CommonServiceProvider.getCommonService().getCapacityChartData( new ApplicationCallBack<CapacityChartModel>()
		{

			@Override
			public void onSuccess( CapacityChartModel result )
			{
				rtmDetailsPanel.add( createDetailHeaderPanel( rtmModel ) );
				rtmDetailsPanel.add( createCollection( result ) );
				getView().getRTMView().add( rtmDetailsPanel );
			}
		} );
	}

	private MaterialRow createCollection( CapacityChartModel result )
	{

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().rtm_detail_list() );

		MaterialCollectionItem collectionItem1 = new MaterialCollectionItem();
		collectionItem1.add( this.uiComponentsUtil.getLabel( "Projected Size", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary1 = new MaterialCollectionSecondary();
		collectionSecondary1.add( this.uiComponentsUtil.getLabel( Converter.getFormatSize( result.getUsagableSize() ), "", resources.style().displayInline() ) );
		collectionItem1.add( collectionSecondary1 );

		MaterialCollectionItem collectionItem2 = new MaterialCollectionItem();
		collectionItem2.add( this.uiComponentsUtil.getLabel( "Allocated Size", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary2 = new MaterialCollectionSecondary();
		collectionSecondary2.add( this.uiComponentsUtil.getLabel( Converter.getFormatSize( result.getAllocatedSize() ), "", resources.style().displayInline() ) );
		collectionItem2.add( collectionSecondary2 );

		/*
		 * MaterialCollectionItem collectionItem3 = new MaterialCollectionItem();
		 * collectionItem3.add( this.uiComponentsUtil.getLabel( "LEMS", "",
		 * resources.style().displayInline() ) ); MaterialCollectionSecondary
		 * collectionSecondary3 = new MaterialCollectionSecondary();
		 * collectionSecondary3.add( this.uiComponentsUtil.getLabel( "", "",
		 * resources.style().displayInline() ) ); collectionItem3.add(
		 * collectionSecondary3 );
		 * 
		 * MaterialCollectionItem collectionItem4 = new MaterialCollectionItem();
		 * collectionItem4.add( this.uiComponentsUtil.getLabel( "DNA Amplification", "",
		 * resources.style().displayInline() ) ); MaterialCollectionSecondary
		 * collectionSecondary4 = new MaterialCollectionSecondary();
		 * collectionSecondary4.add( this.uiComponentsUtil.getLabel( "", "",
		 * resources.style().displayInline() ) ); collectionItem4.add(
		 * collectionSecondary4 );
		 */

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		/*
		 * collection1.add( collectionItem3 ); collection1.add( collectionItem4 );
		 */

		MaterialRow row = new MaterialRow();
		row.addStyleName( resources.style().rtm_detail_row() );
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s4" );
		column1.addStyleName( resources.style().rtm_detail_list() );
		column1.add( collection1 );

		MaterialColumn column2 = new MaterialColumn();
		column2.setGrid( "s8" );
		column2.addStyleName( resources.style().rtm_detail_list() );
		MaterialPanel rtmChart = new MaterialPanel();
		rtmChart.setId( "rtmChart" );
		rtmChart.setHeight( "35rem" );
		column2.add( rtmChart );
		rtmChart.addAttachHandler( new Handler()
		{

			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				lemDetailChart = LEMDetailsChart.drawLEMDetailsChart( rtmChart.getId(), "0", "0" );

				LEMDetailsChart.setData( lemDetailChart, Converter.formatDecimal( Double.valueOf( result.getAllocatedPercent() ) ), Converter.getFormatSize( result.getAllocatedSize() ), Converter.formatDecimal( Double.valueOf( result.getUnAllocatedPercent() ) )

				);
				MaterialLoader.loading( false );
			}
		} );

		/*
		 * if ( rtmChart.isAttached() ) { LEMDetailsChart.setData( lemDetailChart,
		 * result.getAllocatedPercent(), Converter.getFormatSize(
		 * result.getAllocatedSize() ) ); }
		 */

		row.add( column1 );
		row.add( column2 );
		return row;
	}

	private MaterialRow createDetailHeaderPanel( RTMModel rtmModel )
	{
		MaterialRow detailHeaderPanel = new MaterialRow();
	/*	detailHeaderPanel.addStyleName( resources.style().rtm_detail() );
		detailHeaderPanel.setId( "lemdetailsChartButton" + Document.get().createUniqueId() );
		detailHeaderPanel.setGrid( "s12" );
		detailHeaderPanel.addStyleName( "detail_header_penal" );
*/
		detailHeaderPanel.addStyleName( resources.style().nameEditIcon_row() );
		MaterialTextBox rtmName = this.uiComponentsUtil.getDetailVmNameEdit( rtmModel.getName() );
		rtmName.addValidator( uiComponentsUtil.getTextBoxEmptyValidator() );
		rtmName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		rtmName.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				rtmName.validate();
			}
		} );
		rtmName.addValueChangeHandler( new ValueChangeHandler<String>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				if ( !rtmName.validate() )
				{
					return;
				}
			    UpdateRTMModel updateRTMModel=new UpdateRTMModel(rtmModel.getName(),event.getValue());
				CommonServiceProvider.getCommonService().updateRTMName( updateRTMModel, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						if ( result )
						{
							MaterialToast.fireToast( "RTM name updated to " + event.getValue() + "..!", "rounded" );
							initializeRTMCmd.execute();
						}
						else
						{
							MaterialToast.fireToast( "Unable to update RTM name..!", "rounded" );
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
		

/*		MaterialColumn actionItemCol = new MaterialColumn();
		actionItemCol.setGrid( "s4" );
		actionItemCol.addStyleName( resources.style().action_item_bar() );*/

		MaterialButton deleteRTMBtn = new MaterialButton( "", IconType.DELETE, ButtonType.FLAT );
		deleteRTMBtn.setTitle( "Delete RTM" );
		deleteRTMBtn.addStyleName( resources.style().lem_delete_btn() );
		deleteRTMBtn.setIconPosition( IconPosition.RIGHT );
		deleteRTMBtn.setMarginTop(2);
		deleteRTMBtn.setMarginLeft(12);
		deleteRTMBtn.setHeight("32px");
		deleteRTMBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				DeleteRTMPopup deleteRTMPopUp = new DeleteRTMPopup( rtmModel, uiComponentsUtil, currentUser, initializeRTMCmd );
				deleteRTMPopUp.open();

			}
		} );
		//actionItemCol.add( deleteRTMBtn );
		//detailHeaderPanel.add( detailLabel );
		detailHeaderPanel.add( rtmName );
		detailHeaderPanel.add( deleteRTMBtn );
		return detailHeaderPanel;
	}

	@Inject
	RTMPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		getView().setUiHandlers( this );
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		initializeRTMCmd();
	}

	@Override
	protected void onReset()
	{
		super.onReset();
		MaterialLoader.loading( true );
		dataUpdate();
	}

	private boolean checkInputText( MaterialTextBox rtmTxtBox )
	{
		if ( rtmTxtBox.getValue() != null && !rtmTxtBox.getValue().isEmpty() && !rtmTxtBox.getValue().matches( "^[a-zA-Z0-9_-]*$" ) )
		{
			rtmTxtBox.setErrorText( ErrorMessages.INVALID_TEXT_INPUT );
			return false;
		}
		else
		{
			rtmTxtBox.clearErrorText();
			rtmTxtBox.setSuccessText( "" );
			return true;
		}
	}
}
