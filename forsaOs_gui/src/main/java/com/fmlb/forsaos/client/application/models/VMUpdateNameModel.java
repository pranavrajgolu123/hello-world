package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class VMUpdateNameModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String id;
	
	private String new_name;
	
	
	public VMUpdateNameModel()
	{
		super();
	}

	public VMUpdateNameModel( String name, String id, String new_name )
	{
		super();
		this.name = name;
		this.id = id;
		this.new_name = new_name;
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
