package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UpdateTimeZoneModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String timezone;

	public UpdateTimeZoneModel()
	{
		super();
	}

	public UpdateTimeZoneModel( String timezone )
	{
		super();
		this.timezone = timezone;
	}

	public String getTimezone()
	{
		return timezone;
	}

	public void setTimezone( String timezone )
	{
		this.timezone = timezone;
	}

}
