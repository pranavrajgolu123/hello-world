package com.fmlb.forsaos.client.application.diagnostics.smtp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.SMTPConfigurationModel;
import com.fmlb.forsaos.client.application.models.SendTestEmailModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class SendTestEmailPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private SMTPConfigurationModel smtpModel;

	private MaterialTextBox receivers;

	private MaterialTextArea emailBody;

	private MaterialTextBox title;

	private CurrentUser currentUser;

	AppResources resources = GWT.create( AppResources.class );

	public SendTestEmailPopUp( SMTPConfigurationModel smtpModel, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		super( "Send Test Email", "", "Submit", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.smtpModel = smtpModel;

		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow createDirRow = new MaterialRow();

		receivers = uiComponentsUtil.getTexBox( "Receiver Email Address", "", "s12" );
		receivers.addValidator( uiComponentsUtil.getEmailValidator() );
		receivers.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				receivers.validate();
			}
		} );
		receivers.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				receivers.validate();
			}
		} );

		title = uiComponentsUtil.getTexBox( "Subject", "", "s12" );
		title.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				title.validate();
			}
		} );
		title.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				title.validate();
			}
		} );

		emailBody = uiComponentsUtil.getTextArea( "Message", "", "s12" );
		emailBody.addStyleName( "smtpMsgPenal" );

		createDirRow.add( receivers );
		createDirRow.add( title );
		createDirRow.add( emailBody );

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
					sendTestEmail();
				}
			}
		} );
	}

	private void sendTestEmail()
	{
		List<String> receiver = new ArrayList<String>( Arrays.asList( receivers.getValue().toString().split( ";" ) ) );

		SendTestEmailModel sendEmail = new SendTestEmailModel( receiver, title.getValue().toString(), emailBody.getValue().toString(), smtpModel.getName().toString() );
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().sendTestEmail( sendEmail, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				if ( result )
				{
					close();
					LoggerUtil.log( "Email has been sent" );
					MaterialToast.fireToast( "Email Has been sent!", "rounded" );
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
		if ( receivers.validate() && title.validate() )
		{
			valid = true;
		}
		else
		{
			receivers.validate();
			title.validate();

		}
		return valid;

	}

}
