package com.fmlb.forsaos.client.application.diagnostics.status;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.DiagSystemHealthModel;
import com.fmlb.forsaos.client.application.models.DiagSystemStateModel;
import com.fmlb.forsaos.client.application.models.SysInfoModel;
import com.fmlb.forsaos.client.application.models.SystemStatusChartModel;
import com.fmlb.forsaos.client.application.models.UPSDetailsModel;
import com.fmlb.forsaos.client.application.models.UPSModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.application.websocket.WebsocketHelper;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.exceptions.WebsocketEndpoints;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.sksamuel.gwt.websockets.Websocket;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Flex;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;

public class DiagnosticsStatusTabs extends MaterialPanel
{

	private AppResources resources = GWT.create( AppResources.class );

	private UIComponentsUtil uiComponentsUtil;

	private DiagStatusTabContent diagnosticStatusSytemTab;

	private DiagNVMETabContent diagNVMETabContent;

	private DiagNetworkTabContent diagNetworkTabContent;

	private DiagUPSTabContent diagUPSTabContent;

	private MaterialRow tabContentRow;

	private CurrentUser currentUser;

	private MaterialTab diagStatusTab;

	private MaterialTabItem statusTab;

	private MaterialTabItem nvmeTab;

	private MaterialTabItem networkTab;

	private MaterialTabItem upsTab;

	private MaterialLink statusTabLink;

	private MaterialLink nvmeTabLink;

	private MaterialLink networkTabLink;

	private MaterialLink upsTabLink;

	private Websocket diagStatusTabWebsocket;

	private Websocket diagUPSTabWebsocket;

	DiagnosticsStatusTabs( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		add( createTabPanel() );

		diagnosticStatusSytemTab = new DiagStatusTabContent( uiComponentsUtil );
		diagnosticStatusSytemTab.setId( "Status" );

		diagNVMETabContent = new DiagNVMETabContent( uiComponentsUtil );
		diagNVMETabContent.setId( "NVME" );

		diagNetworkTabContent = new DiagNetworkTabContent( uiComponentsUtil );
		diagNetworkTabContent.setId( "Network" );

		diagUPSTabContent = new DiagUPSTabContent( uiComponentsUtil, this.currentUser );
		diagUPSTabContent.setId( "UPS" );

		tabContentRow = new MaterialRow();
		tabContentRow.add( diagnosticStatusSytemTab );
		tabContentRow.add( diagNVMETabContent );
		tabContentRow.add( diagNetworkTabContent );
		tabContentRow.add( diagUPSTabContent );
		add( tabContentRow );
	}

	private MaterialTab createTabPanel()
	{
		diagStatusTab = new MaterialTab();
		diagStatusTab.setBackgroundColor( Color.WHITE );
		diagStatusTab.addStyleName( resources.style().system_tabs() );
		diagStatusTab.setShadow( 1 );
		diagStatusTab.setIndicatorColor( Color.TRANSPARENT );

		statusTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		nvmeTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		networkTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		upsTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );

		statusTabLink = uiComponentsUtil.getTabLink( "Status", "Status", Color.BLACK );
		nvmeTabLink = uiComponentsUtil.getTabLink( "NVME", "NVME", Color.BLACK );
		networkTabLink = uiComponentsUtil.getTabLink( "Network", "Network", Color.BLACK );
		upsTabLink = uiComponentsUtil.getTabLink( "UPS", "UPS", Color.BLACK );

		statusTab.add( statusTabLink );
		nvmeTab.add( nvmeTabLink );
		networkTab.add( networkTabLink );
		upsTab.add( upsTabLink );

		// settingsTab.setWaves( WavesType.fromStyleName(
		// resources.style().vm_tab_waves() ) );
		statusTab.setBackgroundColor( Color.BLUE );// by default selected
		statusTabLink.setTextColor( Color.WHITE );
		statusTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				statusTab.setBackgroundColor( Color.BLUE );
				statusTabLink.setTextColor( Color.WHITE );

				nvmeTab.setBackgroundColor( Color.WHITE );
				nvmeTabLink.setTextColor( Color.BLACK );

				upsTab.setBackgroundColor( Color.WHITE );
				upsTabLink.setTextColor( Color.BLACK );

				networkTab.setBackgroundColor( Color.WHITE );
				networkTabLink.setTextColor( Color.BLACK );

			}
		} );

		nvmeTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				nvmeTab.setBackgroundColor( Color.BLUE );
				nvmeTabLink.setTextColor( Color.WHITE );

				statusTab.setBackgroundColor( Color.WHITE );
				statusTabLink.setTextColor( Color.BLACK );

				upsTab.setBackgroundColor( Color.WHITE );
				upsTabLink.setTextColor( Color.BLACK );

				networkTab.setBackgroundColor( Color.WHITE );
				networkTabLink.setTextColor( Color.BLACK );
			}
		} );

		networkTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				networkTab.setBackgroundColor( Color.BLUE );
				networkTabLink.setTextColor( Color.WHITE );

				statusTab.setBackgroundColor( Color.WHITE );
				statusTabLink.setTextColor( Color.BLACK );

				upsTab.setBackgroundColor( Color.WHITE );
				upsTabLink.setTextColor( Color.BLACK );

				nvmeTab.setBackgroundColor( Color.WHITE );
				nvmeTabLink.setTextColor( Color.BLACK );
			}
		} );
		upsTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				upsTab.setBackgroundColor( Color.BLUE );
				upsTabLink.setTextColor( Color.WHITE );

				statusTab.setBackgroundColor( Color.WHITE );
				statusTabLink.setTextColor( Color.BLACK );

				nvmeTab.setBackgroundColor( Color.WHITE );
				nvmeTabLink.setTextColor( Color.BLACK );

				networkTab.setBackgroundColor( Color.WHITE );
				networkTabLink.setTextColor( Color.BLACK );
			}
		} );

		diagStatusTab.add( statusTab );
		diagStatusTab.add( nvmeTab );
		diagStatusTab.add( networkTab );
		diagStatusTab.add( upsTab );

		return diagStatusTab;
	}

	public void updateAllTabData()
	{
		MaterialLoader.loading( true );
		CommonServiceProvider.getCommonService().getDiagSystemStateModel( null, new ApplicationCallBack<DiagSystemStateModel>()
		{

			@Override
			public void onSuccess( DiagSystemStateModel result )
			{
				MaterialLoader.loading( false );
				diagnosticStatusSytemTab.updateData( result.getSystemHealthModel() );
				diagNVMETabContent.updateData( result.getNvmeList() );
				diagNetworkTabContent.updateData( result.getDiagNetworkModel() );
				diagUPSTabContent.updateData();
				initializeDiagStatusWS();
				initializeDiagUPSWS();
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );

			}
		} );
	}

	public void selectUPSTab()
	{
		diagStatusTab.setTabIndex( 3 );

		upsTab.setBackgroundColor( Color.BLUE );
		upsTabLink.setTextColor( Color.WHITE );

		statusTab.setBackgroundColor( Color.WHITE );
		statusTabLink.setTextColor( Color.BLACK );

		nvmeTab.setBackgroundColor( Color.WHITE );
		nvmeTabLink.setTextColor( Color.BLACK );

		networkTab.setBackgroundColor( Color.WHITE );
		networkTabLink.setTextColor( Color.BLACK );
	}

	private void initializeDiagStatusWS()
	{
		LoggerUtil.log( "TRYING TO Initializing WEB SOCKET" );
		if ( diagStatusTabWebsocket == null )
		{
			LoggerUtil.log( "Initializing WEB SOCKET AS IT IS NULL" );
			diagStatusTabWebsocket = WebsocketHelper.getWebsocketInstance( WebsocketEndpoints.DIAGNOSTIC_END_POINT, new IcommandWithData()
			{
				@Override
				public void executeWithData( Object obj )
				{
					updateDiagnosticTabOnDataReceived( ( JSONValue ) obj );
				}
			} );
		}
		else if ( diagStatusTabWebsocket.getState() == 3 )
		{
			LoggerUtil.log( "Reopening the WEB SOCKET AS IT was closed" );
			diagStatusTabWebsocket.open();
		}
	}

	private void initializeDiagUPSWS()
	{
		LoggerUtil.log( "TRYING TO Initializing diagUPSTabWebsocket WEB SOCKET" );
		if ( diagUPSTabWebsocket == null )
		{
			LoggerUtil.log( "Initializing diagUPSTabWebsocket WEB SOCKET AS IT IS NULL" );
			diagUPSTabWebsocket = WebsocketHelper.getWebsocketInstance( WebsocketEndpoints.UPS_END_POINT, new IcommandWithData()
			{
				@Override
				public void executeWithData( Object obj )
				{
					updateUPSTabOnDataReceived( ( JSONValue ) obj );
				}
			} );
		}
		else if ( diagUPSTabWebsocket.getState() == 3 )
		{
			LoggerUtil.log( "Reopening the diagUPSTabWebsocket  WEB SOCKET AS IT was closed" );
			diagUPSTabWebsocket.open();
		}
	}

	private void updateUPSTabOnDataReceived( JSONValue parsedData )
	{
		LoggerUtil.log( "UPS 1" );
		JSONObject upsDataObj = parsedData.isObject();
		LoggerUtil.log( "UPS 2" );
		JSONArray upsDataArray = upsDataObj.get( "ups" ).isArray();
		LoggerUtil.log( "UPS 3" );
		List<UPSModel> upsModelList = new ArrayList<>();
		List<UPSDetailsModel> upsDetailsModelList = new ArrayList<>();
		if ( upsDataArray != null )
		{
			LoggerUtil.log( "UPS 4" );
			for ( int i = 0; i < upsDataArray.size(); i++ )
			{
				LoggerUtil.log( "UPS 5" );
				JSONObject upsData = upsDataArray.get( i ).isObject();
				LoggerUtil.log( "UPS 6" );
				UPSModel upsModel = new UPSModel();
				upsModel.setName( RestCallUtil.getRawString( upsData, "name" ) );
				LoggerUtil.log( "UPS 7" );
				UPSDetailsModel upsDetailsModel = new UPSDetailsModel();
				LoggerUtil.log( "UPS 8" );
				upsDetailsModel.setTemp( ( int ) RestCallUtil.getRawDouble( upsData, "temp" ) );
				LoggerUtil.log( "UPS 9" );
				upsDetailsModel.setOutput_power( ( long ) RestCallUtil.getRawDouble( upsData, "output_power" ) );
				LoggerUtil.log( "UPS 10" );
				upsDetailsModel.setRuntime_remaining( ( long ) RestCallUtil.getRawDouble( upsData, "runtime_remaining" ) );
				LoggerUtil.log( "UPS 11" );
				upsDetailsModel.setState( RestCallUtil.getRawString( upsData, "state" ) );
				LoggerUtil.log( "UPS 12" );
				upsDetailsModel.setBattery_charge( ( int ) RestCallUtil.getRawDouble( upsData, "battery_charge" ) );
				LoggerUtil.log( "UPS 13" );
				upsDetailsModel.setBattery_life( RestCallUtil.getRawString( upsData, "battery_life" ) );
				LoggerUtil.log( "UPS 14" );
				upsDetailsModel.setMessage( RestCallUtil.getRawString( upsData, "message" ) );
				LoggerUtil.log( "UPS 15" );
				upsDetailsModel.setBattery_need_replacement( RestCallUtil.getRawBoolean( upsData, "battery_need_replacement" ) );
				LoggerUtil.log( "UPS 16" );
				upsDetailsModel.setStatus_modifier( RestCallUtil.getRawString( upsData, "status_modifier" ) );
				LoggerUtil.log( "UPS 17" );
				upsModelList.add( upsModel );
				LoggerUtil.log( "UPS 18" );
				upsDetailsModelList.add( upsDetailsModel );
				LoggerUtil.log( "UPS 19" );

			}
			LoggerUtil.log( "UPS 20" );
			diagUPSTabContent.updateDynamicData( upsModelList, upsDetailsModelList );
			LoggerUtil.log( "UPS 21" );
		}
	}

	private void updateDiagnosticTabOnDataReceived( JSONValue parsedData )
	{
		JSONObject object = parsedData.isObject();
		JSONObject bmcObject = object.get( "bmc" ).isObject();
		DiagSystemHealthModel diagSystemHealthModel = new DiagSystemHealthModel();
		LoggerUtil.log( "Start populating diagSystemHealthModel model 1" );
		diagSystemHealthModel.setCPU1_temperature_current( RestCallUtil.getRawString( bmcObject, "CPU1_temperature_current" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 2" );
		diagSystemHealthModel.setCPU1_temperature_max( RestCallUtil.getRawString( bmcObject, "CPU1_temperature_max" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 3" );
		diagSystemHealthModel.setCPU2_temperature_current( RestCallUtil.getRawString( bmcObject, "CPU2_temperature_current" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 4" );
		diagSystemHealthModel.setCPU2_temperature_max( RestCallUtil.getRawString( bmcObject, "CPU2_temperature_max" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 5" );
		diagSystemHealthModel.setFans_fan1_speed( RestCallUtil.getRawString( bmcObject, "Fans_fan1_speed" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 6" );
		diagSystemHealthModel.setFans_fan2_speed( RestCallUtil.getRawString( bmcObject, "Fans_fan2_speed" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 7" );
		diagSystemHealthModel.setFans_fan3_speed( RestCallUtil.getRawString( bmcObject, "Fans_fan3_speed" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 8" );
		diagSystemHealthModel.setFans_fan4_speed( RestCallUtil.getRawString( bmcObject, "Fans_fan4_speed" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 9" );
		diagSystemHealthModel.setFans_fan5_speed( RestCallUtil.getRawString( bmcObject, "Fans_fan5_speed" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 10" );
		diagSystemHealthModel.setFans_fan6_speed( RestCallUtil.getRawString( bmcObject, "Fans_fan6_speed" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 11" );
		diagSystemHealthModel.setFans_fan7_speed( RestCallUtil.getRawString( bmcObject, "Fans_fan7_speed" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 12" );
		diagSystemHealthModel.setFans_fan8_speed( RestCallUtil.getRawString( bmcObject, "Fans_fan8_speed" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 13" );
		diagSystemHealthModel.setMemory_temperature_current( RestCallUtil.getRawString( bmcObject, "Memory_temperature_current" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 14" );
		diagSystemHealthModel.setPowerSupply_fan_speed_minimal( RestCallUtil.getRawString( bmcObject, "PowerSupply_fan_speed_minimal" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 15" );
		diagSystemHealthModel.setPowerSupply_temperature_maximal( RestCallUtil.getRawString( bmcObject, "PowerSupply_temperature_maximal" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 16" );
		diagSystemHealthModel.setVoltage_12V( RestCallUtil.getRawString( bmcObject, "Voltage_12V" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 17" );
		diagSystemHealthModel.setVoltage_3_3V( RestCallUtil.getRawString( bmcObject, "Voltage_3_3V" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 18" );
		diagSystemHealthModel.setVoltage_5V( RestCallUtil.getRawString( bmcObject, "Voltage_5V" ) );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 19" );

		double cpuUsage = 0;
		double memUsage = 0;
		if ( RestCallUtil.getRawString( bmcObject, "cpu_overall" ) != null )
		{
			cpuUsage = Double.valueOf( RestCallUtil.getRawString( bmcObject, "cpu_overall" ) );
		}
		if ( RestCallUtil.getRawString( bmcObject, "memory" ) != null )
		{
			memUsage = Double.valueOf( RestCallUtil.getRawString( bmcObject, "memory" ) );
		}

		SystemStatusChartModel systemStatusChartModel = new SystemStatusChartModel();
		systemStatusChartModel.setCpuUsage( cpuUsage );
		systemStatusChartModel.setMemUsage( memUsage );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 20" );
		SysInfoModel sysInfoModel = new SysInfoModel();
		diagSystemHealthModel.setSysInfoModel( sysInfoModel );
		diagSystemHealthModel.setSystemStatusChartModel( systemStatusChartModel );
		diagnosticStatusSytemTab.updateDynamicData( diagSystemHealthModel );
		LoggerUtil.log( "Start populating diagSystemHealthModel model 21" );
	}

	public Websocket getDiagStatusTabWebsocket()
	{
		return diagStatusTabWebsocket;
	}

	public Websocket getDiagUPSTabWebsocket()
	{
		return diagUPSTabWebsocket;
	}

}
