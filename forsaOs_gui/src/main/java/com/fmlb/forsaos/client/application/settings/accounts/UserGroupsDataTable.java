package com.fmlb.forsaos.client.application.settings.accounts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.ActiveDirectoryModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.UserGroupDeleteModel;
import com.fmlb.forsaos.client.application.models.UserGroupModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
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

public class UserGroupsDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<UserGroupModel> materialDataTable;

	private List<UserGroupModel> userGroupModels = new ArrayList<>();

	private MaterialLabel userGroupLabel;

	private MaterialLabel totalLocalUserLabel;

	private int totalUserGroups;

	private MaterialButton createUserGrpBtn;

	MaterialSearch search;

	private CurrentUser currentUser;

	private ConfirmPasswordPopup deleteUserGroupPopUp;

	private IcommandWithData updateUserAccountTabCommand;
	
	AppResources resources = GWT.create( AppResources.class );

	public UserGroupsDataTable( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData updateUserAccountTabCommand )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.updateUserAccountTabCommand = updateUserAccountTabCommand;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createCreateEnableFeatureBtn();
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

	private void createCreateEnableFeatureBtn()
	{
		createUserGrpBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Create User Group" );
		createUserGrpBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createUserGrpBtnAction();

			}
		} );
	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<UserGroupModel>();
		materialDataTable.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					//materialDataTable.setHeight( "calc(30vh - 20px)" );
					materialDataTable.setHeight( "calc(72vh - 50px)" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					userGroupLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "User Groups" );
					totalLocalUserLabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalUserGroups + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( userGroupLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalLocalUserLabel );
					materialDataTable.getScaffolding().getToolPanel().add( createUserGrpBtn );
					// materialDataTable.getScaffolding().getToolPanel().add(search);
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					// materialDataTable.getScaffolding().getTopPanel().add(search);

					materialDataTable.getColumnMenuIcon().setVisible( false );

				}

			}
		} );

		materialDataTable.addColumn( new TextColumn<UserGroupModel>()
		{
			@Override
			public Comparator< ? super RowComponent<UserGroupModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}

			@Override
			public String getValue( UserGroupModel object )
			{
				return object.getName().toString();
			}
		}, "User Group" );

		/*	materialDataTable.addColumn( new WidgetColumn<UserGroupModel, MaterialButton>()
			{
				@Override
				public MaterialButton getValue( UserGroupModel object )
				{
					MaterialButton editBtn = new MaterialButton();
					editBtn.setTitle( "Edit" );
					editBtn.setIconType( IconType.EDIT );
					editBtn.setBackgroundColor( Color.BLUE );
					editBtn.addClickHandler( new ClickHandler()
					{
		
						@Override
						public void onClick( ClickEvent event )
						{
							event.stopPropagation();
		
						}
					} );
					return editBtn;
				}
			}, "Edit" );
		*/
		materialDataTable.addColumn( new WidgetColumn<UserGroupModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( UserGroupModel object )
			{
				MaterialButton refresh = new MaterialButton();
				refresh.setTitle( "Refresh" );
				refresh.setIconType( IconType.REFRESH );
				//refresh.setBackgroundColor( Color.BLUE );
				
				if ( object.getFilter().equalsIgnoreCase( "" ) )
				{
					refresh.setEnabled( false );
					return refresh;
				}
				refresh.addStyleName( "userGroupActionBtn" );
				refresh.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						refreshGroupBtnAction( object );
					}
				} );
				return refresh;
			}
		}, "Refresh" );

		materialDataTable.addColumn( new WidgetColumn<UserGroupModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( UserGroupModel object )
			{
				MaterialButton editIcn = new MaterialButton();
				editIcn.setTitle( "Edit" );
				editIcn.setIconType( IconType.EDIT );
				//editIcn.setBackgroundColor( Color.BLUE );
				editIcn.addStyleName( "userGroupActionBtn" );

				editIcn.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						editBtnAction( object );
					}
				} );
				return editIcn;
			}
		}, "Edit" );

		materialDataTable.addColumn( new WidgetColumn<UserGroupModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( UserGroupModel object )
			{
				MaterialButton deleteBtn = new MaterialButton();
				deleteBtn.setTitle( "Delete" );
				deleteBtn.setIconType( IconType.DELETE );
				// deleteBtn.setText("badge " + object.getId());
				deleteBtn.setBackgroundColor( Color.BLUE );
				deleteBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						deleteGroupBtnAction( object );

					}
				} );
				return deleteBtn;
			}
		}, "Delete" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void generateData()
	{
		List<UserGroupModel> result = new ArrayList<UserGroupModel>();
		totalUserGroups = result.size();
		createTable();
		materialRow.add( materialDataTable );
		add( materialRow );
	}

	public IcommandWithData getUpdateIcommandWithData()
	{
		IcommandWithData icommand = new IcommandWithData()
		{

			@Override
			public void executeWithData( Object data )
			{

				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().getGroupNames( null, new ApplicationCallBack<List<UserGroupModel>>()
				{
					@Override
					public void onSuccess( List<UserGroupModel> result )
					{
						MaterialLoader.loading( false );
						userGroupModels.clear();
						userGroupModels.addAll( result );
						materialDataTable.setRowData( 0, userGroupModels );

						totalUserGroups = userGroupModels.size();
						if ( totalLocalUserLabel != null )
						{
							totalLocalUserLabel.setText( "Total Items: " + "(" + totalUserGroups + ")" );
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

	private void createUserGrpBtnAction()
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getActiveDirectoryNames( null, new ApplicationCallBack<List<ActiveDirectoryModel>>()
		{
			@Override
			public void onSuccess( List<ActiveDirectoryModel> activeDirectoryModels )
			{
				MaterialLoader.loading( false );
				CreateUserGroupPopup createPop = new CreateUserGroupPopup( uiComponentsUtil, getUpdateIcommandWithData(), activeDirectoryModels, updateUserAccountTabCommand );
				createPop.open();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void deleteGroupBtnAction( UserGroupModel groupModel )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to delete user group " + groupModel.getName() + " ?" );
		deleteUserGroupPopUp = new ConfirmPasswordPopup( "Delete User Group", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				MaterialLoader.loading( true, deleteUserGroupPopUp.getBodyPanel() );
				UserGroupDeleteModel deleteGroup = new UserGroupDeleteModel( groupModel.getName() );
				CommonServiceProvider.getCommonService().deleteUserGroup( deleteGroup, new ApplicationCallBack<Boolean>()
				{

					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, deleteUserGroupPopUp.getBodyPanel() );
						deleteUserGroupPopUp.close();
						MaterialToast.fireToast( deleteGroup.getName() + " Deleted..!", "rounded" );
						getUpdateIcommandWithData().executeWithData( true );
						if ( updateUserAccountTabCommand != null )
						{
							updateUserAccountTabCommand.executeWithData( null );
						}
					}

					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false, deleteUserGroupPopUp.getBodyPanel() );
						super.onFailureShowErrorPopup( caught, null );
					}
				} );
			}
		} );
		deleteUserGroupPopUp.open();
	}

	private void refreshGroupBtnAction( UserGroupModel object )
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().refreshUserGroup( "{ \"name\":\"" + object.getName() + "\" }", new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				if ( updateUserAccountTabCommand != null )
				{
					updateUserAccountTabCommand.executeWithData( null );
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

	private void editBtnAction( UserGroupModel userGroupModel )
	{
		EditUserGroupPopup editUserGroupPopup = new EditUserGroupPopup( uiComponentsUtil, getUpdateIcommandWithData(), updateUserAccountTabCommand, userGroupModel );
		editUserGroupPopup.open();
	}

}
