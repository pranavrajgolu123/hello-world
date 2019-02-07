package com.fmlb.forsaos.client.application.eventlog;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import gwt.material.design.client.ui.MaterialPanel;

public class EventLogPresenter extends Presenter<EventLogPresenter.MyView, EventLogPresenter.MyProxy> implements EventLogUiHandlers
{
	interface MyView extends View, HasUiHandlers<EventLogUiHandlers>
	{
		public MaterialPanel getEventLog();
	}

	@NameToken( NameTokens.EVENTLOG )
	@ProxyStandard
	interface MyProxy extends ProxyPlace<EventLogPresenter>
	{
	}
	
	private EventLogDataTable eventLogDataTable;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	@Inject
	EventLogPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );

		getView().setUiHandlers( this );
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
	}

	@Override
	protected void onReveal()
	{
		super.onReveal();
		LoggerUtil.log( "EventLog reveal" );
	}

	@Override
	protected void onReset()
	{
		super.onReset();
		dataUpdate();
	}

	private void dataUpdate()
	{
		getView().getEventLog().clear();
		BreadcrumbEvent.fire( EventLogPresenter.this, "Event Log", NameTokens.EVENTLOG );
		
		eventLogDataTable = new EventLogDataTable( this.uiComponentsUtil, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
			}
		} );
		getView().getEventLog().add( eventLogDataTable );
		
		eventLogDataTable.getUpdateIcommandWithData().executeWithData( false );
	}

	@Override
	protected void onHide()
	{
		super.onHide();
		LoggerUtil.log( "on hide" );
	}
}