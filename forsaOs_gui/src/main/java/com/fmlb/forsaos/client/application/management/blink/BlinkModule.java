package com.fmlb.forsaos.client.application.management.blink;

import com.fmlb.forsaos.client.application.management.blink.details.BlinkDetailsModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class BlinkModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		install( new BlinkDetailsModule() );
		bindPresenter( BlinkPresenter.class, BlinkPresenter.MyView.class, BlinkView.class, BlinkPresenter.MyProxy.class );
	}
}