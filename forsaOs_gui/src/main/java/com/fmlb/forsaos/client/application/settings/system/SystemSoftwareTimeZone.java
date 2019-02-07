package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.UpdateTimeZoneModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.DateUtility;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.html.Option;

public class SystemSoftwareTimeZone extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialPanel timezonePanel;

	private MaterialButton updateTimezonebtn;

	private MaterialComboBox<Option> timezoneModelsDropDown;

	private MaterialLabel timeZoneLbl;

	private IcommandWithData updateCurrentSystemTimeCmd;

	AppResources resources = GWT.create( AppResources.class );

	public SystemSoftwareTimeZone( UIComponentsUtil uiComponentsUtil, IcommandWithData updateCurrentSystemTimeCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateCurrentSystemTimeCmd = updateCurrentSystemTimeCmd;
		createSystemTimeZonePanel();
		add( timezonePanel );

	}

	private void createSystemTimeZonePanel()
	{
		timezonePanel = new MaterialPanel();
		timeZoneLbl = this.uiComponentsUtil.getLabel( "System Time Zone", "s12", resources.style().vm_setting_header() );

		timezonePanel.add( timeZoneLbl );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( "system_timezone_row" );

		timezoneModelsDropDown = ( MaterialComboBox<Option> ) uiComponentsUtil.getComboBox( DateUtility.getTimeZonesMap(), "Select Timezone", "s6" );

		firstRow.add( timezoneModelsDropDown );

		updateTimezonebtn = new MaterialButton( "Update Time Zone" );
		updateTimezonebtn.setWaves( WavesType.DEFAULT );
		updateTimezonebtn.setTitle( "Update Time Zone" );
		updateTimezonebtn.setMarginTop( 19 );

		updateTimezonebtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				if ( validate() )
				{
					updateTimeZone();
				}
			}
		} );

		firstRow.add( updateTimezonebtn );
		timezonePanel.add( firstRow );

	}

	private void updateTimeZone()
	{
		MaterialLoader.loading( true );
		UpdateTimeZoneModel updateTimeZoneModel = new UpdateTimeZoneModel();
		LoggerUtil.log( "timezone------------------------------------" + timezoneModelsDropDown.getValue() );
		LoggerUtil.log( "timezone------------------------------------" + timezoneModelsDropDown.getValue().get( 0 ) );
		//	LoggerUtil.log( "timezone------------------------------------" + timezoneModelsDropDown.getValue().get( 0 ).getText());
		//	LoggerUtil.log( "timezone------------------------------------" + timezoneModelsDropDown.getValue().get( 0 ).getValue());
		String selectedTimeZone = String.valueOf( timezoneModelsDropDown.getValue().get( 0 ) );
		updateTimeZoneModel.setTimezone( selectedTimeZone );
		CommonServiceProvider.getCommonService().updateTimeZone( updateTimeZoneModel, new ApplicationCallBack<Boolean>()
		{

			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				if ( updateCurrentSystemTimeCmd != null )
				{
					updateCurrentSystemTimeCmd.executeWithData( false );
				}

			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );

			}
		} );
	}

	public MaterialLabel getTimeZoneLbl()
	{
		return timeZoneLbl;
	}

	public MaterialComboBox<Option> getTimezoneModelsDropDown()
	{
		return timezoneModelsDropDown;
	}

	public boolean validate()
	{
		boolean valid = false;
		if ( timezoneModelsDropDown.validate() )
		{
			valid = true;
		}
		else
		{
			timezoneModelsDropDown.validate();
		}
		return valid;

	}

}
