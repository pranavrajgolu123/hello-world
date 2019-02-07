package com.fmlb.forsaos.client.application.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SelectNavigationLinkEvent extends GwtEvent<SelectNavigationLinkEvent.SelectNavigationLinkHandler>
{
	private static Type<SelectNavigationLinkHandler> TYPE = new Type<SelectNavigationLinkHandler>();

	public interface SelectNavigationLinkHandler extends EventHandler
	{
		void onSelectNavigationLink( SelectNavigationLinkEvent event );
	}

	private final String nameToken;

	public SelectNavigationLinkEvent( final String nameToken )
	{
		this.nameToken = nameToken;
	}

	public static Type<SelectNavigationLinkHandler> getType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch( final SelectNavigationLinkHandler handler )
	{
		handler.onSelectNavigationLink( this );
	}
	

	public static void fire( HasHandlers source, String nameToken )
	{
		if ( TYPE != null )
		{
			source.fireEvent( new SelectNavigationLinkEvent( nameToken ) );
		}
	}

	@Override
	public Type<SelectNavigationLinkHandler> getAssociatedType()
	{
		return TYPE;
	}

	public String getNameToken()
	{
		return this.nameToken;
	}
}
