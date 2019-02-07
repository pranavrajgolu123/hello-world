package com.fmlb.forsaos.client.application.dashboard;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CapacityChartModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.SystemStatusChartModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.application.websocket.WebsocketHelper;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.shared.application.exceptions.WebsocketEndpoints;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Timer;
import com.sksamuel.gwt.websockets.Websocket;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class DashboardCharts extends MaterialPanel
{

	AppResources appResources = GWT.create( AppResources.class );

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	private JavaScriptObject capacityChart = null;

	private JavaScriptObject usageChart = null;

	private JavaScriptObject cpuUsageChart = null;

	private JavaScriptObject memUsageChart = null;

	private JavaScriptObject coreTempChart = null;

	private JavaScriptObject performanceChart = null;

	private Timer timer;

	private MaterialLabel physicalValue;

	private MaterialLabel usableValue;

	private MaterialLabel cpuLabel = null;

	private MaterialLabel memoryLabel = null;

	private MaterialLabel coreTempLabel = null;

	private boolean capacityChartImpl = false;

	private boolean usageChartImpl = false;

	private boolean cpuUsageChartImpl = false;

	private boolean memUsageChartImpl = false;

	private boolean coreTempChartImpl = false;

	private boolean performanceChartImpl = false;

	private Websocket dashboardChartsbWebsocket;

	public DashboardCharts( UIComponentsUtil uiComponentsUtil, CurrentUser currentUser )
	{
		this.currentUser = currentUser;
		/* setBackgroundColor( Color.WHITE ); */
		this.uiComponentsUtil = uiComponentsUtil;
		cpuLabel = this.uiComponentsUtil.getLabel( "CPU", "" );
		memoryLabel = this.uiComponentsUtil.getLabel( "Memory", "" );
		coreTempLabel = this.uiComponentsUtil.getLabel( "Core Temperature", "" );
		MaterialRow row = new MaterialRow();
		row.setGrid( "s12" );

		MaterialPanel capacityChartConatianer = new MaterialPanel();
		//		capacityChartConatianer.setGrid( "s4" );
		MaterialPanel capacityChartPanel = new MaterialPanel();
		capacityChartPanel.setGrid( "s12" );
		capacityChartPanel.setId( "capacityChartPanel" );
		capacityChartPanel.addStyleName( appResources.style().capacityChartPanel() );
		capacityChartConatianer.add( capacityChartPanel );
		capacityChartPanel.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				capacityChart = CapacityChart.drawCapacityChart( capacityChartPanel.getId() );
			}
		} );

		MaterialLabel physicalLabel = uiComponentsUtil.getLabel( "Physical", "s3" );
		physicalLabel.addStyleName( appResources.style().dashbaord_physicalLabel() );
		physicalValue = uiComponentsUtil.getLabel( "", "s3" );
		physicalValue.addStyleName( appResources.style().dashbaord_physicalValue() );
		MaterialLabel usableLabel = uiComponentsUtil.getLabel( "Usable", "s3" );
		usableLabel.addStyleName( appResources.style().dashbaord_usableLabel() );
		usableValue = uiComponentsUtil.getLabel( "", "s3" );
		usableValue.addStyleName( appResources.style().dashbaord_usableValue() );

		capacityChartConatianer.add( physicalLabel );
		capacityChartConatianer.add( physicalValue );
		capacityChartConatianer.add( usableLabel );
		capacityChartConatianer.add( usableValue );

		// MaterialLoader.loading( true, capacityChartConatianer );

		getCapacityChartData();

		MaterialPanel rtmUsageChartPanel = new MaterialPanel();
		//		rtmUsageChartPanel.setGrid( "s8" );
		rtmUsageChartPanel.setId( "rtmUsageChartPanel" );
		rtmUsageChartPanel.addStyleName( appResources.style().rtmUsageChartPanel() );
		rtmUsageChartPanel.addAttachHandler( new Handler()
		{

			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				usageChart = RTMUsageChart.drawRTMUsageChart( rtmUsageChartPanel.getId() );
			}
		} );
		// MaterialLoader.loading(true, rtmUsageChartPanel);
		getRTMChartsData();
		MaterialPanel systemStatusCol = new MaterialPanel();
		//		systemStatusCol.setGrid( "s4" );

		MaterialPanel cpuUsageContainer = new MaterialPanel();
		//		cpuUsageContainer.setGrid( "s12" );
		cpuUsageContainer.setId( "cpuUsageContainer" );
		cpuUsageContainer.addStyleName( appResources.style().cpuUsage() );

		MaterialPanel memoryUsageContainer = new MaterialPanel();
		//		memoryUsageContainer.setGrid( "s12" );
		memoryUsageContainer.setId( "memoryUsageContainer" );
		memoryUsageContainer.addStyleName( appResources.style().memoryUsage() );

		MaterialPanel coreTempUsageContainer = new MaterialPanel();
		//		coreTempUsageContainer.setGrid( "s12" );
		coreTempUsageContainer.setId( "coreTempUsageContainer" );
		coreTempUsageContainer.addStyleName( appResources.style().coreTempUsage() );

		systemStatusCol.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					cpuUsageChart = ProgressiveChart.drawProgressiveChart( cpuUsageContainer.getId() );
					memUsageChart = ProgressiveChart.drawProgressiveChart( memoryUsageContainer.getId() );
					coreTempChart = ProgressiveChart.drawProgressiveChart( coreTempUsageContainer.getId() );
				}
			}
		} );

		getSystemStatusChartData();

		cpuLabel.addStyleName( appResources.style().dashbaord_system_status_Label() );
		memoryLabel.addStyleName( appResources.style().dashbaord_system_status_Label() );
		coreTempLabel.addStyleName( appResources.style().dashbaord_system_status_Label() );
		systemStatusCol.add( cpuLabel );
		systemStatusCol.add( cpuUsageContainer );
		systemStatusCol.add( memoryLabel );
		systemStatusCol.add( memoryUsageContainer );
		systemStatusCol.add( coreTempLabel );
		systemStatusCol.add( coreTempUsageContainer );

		MaterialPanel rtmPerformanceChartPanel = new MaterialPanel();
		//		rtmPerformanceChartPanel.setGrid( "s8" );
		rtmPerformanceChartPanel.setId( "rtmPerformanceChartPanel" );
		rtmPerformanceChartPanel.addStyleName( appResources.style().rtmPerformanceChartPanel() );
		rtmPerformanceChartPanel.addAttachHandler( new Handler()
		{

			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				performanceChart = RTMPerformanceChart.drawRTMPerformanceChart( rtmPerformanceChartPanel.getId() );

			}
		} );

		MaterialLabel capacityChartPanelLabel = this.uiComponentsUtil.getLabel( "Capacity/Amplification", "" );
		capacityChartPanelLabel.addStyleName( appResources.style().chart_penal_label() );
		MaterialLabel rtmUsageChartPanelLabel = this.uiComponentsUtil.getLabel( "RTM Usage", "" );
		rtmUsageChartPanelLabel.addStyleName( appResources.style().chart_penal_label() );
		MaterialLabel systemStatusChartPanelLabel = this.uiComponentsUtil.getLabel( "Host Status", "" );
		systemStatusChartPanelLabel.addStyleName( appResources.style().chart_penal_label() );
		MaterialLabel performanceChartPanelLabel = this.uiComponentsUtil.getLabel( "Performance", "" );
		performanceChartPanelLabel.addStyleName( appResources.style().chart_penal_label() );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( "dashbaord_row" );
		MaterialPanel capacityPanel = new MaterialPanel();
		capacityPanel.setGrid( "s4" );
		MaterialPanel capacityWrapperPanel = new MaterialPanel();
		capacityWrapperPanel.addStyleName( appResources.style().dashbaord_chart_row() );
		capacityPanel.add( capacityWrapperPanel );
		firstRow.add( capacityPanel );
		capacityWrapperPanel.add( capacityChartPanelLabel );
		capacityWrapperPanel.add( capacityChartConatianer );

		MaterialPanel rtmPanel = new MaterialPanel();
		rtmPanel.setGrid( "s8" );
		MaterialPanel rtmWrapperPanel = new MaterialPanel();
		rtmWrapperPanel.addStyleName( appResources.style().dashbaord_chart_row() );
		rtmPanel.add( rtmWrapperPanel );
		firstRow.add( rtmPanel );
		rtmWrapperPanel.add( rtmUsageChartPanelLabel );
		rtmWrapperPanel.add( rtmUsageChartPanel );

		MaterialRow secondRow = new MaterialRow();
		secondRow.addStyleName( "dashbaord_row" );
		MaterialPanel sysStatusPanel = new MaterialPanel();
		sysStatusPanel.setGrid( "s4" );
		MaterialPanel statusWrapperPanel = new MaterialPanel();
		statusWrapperPanel.addStyleName( appResources.style().dashbaord_chart_row() );
		sysStatusPanel.add( statusWrapperPanel );
		secondRow.add( sysStatusPanel );
		statusWrapperPanel.add( systemStatusChartPanelLabel );
		statusWrapperPanel.add( systemStatusCol );

		MaterialPanel perfPanel = new MaterialPanel();
		perfPanel.setGrid( "s8" );
		MaterialPanel perfWrapperPanel = new MaterialPanel();
		perfWrapperPanel.addStyleName( appResources.style().dashbaord_chart_row() );
		perfPanel.add( perfWrapperPanel );
		secondRow.add( perfPanel );
		perfWrapperPanel.add( performanceChartPanelLabel );
		perfWrapperPanel.add( rtmPerformanceChartPanel );

		row.add( firstRow );
		row.add( secondRow );
		add( row );

		getCapacityChartData();
		getRTMChartsData();
		getSystemStatusChartData();
		initializeWS();
		//startPolling();
	}

	private void getSystemStatusChartData()
	{
		LoggerUtil.log( "Firing getSystemStatusChartData" );
		CommonServiceProvider.getCommonService().getSystemStatusChartData( new ApplicationCallBack<SystemStatusChartModel>()
		{
			@Override
			public void onSuccess( SystemStatusChartModel systemStatusChartModel )
			{
				LoggerUtil.log( "Got data for Firing getSystemStatusChartData" );
				String cpuUsage = Converter.formatDecimal( systemStatusChartModel.getCpuUsage() );
				String memoryUsage = Converter.formatDecimal( systemStatusChartModel.getMemUsage() );
				String coreTemp = Converter.formatDecimal( systemStatusChartModel.getCoreTemp() );
				String coreTempPer = Converter.formatDecimal( systemStatusChartModel.getCoreTempPerc() );
				ProgressiveChart.setData( cpuUsageChart, cpuUsage );
				ProgressiveChart.setData( memUsageChart, memoryUsage );
				ProgressiveChart.setData( coreTempChart, coreTempPer, "95", "85", coreTemp + " &#8451;" );
				cpuLabel.setText( "CPU - " + cpuUsage + "%" );
				memoryLabel.setText( "Memory - " + memoryUsage + "%" );
				coreTempLabel.getElement().setInnerHTML( "Core Temperature - " + coreTemp + " &#8451;" );
				cpuUsageChartImpl = true;
				memUsageChartImpl = true;
				coreTempChartImpl = true;
				LoggerUtil.log( "Got data for Firing getSystemStatusChartData-chart data set " );
				//coreTempLabel.setText( "Core Temperature - " + coreTemp+ "" );
			}

		} );
	}

	private void getRTMChartsData()
	{
		CommonServiceProvider.getCommonService().getRTMUsageChartData( new ApplicationCallBack<String>()
		{
			@Override
			public void onSuccess( String result )
			{
				RTMUsageChart.setData( usageChart, JsonUtils.unsafeEval( result ) );
				RTMPerformanceChart.setData( performanceChart, JsonUtils.unsafeEval( result ) );
				usageChartImpl = true;
				performanceChartImpl = true;

			}
		} );
	}

	private void getCapacityChartData()
	{
		CommonServiceProvider.getCommonService().getCapacityChartData( new ApplicationCallBack<CapacityChartModel>()
		{
			@Override
			public void onSuccess( CapacityChartModel capacityChartModel )
			{
				if ( capacityChartModel != null )
				{
					LoggerUtil.log( "Capacity chart received values  values " );
					LoggerUtil.log( "getAvailablePercent " + capacityChartModel.getAvailablePercent().toString() );
					LoggerUtil.log( "getAvailableSize " + String.valueOf( capacityChartModel.getAvailableSize() ) );
					LoggerUtil.log( "getAllocatedPercent " + capacityChartModel.getAllocatedPercent() );
					LoggerUtil.log( "getAllocatedSize " + String.valueOf( capacityChartModel.getAllocatedSize() ) );
					LoggerUtil.log( "getAmpl " + capacityChartModel.getAmpl() );
					LoggerUtil.log( "getPhysicalSize " + String.valueOf( capacityChartModel.getPhysicalSize() ) );
					LoggerUtil.log( "getUsagableSize " + String.valueOf( capacityChartModel.getUsagableSize() ) );

					CapacityChart.setData( capacityChart, capacityChartModel.getAvailablePercent(), Converter.getFormatSize( capacityChartModel.getAvailableSize() ), capacityChartModel.getAllocatedPercent(), Converter.getFormatSize( capacityChartModel.getAllocatedSize() ), capacityChartModel.getAmpl() );
					physicalValue.setText( Converter.getFormatSize( capacityChartModel.getPhysicalSize() ) );
					usableValue.setText( Converter.getFormatSize( capacityChartModel.getUsagableSize() ) );
					capacityChartImpl = true;
					LoggerUtil.log( "capacityChartImpl value " + capacityChartImpl );
					// MaterialLoader.loading( false, capacityChartConatianer );
				}
			}
		} );
	}

	private void startPolling()
	{
		timer = new Timer()
		{
			@Override
			public void run()
			{
				LoggerUtil.log( "polling every 5 seconds" );
				getCapacityChartData();
				getRTMChartsData();
				getSystemStatusChartData();

			}
		};
		// Schedule the timer to run once in 5 seconds.
		timer.scheduleRepeating( 30000 );
	}

	public Timer getTimer()
	{
		return timer;
	}

	public void updateChartsRealtime( JSONValue parsedData )
	{
		LoggerUtil.log( "capacityChartImpl value " + capacityChartImpl );
		JSONObject parsedObject = parsedData.isObject();
		if ( capacityChartImpl )
		{
			try
			{
				CapacityChartModel capacityChartModel = updateCapacityChartPreProcessData( parsedObject );
				updateCapacityChartData( capacityChartModel );
				LoggerUtil.log( "Updated  capacity chart relatime" );
			}
			catch ( Exception e )
			{
				LoggerUtil.log( "Failed to update capacity charts in realtime " );
				e.printStackTrace();
			}
		}
		if ( cpuUsageChartImpl && memUsageChartImpl && coreTempChartImpl )
		{
			try
			{
				SystemStatusChartModel systemStatusChartModel = updateSystemStatusChartsPreProcessData( parsedObject );
				updateSystemStatusCharts( systemStatusChartModel );
				LoggerUtil.log( "Updated system chart relatime" );
			}
			catch ( Exception e )
			{
				LoggerUtil.log( "Failed to update System status charts in realtime " );
				e.printStackTrace();
			}
		}
		if ( usageChartImpl && performanceChartImpl )
		{
			try
			{
				JSONObject rtmChartData = updateRTMUsagePerfChartPreProcessData( parsedObject );
				updateRTMUsageAndPerfChartData( rtmChartData.toString() );
				LoggerUtil.log( "Updated RTM USAGE CHART" );
			}
			catch ( Exception e )
			{
				LoggerUtil.log( "Failed to update RTM Usage and Performance charts in realtime " );
				e.printStackTrace();
			}
		}
	}

	private JSONObject updateRTMUsagePerfChartPreProcessData( JSONObject parsedObject )
	{
		LoggerUtil.log( "Updating RTM USAGE chart relatime" );
		LoggerUtil.log( "start data retrieval" );
		double physicalRTMsize = RestCallUtil.getRawDouble( parsedObject, "rtm_total_size" );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 1" );
		double cur_usage_size = physicalRTMsize - ( RestCallUtil.getRawDouble( parsedObject, "avail_rtm_size" ) * 4096 );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 2" );
		long read = ( long ) RestCallUtil.getRawDouble( parsedObject, "read" );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 3" );
		long write = ( long ) RestCallUtil.getRawDouble( parsedObject, "write" );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 4" );
		long timePoint = ( long ) RestCallUtil.getRawDouble( parsedObject, "timepoint" );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 5" );

		double readThrough = ( long ) RestCallUtil.getRawDouble( parsedObject, "read_through" );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 6" );
		double writeThrough = ( long ) RestCallUtil.getRawDouble( parsedObject, "write_through" );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 7" );

		LoggerUtil.log( "populate JSON object" );
		JSONObject rtmChartData = new JSONObject();
		LoggerUtil.log( "Updating RTM USAGE chart relatime 8" );
		rtmChartData.put( "read", RestCallUtil.getJSONNumber( read ) );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 9" );
		rtmChartData.put( "write", RestCallUtil.getJSONNumber( write ) );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 10" );
		rtmChartData.put( "cur_total_size", RestCallUtil.getJSONNumber( physicalRTMsize ) );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 11" );
		rtmChartData.put( "cur_usage_size", RestCallUtil.getJSONNumber( cur_usage_size ) );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 12" );
		rtmChartData.put( "timepoint", RestCallUtil.getJSONNumber( timePoint * 1000 ) );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 13" );
		rtmChartData.put( "readThrough", RestCallUtil.getJSONNumber( readThrough ) );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 14" );
		rtmChartData.put( "writeThrough", RestCallUtil.getJSONNumber( writeThrough ) );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 15" );

		LoggerUtil.log( "RTM CHART DATAT " + rtmChartData.toString() );
		return rtmChartData;
	}

	private SystemStatusChartModel updateSystemStatusChartsPreProcessData( JSONObject parsedObject )
	{
		LoggerUtil.log( "Updating system charts relatime" );
		LoggerUtil.log( "getting cpu_overall value" );
		double cpuUsage = RestCallUtil.getRawDouble( parsedObject, "cpu_overall" );
		LoggerUtil.log( "cpu_overall value " + cpuUsage );
		LoggerUtil.log( "getting memory value" );
		double memUsage = RestCallUtil.getRawDouble( parsedObject, "memory" );
		LoggerUtil.log( "memory value " + memUsage );
		LoggerUtil.log( "getting CPU1_temperature value" );
		double cpuTemp1 = 0;
		if ( RestCallUtil.getRawString( parsedObject, "CPU1_temperature" ) != null )
		{
			cpuTemp1 = Double.valueOf( RestCallUtil.getRawString( parsedObject, "CPU1_temperature" ) );
		}
		LoggerUtil.log( "CPU1_temperature value " + cpuTemp1 );
		LoggerUtil.log( "getting CPU2_temperature value" );

		double cpuTemp2 = 0;
		if ( RestCallUtil.getRawString( parsedObject, "CPU1_temperature" ) != null )
		{
			cpuTemp2 = Double.valueOf( RestCallUtil.getRawString( parsedObject, "CPU2_temperature" ) );
		}
		LoggerUtil.log( "CPU2_temperature value " + cpuTemp2 );
		double higherTemp = cpuTemp1;
		if ( cpuTemp2 > higherTemp )
		{
			higherTemp = cpuTemp2;
		}
		LoggerUtil.log( "higher temp  value " + higherTemp );
		double coreTempPer = ( double ) ( ( higherTemp / ApplicationConstants.MAX_TEMP ) * 100 );
		SystemStatusChartModel systemStatusChartModel = new SystemStatusChartModel( memUsage, cpuUsage, higherTemp, coreTempPer );
		return systemStatusChartModel;
	}

	private CapacityChartModel updateCapacityChartPreProcessData( JSONObject parsedObject )
	{
		LoggerUtil.log( "Updating capacity chart relatime" );

		double availableSize = RestCallUtil.getRawDouble( parsedObject, "available_size" );

		if ( availableSize < 0 )
		{
			availableSize = 0;
		}

		double curTotalSize = RestCallUtil.getRawDouble( parsedObject, "cur_total_size" );
		double rtmTotalSize = RestCallUtil.getRawDouble( parsedObject, "rtm_total_size" );
		double ampl = RestCallUtil.getRawDouble( parsedObject, "ampl" );

		double availablePercent = ( double ) ( ( availableSize / curTotalSize ) * 100 );
		double allocatedSize = curTotalSize - availableSize;

		double allocatedPercent = ( double ) ( ( allocatedSize / curTotalSize ) * 100 );
		double physicalSize = rtmTotalSize;
		double usagableSize = curTotalSize;

		CapacityChartModel capacityChartModel = new CapacityChartModel( String.valueOf( availablePercent ), availableSize, String.valueOf( allocatedPercent ), allocatedSize, physicalSize, usagableSize, Converter.formatDecimal( ampl ) + " X" );
		capacityChartModel.setUnAllocatedPercent( String.valueOf( 100 - allocatedPercent ) );
		return capacityChartModel;
	}

	public void updateCapacityChartData( CapacityChartModel capacityChartModel )
	{
		CapacityChart.setData( capacityChart, capacityChartModel.getAvailablePercent(), Converter.getFormatSize( capacityChartModel.getAvailableSize() ), capacityChartModel.getAllocatedPercent(), Converter.getFormatSize( capacityChartModel.getAllocatedSize() ), capacityChartModel.getAmpl() );
		physicalValue.setText( Converter.getFormatSize( capacityChartModel.getPhysicalSize() ) );
		usableValue.setText( Converter.getFormatSize( capacityChartModel.getUsagableSize() ) );
	}

	public void updateRTMUsageAndPerfChartData( String result )
	{
		LoggerUtil.log( "Updating RTM USAGE chart relatime 16" );
		RTMUsageChart.setIncrementalData( usageChart, JsonUtils.unsafeEval( result ) );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 17" );
		RTMPerformanceChart.setIncrementalData( performanceChart, JsonUtils.unsafeEval( result ) );
		LoggerUtil.log( "Updating RTM USAGE chart relatime 18" );
	}

	public void updateSystemStatusCharts( SystemStatusChartModel systemStatusChartModel )
	{
		LoggerUtil.log( "indide updateSystemStatusCharts" );
		String cpuUsage = Converter.formatDecimal( systemStatusChartModel.getCpuUsage() );
		LoggerUtil.log( "indide updateSystemStatusCharts 1" );
		String memoryUsage = Converter.formatDecimal( systemStatusChartModel.getMemUsage() );
		LoggerUtil.log( "indide updateSystemStatusCharts 2" );
		String coreTemp = Converter.formatDecimal( systemStatusChartModel.getCoreTemp() );
		LoggerUtil.log( "indide updateSystemStatusCharts 3" );
		String coreTempPer = Converter.formatDecimal( systemStatusChartModel.getCoreTempPerc() );
		LoggerUtil.log( "indide updateSystemStatusCharts 4" );
		ProgressiveChart.setData( cpuUsageChart, cpuUsage );
		LoggerUtil.log( "indide updateSystemStatusCharts 5" );
		ProgressiveChart.setData( memUsageChart, memoryUsage );
		LoggerUtil.log( "indide updateSystemStatusCharts 6" );
		ProgressiveChart.setData( coreTempChart, coreTempPer, "95", "85", coreTemp + " &#8451;" );
		LoggerUtil.log( "indide updateSystemStatusCharts 7" );
		cpuLabel.setText( "CPU - " + cpuUsage + "%" );
		LoggerUtil.log( "indide updateSystemStatusCharts 8" );
		memoryLabel.setText( "Memory - " + memoryUsage + "%" );
		LoggerUtil.log( "indide updateSystemStatusCharts 9" );
		coreTempLabel.getElement().setInnerHTML( "Core Temperature - " + coreTemp + " &#8451;" );
		LoggerUtil.log( "indide updateSystemStatusCharts 10" );
	}

	private void initializeWS()
	{
		LoggerUtil.log( "TRYING TO Initializing WEB SOCKET" );
		if ( dashboardChartsbWebsocket == null )
		{
			LoggerUtil.log( "Initializing WEB SOCKET AS IT IS NULL" );
			dashboardChartsbWebsocket = WebsocketHelper.getWebsocketInstance( WebsocketEndpoints.DASHBOARD_END_POINT, new IcommandWithData()
			{
				@Override
				public void executeWithData( Object obj )
				{
					updateChartsRealtime( ( JSONValue ) obj );
				}
			} );
		}
		else if ( dashboardChartsbWebsocket.getState() == 3 )
		{
			LoggerUtil.log( "Reopening the WEB SOCKET AS IT was closed" );
			dashboardChartsbWebsocket.open();
		}
	}

	public Websocket getDashboardChartsbWebsocket()
	{
		return dashboardChartsbWebsocket;
	}

	/*public void setFirst( boolean isFirst )
	{
		this.isFirst = isFirst;
	}*/

}
