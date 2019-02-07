package com.fmlb.forsaos.client.application.vm.details;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;

class VMDetailsView extends ViewWithUiHandlers<VMDetailsUiHandlers> implements VMDetailsPresenter.MyView
{
	interface Binder extends UiBinder<Widget, VMDetailsView>
	{
	}

	@UiField
	MaterialPanel main;

	public MaterialPanel getMainPanel()
	{
		return main;
	}

	@Inject
	VMDetailsView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

}