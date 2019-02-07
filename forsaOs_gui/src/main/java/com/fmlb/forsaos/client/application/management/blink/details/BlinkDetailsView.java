package com.fmlb.forsaos.client.application.management.blink.details;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;

class BlinkDetailsView extends ViewWithUiHandlers<BlinkDetailsUiHandlers> implements BlinkDetailsPresenter.MyView
{
	interface Binder extends UiBinder<Widget, BlinkDetailsView>
	{
	}

	@UiField
	MaterialPanel main;

	public MaterialPanel getMainPanel()
	{
		return main;
	}

	@Inject
	BlinkDetailsView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}
}