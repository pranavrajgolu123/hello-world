package com.fmlb.forsaos.client.application.diagnostics.smtp;

import java.util.HashMap;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.AddEmailModel;
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
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class AddEmailAddressPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox email;

	private Icommand updateEmailDataTable;

	private AddEmailModel addemailModel;

	private MaterialComboBox<String> securityLevelDropDown;

	AppResources resources = GWT.create( AppResources.class );

	public AddEmailAddressPopUp( UIComponentsUtil uiComponentsUtil, Icommand updateEmailDataTable )
	{
		super( "Add E-mail Address", "", "Add", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateEmailDataTable = updateEmailDataTable;
		LoggerUtil.log( "initialize add email" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow createDirRow = new MaterialRow();

		email = uiComponentsUtil.getTexBox( "Email Address", "Enter e-mail address", "s6" );
		email.addValidator( uiComponentsUtil.getEmailValidator() );
		email.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				email.validate();
			}
		} );
		email.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				email.validate();
			}
		} );

		HashMap<String, String> securityLevelDropDownOptionMap = new HashMap<String, String>();
		securityLevelDropDownOptionMap.put( "Warning", "warning" );
		securityLevelDropDownOptionMap.put( "Error", "Error" );
		securityLevelDropDownOptionMap.put( "Fatal", "Fatal" );

		securityLevelDropDown = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( securityLevelDropDownOptionMap, "Level", "s6" );

		createDirRow.add( email );
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
					createNewEmail();
				}

			}
		} );
	}

	private void createNewEmail()
	{

		addemailModel = new AddEmailModel( email.getValue().toString(), securityLevelDropDown.getValue().get( 0 ).toString() );
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().addEmail( addemailModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( addemailModel.getEmail() + " Added..!", "rounded" );
				if ( updateEmailDataTable != null )
				{
					updateEmailDataTable.execute();
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}

		} );

	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( email.validate() )
		{
			valid = true;
		}
		else
		{
			email.validate();

		}
		return valid;

	}
}
