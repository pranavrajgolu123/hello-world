package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.settings.system.SystemPresenter;
import com.fmlb.forsaos.client.application.settings.system.SystemView;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;


public class SystemModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SystemPresenter.class, SystemPresenter.MyView.class, SystemView.class, SystemPresenter.MyProxy.class);
    }
}
