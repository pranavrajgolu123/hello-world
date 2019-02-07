package com.fmlb.forsaos.client.application.vm.details;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class VMDetailsConsole extends MaterialPanel
{
	private VMModel vmModel;

	private UIComponentsUtil uiComponentsUtil;

	AppResources resources = GWT.create( AppResources.class );

	private CurrentUser currentUser;

	private JavaScriptObject jsGuac;

	private MaterialPanel keyboardPanel;

	private MaterialPanel consolePanel;

	public VMDetailsConsole( VMModel vmModel, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		this.vmModel = vmModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		MaterialButton vartualkeyBoardButton = new MaterialButton( "Virtual Keyboard" );
		MaterialRow virtualrow = new MaterialRow();
		virtualrow.addStyleName( resources.style().virtuale_row() );
		virtualrow.add( vartualkeyBoardButton );
		add( virtualrow );
		add( getConsolePanel() );
		add( getKeyBoardPanel() );
		keyboardPanel.setVisible( false );
		vartualkeyBoardButton.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				if ( keyboardPanel.isVisible() )
				{
					keyboardPanel.setVisible( false );
				}
				else
				{
					keyboardPanel.setVisible( true );
				}
			}
		} );
	}

	public MaterialPanel getKeyBoardPanel()
	{
		keyboardPanel = new MaterialPanel();
		keyboardPanel.setId( "console-panel-id-2" );
		keyboardPanel.addStyleName( resources.style().vm_keyboard_panel() );
		return keyboardPanel;
	}

	public MaterialPanel getConsolePanel()
	{
		consolePanel = new MaterialPanel();
		consolePanel.setGrid( "s12" );
		consolePanel.addStyleName( resources.style().vm_console_panel() );
		consolePanel.setId( "console-panel-id" );
		String configHostname = "0.0.0.0";

		consolePanel.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					String data = "hostname=" + Window.Location.getHostName() + "&username=" + currentUser.getUserName() + "&password=" + currentUser.getPassword() + "&configHostname=" + configHostname + "&configPort=" + vmModel.getGraphic_port();
					//						"&param4=data4"+"&param4=data4"+"&param4=data4";
					try
					{
						jsGuac = onConsoleLoad( data, true, null );
					}
					catch ( Exception e )
					{
						List<String> errorMessages = new ArrayList<String>();
						errorMessages.add( "Unable to make get connection." );

						ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
						errorPopup.open();
					}
				}
				else
				{
					try
					{
						onConsoleLoad( null, false, jsGuac );
					}
					catch ( Exception e )
					{
						List<String> errorMessages = new ArrayList<String>();
						errorMessages.add( "Failed to disconnect console." );

						ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
						errorPopup.open();
					}
				}
			}
		} );

		return consolePanel;
	}

	public void clearPanel()
	{
		try
		{
			remove( consolePanel );
			remove( keyboardPanel );
			onConsoleLoad( null, false, jsGuac );
		}
		catch ( Exception e )
		{
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add( "Failed to disconnect console." );

			ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
			errorPopup.open();
		}
	}

	public static native JavaScriptObject onConsoleLoad( String data, boolean isAttached, JavaScriptObject jsGuac ) /*-{
		return $wnd.onConsoleLoad(data, isAttached, jsGuac);
	}-*/;
}
