package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DiagNVMESmartLogModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int critical_warning;

	private int temperature;

	private int avail_spare;

	private int spare_thresh;

	private int percent_used;

	private int controller_busy_time;

	private int power_cycles;

	private int power_on_hours;

	private int unsafe_shutdowns;

	private int media_errors;

	private int num_err_log_entries;

	private int warning_temp_time;

	private int critical_comp_time;

	private int temperature_sensor_1;

	private int temperature_sensor_2;

	private int thm_temp1_trans_count;

	private int thm_temp2_trans_count;

	private int thm_temp1_total_time;

	private int thm_temp2_total_time;

	private long data_units_read;

	private long data_units_written;

	private long host_read_commands;

	private long host_write_commands;

	public int getCritical_warning()
	{
		return critical_warning;
	}

	public void setCritical_warning( int critical_warning )
	{
		this.critical_warning = critical_warning;
	}

	public int getTemperature()
	{
		return temperature;
	}

	public void setTemperature( int temperature )
	{
		this.temperature = temperature;
	}

	public int getAvail_spare()
	{
		return avail_spare;
	}

	public void setAvail_spare( int avail_spare )
	{
		this.avail_spare = avail_spare;
	}

	public int getSpare_thresh()
	{
		return spare_thresh;
	}

	public void setSpare_thresh( int spare_thresh )
	{
		this.spare_thresh = spare_thresh;
	}

	public int getPercent_used()
	{
		return percent_used;
	}

	public void setPercent_used( int percent_used )
	{
		this.percent_used = percent_used;
	}

	public int getController_busy_time()
	{
		return controller_busy_time;
	}

	public void setController_busy_time( int controller_busy_time )
	{
		this.controller_busy_time = controller_busy_time;
	}

	public int getPower_cycles()
	{
		return power_cycles;
	}

	public void setPower_cycles( int power_cycles )
	{
		this.power_cycles = power_cycles;
	}

	public int getPower_on_hours()
	{
		return power_on_hours;
	}

	public void setPower_on_hours( int power_on_hours )
	{
		this.power_on_hours = power_on_hours;
	}

	public int getUnsafe_shutdowns()
	{
		return unsafe_shutdowns;
	}

	public void setUnsafe_shutdowns( int unsafe_shutdowns )
	{
		this.unsafe_shutdowns = unsafe_shutdowns;
	}

	public int getMedia_errors()
	{
		return media_errors;
	}

	public void setMedia_errors( int media_errors )
	{
		this.media_errors = media_errors;
	}

	public int getNum_err_log_entries()
	{
		return num_err_log_entries;
	}

	public void setNum_err_log_entries( int num_err_log_entries )
	{
		this.num_err_log_entries = num_err_log_entries;
	}

	public int getWarning_temp_time()
	{
		return warning_temp_time;
	}

	public void setWarning_temp_time( int warning_temp_time )
	{
		this.warning_temp_time = warning_temp_time;
	}

	public int getCritical_comp_time()
	{
		return critical_comp_time;
	}

	public void setCritical_comp_time( int critical_comp_time )
	{
		this.critical_comp_time = critical_comp_time;
	}

	public int getTemperature_sensor_1()
	{
		return temperature_sensor_1;
	}

	public void setTemperature_sensor_1( int temperature_sensor_1 )
	{
		this.temperature_sensor_1 = temperature_sensor_1;
	}

	public int getTemperature_sensor_2()
	{
		return temperature_sensor_2;
	}

	public void setTemperature_sensor_2( int temperature_sensor_2 )
	{
		this.temperature_sensor_2 = temperature_sensor_2;
	}

	public int getThm_temp1_trans_count()
	{
		return thm_temp1_trans_count;
	}

	public void setThm_temp1_trans_count( int thm_temp1_trans_count )
	{
		this.thm_temp1_trans_count = thm_temp1_trans_count;
	}

	public int getThm_temp2_trans_count()
	{
		return thm_temp2_trans_count;
	}

	public void setThm_temp2_trans_count( int thm_temp2_trans_count )
	{
		this.thm_temp2_trans_count = thm_temp2_trans_count;
	}

	public int getThm_temp1_total_time()
	{
		return thm_temp1_total_time;
	}

	public void setThm_temp1_total_time( int thm_temp1_total_time )
	{
		this.thm_temp1_total_time = thm_temp1_total_time;
	}

	public int getThm_temp2_total_time()
	{
		return thm_temp2_total_time;
	}

	public void setThm_temp2_total_time( int thm_temp2_total_time )
	{
		this.thm_temp2_total_time = thm_temp2_total_time;
	}

	public long getData_units_read()
	{
		return data_units_read;
	}

	public void setData_units_read( long data_units_read )
	{
		this.data_units_read = data_units_read;
	}

	public long getData_units_written()
	{
		return data_units_written;
	}

	public void setData_units_written( long data_units_written )
	{
		this.data_units_written = data_units_written;
	}

	public long getHost_read_commands()
	{
		return host_read_commands;
	}

	public void setHost_read_commands( long host_read_commands )
	{
		this.host_read_commands = host_read_commands;
	}

	public long getHost_write_commands()
	{
		return host_write_commands;
	}

	public void setHost_write_commands( long host_write_commands )
	{
		this.host_write_commands = host_write_commands;
	}

}
