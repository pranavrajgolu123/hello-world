package com.fmlb.forsaos.client.application.diagnostics.smtp;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SMTPModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( SMTPPresenter.class, SMTPPresenter.MyView.class, SMTPView.class, SMTPPresenter.MyProxy.class );
	}
}
