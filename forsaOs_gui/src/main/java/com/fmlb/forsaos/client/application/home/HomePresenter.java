package com.fmlb.forsaos.client.application.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.ApplicationPresenter;
import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ClientUtil;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.NavigationLinkModel;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent;
import com.fmlb.forsaos.client.application.event.BreadcrumbEvent.BreadcrumbHandler;
import com.fmlb.forsaos.client.application.event.LogoutEvent;
import com.fmlb.forsaos.client.application.event.LogoutEvent.LogoutHandler;
import com.fmlb.forsaos.client.application.event.SelectNavigationLinkEvent;
import com.fmlb.forsaos.client.application.event.SelectNavigationLinkEvent.SelectNavigationLinkHandler;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.ProgressModel;
import com.fmlb.forsaos.client.application.models.RestartForsaOSModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.application.websocket.WebsocketHelper;
import com.fmlb.forsaos.client.place.NameTokens;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.shared.application.exceptions.WebsocketEndpoints;
import com.fmlb.forsaos.shared.application.utility.CommonUtil;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.presenter.slots.NestedSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListener;

import gwt.material.design.client.constants.LoaderSize;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialPreLoader;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements HomeUiHandlers, BreadcrumbHandler, SelectNavigationLinkHandler, LogoutHandler
{
	private List<String> sysTimes = null;

	private Timer timer;

	private String upTime;

	private String currentTime;

	private Websocket alertsWebsocket;

	private Websocket blinkStartWebsocket;

	private Websocket blinkStopWebsocket;

	interface MyView extends View, HasUiHandlers<HomeUiHandlers>
	{
		MaterialContainer getPageContainer();

		MaterialNavBrand getAppTitle();

		MaterialLink getDashboardLink();

		MaterialLink getLemLink();

		MaterialLink getVirtLink();

		HashMap<String, NavigationLinkModel> getNameTokenToNavigationLinkMap();

		MaterialLabel getSysUpTime();

		MaterialLabel getServerTime();

		MaterialPreLoader getBlinkStatusLoader();

		MaterialLink getUserNameTitle();
	}

	PlaceManager placeManager;

	private boolean userLoggedOut = false;

	private UIComponentsUtil uiComponentsUtil;

	@ProxyStandard
	@NameToken( NameTokens.HOME )
	interface MyProxy extends ProxyPlace<HomePresenter>
	{
	}

	public static final NestedSlot SLOT_HOME_CONTENT = new NestedSlot();

	private CurrentUser currentUser;

	@Override
	protected void onReveal()
	{
		super.onReveal();
		/*if ( userLoggedOut )
		{
			navigateToDashboard();
		}*/
		endTimer();
		showStatus();
	}

	@Inject
	HomePresenter( EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager, CurrentUser currentUser, UIComponentsUtil uiComponentsUtil )
	{
		super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN );
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		this.uiComponentsUtil = uiComponentsUtil;
		getView().setUiHandlers( this );
		getView().getDashboardLink().setId( "DashboardLink" + DOM.createUniqueId() );
		LoggerUtil.log( getView().getDashboardLink().getId() );
		getView().getLemLink().setId( "lemLink" + DOM.createUniqueId() );
		LoggerUtil.log( getView().getLemLink().getId() );
		getView().getBlinkStatusLoader().setLoaderSize( LoaderSize.SMALL );
		hideBlinkProgressLoader();
		// initializeAlertsSWS();
		initializeBlinkStartWS();
		initializeBlinkStopWS();
		dashBoardNavigation();
		//		getProgressOfBlink();
		LoggerUtil.log( "HomePresenter constructor" );
		ClientUtil.EVENT_BUS.addHandler( LogoutEvent.getType(), new LogoutEvent.LogoutHandler()
		{

			@Override
			public void onLogout( LogoutEvent event )
			{
				logout();

			}
		} );
	}

	private void initializeAlertsSWS()
	{
		LoggerUtil.log( "TRYING TO Initializing alertsWebsocket WEB SOCKET" );
		if ( alertsWebsocket == null )
		{
			LoggerUtil.log( "Initializing alertsWebsocket WEB SOCKET AS IT IS NULL" );
			alertsWebsocket = WebsocketHelper.getWebsocketInstance( WebsocketEndpoints.ALERTS_END_POINT, new IcommandWithData()
			{
				@Override
				public void executeWithData( Object obj )
				{
					updateAlertNotifications( ( JSONValue ) obj );
				}
			} );
		}
		else if ( alertsWebsocket.getState() == 3 )
		{
			LoggerUtil.log( "Reopening the alertsWebsocket  WEB SOCKET AS IT was closed" );
			alertsWebsocket.open();
		}
	}

	private void initializeBlinkStartWS()
	{
		LoggerUtil.log( "TRYING TO Initializing BlinkStart WEB SOCKET" );
		if ( blinkStartWebsocket == null )
		{
			LoggerUtil.log( "Initializing BlinkStart WEB SOCKET AS IT IS NULL" );
			blinkStartWebsocket = WebsocketHelper.getWebsocketInstance( WebsocketEndpoints.BLINK_START_END_POINT, new IcommandWithData()
			{
				@Override
				public void executeWithData( Object obj )
				{
					blinkStartAction( ( JSONValue ) obj );
				}
			} );
			blinkStartWebsocket.addListener( new WebsocketListener()
			{

				@Override
				public void onOpen()
				{
					getProgressOfBlink();

				}

				@Override
				public void onMessage( String msg )
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void onClose()
				{
					// TODO Auto-generated method stub

				}
			} );
		}
		else if ( blinkStartWebsocket.getState() == 3 )
		{
			LoggerUtil.log( "Reopening the BlinkStart  WEB SOCKET AS IT was closed" );
			blinkStartWebsocket.open();
		}
	}

	private void initializeBlinkStopWS()
	{
		LoggerUtil.log( "TRYING TO Initializing BlinkStop WEB SOCKET" );
		if ( blinkStopWebsocket == null )
		{
			LoggerUtil.log( "Initializing BlinkStop WEB SOCKET AS IT IS NULL" );
			blinkStopWebsocket = WebsocketHelper.getWebsocketInstance( WebsocketEndpoints.BLINK_STOP_END_POINT, new IcommandWithData()
			{
				@Override
				public void executeWithData( Object obj )
				{
					blinkStopAction( ( JSONValue ) obj );
				}
			} );
		}
		else if ( blinkStopWebsocket.getState() == 3 )
		{
			LoggerUtil.log( "Reopening the BlinkStop  WEB SOCKET AS IT was closed" );
			blinkStopWebsocket.open();
		}
	}

	@Override
	protected void onHide()
	{
		super.onHide();
		LoggerUtil.log( "on hide home PRSENETER" );
		if ( alertsWebsocket != null )
		{
			LoggerUtil.log( "close alertsWebsocket web socket connection" );
			alertsWebsocket.close();
		}
		if ( blinkStartWebsocket != null )
		{
			LoggerUtil.log( "close blinkStartWebsocket web socket connection" );
			blinkStartWebsocket.close();
		}
		if ( blinkStopWebsocket != null )
		{
			LoggerUtil.log( "close blinkStopWebsocket web socket connection" );
			blinkStopWebsocket.close();
		}
	}

	private void updateAlertNotifications( JSONValue parsedData )
	{
		JSONObject object = parsedData.isObject();
		JSONObject logsObject = object.get( "log" ).isObject();
		LoggerUtil.log( "ALERT DATA " + logsObject.toString() );
	}

	private void blinkStartAction( JSONValue parsedData )
	{
		//JSONObject object = parsedData.isObject();
		showBlinkProgressLoader();
	}

	private void showBlinkProgressLoader()
	{
		getView().getBlinkStatusLoader().setTitle( "Blink in progress" );
		getView().getBlinkStatusLoader().setVisible( true );
	}

	private void blinkStopAction( JSONValue parsedData )
	{
		//JSONObject object = parsedData.isObject();
		hideBlinkProgressLoader();
	}

	private void hideBlinkProgressLoader()
	{
		getView().getBlinkStatusLoader().setVisible( false );
	}

	@Override
	public void logout()
	{
		endTimer();
		this.currentUser.setLoggedIn( false );
		placeManager.revealDefaultPlace();
		userLoggedOut = true;
		// Cookies.setCookie( ApplicationConstants.USER_LOGGED_OUT,
		// ApplicationConstants.TRUE );
		//Cookies.setCookie( ApplicationConstants.SESSION_ID, "" );
		CommonServiceProvider.getCommonService().logout( new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				Cookies.setCookie( ApplicationConstants.SESSION_ID, "" );
			}
		} );
		clearSlot( SLOT_HOME_CONTENT );
	}

	private void navigateTo( String nameToken )
	{
		MaterialLoader.loading( true );
		LoggerUtil.log( placeManager.getCurrentPlaceRequest().toString() );
		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( nameToken ).build();
		placeManager.revealPlace( placeRequest );
	}

	@Override
	public void navigateToDashboard()
	{
		navigateTo( NameTokens.DASHBOARD );
	}

	@Override
	public void navigateToRepo()
	{
		navigateTo( NameTokens.REPO );
	}

	@Override
	public void navigateToScheduler()
	{
		navigateTo( NameTokens.SCHEDULER );
	}

	@Override
	public void navigateToManagementBlink()
	{
		navigateTo( NameTokens.BLINK );
	}

	@Override
	public void navigateToLEM()
	{
		navigateTo( NameTokens.LEM );
	}

	@Override
	public void navigateToVM()
	{
		navigateTo( NameTokens.VM );
	}

	@Override
	public void navigateToRTM()
	{
		navigateTo( NameTokens.RTM );
	}

	@Override
	public void navigateToDiagnosticsSMTP()
	{
		navigateTo( NameTokens.DIAG_SMTP );
	}

	@Override
	public void navigateToSettingsAccounts()
	{
		navigateTo( NameTokens.ACCOUNTS );
	}

	@Override
	public void navigateToSettingsSystem()
	{
		navigateTo( NameTokens.SYSTEM );
	}

	@Override
	public void navigateToSettingsSecurity()
	{
		navigateTo( NameTokens.SECURITY );
	}

	@Override
	public void navigateToLicenseManager()
	{
		navigateTo( NameTokens.LICENSEMANAGER );
	}

	@Override
	public void navigateToDiagnosticsStatus()
	{
		navigateTo( NameTokens.DIAG_STATUS );
	}

	@Override
	public void navigateToDiagnosticsMacAddresses()
	{
		// navigateTo( NameTokens.DIAG_MAC_ADDRESSES );
	}

	@Override
	public void navigateToNetworking()
	{
		navigateTo( NameTokens.NETWORKING );
	}

	@Override
	public void navigateToEventLog()
	{
		navigateTo( NameTokens.EVENTLOG );
	}

	@Override
	public void navigateToPartition()
	{
		navigateTo( NameTokens.PARTITION );
	}

	@Override
	protected void onBind()
	{
		super.onBind();
		addRegisteredHandler( BreadcrumbEvent.getType(), HomePresenter.this );
		addRegisteredHandler( SelectNavigationLinkEvent.getType(), HomePresenter.this );
		addRegisteredHandler( LogoutEvent.getType(), HomePresenter.this );
	}

	@Override
	public void onBreadcrumb( BreadcrumbEvent event )
	{
		LoggerUtil.log( "bread crum event received" + event );
		LoggerUtil.log( "Is user logged out " + userLoggedOut );
		if ( event != null )
		{
			getView().getAppTitle().setText( event.getTitle() );
			if ( userLoggedOut && getView().getNameTokenToNavigationLinkMap() != null && getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ) != null )
			{
				if ( null != getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ).getParentLink() )
				{
					getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ).getParentLink().$this().click();
				}
				LoggerUtil.log( "before clikc" );
				if ( getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ) != null && getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ).getLink() != null )
				{
					getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ).getLink().$this().click();
				}
				LoggerUtil.log( "after click" );
			}
		}

	}

	@Override
	public void onSelectNavigationLink( SelectNavigationLinkEvent event )
	{
		LoggerUtil.log( "select navigation event received" + event );
		if ( event != null )
		{
			if ( getView().getNameTokenToNavigationLinkMap() != null && getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ) != null )
			{
				if ( null != getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ).getParentLink() )
				{
					getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ).getParentLink().$this().click();
				}
				LoggerUtil.log( "before clikc" );
				if ( getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ) != null && getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ).getLink() != null )
				{
					getView().getNameTokenToNavigationLinkMap().get( event.getNameToken() ).getLink().$this().click();
				}
				LoggerUtil.log( "after click" );
			}
		}

	}

	@Override
	public void shutdownForsaOs()
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "This will stop all ForsaOS services." );
		warningMsgList.add( "Do you want to continue?" );
		ConfirmPasswordPopup confirmPasswordPopup = new ConfirmPasswordPopup( "Shutdown ForsaOS", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{

				CommonServiceProvider.getCommonService().shutdownForsaOs( new ApplicationCallBack<Boolean>()
				{

					@Override
					public void onSuccess( Boolean result )
					{
						if ( result != null && result )
						{
							redirectToLogin();
						}
					}
				} );
			}
		} );
		confirmPasswordPopup.open();
	}

	@Override
	public void restartForsaOs()
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "This may take several minutes to restart ForsaOS services." );
		warningMsgList.add( "Do you want to continue?" );
		ConfirmPasswordPopup confirmPasswordPopup = new ConfirmPasswordPopup( "Restart ForsaOS", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				CommonServiceProvider.getCommonService().restartForsaOs( new RestartForsaOSModel(), new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						if ( result != null && result )
						{
							redirectToLogin();
						}

					}
				} );
			}
		} );
		confirmPasswordPopup.open();

	}

	@Override
	public void shutdownSystem()
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "System connectivity will be lost." );
		warningMsgList.add( "Do you want to continue?" );
		ConfirmPasswordPopup confirmPasswordPopup = new ConfirmPasswordPopup( "Shutdown Host", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				CommonServiceProvider.getCommonService().shutdownSystem( new ApplicationCallBack<Boolean>()
				{

					@Override
					public void onSuccess( Boolean result )
					{
						if ( result != null && result )
						{
							redirectToLogin();
						}

					}
				} );
			}
		} );
		confirmPasswordPopup.open();

	}

	@Override
	public void restartSystem()
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "It may take several minutes for the system to come up." );
		warningMsgList.add( "Do you want to continue?" );
		ConfirmPasswordPopup confirmPasswordPopup = new ConfirmPasswordPopup( "Restart Host", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				CommonServiceProvider.getCommonService().restartSystem( new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						if ( result != null && result )
						{
							redirectToLogin();
						}

					}
				} );

			}
		} );
		confirmPasswordPopup.open();

	}

	private void redirectToLogin()
	{
		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.LOGIN ).build();
		placeManager.revealPlace( placeRequest, true );
	}

	@Override
	public void onLogout( LogoutEvent event )
	{
		endTimer();
		LoggerUtil.log( "bread crum event received" + event );
		LoggerUtil.log( "Is user logged out " + userLoggedOut );
		if ( event != null )
		{
			logout();
		}
	}

	private void showStatus()
	{
		CommonServiceProvider.getCommonService().sysBootTime( new ApplicationCallBack<List<String>>()
		{
			@Override
			public void onSuccess( List<String> result )
			{
				sysTimes = result;
				startTimer();
			}
		} );
	}

	public void startTimer()
	{
		if ( sysTimes != null && !sysTimes.isEmpty() )
		{
			upTime = sysTimes.get( 0 ).trim();
			currentTime = sysTimes.get( 1 );

			DateTimeFormat timeformat = DateTimeFormat.getFormat( CommonUtil.SYS_DATE_TIME_FORMAT );
			DateTimeFormat displayFormat = DateTimeFormat.getFormat( CommonUtil.DATE_TIME_FORMAT );
			Date startDate = timeformat.parse( upTime );
			Date currentDate = timeformat.parse( currentTime );

			timer = new Timer()
			{
				@Override
				public void run()
				{
					//LoggerUtil.log( "Timer is running" );
					currentDate.setTime( currentDate.getTime() + 1000 );
					long time = currentDate.getTime() - startDate.getTime();
					long days = ( long ) Math.floor( time / ( 1000 * 60 * 60 * 24 ) );
					long hours = ( long ) Math.floor( ( time % ( 1000 * 60 * 60 * 24 ) ) / ( 1000 * 60 * 60 ) );
					long minutes = ( long ) Math.floor( ( time % ( 1000 * 60 * 60 ) ) / ( 1000 * 60 ) );
					long seconds = ( long ) Math.floor( ( time % ( 1000 * 60 ) ) / 1000 );

					String counter = days + " DAY" + ( days == 1 ? "" : "S | " ) + ( hours < 10 ? "0" + hours : hours ) + "h : " + ( minutes < 10 ? "0" + minutes : minutes ) + "m : " + ( seconds < 10 ? "0" + seconds : seconds ) + "s";

					getView().getSysUpTime().setText( counter );
					getView().getServerTime().setText( displayFormat.format( currentDate ) );
				}
			};
			timer.scheduleRepeating( 1000 );
		}
	}

	public void endTimer()
	{
		if ( timer != null )
		{
			LoggerUtil.log( "Timer is ended" );
			timer.cancel();
		}
	}

	// This method gets called for every time a presenter is shown as this is the main slot
	@Override
	protected void onReset()
	{
		super.onReset();
		getView().getUserNameTitle().setText( currentUser.getUserName() );
		getView().getUserNameTitle().setTitle( currentUser.getUserName() );
		if ( userLoggedOut )
		{
			userLoggedOut = false;
			dashBoardNavigation();
			hideBlinkProgressLoader();
			// initializeAlertsSWS();
			initializeBlinkStartWS();
			initializeBlinkStopWS();
		}

	}

	// This method gets called for every time a presenter is shown as this is the called in on Reset
	// We need to call this method on reset as dashboard is the default screen when a user logs in
	// But this also causes the dashboard screen to load multiple times if a user logs out from the dashboard screen.
	private void dashBoardNavigation()
	{
		if ( this.placeManager != null && this.placeManager.getCurrentPlaceHierarchy() != null && !this.placeManager.getCurrentPlaceHierarchy().isEmpty() )
		{
			if ( this.placeManager.getCurrentPlaceRequest().getParameter( "navigateDashBoard", "" ).equals( "true" ) )
			{
				//navigateToDashboard();
				PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.DASHBOARD ).with( "chekUPS", "true" ).build();
				placeManager.revealPlace( placeRequest, true );
			}
		}
	}

	private void getProgressOfBlink()
	{
		CommonServiceProvider.getCommonService().getProgress( new ApplicationCallBack<ProgressModel>()
		{
			@Override
			public void onSuccess( ProgressModel progress )
			{
				if ( progress.getProgress() > 0 )
				{
					showBlinkProgressLoader();
				}
				else
				{
					hideBlinkProgressLoader();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

}
