package com.fmlb.forsaos.client.application.vm.details;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.application.websocket.WebsocketHelper;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.exceptions.WebsocketEndpoints;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.fmlb.forsaos.shared.application.utility.VMState;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Widget;
import com.sksamuel.gwt.websockets.Websocket;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Flex;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class VMDetails extends MaterialPanel
{

	private VMModel vmModel;

	private UIComponentsUtil uiComponentsUtil;

	AppResources resources = GWT.create( AppResources.class );

	private CurrentUser currentUser;

	private Icommand navigateCmd;

	private VMDetailsSettings vmDetailsSettings;

	private VMDetailsPerformance vmDetailsPerformance;

	private VMDetailsConsole vmDetailsConsole;

	private MaterialRow tabContentRow;

	private Websocket vmStatsWebsocket;

	public VMDetails( VMModel vmModel, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, Icommand navigateCmd )
	{
		this.vmModel = vmModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.currentUser = currentUser;
		this.navigateCmd = navigateCmd;

		MaterialRow vm_power_row = new MaterialRow();
		vm_power_row.addStyleName( resources.style().nameEditIcon_row() );
		MaterialTextBox vmName = this.uiComponentsUtil.getDetailVmNameEdit( vmModel.getVmName() );
		/*vmName.setGrid( "s11" );*/
		vmName.addValidator( uiComponentsUtil.getTextBoxEmptyValidator() );
		vmName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		vmName.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				vmName.validate();
			}
		} );
		vmName.addValueChangeHandler( new ValueChangeHandler<String>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				if ( !vmName.validate() )
				{
					return;
				}
				LoggerUtil.log( "vm name cahnghed too" + event.getValue() );
				CommonServiceProvider.getCommonService().updateVMName( vmModel, event.getValue(), new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						if ( result )
						{
							MaterialToast.fireToast( "VM name updated to " + event.getValue() + "..!", "rounded" );
						}
						else
						{
							MaterialToast.fireToast( "Unable to update VM name..!", "rounded" );
						}
					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );
					};
				} );

			}
		} );

		vm_power_row.add( vmName );
		vm_power_row.add( getPowerButton() );

		add( vm_power_row );
		add( createTabPanel() );

		vmDetailsSettings = new VMDetailsSettings( this.vmModel, this.uiComponentsUtil, null, currentUser );
		vmDetailsSettings.setId( "Settings" );
		vmDetailsPerformance = new VMDetailsPerformance( this.vmModel, this.uiComponentsUtil );
		vmDetailsPerformance.setId( "Performance" );

		tabContentRow = new MaterialRow();
		tabContentRow.add( vmDetailsSettings );
		tabContentRow.add( vmDetailsPerformance );

		add( tabContentRow );

		//setWidgetToTabContentArea( "" );
	}

	private MaterialButton getPowerButton()
	{
		MaterialIcon powerButtonIcon = new MaterialIcon( IconType.POWER_SETTINGS_NEW );
		powerButtonIcon.setIconPosition( IconPosition.RIGHT );
		MaterialButton materialButton = new MaterialButton( "POWER", ButtonType.RAISED, powerButtonIcon );
		materialButton.setActivates( "vmDetailsActivator" );
		/*materialButton.setGrid( "s1" );*/
		materialButton.addStyleName( resources.style().vm_power_btn() );

		if ( vmModel.getStatus() == 2 )
		{
			materialButton.setText( "ON" );
			materialButton.setBackgroundColor( Color.GREEN );
			powerButtonIcon.setIconType( IconType.POWER_SETTINGS_NEW );
			powerButtonIcon.setIconColor( Color.WHITE );
		}
		else if ( vmModel.getStatus() == 3 )
		{
			materialButton.setText( "PAUSED" );
			materialButton.setBackgroundColor( Color.ORANGE_ACCENT_3 );
			powerButtonIcon.setIconType( IconType.PAUSE );
			powerButtonIcon.setIconColor( Color.WHITE );
		}
		else
		{
			materialButton.setText( "OFF" );
			materialButton.setBackgroundColor( Color.GREY );
			powerButtonIcon.setIconType( IconType.POWER_SETTINGS_NEW );
			powerButtonIcon.setIconColor( Color.WHITE );
		}

		MaterialDropDown vmSettingsDropDown = this.uiComponentsUtil.getVMSettingsDropDown( "vmDetailsActivator", false );
		materialButton.add( vmSettingsDropDown );

		this.uiComponentsUtil.enableDisablePowerIconMenus( vmModel, vmSettingsDropDown );

		vmSettingsDropDown.addSelectionHandler( new SelectionHandler<Widget>()
		{
			@Override
			public void onSelection( SelectionEvent<Widget> event )
			{
				onItemPowerIconClick( vmModel, ( ( MaterialLink ) event.getSelectedItem() ).getText(), materialButton, powerButtonIcon, vmSettingsDropDown );
			}
		} );

		return materialButton;
	}

	private void onItemPowerIconClick( VMModel vmModel, String selectedAction, MaterialButton materialButton, MaterialIcon powerButtonIcon, MaterialDropDown settingsItemLevelDropDown )
	{
		CommonServiceProvider.getCommonService().changeVMState( vmModel, VMState.getVMState( selectedAction ), new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialToast.fireToast( vmModel.getName() + "'s status updated to " + selectedAction );
				if ( selectedAction.equalsIgnoreCase( "start" ) || selectedAction.equalsIgnoreCase( "resume" ) )
				{
					materialButton.setText( "ON" );
					materialButton.setBackgroundColor( Color.GREEN );
					powerButtonIcon.setIconType( IconType.POWER_SETTINGS_NEW );
					powerButtonIcon.setIconColor( Color.WHITE );

					vmModel.setStatus( 2 );
				}
				else if ( selectedAction.equalsIgnoreCase( "pause" ) )
				{
					materialButton.setText( "PAUSED" );
					materialButton.setBackgroundColor( Color.ORANGE_ACCENT_3 );
					powerButtonIcon.setIconType( IconType.PAUSE );
					powerButtonIcon.setIconColor( Color.WHITE );

					vmModel.setStatus( 3 );
				}
				else
				{
					materialButton.setText( "OFF" );
					materialButton.setBackgroundColor( Color.GREY );
					powerButtonIcon.setIconType( IconType.POWER_SETTINGS_NEW );
					powerButtonIcon.setIconColor( Color.WHITE );

					vmModel.setStatus( 4 );
				}
				uiComponentsUtil.enableDisablePowerIconMenus( vmModel, settingsItemLevelDropDown );
			}

			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, "Unable to change status of " + vmModel.getName() + " to " + selectedAction );
			}
		} );
	}

	private MaterialTab createTabPanel()
	{
		MaterialTab vmConfTab = new MaterialTab();
		vmConfTab.setBackgroundColor( Color.WHITE );
		vmConfTab.addStyleName( resources.style().vm_tabs() );
		vmConfTab.setShadow( 1 );
		vmConfTab.setIndicatorColor( Color.TRANSPARENT );

		MaterialTabItem settingsTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		MaterialTabItem performanceTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );
		MaterialTabItem consoleTab = uiComponentsUtil.getTabItem( Flex.NONE, WavesType.LIGHT );

		MaterialLink settingsTabLink = uiComponentsUtil.getTabLink( "Settings", "Settings", Color.BLACK );
		MaterialLink performanceTabLink = uiComponentsUtil.getTabLink( "Performance", "Performance", Color.BLACK );
		MaterialLink consoleTabLink = uiComponentsUtil.getTabLink( "Console", "Console", Color.BLACK );

		settingsTab.add( settingsTabLink );
		performanceTab.add( performanceTabLink );
		consoleTab.add( consoleTabLink );

		// settingsTab.setWaves( WavesType.fromStyleName(
		// resources.style().vm_tab_waves() ) );
		settingsTab.setBackgroundColor( Color.BLUE );// by default selected
		settingsTabLink.setTextColor( Color.WHITE );
		settingsTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				settingsTab.setBackgroundColor( Color.BLUE );
				settingsTabLink.setTextColor( Color.WHITE );

				performanceTab.setBackgroundColor( Color.WHITE );
				performanceTabLink.setTextColor( Color.BLACK );

				consoleTab.setBackgroundColor( Color.WHITE );
				consoleTabLink.setTextColor( Color.BLACK );

				if ( vmDetailsConsole != null )
				{
					vmDetailsConsole.clearPanel();
					vmDetailsConsole.clear();
					tabContentRow.remove( vmDetailsConsole );
				}

			}
		} );

		performanceTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				performanceTab.setBackgroundColor( Color.BLUE );
				performanceTabLink.setTextColor( Color.WHITE );

				settingsTab.setBackgroundColor( Color.WHITE );
				settingsTabLink.setTextColor( Color.BLACK );

				consoleTab.setBackgroundColor( Color.WHITE );
				consoleTabLink.setTextColor( Color.BLACK );

				if ( vmDetailsConsole != null )
				{
					vmDetailsConsole.clearPanel();
					vmDetailsConsole.clear();
					tabContentRow.remove( vmDetailsConsole );
				}
			}
		} );

		consoleTab.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				consoleTab.setBackgroundColor( Color.BLUE );
				consoleTabLink.setTextColor( Color.WHITE );

				settingsTab.setBackgroundColor( Color.WHITE );
				settingsTabLink.setTextColor( Color.BLACK );

				performanceTab.setBackgroundColor( Color.WHITE );
				performanceTabLink.setTextColor( Color.BLACK );

				createConsolePanel();
			}
		} );

		vmConfTab.add( settingsTab );
		vmConfTab.add( performanceTab );
		vmConfTab.add( consoleTab );

		return vmConfTab;
	}

	private void createConsolePanel()
	{
		if ( vmDetailsConsole != null )
		{
			vmDetailsConsole.clearPanel();
			vmDetailsConsole.clear();
			tabContentRow.remove( vmDetailsConsole );
		}
		vmDetailsConsole = new VMDetailsConsole( vmModel, uiComponentsUtil, currentUser );
		vmDetailsConsole.setId( "Console" );
		tabContentRow.add( vmDetailsConsole );
	}

	public void initializevmStatsSWS()
	{
		LoggerUtil.log( "TRYING TO Initializing vmStatsWebsocket WEB SOCKET" );
		if ( vmStatsWebsocket == null )
		{
			LoggerUtil.log( "Initializing vmStatsWebsocket WEB SOCKET AS IT IS NULL" );
			vmStatsWebsocket = WebsocketHelper.getWebsocketInstance( WebsocketEndpoints.VM_STATS_END_POINT, new IcommandWithData()
			{
				@Override
				public void executeWithData( Object obj )
				{
					onVMStatsDataReceived( ( JSONValue ) obj );
				}
			} );
		}
		else if ( vmStatsWebsocket.getState() == 3 )
		{
			LoggerUtil.log( "Reopening the vmStatsWebsocket  WEB SOCKET AS IT was closed" );
			vmStatsWebsocket.open();
		}
	}

	public Websocket getVmStatsWebsocket()
	{
		return vmStatsWebsocket;
	}

	private void onVMStatsDataReceived( JSONValue parsedData )
	{

		JSONObject object = parsedData.isObject();
		LoggerUtil.log( "VM Stats data " + object.toString() );

		LoggerUtil.log( "VM Stats data 1 " );

		long timePoint = ( long ) RestCallUtil.getRawDouble( object, "timestamp" );
		JSONNumber timepointNumber = RestCallUtil.getJSONNumber( timePoint * 1000 );

		LoggerUtil.log( "VM Stats data 2 " );

		// Adding try catch block- If one chart update fails it should not impact others
		try
		{
			updateCPUUsageChart( object, timepointNumber );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		try
		{
			updateCPUReadWriteIOChart( object, timepointNumber );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			updateVMReadWriteLatencyChartData( object, timepointNumber );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			updateVMMemoryChartData( object, timepointNumber );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			updateVMReadWriteBandwidthChartData( object, timepointNumber );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

	}

	private void updateCPUUsageChart( JSONObject object, JSONNumber timepointNumber )
	{
		JSONObject cpuUsageDataPoint = new JSONObject();
		cpuUsageDataPoint.put( "timestamp", timepointNumber );
		LoggerUtil.log( "VM Stats data 3 " );

		double cpuPer = RestCallUtil.getRawDouble( object, "cpu_percent" );
		LoggerUtil.log( "VM Stats data 4 " );

		JSONNumber cpuPerFormatted = RestCallUtil.getJSONNumber( Double.valueOf( NumberFormat.getFormat( ".##" ).format( cpuPer ) ) );
		LoggerUtil.log( "VM Stats data 5 " );

		cpuUsageDataPoint.put( "cpu_percent", cpuPerFormatted );

		LoggerUtil.log( "VM Stats data 6 " );
		vmDetailsPerformance.updateCPUUsageChartData( cpuUsageDataPoint );
		LoggerUtil.log( "VM Stats data 7 " );
	}

	private void updateCPUReadWriteIOChart( JSONObject object, JSONNumber timepointNumber )
	{
		JSONObject readWriteIODataPoint = new JSONObject();

		readWriteIODataPoint.put( "timestamp", timepointNumber );
		LoggerUtil.log( "updateCPUReadWroiteIOChart 1" );

		readWriteIODataPoint.put( "rd_iops", object.get( "rd_iops" ) );
		LoggerUtil.log( "updateCPUReadWroiteIOChart 2" );

		readWriteIODataPoint.put( "wr_iops", object.get( "wr_iops" ) );
		LoggerUtil.log( "updateCPUReadWroiteIOChart 3" );

		vmDetailsPerformance.updateReadWriteIOChartData( readWriteIODataPoint );
		LoggerUtil.log( "updateCPUReadWroiteIOChart 4" );
	}

	private void updateVMReadWriteLatencyChartData( JSONObject object, JSONNumber timepointNumber )
	{
		JSONObject readWriteLatencyDataPoint = new JSONObject();

		readWriteLatencyDataPoint.put( "timestamp", timepointNumber );
		LoggerUtil.log( "updateVMReadWriteLatencyChartData 1" );

		readWriteLatencyDataPoint.put( "rd_latency_nsec", object.get( "rd_latency_nsec" ) );
		LoggerUtil.log( "updateVMReadWriteLatencyChartData 2" );

		readWriteLatencyDataPoint.put( "wr_latency_nsec", object.get( "wr_latency_nsec" ) );
		LoggerUtil.log( "updateVMReadWriteLatencyChartData 3" );

		vmDetailsPerformance.updateVMReadWriteLatencyChartData( readWriteLatencyDataPoint );
		LoggerUtil.log( "updateVMReadWriteLatencyChartData 4" );
	}

	private void updateVMMemoryChartData( JSONObject object, JSONNumber timepointNumber )
	{
		JSONObject memoryUsageDataPoint = new JSONObject();

		memoryUsageDataPoint.put( "timestamp", timepointNumber );
		LoggerUtil.log( "updateVMMemoryChartData 1" );

		long usedMemkb = ( long ) RestCallUtil.getRawDouble( object, "mem_rss_kb" );
		LoggerUtil.log( "updateVMMemoryChartData 2" );

		JSONNumber usedMemBytes = RestCallUtil.getJSONNumber( usedMemkb * 1024 );
		LoggerUtil.log( "updateVMMemoryChartData 3" );

		memoryUsageDataPoint.put( "mem_rss_kb", usedMemBytes );
		LoggerUtil.log( "updateVMMemoryChartData 4" );

		vmDetailsPerformance.updateVMMemoryChartData( memoryUsageDataPoint );
		LoggerUtil.log( "updateVMMemoryChartData 5" );

	}

	private void updateVMReadWriteBandwidthChartData( JSONObject object, JSONNumber timepointNumber )
	{
		JSONObject memoryUsageDataPoint = new JSONObject();

		memoryUsageDataPoint.put( "timestamp", timepointNumber );
		LoggerUtil.log( "updateVMReadWriteBandwidthChartData 1" );

		long readBwKb = ( long ) RestCallUtil.getRawDouble( object, "rd_bandwidth_kb_s" );
		LoggerUtil.log( "updateVMReadWriteBandwidthChartData 2" );

		long writeBwKb = ( long ) RestCallUtil.getRawDouble( object, "wr_bandwidth_kb_s" );
		LoggerUtil.log( "updateVMReadWriteBandwidthChartData 2" );

		JSONNumber readBwBytes = RestCallUtil.getJSONNumber( readBwKb * 1024 );
		LoggerUtil.log( "updateVMReadWriteBandwidthChartData 3" );

		JSONNumber writeBwBytes = RestCallUtil.getJSONNumber( writeBwKb * 1024 );
		LoggerUtil.log( "updateVMReadWriteBandwidthChartData 4" );

		memoryUsageDataPoint.put( "rd_bandwidth_kb_s", readBwBytes );
		LoggerUtil.log( "updateVMReadWriteBandwidthChartData 5" );

		memoryUsageDataPoint.put( "wr_bandwidth_kb_s", writeBwBytes );
		LoggerUtil.log( "updateVMReadWriteBandwidthChartData 6" );

		vmDetailsPerformance.updateVMReadWriteBandwidthChartData( memoryUsageDataPoint );
		LoggerUtil.log( "updateVMReadWriteBandwidthChartData 7" );

	}

}
