package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UPSDetailsModel implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Integer temp;

	private long output_power;

	private long runtime_remaining;

	private String state = "";

	private Integer battery_charge;

	private String battery_life = "";

	private String message = "";

	private Boolean battery_need_replacement = false;

	private String status_modifier = "";

	public UPSDetailsModel()
	{
		super();
	}

	public UPSDetailsModel( Integer temp, long output_power, long runtime_remaining, String state, Integer battery_charge, String battery_life, String message, Boolean battery_need_replacement, String status_modifier )
	{
		super();
		this.battery_charge = battery_charge;
		this.battery_life = battery_life;
		this.battery_need_replacement = battery_need_replacement;
		this.message = message;
		this.output_power = output_power;
		this.runtime_remaining = runtime_remaining;
		this.state = state;
		this.status_modifier = status_modifier;
		this.temp = temp;

	}

	public Integer getTemp()
	{
		return temp;
	}

	public void setTemp( Integer temp )
	{
		this.temp = temp;
	}

	public long getOutput_power()
	{
		return output_power;
	}

	public void setOutput_power( long output_power )
	{
		this.output_power = output_power;
	}

	public long getRuntime_remaining()
	{
		return runtime_remaining;
	}

	public void setRuntime_remaining( long runtime_remaining )
	{
		this.runtime_remaining = runtime_remaining;
	}

	public String getState()
	{
		return state;
	}

	public void setState( String state )
	{
		this.state = state;
	}

	public Integer getBattery_charge()
	{
		return battery_charge;
	}

	public void setBattery_charge( Integer battery_charge )
	{
		this.battery_charge = battery_charge;
	}

	public String getBattery_life()
	{
		return battery_life;
	}

	public void setBattery_life( String battery_life )
	{
		this.battery_life = battery_life;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage( String message )
	{
		this.message = message;
	}

	public Boolean getBattery_need_replacement()
	{
		return battery_need_replacement;
	}

	public void setBattery_need_replacement( Boolean battery_need_replacement )
	{
		this.battery_need_replacement = battery_need_replacement;
	}

	public String getStatus_modifier()
	{
		return status_modifier;
	}

	public void setStatus_modifier( String status_modifier )
	{
		this.status_modifier = status_modifier;
	}

}
