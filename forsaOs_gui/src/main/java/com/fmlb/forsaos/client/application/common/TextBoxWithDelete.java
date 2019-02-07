package com.fmlb.forsaos.client.application.common;

import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;

public class TextBoxWithDelete extends MaterialPanel
{
	private MaterialTextBox input;

	private MaterialIcon deleteIcon;

	private String inputLabel;

	private IPType ipType;

	private boolean isStringTextBox;

	private String inputColSpec;

	private String delIconColSpec;

	private UIComponentsUtil uiComponentsUtil;

	public TextBoxWithDelete( UIComponentsUtil uiComponentsUtil, String inputLabel, boolean isStringTextBox, IPType ipType, String inputColSpec, String delIconColSpec )
	{
		super();
		this.uiComponentsUtil = uiComponentsUtil;
		this.inputLabel = inputLabel;
		this.isStringTextBox = isStringTextBox;
		this.inputColSpec = inputColSpec;
		this.delIconColSpec = delIconColSpec;
		initializeWidget( ipType );
	}

	private void initializeWidget( IPType ipType )
	{
		if ( !this.isStringTextBox )
		{
			this.ipType = ipType;
			if ( this.ipType.equals( IPType.IP_V4 ) )
			{
				input = this.uiComponentsUtil.getIP4TextBox( this.inputLabel, "", this.inputColSpec, true );
			}
			else if ( this.ipType.equals( IPType.IP_V6 ) )
			{
				input = this.uiComponentsUtil.getIP6TextBox( this.inputLabel, "", this.inputColSpec, true );
			}
		}
		else
		{
			input = this.uiComponentsUtil.getTexBox( this.inputLabel, "", this.inputColSpec );
		}

		deleteIcon = new MaterialIcon( IconType.DELETE );
		deleteIcon.setGrid( this.delIconColSpec );
		add( input );
		add( deleteIcon );
		deleteIcon.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				removeFromParent();
			}

		} );
	}

	public String getTextValue()
	{
		return input.getValue();
	}

	public MaterialTextBox getInput()
	{
		return input;
	}

	public MaterialIcon getDeleteIcon()
	{
		return deleteIcon;
	}

}
