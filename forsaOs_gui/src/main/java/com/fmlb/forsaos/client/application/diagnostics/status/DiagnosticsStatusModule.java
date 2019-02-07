package com.fmlb.forsaos.client.application.diagnostics.status;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DiagnosticsStatusModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(DiagnosticsStatusPresenter.class, DiagnosticsStatusPresenter.MyView.class, DiagnosticsStatusView.class, DiagnosticsStatusPresenter.MyProxy.class);
    }
}