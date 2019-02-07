package com.fmlb.forsaos.client.application.management.repo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmationPopup;
import com.fmlb.forsaos.client.application.common.DataInfoPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.REPOISCSICreateModel;
import com.fmlb.forsaos.client.application.models.REPOLocalCreateModel;
import com.fmlb.forsaos.client.application.models.REPOModel;
import com.fmlb.forsaos.client.application.models.REPOMountAllModel;
import com.fmlb.forsaos.client.application.models.REPOMountInfoISCSIModel;
import com.fmlb.forsaos.client.application.models.REPOMountInfoLocalModel;
import com.fmlb.forsaos.client.application.models.REPOUnMountAllModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.fmlb.forsaos.shared.application.utility.ErrorCodes;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.events.CategoryOpenedEvent;
import gwt.material.design.client.data.events.CategoryOpenedHandler;
import gwt.material.design.client.data.events.RowSelectEvent;
import gwt.material.design.client.data.events.RowSelectHandler;
import gwt.material.design.client.data.events.SelectAllEvent;
import gwt.material.design.client.data.events.SelectAllHandler;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class REPODataTable extends MaterialPanel
{

	private static final String createMenuActivator = "createRepoMenu";

	private static final String repoItemMenuActivator = "createRepoItemMenu";

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private List<REPOModel> repoModels = new ArrayList<REPOModel>();

	private MaterialDataTable<REPOModel> materialDataTable;

	private MaterialDataPager<REPOModel> pager;

	private ListDataSource<REPOModel> dataSource;

	private MaterialLabel vmLabel;

	private MaterialLabel totalVMlabel;

	private int totalRepos;

	private MaterialButton createRepoBtn;

	MaterialSearch search;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	AppResources resources = GWT.create( AppResources.class );

	private MaterialDropDown createRepoDropDown;

	private ConfirmationPopup confirmDeletionPopup;

	public REPODataTable( int height, UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.placeManager = placeManager;
		this.navigationCmd = navigationCmd;
		initializeDataTable();
	}

	private void initializeDataTable()
	{
		createSearchComponent();
		createCreateRepoMenu();
		createCreateRepoBtn();
		generateData();
	}

	private void createCreateRepoMenu()
	{
		createRepoDropDown = getCreateRepoDropDown( createMenuActivator );
		/*createRepoDropDown.addSelectionHandler( new SelectionHandler<Widget>()
		{
			@Override
			public void onSelection( SelectionEvent<Widget> event )
			{
				showCreateRepoPopup( ( ( MaterialLink ) event.getSelectedItem() ).getText() );
			}
		} );*/
	}

	private void showCreateRepoPopup( String repo )
	{
		LoggerUtil.log( repo );
		switch( repo.toLowerCase() )
		{
		case "cifs":
			CreateCIFSRepoPopup cifsPopUp = new CreateCIFSRepoPopup( uiComponentsUtil, getUpdateIcommandWithData(), null, true );
			cifsPopUp.open();
			break;
		case "iscsi":
			CreateISCSIRepoPopup iscsiPopup = new CreateISCSIRepoPopup( uiComponentsUtil, getUpdateIcommandWithData(), null, true );
			iscsiPopup.open();
			break;
		case "nfs":
			CreateNFSRepoPopup nfsPopUp = new CreateNFSRepoPopup( uiComponentsUtil, getUpdateIcommandWithData(), null, true );
			nfsPopUp.open();
			break;
		case "local":
			CreateLocalRepoPopup localPopUp = new CreateLocalRepoPopup( uiComponentsUtil, getUpdateIcommandWithData(), null, true );
			localPopUp.open();
			break;
		default:
			break;
		}
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

	private void createCreateRepoBtn()
	{
		createRepoBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Add Target" );
		createRepoBtn.setActivates( createMenuActivator );
		createRepoBtn.add( createRepoDropDown );
	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<REPOModel>();
		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setCategoryFactory( new CustomCategoryFactory( uiComponentsUtil, repoModels, getUpdateIcommandWithData(), materialDataTable, currentUser ) );
		materialDataTable.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					materialDataTable.setHeight( "calc(72vh - 50px)" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					vmLabel = uiComponentsUtil.getDataGridBlackHeaderLabel( "Targets" );
					totalVMlabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalRepos + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( vmLabel );
					materialDataTable.getScaffolding().getInfoPanel().add( totalVMlabel );
					materialDataTable.getScaffolding().getToolPanel().add( createRepoBtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					materialDataTable.addStyleName( "repoTableHeader" );
				}

			}
		} );

		/*materialDataTable.addColumn( new TextColumn<REPOModel>()
		{
			@Override
			public String getValue( REPOModel object )
			{
				return "";
			}
		}, "" );*/

		materialDataTable.addColumn( new WidgetColumn<REPOModel, MaterialButton>()
		{
			@Override
			public String width()
			{
				return "6%";
			}

			@Override
			public MaterialButton getValue( REPOModel repoModel )
			{
				MaterialButton repoMenu = new MaterialButton();
				repoMenu.setTitle( "Manage" );
				repoMenu.setIconType( IconType.SETTINGS );
				repoMenu.setIconColor( Color.BLUE );
				MaterialDropDown repoItemDropDown = getRepoItemDropDown( repoItemMenuActivator );
				repoItemDropDown.addStyleName( resources.style().power_action_dropdown() );
				repoMenu.add( repoItemDropDown );
				repoMenu.setActivates( repoItemMenuActivator );
				repoItemDropDown.addSelectionHandler( new SelectionHandler<Widget>()
				{
					@Override
					public void onSelection( SelectionEvent<Widget> callback )
					{
						onRepoMenuSelection( repoModel, ( ( MaterialLink ) callback.getSelectedItem() ).getText() );
					}
				} );
				repoMenu.addClickHandler( new ClickHandler()
				{
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						enableDisableManageIconMenus( repoModel, repoItemDropDown );
					}
				} );
				return repoMenu;
			}
		}, "" );

		materialDataTable.addColumn( new TextColumn<REPOModel>()
		{
			@Override
			public String width()
			{
				return "25%";
			}

			@Override
			public Comparator< ? super RowComponent<REPOModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}

			@Override
			public String getValue( REPOModel repoModel )
			{
				return repoModel.getName();
			}
		}, "Name" );

		materialDataTable.addColumn( new TextColumn<REPOModel>()
		{
			@Override
			public String width()
			{
				return "20%";
			}

			@Override
			public Comparator< ? super RowComponent<REPOModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getMountStatus().compareTo( o2.getData().getMountStatus() );
			}

			@Override
			public String getValue( REPOModel object )
			{
				return object.getMountStatus();
			}
		}, "Mount Status" );

		materialDataTable.addColumn( new TextColumn<REPOModel>()
		{
			@Override
			public String width()
			{
				return "30%";
			}

			@Override
			public String getValue( REPOModel object )
			{
				return "";
			}
		}, "" );
		materialDataTable.addColumn( new TextColumn<REPOModel>()
		{
			@Override
			public String width()
			{
				return "10%";
			}

			@Override
			public String getValue( REPOModel object )
			{
				return "";
			}
		}, "" );
		materialDataTable.addColumn( new TextColumn<REPOModel>()
		{
			/*@Override
			public String width()
			{
				return "10%";
			}*/
			@Override
			public String getValue( REPOModel object )
			{
				return "";
			}
		}, "" );
		materialDataTable.addColumn( new TextColumn<REPOModel>()
		{
			/*@Override
			public String width()
			{
				return "5%";
			}*/
			@Override
			public String getValue( REPOModel object )
			{
				return "";
			}
		}, "" );

		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );

		materialDataTable.addRowSelectHandler( new RowSelectHandler<REPOModel>()
		{
			@Override
			public void onRowSelect( RowSelectEvent<REPOModel> event )
			{
				//				enableDisableDelIcon();
			}
		} );
		materialDataTable.addSelectAllHandler( new SelectAllHandler<REPOModel>()
		{
			@Override
			public void onSelectAll( SelectAllEvent<REPOModel> event )
			{
				//				enableDisableDelIcon();
			}
		} );

		materialDataTable.addCategoryOpenedHandler( new CategoryOpenedHandler()
		{
			@Override
			public void onCategoryOpened( CategoryOpenedEvent event )
			{
			}
		} );

		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.setUseCategories( true );
		materialDataTable.setUseStickyHeader( true );
		materialDataTable.setSelectionType( SelectionType.NONE );
	}

	private void onRepoMenuSelection( REPOModel repoModel, String selectedAction )
	{
		if ( selectedAction.replaceAll( "\\s+", "" ).equalsIgnoreCase( "showdetails" ) )
		{
			showDetailsSelectedRepoItem( repoModel );
			return;
		}
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to " + selectedAction + " the Repository?" );
		confirmDeletionPopup = new ConfirmationPopup( "", selectedAction, warningMsgList, uiComponentsUtil, new Icommand()
		{
			@Override
			public void execute()
			{
				confirmDeletionPopup.close();
				switch( selectedAction.replaceAll( "\\s+", "" ).toLowerCase() )
				{
				case "mount":
					mountSelectedRepoItem( repoModel );
					break;
				case "unmount":
					unmountSelectedRepoItem( repoModel );
					break;
				case "format":
					formatSelectedRepoItem( repoModel );
					break;
				}
			}
		} );
		confirmDeletionPopup.open();
	}

	private void mountSelectedRepoItem( REPOModel repoModel )
	{
		MaterialLoader.loading( true, confirmDeletionPopup.getBodyPanel() );
		List<String> mountList = new ArrayList<String>();
		mountList.add( repoModel.getSource() );
		REPOMountAllModel repoMountAllModel = new REPOMountAllModel( repoModel.getRepoMainModel().get_id(), mountList );
		CommonServiceProvider.getCommonService().mountConfig( repoMountAllModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
				if ( result )
				{
					MaterialToast.fireToast( "Repository Mounted!" );
					repoModel.setMountStatus( "Mounted" );
					materialDataTable.updateRow( repoModel );
				}
				else
				{
					MaterialToast.fireToast( "Failed to Mount!" );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void unmountSelectedRepoItem( REPOModel repoModel )
	{
		MaterialLoader.loading( true, confirmDeletionPopup.getBodyPanel() );
		List<String> mountList = new ArrayList<String>();
		mountList.add( repoModel.getSource() );
		REPOUnMountAllModel repoUnMountAllModel = new REPOUnMountAllModel( repoModel.getRepoMainModel().get_id(), mountList );
		CommonServiceProvider.getCommonService().unmountConfig( repoUnMountAllModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
				if ( result )
				{
					MaterialToast.fireToast( "Repository Unmounted!" );
					repoModel.setMountStatus( "Not Mounted" );
					materialDataTable.updateRow( repoModel );
				}
				else
				{
					MaterialToast.fireToast( "Failed to Unmount!" );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void showDetailsSelectedRepoItem( REPOModel repoModel )
	{
		MaterialLoader.loading( true );
		if ( repoModel.getRepoMainModel().getProtocol().equalsIgnoreCase( "local" ) )
		{
			showLocalRepoInfo( repoModel );
		}
		else
		{
			showISCSIRepoInfo( repoModel );
		}
	}

	private void showLocalRepoInfo( REPOModel repoModel )
	{
		CommonServiceProvider.getCommonService().getConfigLocalRepoModel( "{ \"query\": {\"_id\": \"" + repoModel.getRepoMainModel().get_id() + "\"} }", new ApplicationCallBack<REPOLocalCreateModel>()
		{
			@Override
			public void onSuccess( REPOLocalCreateModel result )
			{
				MaterialLoader.loading( false );

				REPOMountInfoLocalModel repoMountInfo = null;
				for ( REPOMountInfoLocalModel repoMountInfoLocalModel : result.getMountinfo() )
				{
					if ( repoModel.getSource().equalsIgnoreCase( repoMountInfoLocalModel.getSource() ) )
					{
						repoMountInfo = repoMountInfoLocalModel;
						break;
					}
				}
				if ( repoMountInfo != null )
				{
					Map<String, String> data = new HashMap<String, String>();
					data.put( "Device Name:", repoMountInfo.getSource() );
					data.put( "Device ID:", repoMountInfo.getUuid() );
					data.put( "Device Type:", repoMountInfo.getType() );
					data.put( "Size:", repoMountInfo.getSize() );
					data.put( "Vendor:", repoMountInfo.getModel() );
					/*data.put( "BUS:", "NOT AVAILABLE" );
					String partitions = "";
					if ( repoMountInfo.getPartitions() != null && repoMountInfo.getPartitions().length != 0 )
					{
						for ( String partition : repoMountInfo.getPartitions() )
						{
							partitions = partitions + partition + ", ";
						}
						partitions = partitions.substring( 0, partitions.length() - 2 );
					}
					else
					{
						partitions = "Not Available";
					}
					data.put( "Partition Info", partitions );*/

					DataInfoPopup dataInfoPopup = new DataInfoPopup( "Disk Information", "CLOSE", data, uiComponentsUtil );
					dataInfoPopup.open();
				}
				else
				{
					super.onFailureShowErrorPopup( new FBException( "Unable to fetch information.", ErrorCodes.GENERIC_ERROR ), null );
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

	private void showISCSIRepoInfo( REPOModel repoModel )
	{
		CommonServiceProvider.getCommonService().getConfigISCSIRepoModel( "{ \"query\": {\"_id\": \"" + repoModel.getRepoMainModel().get_id() + "\"} }", new ApplicationCallBack<REPOISCSICreateModel>()
		{
			@Override
			public void onSuccess( REPOISCSICreateModel result )
			{
				MaterialLoader.loading( false );

				REPOMountInfoISCSIModel repoMountInfo = null;
				for ( REPOMountInfoISCSIModel repoMountInfoLocalModel : result.getMountinfo() )
				{
					if ( repoModel.getSource().equalsIgnoreCase( repoMountInfoLocalModel.getSource() ) )
					{
						repoMountInfo = repoMountInfoLocalModel;
						break;
					}
				}
				if ( repoMountInfo != null )
				{
					Map<String, String> data = new HashMap<String, String>();
					data.put( "DEVICE NAME", repoMountInfo.getSource() );
					data.put( "DEVICE ID", repoMountInfo.getUuid() );
					data.put( "DEVICE TYPE", repoMountInfo.getType() );
					data.put( "SIZE", repoMountInfo.getSize() );
					data.put( "VENDOR", repoMountInfo.getModel() );
					/*data.put( "BUS", "NOT AVAILABLE" );
					String partitions = "";
					if ( repoMountInfo.getPartitions() != null && repoMountInfo.getPartitions().length != 0 )
					{
						for ( String partition : repoMountInfo.getPartitions() )
						{
							partitions = partitions + partition + ", ";
						}
						partitions = partitions.substring( 0, partitions.length() - 2 );
					}
					else
					{
						partitions = "NOT AVAILABLE";
					}
					data.put( "PARTITION INFO", partitions );*/

					DataInfoPopup dataInfoPopup = new DataInfoPopup( "DISK INFORMATION", "CLOSE", data, uiComponentsUtil );
					dataInfoPopup.open();
				}
				else
				{
					super.onFailureShowErrorPopup( new FBException( "Unable to fetch information.", ErrorCodes.GENERIC_ERROR ), null );
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

	private void formatSelectedRepoItem( REPOModel repoModel )
	{
		MaterialLoader.loading( true, confirmDeletionPopup.getBodyPanel() );
		String requestJson = "{\"name\":\"" + repoModel.getSource() + "\",\"fs\":\"\"}";
		CommonServiceProvider.getCommonService().makeFs( requestJson, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
				if ( result )
				{
					MaterialToast.fireToast( "Format triggered successfully..!" );
				}
				else
				{
					MaterialToast.fireToast( "Failed to Unmount!" );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, confirmDeletionPopup.getBodyPanel() );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	private void generateData()
	{
		LoggerUtil.log( "generate data REPO" );
		dataSource = new ListDataSource<REPOModel>();
		List<REPOModel> result = new ArrayList<REPOModel>();
		dataSource.add( 0, result );
		/*for ( int i = 0; i < 5; i++ )
		{
			for ( int j = 0; j < 5; j++ )
			{
				REPOModel repoModel = new REPOModel( "cat"+i, i+"ij"+j, i+"ij"+j, i+"ij"+j, i+"ij"+j, null );
				result.add( repoModel );
			}
		}*/
		//		materialDataTable.setRowData( 0, result );
		//		pager.setDataSource( dataSource );
		totalRepos = 0;
		createTable();
		pager = new MaterialDataPager<REPOModel>( materialDataTable, dataSource );
		materialDataTable.add( pager );
		materialRow.add( materialDataTable );
		materialDataTable.getView().setRedraw( true );
		materialDataTable.getView().refresh();
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
				updateREPOGrid( ( boolean ) data );
			}
		};
		return icommand;
	}

	private void updateREPOGrid( Boolean addDelayFl )
	{
		CommonServiceProvider.getCommonService().getConfigRepo( "{}", addDelayFl, new ApplicationCallBack<List<REPOModel>>()
		{
			@Override
			public void onSuccess( List<REPOModel> result )
			{
				materialDataTable.clearCategories();
				repoModels = result;
				materialDataTable.setRowData( 0, repoModels );
				materialDataTable.setCategoryFactory( new CustomCategoryFactory( uiComponentsUtil, repoModels, getUpdateIcommandWithData(), materialDataTable, currentUser ) );
				if ( materialDataTable.getCategories() != null )
				{
					totalRepos = materialDataTable.getCategories().size();
					for ( CategoryComponent categoryComponent : materialDataTable.getCategories() )
					{
						CustomCategoryComponent customCategoryComponent = ( CustomCategoryComponent ) categoryComponent;
						customCategoryComponent.setRepoModels( repoModels );
					}
				}
				else
				{
					totalRepos = 0;
				}
				dataSource = new ListDataSource<REPOModel>();
				dataSource.add( 0, result );
				pager.setDataSource( dataSource );
				materialDataTable.getView().setRedraw( true );
				materialDataTable.getView().refresh();
				for ( REPOModel repoModel : repoModels )
				{
					LoggerUtil.log( repoModel.getCategory() + " " + repoModel.getName() );
					if ( repoModel.getRepoMainModel().getMountinfo() == null || repoModel.getRepoMainModel().getMountinfo().length == 0 )
					{
						materialDataTable.disableCategory( repoModel.getRepoMainModel().getName() );
						LoggerUtil.log( repoModel.getRepoMainModel().getName() );
					}
				}
				totalVMlabel.setText( "Total Items: " + "(" + totalRepos + ")" );
				MaterialLoader.loading( false );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}

	public MaterialDropDown getRepoItemDropDown( String activatorStr )
	{
		MaterialDropDown materialDropDown = new MaterialDropDown( activatorStr );
		materialDropDown.setStyleName( activatorStr );
		materialDropDown.setConstrainWidth( false );
		materialDropDown.setBelowOrigin( true );

		MaterialLink mount = this.uiComponentsUtil.getMaterialLink( "Mount" );
		MaterialLink unmount = this.uiComponentsUtil.getMaterialLink( "Unmount" );
		MaterialLink details = this.uiComponentsUtil.getMaterialLink( "Show Details" );
		MaterialLink format = this.uiComponentsUtil.getMaterialLink( "Format" );

		materialDropDown.add( mount );
		materialDropDown.add( unmount );
		materialDropDown.add( details );
		materialDropDown.add( format );

		return materialDropDown;
	}

	private void enableDisableManageIconMenus( REPOModel repoModel, MaterialDropDown repoItemLevelDropDown )
	{
		if ( repoModel.getMountStatus().equalsIgnoreCase( "mounted" ) )
		{
			repoItemLevelDropDown.getWidget( 0 ).setVisible( false );
			repoItemLevelDropDown.getWidget( 1 ).setVisible( true );
		}
		else
		{
			repoItemLevelDropDown.getWidget( 0 ).setVisible( true );
			repoItemLevelDropDown.getWidget( 1 ).setVisible( false );
		}
		switch( repoModel.getProtocol().toLowerCase() )
		{
		case "cifs":
			repoItemLevelDropDown.getWidget( 2 ).setVisible( false );
			repoItemLevelDropDown.getWidget( 3 ).setVisible( false );
			break;
		case "iscsi":
			repoItemLevelDropDown.getWidget( 2 ).setVisible( true );
			repoItemLevelDropDown.getWidget( 3 ).setVisible( true );
			break;
		case "local":
			repoItemLevelDropDown.getWidget( 2 ).setVisible( true );
			repoItemLevelDropDown.getWidget( 3 ).setVisible( true );
			break;
		case "nfs":
			repoItemLevelDropDown.getWidget( 2 ).setVisible( false );
			repoItemLevelDropDown.getWidget( 3 ).setVisible( false );
			break;
		}
	}

	public MaterialDropDown getCreateRepoDropDown( String activatorStr )
	{
		MaterialDropDown materialDropDown = new MaterialDropDown( activatorStr );
		materialDropDown.setStyleName( activatorStr );
		materialDropDown.setConstrainWidth( false );
		materialDropDown.setBelowOrigin( true );

		MaterialLink cifs = this.uiComponentsUtil.getMaterialLink( "CIFS" );
		MaterialLink iscsi = this.uiComponentsUtil.getMaterialLink( "ISCSI" );
		MaterialLink nfs = this.uiComponentsUtil.getMaterialLink( "NFS" );
		MaterialLink local = this.uiComponentsUtil.getMaterialLink( "LOCAL" );

		materialDropDown.add( cifs );
		materialDropDown.add( iscsi );
		materialDropDown.add( nfs );
		materialDropDown.add( local );

		cifs.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				showCreateRepoPopup( "cifs" );
			}
		} );
		iscsi.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				showCreateRepoPopup( "iscsi" );
			}
		} );
		nfs.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				showCreateRepoPopup( "nfs" );
			}
		} );
		local.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				showCreateRepoPopup( "local" );
			}
		} );

		return materialDropDown;
	}
}
