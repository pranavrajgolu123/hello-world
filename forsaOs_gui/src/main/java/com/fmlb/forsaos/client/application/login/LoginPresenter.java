
package com.fmlb.forsaos.client.application.login;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.UserSessionObject;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;

public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> implements LoginUiHandlers
{

	interface MyView extends View, HasUiHandlers<LoginUiHandlers>
	{
		public MaterialButton getBtnLogin();

		public MaterialLabel getErrorLabel();
	}

	@NameToken( NameTokens.LOGIN )
	@ProxyCodeSplit
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<LoginPresenter>
	{
	}

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	@Inject
	LoginPresenter( EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager, CurrentUser currentUser/* ,RestCallUtil restCallUtil */ )
	{
		super( eventBus, view, proxy, /* ApplicationPresenter.SLOT_MAIN */RevealType.Root );
		LoggerUtil.log( "login presenter " );
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		getView().setUiHandlers( this );
		if ( Cookies.getCookie( "sessionId" ) != null && Cookies.getCookie( "sessionId" ) != "" )
		{
			CommonServiceProvider.getCommonService().isSessionValid( Cookies.getCookie( "sessionId" ), new ApplicationCallBack<CurrentUser>()
			{

				@Override
				public void onSuccess( CurrentUser result )
				{
					if ( result.isLoggedIn() )
					{
						currentUser.setLoggedIn( result.isLoggedIn() );
						currentUser.setUserName( result.getUserName() );
						currentUser.setWindowLocation( result.getWindowLocation() );
						currentUser.setPassword( result.getPassword() );
						MaterialLoader.loading( false );
						currentUser.setLoggedIn( true );
						LoggerUtil.log( placeManager.getCurrentPlaceRequest().getNameToken() );
						LoggerUtil.log( History.getToken() );
						LoggerUtil.log( "Firing select navigation event" );
						String historyToken = History.getToken();
						if ( History.getToken().contains( "?" ) )
						{
							historyToken = historyToken.split( "\\?" )[0];
						}
						LoggerUtil.log( "history token is " + historyToken );
						PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( historyToken ).build();
						placeManager.revealPlace( placeRequest, true );

					}

				}
			} );
		}

		else
		{
			MaterialLoader.loading( false );
		}
	}

	@Override
	protected void onReveal()
	{
		super.onReveal();
		LoggerUtil.log( "on reveal log im" );
		LoggerUtil.log( "on reveal login presenter" + currentUser.isLoggedIn() );
		/*if ( Cookies.getCookie( "sessionId" ) != null && Cookies.getCookie( "sessionId" )!="")
		{
			CommonServiceProvider.getCommonService().isSessionValid( Cookies.getCookie( "sessionId" ), new ApplicationCallBack<Boolean>()
			{
		
				@Override
				public void onSuccess( Boolean result )
				{
					MaterialLoader.loading( false );
					currentUser.setLoggedIn( true );
					LoggerUtil.log(  placeManager.getCurrentPlaceRequest().getNameToken()  );
					LoggerUtil.log(History.getToken());
					PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(History.getToken()).build();
					placeManager.revealPlace( placeRequest, true );
		
				}
		
				@Override
				public void onFailure( Throwable caught )
				{
					MaterialLoader.loading( false );
		
				}
			} );
		}
		
		else
		{
			MaterialLoader.loading( false );
		}*/

	}

	@Override
	protected void onReset()
	{
		// TODO Auto-generated method stub
		super.onReset();
		LoggerUtil.log( "on reset log im" );
		/*		MaterialLoader.loading( true );
				if ( Cookies.getCookie( "sessionId" ) != null )
				{
					CommonServiceProvider.getCommonService().isSessionValid( Cookies.getCookie( "sessionId" ), new ApplicationCallBack<Boolean>()
					{
		
						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false );
		
							PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( placeManager.getCurrentPlaceRequest().getNameToken() ).build();
							placeManager.revealPlace( placeRequest, false );
		
						}
		
						@Override
						public void onFailure( Throwable caught )
						{
							MaterialLoader.loading( false );
		
						}
					} );
				}
		
				else
				{
					MaterialLoader.loading( false );
				}*/
	}

	@Override
	public void confirm( String username, String password )
	{
		if ( username == null || username.isEmpty() || password == null || password.isEmpty() )
		{
			getView().getErrorLabel().getElement().getStyle().setVisibility( Visibility.VISIBLE );
			getView().getErrorLabel().setText( "Username or Password cannot be empty" );
			return;
		}

		MaterialLoader.loading( true );
		currentUser.setLoggedIn( false );
		currentUser.setUserName( username );
		currentUser.setPassword( password );
		
		// ###################### FOR PROD ########################################
		
		currentUser.setWindowLocation( "10.10.5.202" );
		
		//#########################################################################
		
		CommonServiceProvider.getCommonService().doLogin( currentUser, new ApplicationCallBack<UserSessionObject>()
		{
			@Override
			public void onSuccess( UserSessionObject userSessionObject )
			{
				if ( userSessionObject.getSessionId() != "" )
				{
					Cookies.setCookie( "sessionId", userSessionObject.getSessionId() );
					currentUser.setLoggedIn( true );
					currentUser.setUserName( username );
					currentUser.setPassword( password );

					PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken( NameTokens.HOME ).with( "navigateDashBoard", "true" ).build();
					placeManager.revealPlace( placeRequest, false );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailure( caught );
				currentUser.setLoggedIn( false );
				getView().getErrorLabel().getElement().getStyle().setVisibility( Visibility.VISIBLE );
				getView().getErrorLabel().setText( "Invalid Username or Password" );
				MaterialLoader.loading( false );
			}
		} );
	}

}
