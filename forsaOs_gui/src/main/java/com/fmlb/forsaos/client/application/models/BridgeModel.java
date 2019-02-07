package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class BridgeModel implements Serializable, ComboBoxModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String port;

	private String id;

	public BridgeModel()
	{
		super();
	}

	public BridgeModel( String name, String port )
	{
		super();
		this.name = name;
		this.port = port;
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

	@Override
	public String getDisplayName()
	{
		// TODO Auto-generated method stub
		return this.name;
	}

}
