package com.fmlb.forsaos.client.application.lem;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class LEMView extends ViewWithUiHandlers<LEMUiHandlers> implements LEMPresenter.MyView
{
	interface Binder extends UiBinder<Widget, LEMView>
	{
	}

	@UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel()
	{
		return main;
	}

	@Inject
	LEMView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

	/*
	 * @Override public void setInSlot(Object slot, IsWidget content) { if (slot ==
	 * LEMPresenter.SLOT_LEM) { main.setWidget(content); } else {
	 * super.setInSlot(slot, content); } }
	 */
}