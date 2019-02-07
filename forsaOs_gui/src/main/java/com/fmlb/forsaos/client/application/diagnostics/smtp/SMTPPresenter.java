package com.fmlb.forsaos.client.application.diagnostics.smtp;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
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
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import gwt.material.design.client.ui.MaterialLoader;

public class SMTPPresenter extends Presenter<SMTPPresenter.MyView, SMTPPresenter.MyProxy> implements SMTPUiHandlers
{
	interface MyView extends View, HasUiHandlers<SMTPUiHandlers>
	{
		public HTMLPanel getMainPanel();

	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_SMTP = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.DIAG_SMTP )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SMTPPresenter>
	{
	}

	private SMTPPanel smtpDataTable;

	private UIComponentsUtil uiComponentsUtil;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
	}

	@Inject
	SMTPPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		LoggerUtil.log( "constructor lem presenter" );
		this.currentUser = currentUser;
		this.uiComponentsUtil = uiComponentsUtil;
		getView().setUiHandlers( this );

		smtpDataTable = new SMTPPanel( this.uiComponentsUtil, this.placeManager, this.currentUser, new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{

			}
		} );

		getView().getMainPanel().add( smtpDataTable );

	}

	@Override
	protected void onReset()
	{
		super.onReset();
		LoggerUtil.log( "on reset SMTP presenter" );
		dataUpdate();
	}

	private void dataUpdate()
	{
		MaterialLoader.loading( false );
		smtpDataTable.updateData();
		BreadcrumbEvent.fire( SMTPPresenter.this, "Diagnostic >> SMTP", NameTokens.DIAG_SMTP );
	}
}
