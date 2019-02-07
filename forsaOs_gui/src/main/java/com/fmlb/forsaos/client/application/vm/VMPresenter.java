package com.fmlb.forsaos.client.application.vm;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.ShowVMDetailsPresenterEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.VMModel;
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

public class VMPresenter extends Presenter<VMPresenter.MyView, VMPresenter.MyProxy> implements VMUiHandlers
{
	interface MyView extends View, HasUiHandlers<VMUiHandlers>
	{
		public HTMLPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_VM = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.VM )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<VMPresenter>
	{
	}

	private PlaceManager placeManager;

	private VMDataTable vmDataTable;

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		MaterialLoader.loading( false );
		LoggerUtil.log( "on reveal VM presenter" );
	}

	@Inject
	VMPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		getView().setUiHandlers( this );
		vmDataTable = new VMDataTable( 500, this.uiComponentsUtil, this.placeManager, this.currentUser, new IcommandWithData()
		{
			@Override
			public void executeWithData( Object obj )
			{
				MaterialLoader.loading( true );
				ShowVMDetailsPresenterEvent.fire( VMPresenter.this, ( VMModel ) obj );

			}
		} );
		getView().getMainPanel().add( vmDataTable );

	}

	@Override
	protected void onReset()
	{
		super.onReset();
		dataUpdate();
	}

	private void dataUpdate()
	{
		vmDataTable.getUpdateIcommandWithData().executeWithData( false );
		BreadcrumbEvent.fire( VMPresenter.this, "Virtualization >> VM", NameTokens.VM );
	}

}