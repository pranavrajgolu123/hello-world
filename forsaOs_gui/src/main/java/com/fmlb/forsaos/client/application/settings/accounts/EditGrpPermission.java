package com.fmlb.forsaos.client.application.settings.accounts;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class EditGrpPermission extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox groupName;
	
	private MaterialCheckBox accounts;
	
	private MaterialCheckBox diagnostics;
	
	private MaterialCheckBox management;
	
	private MaterialCheckBox reporting ;
	
	private MaterialCheckBox security ;
	
	private MaterialCheckBox virtualization;
	
	private MaterialCheckBox lEMs  ;
	
	private MaterialCheckBox vMs ;
	
	private MaterialCheckBox control ;
	
	private MaterialCheckBox settings;
	
	private MaterialLabel permissionlbl;


	AppResources resources = GWT.create( AppResources.class );

	public EditGrpPermission ( UIComponentsUtil uiComponentsUtil)
	{
		super("Edit Group Permissions", "", "Submit", true );
		//this.updateVMDataTableCmd = updateVMDataTableCmd;
		this.uiComponentsUtil = uiComponentsUtil;
		LoggerUtil.log( "initialize create Directory User Group" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow createDirRow = new MaterialRow();

		groupName = uiComponentsUtil.getTexBox( "Group Name", "Type group name", "s12" );

		permissionlbl=uiComponentsUtil.getLabel("Permission","s12");
		
		accounts= new MaterialCheckBox("Accounts");
		diagnostics= new MaterialCheckBox("Diagnostics");
		management= new MaterialCheckBox("Management");
		reporting= new MaterialCheckBox("Reporting");
		security= new MaterialCheckBox("Security");
		virtualization= new MaterialCheckBox("Virtualization ");
		lEMs= new MaterialCheckBox("LEMs");
		vMs= new MaterialCheckBox("VMs");
		control= new MaterialCheckBox("Control ");
		settings= new MaterialCheckBox("Settings");
		
		MaterialColumn indexColumn1=new MaterialColumn();
		indexColumn1.add(accounts);
		indexColumn1.add(diagnostics);
		indexColumn1.add(management);
		indexColumn1.add(reporting);
		indexColumn1.add(security);
		
		MaterialColumn indexColumn2=new MaterialColumn();
		indexColumn2.add(virtualization);
		indexColumn2.add(lEMs);
		indexColumn2.add(vMs);
		indexColumn2.add(control);
		indexColumn2.add(settings);
	
		createDirRow.add( groupName );
		createDirRow.add( permissionlbl );
		
		createDirRow.add(indexColumn1);
		createDirRow.add(indexColumn2);


		getBodyPanel().add(createDirRow);
	}



	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				MaterialLoader.loading( true );
				LoggerUtil.log( "Edit Group Permission" );


			}
		} );
	}
	
}



