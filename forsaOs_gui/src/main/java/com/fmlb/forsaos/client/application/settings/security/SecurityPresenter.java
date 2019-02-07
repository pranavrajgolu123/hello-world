package com.fmlb.forsaos.client.application.settings.security;

import com.fmlb.forsaos.client.application.common.Icommand;
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

public class SecurityPresenter extends Presenter<SecurityPresenter.MyView, SecurityPresenter.MyProxy> implements SecurityUiHandlers
{
	public interface MyView extends View, HasUiHandlers<SecurityUiHandlers>
	{
		public HTMLPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_Security = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.SECURITY )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SecurityPresenter>
	{

	}

	private PlaceManager placeManager;

	private SecurityTabs securityTabs;

	private UIComponentsUtil uiComponentsUtil;

	private Icommand navigateCmd;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
	}

	@Inject
	SecurityPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		getView().setUiHandlers( this );
		securityTabs = new SecurityTabs( this.uiComponentsUtil, this.currentUser, this.navigateCmd );
		getView().getMainPanel().add( securityTabs );
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
		securityTabs.updateAllTabData();
		BreadcrumbEvent.fire( SecurityPresenter.this, "Settings >> Security", NameTokens.SECURITY );
	}
}
