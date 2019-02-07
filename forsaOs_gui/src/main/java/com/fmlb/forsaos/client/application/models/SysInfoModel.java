package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class SysInfoModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String hostname;

	private String timezone;

	private String version;

	public SysInfoModel()
	{
		super();
	}

	public SysInfoModel( String hostname, String timezone )
	{
		super();
		this.hostname = hostname;
		this.timezone = timezone;
	}

	public String getHostname()
	{
		return hostname;
	}

	public void setHostname( String hostname )
	{
		this.hostname = hostname;
	}

	public String getTimezone()
	{
		return timezone;
	}

	public void setTimezone( String timezone )
	{
		this.timezone = timezone;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion( String version )
	{
		this.version = version;
	}

}
