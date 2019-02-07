package com.fmlb.forsaos.client.application.management.repo;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.ConfirmationPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.REPOCIFSCreateModel;
import com.fmlb.forsaos.client.application.models.REPOISCSICreateModel;
import com.fmlb.forsaos.client.application.models.REPOLocalCreateModel;
import com.fmlb.forsaos.client.application.models.REPOMainModel;
import com.fmlb.forsaos.client.application.models.REPOModel;
import com.fmlb.forsaos.client.application.models.REPOMountAllModel;
import com.fmlb.forsaos.client.application.models.REPONFSCreateModel;
import com.fmlb.forsaos.client.application.models.REPOUnMountAllModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.data.HasCategories;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.TableHeader;
import gwt.material.design.client.ui.table.TableSubHeader;

public class CustomCategoryComponent extends CategoryComponent
{
	private static final String manageRepoActivator = "manageRepoActivator";

	private List<REPOModel> repoModels;

	private IcommandWithData icommandWithData;

	private UIComponentsUtil uiComponentsUtil;

	private MaterialDataTable<REPOModel> materialDataTable;
	
	private ConfirmationPopup confirmDeletionPopup;
	
	private ConfirmPasswordPopup confirmPasswordPopup;
	
	AppResources resources = GWT.create( AppResources.class );

	private CurrentUser currentUser;

	public CustomCategoryComponent( UIComponentsUtil uiComponentsUtil, HasCategories parent, String name, List<REPOModel> repoModels, IcommandWithData icommandWithData, MaterialDataTable<REPOModel> materialDataTable, CurrentUser currentUser )
	{
		super( parent, name );
		this.uiComponentsUtil = uiComponentsUtil;
		this.repoModels = repoModels;
		this.icommandWithData = icommandWithData;
		this.materialDataTable = materialDataTable;
		this.currentUser = currentUser;
	}

	public CustomCategoryComponent( HasCategories parent, String name, boolean openByDefault )
	{
		super( parent, name, true );
	}

	public void attachProtocolName( TableSubHeader subheader, REPOModel rowRepoModel )
	{
		if ( rowRepoModel != null )
		{
			MaterialLabel protocolLabel = new MaterialLabel();
			protocolLabel.setText( rowRepoModel.getProtocol().toUpperCase() );
			protocolLabel.setTitle( "Edit " + rowRepoModel.getCategory() );
			protocolLabel.setHoverable( true );
			protocolLabel.addClickHandler( new ClickHandler()
			{
				@Override
				public void onClick( ClickEvent event )
				{
					event.preventDefault();
					event.stopPropagation();
					editRepo( rowRepoModel );
				}
			} );

			TableHeader header = ( TableHeader ) subheader.getWidget( 2 );
			for ( int i = 0; i < header.getWidgetCount(); i++ )
			{
				header.remove( i );
			}
			header.add( protocolLabel );
		}
	}

	public void attachProtocol( TableSubHeader subheader, AttachEvent event )
	{
		String categoryName = ( ( TableSubHeader ) event.getSource() ).getName();

		REPOModel rowRepoModel = null;
		for ( REPOModel repoModel : repoModels )
		{
			if ( repoModel.getCategory().equalsIgnoreCase( categoryName ) )
			{
				rowRepoModel = repoModel;
				break;
			}
		}
		attachProtocolIcon( subheader, rowRepoModel );
		attachProtocolName( subheader, rowRepoModel );
	}

	private void attachProtocolIcon( TableSubHeader subheader, REPOModel rowRepoModel )
	{
		if ( rowRepoModel != null )
		{
			String protocolIcon = "";
			switch( rowRepoModel.getProtocol().toLowerCase() )
			{
			case "cifs":
				protocolIcon = "/images/REPO_CIFS.jpg";
				break;
			case "iscsi":
				protocolIcon = "/images/REPO_ISCSI.jpg";
				break;
			case "nfs":
				protocolIcon = "/images/REPO_NFS.jpg";
				break;
			case "local":
				protocolIcon = "/images/REPO_LOCAL.jpg";
				break;
			}
			MaterialImage materialImage = new MaterialImage( protocolIcon );
			//image
			TableHeader header = ( TableHeader ) subheader.getWidget( 0 );
			for ( int i = 0; i < header.getWidgetCount(); i++ )
			{
				header.remove( i );
			}
			header.add( materialImage );
		}
	}

	@Override
	protected void render( TableSubHeader subheader, int column )
	{
		subheader.addAttachHandler( ( event ) -> {
			attachProtocol( subheader, event );
		} );

		String categoryName = getName();
		LoggerUtil.log( categoryName );

		TableHeader protocolRepoHeader = new TableHeader();
		MaterialLabel protocolLabel = new MaterialLabel();

		TableHeader manageRepoHeader = new TableHeader();
		MaterialButton manageButton = new MaterialButton( "Manage" );
		manageButton.setTitle( "Manage" );
		manageButton.addStyleName( "repoManageBtn");

		MaterialDropDown repoItemDropDown = getManageRepoDropDown( manageRepoActivator );
		manageButton.add( repoItemDropDown );
		repoItemDropDown.addStyleName( "repoManageDropDown");
		manageButton.setActivates( manageRepoActivator );
		repoItemDropDown.addSelectionHandler( new SelectionHandler<Widget>()
		{
			@Override
			public void onSelection( SelectionEvent<Widget> callback )
			{
				onRepoManageMenuSelection( categoryName, ( ( MaterialLink ) callback.getSelectedItem() ).getText() );
			}
		} );
		manageButton.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				event.stopPropagation();
				event.preventDefault();
//				enableDisableManageIconMenus( categoryName, repoItemDropDown );
			}
		} );

		// Here you can define any element attach to the Category Component SubHeader
		TableHeader deleteRepoHeader = new TableHeader();
		MaterialButton deleteButton = new MaterialButton();
		deleteButton.addStyleName( resources.style().repoDeleteBtn() );
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
				event.stopPropagation();
				ArrayList<String> warningMsgList = new ArrayList<String>();
				warningMsgList.add( "Do you want to delete this configuration?" );
				warningMsgList.add( "Note: All the repositories in this configuration will be unmounted." );
				
				confirmPasswordPopup = new ConfirmPasswordPopup( "Delete Configuration", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
				{
					@Override
					public void execute()
					{
						deleteRepo( categoryName );
					}
				} );
				confirmPasswordPopup.open();
			}
		});
		protocolRepoHeader.add( protocolLabel );
		deleteRepoHeader.add( deleteButton );
		manageRepoHeader.add( manageButton );

		subheader.add( new TableHeader() );
		subheader.add( protocolRepoHeader );
		subheader.add( manageRepoHeader );
		subheader.add( deleteRepoHeader );
	}

	private void editRepo( REPOModel repoModel )
	{
		REPOMainModel repoMainModel = repoModel.getRepoMainModel();
		switch( repoMainModel.getProtocol().toLowerCase() )
		{
		case "cifs":
			showCreateCifsPopup( repoMainModel );
			break;
		case "iscsi":
			showCreateIscsiPopup( repoMainModel );
			break;
		case "nfs":
			showCreateNfsPopup( repoMainModel );
			break;
		case "local":
			showCreateLocalPopup( repoMainModel );
			break;
		}
	}

	private void showCreateCifsPopup( REPOMainModel repoMainModel )
	{
		CommonServiceProvider.getCommonService().getConfigCIFSRepoModel( "{ \"query\": {\"_id\": \"" + repoMainModel.get_id() + "\"} }", new ApplicationCallBack<REPOCIFSCreateModel>()
		{
			@Override
			public void onSuccess( REPOCIFSCreateModel result )
			{
				CreateCIFSRepoPopup createCIFSRepoPopup = new CreateCIFSRepoPopup( uiComponentsUtil, icommandWithData, result, false );
				createCIFSRepoPopup.open();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void showCreateIscsiPopup( REPOMainModel repoMainModel )
	{
		CommonServiceProvider.getCommonService().getConfigISCSIRepoModel( "{ \"query\": {\"_id\": \"" + repoMainModel.get_id() + "\"} }", new ApplicationCallBack<REPOISCSICreateModel>()
		{
			@Override
			public void onSuccess( REPOISCSICreateModel result )
			{
				CreateISCSIRepoPopup createISCSIRepoPopup = new CreateISCSIRepoPopup( uiComponentsUtil, icommandWithData, result, false );
				createISCSIRepoPopup.open();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void showCreateNfsPopup( REPOMainModel repoMainModel )
	{
		CommonServiceProvider.getCommonService().getConfigNFSRepoModel( "{ \"query\": {\"_id\": \"" + repoMainModel.get_id() + "\"} }", new ApplicationCallBack<REPONFSCreateModel>()
		{
			@Override
			public void onSuccess( REPONFSCreateModel result )
			{
				CreateNFSRepoPopup createNFSRepoPopup = new CreateNFSRepoPopup( uiComponentsUtil, icommandWithData, result, false );
				createNFSRepoPopup.open();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void showCreateLocalPopup( REPOMainModel repoMainModel )
	{
		CommonServiceProvider.getCommonService().getConfigLocalRepoModel( "{ \"query\": {\"_id\": \"" + repoMainModel.get_id() + "\"} }", new ApplicationCallBack<REPOLocalCreateModel>()
		{
			@Override
			public void onSuccess( REPOLocalCreateModel result )
			{
				CreateLocalRepoPopup createLocalRepoPopup = new CreateLocalRepoPopup( uiComponentsUtil, icommandWithData, result, false );
				createLocalRepoPopup.open();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void deleteRepo( String categoryName )
	{
		String configId = null;
		for ( REPOModel repoModel : repoModels )
		{
			if ( repoModel.getCategory().equalsIgnoreCase( categoryName ) )
			{
				configId = repoModel.getRepoMainModel().get_id();
				break;
			}
		}

		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().removeConfig( configId, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				confirmPasswordPopup.close();
				icommandWithData.executeWithData( true );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
				confirmPasswordPopup.close();
			}
		} );
	}

	public List<REPOModel> getRepoModels()
	{
		return repoModels;
	}

	public void setRepoModels( List<REPOModel> repoModels )
	{
		this.repoModels = repoModels;
	}

	public MaterialDropDown getManageRepoDropDown( String activatorStr )
	{
		MaterialDropDown materialDropDown = new MaterialDropDown( activatorStr );
		materialDropDown.setStyleName( activatorStr );
		materialDropDown.setConstrainWidth( false );
		materialDropDown.setBelowOrigin( true );

		MaterialLink mount = this.uiComponentsUtil.getMaterialLink( "Mount All" );
		MaterialLink unmount = this.uiComponentsUtil.getMaterialLink( "Unmount All" );

		materialDropDown.add( mount );
		materialDropDown.add( unmount );

		return materialDropDown;
	}

	private void onRepoManageMenuSelection( String categoryName, String selectedAction )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to " + selectedAction + " the repositories?" );
		confirmDeletionPopup = new ConfirmationPopup( "", selectedAction, warningMsgList, uiComponentsUtil, new Icommand()
		{
			@Override
			public void execute()
			{
				String configId = null;
				for ( REPOModel repoModel : repoModels )
				{
					if ( repoModel.getCategory().equalsIgnoreCase( categoryName ) )
					{
						configId = repoModel.getRepoMainModel().get_id();
						break;
					}
				}
				if ( selectedAction.equalsIgnoreCase( "Mount All" ) )
				{
					mountAllRepo( configId );
				}
				else
				{
					unmountAllRepo( configId );
				}
			}
		} );
		confirmDeletionPopup.open();
	}

	private void mountAllRepo( String configId )
	{
		List<String> mount_list = new ArrayList<String>();
		for ( REPOModel repoModel : repoModels )
		{
			if ( repoModel.getRepoMainModel().get_id().equals( configId ) && repoModel.getMountStatus().equalsIgnoreCase( "Not Mounted" ) )
			{
				mount_list.add( repoModel.getSource() );
			}
		}
		REPOMountAllModel repoMountAllModel = new REPOMountAllModel( configId, mount_list );
		MaterialLoader.loading( true, confirmDeletionPopup.getBodyPanel() );
		CommonServiceProvider.getCommonService().mountConfig( repoMountAllModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
				if ( result )
				{
					MaterialToast.fireToast( "Repositories Mounted!" );
					for ( REPOModel repoModel : repoModels )
					{
						if ( repoModel.getRepoMainModel().get_id().equals( configId ) )
						{
							repoModel.setMountStatus( "Mounted" );
							materialDataTable.updateRow( repoModel );
						}
					}
				}
				else
				{
					MaterialToast.fireToast( "Failed to Mount all!" );
				}
				confirmDeletionPopup.close();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
				confirmDeletionPopup.close();
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void unmountAllRepo( String configId )
	{
		List<String> unmount_list = new ArrayList<String>();
		for ( REPOModel repoModel : repoModels )
		{
			if ( repoModel.getRepoMainModel().get_id().equals( configId ) && repoModel.getMountStatus().equalsIgnoreCase( "Mounted" ) )
			{
				unmount_list.add( repoModel.getSource() );
			}
		}
		REPOUnMountAllModel repoUnMountAllModel = new REPOUnMountAllModel( configId, unmount_list );
		MaterialLoader.loading( true, confirmDeletionPopup.getBodyPanel() );
		CommonServiceProvider.getCommonService().unmountConfig( repoUnMountAllModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				confirmDeletionPopup.close();
				if ( result )
				{
					MaterialToast.fireToast( "Repositories Unmounted!" );
					for ( REPOModel repoModel : repoModels )
					{
						if ( repoModel.getRepoMainModel().get_id().equals( configId ) )
						{
							repoModel.setMountStatus( "Not Mounted" );
							materialDataTable.updateRow( repoModel );
						}
					}
				}
				else
				{
					MaterialToast.fireToast( "Failed to Unmount all!" );
				}
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				confirmDeletionPopup.close();
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void enableDisableManageIconMenus( String categoryName, MaterialDropDown repoItemDropDown )
	{
		boolean enableMount = false;
		boolean enableUnmount = false;
		for ( REPOModel repoModel : repoModels )
		{
			if ( repoModel.getCategory().equalsIgnoreCase( categoryName ) )
			{
				if ( repoModel.getMountStatus().equalsIgnoreCase( "mounted" ) )
				{
					enableMount = true;
				}
				else
				{
					enableUnmount = true;
				}
			}
		}
		if ( enableMount )
		{
			repoItemDropDown.getWidget( 0 ).setVisible( false );
		}
		else
		{
			repoItemDropDown.getWidget( 0 ).setVisible( true );
		}

		if ( enableUnmount )
		{
			repoItemDropDown.getWidget( 1 ).setVisible( false );
		}
		else
		{
			repoItemDropDown.getWidget( 1 ).setVisible( true );
		}
	}
}
