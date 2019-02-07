package com.fmlb.forsaos.client.application.eventlog;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class EventLogModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( EventLogPresenter.class, EventLogPresenter.MyView.class, EventLogView.class, EventLogPresenter.MyProxy.class );
	}
}