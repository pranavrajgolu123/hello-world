package com.fmlb.forsaos.server.websocket;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint( "/forsaos/webSocketService/diagnostic" )
public class DiagnosticWebsocketEndpoint
{

	@OnOpen
	public void onOpen( final Session session )
	{
		System.out.println( "diagnostic Open Connection ...for session " + session.getId() );
		DiagnosticWebsocketEndpointHandler.getInstance().addSessions( session );
	}

	@OnClose
	public void onClose( final Session session )
	{
		System.out.println( "diagnostic Close Connection ...for session " + session.getId() );
		DiagnosticWebsocketEndpointHandler.getInstance().closeSessions( session );
	}

	@OnMessage
	public String onMessage( String message, final Session session )
	{
		System.out.println( "diagnostic Message from the client: " + message );
		String echoMsg = "diagnostic Echo from the server : " + message;
		DiagnosticWebsocketEndpointHandler.getInstance().onMessageReceived( message, session );
		return echoMsg;
	}

	@OnMessage
	public void onBinaryMessage( final byte[] data, final Session session )
	{
		final String message = new String( data, Charset.forName( "US-ASCII" ) );
		System.out.println( "diagnostic onBinaryMessage (" + message + "," + session.getId() + "" );
		session.getAsyncRemote().sendBinary( ByteBuffer.wrap( ( "You said" + message ).getBytes() ) );
	}

	@OnError
	public void onError( Throwable e )
	{
		e.printStackTrace();
	}

}
