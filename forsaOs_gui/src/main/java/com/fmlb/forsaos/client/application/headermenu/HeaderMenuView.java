package com.fmlb.forsaos.client.application.headermenu;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class HeaderMenuView extends ViewWithUiHandlers<HeaderMenuUiHandlers> implements HeaderMenuPresenter.MyView
{
	interface Binder extends UiBinder<Widget, HeaderMenuView>
	{
	}

	/*
	 * @UiField SimplePanel main;
	 */

	@Inject
	HeaderMenuView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

}