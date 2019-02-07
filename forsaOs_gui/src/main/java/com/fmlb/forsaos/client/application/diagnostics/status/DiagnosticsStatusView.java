package com.fmlb.forsaos.client.application.diagnostics.status;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class DiagnosticsStatusView extends ViewWithUiHandlers<DiagnosticsStatusUiHandlers> implements DiagnosticsStatusPresenter.MyView {
	interface Binder extends UiBinder<Widget, DiagnosticsStatusView> {
	}

	@UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel() {
		return main;
	}

	@Inject
	DiagnosticsStatusView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/*
	 * @Override public void setInSlot(Object slot, IsWidget content) { if (slot ==
	 * StatusPresenter.SLOT_Status) { main.setWidget(content); } else {
	 * super.setInSlot(slot, content); } }
	 */
}