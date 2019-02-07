package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UPSModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name = "";

	private String port = "";

	private String id = "";

	private String ip = "";

	public UPSModel()
	{
		super();
	}

	public UPSModel( String port, String name, String id, String ip )
	{
		super();
		this.port = port;
		this.name = name;
		this.id = id;
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

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
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
