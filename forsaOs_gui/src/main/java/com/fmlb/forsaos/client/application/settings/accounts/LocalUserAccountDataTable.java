package com.fmlb.forsaos.client.application.settings.accounts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.UserAccountModel;
import com.fmlb.forsaos.client.application.models.UserDeleteModel;
import com.fmlb.forsaos.client.application.models.UserGroupModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Random;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class LocalUserAccountDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<UserAccountModel> materialDataTable;

	private List<UserAccountModel> userAccountModels = new ArrayList<>();

	private MaterialLabel localUserAcntLbl;

	private MaterialLabel totalLocalUserLabel;

	private int totalLocalUsers;

	private MaterialButton createUserBtn;

	MaterialSearch search;

	private CurrentUser currentUser;

	private ConfirmPasswordPopup deleteUserPopup;

	private MaterialComboBox<UserGroupModel> userGroupModelComboBox;

	private MaterialButton filterButton;

	private List<UserGroupModel> userGroupModelList = new ArrayList<UserGroupModel>();

	AppResources resources = GWT.create( AppResources.class );

	public LocalUserAccountDataTable( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createCreateEnableFeatureBtn();
		createGroupNameComboBox();
		generateData();
	}

	private void createSearchComponent()
	{
		search = new MaterialSearch();
		search.setBackgroundColor( Color.WHITE );
		search.setIconColor( Color.BLACK );
		search.setActive( true );
		search.setShadow( 1 );
		search.setDisplay( Display.INLINE );
		search.setWidth( "30" );
	}

	private void createGroupNameComboBox()
	{
		userGroupModelComboBox = ( MaterialComboBox<UserGroupModel> ) uiComponentsUtil.getComboBoxModelDropDownWithoutNone( userGroupModelList, "Select Group", "" );
		userGroupModelComboBox.addStyleName( "userAccountDropDown" );
		filterButton = new MaterialButton( "Filter" );
		filterButton.setTitle( " Filter" );
		filterButton.setIconPosition( IconPosition.RIGHT );
		filterButton.addStyleName( resources.style().sendAlertBtn() );
		filterButton.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				getFilterIcommandWithData().executeWithData( null );

			}
		} );
		updateUserGroupComboBox();

	}

	private void updateUserGroupComboBox()
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getGroupNames( null, new ApplicationCallBack<List<UserGroupModel>>()
		{
			@Override
			public void onSuccess( List<UserGroupModel> userGroupModels )
			{
				MaterialLoader.loading( false );
				userGroupModelList.clear();
				userGroupModelList = userGroupModels;
				UserGroupModel allModel = new UserGroupModel()
				{

					@Override
					public String getDisplayName()
					{
						return "All";
					}

					@Override
					public String getId()
					{
						return Random.nextInt() + "" + Random.nextDouble();
					}

				};
				userGroupModelComboBox.clear();
				userGroupModelComboBox.addItem( allModel.getDisplayName(), ( UserGroupModel ) allModel );
				for ( UserGroupModel userGrpModel : userGroupModelList )
				{
					userGroupModelComboBox.addItem( userGrpModel.getDisplayName(), ( UserGroupModel ) userGrpModel );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void createCreateEnableFeatureBtn()
	{
		createUserBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create User" );
		createUserBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createUserBtnAction();

			}
		} );
	}

	private void createUserBtnAction()
	{

		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getGroupNames( null, new ApplicationCallBack<List<UserGroupModel>>()
		{
			@Override
			public void onSuccess( List<UserGroupModel> userGroupModels )
			{
				MaterialLoader.loading( false );
				CreateNewUserPopup CreateNewUsrPopUP = new CreateNewUserPopup( uiComponentsUtil, getFilterIcommandWithData(), currentUser, userGroupModels );
				CreateNewUsrPopUP.open();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<UserAccountModel>();
		materialDataTable.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					// materialDataTable.setHeight( "calc(30vh - 20px)" );
					materialDataTable.setHeight( "calc(72vh - 30px)" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					localUserAcntLbl = uiComponentsUtil.getDataGridBlackHeaderLabel( "Local User Accounts" );
					totalLocalUserLabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalLocalUsers + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( localUserAcntLbl );
					materialDataTable.getScaffolding().getInfoPanel().add( totalLocalUserLabel );
					materialDataTable.getScaffolding().getToolPanel().add( userGroupModelComboBox );
					materialDataTable.getScaffolding().getToolPanel().add( filterButton );
					materialDataTable.getScaffolding().getToolPanel().add( createUserBtn );
					// materialDataTable.getScaffolding().getToolPanel().add(search);
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					// materialDataTable.getScaffolding().getTopPanel().add(search);

					materialDataTable.getColumnMenuIcon().setVisible( false );

				}

			}
		} );
		materialDataTable.addColumn( new TextColumn<UserAccountModel>()
		{
			@Override
			public Comparator< ? super RowComponent<UserAccountModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}

			@Override
			public String getValue( UserAccountModel object )
			{
				return object.getName().toString();
			}
		}, "User Name" );

		materialDataTable.addColumn( new WidgetColumn<UserAccountModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( UserAccountModel object )
			{
				MaterialButton changePassBtn = new MaterialButton();
				changePassBtn.setTitle( "Change Password" );
				changePassBtn.setIconType( IconType.LOCK );
				changePassBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						changePasswordUserBtnAction( object );

					}
				} );
				return changePassBtn;
			}
		}, "Change Password" );

		materialDataTable.addColumn( new WidgetColumn<UserAccountModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( UserAccountModel accountModel )
			{
				MaterialButton extendExpiry = new MaterialButton();
				extendExpiry.setTitle( "Extend" );
				extendExpiry.setIconType( IconType.TIMER );
				if ( !accountModel.getToken_expire() )
				{
					extendExpiry.setEnabled( false );
					return extendExpiry;
				}
				extendExpiry.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						extendExpiry( accountModel );
					}
				} );
				return extendExpiry;
			}
		}, "Extend Token" );

		materialDataTable.addColumn( new WidgetColumn<UserAccountModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( UserAccountModel object )
			{
				MaterialButton deleteBtn = new MaterialButton();
				deleteBtn.setTitle( "Delete" );
				deleteBtn.setIconType( IconType.DELETE );
				deleteBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						deleteUserBtnAction( object );

					}
				} );
				return deleteBtn;
			}
		}, "Delete User" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void generateData()
	{
		List<UserAccountModel> result = new ArrayList<UserAccountModel>();
		totalLocalUsers = result.size();
		createTable();
		materialRow.add( materialDataTable );
		add( materialRow );
	}

	public IcommandWithData getFilterIcommandWithData()
	{
		IcommandWithData icommand = new IcommandWithData()
		{
			@Override
			public void executeWithData( Object data )
			{
				String query = null;
				if ( userGroupModelComboBox != null && userGroupModelComboBox.getValue() != null && !userGroupModelComboBox.getValue().isEmpty() )
				{
					if ( userGroupModelComboBox.getValue().get( 0 ).getDisplayName().equalsIgnoreCase( "All" ) )
					{
						query = null;
					}
					else
					{
						JSONObject object = new JSONObject();
						object.put( "groups", RestCallUtil.getJSONString( userGroupModelComboBox.getValue().get( 0 ).getId() ) );
						query = RestCallUtil.getQueryRequest( object.toString() );
					}
				}
				if ( data != null && data instanceof String )
				{
					query = ( String ) data;
				}
				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().getUserNames( query, new ApplicationCallBack<List<UserAccountModel>>()
				{
					@Override
					public void onSuccess( List<UserAccountModel> result )
					{
						MaterialLoader.loading( false );
						userAccountModels.clear();
						userAccountModels.addAll( result );
						materialDataTable.setRowData( 0, userAccountModels );

						totalLocalUsers = userAccountModels.size();
						if ( totalLocalUserLabel != null )
						{
							totalLocalUserLabel.setText( "Total Items: " + "(" + totalLocalUsers + ")" );
						}
						materialDataTable.getView().setRedraw( true );
						materialDataTable.getView().refresh();
					}

					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false );
						super.onFailureShowErrorPopup( caught, null );
					}
				} );
			}
		};

		return icommand;

	}

	public IcommandWithData getUpdateIcommandWithData()
	{
		IcommandWithData icommand = new IcommandWithData()
		{
			@Override
			public void executeWithData( Object data )
			{
				String query = null;
				if ( data != null && data instanceof String )
				{
					query = ( String ) data;
				}
				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().getUserNames( query, new ApplicationCallBack<List<UserAccountModel>>()
				{
					@Override
					public void onSuccess( List<UserAccountModel> result )
					{
						MaterialLoader.loading( false );
						userAccountModels.clear();
						userAccountModels.addAll( result );
						materialDataTable.setRowData( 0, userAccountModels );

						totalLocalUsers = userAccountModels.size();
						if ( totalLocalUserLabel != null )
						{
							totalLocalUserLabel.setText( "Total Items: " + "(" + totalLocalUsers + ")" );
						}
						materialDataTable.getView().setRedraw( true );
						materialDataTable.getView().refresh();
						updateUserGroupComboBox();
					}

					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false );
						super.onFailureShowErrorPopup( caught, null );
					}
				} );
			}
		};

		return icommand;

	}

	private void deleteUserBtnAction( UserAccountModel userModel )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Are you sure you want to remove " + userModel.getName() + " ?" );
		deleteUserPopup = new ConfirmPasswordPopup( "Delete User", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{

				MaterialLoader.loading( true, deleteUserPopup.getBodyPanel() );
				UserDeleteModel deleteUser = new UserDeleteModel( userModel.getName() );
				CommonServiceProvider.getCommonService().deleteUser( deleteUser, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, deleteUserPopup.getBodyPanel() );
						deleteUserPopup.close();
						MaterialToast.fireToast( deleteUser.getName() + " Deleted..!", "rounded" );
						getFilterIcommandWithData().executeWithData( null );
					}

					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false, deleteUserPopup.getBodyPanel() );
						super.onFailureShowErrorPopup( caught, null );
					}
				} );
			}
		} );
		deleteUserPopup.open();

	}

	private void changePasswordUserBtnAction( UserAccountModel userModel )
	{
		ChangePasswordPopUp changePassword = new ChangePasswordPopUp( userModel, uiComponentsUtil, getFilterIcommandWithData(), currentUser );
		changePassword.open();
	}

	private void extendExpiry( UserAccountModel accountModel )
	{
		ExtendExpiryTokenPopup expiryTokenPopup = new ExtendExpiryTokenPopup( uiComponentsUtil, getFilterIcommandWithData(), currentUser, accountModel );
		expiryTokenPopup.open();
	}
}
