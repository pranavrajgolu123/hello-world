package com.fmlb.forsaos.client.application.common;

import java.util.ArrayList;

import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialRow;

public class ConfirmationPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private ArrayList<String> warningMsgList;

	private Icommand icommand;

	private MaterialButton cancelBtn;

	public ConfirmationPopup( String title, String buttonText, ArrayList<String> warningMsgList, UIComponentsUtil uiComponentsUtil, Icommand icommand )
	{
		super( title, "", buttonText, true );
		this.icommand = icommand;
		this.warningMsgList = warningMsgList;
		this.uiComponentsUtil = uiComponentsUtil;
		initialize();
		buttonAction();
		initializeCancelBtn();
	}

	private void initializeCancelBtn()
	{
		this.cancelBtn = new MaterialButton();
		this.cancelBtn.setText( "Cancel" );
		this.cancelBtn.setType( ButtonType.OUTLINED );
		this.cancelBtn.setWaves( WavesType.LIGHT );
		this.cancelBtn.setBackgroundColor( Color.BLACK );
		this.cancelBtn.setTextColor( Color.WHITE );
		this.cancelBtn.addStyleName( resources.style().popButton() );
		this.cancelBtn.addStyleName( resources.style().popup_btn() );
		getButtonRow().add( this.cancelBtn );
		this.cancelBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				close();
			}
		} );
	}

	private void initialize()
	{
		MaterialRow confirmPassRow = new MaterialRow();
		for ( String warningMsg : warningMsgList )
		{
			confirmPassRow.add( this.uiComponentsUtil.getLabel( warningMsg, "" ) );
		}
		getBodyPanel().add( confirmPassRow );
		confirmPassRow.addStyleName( resources.style().repoDeleteErrorMsg() );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				if ( validate() )
				{
					if ( icommand != null )
					{
						icommand.execute();
					}
				}

			}
		} );
	}
}
