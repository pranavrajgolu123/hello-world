package com.fmlb.forsaos.client.application.login;

import com.gwtplatform.mvp.client.UiHandlers;

interface LoginUiHandlers extends UiHandlers
{
	void confirm( String username, String password );
}