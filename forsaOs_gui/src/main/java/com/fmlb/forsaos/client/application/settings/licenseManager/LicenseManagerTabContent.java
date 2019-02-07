package com.fmlb.forsaos.client.application.settings.licenseManager;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.ConfirmPasswordPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LicenseDetailsModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class LicenseManagerTabContent extends MaterialPanel
{

	private CurrentUser currentUser;

	private UIComponentsUtil uiComponentsUtil;

	private List<LicenseDetailsModel> licenseDetailsModelList = new ArrayList<>();

	private ConfirmPasswordPopup removeLicenseConfirmPasswordPopup;

	private IcommandWithData registerLicenseCommand;

	AppResources resources = GWT.create( AppResources.class );

	public LicenseManagerTabContent( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, IcommandWithData registerLicenseCommand )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.registerLicenseCommand = registerLicenseCommand;

		LoggerUtil.log( "License Data Table" );

	}

	private MaterialButton getRegisterLicenseBtn()
	{
		MaterialButton registerLicenseBtn = new MaterialButton();
		registerLicenseBtn.setTitle( "Register License" );
		registerLicenseBtn.setText( "Register License" );
		registerLicenseBtn.setFloat( Float.RIGHT );
		registerLicenseBtn.addStyleName( resources.style().upsRegisterBtn() );
		registerLicenseBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				event.stopPropagation();
				RegisterLicensePopup registerLicensePopup = new RegisterLicensePopup( "Register License", "Register", uiComponentsUtil, registerLicenseCommand );
				registerLicensePopup.open();

			}

		} );
		return registerLicenseBtn;
	}

	private void removeLicenseBtnAction( LicenseDetailsModel license )
	{

		ArrayList<String> warningMsgList = new ArrayList<String>();
		warningMsgList.add( "Do you want to remove the License " + license.getName() + "?" );
		removeLicenseConfirmPasswordPopup = new ConfirmPasswordPopup( "Remove License", warningMsgList, uiComponentsUtil, currentUser, new Icommand()
		{

			@Override
			public void execute()
			{
				LoggerUtil.log( "remove License" );
				MaterialLoader.loading( true, removeLicenseConfirmPasswordPopup.getBodyPanel() );
				CommonServiceProvider.getCommonService().deleteLicense( new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, removeLicenseConfirmPasswordPopup.getBodyPanel() );
						removeLicenseConfirmPasswordPopup.close();
						MaterialToast.fireToast( license.getName() + "License Removed..!", "rounded" );
						getUpdateIcommandWithData().executeWithData( true );
					}

					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false, removeLicenseConfirmPasswordPopup.getBodyPanel() );
						super.onFailureShowErrorPopup( caught, null );

					}
				} );

			}
		} );
		removeLicenseConfirmPasswordPopup.open();

	}

	private MaterialPanel drawLicenseInfoCollection( LicenseDetailsModel license )
	{
		MaterialPanel licenseDetailsPanel = new MaterialPanel();
		licenseDetailsPanel.addStyleName( "blinkPanel" );
		MaterialLabel upsDetailsLabel = this.uiComponentsUtil.getLabel( license.getName(), "s12", resources.style().statusHeader() );
		upsDetailsLabel.addStyleName( "upsHeaderPenal" );

		licenseDetailsPanel.add( upsDetailsLabel );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( resources.style().vm_setting_row() );

		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		collection1.addStyleName( "NVMEDetails" );

		MaterialCollectionItem collectionItem1 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Expiration Date", license.getExpiration_date() );

		MaterialCollectionItem collectionItem2 = this.uiComponentsUtil.getMaterialCollectionItem( "s12", "License Key", license.getLicense() );

		MaterialCollectionItem collectionItem3 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "License Type ", license.getType() );

		MaterialCollectionItem collectionItem4 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Serial Number", license.getSerial() );

		MaterialCollectionItem collectionItem5 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Company Name", license.getCompany_name() );

		MaterialCollectionItem collectionItem6 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Validation", license.getValidation() );

		MaterialCollectionItem collectionItem7 = this.uiComponentsUtil.getMaterialCollectionItem( "s6", "Activation", license.getActivation() );

		collection1.add( collectionItem3 );
		collection1.add( collectionItem4 );
		collection1.add( collectionItem1 );
		collection1.add( collectionItem5 );
		collection1.add( collectionItem6 );
		collection1.add( collectionItem7 );
		collection1.add( collectionItem2 );

		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s12" );
		column1.addStyleName( "schedulerBlinkRow" );
		column1.add( collection1 );

		MaterialButton upgradeLicenseBtn = new MaterialButton();
		upgradeLicenseBtn.setTitle( "Upgrade License" );
		upgradeLicenseBtn.setText( "Upgrade License" );
		upgradeLicenseBtn.setFloat( Float.RIGHT );
		upgradeLicenseBtn.addStyleName( resources.style().sendAlertBtn() );
		upgradeLicenseBtn.setMarginTop( 0 );
		upgradeLicenseBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				event.stopPropagation();
				RegisterLicensePopup registerLicensePopup = new RegisterLicensePopup( "Upgrade License", "Upgrade", uiComponentsUtil, registerLicenseCommand );
				registerLicensePopup.open();

			}

		} );

		MaterialButton deregisterLicenseBtn = new MaterialButton();
		deregisterLicenseBtn.setTitle( "Remove License" );
		deregisterLicenseBtn.addStyleName( resources.style().lem_delete_btn() );
		deregisterLicenseBtn.setFloat( Float.RIGHT );
		deregisterLicenseBtn.setIconType( IconType.DELETE );
		deregisterLicenseBtn.setIconPosition( IconPosition.RIGHT );
		deregisterLicenseBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				removeLicenseBtnAction( license );
			}
		} );
		firstRow.add( column1 );

		upsDetailsLabel.add( deregisterLicenseBtn );
		upsDetailsLabel.add( upgradeLicenseBtn );

		licenseDetailsPanel.add( firstRow );

		return licenseDetailsPanel;
	}

	public IcommandWithData getUpdateIcommandWithData()
	{

		IcommandWithData icommand = new IcommandWithData()
		{

			@Override
			public void executeWithData( Object data )
			{
				MaterialLoader.loading( true );
				clear();
				CommonServiceProvider.getCommonService().getLicense( new ApplicationCallBack<LicenseDetailsModel>()
				{
					@Override
					public void onSuccess( LicenseDetailsModel result )
					{
						MaterialLoader.loading( false );
						licenseDetailsModelList.clear();
						licenseDetailsModelList.add( result );
						clear();

						if ( result.getLicense() != "" )
						{

							add( drawLicenseInfoCollection( licenseDetailsModelList.get( 0 ) ) );

						}
						else
						{
							//add( getRegisterLicenseBtn( ) );
							MaterialPanel wrapperPanel = new MaterialPanel();
							wrapperPanel.setGrid( "s12" );
							wrapperPanel.add( getRegisterLicenseBtn() );
							wrapperPanel.setMargin( 10);

							MaterialLabel noLicenseLable = uiComponentsUtil.getLabel( "No License has been registered", "s12", "" );
							noLicenseLable.addStyleName( resources.style().emptyDataPenal() );
							wrapperPanel.add( noLicenseLable );
							add( wrapperPanel );
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
		};
		return icommand;
	}

}
