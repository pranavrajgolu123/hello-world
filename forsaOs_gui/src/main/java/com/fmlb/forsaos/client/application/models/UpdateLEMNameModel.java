package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UpdateLEMNameModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String new_name;

	private String id;

	public UpdateLEMNameModel()
	{
		super();
	}

	public UpdateLEMNameModel( String name, String new_name, String id )
	{
		super();
		this.name = name;
		this.new_name = new_name;
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getNew_name()
	{
		return new_name;
	}

	public void setNew_name( String new_name )
	{
		this.new_name = new_name;
	}
}
