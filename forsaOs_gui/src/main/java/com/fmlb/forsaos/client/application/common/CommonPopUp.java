
package com.fmlb.forsaos.client.application.common;

import java.util.ArrayList;

import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class CommonPopUp extends MaterialWindow
{

	AppResources resources = GWT.create( AppResources.class );

	private String mainTitle;

	private String subTitle;

	private String buttonText;

	private MaterialButton button;

	private MaterialPanel bodyPanel;

	private MaterialRow buttonRow;

	private boolean showButtonFl = false;

	private IconType buttonIconType;

	private boolean overlayFl = true;

	private ArrayList<Icommand> buttonClickCmds = new ArrayList<Icommand>();

	public CommonPopUp( String mainTitle, String subTitle, String buttonText, boolean overlayFl )
	{
		this.mainTitle = mainTitle;
		this.subTitle = subTitle;
		this.buttonText = buttonText;
		this.overlayFl = overlayFl;

		initializePopUp();

	}

	public CommonPopUp( String mainTitle, String subTitle, String buttonText, boolean overlayFl, boolean showButtonFl, IconType buttonIconType )
	{
		this.mainTitle = mainTitle;
		this.subTitle = subTitle;
		this.buttonText = buttonText;
		this.overlayFl = overlayFl;
		this.showButtonFl = showButtonFl;
		this.buttonIconType = buttonIconType;
		initializePopUp();

	}

	private void initializePopUp()
	{
		setOverlay( this.overlayFl );
		setTitle( this.mainTitle + this.subTitle );
		this.bodyPanel = new MaterialPanel();
		this.buttonRow = new MaterialRow();
		this.buttonRow.addStyleName( resources.style().popup_btn_row() );
		this.button = createPopUpButton();
		this.button.addStyleName( resources.style().popup_btn() );
		this.button.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				for ( Icommand cmd : buttonClickCmds )
				{
					cmd.execute();
				}

			}
		} );
		this.buttonRow.add( this.button );
		add( this.bodyPanel );
		add( this.buttonRow );

	}

	public void addButtonClickCommand( final Icommand icmd )
	{
		this.buttonClickCmds.add( icmd );
	}

	protected MaterialButton createPopUpButton()
	{
		MaterialButton button = new MaterialButton();
		button.setText( this.buttonText );
		button.setType( ButtonType.OUTLINED );
		button.setWaves( WavesType.LIGHT );
		button.setBackgroundColor( Color.BLACK );
		button.setTextColor( Color.WHITE );
		button.addStyleName( resources.style().popButton() );
		if ( this.showButtonFl )
		{
			button.setIconType( this.buttonIconType );
			button.setIconPosition( IconPosition.RIGHT );
			button.setIconColor( Color.RED );
		}
		return button;
	}

	public AppResources getResources()
	{
		return resources;
	}

	public void setResources( AppResources resources )
	{
		this.resources = resources;
	}

	public String getMainTitle()
	{
		return mainTitle;
	}

	public void setMainTitle( String mainTitle )
	{
		this.mainTitle = mainTitle;
	}

	public String getSubTitle()
	{
		return subTitle;
	}

	public void setSubTitle( String subTitle )
	{
		this.subTitle = subTitle;
	}

	public String getButtonText()
	{
		return buttonText;
	}

	public void setButtonText( String buttonText )
	{
		this.buttonText = buttonText;
	}

	public MaterialButton getButton()
	{
		return button;
	}

	public void setButton( MaterialButton button )
	{
		this.button = button;
	}

	public MaterialPanel getBodyPanel()
	{
		return bodyPanel;
	}

	public void setBodyPanel( MaterialPanel bodyPanel )
	{
		this.bodyPanel = bodyPanel;
	}

	public MaterialRow getButtonRow()
	{
		return buttonRow;
	}

	public void setButtonRow( MaterialRow buttonRow )
	{
		this.buttonRow = buttonRow;
	}

	public boolean isOverlayFl()
	{
		return overlayFl;
	}

	public void setOverlayFl( boolean overlayFl )
	{
		this.overlayFl = overlayFl;
	}

}
