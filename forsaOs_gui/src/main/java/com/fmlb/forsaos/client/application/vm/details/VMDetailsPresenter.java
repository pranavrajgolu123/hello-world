package com.fmlb.forsaos.client.application.vm.details;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.ShowVMDetailsPresenterEvent;
import com.fmlb.forsaos.client.application.event.ShowVMDetailsPresenterEvent.ShowVMDetailsPresenterHandler;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;

public class VMDetailsPresenter extends Presenter<VMDetailsPresenter.MyView, VMDetailsPresenter.MyProxy> implements VMDetailsUiHandlers, ShowVMDetailsPresenterHandler
{
	private VMDetails vmDetails;

	private Timer timer;

	interface MyView extends View, HasUiHandlers<VMDetailsUiHandlers>
	{
		public MaterialPanel getMainPanel();
	}

	@ContentSlot
	public static final Type<RevealContentHandler< ? >> SLOT_VMDetails = new Type<RevealContentHandler< ? >>();

	@NameToken( NameTokens.VM_DETAILS )
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<VMDetailsPresenter>
	{
	}

	@Override
	protected void onReveal()
	{
		super.onReveal();
		vmDetails = new VMDetails( vmModel, this.uiComponentsUtil, currentUser, new Icommand()
		{

			@Override
			public void execute()
			{
				/*
				if ( !placeManager.getCurrentPlaceRequest().getNameToken().equals( NameTokens.vm ) )
				{
					MaterialLoader.loading( true );
					LoggerUtil.log( placeManager.getCurrentPlaceRequest().toString() );
					PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.vm ).build();
					placeManager.revealPlace( placeRequest );
				}
				
				*/}
		} );
		getView().getMainPanel().clear();
		getView().getMainPanel().add( vmDetails );
		startVMMonitoring( vmModel.getVmName() );
		vmDetails.initializevmStatsSWS();
		BreadcrumbEvent.fire( VMDetailsPresenter.this, "Virtualization >> VM >> " + vmModel.getName(), NameTokens.VM );
		MaterialLoader.loading( false );
	}

	private UIComponentsUtil uiComponentsUtil;

	private VMModel vmModel;

	private CurrentUser currentUser;

	private PlaceManager placeManager;

	@Inject
	VMDetailsPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.placeManager = placeManager;
		getView().setUiHandlers( this );
	}

	@ProxyEvent
	@Override
	public void onShowVMDetailsPresenter( ShowVMDetailsPresenterEvent event )
	{

		vmModel = event.getVMModel();
		LoggerUtil.log( "event received" + event.getVMModel().getVmName() );
		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.VM_DETAILS ).build();
		placeManager.revealPlace( placeRequest );
	}

	@Override
	protected void onHide()
	{
		super.onHide();
		if ( vmDetails != null && vmDetails.isAttached() )
		{
			vmDetails.removeFromParent();
		}
		if ( vmDetails != null && vmDetails.getVmStatsWebsocket() != null )
		{
			LoggerUtil.log( "close VmStats  web socket connection" );
			vmDetails.getVmStatsWebsocket().close();
		}
		if ( timer != null )
		{
			timer.cancel();
		}
		stopVMMonitoring();

	}

	private void startVMMonitoring( String vmName )
	{
		timer = new Timer()
		{
			@Override
			public void run()
			{
				JSONObject vmObj = new JSONObject();
				vmObj.put( "name", RestCallUtil.getJSONString( vmName ) );
				LoggerUtil.log( "Start vm monitoring with req " + vmObj.toString() );
				CommonServiceProvider.getCommonService().startVMMonitoring( vmObj.toString(), new ApplicationCallBack<Boolean>()
				{

					@Override
					public void onSuccess( Boolean result )
					{
						LoggerUtil.log( "Success vm monitoring with req " + vmObj.toString() );
					}

				} );
			}
		};
		// Schedule the timer to run once in 300 seconds.
		timer.scheduleRepeating( 300000 );
		timer.run();
	}

	private void stopVMMonitoring()
	{
		LoggerUtil.log( "Stop vm monitoring with req " );
		CommonServiceProvider.getCommonService().stopVMMonitoring( new ApplicationCallBack<Boolean>()
		{

			@Override
			public void onSuccess( Boolean result )
			{
				LoggerUtil.log( "Success Stop vm monitoring with req " );

			}

		} );
	}
}