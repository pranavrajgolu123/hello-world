package com.fmlb.forsaos.client.application.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.WarningPopup;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.home.HomePresenter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.UPSModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;

public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy> implements DashboardUiHandlers
{
	interface MyView extends View, HasUiHandlers<DashboardUiHandlers>
	{
		public MaterialPanel getDashboard();
	}

	@NameToken( NameTokens.DASHBOARD )
	@ProxyStandard
	interface MyProxy extends ProxyPlace<DashboardPresenter>
	{
	}

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	private Icommand cancelTimerCmd;

	private DashboardCharts dashboardCharts;

	private PlaceManager placeManager;

	@Inject
	DashboardPresenter( EventBus eventBus, MyView view, MyProxy proxy, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, PlaceManager placeManager )
	{
		super( eventBus, view, proxy, HomePresenter.SLOT_HOME_CONTENT );
		LoggerUtil.log( "DashboardPresenter Constructor" );
		getView().setUiHandlers( this );
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.placeManager = placeManager;
	}

	@Override
	protected void onReveal()
	{
		super.onReveal();
		LoggerUtil.log( "DashboardPresenter reveal" );
		LoggerUtil.log( "dashboard reveal" );
	}

	@Override
	protected void onReset()
	{
		super.onReset();
		LoggerUtil.log( "DashboardPresenter reset" );
		if ( this.placeManager != null && this.placeManager.getCurrentPlaceHierarchy() != null && !this.placeManager.getCurrentPlaceHierarchy().isEmpty() )
		{
			if ( this.placeManager.getCurrentPlaceRequest().getParameter( "navigateDashBoard", "" ).equals( "true" ) )
			{
				this.placeManager.getCurrentPlaceRequest().getParameter( "navigateDashBoard", "" ).replaceAll( "navigateDashBoard", "" );
			}
		}
		dataUpdate();
		checkUPSList();
	}

	private void dataUpdate()
	{
		LoggerUtil.log( "DashboardPresenter data update" );
		if ( dashboardCharts != null && dashboardCharts.getDashboardChartsbWebsocket() != null )
		{
			LoggerUtil.log( "dashboard close web socket connection" );
			dashboardCharts.getDashboardChartsbWebsocket().close();
		}
		dashboardCharts = new DashboardCharts( this.uiComponentsUtil, currentUser );
		getView().getDashboard().clear();
		getView().getDashboard().add( dashboardCharts );
		BreadcrumbEvent.fire( DashboardPresenter.this, "Dashboard", NameTokens.DASHBOARD );
		MaterialLoader.loading( false );
	}

	@Override
	protected void onHide()
	{
		super.onHide();
		LoggerUtil.log( "DashboardPresenter hide" );
		LoggerUtil.log( "on hide" );
		if ( dashboardCharts.getTimer() != null )
		{
			LoggerUtil.log( "cancel cmd execute" );
			dashboardCharts.getTimer().cancel();
		}
		if ( dashboardCharts.getDashboardChartsbWebsocket() != null )
		{
			LoggerUtil.log( "close web socket connection" );
			dashboardCharts.getDashboardChartsbWebsocket().close();
		}
	}

	private void checkUPSList()
	{
		LoggerUtil.log( "DashboardPresenter check ups" );
		if ( this.placeManager != null && this.placeManager.getCurrentPlaceHierarchy() != null && !this.placeManager.getCurrentPlaceHierarchy().isEmpty() )
		{
			if ( this.placeManager.getCurrentPlaceRequest().getParameter( "chekUPS", "" ).equals( "true" ) )
			{
				CommonServiceProvider.getCommonService().getUPS( null, new ApplicationCallBack<List<UPSModel>>()
				{
					@Override
					public void onSuccess( List<UPSModel> updModelList )
					{
						if ( updModelList.size() == 0 )
						{
							LoggerUtil.log( "DashboardPresenter show error popo" );
							ArrayList<String> errorMsgs = new ArrayList<>();
							errorMsgs.add( "UPS requires attention: UPS is not registered. Recommend to capture a BLINK. " );
							errorMsgs.add( "Please take appropriate action." );
							errorMsgs.add( "Do you acknowledge and wish to proceed?" );
							WarningPopup warningPopup = new WarningPopup( errorMsgs, ApplicationConstants.WARNING_POPUP_HEADER, "", ApplicationConstants.YES, true );
							warningPopup.getButton().addClickHandler( new ClickHandler()
							{
								@Override
								public void onClick( ClickEvent event )
								{
									warningPopup.close();
									PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.DIAG_STATUS ).with( "navigateToUPS", "true" ).build();
									placeManager.revealPlace( placeRequest, true );

								}
							} );
							MaterialLoader.loading( false );
							warningPopup.open();
						}
					}

					/*@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );
					}*/
				} );
			}
		}
	}

}