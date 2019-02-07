package com.fmlb.forsaos.client.application.settings.licenseManager;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LicenseModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( LicensePresenter.class, LicensePresenter.MyView.class, LicenseView.class, LicensePresenter.MyProxy.class );
	}
}