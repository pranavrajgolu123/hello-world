package com.fmlb.forsaos.client.application.management.networking;

import com.fmlb.forsaos.client.application.management.networking.detail.NetworkingDetailModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class NetworkingModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new NetworkingDetailModule());
		bindPresenter(NetworkingPresenter.class, NetworkingPresenter.MyView.class, NetworkingView.class, NetworkingPresenter.MyProxy.class);
    }
}