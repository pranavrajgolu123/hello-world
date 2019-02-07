package com.fmlb.forsaos.client.application.settings.system;


import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;

class SystemView extends ViewWithUiHandlers<SystemUiHandlers> implements SystemPresenter.MyView
{
	interface Binder extends UiBinder<Widget, SystemView>
	{
	}

	@UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel()
	{
		return main;
	}

	@Inject
	SystemView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

}