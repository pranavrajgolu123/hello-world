package com.fmlb.forsaos.client.application.settings.accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.ActiveDirectoryModel;
import com.fmlb.forsaos.client.application.models.ComboBoxModel;
import com.fmlb.forsaos.client.application.models.CreateUserGroupModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.AuthCodes;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.json.client.JSONObject;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.CheckBoxType;
import gwt.material.design.client.constants.CollectionType;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.html.Option;

public class CreateUserGroupPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox localGroupName;

	private MaterialPanel domainNamePanel;

	private MaterialPanel localGrpNamePanel;

	private MaterialPanel radioBtnPanel;

	private MaterialRadioButton localGroupRdBtn;

	private MaterialRadioButton activeDirRdBtn;

	private CreateUserGroupModel createUserGroupModel;

	private IcommandWithData updateUserGroupDataTableCmd;

	private MaterialComboBox<ActiveDirectoryModel> activeDirComboBox;

	private MaterialComboBox<Option> activeDirGroupsComboBox;

	private HashMap<String, List<String>> domainToGroupNamesMap = new HashMap<String, List<String>>();

	private List<ActiveDirectoryModel> activeDirectoryModels;

	private MaterialRow permissionsRow;

	private MaterialCheckBox managementCheckBox;

	private MaterialCheckBox virtualizationCheckBox;

	private MaterialCheckBox centralCheckBox;

	private MaterialCheckBox nodeCheckBox;

	private MaterialCheckBox networkingCheckBox;

	private MaterialCheckBox controllerCheckBox;

	AppResources resources = GWT.create( AppResources.class );

	private MaterialLabel permissionsLbl;

	private IcommandWithData updateUserAccountTabCommand;

	public CreateUserGroupPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData updateUserGroupDataTableCmd, List<ActiveDirectoryModel> activeDirectoryModels, IcommandWithData updateUserAccountTabCommand )
	{
		super( "Create User Group", "", "Submit", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateUserGroupDataTableCmd = updateUserGroupDataTableCmd;
		this.activeDirectoryModels = activeDirectoryModels;
		this.updateUserAccountTabCommand = updateUserAccountTabCommand;
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow createUserGrpRow = new MaterialRow();

		createRadioBtnPnl();
		createLocalGroupNamePnael();
		createDomainNamePnl();
		createPermissionRow();

		createUserGrpRow.add( radioBtnPanel );
		createUserGrpRow.add( localGrpNamePanel );
		createUserGrpRow.add( domainNamePanel );
		createUserGrpRow.add( permissionsRow );
		getBodyPanel().add( createUserGrpRow );
	}

	private void createLocalGroupNamePnael()
	{

		localGroupName = uiComponentsUtil.getTexBox( "Group Name", "Type Group Name", "s12" );
		localGroupName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		localGroupName.addStyleName( "smartLogRow" );

		localGrpNamePanel = new MaterialPanel();
		localGrpNamePanel.add( localGroupName );
		localGrpNamePanel.addStyleName( resources.style().popup_checkbox_panel_body() );

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

	private void createDomainNamePnl()
	{
		activeDirComboBox = ( MaterialComboBox<ActiveDirectoryModel> ) uiComponentsUtil.getComboBoxModelDropDownWithoutNone( ( List< ? extends ComboBoxModel> ) activeDirectoryModels, "Domain Name", "s4" );

		activeDirComboBox.addValueChangeHandler( new ValueChangeHandler<List<ActiveDirectoryModel>>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<List<ActiveDirectoryModel>> event )
			{
				populateActiveDirectoryGrps( event.getValue().get( 0 ).getName() );

			}
		} );
		activeDirGroupsComboBox = ( MaterialComboBox<Option> ) uiComponentsUtil.getComboBox( new HashMap<String, String>(), "Select Group", "s8" );
		if ( activeDirectoryModels != null && !activeDirectoryModels.isEmpty() )
		{
			populateActiveDirectoryGrps( activeDirectoryModels.get( 0 ).getName() );
		}
		domainNamePanel = new MaterialPanel();
		domainNamePanel.setVisible( false );
		domainNamePanel.add( activeDirComboBox );

		domainNamePanel.add( activeDirGroupsComboBox );
		domainNamePanel.addStyleName( resources.style().activeCheckboxPanel() );
	}

	public List<Option> getGroupOptions( List<String> groupNames )
	{
		List<Option> options = new ArrayList<>();
		for ( int i = 0; i < groupNames.size(); i++ )
		{
			Option option = new Option();
			option.setValue( groupNames.get( i ) );
			option.setText( groupNames.get( i ) );
			option.setTitle( groupNames.get( i ) );
			options.add( option );
		}
		return options;

	}

	private void populateActiveDirectoryGrps( String activeDirectoryName )
	{
		List<String> groupNames = new ArrayList<>();
		if ( domainToGroupNamesMap.containsKey( activeDirectoryName ) )
		{
			populateActiveGrpComBox( activeDirectoryName );
		}
		else
		{
			MaterialLoader.loading( true, getBodyPanel() );
			JSONObject queryObj = new JSONObject();
			queryObj.put( "name", RestCallUtil.getJSONString( activeDirectoryName ) );
			CommonServiceProvider.getCommonService().getActiveDirectoryGroup( queryObj.toString(), new ApplicationCallBack<List<String>>()
			{

				@Override
				public void onSuccess( List<String> result )
				{
					MaterialLoader.loading( false, getBodyPanel() );
					groupNames.addAll( result );
					domainToGroupNamesMap.put( activeDirectoryName, result );
					populateActiveGrpComBox( activeDirectoryName );
				}

				@Override
				public void onFailure( Throwable caught )
				{
					super.onFailureShowErrorPopup( caught, null );

				}
			} );
		}
	}

	private void populateActiveGrpComBox( String activeDirectoryName )
	{
		List<Option> groupOptions = getGroupOptions( domainToGroupNamesMap.get( activeDirectoryName ) );
		activeDirGroupsComboBox.clear();
		for ( Option option : groupOptions )
		{
			activeDirGroupsComboBox.add( option );
		}
	}

	private void createRadioBtnPnl()
	{
		localGroupRdBtn = new MaterialRadioButton( "groupOptions", "Local" );
		localGroupRdBtn.addStyleName( resources.style().newRadioBtn() );
		localGroupRdBtn.setValue( true );
		localGroupRdBtn.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( event.getValue() )
				{
					localGrpNamePanel.setVisible( true );
					domainNamePanel.setVisible( false );
				}

			}
		} );
		activeDirRdBtn = new MaterialRadioButton( "groupOptions", "Active" );
		activeDirRdBtn.addStyleName( resources.style().newRadioBtn() );
		activeDirRdBtn.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				localGrpNamePanel.setVisible( false );
				domainNamePanel.setVisible( true );

			}
		} );
		radioBtnPanel = new MaterialPanel();
		//checkBoxPanel.add( localGrpCheckBox );
		radioBtnPanel.add( localGroupRdBtn );
		radioBtnPanel.add( activeDirRdBtn );
		radioBtnPanel.setGrid( "s12" );
		radioBtnPanel.addStyleName( resources.style().popup_checkbox_panel() );

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
					createUserGroupBtnActn();
				}
			}
		} );
	}

	private void createUserGroupBtnActn()
	{

		if ( localGroupRdBtn.getValue() )
		{
			createUserGroupModel = new CreateUserGroupModel( localGroupName.getValue().toString(), true, null );
			createUserGroupModel.setAuth( getAuthCode() );
			createUserGroup();
		}
		else
		{
			String activDirGrpName = String.valueOf( activeDirGroupsComboBox.getValue().get( 0 ) );
			createUserGroupModel = new CreateUserGroupModel( activDirGrpName, false, activeDirComboBox.getValue().get( 0 ).getName() );
			createUserGroupModel.setAuth( getAuthCode() );
			createUserGroup();
		}

	}

	private void createUserGroup()
	{
		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().createGroup( createUserGroupModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( createUserGroupModel.getName() + " Created..!", "rounded" );
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
				super.onFailureShowErrorPopup( caught, null );
			}

		} );
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( localGroupRdBtn.getValue() )
		{
			if ( localGroupName.validate() )
			{
				valid = true;
				localGroupName.clearErrorText();
			}

		}
		else if ( activeDirRdBtn.getValue() )
		{
			if ( ( activeDirComboBox.getValue() != null && !activeDirComboBox.getValue().isEmpty() ) || ( activeDirGroupsComboBox.getValue() != null && !activeDirGroupsComboBox.getValue().isEmpty() ) )
			{
				valid = true;
				activeDirComboBox.clearErrorText();
				activeDirGroupsComboBox.clearErrorText();
			}
			else
			{
				activeDirComboBox.setErrorText( "Active directory not configured" );
				activeDirGroupsComboBox.setErrorText( "Active directory not configured" );
			}
		}
		else
		{
			valid = true;

		}
		return valid;

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

}
