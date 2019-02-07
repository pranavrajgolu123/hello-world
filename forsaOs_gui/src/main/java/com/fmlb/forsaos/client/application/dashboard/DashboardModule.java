package com.fmlb.forsaos.client.application.dashboard;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DashboardModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( DashboardPresenter.class, DashboardPresenter.MyView.class, DashboardView.class, DashboardPresenter.MyProxy.class );
	}
}