package com.fmlb.forsaos.client.application.management.networking;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class NetworkingView extends ViewWithUiHandlers<NetworkingUiHandlers> implements NetworkingPresenter.MyView {
    interface Binder extends UiBinder<Widget, NetworkingView> {
    }

    @UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel()
	{
		return main;
	}

    @Inject
    NetworkingView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
   /* @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == NetworkingPresenter.SLOT_Networking) {
            main.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }*/
}