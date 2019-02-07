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
import com.fmlb.forsaos.client.application.models.DeleteActiveDirectory;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
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

public class ActiveDirectoryDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private MaterialDataTable<ActiveDirectoryModel> materialDataTable;

	private List<ActiveDirectoryModel> activeDirectoryModels = new ArrayList<>();

	private MaterialLabel activeDirectoryLbl;

	private MaterialLabel totalActiveDirLabel;

	private int totalActiveDirs;

	private MaterialButton createActivDirBtn;

	MaterialSearch search;

	private CurrentUser currentUser;

	private ConfirmPasswordPopup delActiveDirConfirmPasswordPopup;

	public ActiveDirectoryDataTable( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
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
		createActivDirBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Join Active Directory" );
		createActivDirBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createActiveDirBtnAction();

			}
		} );
	}

	private void deleteDomainBtnAction( ActiveDirectoryModel directoryModel )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to delete active directory domain " + directoryModel.getName() + " ?" );
		delActiveDirConfirmPasswordPopup = new ConfirmPasswordPopup( "Leave Domain", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				LoggerUtil.log( "Delete Domain" );
				MaterialLoader.loading( true, delActiveDirConfirmPasswordPopup.getBodyPanel() );
				DeleteActiveDirectory deleteActiveDirectory = new DeleteActiveDirectory( directoryModel.getName() );
				CommonServiceProvider.getCommonService().deleteDomain( deleteActiveDirectory, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, delActiveDirConfirmPasswordPopup.getBodyPanel() );
						delActiveDirConfirmPasswordPopup.close();
						MaterialToast.fireToast( deleteActiveDirectory.getName() + " Deleted..!", "rounded" );
						getUpdateIcommandWithData().executeWithData( true );
					}

					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false, delActiveDirConfirmPasswordPopup.getBodyPanel() );
						super.onFailureShowErrorPopup( caught, null );
					}
				} );
			}
		} );
		delActiveDirConfirmPasswordPopup.open();
	}

	private void createActiveDirBtnAction()
	{
		CreateActiveDirPopup createPop = new CreateActiveDirPopup( uiComponentsUtil, getUpdateIcommandWithData(), currentUser );
		createPop.open();
	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<ActiveDirectoryModel>();
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
					activeDirectoryLbl = uiComponentsUtil.getDataGridBlackHeaderLabel( "Active Directories" );
					totalActiveDirLabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalActiveDirs + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( activeDirectoryLbl );
					materialDataTable.getScaffolding().getInfoPanel().add( totalActiveDirLabel );
					materialDataTable.getScaffolding().getToolPanel().add( createActivDirBtn );
					// materialDataTable.getScaffolding().getToolPanel().add(search);
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					// materialDataTable.getScaffolding().getTopPanel().add(search);

					materialDataTable.getColumnMenuIcon().setVisible( false );

				}

			}
		} );
		materialDataTable.addColumn( new TextColumn<ActiveDirectoryModel>()
		{
			@Override
			public Comparator< ? super RowComponent<ActiveDirectoryModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}

			@Override
			public String getValue( ActiveDirectoryModel object )
			{
				return object.getName().toString();
			}
		}, "Domain" );

		materialDataTable.addColumn( new TextColumn<ActiveDirectoryModel>()
		{
			@Override
			public Comparator< ? super RowComponent<ActiveDirectoryModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getIp().toString().compareToIgnoreCase( o2.getData().getIp().toString() );
			}

			@Override
			public String getValue( ActiveDirectoryModel object )
			{
				return object.getIp().toString();
			}
		}, "Machine/IP" );

		/*materialDataTable.addColumn( new WidgetColumn<ActiveDirectoryModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( ActiveDirectoryModel object )
			{
				MaterialButton rfreshBtn = new MaterialButton();
				rfreshBtn.setTitle( "Refresh" );
				rfreshBtn.setIconType( IconType.REFRESH );
				rfreshBtn.addClickHandler( new ClickHandler()
				{
		
					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
		
					}
				} );
				return rfreshBtn;
			}
		}, "Refresh" );
		
		materialDataTable.addColumn( new WidgetColumn<ActiveDirectoryModel, MaterialButton>()
		{
		
			@Override
			public MaterialButton getValue( ActiveDirectoryModel object )
			{
				MaterialButton editBtn = new MaterialButton();
				editBtn.setTitle( "Edit" );
				editBtn.setIconType( IconType.EDIT );
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
		materialDataTable.addColumn( new WidgetColumn<ActiveDirectoryModel, MaterialButton>()
		{

			@Override
			public MaterialButton getValue( ActiveDirectoryModel object )
			{
				MaterialButton deleteBtn = new MaterialButton();
				deleteBtn.setTitle( "Delete" );
				deleteBtn.setIconType( IconType.DELETE );
				deleteBtn.setBackgroundColor( Color.BLUE );
				deleteBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						deleteDomainBtnAction( object );
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
		List<ActiveDirectoryModel> result = new ArrayList<ActiveDirectoryModel>();
		totalActiveDirs = result.size();
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
				CommonServiceProvider.getCommonService().getActiveDirectoryNames( null, new ApplicationCallBack<List<ActiveDirectoryModel>>()
				{
					@Override
					public void onSuccess( List<ActiveDirectoryModel> result )
					{
						MaterialLoader.loading( false );
						activeDirectoryModels.clear();
						activeDirectoryModels.addAll( result );
						materialDataTable.setRowData( 0, activeDirectoryModels );

						totalActiveDirs = activeDirectoryModels.size();
						if ( totalActiveDirLabel != null )
						{
							totalActiveDirLabel.setText( "Total Items: " + "(" + totalActiveDirs + ")" );
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

}
