package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.SysInfoModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;

public class SystemSoftwareTabContent extends MaterialPanel
{
	private SysInfoModel sysInfoModel;

	private SystemSoftwareHostName systemhostname;

	private SystemSoftwareTimeZone systemTimeZone;

	AppResources resources = GWT.create( AppResources.class );

	public SystemSoftwareTabContent( UIComponentsUtil uiComponentsUtil )
	{
		systemhostname = new SystemSoftwareHostName( uiComponentsUtil, updateSysInfoCmd() );
		systemTimeZone = new SystemSoftwareTimeZone( uiComponentsUtil, updateSysInfoCmd() );
		add( systemhostname );
		add( systemTimeZone );
		getSysInfo();
	}

	private void getSysInfo()
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getSysInfoModel( new ApplicationCallBack<SysInfoModel>()
		{

			@Override
			public void onSuccess( SysInfoModel result )
			{
				MaterialLoader.loading( false );
				sysInfoModel = result;
				//Option option = new Option();
				//option.setValue( sysInfoModel.getTimezone() );
				//option.setText( sysInfoModel.getTimezone() );
				//option.setTitle( sysInfoModel.getTimezone() );
				//LoggerUtil.log( String.valueOf( systemTimeZone.getTimezoneModelsDropDown().getValueIndex( option ) ) );
				//systemTimeZone.getTimezoneModelsDropDown().setSelectedIndex( systemTimeZone.getTimezoneModelsDropDown().getValueIndex( option ) );
				systemhostname.getHostnameLabel().setText( "HostName : " + sysInfoModel.getHostname() );
				systemTimeZone.getTimeZoneLbl().setText( "Host Time Zone : " + sysInfoModel.getTimezone() );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );

			}
		} );
	}

	public IcommandWithData updateSysInfoCmd()
	{
		IcommandWithData icommand = new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				getSysInfo();

			}
		};
		return icommand;
	}

}
