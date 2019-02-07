package com.fmlb.forsaos.client.application.vm;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class VMView extends ViewWithUiHandlers<VMUiHandlers> implements VMPresenter.MyView
{
	interface Binder extends UiBinder<Widget, VMView>
	{
	}

	@UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel()
	{
		return main;
	}

	@Inject
	VMView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

	/* @Override
	public void setInSlot(Object slot, IsWidget content) {
	    if (slot == VMPresenter.SLOT_VM) {
	        main.setWidget(content);
	    } else {
	        super.setInSlot(slot, content);
	    }
	}*/
}