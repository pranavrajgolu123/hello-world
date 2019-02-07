package com.fmlb.forsaos.client.application.lem.details;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;

class LEMDetailsView extends ViewWithUiHandlers<LEMDetailsUiHandlers> implements LEMDetailsPresenter.MyView
{
	interface Binder extends UiBinder<Widget, LEMDetailsView>
	{
	}

	@UiField
	MaterialPanel main;

	public MaterialPanel getMainPanel()
	{
		return main;
	}

	@Inject
	LEMDetailsView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

	/*
	 * @Override public void setInSlot(Object slot, IsWidget content) { if (slot ==
	 * LEMDetailsPresenter.SLOT_LEMDetails) { main.setWidget(content); } else {
	 * super.setInSlot(slot, content); } }
	 */
}