package com.fmlb.forsaos.client.application.websocket;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.shared.application.exceptions.WebsocketEndpoints;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListener;

public class WebsocketHelper
{

	public static String getBaseURL( WebsocketEndpoints endpoint )
	{
		String baseURL = GWT.getModuleBaseURL();
		baseURL = baseURL.replace( Window.Location.getProtocol(), "ws:" );
		baseURL = baseURL + endpoint.getEndpointValue();
		return baseURL;
	}

	public static Websocket getWebsocketInstance( WebsocketEndpoints endpoint, IcommandWithData omMessageRecCmd )
	{
		Websocket socket = new Websocket( getBaseURL( endpoint ) );
		socket.addListener( new WebsocketListener()
		{
			@Override
			public void onOpen()
			{
				LoggerUtil.log( endpoint.getEndpointValue() + " WEB SOCKET OPENED" );

			}

			@Override
			public void onMessage( String msg )
			{
				LoggerUtil.log( endpoint.getEndpointValue() + " WEB SOCKET ON MESSAGE from server " + msg );
				if ( msg == null )
				{
					return;
				}

				try
				{
					JSONValue parseStrict = JSONParser.parseStrict( msg );
					if ( omMessageRecCmd != null )
					{
						try
						{
							omMessageRecCmd.executeWithData( parseStrict );
						}
						catch ( Exception e )
						{
							LoggerUtil.log( "Error in excuting omMessageRecCmd with data received through web socket endpoint " + endpoint.getEndpointValue() );
							LoggerUtil.log( "Data is:" );
							LoggerUtil.log( msg );
							e.printStackTrace();
						}
					}
				}
				catch ( Exception e )
				{
					LoggerUtil.log( "Error in parsing data received through web socket endpoint " + endpoint.getEndpointValue() );
					LoggerUtil.log( "Data is:" );
					LoggerUtil.log( msg );
					e.printStackTrace();
				}
			}

			@Override
			public void onClose()
			{
				LoggerUtil.log( endpoint.getEndpointValue() + " WEB SOCKET ON close" );

			}
		} );
		socket.open();
		return socket;
	}
}
