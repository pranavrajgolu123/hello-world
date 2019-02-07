package com.fmlb.forsaos.client.application.rtm;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class RTMModule extends AbstractPresenterModule
{

	@Override
	protected void configure()
	{
		bindPresenter( RTMPresenter.class, RTMPresenter.MyView.class, RTMView.class, RTMPresenter.MyProxy.class );
	}

}
