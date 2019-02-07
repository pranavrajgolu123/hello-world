package com.fmlb.forsaos.client.application.settings.accounts;

import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CreateNewUserModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.UserGroupModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CreateNewUserPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox userName;

	private MaterialTextBox password;

	private MaterialTextBox email;

	private IcommandWithData updateUserDataTableCmd;

	@SuppressWarnings( "unused" )
	private CurrentUser currentUser;

	private List<UserGroupModel> userGroupModels;

	private MaterialComboBox<UserGroupModel> userGroupDropDown;

	AppResources resources = GWT.create( AppResources.class );

	private MaterialCheckBox isAdminCheckBox;

	private MaterialCheckBox extendExpire;

	private MaterialIntegerBox month;

	private MaterialIntegerBox day;

	private MaterialIntegerBox hour;

	public CreateNewUserPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData updateUserDataTableCmd, CurrentUser currentUser, List<UserGroupModel> userGroupModels )
	{
		super( "Create New User", "", "Create New User", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateUserDataTableCmd = updateUserDataTableCmd;
		this.currentUser = currentUser;
		this.userGroupModels = userGroupModels;
		LoggerUtil.log( "initialize create new user" );
		initialize();
		buttonAction();
	}

	@SuppressWarnings( "unchecked" )
	private void initialize()
	{

		MaterialRow creatNewUserRow = new MaterialRow();

		userName = uiComponentsUtil.getTexBox( "User Name", "Enter user name", "s6" );
		userName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );

		password = uiComponentsUtil.getDefaultPasswordBox( "Password", "s6" );

		email = uiComponentsUtil.getEmailBox( "Enter Email", "Enter email", "s6" );

		userGroupDropDown = ( MaterialComboBox<UserGroupModel> ) uiComponentsUtil.getComboBoxModelDropDownWithoutNone( userGroupModels, "Group Name", "s6" );
		if ( userGroupModels == null || userGroupModels.isEmpty() )
		{
			userGroupDropDown.setErrorText( "No User Group Created." );
		}

		isAdminCheckBox = new MaterialCheckBox( "Admin" );
		isAdminCheckBox.setGrid( "s6" );
		isAdminCheckBox.addStyleName( resources.style().createNewUserAdminCheckbox() );

		extendExpire = new MaterialCheckBox( "Extend Token" );
		extendExpire.setGrid( "s6" );
		extendExpire.addStyleName( resources.style().createNewUserAdminCheckbox() );
		extendExpire.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( event.getValue() )
				{
					month.setEnabled( true );
					day.setEnabled( true );
					hour.setEnabled( true );
				}
				else
				{
					month.setEnabled( false );
					day.setEnabled( false );
					hour.setEnabled( false );
				}
			}
		} );

		month = uiComponentsUtil.getIntegerBoxIncludeNullAndZero( "Month", "Month", "s4" );
		day = uiComponentsUtil.getIntegerBoxIncludeNullAndZero( "Day", "Day", "s4" );
		day.removeValidator( uiComponentsUtil.getIntegerBoxEmptyValidator() );
		hour = uiComponentsUtil.getIntegerBoxIncludeNullAndZero( "Hour", "Hour", "s4" );
		hour.removeValidator( uiComponentsUtil.getIntegerBoxEmptyValidator() );
		month.setMin( "0" );
		day.setMin( "0" );
		hour.setMin( "0" );

		month.setEnabled( false );
		day.setEnabled( false );
		hour.setEnabled( false );

		creatNewUserRow.add( userName );
		creatNewUserRow.add( password );
		creatNewUserRow.add( email );
		creatNewUserRow.add( userGroupDropDown );

		creatNewUserRow.add( isAdminCheckBox );
		creatNewUserRow.add( extendExpire );
		creatNewUserRow.add( month );
		creatNewUserRow.add( day );
		creatNewUserRow.add( hour );

		getBodyPanel().add( creatNewUserRow );
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
					createNewUser();
				}
			}
		} );
	}

	private void createNewUser()
	{
		Integer monthValue = null;
		Integer dayValue = null;
		Integer hourValue = null;
		Integer auth = null;
		boolean tokenExpire = true;
		if ( extendExpire.getValue() )
		{
			monthValue = month.getValue();
			dayValue = day.getValue();
			hourValue = hour.getValue();
		}
		if ( isAdminCheckBox.getValue() )
		{
			auth = 255;
			if ( !extendExpire.getValue() )
			{
				tokenExpire = false;
			}
		}
		CreateNewUserModel createUserModel = new CreateNewUserModel( userName.getValue(), password.getValue(), email.getValue(), userGroupDropDown.getValue().get( 0 ).getName(), isAdminCheckBox.getValue(), tokenExpire, monthValue, dayValue, hourValue );
		createUserModel.setAuth( auth );
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().createUser( createUserModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( createUserModel.getName() + " Created..!", "rounded" );
				if ( updateUserDataTableCmd != null )
				{
					updateUserDataTableCmd.executeWithData( null );
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
		if ( userName.validate() && password.validate() && email.validate() && userGroupDropDown.getValue() != null )
		{
			if ( extendExpire.getValue() )
			{
				if ( month.validate() && day.validate() && hour.validate() )
				{
					valid = true;
				}
			}
			else
			{
				valid = true;
			}
		}
		return valid;

	}

}
