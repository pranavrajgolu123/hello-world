package com.fmlb.forsaos.client.application.rtm;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialRow;

public class RTMView extends ViewWithUiHandlers<RTMUiHandlers> implements RTMPresenter.MyView
{

	interface RTMViewUiBinder extends UiBinder<Widget, RTMView>
	{
	}

	@UiField
	MaterialRow rtmView;

	public MaterialRow getRTMView()
	{
		return rtmView;
	}

	@Inject
	public RTMView( RTMViewUiBinder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}
}
