package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class EditActiveDirPopUp extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox domain;

	private MaterialTextBox machine;

	private MaterialTextBox directoryUsrName;

	private MaterialTextBox directoryPass;
	
	private MaterialCheckBox tlsCheckBox;

	private MaterialPanel newtlsCheckBoxPanel;



	AppResources resources = GWT.create( AppResources.class );

	public EditActiveDirPopUp( UIComponentsUtil uiComponentsUtil)
	{
		super( "Edit Active Directory", "", "Edit Active Directory", true );
		//this.updateVMDataTableCmd = updateVMDataTableCmd;
		this.uiComponentsUtil = uiComponentsUtil;
		LoggerUtil.log( "initialize create Directory User Group" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow createDirRow = new MaterialRow();

		domain = uiComponentsUtil.getTexBox( "Domain", "Domain", "s6" );
		machine = uiComponentsUtil.getTexBox( "Machine", "Machine", "s6" );
		directoryUsrName = uiComponentsUtil.getTexBox( "Directory Username", "Directory Username", "s6" );
		directoryPass = uiComponentsUtil.getTexBox( "Directory Password", "Directory Password", "s6" );
		
		//Attach LEM
		createTLSRadioButtons();

		createDirRow.add( domain );
		createDirRow.add( machine );
		createDirRow.add( directoryUsrName );
		createDirRow.add( directoryPass );


		//Radio buttons
		createDirRow.add( newtlsCheckBoxPanel );
		getBodyPanel().add(createDirRow);
	}

	private void createTLSRadioButtons()
	{
		
		tlsCheckBox = new MaterialCheckBox("\r\n" + "TLS/SSL ENCRYPTION");
		tlsCheckBox.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				boolean tlsCheckBox = event.getValue();
				if ( tlsCheckBox )
				{
					
				}
				else
				{
					
				}
			}
		} );
		newtlsCheckBoxPanel = new MaterialPanel();
		newtlsCheckBoxPanel.add( tlsCheckBox );
		newtlsCheckBoxPanel.setGrid( "s12" );
		newtlsCheckBoxPanel.addStyleName( resources.style().popup_checkbox_panel() );
		
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				MaterialLoader.loading( true );
				LoggerUtil.log( "Edit Active Directory" );

				if ( tlsCheckBox.getValue() )
				{
					//createVmWithNewLem();
				}
				else
				{
					//createVMOnly();
				}
			}
		} );
	}
	
}

