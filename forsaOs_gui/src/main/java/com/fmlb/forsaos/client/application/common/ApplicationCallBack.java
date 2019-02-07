package com.fmlb.forsaos.client.application.common;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.event.LogoutEvent;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;

public abstract class ApplicationCallBack<T> implements AsyncCallback<T>
{
	@Override
	public void onFailure( Throwable caught )
	{
		validateSession( caught );
	}

	public void validateSession( Throwable caught )
	{
		MaterialLoader.loading( false );
		if ( caught != null && caught instanceof FBException && isSessionExpired( ( ( FBException ) caught ).getErrorMessage() ) )
		{
			Cookies.removeCookie( ApplicationConstants.SESSION_ID );
			redirectToLogin();

			return;
		}
	}

	public void onFailureShowErrorPopup( Throwable caught, String customErrorMessage )
	{
		MaterialLoader.loading( false );
		if ( caught != null && caught instanceof FBException && isSessionExpired( ( ( FBException ) caught ).getErrorMessage() ) )
		{
			Cookies.removeCookie( ApplicationConstants.SESSION_ID );
			redirectToLogin();

			return;
		}
		customErrorMessage = customErrorMessage != null ? ( customErrorMessage + ". " ) : "Request failed. ";

		List<String> errorMessages = new ArrayList<String>();
		errorMessages.add( customErrorMessage );

		if ( caught instanceof FBException )
		{
			FBException fbException = ( FBException ) caught;
			errorMessages.add( fbException.getErrorMessage() );
			ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
			errorPopup.open();
		}
		else
		{
			ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
			errorPopup.open();
		}
	}

	private void redirectToLogin()
	{
		ClientUtil.EVENT_BUS.fireEvent( new LogoutEvent( "" ) );
		/*UrlBuilder createUrlBuilder = Window.Location.createUrlBuilder();
		createUrlBuilder.setProtocol( Window.Location.getProtocol() ).setHost( Window.Location.getHostName() ).setPath( Window.Location.getPath() ).setHash( "#/login" );
		Window.Location.assign( createUrlBuilder.buildString() );*/
	}

	private boolean isSessionExpired( String message )
	{
		if ( message != null && message.trim().length() != 0 && message.equalsIgnoreCase( ApplicationConstants.INVALID_SESSION ) )
		{
			return true;
		}
		return false;
	}

};