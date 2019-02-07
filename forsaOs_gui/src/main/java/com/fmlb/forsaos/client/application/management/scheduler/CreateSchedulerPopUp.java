package com.fmlb.forsaos.client.application.management.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.SchedulerBlinkModel;
import com.fmlb.forsaos.client.application.models.SchedulerModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.utility.ResourceServiceEndpoints;
import com.fmlb.forsaos.shared.application.utility.ServiceTypes;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

@SuppressWarnings( "unchecked" )
public class CreateSchedulerPopUp extends CommonPopUp
{
	private UIComponentsUtil uiComponentsUtil;

	private IcommandWithData updateSchedulerTableCmd;

	private MaterialTextBox schedulerName;

	private MaterialIntegerBox intervalIntegerBox;

	private MaterialIntegerBox hourIntegerBox;

	private MaterialIntegerBox minIntegerBox;

	private MaterialIntegerBox secIntegerBox;

	private MaterialIntegerBox maxcountIntegerBox;

	private MaterialComboBox<String> scheduleType;

	private MaterialComboBox<String> intervalType;

	private MaterialComboBox<String> partitionsComboBox;

	private List<String> partitions = new ArrayList<>();

	private MaterialRow dateTimeRow;

	private MaterialRow createschedulerRow;

	private MaterialRow blinkdata;

	private MaterialDatePicker datePicker;

	private CurrentUser currentUser;

	private String ip = "127.0.0.1";

	private int month;

	private int year;

	private int day;

	private Integer repeat_hour;

	private Integer repeat_day;

	AppResources resources = GWT.create( AppResources.class );

	public CreateSchedulerPopUp( UIComponentsUtil uiComponentsUtil, IcommandWithData updateSchedulerTableCmd, CurrentUser currentUser, List<String> result )
	{
		super( "Create Scheduler", "", "Create Scheduler", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateSchedulerTableCmd = updateSchedulerTableCmd;
		this.currentUser = currentUser;
		this.partitions = result;
		LoggerUtil.log( "initialize create schedule" );
		scheduleType();
		blinkDetail();
		intervalType();
		timeDataRow();
		initialize();
		buttonAction();
		getElement().getStyle().setOverflow( Overflow.VISIBLE );
	}

	private void initialize()
	{
		createschedulerRow = new MaterialRow();

		schedulerName = uiComponentsUtil.getTexBox( "Scheduler Name", "Type scheduler Name", "s6" );
		schedulerName.addValidator( uiComponentsUtil.getInvalidCharacterValidator() );
		schedulerName.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				schedulerName.validate();
			}
		} );
		schedulerName.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				schedulerName.validate();
			}
		} );

		intervalIntegerBox = uiComponentsUtil.getIntegerBox( "Interval", "", "s3" );
		intervalIntegerBox.setMin( "1" );

		maxcountIntegerBox = uiComponentsUtil.getIntegerBox( "Max Count", "", "s6" );
		maxcountIntegerBox.setMin( "1" );
		maxcountIntegerBox.setValue( 1 );
		maxcountIntegerBox.setMax( "1" );
		maxcountIntegerBox.setAutoValidate( true );

		createschedulerRow.add( schedulerName );
		createschedulerRow.add( scheduleType );
		createschedulerRow.add( blinkdata );
		blinkdata.setVisible( false );
		createschedulerRow.add( intervalIntegerBox );
		createschedulerRow.add( intervalType );
		createschedulerRow.add( dateTimeRow );
		createschedulerRow.add( maxcountIntegerBox );

		getBodyPanel().add( createschedulerRow );
	}

	private void scheduleType()
	{
		HashMap<String, String> scheduleTypeOptionMap = new HashMap<String, String>();
		scheduleTypeOptionMap.put( "Select", "Select" );
		scheduleTypeOptionMap.put( "Blink", "Blink" );

		scheduleType = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( scheduleTypeOptionMap, "Schedule Type", "s6" );
		scheduleType.addValueChangeHandler( new ValueChangeHandler<List<String>>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<List<String>> event )
			{
				validateScheduleType();
				if ( event.getValue().get( 0 ).equalsIgnoreCase( "Select" ) )
				{
					blinkdata.setVisible( false );
				}
				if ( event.getValue().get( 0 ).equalsIgnoreCase( "Blink" ) )
				{
					blinkdata.setVisible( true );
				}
			}
		} );
	}

	private void intervalType()
	{
		HashMap<String, String> scheduleTypeOptionMap = new HashMap<String, String>();
		scheduleTypeOptionMap.put( "HOUR", "HOUR" );
		scheduleTypeOptionMap.put( "DAY", "DAY" );

		intervalType = ( MaterialComboBox<String> ) uiComponentsUtil.getComboBox( scheduleTypeOptionMap, "", "s3" );
	}

	@SuppressWarnings( "deprecation" )
	private void timeDataRow()
	{
		dateTimeRow = new MaterialRow();
		datePicker = new MaterialDatePicker( "Date" );
		datePicker.setAutoClose( true );
		datePicker.setGrid( "s3" );
		datePicker.addStyleName( "intervalDateRow intervalDatePenal" );

		Date today = new Date();
		datePicker.setDate( today );
		day = today.getDate();
		month = today.getMonth();
		month = month + 1;
		year = today.getYear();
		year = year + 1900;

		datePicker.addCloseHandler( new CloseHandler<MaterialDatePicker>()
		{
			@Override
			public void onClose( CloseEvent<MaterialDatePicker> event )
			{
				day = datePicker.getDate().getDate();
				month = datePicker.getDate().getMonth();
				month = month + 1;
				year = datePicker.getDate().getYear();
				year = year + 1900;
			}
		} );

		hourIntegerBox = uiComponentsUtil.getIntegerBoxIncludeZero( "Hour", "", "s1" );
		hourIntegerBox.setMin( "0" );
		hourIntegerBox.setValue( 0 );
		hourIntegerBox.setMax( "23" );
		hourIntegerBox.addStyleName( "intervalDateRow" );

		minIntegerBox = uiComponentsUtil.getIntegerBoxIncludeZero( "Min", "", "s1" );
		minIntegerBox.setMin( "0" );
		minIntegerBox.setValue( 0 );
		minIntegerBox.setMax( "59" );
		minIntegerBox.addStyleName( "intervalDateRow" );

		secIntegerBox = uiComponentsUtil.getIntegerBoxIncludeZero( "Sec", "", "s1" );
		secIntegerBox.setMin( "0" );
		secIntegerBox.setValue( 0 );
		secIntegerBox.setMax( "59" );
		secIntegerBox.addStyleName( "intervalDateRow" );

		dateTimeRow.add( datePicker );
		dateTimeRow.add( hourIntegerBox );
		dateTimeRow.add( minIntegerBox );
		dateTimeRow.add( secIntegerBox );

		dateTimeRow.addStyleName( "statusRow" );
	}

	private MaterialRow blinkDetail()
	{
		blinkdata = new MaterialRow();
		blinkdata.setGrid( "s12" );
		blinkdata.addStyleName( "schedulerBlinkRow" );

		HashMap<String, String> partitionsMap = new HashMap<String, String>();
		if ( partitions != null && !partitions.isEmpty() )
		{
			for ( String partition : partitions )
			{
				partitionsMap.put( partition, partition );
			}
		}
		partitionsComboBox = ( MaterialComboBox<String> ) this.uiComponentsUtil.getComboBox( partitionsMap, "Select Partition", "s6" );
		if ( partitions == null || partitions.isEmpty() )
		{
			partitionsComboBox.setErrorText( "No blink partition found." );
		}

		blinkdata.add( partitionsComboBox );

		return blinkdata;
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				if ( validate() )
				{
					createCurrentSchedule();
				}
			}
		} );
	}

	@Override
	public boolean validate()
	{
		boolean isValid = false;
		if ( schedulerName.validate() && validateScheduleType() && validatePartitions() && hourIntegerBox.validate() && minIntegerBox.validate() && secIntegerBox.validate() && maxcountIntegerBox.validate() )
		{
			isValid = true;
		}
		return isValid;
	}

	private boolean validatePartitions()
	{
		boolean isValid = true;
		if ( partitions == null || partitions.isEmpty() )
		{
			isValid = false;
		}
		return isValid;
	}

	public boolean validateScheduleType()
	{
		boolean isValid = false;
		if ( !scheduleType.getValue().get( 0 ).equalsIgnoreCase( "Select" ) )
		{
			isValid = true;
			scheduleType.clearErrorText();
			scheduleType.setSuccessText( "" );
		}
		else
		{
			scheduleType.setErrorText( "Please select scheduler type." );
		}
		return isValid;
	}

	private void createCurrentSchedule()
	{
		if ( intervalIntegerBox.getValue() != null && intervalType.getValue() != null && intervalIntegerBox.getValue() > 0 )
		{
			String repeatType = intervalType.getValue().get( 0 );
			if ( repeatType.equalsIgnoreCase( "HOUR" ) )
			{
				intervalIntegerBox.clearErrorText();
				repeat_hour = intervalIntegerBox.getValue();
			}
			if ( repeatType.equalsIgnoreCase( "DAY" ) )
			{
				intervalIntegerBox.clearErrorText();
				repeat_day = intervalIntegerBox.getValue();
			}
		}

		SchedulerBlinkModel blinkdata = new SchedulerBlinkModel( schedulerName.getValue(), partitionsComboBox.getValue().get( 0 ), Long.valueOf( maxcountIntegerBox.getValue() ), "schedule" );

		SchedulerModel createSchedulerModel = new SchedulerModel( schedulerName.getValue().toString(), Long.valueOf( hourIntegerBox.getValue().toString() ), Long.valueOf( minIntegerBox.getValue().toString() ), ip, ResourceServiceEndpoints.BLINK_NEW_CREATE.getPath(), month, ServiceTypes.Management.getPortNumber() + "", Long.valueOf( secIntegerBox.getValue().toString() ), year, blinkdata, day );
		createSchedulerModel.setRepeat_hour( repeat_hour );
		createSchedulerModel.setRepeat_day( repeat_day );

		MaterialLoader.loading( true, getBodyPanel() );
		CommonServiceProvider.getCommonService().createScheduler( createSchedulerModel, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( createSchedulerModel.getName() + " Scheduler Created..!", "rounded" );
				if ( updateSchedulerTableCmd != null )
				{
					updateSchedulerTableCmd.executeWithData( true );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}
}
