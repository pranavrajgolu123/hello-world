package com.fmlb.forsaos.client.application.vm.details;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class VMDetailsModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( VMDetailsPresenter.class, VMDetailsPresenter.MyView.class, VMDetailsView.class, VMDetailsPresenter.MyProxy.class );
	}
}