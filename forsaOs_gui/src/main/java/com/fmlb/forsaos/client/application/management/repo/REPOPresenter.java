package com.fmlb.forsaos.client.application.management.repo;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
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

public class REPOPresenter extends Presenter<REPOPresenter.MyView, REPOPresenter.MyProxy> implements REPOUiHandlers
{
	interface MyView extends View, HasUiHandlers<REPOUiHandlers>
	{
		public HTMLPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_REPO = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.REPO )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<REPOPresenter>
	{
	}

	private PlaceManager placeManager;

	private REPODataTable repoDataTable;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		MaterialLoader.loading( false );
		LoggerUtil.log( "on reveal REPO presenter" );
	}

	@Inject
	REPOPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		getView().setUiHandlers( this );
		repoDataTable = new REPODataTable( 500, this.uiComponentsUtil, this.placeManager, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
			}
		} );
		getView().getMainPanel().add( repoDataTable );
	}

	@Override
	protected void onReset()
	{
		super.onReset();
		dataUpdate();
	}

	private void dataUpdate()
	{
		repoDataTable.getUpdateIcommandWithData().executeWithData( false );
		BreadcrumbEvent.fire( REPOPresenter.this, "Management >> Repository", NameTokens.REPO );
	}

}