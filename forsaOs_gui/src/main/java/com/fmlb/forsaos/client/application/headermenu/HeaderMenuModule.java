package com.fmlb.forsaos.client.application.headermenu;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class HeaderMenuModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenterWidget( HeaderMenuPresenter.class, HeaderMenuPresenter.MyView.class, HeaderMenuView.class );
	}
}