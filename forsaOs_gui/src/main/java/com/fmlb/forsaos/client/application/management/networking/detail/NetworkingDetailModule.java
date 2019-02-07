package com.fmlb.forsaos.client.application.management.networking.detail;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class NetworkingDetailModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(NetworkingDetailPresenter.class, NetworkingDetailPresenter.MyView.class, NetworkingDetailView.class, NetworkingDetailPresenter.MyProxy.class);
    }
}