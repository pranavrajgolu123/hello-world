package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialPanel;

public class SystemMiscTabContent extends MaterialPanel
{
	private LicenseFeatureDataTable licenseFeatureDataTable;

	private SystemMiscUploadSoftwarePack systemMiscUploadSoftwarePack;

	AppResources resources = GWT.create( AppResources.class );

	public SystemMiscTabContent( UIComponentsUtil uiComponentsUtil )
	{
		licenseFeatureDataTable = new LicenseFeatureDataTable( uiComponentsUtil );
		licenseFeatureDataTable.getUpdateIcommandWithData().executeWithData( false );
		systemMiscUploadSoftwarePack = new SystemMiscUploadSoftwarePack( uiComponentsUtil );
		add( licenseFeatureDataTable );
		add( systemMiscUploadSoftwarePack );
	}

}
