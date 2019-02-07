package com.fmlb.forsaos.client.application.management.partition;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class PartitionView extends ViewWithUiHandlers<PartitionUiHandlers> implements PartitionPresenter.MyView
{
	interface Binder extends UiBinder<Widget, PartitionView>
	{
	}

	@UiField
	HTMLPanel main;

	public HTMLPanel getMainPanel()
	{
		return main;
	}

	@Inject
	PartitionView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}
}