package com.fmlb.forsaos.client.application.management.scheduler;

import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.management.repo.REPOPresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;

@SuppressWarnings( "deprecation" )
public class SchedulerPresenter extends Presenter<SchedulerPresenter.MyView, SchedulerPresenter.MyProxy> implements SchedulerUiHandlers
{
	interface MyView extends View, HasUiHandlers<SchedulerUiHandlers>
	{

		MaterialPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_SCHEDULER = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.SCHEDULER )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SchedulerPresenter>
	{

	}

	@SuppressWarnings( "unused" )
	private PlaceManager placeManager;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	private Icommand navigateCmd;

	private SchedulerDataTable currentSchedulesTable;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		MaterialLoader.loading( false );
		LoggerUtil.log( "on reveal scheduler presenter" );
	}

	@Inject
	SchedulerPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		getView().setUiHandlers( this );
		currentSchedulesTable = new SchedulerDataTable( this.uiComponentsUtil, this.placeManager, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
			}
		} );
		currentSchedulesTable.setId( "CurrentSchedule" );
		getView().getMainPanel().add( currentSchedulesTable );
	}

	@Override
	protected void onReset()
	{
		super.onReset();
		dataUpdate();
	}

	private void dataUpdate()
	{
		currentSchedulesTable.getUpdateIcommandWithData().executeWithData( true );
		BreadcrumbEvent.fire( SchedulerPresenter.this, "Management >> Scheduler", NameTokens.SCHEDULER );
	}

}
