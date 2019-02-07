package com.fmlb.forsaos.client.application.management.scheduler;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SchedulerModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( SchedulerPresenter.class, SchedulerPresenter.MyView.class, SchedulerView.class, SchedulerPresenter.MyProxy.class );
	}
}
