package com.fmlb.forsaos.client.application.management.blink;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.ShowBlinkDetailsPresenterEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.BlinkModel;
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
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import gwt.material.design.client.ui.MaterialLoader;

public class BlinkPresenter extends Presenter<BlinkPresenter.MyView, BlinkPresenter.MyProxy> implements BlinkUiHandlers
{
	interface MyView extends View, HasUiHandlers<BlinkUiHandlers>
	{
		public HTMLPanel getMainPanel();

	}

	@NameToken( NameTokens.BLINK )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<BlinkPresenter>
	{
	}

	private BlinkDataTable blinkDataTable;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		MaterialLoader.loading( false );
		LoggerUtil.log( "on reveal blink presenter" );
	}

	@Inject
	BlinkPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		LoggerUtil.log( "constructor blink presenter" );
		this.currentUser = currentUser;
		this.uiComponentsUtil = uiComponentsUtil;
		getView().setUiHandlers( this );

		blinkDataTable = new BlinkDataTable( 500, this.uiComponentsUtil, this.currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				MaterialLoader.loading( true );
				ShowBlinkDetailsPresenterEvent.fire( BlinkPresenter.this, ( BlinkModel ) obj );

			}
		} );

		getView().getMainPanel().add( blinkDataTable );

	}

	@Override
	protected void onReset()
	{
		super.onReset();
		LoggerUtil.log( "on reset blink presenter" );
		dataUpdate();
	}

	private void dataUpdate()
	{
		MaterialLoader.loading( false );
		blinkDataTable.getUpdateIcommandWithData().executeWithData( false );
		BreadcrumbEvent.fire( BlinkPresenter.this, "Management >> Blink", NameTokens.BLINK );
	}

}