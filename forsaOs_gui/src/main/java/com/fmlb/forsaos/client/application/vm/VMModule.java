package com.fmlb.forsaos.client.application.vm;

import com.fmlb.forsaos.client.application.vm.details.VMDetailsModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class VMModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		install( new VMDetailsModule() );
		bindPresenter( VMPresenter.class, VMPresenter.MyView.class, VMView.class, VMPresenter.MyProxy.class );
	}
}