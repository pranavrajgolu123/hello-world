package com.fmlb.forsaos.client.application.diagnostics.status;

import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
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
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import gwt.material.design.client.ui.MaterialLoader;

public class DiagnosticsStatusPresenter extends Presenter<DiagnosticsStatusPresenter.MyView, DiagnosticsStatusPresenter.MyProxy> implements DiagnosticsStatusUiHandlers
{
	interface MyView extends View, HasUiHandlers<DiagnosticsStatusUiHandlers>
	{
		public HTMLPanel getMainPanel();
	}

	private UIComponentsUtil uiComponentsUtil;

	private DiagnosticsStatusTabs diagStatusTabs;

	private CurrentUser currentUser;

	private PlaceManager placeManager;

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_Status = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.DIAG_STATUS )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<DiagnosticsStatusPresenter>
	{
	}

	@Override
	protected void onReveal()
	{
		super.onReveal();

	}

	@Inject
	DiagnosticsStatusPresenter( EventBus eventBus, CurrentUser currentUser, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		getView().setUiHandlers( this );
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.placeManager = placeManager;
		diagStatusTabs = new DiagnosticsStatusTabs( this.uiComponentsUtil, this.currentUser );
		getView().getMainPanel().add( diagStatusTabs );
	}

	@Override
	protected void onReset()
	{
		super.onReset();
		dataUpdate();
	}

	private void dataUpdate()
	{
		MaterialLoader.loading( false );
		diagStatusTabs.updateAllTabData();
		if ( this.placeManager != null && this.placeManager.getCurrentPlaceHierarchy() != null && !this.placeManager.getCurrentPlaceHierarchy().isEmpty() )
		{
			if ( this.placeManager.getCurrentPlaceRequest().getParameter( "navigateToUPS", "" ).equals( "true" ) )
			{
				diagStatusTabs.selectUPSTab();
			}
		}
		BreadcrumbEvent.fire( DiagnosticsStatusPresenter.this, "Diagnostics >> Status", NameTokens.DIAG_STATUS );
	}

	@Override
	protected void onHide()
	{
		super.onHide();
		LoggerUtil.log( "on hide" );
		if ( diagStatusTabs.getDiagStatusTabWebsocket() != null )
		{
			LoggerUtil.log( "close DiagStatusTabWebsocket web socket connection" );
			diagStatusTabs.getDiagStatusTabWebsocket().close();
		}
		if ( diagStatusTabs.getDiagUPSTabWebsocket() != null )
		{
			LoggerUtil.log( "close DiagUPSTabWebsocket web socket connection" );
			diagStatusTabs.getDiagUPSTabWebsocket().close();
		}
	}

}