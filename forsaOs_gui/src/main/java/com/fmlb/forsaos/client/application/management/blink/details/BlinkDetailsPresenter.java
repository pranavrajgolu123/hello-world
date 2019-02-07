package com.fmlb.forsaos.client.application.management.blink.details;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.ShowBlinkDetailsPresenterEvent;
import com.fmlb.forsaos.client.application.event.ShowBlinkDetailsPresenterEvent.ShowBlinkDetailsPresenterHandler;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.BlinkModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;

public class BlinkDetailsPresenter extends Presenter<BlinkDetailsPresenter.MyView, BlinkDetailsPresenter.MyProxy> implements BlinkDetailsUiHandlers, ShowBlinkDetailsPresenterHandler
{
	interface MyView extends View, HasUiHandlers<BlinkDetailsUiHandlers>
	{
		public MaterialPanel getMainPanel();
	}

	@NameToken( NameTokens.BLINK_DETAILS )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<BlinkDetailsPresenter>
	{
	}

	private BlinkModel blinkModel;

	private CurrentUser currentUser;

	private UIComponentsUtil uiComponentsUtil;

	private PlaceManager placeManager;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		LoggerUtil.log( "reveal blink detail presenter" );
		BlinkDetails blinkDetails = new BlinkDetails( blinkModel, this.uiComponentsUtil, currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				if ( !placeManager.getCurrentPlaceRequest().getNameToken().equals( NameTokens.BLINK ) )
				{
					MaterialLoader.loading( true );
					LoggerUtil.log( placeManager.getCurrentPlaceRequest().toString() );
					PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.BLINK ).build();
					placeManager.revealPlace( placeRequest );
				}
			}
		} );
		getView().getMainPanel().clear();
		getView().getMainPanel().add( blinkDetails );
		BreadcrumbEvent.fire( BlinkDetailsPresenter.this, "Management >> Blink >> " + blinkModel.getName(), NameTokens.BLINK );
		MaterialLoader.loading( false );
	}

	@Inject
	BlinkDetailsPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		LoggerUtil.log( "constructor blink detail presenter" );
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		getView().setUiHandlers( this );

	}

	@Override
	protected void onBind()
	{
		super.onBind();
		LoggerUtil.log( "on bind blink detail presenter" );
		addRegisteredHandler( ShowBlinkDetailsPresenterEvent.getType(), this );
	}

	@ProxyEvent
	@Override
	public void onShowBlinkDetailsPresenter( ShowBlinkDetailsPresenterEvent event )
	{
		blinkModel = event.getBlinkModel();
		LoggerUtil.log( "event received" + event.getBlinkModel().getName() );
		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.BLINK_DETAILS ).build();
		placeManager.revealPlace( placeRequest );

	}

}