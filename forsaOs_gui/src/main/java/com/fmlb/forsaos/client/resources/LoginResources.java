package com.fmlb.forsaos.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface LoginResources extends ClientBundle
{

	interface Style extends CssResource
	{

		String panel();

		String fieldPanel();

		String i2_logo();

		String rowAction();

		String login_box();

		String login_bg();

		String f_logo();

		String login_txt();

		String login_input();

		String password_input();

		String login_btn();

		String invalid_txt();
		
		String i2_logo_panel();

	}

	@Source( "css/login.gss" )
	Style style();

}
