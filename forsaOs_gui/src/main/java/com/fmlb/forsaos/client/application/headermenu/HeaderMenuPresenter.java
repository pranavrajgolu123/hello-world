package com.fmlb.forsaos.client.application.headermenu;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.HasUiHandlers;

public class HeaderMenuPresenter extends PresenterWidget<HeaderMenuPresenter.MyView> implements HeaderMenuUiHandlers
{
	interface MyView extends View, HasUiHandlers<HeaderMenuUiHandlers>
	{
	}

	@Inject
	HeaderMenuPresenter( EventBus eventBus, MyView view )
	{
		super( eventBus, view );

		getView().setUiHandlers( this );
	}

}