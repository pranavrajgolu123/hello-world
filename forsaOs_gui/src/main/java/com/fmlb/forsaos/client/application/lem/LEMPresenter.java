package com.fmlb.forsaos.client.application.lem;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.ShowLEMDetailsPresenterEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import gwt.material.design.client.ui.MaterialLoader;

public class LEMPresenter extends Presenter<LEMPresenter.MyView, LEMPresenter.MyProxy> implements LEMUiHandlers
{
	interface MyView extends View, HasUiHandlers<LEMUiHandlers>
	{
		public HTMLPanel getMainPanel();

	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_LEM = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.LEM )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<LEMPresenter>
	{
	}

	private LEMDataTable lemDataTable;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		LoggerUtil.log( "on reveal lem presenter" );
	}

	@Inject
	LEMPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		LoggerUtil.log( "constructor lem presenter" );
		this.currentUser = currentUser;
		this.uiComponentsUtil = uiComponentsUtil;
		getView().setUiHandlers( this );

		lemDataTable = new LEMDataTable( 500, this.uiComponentsUtil, this.currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				MaterialLoader.loading( true );
				ShowLEMDetailsPresenterEvent.fire( LEMPresenter.this, ( LEMModel ) obj );

			}
		} );
		// getView().getMainPanel().setHeight("500");
		getView().getMainPanel().add( lemDataTable );
		//ShowLEMDetailsPresenterEvent.fire( this, new LEMModel() );

	}

	@Override
	protected void onReset()
	{
		super.onReset();
		LoggerUtil.log( "on reset lem presenter" );
		dataUpdate();
	}

	private void dataUpdate()
	{
		MaterialLoader.loading( false );
		lemDataTable.getUpdateIcommandWithData().executeWithData( false );
		BreadcrumbEvent.fire( LEMPresenter.this, "Virtualization >> LEM", NameTokens.LEM );
	}

}