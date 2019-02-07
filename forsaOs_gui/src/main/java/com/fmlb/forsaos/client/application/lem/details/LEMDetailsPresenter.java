package com.fmlb.forsaos.client.application.lem.details;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.ShowLEMDetailsPresenterEvent;
import com.fmlb.forsaos.client.application.event.ShowLEMDetailsPresenterEvent.ShowLEMDetailsPresenterHandler;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.RTMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;

public class LEMDetailsPresenter extends Presenter<LEMDetailsPresenter.MyView, LEMDetailsPresenter.MyProxy> implements LEMDetailsUiHandlers, ShowLEMDetailsPresenterHandler
{
	interface MyView extends View, HasUiHandlers<LEMDetailsUiHandlers>
	{
		public MaterialPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_LEMDetails = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.LEM_DETAILS )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<LEMDetailsPresenter>
	{
	}

	private LEMModel lemModel;

	private RTMModel rtmModel;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		LoggerUtil.log( "reveal lem detail presenter" );
		LEMDetails lemDetails = new LEMDetails( lemModel, this.uiComponentsUtil, rtmModel, currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{

				if ( !placeManager.getCurrentPlaceRequest().getNameToken().equals( NameTokens.LEM ) )
				{
					MaterialLoader.loading( true );
					LoggerUtil.log( placeManager.getCurrentPlaceRequest().toString() );
					PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.LEM ).build();
					placeManager.revealPlace( placeRequest );
				}

			}
		} );
		getView().getMainPanel().clear();
		getView().getMainPanel().add( lemDetails );
		BreadcrumbEvent.fire( LEMDetailsPresenter.this, "Virtualization >> LEM >> " + lemModel.getLemName(), NameTokens.LEM );
		MaterialLoader.loading( false );
	}

	private UIComponentsUtil uiComponentsUtil;

	private PlaceManager placeManager;

	@Inject
	LEMDetailsPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		LoggerUtil.log( "constructor lem detail presenter" );
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		getView().setUiHandlers( this );
		/*ArrayList<LEMModel> lemModels = new ArrayList<>();
		for ( int i = 0; i < 1; i++ )
		{
			long sectSize = 4096;
			lemModels.add( new LEMModel( "lem" + i, String.valueOf( i ), Long.valueOf( i ), MemorySizeType.GB, sectSize, OSFlagType.FAT16, "N", i + "", "VM" + i, 845 ) );
		}*/
	}

	@Override
	protected void onBind()
	{
		super.onBind();
		LoggerUtil.log( "on bind lem detail presenter" );
		addRegisteredHandler( ShowLEMDetailsPresenterEvent.getType(), this );
	}

	@ProxyEvent
	@Override
	public void onShowLEMDetailsPresenter( ShowLEMDetailsPresenterEvent event )
	{
		lemModel = event.getLEMModel();
		LoggerUtil.log( "event received" + event.getLEMModel().getLemName() );
		CommonServiceProvider.getCommonService().getRTM( null, new ApplicationCallBack<RTMModel>()
		{

			@Override
			public void onSuccess( RTMModel result )
			{
				rtmModel = result;
				LoggerUtil.log( "got rtm data" );
				PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.LEM_DETAILS ).build();
				placeManager.revealPlace( placeRequest );
			}
		} );

	}

}