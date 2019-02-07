package com.fmlb.forsaos.client.application.diagnostics.smtp;

import java.util.HashMap;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.SendAlertAllUserModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.FieldType;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class SendAlertAllUserPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox subject;

	private MaterialTextArea msg;

	private SendAlertAllUserModel sendAlert;

	private CurrentUser currentUser;

	private MaterialComboBox<String> securityLevelDropDown;

	AppResources resources = GWT.create( AppResources.class );

	public SendAlertAllUserPopUp( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		super( "Send Alert to All Users", "", "Send Alert", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		LoggerUtil.log( "Send email to all" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow createDirRow = new MaterialRow();

		subject = uiComponentsUtil.getTexBox( "Subject", "Enter Subject", "s12" );
		subject.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				subject.validate();
			}
		} );
		subject.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				subject.validate();
			}
		} );

		msg = uiComponentsUtil.getTextArea( "Message", "Enter Message", "s12" );
		msg.addStyleName( "smtpMsgPenal" );

		HashMap<String, String> securityLevelDropDownOptionMap = new HashMap<String, String>();
		securityLevelDropDownOptionMap.put( "Warning", "warning" );
		securityLevelDropDownOptionMap.put( "Error", "Error" );
		securityLevelDropDownOptionMap.put( "Fatal", "Fatal" );

		securityLevelDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( securityLevelDropDownOptionMap, "Enter Level", "s6" );
		securityLevelDropDown.setFieldType( FieldType.ALIGNED_LABEL );
		securityLevelDropDown.setLabel( " Level" );
		securityLevelDropDown.addStyleName( resources.style().vm_details_setting_row() );
		securityLevelDropDown.setHideSearch( true );
		//securityLevelDropDown.setGrid("s6");
		securityLevelDropDown.addStyleName( "sendAlertLevelDropdown" );

		createDirRow.add( subject );
		createDirRow.add( msg );
		createDirRow.add( securityLevelDropDown );

		getBodyPanel().add( createDirRow );
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
					sendEmailToAll();
				}
			}
		} );
	}

	private void sendEmailToAll()
	{

		sendAlert = new SendAlertAllUserModel( subject.getValue().toString(), msg.getValue().toString(), securityLevelDropDown.getValue().get( 0 ).toString() );
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().sendAlertEmail( sendAlert, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );

				if ( result )
				{
					close();
					LoggerUtil.log( "Email has been sent" );
					MaterialToast.fireToast( "Email Has been sent to all user!", "rounded" );
				}
				else
				{
					MaterialToast.fireToast( "Unable to send email", "rounded" );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			};
		} );

	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( subject.validate() )
		{
			valid = true;
		}
		else
		{
			subject.validate();

		}
		return valid;

	}

}
