package com.fmlb.forsaos.client.application;

import com.fmlb.forsaos.client.application.headermenu.HeaderMenuPresenter;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.presenter.slots.NestedSlot;
import com.gwtplatform.mvp.client.presenter.slots.SingleSlot;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy>
{
	interface MyView extends View
	{
	}

	@ProxyStandard
	interface MyProxy extends Proxy<ApplicationPresenter>
	{
	}

	HeaderMenuPresenter headerMenuPresenter;

	/*
	 * @ContentSlot public static final GwtEvent.Type<RevealContentHandler<?>>
	 * SLOT_MAIN = new GwtEvent.Type<>();
	 */
	public static final NestedSlot SLOT_MAIN = new NestedSlot();

	public static final SingleSlot<HeaderMenuPresenter> SLOT_MENU_PANEL = new SingleSlot<HeaderMenuPresenter>();

	@Inject
	ApplicationPresenter( EventBus eventBus, MyView view, MyProxy proxy, HeaderMenuPresenter headerMenuPresenter )
	{
		super( eventBus, view, proxy, RevealType.Root );
		this.headerMenuPresenter = headerMenuPresenter;
	}
	/*
	 * @Override protected void onBind() { setInSlot(SLOT_MENU_PANEL,
	 * headerMenuPresenter); super.onBind(); }
	 */
}
