package com.fmlb.forsaos.client.application;

import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView
{
	interface Binder extends UiBinder<Widget, ApplicationView>
	{
	}

	AppResources resources = GWT.create( AppResources.class );

	@UiField
	SimplePanel main;

	@Inject
	ApplicationView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
		main.clear();
		main.addStyleName( resources.style().mainPanel() );
		bindSlot( ApplicationPresenter.SLOT_MAIN, main );
		// bindSlot(ApplicationPresenter.SLOT_MENU_PANEL, header);
	}

	@Override
	public void setInSlot( Object slot, IsWidget content )
	{
		super.setInSlot( slot, content );
		/*
		 * if ( slot == ApplicationPresenter.SLOT_MAIN ) { main.clear();
		 * main.addStyleName( resources.style().mainPanel() ); main.add( content ); }
		 * else { super.setInSlot( slot, content ); }
		 */
	}
}
