package com.fmlb.forsaos.client.application.login;

import javax.inject.Inject;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

class LoginView extends ViewWithUiHandlers<LoginUiHandlers> implements LoginPresenter.MyView
{
	interface Binder extends UiBinder<Widget, LoginView>
	{
	}

	@UiField
	MaterialButton btnLogin;

	@UiField
	MaterialTextBox password;

	@UiField
	MaterialTextBox username;

	@UiField
	MaterialLabel errorLabel;

	public MaterialButton getBtnLogin()
	{
		return btnLogin;
	}

	public MaterialLabel getErrorLabel()
	{
		return errorLabel;
	}

	@UiHandler( "btnLogin" )
	void onSend( ClickEvent event )
	{
		getUiHandlers().confirm( username.getText(), password.getText() );
	}

	@UiHandler( "password" )
	void onKeyDown( KeyDownEvent e )
	{
		if ( e.getNativeKeyCode() == KeyCodes.KEY_ENTER )
		{
			getUiHandlers().confirm( username.getText(), password.getText() );
		}
	}

	@Inject
	LoginView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
		errorLabel.getElement().getStyle().setVisibility( Visibility.HIDDEN );
	}
}