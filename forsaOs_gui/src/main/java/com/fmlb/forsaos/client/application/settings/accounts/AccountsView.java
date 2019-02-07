package com.fmlb.forsaos.client.application.settings.accounts;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class AccountsView extends ViewWithUiHandlers<AccountsUiHandlers> implements AccountsPresenter.MyView
{
	interface Binder extends UiBinder<Widget, AccountsView>
	{
	}

	@UiField
	HTMLPanel main;

	@Inject
	AccountsView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

	@Override
	public HTMLPanel getMainPanel()
	{
		return main;
	}

}
