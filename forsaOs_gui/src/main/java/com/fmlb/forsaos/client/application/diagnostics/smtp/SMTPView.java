package com.fmlb.forsaos.client.application.diagnostics.smtp;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class SMTPView extends ViewWithUiHandlers<SMTPUiHandlers> implements SMTPPresenter.MyView
{
	interface Binder extends UiBinder<Widget, SMTPView>
	{
	}

	@UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel()
	{
		return main;
	}

	@Inject
	SMTPView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

}