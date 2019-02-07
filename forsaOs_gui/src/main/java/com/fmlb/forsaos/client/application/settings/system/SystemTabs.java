package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Flex;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;

public class SystemTabs extends MaterialPanel
{

	private MaterialRow materialRow = new MaterialRow();

	public UIComponentsUtil uiComponentsUtil;

	private SystemSoftwareTabContent systemSoftwareTabContent;

	private SystemMiscTabContent systemMISC;

	AppResources resources = GWT.create( AppResources.class );

	public SystemTabs( UIComponentsUtil uiComponentsUtil )
	{
		this.uiComponentsUtil = uiComponentsUtil;

		systemSoftwareTabContent = new SystemSoftwareTabContent( this.uiComponentsUtil );
		systemSoftwareTabContent.setId( "Software" );

		systemMISC = new SystemMiscTabContent( this.uiComponentsUtil );
		systemMISC.setId( "MISC" );

		materialRow.add( systemSoftwareTabContent );
		// commented as feature is unavailable
		// materialRow.add( systemMISC );

		add( createTabPanel() );
		add( materialRow );
	}

	private MaterialTab createTabPanel()
	{
		MaterialTab settingsTab = new MaterialTab();
		settingsTab.setBackgroundColor( Color.WHITE );
		settingsTab.addStyleName( resources.style().system_tabs() );
		settingsTab.setShadow( 1 );
		settingsTab.setIndicatorColor( Color.TRANSPARENT );

		MaterialTabItem softwareTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		MaterialTabItem miscTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );

		MaterialLink softwareTabLink = uiComponentsUtil.getTabLink( "Software", "Software", Color.BLACK );

		MaterialLink miscTabLink = uiComponentsUtil.getTabLink( "MISC", "MISC", Color.BLACK );

		softwareTab.add( softwareTabLink );
		miscTab.add( miscTabLink );

		softwareTab.setBackgroundColor( Color.BLUE );// by default selected
		softwareTabLink.setTextColor( Color.WHITE );

		softwareTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{

				softwareTab.setBackgroundColor( Color.BLUE );
				softwareTabLink.setTextColor( Color.WHITE );

				miscTab.setBackgroundColor( Color.WHITE );
				miscTabLink.setTextColor( Color.BLACK );

			}
		} );

		miscTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{

				softwareTab.setBackgroundColor( Color.WHITE );
				softwareTabLink.setTextColor( Color.BLACK );

				miscTab.setBackgroundColor( Color.BLUE );
				miscTabLink.setTextColor( Color.WHITE );

			}
		} );

		settingsTab.add( softwareTab );
		// commented as feature is unavailable
		//settingsTab.add( miscTab );

		return settingsTab;
	}

	public void updateAllTabData()
	{
		systemSoftwareTabContent.updateSysInfoCmd().executeWithData( false );

	}
}