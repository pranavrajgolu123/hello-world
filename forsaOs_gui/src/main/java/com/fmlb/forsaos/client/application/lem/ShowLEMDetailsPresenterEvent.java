package com.fmlb.forsaos.client.application.lem;

import com.fmlb.forsaos.client.application.models.LEMModel;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShowLEMDetailsPresenterEvent extends GwtEvent<ShowLEMDetailsPresenterEvent.ShowLEMDetailsPresenterHandler>
{
	private static Type<ShowLEMDetailsPresenterHandler> TYPE = new Type<ShowLEMDetailsPresenterHandler>();

	public interface ShowLEMDetailsPresenterHandler extends EventHandler
	{
		void onShowLEMDetailsPresenter( ShowLEMDetailsPresenterEvent event );
	}

	private final LEMModel lemModel;

	public static void fire( HasHandlers source, LEMModel lemModel )
	{
		if ( TYPE != null )
		{
			source.fireEvent( new ShowLEMDetailsPresenterEvent( lemModel ) );
		}
	}

	public ShowLEMDetailsPresenterEvent( final LEMModel lemModel )
	{
		this.lemModel = lemModel;
	}

	public static Type<ShowLEMDetailsPresenterHandler> getType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch( final ShowLEMDetailsPresenterHandler handler )
	{
		handler.onShowLEMDetailsPresenter( this );
	}

	@Override
	public Type<ShowLEMDetailsPresenterHandler> getAssociatedType()
	{
		return TYPE;
	}

	public LEMModel getLEMModel()
	{
		return this.lemModel;
	}
}
