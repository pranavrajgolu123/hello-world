package com.fmlb.forsaos.client.application.settings.licenseManager;

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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;

public class LicensePresenter extends Presenter<LicensePresenter.MyView, LicensePresenter.MyProxy> implements LicenseUiHandlers
{
	interface MyView extends View, HasUiHandlers<LicenseUiHandlers>
	{

		MaterialPanel getMainPanel();
	}

	@NameToken( NameTokens.LICENSEMANAGER )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<LicensePresenter>
	{

	}

	private LicenseManagerTabContent licenseDataTable;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		//MaterialLoader.loading( false );
		LoggerUtil.log( "on reveal LicenseManager presenter" );
	}

	@Inject
	LicensePresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		getView().setUiHandlers( this );
		licenseDataTable = new LicenseManagerTabContent( this.uiComponentsUtil, this.currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				//MaterialLoader.loading( false );
				licenseDataTable.getUpdateIcommandWithData().executeWithData( true );

			}
		} );
		getView().getMainPanel().add( licenseDataTable );
	}

	@Override
	protected void onBind()
	{
		super.onBind();
		LoggerUtil.log( "on bind license presenter" );
	}

	@Override
	protected void onReset()
	{
		super.onReset();
		dataUpdate();
	}

	private void dataUpdate()
	{
		MaterialLoader.loading( false );
		licenseDataTable.getUpdateIcommandWithData().executeWithData( true );
		BreadcrumbEvent.fire( LicensePresenter.this, "Settings >> License Manager", NameTokens.LICENSEMANAGER );
	}

}
