package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UpdateHostNameModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String hostname;

	public UpdateHostNameModel()
	{
		super();
	}

	public UpdateHostNameModel( String hostname )
	{
		super();
		this.hostname = hostname;
	}

	public String getHostname()
	{
		return hostname;
	}

	public void setHostname( String hostname )
	{
		this.hostname = hostname;
	}

}
