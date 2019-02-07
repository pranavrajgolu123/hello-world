package com.fmlb.forsaos.client.application.home;

import java.util.HashMap;

import javax.inject.Inject;

import com.fmlb.forsaos.client.application.common.NavigationLinkModel;
import com.fmlb.forsaos.client.place.NameTokens;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialPreLoader;

public class HomeView extends ViewWithUiHandlers<HomeUiHandlers> implements HomePresenter.MyView
{
	interface Binder extends UiBinder<Widget, HomeView>
	{
	}

	@UiField
	MaterialNavBrand appTitle;

	@UiField
	MaterialContainer pageContainer;

	@UiField
	MaterialLink dashboardLink;

	@UiField
	MaterialLink rtmLink;

	@UiField
	MaterialLink managemetBlinkLink;

	@UiField
	MaterialLink lemLink;

	@UiField
	MaterialLink vmLink;

	@UiField
	MaterialLink repoLink;

	@UiField
	MaterialLink schedulerLink;

	@UiField
	MaterialLink logoutLink;

	@UiField
	MaterialLink diagStatusLink;

	/*@UiField
	MaterialLink diagMACAddrsLink;*/

	/*@UiField
	MaterialLink diagSMTPLink;*/

	@UiField
	MaterialLink diagSMTPLink;

	@UiField
	MaterialLink manageLink;

	@UiField
	MaterialLink virtLink;

	@UiField
	MaterialLink diagLink;

	/*@UiField
	MaterialLink reportingLink;*/

	@UiField
	MaterialLink eventLogLink;

	@UiField
	MaterialLink settingsLink;

	@UiField
	MaterialLink settingsAccountsLink;

	@UiField
	MaterialLink settingsSystemLink;

	@UiField
	MaterialLink settingsLicenseManagerLink;

	/*@UiField
	MaterialLink settingsSecurityLink;*/

	@UiField
	MaterialLink shutdownForsaOsLink;

	@UiField
	MaterialLink restartForsaOsLink;

	@UiField
	MaterialLink shutdownSystemLink;

	@UiField
	MaterialLink restartSystemLink;

	@UiField
	MaterialLink networkingLink;

	@UiField
	MaterialLink partitionLink;

	@UiField
	MaterialLabel sysUpTime;

	@UiField
	MaterialLabel serverTime;

	@UiField
	MaterialPreLoader blinkStatusLoader;

	@UiField
	MaterialLink userNameTitle;

	private HashMap<String, NavigationLinkModel> nameTokenToNavigationLinkMap = new HashMap<>();

	public HashMap<String, NavigationLinkModel> getNameTokenToNavigationLinkMap()
	{
		return nameTokenToNavigationLinkMap;
	}

	public MaterialContainer getPageContainer()
	{
		return pageContainer;
	}

	public MaterialNavBrand getAppTitle()
	{
		return appTitle;
	}

	public MaterialLink getDashboardLink()
	{
		return dashboardLink;
	}

	public MaterialLink getManagemetBlinkLink()
	{
		return managemetBlinkLink;
	}

	public MaterialLink getLemLink()
	{
		return lemLink;
	}

	public MaterialLink getLink()
	{
		return lemLink;
	}

	public MaterialLink getVirtLink()
	{
		return virtLink;
	}

	public MaterialLink getRtmLink()
	{
		return rtmLink;
	}

	public MaterialLink getVmLink()
	{
		return vmLink;
	}

	public MaterialLink getRepoLink()
	{
		return repoLink;
	}

	public MaterialLink getSchedulerLink()
	{
		return schedulerLink;
	}

	public MaterialLink getManageLink()
	{
		return manageLink;
	}

	public MaterialLink getDiagLink()
	{
		return diagLink;
	}

	public MaterialLink getDiagSMTPLink()
	{
		return diagSMTPLink;
	}

	/*public MaterialLink getReportingLink()
	{
		return reportingLink;
	}*/

	public MaterialLink getEventLogLink()
	{
		return eventLogLink;
	}

	public MaterialLink getSettingsLink()
	{
		return settingsLink;
	}

	public MaterialLink getSettingsAccountsLink()
	{
		return settingsAccountsLink;
	}

	public MaterialLink getSettingsSystemLink()
	{
		return settingsSystemLink;
	}

	public MaterialPreLoader getBlinkStatusLoader()
	{
		return blinkStatusLoader;
	}

	/*
		public MaterialLink getSettingsSecurityLink() {
			return settingsSecurityLink;
		}
	*/

	public MaterialLink getSettingsLicenseManagerLink()
	{
		return settingsLicenseManagerLink;
	}

	public MaterialLink getNetworkingLink()
	{
		return networkingLink;
	}

	public MaterialLink getPartitionLink()
	{
		return partitionLink;
	}

	public MaterialLink getUserNameTitle()
	{
		return userNameTitle;
	}

	@Inject
	HomeView( Binder uiBinder )
	{
		initWidget( uiBinder.createAndBindUi( this ) );
		pageContainer.clear();
		bindSlot( HomePresenter.SLOT_HOME_CONTENT, pageContainer );
		bind();
	}

	@Override
	public void setInSlot( Object slot, IsWidget content )
	{
		super.setInSlot( slot, content );
		/*
		 * if (slot == HomePresenter.SLOT_HOME_CONTENT) { pageContainer.clear();
		 * pageContainer.add(content); } else { super.setInSlot(slot, content); }
		 */
	}

	private void bind()
	{
		populateNameTokenToLinkMap();
		dashboardLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToDashboard();

			}
		} );

		repoLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToRepo();

			}
		} );

		rtmLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToRTM();
			}
		} );

		schedulerLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToScheduler();

			}
		} );

		managemetBlinkLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToManagementBlink();

			}
		} );

		lemLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToLEM();

			}
		} );
		vmLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToVM();

			}
		} );

		diagStatusLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToDiagnosticsStatus();
			}
		} );

		/*diagMACAddrsLink.addClickHandler( new ClickHandler()
		{
		
			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToDiagnosticsMacAddresses();
			}
		} );*/

		diagSMTPLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToDiagnosticsSMTP();
			}
		} );

		settingsAccountsLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToSettingsAccounts();
			}
		} );

		settingsSystemLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToSettingsSystem();
			}
		} );

		settingsLicenseManagerLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToLicenseManager();
			}
		} );

		/*settingsSecurityLink.addClickHandler( new ClickHandler()
		{
		
			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToSettingsSecurity();
			}
		} );*/

		logoutLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().logout();

			}
		} );

		shutdownForsaOsLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().shutdownForsaOs();

			}
		} );
		restartForsaOsLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().restartForsaOs();

			}
		} );
		shutdownSystemLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().shutdownSystem();

			}
		} );
		restartSystemLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().restartSystem();
			}
		} );

		networkingLink.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToNetworking();

			}
		} );

		partitionLink.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToPartition();
			}
		} );

		eventLogLink.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				getUiHandlers().navigateToEventLog();
			}
		} );
	}

	private void populateNameTokenToLinkMap()
	{

		NavigationLinkModel dashBoardlinkModel = new NavigationLinkModel( getDashboardLink(), null );
		NavigationLinkModel eventLogLinkModel = new NavigationLinkModel( getEventLogLink(), null );
		NavigationLinkModel blinkLinkModel = new NavigationLinkModel( getManageLink(), getManagemetBlinkLink() );
		NavigationLinkModel scheduleLinkModel = new NavigationLinkModel( getSchedulerLink(), getManageLink() );
		NavigationLinkModel repoLinkModel = new NavigationLinkModel( getManageLink(), getRepoLink() );
		NavigationLinkModel partitionLinkModel = new NavigationLinkModel( getManageLink(), getPartitionLink() );
		NavigationLinkModel rtmLinkModel = new NavigationLinkModel( getRtmLink(), getVirtLink() );
		NavigationLinkModel lemLinkModel = new NavigationLinkModel( getLemLink(), getVirtLink() );
		NavigationLinkModel vmLinkModel = new NavigationLinkModel( getVmLink(), getVirtLink() );
		NavigationLinkModel SettingsSystemLinkModel = new NavigationLinkModel( getSettingsSystemLink(), getSettingsLink() );
		NavigationLinkModel SettingsAccountsLinkModel = new NavigationLinkModel( getSettingsAccountsLink(), getSettingsLink() );
		NavigationLinkModel SettingsLicenseManagerLinkModel = new NavigationLinkModel( getSettingsSystemLink(), getSettingsLink() );
		nameTokenToNavigationLinkMap.put( NameTokens.DASHBOARD, dashBoardlinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.EVENTLOG, eventLogLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.BLINK, blinkLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.SCHEDULER, scheduleLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.REPO, repoLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.PARTITION, partitionLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.RTM, rtmLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.LEM, lemLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.VM, vmLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.SYSTEM, SettingsSystemLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.ACCOUNTS, SettingsAccountsLinkModel );
		nameTokenToNavigationLinkMap.put( NameTokens.LICENSEMANAGER, SettingsLicenseManagerLinkModel );

	}

	public MaterialLabel getSysUpTime()
	{
		return sysUpTime;
	}

	public void setSysUpTime( MaterialLabel sysUpTime )
	{
		this.sysUpTime = sysUpTime;
	}

	public MaterialLabel getServerTime()
	{
		return serverTime;
	}

	public void setServerTime( MaterialLabel serverTime )
	{
		this.serverTime = serverTime;
	}
}
