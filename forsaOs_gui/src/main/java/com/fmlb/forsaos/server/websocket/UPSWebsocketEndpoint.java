package com.fmlb.forsaos.server.websocket;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint( "/forsaos/webSocketService/ups" )
public class UPSWebsocketEndpoint
{

	@OnOpen
	public void onOpen( final Session session )
	{
		System.out.println( "UPS Open Connection ...for session " + session.getId() );
		UPSWebsocketEndpointHandler.getInstance().addSessions( session );
	}

	@OnClose
	public void onClose( final Session session )
	{
		System.out.println( "UPS Close Connection ...for session " + session.getId() );
		UPSWebsocketEndpointHandler.getInstance().closeSessions( session );
	}

	@OnMessage
	public String onMessage( String message, final Session session )
	{
		System.out.println( "UPS Message from the client: " + message );
		String echoMsg = "UPS Echo from the server : " + message;
		UPSWebsocketEndpointHandler.getInstance().onMessageReceived( message, session );
		return echoMsg;
	}

	@OnMessage
	public void onBinaryMessage( final byte[] data, final Session session )
	{
		final String message = new String( data, Charset.forName( "US-ASCII" ) );
		System.out.println( "UPS onBinaryMessage (" + message + "," + session.getId() + "" );
		session.getAsyncRemote().sendBinary( ByteBuffer.wrap( ( "You said" + message ).getBytes() ) );
	}

	@OnError
	public void onError( Throwable e )
	{
		e.printStackTrace();
	}

}
