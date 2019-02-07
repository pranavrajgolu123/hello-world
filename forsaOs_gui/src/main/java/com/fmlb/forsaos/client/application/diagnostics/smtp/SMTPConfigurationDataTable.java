package com.fmlb.forsaos.client.application.diagnostics.smtp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.DeleteSMTPModel;
import com.fmlb.forsaos.client.application.models.SMTPConfigurationModel;
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
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class SMTPConfigurationDataTable extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	private List<SMTPConfigurationModel> smtpDestinationModel = new ArrayList<SMTPConfigurationModel>();

	private MaterialDataTable<SMTPConfigurationModel> materialDataTable;

	private MaterialLabel smtpConfigLable;

	private PlaceManager placeManager;

	private CurrentUser currentUser;

	private IcommandWithData navigationCmd;

	private MaterialButton configureSMTPbtn;

	private ConfirmPasswordPopup delSMTPConfirmPass;

	private int totalSMTPSevers;

	private MaterialLabel totalSMTPSeversLabel;

	AppResources resources = GWT.create( AppResources.class );

	public SMTPConfigurationDataTable( UIComponentsUtil uiComponentsUtil, PlaceManager placeManager, CurrentUser currentUser, IcommandWithData navigationCmd )
	{

		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		this.navigationCmd = navigationCmd;
		initializeDataTable();

	}

	private void initializeDataTable()
	{
		createNewSmtpConfig();
		generateData();
	}

	private void createNewSmtpConfig()
	{
		configureSMTPbtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Add SMTP Server" );
		configureSMTPbtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createSMTPConfigurationPopUp();
			}
		} );
	}

	private void createSMTPConfigurationPopUp()
	{
		CreateSMTPConfigurationPopUp addSMTPPopUp = new CreateSMTPConfigurationPopUp( uiComponentsUtil, getUpdateIcommand() );
		addSMTPPopUp.open();

	}

	private void deleteSMTPServerConfiguration( SMTPConfigurationModel smtpModel )
	{
		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to delete this SMTP server " + smtpModel.getName() + "?" );
		delSMTPConfirmPass = new ConfirmPasswordPopup( "Delete SMTP Server", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{
			@Override
			public void execute()
			{
				LoggerUtil.log( "Delete SMTP" );
				MaterialLoader.loading( true, delSMTPConfirmPass.getBodyPanel() );
				DeleteSMTPModel deleteSMTP = new DeleteSMTPModel( smtpModel.getName() );
				CommonServiceProvider.getCommonService().deleteSMTP( deleteSMTP, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, delSMTPConfirmPass.getBodyPanel() );
						delSMTPConfirmPass.close();
						MaterialToast.fireToast( deleteSMTP.getServer() + " Deleted..!", "rounded" );
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
		delSMTPConfirmPass.open();
	}

	private void sendTestEmail( SMTPConfigurationModel smtpModel )
	{
		SendTestEmailPopUp sendEmail = new SendTestEmailPopUp( smtpModel, uiComponentsUtil, currentUser );
		sendEmail.open();

	}

	private void createTable()
	{
		materialDataTable = new MaterialDataTable<SMTPConfigurationModel>();
		materialDataTable.addAttachHandler( new Handler()
		{

			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					materialDataTable.setHeight( "calc(20vh - 20px)" );
					materialDataTable.setTitle( "SMTP" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					smtpConfigLable = uiComponentsUtil.getDataGridBlackHeaderLabel( "SMTP Servers" );
					materialDataTable.getScaffolding().getInfoPanel().add( smtpConfigLable );
					totalSMTPSeversLabel = uiComponentsUtil.getDataGridHeaderLabel( "Total Items: " + "(" + totalSMTPSevers + ")" );
					materialDataTable.getScaffolding().getToolPanel().add( configureSMTPbtn );
					materialDataTable.getStretchIcon().setVisible( false );
					materialDataTable.setRowData( 0, smtpDestinationModel );
					materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
					materialDataTable.getColumnMenuIcon().setVisible( false );

				}
			}
		} );

		materialDataTable.addColumn( new TextColumn<SMTPConfigurationModel>()
		{
			@Override
			public Comparator< ? super RowComponent<SMTPConfigurationModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getName().toString().compareToIgnoreCase( o2.getData().getName().toString() );
			}

			@Override
			public String getValue( SMTPConfigurationModel object )
			{
				return object.getName().toString();
			}
		}, "Server" );

		materialDataTable.addColumn( new WidgetColumn<SMTPConfigurationModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( SMTPConfigurationModel object )
			{
				MaterialButton deleteBtn = new MaterialButton();
				deleteBtn.setTitle( "Test Email" );
				deleteBtn.setIconType( IconType.EMAIL );
				deleteBtn.addClickHandler( new ClickHandler()
				{

					@Override
					public void onClick( ClickEvent event )
					{
						event.stopPropagation();
						sendTestEmail( object );

					}
				} );

				return deleteBtn;
			}
		}, "Send Email" );

		materialDataTable.addColumn( new WidgetColumn<SMTPConfigurationModel, MaterialButton>()
		{
			@Override
			public MaterialButton getValue( SMTPConfigurationModel object )
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
						deleteSMTPServerConfiguration( object );

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
		List<SMTPConfigurationModel> result = new ArrayList<SMTPConfigurationModel>();
		totalSMTPSevers = result.size();
		createTable();
		materialRow.add( materialDataTable );
		add( materialRow );
		materialRow.addStyleName( resources.style().nameEditIcon_row() );

	}

	public Icommand getUpdateIcommand()
	{
		Icommand icommand = new Icommand()
		{

			@Override
			public void execute()
			{
				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().getSMTPname( null, new ApplicationCallBack<List<SMTPConfigurationModel>>()
				{
					@Override
					public void onSuccess( List<SMTPConfigurationModel> result )
					{

						MaterialLoader.loading( false );
						smtpDestinationModel.clear();
						smtpDestinationModel.addAll( result );
						materialDataTable.setRowData( 0, smtpDestinationModel );

						totalSMTPSevers = smtpDestinationModel.size();
						if ( totalSMTPSeversLabel != null )
						{
							totalSMTPSeversLabel.setText( "Total Items: " + "(" + totalSMTPSevers + ")" );
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
