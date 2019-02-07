package com.fmlb.forsaos.client.application.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class BreadcrumbEvent extends GwtEvent<BreadcrumbEvent.BreadcrumbHandler>
{
	private static Type<BreadcrumbHandler> TYPE = new Type<BreadcrumbHandler>();

	public interface BreadcrumbHandler extends EventHandler
	{
		void onBreadcrumb( BreadcrumbEvent event );
	}

	private final String title;

	private final String nameToken;

	public BreadcrumbEvent( final String title, final String nameToken )
	{
		this.title = title;
		this.nameToken = nameToken;
	}

	public static Type<BreadcrumbHandler> getType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch( final BreadcrumbHandler handler )
	{
		handler.onBreadcrumb( this );
	}

	public static void fire( HasHandlers source, String data, String nameToken )
	{
		if ( TYPE != null )
		{
			source.fireEvent( new BreadcrumbEvent( data, nameToken ) );
		}
	}

	@Override
	public Type<BreadcrumbHandler> getAssociatedType()
	{
		return TYPE;
	}

	public String getTitle()
	{
		return this.title;
	}

	public String getNameToken()
	{
		return nameToken;
	}

}
