package com.fmlb.forsaos.client.application.management.networking.detail;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.ShowNetworkingDetailsPresenterEvent;
import com.fmlb.forsaos.client.application.event.ShowNetworkingDetailsPresenterEvent.ShowNetworkingDetailsPresenterHandler;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CreateNetworkModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.NetworkingModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

public class NetworkingDetailPresenter extends Presenter<NetworkingDetailPresenter.MyView, NetworkingDetailPresenter.MyProxy> implements NetworkingDetailUiHandlers, ShowNetworkingDetailsPresenterHandler
{
	interface MyView extends View, HasUiHandlers<NetworkingDetailUiHandlers>
	{
		public MaterialPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_NetworkingDetail = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.NETWORKING_DETAILS )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<NetworkingDetailPresenter>
	{
	}

	private NetworkingModel networkingModel;

	private UIComponentsUtil uiComponentsUtil;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	@Inject
	NetworkingDetailPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );

		LoggerUtil.log( "constructor networking detail presenter" );
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		getView().setUiHandlers( this );
	}

	@Override
	protected void onBind()
	{
		super.onBind();
		LoggerUtil.log( "on bind networking detail presenter" );
		addRegisteredHandler( ShowNetworkingDetailsPresenterEvent.getType(), this );
	}

	@ProxyEvent
	@Override
	public void onShowNetworkingDetailsPresenter( ShowNetworkingDetailsPresenterEvent event )
	{
		LoggerUtil.log( "onShowNetworkingDetailsPresenter received" );
		networkingModel = event.getNetworkingModel();
		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.NETWORKING_DETAILS ).build();
		placeManager.revealPlace( placeRequest );
	}

	@Override
	protected void onReset()
	{
		super.onReset();
		LoggerUtil.log( "on rest  networking detail presenter" );
		CommonServiceProvider.getCommonService().getNetworkDeviceCommonProperties( networkingModel.getName(), networkingModel.getType(), new ApplicationCallBack<CreateNetworkModel>()
		{
			@Override
			public void onSuccess( CreateNetworkModel updateNetworkModel )
			{
				NetworkingDetail networkingDetail = new NetworkingDetail( updateNetworkModel, networkingModel, uiComponentsUtil, currentUser, new IcommandWithData()
				{
					@Override
					public void executeWithData( Object obj )
					{
						MaterialLoader.loading( true );
						LoggerUtil.log( placeManager.getCurrentPlaceRequest().toString() );
						PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.NETWORKING ).build();
						placeManager.revealPlace( placeRequest );

					}
				} );
				getView().getMainPanel().clear();
				getView().getMainPanel().add( networkingDetail );
				BreadcrumbEvent.fire( NetworkingDetailPresenter.this, "Management >> Networking >> " + networkingModel.getName(), NameTokens.NETWORKING );
				MaterialLoader.loading( false );

			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup(caught, null);

			}
		} );

	}

}