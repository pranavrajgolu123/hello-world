package com.fmlb.forsaos.client.application.dashboard;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;

class DashboardView extends ViewWithUiHandlers<DashboardUiHandlers> implements DashboardPresenter.MyView
{
	interface Binder extends UiBinder<Widget, DashboardView>
	{
	}

	@UiField
	MaterialPanel dashboard;

	public MaterialPanel getDashboard()
	{
		return dashboard;
	}

	@Inject
	DashboardView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

	/*
	 * @Override public void setInSlot(Object slot, IsWidget content) { if (slot ==
	 * DashboardPresenter.SLOT_Dashboard) { dashboardContent.setWidget(content); }
	 * else { super.setInSlot(slot, content); } }
	 */
}