package com.fmlb.forsaos.client.application;

import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

@DefaultGatekeeper
public class LoggenInGateKeeper implements Gatekeeper
{
	private final CurrentUser currentUser;

	@Inject
	LoggenInGateKeeper( CurrentUser currentUser )
	{
		this.currentUser = currentUser;
	}

	@Override
	public boolean canReveal()
	{
		// TODO Auto-generated method stub
		return currentUser.isLoggedIn();
	}

}
