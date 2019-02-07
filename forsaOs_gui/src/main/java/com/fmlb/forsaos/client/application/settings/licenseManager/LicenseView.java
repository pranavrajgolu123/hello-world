package com.fmlb.forsaos.client.application.settings.licenseManager;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;

public class LicenseView extends ViewWithUiHandlers<LicenseUiHandlers> implements LicensePresenter.MyView
{
	interface Binder extends UiBinder<Widget, LicenseView>
	{
	}

	@UiField
	MaterialPanel licenseManager;

	@Inject
	LicenseView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
	}

	@Override
	public MaterialPanel getMainPanel()
	{
		// TODO Auto-generated method stub
		return licenseManager;
	}
}