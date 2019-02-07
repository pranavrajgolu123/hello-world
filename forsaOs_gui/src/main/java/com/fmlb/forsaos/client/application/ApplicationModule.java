package com.fmlb.forsaos.client.application;

import com.fmlb.forsaos.client.application.dashboard.DashboardModule;
import com.fmlb.forsaos.client.application.diagnostics.smtp.SMTPModule;
import com.fmlb.forsaos.client.application.diagnostics.status.DiagnosticsStatusModule;
import com.fmlb.forsaos.client.application.eventlog.EventLogModule;
import com.fmlb.forsaos.client.application.headermenu.HeaderMenuModule;
import com.fmlb.forsaos.client.application.home.HomeModule;
import com.fmlb.forsaos.client.application.lem.LEMModule;
import com.fmlb.forsaos.client.application.login.LoginModule;
import com.fmlb.forsaos.client.application.management.blink.BlinkModule;
import com.fmlb.forsaos.client.application.management.networking.NetworkingModule;
import com.fmlb.forsaos.client.application.management.partition.PartitionModule;
import com.fmlb.forsaos.client.application.management.repo.REPOModule;
import com.fmlb.forsaos.client.application.management.scheduler.SchedulerModule;
import com.fmlb.forsaos.client.application.rtm.RTMModule;
import com.fmlb.forsaos.client.application.settings.accounts.AccountsModule;
import com.fmlb.forsaos.client.application.settings.licenseManager.LicenseModule;
import com.fmlb.forsaos.client.application.settings.security.SecurityModule;
import com.fmlb.forsaos.client.application.settings.system.SystemModule;
import com.fmlb.forsaos.client.application.vm.VMModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule
{

	@Override
	protected void configure()
	{
		install( new DiagnosticsStatusModule() );
		install( new VMModule() );
		install( new LEMModule() );
		install( new REPOModule() );
		install( new PartitionModule() );
		install( new HeaderMenuModule() );
		install( new DashboardModule() );
		install( new EventLogModule() );
		install( new LoginModule() );
		install( new HomeModule() );
		install( new RTMModule() );
		install( new AccountsModule() );
		install( new SystemModule() );
		install( new SecurityModule() );
		install( new NetworkingModule() );
		install( new BlinkModule() );
		install( new SchedulerModule() );
		install( new SMTPModule() );
		install( new LicenseModule() );
		bindPresenter( ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class, ApplicationPresenter.MyProxy.class );
	}
}
