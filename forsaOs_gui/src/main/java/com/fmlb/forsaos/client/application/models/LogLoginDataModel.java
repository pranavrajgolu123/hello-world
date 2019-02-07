package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class LogLoginDataModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	public LogLoginDataModel()
	{
		super();
	}

	public LogLoginDataModel( String username )
	{
		super();
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername( String username )
	{
		this.username = username;
	}

}
