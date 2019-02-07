package com.fmlb.forsaos.server.websocket;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint( "/forsaos/webSocketService/blink_start" )
public class BlinkStartWebsocketEndpoint
{

	@OnOpen
	public void onOpen( final Session session )
	{
		System.out.println( "blink start Open Connection ...for session " + session.getId() );
		BlinkStartWebsocketEndpointHandler.getInstance().addSessions( session );
	}

	@OnClose
	public void onClose( final Session session )
	{
		System.out.println( "blink start Close Connection ...for session " + session.getId() );
		BlinkStartWebsocketEndpointHandler.getInstance().closeSessions( session );
	}

	@OnMessage
	public String onMessage( String message, final Session session )
	{
		System.out.println( "blink start Message from the client: " + message );
		String echoMsg = "blink start Echo from the server : " + message;
		BlinkStartWebsocketEndpointHandler.getInstance().onMessageReceived( message, session );
		return echoMsg;
	}

	@OnMessage
	public void onBinaryMessage( final byte[] data, final Session session )
	{
		final String message = new String( data, Charset.forName( "US-ASCII" ) );
		System.out.println( "blink start onBinaryMessage (" + message + "," + session.getId() + "" );
		session.getAsyncRemote().sendBinary( ByteBuffer.wrap( ( "You said" + message ).getBytes() ) );
	}

	@OnError
	public void onError( Throwable e )
	{
		e.printStackTrace();
	}

}
