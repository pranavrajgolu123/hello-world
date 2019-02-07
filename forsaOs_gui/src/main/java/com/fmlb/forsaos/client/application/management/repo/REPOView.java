package com.fmlb.forsaos.client.application.management.repo;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class REPOView extends ViewWithUiHandlers<REPOUiHandlers> implements REPOPresenter.MyView
{
	interface Binder extends UiBinder<Widget, REPOView>
	{
	}

	@UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel()
	{
		return main;
	}

	@Inject
	REPOView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}
}