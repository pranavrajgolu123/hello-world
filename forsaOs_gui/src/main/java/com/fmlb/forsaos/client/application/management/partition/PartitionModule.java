package com.fmlb.forsaos.client.application.management.partition;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class PartitionModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		bindPresenter( PartitionPresenter.class, PartitionPresenter.MyView.class, PartitionView.class, PartitionPresenter.MyProxy.class );
	}
}