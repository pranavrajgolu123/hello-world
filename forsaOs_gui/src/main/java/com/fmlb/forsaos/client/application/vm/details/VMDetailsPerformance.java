package com.fmlb.forsaos.client.application.vm.details;

import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONObject;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class VMDetailsPerformance extends MaterialPanel
{
	private VMModel vmModel;

	private AppResources appResources = GWT.create( AppResources.class );

	private UIComponentsUtil uiComponentsUtil;

	private CurrentUser currentUser;

	private JavaScriptObject cpuUsageChart = null;

	private JavaScriptObject memUsageChart = null;

	private JavaScriptObject readWriteBandwidthChart = null;

	private JavaScriptObject readWriteIOChart = null;

	private JavaScriptObject readWriteLatencyChart = null;

	private MaterialRow chartRow;

	private MaterialPanel cpuUsagechartContainer;

	private MaterialPanel memUsageChartContainer;

	private MaterialPanel readWriteBandwidthChartContainer;

	private MaterialPanel readWriteIOChartContainer;

	private MaterialPanel readWriteLatencyChartContainer;

	private MaterialLabel memUsageChartContainerLbl;

	AppResources resources = GWT.create( AppResources.class );

	public VMDetailsPerformance( VMModel vmModel, UIComponentsUtil uiComponentsUtil )
	{
		this.vmModel = vmModel;
		this.uiComponentsUtil = uiComponentsUtil;

		chartRow = new MaterialRow();

		cpuUsagechartContainer = getChartContainer( "cpuUsagechartContainer", "s12", appResources.style().capacityChartPanel() );
		chartRow.add( getChartContainerWrapper( "s6", cpuUsagechartContainer, "CPU Usage", appResources.style().performanceHeaderPenal(), "s12" ) );
		cpuUsagechartContainer.addStyleName( resources.style().performanceChartPenal() );

		memUsageChartContainer = getChartContainer( "memUsageChartContainer", "s12", appResources.style().capacityChartPanel() );
		chartRow.add( getMemoryUsageChartCol() );
		memUsageChartContainer.addStyleName( resources.style().performanceChartPenal() );

		readWriteBandwidthChartContainer = getChartContainer( "readWriteBandwidthChartContainer", "s12", appResources.style().capacityChartPanel() );
		chartRow.add( getChartContainerWrapper( "s6", readWriteBandwidthChartContainer, "Read/Write Bandwidth", appResources.style().performanceHeaderPenal(), "s12" ) );
		readWriteBandwidthChartContainer.addStyleName( resources.style().performanceChartPenal() );

		readWriteIOChartContainer = getChartContainer( "readWriteIOChartContainer", "s12", appResources.style().capacityChartPanel() );
		chartRow.add( getChartContainerWrapper( "s6", readWriteIOChartContainer, "Read/Write I/O", appResources.style().performanceHeaderPenal(), "s12" ) );
		readWriteIOChartContainer.addStyleName( resources.style().performanceChartPenal() );

		readWriteLatencyChartContainer = getChartContainer( "readWriteLatencyChartContainer", "s12", appResources.style().capacityChartPanel() );
		chartRow.add( getChartContainerWrapper( "s6", readWriteLatencyChartContainer, "Read/Write Latency", appResources.style().performanceHeaderPenal(), "s12" ) );
		readWriteLatencyChartContainer.addStyleName( resources.style().performanceChartPenal() );

		initializeCPUUsageChart();
		initializeReadWriteIOChart();
		initializeVMReadWriteLatencyChart();
		initializeVMMemoryUsageChart();
		initializeVMReadWriteBandwidthChart();
		add( chartRow );
		chartRow.addStyleName( "performanceRow" );
	}

	// Special container for memory usage chart as the header label needs to change dynamically
	private MaterialColumn getMemoryUsageChartCol()
	{
		MaterialColumn memUsageChartContainerCol = new MaterialColumn();
		memUsageChartContainerCol.setGrid( "s6" );

		memUsageChartContainerLbl = this.uiComponentsUtil.getLabel( "Memory Usage 0B of " + Converter.getFormatKiBSize( vmModel.getMemory(), MemorySizeType.KiB.getValue() ), "" );
		memUsageChartContainerLbl.addStyleName( appResources.style().performanceHeaderPenal() );
		memUsageChartContainerLbl.setGrid( "s12" );

		memUsageChartContainerCol.add( memUsageChartContainerLbl );
		memUsageChartContainerCol.add( memUsageChartContainer );
		return memUsageChartContainerCol;
	}

	private void initializeCPUUsageChart()
	{
		cpuUsagechartContainer.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					cpuUsageChart = VMCPUUsageChart.drawVMCPUUsageChart( cpuUsagechartContainer.getId() );

					/*JSONObject dataPoint = new JSONObject();
					dataPoint.put( "timestamp", RestCallUtil.getJSONNumber( 1545982316000d ) );
					dataPoint.put( "cpu_percent", RestCallUtil.getJSONNumber( 0.771867 ) );
					LoggerUtil.log( dataPoint.toString() );
					VMCPUUsageChart.setIncrementalData( cpuUsageChart, JsonUtils.unsafeEval( dataPoint.toString() ) );*/

				}
			}
		} );
	}

	public void updateCPUUsageChartData( JSONObject parsedData )
	{
		LoggerUtil.log( "vm cpu usage parsed data " + parsedData.toString() );
		VMCPUUsageChart.setIncrementalData( cpuUsageChart, JsonUtils.unsafeEval( parsedData.toString() ) );
	}

	private void initializeReadWriteIOChart()
	{
		readWriteIOChartContainer.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					readWriteIOChart = VMReadWriteIOChart.drawVMReadWriteIOChart( readWriteIOChartContainer.getId() );

				}
			}
		} );
	}

	public void updateReadWriteIOChartData( JSONObject parsedData )
	{
		LoggerUtil.log( "vm ReadWriteIOChart parsed data " + parsedData.toString() );
		VMReadWriteIOChart.setIncrementalData( readWriteIOChart, JsonUtils.unsafeEval( parsedData.toString() ) );
	}

	private void initializeVMReadWriteLatencyChart()
	{
		readWriteLatencyChartContainer.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					readWriteLatencyChart = VMReadWriteLatencyChart.drawVMReadWriteLatencyChart( readWriteLatencyChartContainer.getId() );

					/*JSONObject readWriteLatencyDataPoint = new JSONObject();
					
					readWriteLatencyDataPoint.put( "timestamp", RestCallUtil.getJSONNumber( 1545982316000d ) );
					LoggerUtil.log( "updateVMReadWriteLatencyChartData 1" );
					
					readWriteLatencyDataPoint.put( "rd_latency_nsec", RestCallUtil.getJSONNumber( 900000d ) );
					LoggerUtil.log( "updateVMReadWriteLatencyChartData 2" );
					
					readWriteLatencyDataPoint.put( "wr_latency_nsec", RestCallUtil.getJSONNumber( 900000d ) );
					LoggerUtil.log( "updateVMReadWriteLatencyChartData 3" );
					
					VMReadWriteLatencyChart.setIncrementalData( readWriteLatencyChart, JsonUtils.unsafeEval( readWriteLatencyDataPoint.toString() ) );*/
				}
			}
		} );
	}

	public void updateVMMemoryChartData( JSONObject parsedData )
	{
		LoggerUtil.log( "VMMemoryChartData" + parsedData.toString() );
		long usedMemBystes = ( long ) RestCallUtil.getRawDouble( parsedData, "mem_rss_kb" );
		String formatSize = Converter.getFormatSize( usedMemBystes );
		memUsageChartContainerLbl.setText( "Memory Usage " + formatSize + " of " + Converter.getFormatKiBSize( vmModel.getMemory(), MemorySizeType.KiB.getValue() ) );
		memUsageChartContainerLbl.setTitle( "Memory Usage " + formatSize + " of " + Converter.getFormatKiBSize( vmModel.getMemory(), MemorySizeType.KiB.getValue() ) );
		VMMemoryUsageChart.setIncrementalData( memUsageChart, JsonUtils.unsafeEval( parsedData.toString() ) );
	}

	private void initializeVMMemoryUsageChart()
	{
		memUsageChartContainer.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					memUsageChart = VMMemoryUsageChart.drawVMMemoryUsageChart( memUsageChartContainer.getId() );

				}
			}
		} );
	}

	public void updateVMReadWriteLatencyChartData( JSONObject parsedData )
	{
		LoggerUtil.log( "VMReadWriteLatencyChart" + parsedData.toString() );
		VMReadWriteLatencyChart.setIncrementalData( readWriteLatencyChart, JsonUtils.unsafeEval( parsedData.toString() ) );
	}

	private void initializeVMReadWriteBandwidthChart()
	{
		readWriteBandwidthChartContainer.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					readWriteBandwidthChart = VMReadWriteBandwidthChart.drawVMReadWriteBandwidthChart( readWriteBandwidthChartContainer.getId() );

				}
			}
		} );
	}

	public void updateVMReadWriteBandwidthChartData( JSONObject parsedData )
	{
		LoggerUtil.log( "updateVMReadWriteBandwidthChartData" + parsedData.toString() );
		VMReadWriteBandwidthChart.setIncrementalData( readWriteBandwidthChart, JsonUtils.unsafeEval( parsedData.toString() ) );
	}

	private MaterialPanel getChartContainer( String chartContainerId, String chartContainerColSpec, String chartContainerStyleName )
	{
		MaterialPanel chartContainer = new MaterialPanel();
		chartContainer.setId( chartContainerId );
		chartContainer.setGrid( chartContainerColSpec );
		chartContainer.addStyleName( chartContainerStyleName );
		return chartContainer;
	}

	private MaterialColumn getChartContainerWrapper( String chartContainerColoumColSpec, MaterialPanel chartContainer, String chartLabel, String chartLabelStyle, String chartLabelColSpec )
	{
		MaterialColumn chartContainerCol = new MaterialColumn();
		chartContainerCol.setGrid( chartContainerColoumColSpec );

		MaterialLabel chartContainerLbl = this.uiComponentsUtil.getLabel( chartLabel, "" );
		chartContainerLbl.addStyleName( chartLabelStyle );
		chartContainerLbl.setGrid( chartLabelColSpec );

		chartContainerCol.add( chartContainerLbl );
		chartContainerCol.add( chartContainer );
		return chartContainerCol;
	}

}