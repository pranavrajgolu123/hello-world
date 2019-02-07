package com.fmlb.forsaos.client.application.services;

import org.apache.http.HttpStatus;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

public class AsyncRestRequestCallback implements RequestCallback
{

	@Override
	public void onResponseReceived( Request request, Response response )
	{
		if ( response.getStatusCode() == HttpStatus.SC_OK )
		{
			console( "Resoponse received: " );
			console( "Http Status Code : " + response.getStatusCode() );
			console( "Http Status Text : " + response.getStatusText() );
			console( "Response Text : " + response.getText() );
			return;
		}
		switch( response.getStatusCode() )
		{
		case HttpStatus.SC_BAD_REQUEST:
			console( HttpStatus.SC_BAD_REQUEST + response.getStatusText() );
			break;
		case HttpStatus.SC_UNAUTHORIZED:
			console( HttpStatus.SC_UNAUTHORIZED + response.getStatusText() );
			break;
		case HttpStatus.SC_FORBIDDEN:
			console( HttpStatus.SC_FORBIDDEN + response.getStatusText() );
			break;
		default:
			break;
		}

	}

	@Override
	public void onError( Request request, Throwable exception )
	{
		// Close loading model popups
		// Show error popup based on exception type
		// Timeouts etc
		console( exception.getMessage() );
		console( exception.getLocalizedMessage() );

		if ( exception instanceof RequestException )
		{
			// do something
		}
	}

	public static native void console( String text )
	/*-{
		console.log(text);
	}-*/;
}
