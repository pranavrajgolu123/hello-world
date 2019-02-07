package com.fmlb.forsaos.client.application.event;

import com.fmlb.forsaos.client.application.models.BlinkModel;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShowBlinkDetailsPresenterEvent extends GwtEvent<ShowBlinkDetailsPresenterEvent.ShowBlinkDetailsPresenterHandler>
{
	private static Type<ShowBlinkDetailsPresenterHandler> TYPE = new Type<ShowBlinkDetailsPresenterHandler>();

	public interface ShowBlinkDetailsPresenterHandler extends EventHandler
	{
		void onShowBlinkDetailsPresenter( ShowBlinkDetailsPresenterEvent event );
	}

	private final BlinkModel blinkModel;

	public static void fire( HasHandlers source, BlinkModel blinkModel )
	{
		if ( TYPE != null )
		{
			source.fireEvent( new ShowBlinkDetailsPresenterEvent( blinkModel ) );
		}
	}

	public ShowBlinkDetailsPresenterEvent( final BlinkModel blinkModel )
	{
		this.blinkModel = blinkModel;
	}

	public static Type<ShowBlinkDetailsPresenterHandler> getType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch( final ShowBlinkDetailsPresenterHandler handler )
	{
		handler.onShowBlinkDetailsPresenter( this );
	}

	@Override
	public Type<ShowBlinkDetailsPresenterHandler> getAssociatedType()
	{
		return TYPE;
	}

	public static Type<ShowBlinkDetailsPresenterHandler> getTYPE()
	{
		return TYPE;
	}

	public BlinkModel getBlinkModel()
	{
		return blinkModel;
	}

	
}
