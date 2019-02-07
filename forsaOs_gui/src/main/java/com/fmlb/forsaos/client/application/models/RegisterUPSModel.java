package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class RegisterUPSModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String port;

	private String ip;

	public RegisterUPSModel()
	{
		super();
	}

	public RegisterUPSModel( String name, String port, String ip )
	{
		super();
		this.name = name;
		this.port = port;
		this.ip = ip;

	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort( String port )
	{
		this.port = port;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp( String ip )
	{
		this.ip = ip;
	}

}
