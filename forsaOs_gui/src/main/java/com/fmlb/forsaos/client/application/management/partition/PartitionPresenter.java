package com.fmlb.forsaos.client.application.management.partition;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import gwt.material.design.client.ui.MaterialLoader;

public class PartitionPresenter extends Presenter<PartitionPresenter.MyView, PartitionPresenter.MyProxy> implements PartitionUiHandlers
{
	interface MyView extends View, HasUiHandlers<PartitionUiHandlers>
	{
		public HTMLPanel getMainPanel();
	}

	@NameToken( NameTokens.PARTITION )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<PartitionPresenter>
	{
	}

	private PlaceManager placeManager;

	private PartitionDataTable partitionDataTable;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		MaterialLoader.loading( false );
		LoggerUtil.log( "on reveal Partition presenter" );
	}

	@Inject
	PartitionPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		getView().setUiHandlers( this );
		partitionDataTable = new PartitionDataTable( 500, this.uiComponentsUtil, this.placeManager, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
			}
		} );
		getView().getMainPanel().add( partitionDataTable );
	}

	@Override
	protected void onReset()
	{
		super.onReset();
		dataUpdate();
	}

	private void dataUpdate()
	{
		partitionDataTable.getUpdateIcommandWithData().executeWithData( false );
		BreadcrumbEvent.fire( PartitionPresenter.this, "Management >> Partition", NameTokens.PARTITION );
	}

}