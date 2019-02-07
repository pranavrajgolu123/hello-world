package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
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
import gwt.material.design.client.ui.MaterialPanel;

public class SystemPresenter extends Presenter<SystemPresenter.MyView, SystemPresenter.MyProxy> implements SystemUiHandlers
{
	public interface MyView extends View, HasUiHandlers<SystemUiHandlers>
	{
		public HTMLPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_System = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.SYSTEM )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SystemPresenter>
	{

	}

	private SystemTabs systemTabs;

	private UIComponentsUtil uiComponentsUtil;

	@Override
	protected void onReveal()
	{
		super.onReveal();

	}

	@Inject
	SystemPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		this.uiComponentsUtil = uiComponentsUtil;
		getView().setUiHandlers( this );
		systemTabs = new SystemTabs( this.uiComponentsUtil );
		getView().getMainPanel().add( systemTabs );
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
		systemTabs.updateAllTabData();
		BreadcrumbEvent.fire( SystemPresenter.this, "Settings >> Host", NameTokens.SYSTEM );
	}

}
