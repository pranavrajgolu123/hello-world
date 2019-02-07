package com.fmlb.forsaos.client.application.event;

import com.fmlb.forsaos.client.application.models.NetworkingModel;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShowNetworkingDetailsPresenterEvent extends GwtEvent<ShowNetworkingDetailsPresenterEvent.ShowNetworkingDetailsPresenterHandler>
{

	private static Type<ShowNetworkingDetailsPresenterHandler> TYPE = new Type<ShowNetworkingDetailsPresenterHandler>();

	public interface ShowNetworkingDetailsPresenterHandler extends EventHandler
	{
		void onShowNetworkingDetailsPresenter( ShowNetworkingDetailsPresenterEvent event );
	}

	private final NetworkingModel networkingModel;

	public static void fire( HasHandlers source, NetworkingModel networkingModel )
	{
		if ( TYPE != null )
		{
			source.fireEvent( new ShowNetworkingDetailsPresenterEvent( networkingModel ) );
		}
	}

	public ShowNetworkingDetailsPresenterEvent( final NetworkingModel networkingModel )
	{
		this.networkingModel = networkingModel;
	}

	public static Type<ShowNetworkingDetailsPresenterHandler> getType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch( final ShowNetworkingDetailsPresenterHandler handler )
	{
		handler.onShowNetworkingDetailsPresenter( this );
	}

	@Override
	public Type<ShowNetworkingDetailsPresenterHandler> getAssociatedType()
	{
		return TYPE;
	}

	public NetworkingModel getNetworkingModel()
	{
		return networkingModel;
	}

}
