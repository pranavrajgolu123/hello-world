package com.fmlb.forsaos.client.application.eventlog;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;

class EventLogView extends ViewWithUiHandlers<EventLogUiHandlers> implements EventLogPresenter.MyView
{
	interface Binder extends UiBinder<Widget, EventLogView>
	{
	}

	@UiField
	MaterialPanel eventLog;

	public MaterialPanel getEventLog()
	{
		return eventLog;
	}

	@Inject
	EventLogView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}
}