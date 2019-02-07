package com.fmlb.forsaos.client.application.management.scheduler;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;

public class SchedulerView extends ViewWithUiHandlers<SchedulerUiHandlers> implements SchedulerPresenter.MyView
{
	interface Binder extends UiBinder<Widget, SchedulerView>
	{
	}

	@UiField
	MaterialPanel scheduler;

	@Inject
	SchedulerView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

	@Override
	public MaterialPanel getMainPanel()
	{
		return scheduler;
	}
}
