package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DiagUPSStatusModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int temp;

	private int output_power;

	private int runtime_remaining;

	private String state;

	private boolean battery_need_replacement;

	private String battery_life;

	private int battery_charge;

	public int getTemp()
	{
		return temp;
	}

	public void setTemp( int temp )
	{
		this.temp = temp;
	}

	public int getOutput_power()
	{
		return output_power;
	}

	public void setOutput_power( int output_power )
	{
		this.output_power = output_power;
	}

	public int getRuntime_remaining()
	{
		return runtime_remaining;
	}

	public void setRuntime_remaining( int runtime_remaining )
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

	public boolean isBattery_need_replacement()
	{
		return battery_need_replacement;
	}

	public void setBattery_need_replacement( boolean battery_need_replacement )
	{
		this.battery_need_replacement = battery_need_replacement;
	}

	public String getBattery_life()
	{
		return battery_life;
	}

	public void setBattery_life( String battery_life )
	{
		this.battery_life = battery_life;
	}

	public int getBattery_charge()
	{
		return battery_charge;
	}

	public void setBattery_charge( int battery_charge )
	{
		this.battery_charge = battery_charge;
	}
}
