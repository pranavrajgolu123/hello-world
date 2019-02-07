package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.LogoutEvent;
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

public class AccountsPresenter extends Presenter<AccountsPresenter.MyView, AccountsPresenter.MyProxy> implements AccountsUiHandlers
{
	public interface MyView extends View, HasUiHandlers<AccountsUiHandlers>
	{
		public HTMLPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_Accounts = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.ACCOUNTS )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<AccountsPresenter>
	{

	}

	private PlaceManager placeManager;

	private AccountsTabs accountsTabs;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	private IcommandWithData navigateCmd;

	@Override
	protected void onReveal()
	{
		super.onReveal();
	}

	@Inject
	AccountsPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		LoggerUtil.log( "Cocnstructor accountesprsenter -satert" );
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		navigateCmd = new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				LogoutEvent.fire( AccountsPresenter.this, "" );

			}
		};
		getView().setUiHandlers( this );
		accountsTabs = new AccountsTabs( this.uiComponentsUtil, this.currentUser, this.navigateCmd );
		getView().getMainPanel().add( accountsTabs );
		LoggerUtil.log( "Cocnstructor accountesprsenter -end" );
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
		accountsTabs.updateAllTabData();
		BreadcrumbEvent.fire( AccountsPresenter.this, "Settings >> Accounts", NameTokens.ACCOUNTS );
	}
}
