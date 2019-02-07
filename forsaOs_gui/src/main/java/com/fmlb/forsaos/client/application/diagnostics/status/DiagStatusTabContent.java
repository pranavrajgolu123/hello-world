package com.fmlb.forsaos.client.application.diagnostics.status;

import com.fmlb.forsaos.client.application.common.Converter;
import com.fmlb.forsaos.client.application.common.ProgressBar;
import com.fmlb.forsaos.client.application.models.DiagSystemHealthModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.CommonUtil;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class DiagStatusTabContent extends MaterialPanel
{

	private AppResources resources = GWT.create( AppResources.class );

	private DiagSystemHealthModel diagSystemHealthModel;

	private UIComponentsUtil uiComponentsUtil;

	private MaterialPanel usagePanel;

	private MaterialColumn cpuUsageCol;

	private MaterialColumn memUsageCol;

	private MaterialLabel systemNameVal;

	private MaterialLabel versionVal;

	private ProgressBar cpuProgressBar;

	private ProgressBar memUsageProgressBar;

	private ProgressBar cpu1TempProgressBar;

	private ProgressBar cpu2TempProgressBar;

	private MaterialRow fanStausRow;

	private MaterialRow fanStatusBodyRow;

	private MaterialRow systemTempRow;

	private MaterialLabel volate12Val;

	private MaterialLabel volate5Val;

	private MaterialLabel volate3Val;

	private static final String PROGRESS_BAR_HEIGHT = "16px";

	private boolean isImplDataReceived = false;

	public DiagStatusTabContent( UIComponentsUtil uiComponentsUtil )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		drawPanel();
	}

	private void drawPanel()
	{
		add( createSystemInfoCollection() );
		add( createVolatageCollection() );
		add( createUsagePanel() );
		add( getSystemTempRow() );
		add( getFanRow() );
	}

	private MaterialRow createSystemInfoCollection()
	{

		MaterialLabel systemInfoHeaderLbl = this.uiComponentsUtil.getLabel( "Host Information", "s12", resources.style().statusHeader() );
		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		collection1.addStyleName( "NVMEDetails" );

		MaterialCollectionItem collectionItem1 = new MaterialCollectionItem();
		collectionItem1.setGrid( "s6" );
		collectionItem1.add( this.uiComponentsUtil.getLabel( "Host Name", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary1 = new MaterialCollectionSecondary();
		systemNameVal = this.uiComponentsUtil.getLabel( "", "", resources.style().displayInline() );
		collectionSecondary1.add( systemNameVal );
		collectionItem1.add( collectionSecondary1 );

		MaterialCollectionItem collectionItem2 = new MaterialCollectionItem();
		collectionItem2.setGrid( "s6" );
		collectionItem2.add( this.uiComponentsUtil.getLabel( "ForsaOS Version", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary2 = new MaterialCollectionSecondary();
		versionVal = this.uiComponentsUtil.getLabel( "", "", resources.style().displayInline() );
		collectionSecondary2.add( versionVal );
		collectionItem2.add( collectionSecondary2 );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );

		MaterialRow row = new MaterialRow();
		//row.addStyleName( resources.style().rtm_detail_row() );
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s12" );
		column1.addStyleName( resources.style().NVMEdetailRow() );
		column1.add( collection1 );

		row.add( systemInfoHeaderLbl );
		row.add( column1 );

		row.addStyleName( "statusRow" );
		return row;
	}

	private MaterialPanel createUsagePanel()
	{
		usagePanel = new MaterialPanel();
		usagePanel.setGrid( "s12" );
		usagePanel.addStyleName( "cpuMemoryRow statusRow" );

		MaterialRow usageRow = new MaterialRow();
		usageRow.setGrid( "s12" );
		usageRow.addStyleName( "statusRow" );
		usageRow.add( createCpuUsageCol() );
		usageRow.add( createMemUsageCol() );
		usagePanel.add( usageRow );
		return usagePanel;
	}

	private MaterialColumn createCpuUsageCol()
	{
		cpuUsageCol = new MaterialColumn();
		cpuUsageCol.setGrid( "s6" );
		cpuUsageCol.addStyleName( "statusCpuRow" );
		MaterialLabel cpuUsageLbl = this.uiComponentsUtil.getLabel( "CPU Usage", "s12", resources.style().statusHeader() );

		cpuProgressBar = new ProgressBar( "0%", "0%", null, null, PROGRESS_BAR_HEIGHT );
		cpuUsageCol.add( cpuUsageLbl );
		cpuUsageCol.add( cpuProgressBar );
		return cpuUsageCol;
	}

	private MaterialColumn createMemUsageCol()
	{
		memUsageCol = new MaterialColumn();
		memUsageCol.setGrid( "s6" );
		memUsageCol.addStyleName( "statusMemoryRow" );
		MaterialLabel memUsageLbl = this.uiComponentsUtil.getLabel( "Memory Usage", "s12", resources.style().statusHeader() );

		memUsageProgressBar = new ProgressBar( "0%", "0", null, null, PROGRESS_BAR_HEIGHT );
		memUsageCol.add( memUsageLbl );
		memUsageCol.add( memUsageProgressBar );
		return memUsageCol;
	}

	private MaterialRow getFanRow()
	{
		fanStausRow = new MaterialRow();
		fanStausRow.setGrid( "s12" );
		fanStausRow.addStyleName( "statusRow NVMERow" );
		fanStatusBodyRow = new MaterialRow();
		fanStatusBodyRow.setGrid( "s12" );
		MaterialLabel fanHeaderLbl = this.uiComponentsUtil.getLabel( "Host Fans", "s12", resources.style().statusHeader() );
		fanStausRow.add( fanHeaderLbl );
		fanStausRow.add( fanStatusBodyRow );
		fanStatusBodyRow.addStyleName( resources.style().NVMEdetailRow() );
		return fanStausRow;
	}

	private MaterialRow getSystemTempRow()
	{
		systemTempRow = new MaterialRow();
		systemTempRow.setGrid( "s12" );
		systemTempRow.addStyleName( "statusRow" );
		MaterialLabel systemTempHeaderLbl = this.uiComponentsUtil.getLabel( "Host CPU Temperature(s)", "s12", resources.style().statusHeader() );
		systemTempRow.add( systemTempHeaderLbl );

		MaterialColumn cpu1Col = new MaterialColumn();
		cpu1Col.setGrid( "s6" );
		cpu1Col.addStyleName( "cpuTemp1" );
		MaterialColumn cpu2Col = new MaterialColumn();
		cpu2Col.setGrid( "s6" );
		cpu2Col.addStyleName( "cpuTemp2" );

		cpu1TempProgressBar = new ProgressBar( "0%", "CPU 1 Temperature", null, null, PROGRESS_BAR_HEIGHT );
		cpu2TempProgressBar = new ProgressBar( "0%", "CPU 2 Temperature", null, null, PROGRESS_BAR_HEIGHT );

		cpu1Col.add( cpu1TempProgressBar );
		cpu2Col.add( cpu2TempProgressBar );

		systemTempRow.add( cpu1Col );
		systemTempRow.add( cpu2Col );
		return systemTempRow;
	}

	private MaterialPanel getFanStatusCol( String fanName, String rpmLbl )
	{
		MaterialPanel fanStausCol = new MaterialPanel();
		fanStausCol.setGrid( "s3" );
		fanStausCol.addStyleName( "systemFanPenal" );
		MaterialIcon icon = new MaterialIcon();
		icon.setGrid( "s2" );
		icon.addStyleName( resources.style().fanIcon() );
		MaterialLabel fanLbl = this.uiComponentsUtil.getLabel( fanName + " " + Double.valueOf( rpmLbl ).intValue() + " " + "RPM", "s10" );
		fanStausCol.add( icon );
		fanStausCol.add( fanLbl );
		return fanStausCol;
	}

	private MaterialRow createVolatageCollection()
	{

		MaterialLabel voltageLabel = this.uiComponentsUtil.getLabel( "Voltages", "s12", resources.style().statusHeader() );
		MaterialCollection collection1 = new MaterialCollection();
		collection1.addStyleName( resources.style().noMargin() );
		collection1.addStyleName( "statusVoltageDetails" );

		MaterialCollectionItem collectionItem1 = new MaterialCollectionItem();
		collectionItem1.setGrid( "s4" );
		collectionItem1.add( this.uiComponentsUtil.getLabel( "Voltage_12V", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary1 = new MaterialCollectionSecondary();
		volate12Val = this.uiComponentsUtil.getLabel( "", "", resources.style().displayInline() );
		collectionSecondary1.add( volate12Val );
		collectionItem1.add( collectionSecondary1 );

		MaterialCollectionItem collectionItem2 = new MaterialCollectionItem();
		collectionItem2.setGrid( "s4" );
		collectionItem2.add( this.uiComponentsUtil.getLabel( "Voltage_3_3V", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary2 = new MaterialCollectionSecondary();
		volate3Val = this.uiComponentsUtil.getLabel( "", "", resources.style().displayInline() );
		collectionSecondary2.add( volate3Val );
		collectionItem2.add( collectionSecondary2 );

		MaterialCollectionItem collectionItem3 = new MaterialCollectionItem();
		collectionItem3.setGrid( "s4" );
		collectionItem3.add( this.uiComponentsUtil.getLabel( "Voltage_5V", "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary3 = new MaterialCollectionSecondary();
		volate5Val = this.uiComponentsUtil.getLabel( "", "", resources.style().displayInline() );
		collectionSecondary3.add( volate5Val );
		collectionItem3.add( collectionSecondary3 );

		collection1.add( collectionItem1 );
		collection1.add( collectionItem2 );
		collection1.add( collectionItem3 );

		MaterialRow row = new MaterialRow();
		//row.addStyleName( resources.style().rtm_detail_row() );
		MaterialColumn column1 = new MaterialColumn();
		column1.setGrid( "s12" );
		column1.addStyleName( resources.style().NVMEdetailRow() );
		column1.add( collection1 );

		row.add( voltageLabel );
		row.add( column1 );
		row.addStyleName( "statusRow" );
		return row;
	}

	public void updateData( DiagSystemHealthModel diagSystemHealthModel )
	{
		LoggerUtil.log( "IMPL MDATA RECEIVED" );
		isImplDataReceived = true;
		assignValues( diagSystemHealthModel );
	}

	private void assignValues( DiagSystemHealthModel result )
	{
		this.diagSystemHealthModel = result;
		systemNameVal.setText( diagSystemHealthModel.getSysInfoModel().getHostname() );
		systemNameVal.setTitle( diagSystemHealthModel.getSysInfoModel().getHostname() );
		versionVal.setText( diagSystemHealthModel.getSysInfoModel().getVersion() );
		versionVal.setTitle( diagSystemHealthModel.getSysInfoModel().getVersion() );

		updateDynamicData( diagSystemHealthModel );
	}

	public void updateDynamicData( DiagSystemHealthModel diagSystemHealthMdl )
	{
		// This is required because first we should load initial data from REST API and then reload data using websocket
		if ( !isImplDataReceived )
		{
			return;
		}

		// This is static data and will not be send over websocket. So getting this data from the model which
		// was set during API call so as not loose this data
		diagSystemHealthMdl.getSysInfoModel().setHostname( diagSystemHealthModel.getSysInfoModel().getHostname() );
		diagSystemHealthMdl.getSysInfoModel().setVersion( diagSystemHealthModel.getSysInfoModel().getVersion() );

		this.diagSystemHealthModel = diagSystemHealthMdl;

		if ( diagSystemHealthModel.getVoltage_12V() != null )
		{
			volate12Val.setText( Converter.formatDecimal( Double.valueOf( diagSystemHealthModel.getVoltage_12V() ) ) + " V" );
			volate12Val.setTitle( Converter.formatDecimal( Double.valueOf( diagSystemHealthModel.getVoltage_12V() ) ) + " V" );
		}
		if ( diagSystemHealthModel.getVoltage_5V() != null )
		{
			volate5Val.setText( Converter.formatDecimal( Double.valueOf( diagSystemHealthModel.getVoltage_5V() ) ) + " V" );
			volate5Val.setTitle( Converter.formatDecimal( Double.valueOf( diagSystemHealthModel.getVoltage_5V() ) ) + " V" );
		}
		if ( diagSystemHealthModel.getVoltage_3_3V() != null )
		{
			volate3Val.setText( Converter.formatDecimal( Double.valueOf( diagSystemHealthModel.getVoltage_3_3V() ) ) + " V" );
			volate3Val.setTitle( Converter.formatDecimal( Double.valueOf( diagSystemHealthModel.getVoltage_3_3V() ) ) + " V" );
		}
		// data unavailable so commented
		//serialNoVal.setText( );
		//serialNoVal.setTitle( );

		String cpuUsagePer = Converter.formatDecimal( diagSystemHealthModel.getSystemStatusChartModel().getCpuUsage() ) + "%";
		String memUsagePer = Converter.formatDecimal( diagSystemHealthModel.getSystemStatusChartModel().getMemUsage() ) + "%";

		cpuProgressBar.setData( cpuUsagePer, cpuUsagePer, null, null, PROGRESS_BAR_HEIGHT );
		memUsageProgressBar.setData( memUsagePer, memUsagePer, null, null, PROGRESS_BAR_HEIGHT );

		if ( diagSystemHealthModel.getCPU1_temperature_current() != null && diagSystemHealthModel.getCPU1_temperature_max() != null )
		{
			double cpu1Temp = Double.valueOf( diagSystemHealthModel.getCPU1_temperature_current() );
			double cpu1TempMax = Double.valueOf( diagSystemHealthModel.getCPU1_temperature_max() );
			double cpu1TempPer = ( double ) ( ( cpu1Temp / cpu1TempMax ) * 100 );
			cpu1TempProgressBar.setData( cpu1TempPer + "%", "CPU 1 Temperature " + Converter.formatDecimal( cpu1Temp ) + CommonUtil.CELSIUS_SYMBOL, null, null, PROGRESS_BAR_HEIGHT );
		}

		if ( diagSystemHealthModel.getCPU2_temperature_current() != null && diagSystemHealthModel.getCPU2_temperature_max() != null )
		{
			double cpu2Temp = Double.valueOf( diagSystemHealthModel.getCPU2_temperature_current() );
			double cpu2TempMax = Double.valueOf( diagSystemHealthModel.getCPU2_temperature_max() );
			double cpu2TempPer = ( double ) ( ( cpu2Temp / cpu2TempMax ) * 100 );
			cpu2TempProgressBar.setData( cpu2TempPer + "%", "CPU 2 Temperature " + Converter.formatDecimal( cpu2Temp ) + CommonUtil.CELSIUS_SYMBOL, null, null, PROGRESS_BAR_HEIGHT );
		}

		fanStatusBodyRow.clear();
		if ( diagSystemHealthModel.getFans_fan1_speed() != null )
		{
			fanStatusBodyRow.add( getFanStatusCol( "Fan 1", diagSystemHealthModel.getFans_fan1_speed() ) );
		}
		if ( diagSystemHealthModel.getFans_fan2_speed() != null )
		{
			fanStatusBodyRow.add( getFanStatusCol( "Fan 2", diagSystemHealthModel.getFans_fan2_speed() ) );
		}
		if ( diagSystemHealthModel.getFans_fan3_speed() != null )
		{
			fanStatusBodyRow.add( getFanStatusCol( "Fan 3", diagSystemHealthModel.getFans_fan3_speed() ) );
		}
		if ( diagSystemHealthModel.getFans_fan4_speed() != null )
		{
			fanStatusBodyRow.add( getFanStatusCol( "Fan 4", diagSystemHealthModel.getFans_fan4_speed() ) );
		}
		if ( diagSystemHealthModel.getFans_fan5_speed() != null )
		{
			fanStatusBodyRow.add( getFanStatusCol( "Fan 5", diagSystemHealthModel.getFans_fan5_speed() ) );
		}
		if ( diagSystemHealthModel.getFans_fan6_speed() != null )
		{
			fanStatusBodyRow.add( getFanStatusCol( "Fan 6", diagSystemHealthModel.getFans_fan6_speed() ) );
		}
		if ( diagSystemHealthModel.getFans_fan7_speed() != null )
		{
			fanStatusBodyRow.add( getFanStatusCol( "Fan 7", diagSystemHealthModel.getFans_fan7_speed() ) );
		}
		if ( diagSystemHealthModel.getFans_fan8_speed() != null )
		{
			fanStatusBodyRow.add( getFanStatusCol( "Fan 8", diagSystemHealthModel.getFans_fan8_speed() ) );
		}
	}

}
