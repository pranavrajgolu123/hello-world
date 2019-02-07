package com.fmlb.forsaos.client.application.settings.security;

import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Flex;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;

public class SecurityTabs extends MaterialPanel
{

	//private UIComponentsUtil uiComponentsUtil;

	private MaterialRow materialRow = new MaterialRow();

	public UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	private Icommand navigateCmd;

	private PlaceManager placeManager;

	private SecuritySSLCertificate securitySSLCertificate;

	private SecurityPasswordReq securityPasswordReq;

	private SecurityMisc securityMisc;

	public SecurityTabs( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, Icommand navigateCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigateCmd = navigateCmd;

		add( createTabPanel() );

		securitySSLCertificate = new SecuritySSLCertificate( this.uiComponentsUtil, this.placeManager, this.currentUser );
		securitySSLCertificate.setId( "SSLCerificates" );
		securityPasswordReq = new SecurityPasswordReq( this.uiComponentsUtil, this.placeManager, this.currentUser );
		securityPasswordReq.setId( "PasswordRequirement" );
		securityMisc = new SecurityMisc( this.uiComponentsUtil, this.placeManager, this.currentUser );
		securityMisc.setId( "MISC" );
		//tabContentRow = new MaterialRow();
		materialRow.add( securitySSLCertificate );
		materialRow.add( securityPasswordReq );
		materialRow.add( securityMisc );
		add( materialRow );

		//setWidgetToTabContentArea( "" );
	}

	AppResources resources = GWT.create( AppResources.class );

	private MaterialTab createTabPanel()
	{
		MaterialTab securityTab = new MaterialTab();
		securityTab.setBackgroundColor( Color.WHITE );
		securityTab.addStyleName( resources.style().vm_tabs() );
		securityTab.setShadow( 1 );
		securityTab.setIndicatorColor( Color.TRANSPARENT );

		MaterialTabItem sslTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		MaterialTabItem passwordReqTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		passwordReqTab.setWidth( "6" );
		MaterialTabItem miscTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );

		MaterialLink sslTabLink = uiComponentsUtil.getTabLink( "SSL Cerificates", "SSLCerificates", Color.BLACK );
		MaterialLink passwordReqTabLink = uiComponentsUtil.getTabLink( "Password Requirement", "PasswordRequirement", Color.BLACK );
		MaterialLink miscTabLink = uiComponentsUtil.getTabLink( "MISC", "MISC", Color.BLACK );

		sslTab.add( sslTabLink );
		passwordReqTab.add( passwordReqTabLink );
		miscTab.add( miscTabLink );

		// securityTab.setWaves( WavesType.fromStyleName(
		// resources.style().vm_tab_waves() ) );
		sslTab.setBackgroundColor( Color.BLUE );// by default selected
		sslTabLink.setTextColor( Color.WHITE );
		sslTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				sslTab.setBackgroundColor( Color.BLUE );
				sslTabLink.setTextColor( Color.WHITE );

				passwordReqTab.setBackgroundColor( Color.WHITE );
				passwordReqTabLink.setTextColor( Color.BLACK );

				miscTab.setBackgroundColor( Color.WHITE );
				miscTabLink.setTextColor( Color.BLACK );

			}
		} );

		passwordReqTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				sslTab.setBackgroundColor( Color.WHITE );
				sslTabLink.setTextColor( Color.BLACK );

				passwordReqTab.setBackgroundColor( Color.BLUE );
				passwordReqTabLink.setTextColor( Color.WHITE );

				miscTab.setBackgroundColor( Color.WHITE );
				miscTabLink.setTextColor( Color.BLACK );

			}
		} );

		miscTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				sslTab.setBackgroundColor( Color.WHITE );
				sslTabLink.setTextColor( Color.BLACK );

				passwordReqTab.setBackgroundColor( Color.WHITE );
				passwordReqTabLink.setTextColor( Color.BLACK );

				miscTab.setBackgroundColor( Color.BLUE );
				miscTabLink.setTextColor( Color.WHITE );

			}
		} );

		securityTab.add( sslTab );
		securityTab.add( passwordReqTab );
		securityTab.add( miscTab );

		return securityTab;
	}

	public void updateAllTabData()
	{

	}
}