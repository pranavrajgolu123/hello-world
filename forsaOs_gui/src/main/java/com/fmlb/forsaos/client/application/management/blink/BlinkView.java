package com.fmlb.forsaos.client.application.management.blink;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class BlinkView extends ViewWithUiHandlers<BlinkUiHandlers> implements BlinkPresenter.MyView
{
	interface Binder extends UiBinder<Widget, BlinkView>
	{
	}

	@UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel()
	{
		return main;
	}

	@Inject
	BlinkView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

}