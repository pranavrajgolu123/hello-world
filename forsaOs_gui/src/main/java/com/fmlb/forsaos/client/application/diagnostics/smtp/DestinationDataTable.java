package com.fmlb.forsaos.client.application.diagnostics.smtp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.DeleteEmailModel;
import com.fmlb.forsaos.client.application.models.SMTPDestinationModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class DestinationDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private List<SMTPDestinationModel> smtpDestinationModel = new ArrayList<SMTPDestinationModel>();

	private MaterialDataTable<SMTPDestinationModel> materialDataTable;

	private ListDataSource<SMTPDestinationModel> dataSource;

	private MaterialLabel smtpLable;

	private MaterialButton addEmailAddressBtn;

	private MaterialButton sendAlertUserBtn;

	private MaterialDataPager<SMTPDestinationModel> pager;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private ConfirmPasswordPopup delEmailConfirmPass;

	private MaterialLabel totalEmailLable;

	private int totalEmail;

	AppResources resources = GWT.create( AppResources.class );

	public DestinationDataTable( UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{

		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		initializeDataTable();

	}

	private void initializeDataTable()
	{
		createAddEmailBtn();
		sendAlertAllUser();
		generateData();
	}

	private void createAddEmailBtn()
	{
		addEmailAddressBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Add E-mail Address" );
		addEmailAddressBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				addEmailAddessActn();
			}
		} );
	}

	private void sendAlertAllUser()
	{
		sendAlertUserBtn = new MaterialButton( "Send Alert" );
		sendAlertUserBtn.addStyleName( resources.style().sendAlertBtn() );
		sendAlertUserBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				sendAlertAllUserAction();
			}
		} );
	}

	private void addEmailAddessActn()
	{
		AddEmailAddressPopUp addEmailPopUP = new AddEmailAddressPopUp( uiComponentsUtil, getUpdateIcommand() );
		addEmailPopUP.open();

	}

	private void sendAlertAllUserAction()
	{
		SendAlertAllUserPopUp sendAlert = new SendAlertAllUserPopUp( uiComponentsUtil, currentUser );
		sendAlert.open();
	}

	private void deleteEmailBtnAction( SMTPDestinationModel emailModel )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to delete this Email Address - " + emailModel.getEmail() + "?" );
		delEmailConfirmPass = new ConfirmPasswordPopup( "Delete Email Address", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				LoggerUtil.log( "Delete Domain" );
				MaterialLoader.loading( true, delEmailConfirmPass.getBodyPanel() );
				DeleteEmailModel deleteEmail = new DeleteEmailModel( emailModel.getEmail() );
				CommonServiceProvider.getCommonService().deleteEmail( deleteEmail, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, delEmailConfirmPass.getBodyPanel() );
						delEmailConfirmPass.close();
						MaterialToast.fireToast( deleteEmail.getEmail() + " Deleted..!", "rounded" );
						getUpdateIcommand().execute();
					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );

					}
				} );
			}
		} );
		delEmailConfirmPass.open();
	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<SMTPDestinationModel>();
		materialDataTable.addAttachHandler( new Handler()
		{

			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					materialDataTable.setHeight( "calc(42vh - 20px)" );
					// materialDataTable.setTitle("Snapshot");
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					smtpLable = uiComponentsUtil.getDataGridBlackHeaderLabel( "Destination Addresses" );
					materialDataTable.getScaffolding().getInfoPanel().add( smtpLable );
					totalEmailLable = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalEmail + ")" );
					materialDataTable.getScaffolding().getInfoPanel().add( totalEmailLable );
					materialDataTable.getScaffolding().getToolPanel().add( sendAlertUserBtn );
					materialDataTable.getScaffolding().getToolPanel().add( addEmailAddressBtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.setRowData( 0, smtpDestinationModel );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					materialDataTable.getColumnMenuIcon().setVisible( false );
				}
			}
		} );

		materialDataTable.addColumn( new TextColumn<SMTPDestinationModel>()
		{
			@Override
			public Comparator< ? super RowComponent<SMTPDestinationModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getEmail().toString().compareToIgnoreCase( o2.getData().getEmail().toString() );
			}

			@Override
			public String getValue( SMTPDestinationModel object )
			{
				return object.getEmail().toString();
			}
		}, "Email Address" );

		materialDataTable.addColumn( new TextColumn<SMTPDestinationModel>()
		{
			@Override
			public Comparator< ? super RowComponent<SMTPDestinationModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getLevel().toString().compareToIgnoreCase( o2.getData().getLevel().toString() );
			}

			@Override
			public String getValue( SMTPDestinationModel object )
			{
				return object.getLevel().toString();
			}
		}, "Alert Level" );

		materialDataTable.addColumn( new WidgetColumn<SMTPDestinationModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( SMTPDestinationModel object )
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
						deleteEmailBtnAction( object );

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
		List<SMTPDestinationModel> result = new ArrayList<SMTPDestinationModel>();
		totalEmail = result.size();
		createTable();
		materialRow.add( materialDataTable );
		add( materialRow );

	}

	public Icommand getUpdateIcommand()
	{
		Icommand icommand = new Icommand()
		{

			@Override
			public void execute()
			{
				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().getEmailAddress( null, new ApplicationCallBack<List<SMTPDestinationModel>>()
				{
					@Override
					public void onSuccess( List<SMTPDestinationModel> result )
					{
						MaterialLoader.loading( false );
						smtpDestinationModel.clear();
						smtpDestinationModel.addAll( result );
						materialDataTable.setRowData( 0, smtpDestinationModel );

						totalEmail = smtpDestinationModel.size();
						if ( totalEmailLable != null )
						{
							totalEmailLable.setText( "Total Items: " + "(" + totalEmail + ")" );
						}
						materialDataTable.getView().setRedraw( true );
						materialDataTable.getView().refresh();

					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );
					}
				} );

			}
		};
		return icommand;

	}
}
