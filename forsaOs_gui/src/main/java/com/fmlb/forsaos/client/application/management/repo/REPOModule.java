package com.fmlb.forsaos.client.application.management.repo;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class REPOModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( REPOPresenter.class, REPOPresenter.MyView.class, REPOView.class, REPOPresenter.MyProxy.class );
	}
}