package com.fmlb.forsaos.client.application.management.networking;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.ShowNetworkingDetailsPresenterEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.NetworkingModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import gwt.material.design.client.ui.MaterialLoader;

public class NetworkingPresenter extends Presenter<NetworkingPresenter.MyView, NetworkingPresenter.MyProxy> implements NetworkingUiHandlers
{
	interface MyView extends View, HasUiHandlers<NetworkingUiHandlers>
	{
		public HTMLPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_Networking = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.NETWORKING )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<NetworkingPresenter>
	{
	}

	private NetworkingDataTable networkingDataTable;

	private InterfacesDataTable interfacesDataTable;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		LoggerUtil.log( "on reveal lem presenter" );
	}

	@Inject
	NetworkingPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		LoggerUtil.log( "constructor lem presenter" );
		this.currentUser = currentUser;
		this.uiComponentsUtil = uiComponentsUtil;
		getView().setUiHandlers( this );

		interfacesDataTable = new InterfacesDataTable( 500, this.uiComponentsUtil, this.currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				// TODO Auto-generated method stub

			}
		} );

		networkingDataTable = new NetworkingDataTable( 500, this.uiComponentsUtil, this.currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				MaterialLoader.loading( true );
				LoggerUtil.log( "firing ShowNetworkingDetailsPresenterEvent "+obj.toString() );
				ShowNetworkingDetailsPresenterEvent.fire( NetworkingPresenter.this, ( NetworkingModel ) obj );

			}
		} );
		// getView().getMainPanel().setHeight("500");
		// getView().getMainPanel().add( interfacesDataTable );
		getView().getMainPanel().add( networkingDataTable );
		//ShowLEMDetailsPresenterEvent.fire( this, new LEMModel() );

	}

	@Override
	protected void onReset()
	{
		super.onReset();
		LoggerUtil.log( "on reset networking presenter" );
		dataUpdate();
	}

	private void dataUpdate()
	{
		MaterialLoader.loading( false );
		networkingDataTable.getUpdateIcommand().execute();
		BreadcrumbEvent.fire( NetworkingPresenter.this, "Management >> Networking", NameTokens.NETWORKING );
	}

}