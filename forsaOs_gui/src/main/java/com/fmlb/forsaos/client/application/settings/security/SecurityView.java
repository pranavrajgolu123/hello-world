package com.fmlb.forsaos.client.application.settings.security;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class SecurityView extends ViewWithUiHandlers<SecurityUiHandlers> implements SecurityPresenter.MyView
{
	interface Binder extends UiBinder<Widget, SecurityView>
	{
	}

	@UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel()
	{
		return main;
	}

	@Inject
	SecurityView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

}