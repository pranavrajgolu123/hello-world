package com.fmlb.forsaos.server.websocket;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import javax.websocket.Session;

public enum DashboardWebsocketEndpointHandler
{

	INSTANCE;

	private Collection<Session> activeSessions;

	public static DashboardWebsocketEndpointHandler getInstance()
	{
		return INSTANCE;
	}

	private DashboardWebsocketEndpointHandler()
	{
		activeSessions = Collections.synchronizedCollection( new HashSet<Session>() );
	}

	public void addSessions( Session... sessions )
	{
		System.out.println( "Session Add" );
		for ( Session session : sessions )
		{
			System.out.println( "Established connection with id : " + session.getId() );
			activeSessions.add( session );
		}
	}

	public void onMessageReceived( final String message, final Session session )
	{
		System.out.println( "Message from " + session.getId() + ": " + message );
	}

	public void closeSessions( Session... sessions )
	{
		for ( Session session : sessions )
		{
			System.out.println( "Session " + session.getId() + " has ended" );
			Iterator<Session> sessionIterator = activeSessions.iterator();
			while ( sessionIterator.hasNext() )
			{
				Session existingSession = sessionIterator.next();
				if ( existingSession.getId().equals( session.getId() ) )
				{
					sessionIterator.remove();
				}

			}
		}
	}

	public void broadcastData( String message )
	{
		Iterator<Session> sessionIterator = activeSessions.iterator();
		while ( sessionIterator.hasNext() )
		{
			Session existingSession = sessionIterator.next();
			if ( existingSession != null && existingSession.isOpen() )
			{

				try
				{
					existingSession.getBasicRemote().sendText( message );
				}
				catch ( IOException e )
				{
					System.out.println( "Unable to send message to client with websocket session id : " + existingSession.getId() );
					e.printStackTrace();
					sessionIterator.remove();
				}
			}

		}
	}

}
