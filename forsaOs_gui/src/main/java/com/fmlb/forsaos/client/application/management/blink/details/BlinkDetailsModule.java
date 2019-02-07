package com.fmlb.forsaos.client.application.management.blink.details;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class BlinkDetailsModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( BlinkDetailsPresenter.class, BlinkDetailsPresenter.MyView.class, BlinkDetailsView.class, BlinkDetailsPresenter.MyProxy.class );
	}
}