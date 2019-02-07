package com.fmlb.forsaos.client.application.settings.security;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SecurityModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
    	
        bindPresenter(SecurityPresenter.class, SecurityPresenter.MyView.class, SecurityView.class, SecurityPresenter.MyProxy.class);
    }
}