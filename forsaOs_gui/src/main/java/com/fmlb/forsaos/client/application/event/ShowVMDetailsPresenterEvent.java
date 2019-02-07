package com.fmlb.forsaos.client.application.event;

import com.fmlb.forsaos.client.application.models.VMModel;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShowVMDetailsPresenterEvent extends GwtEvent<ShowVMDetailsPresenterEvent.ShowVMDetailsPresenterHandler>
{
	private static Type<ShowVMDetailsPresenterHandler> TYPE = new Type<ShowVMDetailsPresenterHandler>();

	public interface ShowVMDetailsPresenterHandler extends EventHandler
	{
		void onShowVMDetailsPresenter( ShowVMDetailsPresenterEvent event );
	}

	private final VMModel vmModel;

	public static void fire( HasHandlers source, VMModel vmModel )
	{
		if ( TYPE != null )
		{
			source.fireEvent( new ShowVMDetailsPresenterEvent( vmModel ) );
		}
	}

	public ShowVMDetailsPresenterEvent( final VMModel vmModel )
	{
		this.vmModel = vmModel;
	}

	public static Type<ShowVMDetailsPresenterHandler> getType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch( final ShowVMDetailsPresenterHandler handler )
	{
		handler.onShowVMDetailsPresenter( this );
	}

	@Override
	public Type<ShowVMDetailsPresenterHandler> getAssociatedType()
	{
		return TYPE;
	}

	public VMModel getVMModel()
	{
		return this.vmModel;
	}
}
