package com.fmlb.forsaos.client.application.management.networking.detail;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;

class NetworkingDetailView extends ViewWithUiHandlers<NetworkingDetailUiHandlers> implements NetworkingDetailPresenter.MyView
{
	interface Binder extends UiBinder<Widget, NetworkingDetailView>
	{
	}

	@UiField
	MaterialPanel main;

	public MaterialPanel getMainPanel()
	{
		return main;
	}

	@Inject
	NetworkingDetailView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

	/* @Override
	public void setInSlot(Object slot, IsWidget content) {
	    if (slot == NetworkingDetailPresenter.SLOT_NetworkingDetail) {
	        main.setWidget(content);
	    } else {
	        super.setInSlot(slot, content);
	    }
	}*/
}