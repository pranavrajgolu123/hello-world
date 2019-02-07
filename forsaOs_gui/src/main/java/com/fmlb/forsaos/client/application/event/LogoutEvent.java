package com.fmlb.forsaos.client.application.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class LogoutEvent extends GwtEvent<LogoutEvent.LogoutHandler>
{
	private static Type<LogoutHandler> TYPE = new Type<LogoutHandler>();

	public interface LogoutHandler extends EventHandler
	{
		void onLogout( LogoutEvent event );
	}

	private final String message;

	public LogoutEvent( final String message )
	{
		this.message = message;
	}

	public static Type<LogoutHandler> getType()
	{
		return TYPE;
	}

	public static void fire( HasHandlers source, String message )
	{
		if ( TYPE != null )
		{
			source.fireEvent( new LogoutEvent( message ) );
		}
	}

	@Override
	protected void dispatch( final LogoutHandler handler )
	{
		handler.onLogout( this );
	}

	@Override
	public Type<LogoutHandler> getAssociatedType()
	{
		return TYPE;
	}

	public String getMessage()
	{
		return this.message;
	}
}
