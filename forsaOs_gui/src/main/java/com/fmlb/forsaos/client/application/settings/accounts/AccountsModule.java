package com.fmlb.forsaos.client.application.settings.accounts;


import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AccountsModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( AccountsPresenter.class, AccountsPresenter.MyView.class, AccountsView.class, AccountsPresenter.MyProxy.class );
	}
}
