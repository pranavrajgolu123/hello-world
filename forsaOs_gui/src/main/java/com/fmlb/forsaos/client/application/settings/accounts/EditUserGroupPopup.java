package com.fmlb.forsaos.client.application.settings.accounts;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.EditUserGroupModel;
import com.fmlb.forsaos.client.application.models.UserGroupModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.AuthCodes;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.constants.CheckBoxType;
import gwt.material.design.client.constants.CollectionType;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class EditUserGroupPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow permissionsRow;

	private MaterialTextBox newGroupNameTextBox;

	private MaterialCheckBox updateUserAuthCodeCheckBox;

	private MaterialCheckBox managementCheckBox;

	private MaterialCheckBox virtualizationCheckBox;

	private MaterialCheckBox centralCheckBox;

	private MaterialCheckBox nodeCheckBox;

	private MaterialCheckBox networkingCheckBox;

	private MaterialCheckBox controllerCheckBox;

	AppResources resources = GWT.create( AppResources.class );

	private MaterialLabel permissionsLbl;

	private IcommandWithData updateUserGroupDataTableCmd;

	private IcommandWithData updateUserAccountTabCommand;

	private UserGroupModel userGroupModel;

	public EditUserGroupPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData updateUserGroupDataTableCmd, IcommandWithData updateUserAccountTabCommand, UserGroupModel userGroupModel )
	{
		super( "Edit User Group: " + userGroupModel.getName(), "", "Submit", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateUserGroupDataTableCmd = updateUserGroupDataTableCmd;
		this.updateUserAccountTabCommand = updateUserAccountTabCommand;
		this.userGroupModel = userGroupModel;
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow editUserGrpRow = new MaterialRow();

		newGroupNameTextBox = uiComponentsUtil.getTexBox( "New Group Name", "Type Group Name", "s12" );
		newGroupNameTextBox.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );

		updateUserAuthCodeCheckBox = new MaterialCheckBox( "Update Users", CheckBoxType.FILLED );
		//updateUserAuthCodeCheckBox.setGrid( "s6" );
		updateUserAuthCodeCheckBox.addStyleName( resources.style().userGroupUpdateUsers() );

		createPermissionRow();

		editUserGrpRow.add( newGroupNameTextBox );
		editUserGrpRow.add( updateUserAuthCodeCheckBox );
		editUserGrpRow.add( permissionsRow );
		getBodyPanel().add( editUserGrpRow );
	}

	private void createPermissionRow()
	{
		permissionsRow = new MaterialRow();
		permissionsRow.addStyleName( "schedulerBlinkRow" );

		permissionsLbl = uiComponentsUtil.getLabel( "Permissions", "s12" );
		permissionsLbl.addStyleName( resources.style().permissionLabel() );

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		collection1.addStyleName( "permissionDetails NVMEDetails" );

		managementCheckBox = getPermissionsCheckBox();
		networkingCheckBox = getPermissionsCheckBox();
		centralCheckBox = getPermissionsCheckBox();
		controllerCheckBox = getPermissionsCheckBox();
		nodeCheckBox = getPermissionsCheckBox();
		virtualizationCheckBox = getPermissionsCheckBox();

		selectCheckBoxesonAuthCode();

		MaterialCollectionItem collectionItem1 = getCollectionItem( managementCheckBox, "Blink Management", "Blink and Partition" );
		MaterialCollectionItem collectionItem2 = getCollectionItem( networkingCheckBox, "Networking", "Network management" );
		MaterialCollectionItem collectionItem3 = getCollectionItem( centralCheckBox, "Central", "Account, Alerts, Scheduler, Settings" );
		MaterialCollectionItem collectionItem4 = getCollectionItem( controllerCheckBox, "System", "Shutdown, Reboot, Service management" );
		MaterialCollectionItem collectionItem5 = getCollectionItem( nodeCheckBox, "Diagnostics and Power Control", "Diagnostics and Status" );
		MaterialCollectionItem collectionItem6 = getCollectionItem( virtualizationCheckBox, "Virtualization", "RTM, LEM, VM" );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		collection1.add( collectionItem3 );
		collection1.add( collectionItem4 );
		collection1.add( collectionItem5 );
		collection1.add( collectionItem6 );

		permissionsRow.add( permissionsLbl );
		permissionsRow.add( collection1 );

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
					editUserGroupBtnActn();
				}
			}
		} );
	}

	private void editUserGroupBtnActn()
	{
		EditUserGroupModel editUserGroupModel = new EditUserGroupModel();
		editUserGroupModel.setName( userGroupModel.getName() );
		if ( newGroupNameTextBox.getValue() != null && !newGroupNameTextBox.getValue().trim().isEmpty() )
		{
			editUserGroupModel.setNew_name( newGroupNameTextBox.getValue() );
		}
		if ( updateUserAuthCodeCheckBox.getValue() )
		{
			editUserGroupModel.setUpdate_user( true );
		}
		editUserGroupModel.setAuth_code( getAuthCode() );
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().editUserGroupModel( editUserGroupModel, new ApplicationCallBack<Boolean>()
		{

			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( editUserGroupModel.getName() + " Updated..!", "rounded" );
				if ( updateUserGroupDataTableCmd != null )
				{
					updateUserGroupDataTableCmd.executeWithData( true );
				}
				if ( updateUserAccountTabCommand != null )
				{
					updateUserAccountTabCommand.executeWithData( null );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				super.onFailureShowErrorPopup( caught, null );

			}
		} );
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;

		return true;

	}

	private int getAuthCode()
	{
		int authCode = 0;
		if ( managementCheckBox.getValue() )
		{
			authCode = authCode + AuthCodes.MANAGEMENT_AUTH_CODE.getValue();
		}
		if ( virtualizationCheckBox.getValue() )
		{
			authCode = authCode + AuthCodes.VIRTUALIZATION_MANAGEMENT_AUTH_CODE.getValue();
		}
		if ( centralCheckBox.getValue() )
		{
			authCode = authCode + AuthCodes.CENTRAL_MANAGEMENT_AUTH_CODE.getValue();
		}
		if ( nodeCheckBox.getValue() )
		{
			authCode = authCode + AuthCodes.NODE_MANAGEMENT_AUTH_CODE.getValue();
		}
		if ( networkingCheckBox.getValue() )
		{
			authCode = authCode + AuthCodes.NETWORKING_MANAGEMENT_AUTH_CODE.getValue();
		}
		if ( controllerCheckBox.getValue() )
		{
			authCode = authCode + AuthCodes.CONTROLLER_MANAGEMENT_AUTH_CODE.getValue();
		}
		return authCode;
	}

	private MaterialCheckBox getPermissionsCheckBox()
	{
		MaterialCheckBox checkBox = new MaterialCheckBox( "", CheckBoxType.FILLED );
		checkBox.setGrid( "s1" );
		return checkBox;
	}

	private MaterialCollectionItem getCollectionItem( MaterialCheckBox checkBox, String mainLabel, String subText )
	{
		MaterialCollectionItem collectionItem = new MaterialCollectionItem();
		collectionItem.setType( CollectionType.AVATAR );
		collectionItem.setDismissible( true );
		collectionItem.setGrid( "s6" );

		MaterialLabel mainLbl = new MaterialLabel( mainLabel );
		mainLbl.addStyleName( resources.style().permissionHeaderTxt() );
		MaterialLabel subLbl = new MaterialLabel( subText );
		collectionItem.add( checkBox );
		collectionItem.add( mainLbl );
		collectionItem.add( subLbl );
		return collectionItem;
	}

	private void selectCheckBoxesonAuthCode()
	{
		int existingAuthCode = userGroupModel.getAuth_code();
		String binaryString = Integer.toBinaryString( existingAuthCode );
		List<Integer> authValues = new ArrayList<Integer>();
		int counter = 0;
		for ( int i = binaryString.length() - 1; i >= 0; i-- )
		{

			char bit = binaryString.charAt( i );
			LoggerUtil.log( String.valueOf( bit ) );
			if ( bit == '1' )
			{
				double authVal = Math.pow( 2, counter );
				authValues.add( ( int ) authVal );
			}
			counter++;
		}
		if ( authValues.contains( AuthCodes.NAAS_MANAGEMENT_AUTH_CODE.getValue() ) )
		{

		}
		if ( authValues.contains( AuthCodes.MANAGEMENT_AUTH_CODE.getValue() ) )
		{
			managementCheckBox.setValue( true );
		}
		if ( authValues.contains( AuthCodes.VIRTUALIZATION_MANAGEMENT_AUTH_CODE.getValue() ) )
		{
			virtualizationCheckBox.setValue( true );
		}
		if ( authValues.contains( AuthCodes.CENTRAL_MANAGEMENT_AUTH_CODE.getValue() ) )
		{
			centralCheckBox.setValue( true );
		}
		if ( authValues.contains( AuthCodes.NODE_MANAGEMENT_AUTH_CODE.getValue() ) )
		{
			nodeCheckBox.setValue( true );
		}
		if ( authValues.contains( AuthCodes.NETWORKING_MANAGEMENT_AUTH_CODE.getValue() ) )
		{
			networkingCheckBox.setValue( true );
		}
		if ( authValues.contains( AuthCodes.CONTROLLER_MANAGEMENT_AUTH_CODE.getValue() ) )
		{
			controllerCheckBox.setValue( true );
		}

	}

}
