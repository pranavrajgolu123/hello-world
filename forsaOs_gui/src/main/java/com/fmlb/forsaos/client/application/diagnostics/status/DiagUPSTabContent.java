package com.fmlb.forsaos.client.application.diagnostics.status;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.DeregisterUPSModel;
import com.fmlb.forsaos.client.application.models.UPSDetailsModel;
import com.fmlb.forsaos.client.application.models.UPSModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.shared.application.utility.CommonUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class DiagUPSTabContent extends MaterialPanel
{

	private CurrentUser currentUser;

	private UIComponentsUtil uiComponentsUtil;

	private ConfirmPasswordPopup deRegisterUPSPopup;

	private List<UPSModel> upsModelList = new ArrayList<>();

	private List<UPSDetailsModel> upsDetailsModelList = new ArrayList<>();

	private IcommandWithData updateUPSTabContentCmd;

	private MaterialButton registerUpsBtn;

	private boolean isImplDataReceived = false;

	AppResources resources = GWT.create( AppResources.class );

	public DiagUPSTabContent( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
	}

	private MaterialButton getRegisterUPSBtn( UIComponentsUtil uiComponentsUtil )
	{
		MaterialButton registerUpsBtn = new MaterialButton();
		registerUpsBtn.setTitle( "Register UPS" );
		registerUpsBtn.setText( "Register UPS" );
		registerUpsBtn.setFloat( Float.RIGHT );
		registerUpsBtn.addStyleName( resources.style().upsRegisterBtn() );
		registerUpsBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				event.stopPropagation();
				registerUPSBtnAction( uiComponentsUtil );

			}

		} );
		return registerUpsBtn;
	}

	private void registerUPSBtnAction( UIComponentsUtil uiComponentsUtil )
	{
		if ( upsModelList.size() >= 2 )
		{
			ArrayList<String> errorMsgs = new ArrayList<>();
			errorMsgs.add( "Maximum of 2 UPS can be registered" );
			ErrorPopup errorPopup = new ErrorPopup( errorMsgs, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
			errorPopup.open();
		}
		else
		{

			RegisterUpsPopup registerUpsPopUp = new RegisterUpsPopup( uiComponentsUtil, getUpdateIcommandWithData() );
			registerUpsPopUp.open();
		}
	}

	private void showNoUPSRegistered()
	{
		MaterialLabel noUPSLable = uiComponentsUtil.getLabel( "No UPS has been registered", "s12", "" );
		noUPSLable.addStyleName( resources.style().emptyDataPenal() );
		add( noUPSLable );
		isImplDataReceived = true;
	}

	private MaterialPanel drawUPSInfoCollection( UPSModel upsModel, UPSDetailsModel upsDetailsModel )
	{

		MaterialPanel upsDetailsPanel = new MaterialPanel();
		upsDetailsPanel.addStyleName( "upsRow" );
		MaterialLabel upsDetailsLabel = this.uiComponentsUtil.getLabel( upsModel.getName(), "s12", resources.style().statusHeader() );
		upsDetailsLabel.addStyleName( "upsHeaderPenal" );

		upsDetailsPanel.add( upsDetailsLabel );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( resources.style().vm_setting_row() );

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		collection1.addStyleName( "NVMEDetails" );

		MaterialCollectionItem collectionItem1 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "IP ", upsModel.getIp() );

		MaterialCollectionItem collectionItem2 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Port ", upsModel.getPort() );

		MaterialCollectionItem collectionItem3 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Temperature ", upsDetailsModel.getTemp().toString() + CommonUtil.CELSIUS_SYMBOL );

		MaterialCollectionItem collectionItem4 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Output Power ", Long.toString( upsDetailsModel.getOutput_power() ) + " W" );

		MaterialCollectionItem collectionItem5 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Runtime Remaining ", Long.toString( upsDetailsModel.getRuntime_remaining() ) );

		MaterialCollectionItem collectionItem6 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "State ", upsDetailsModel.getState() );

		MaterialCollectionItem collectionItem7 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Battery Charge ", Integer.toString( upsDetailsModel.getBattery_charge() ) + " %" );

		MaterialCollectionItem collectionItem8 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Battery Life ", upsDetailsModel.getBattery_life() );

		String batteryReplacementStatus;

		if ( upsDetailsModel.getBattery_need_replacement() == false )
		{
			batteryReplacementStatus = "No";
		}
		else
		{
			batteryReplacementStatus = "Yes";
		}

		MaterialCollectionItem collectionItem9 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Replace Battery ", batteryReplacementStatus );

		MaterialCollectionItem collectionItem10 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Status ", upsDetailsModel.getStatus_modifier() );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem3 );
		collection1.add( collectionItem5 );
		collection1.add( collectionItem7 );
		collection1.add( collectionItem9 );

		collection1.add( collectionItem2 );
		collection1.add( collectionItem4 );
		collection1.add( collectionItem6 );
		collection1.add( collectionItem8 );
		collection1.add( collectionItem10 );

		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s12" );
		column1.addStyleName( "schedulerBlinkRow" );
		column1.add( collection1 );

		MaterialButton deRegisterUPSBtn = getDeregisterUPSBtn( upsModel );

		firstRow.add( column1 );

		upsDetailsLabel.add( deRegisterUPSBtn );

		upsDetailsPanel.add( firstRow );

		return upsDetailsPanel;
	}

	private MaterialButton getDeregisterUPSBtn( UPSModel upsModel )
	{
		MaterialButton deRegisterUPSBtn = new MaterialButton();
		deRegisterUPSBtn.setTitle( "Deregister" );
		deRegisterUPSBtn.addStyleName( resources.style().lem_delete_btn() );
		deRegisterUPSBtn.setFloat( Float.RIGHT );
		deRegisterUPSBtn.setIconType( IconType.DELETE );
		deRegisterUPSBtn.setIconPosition( IconPosition.RIGHT );
		deRegisterUPSBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				deRegisterUPSBtnAction( upsModel.getName() );
			}
		} );
		return deRegisterUPSBtn;
	}

	private void deRegisterUPSBtnAction( String upsName )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to deregister this UPS  " + upsName + " ?" );
		deRegisterUPSPopup = new ConfirmPasswordPopup( "Deregister UPS", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				LoggerUtil.log( "Deregister Ups" );
				MaterialLoader.loading( true, deRegisterUPSPopup.getBodyPanel() );
				DeregisterUPSModel deleteUps = new DeregisterUPSModel( upsName );
				CommonServiceProvider.getCommonService().deRegisterUPS( deleteUps, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						deRegisterUPSPopup.close();
						MaterialLoader.loading( false, deRegisterUPSPopup.getBodyPanel() );
						MaterialToast.fireToast( upsName + " Deregistered..!", "rounded" );
						getUpdateIcommandWithData().executeWithData( false );
					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );

					}
				} );
			}
		} );
		deRegisterUPSPopup.open();
	}

	public IcommandWithData getUpdateIcommandWithData()
	{
		return updateUPSTabContentCmd = new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				updateData();

			}
		};
	}

	private String getUPSDetailReq( String upsName )
	{
		return "{\"name\" : \"" + upsName + "\"}";
	}

	private void drawOneUPSData()
	{
		String request = getUPSDetailReq( upsModelList.get( 0 ).getName() );
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getUPSDetails( request, new ApplicationCallBack<UPSDetailsModel>()
		{

			@Override
			public void onSuccess( UPSDetailsModel result )
			{
				MaterialLoader.loading( false );
				upsDetailsModelList.add( result );
				add( drawUPSInfoCollection( upsModelList.get( 0 ), upsDetailsModelList.get( 0 ) ) );
				isImplDataReceived = true;
			}

			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void drawTwoUPSData()
	{
		String request = getUPSDetailReq( upsModelList.get( 0 ).getName() );
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getUPSDetails( request, new ApplicationCallBack<UPSDetailsModel>()
		{
			@Override
			public void onSuccess( UPSDetailsModel result )
			{
				MaterialLoader.loading( true );
				upsDetailsModelList.clear();
				upsDetailsModelList.add( result );
				String secondRequest = getUPSDetailReq( upsModelList.get( 1 ).getName() );
				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().getUPSDetails( secondRequest, new ApplicationCallBack<UPSDetailsModel>()
				{

					@Override
					public void onSuccess( UPSDetailsModel result )
					{
						MaterialLoader.loading( false );
						upsDetailsModelList.add( result );
						add( drawUPSInfoCollection( upsModelList.get( 0 ), upsDetailsModelList.get( 0 ) ) );
						add( drawUPSInfoCollection( upsModelList.get( 1 ), upsDetailsModelList.get( 1 ) ) );
						isImplDataReceived = true;

					}

					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );
					};
				} );
			}

			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			};
		} );
	}

	public void updateData()
	{
		clear();
		this.registerUpsBtn = getRegisterUPSBtn( uiComponentsUtil );
		add( registerUpsBtn );
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getUPS( null, new ApplicationCallBack<List<UPSModel>>()
		{

			@Override
			public void onSuccess( List<UPSModel> result )
			{
				MaterialLoader.loading( false );
				upsModelList.clear();
				upsModelList.addAll( result );
				int sizeOfList = result.size();
				if ( sizeOfList == 0 )
				{
					showNoUPSRegistered();
				}
				else if ( sizeOfList == 1 )
				{
					drawOneUPSData();

				}
				else if ( sizeOfList == 2 )
				{
					drawTwoUPSData();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	public void updateDynamicData( List<UPSModel> upsModelList, List<UPSDetailsModel> upsDetailsModelList )
	{
		LoggerUtil.log( "UPS 22" );
		if ( !isImplDataReceived )
		{
			LoggerUtil.log( "UPS 23" );
			return;
		}
		LoggerUtil.log( "UPS 24" );
		clear();
		LoggerUtil.log( "UPS 25" );
		this.registerUpsBtn = getRegisterUPSBtn( uiComponentsUtil );
		LoggerUtil.log( "UPS 26" );
		add( registerUpsBtn );
		LoggerUtil.log( "UPS 27" );
		if ( this.upsModelList != null && !this.upsModelList.isEmpty() )
		{
			LoggerUtil.log( "UPS 28" );
			for ( UPSModel upsModel : this.upsModelList )
			{
				LoggerUtil.log( "UPS 29" );
				for ( int i = 0; i < upsModelList.size(); i++ )
				{
					LoggerUtil.log( "UPS 30" );
					if ( upsModel.getName().equals( upsModelList.get( i ).getName() ) )
					{
						LoggerUtil.log( "UPS 31" );
						upsModelList.get( i ).setIp( upsModel.getIp() );
						LoggerUtil.log( "UPS 32" );
						upsModelList.get( i ).setPort( upsModel.getPort() );
						LoggerUtil.log( "UPS 3" );
					}
					LoggerUtil.log( "UPS 34" );
				}
				LoggerUtil.log( "UPS 35" );
			}
			LoggerUtil.log( "UPS 36" );
		}
		LoggerUtil.log( "UPS 37" );
		this.upsModelList.clear();
		LoggerUtil.log( "UPS 38" );
		this.upsDetailsModelList.clear();
		LoggerUtil.log( "UPS 39" );
		this.upsModelList.addAll( upsModelList );
		LoggerUtil.log( "UPS 40" );
		this.upsDetailsModelList.addAll( upsDetailsModelList );
		LoggerUtil.log( "UPS 41" );
		int sizeOfList = this.upsModelList.size();
		LoggerUtil.log( "UPS 42" );
		if ( sizeOfList == 0 )
		{
			LoggerUtil.log( "UPS 43" );
			showNoUPSRegistered();
			LoggerUtil.log( "UPS 44" );
		}
		else if ( sizeOfList == 1 )
		{
			LoggerUtil.log( "UPS 45" );
			add( drawUPSInfoCollection( this.upsModelList.get( 0 ), this.upsDetailsModelList.get( 0 ) ) );
			LoggerUtil.log( "UPS 46" );
		}
		else if ( sizeOfList == 2 )
		{
			LoggerUtil.log( "UPS 47" );
			add( drawUPSInfoCollection( this.upsModelList.get( 0 ), this.upsDetailsModelList.get( 0 ) ) );
			LoggerUtil.log( "UPS 48" );
			add( drawUPSInfoCollection( this.upsModelList.get( 1 ), this.upsDetailsModelList.get( 1 ) ) );
			LoggerUtil.log( "UPS 49" );
		}
	}
}
