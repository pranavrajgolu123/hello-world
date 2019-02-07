package com.fmlb.forsaos.client.application.common;

import java.util.ArrayList;

import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class ConfirmPasswordPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private ArrayList<String> warningMsgList;

	private MaterialTextBox password;

	private Icommand icommand;

	private CurrentUser currentUser;

	private MaterialButton cancelBtn;

	public ConfirmPasswordPopup( String title, ArrayList<String> warningMsgList, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, Icommand icommand )
	{
		super( title, "", title, true );
		this.icommand = icommand;
		this.warningMsgList = warningMsgList;
		this.currentUser = currentUser;
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

		password = this.uiComponentsUtil.getPasswordBox( "", "" );
		password.setFocus( true );
		password.addValidator( this.uiComponentsUtil.getPasswordValidator( currentUser ) );
		for ( String warningMsg : warningMsgList )
		{
			confirmPassRow.add( this.uiComponentsUtil.getLabel( warningMsg, "" ) );
		}
		confirmPassRow.add( password );
		getBodyPanel().add( confirmPassRow );
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

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( password.validate() )
		{
			valid = true;
		}
		else
		{
			password.validate();
		}
		return valid;

	}
}
