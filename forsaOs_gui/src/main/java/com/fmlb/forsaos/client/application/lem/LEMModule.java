package com.fmlb.forsaos.client.application.lem;

import com.fmlb.forsaos.client.application.lem.details.LEMDetailsModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LEMModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		install( new LEMDetailsModule() );
		bindPresenter( LEMPresenter.class, LEMPresenter.MyView.class, LEMView.class, LEMPresenter.MyProxy.class );
	}
}