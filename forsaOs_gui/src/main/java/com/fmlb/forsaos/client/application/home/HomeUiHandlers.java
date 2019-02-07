package com.fmlb.forsaos.client.application.home;

import com.gwtplatform.mvp.client.UiHandlers;

interface HomeUiHandlers extends UiHandlers
{
	void navigateToDashboard();

	void navigateToRepo();

	void navigateToScheduler();

	void navigateToLEM();

	void navigateToVM();

	void logout();

	void navigateToRTM();

	void navigateToManagementBlink();

	void navigateToDiagnosticsStatus();

	void navigateToDiagnosticsSMTP();

	void navigateToDiagnosticsMacAddresses();

	void shutdownForsaOs();

	void restartForsaOs();

	void shutdownSystem();

	void restartSystem();

	void navigateToSettingsAccounts();

	void navigateToSettingsSystem();

	void navigateToSettingsSecurity();

	void navigateToLicenseManager();

	void navigateToNetworking();

	void navigateToEventLog();

	void navigateToPartition();

}