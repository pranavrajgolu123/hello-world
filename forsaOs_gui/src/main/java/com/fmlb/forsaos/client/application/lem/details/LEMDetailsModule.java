package com.fmlb.forsaos.client.application.lem.details;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LEMDetailsModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( LEMDetailsPresenter.class, LEMDetailsPresenter.MyView.class, LEMDetailsView.class, LEMDetailsPresenter.MyProxy.class );
	}
}