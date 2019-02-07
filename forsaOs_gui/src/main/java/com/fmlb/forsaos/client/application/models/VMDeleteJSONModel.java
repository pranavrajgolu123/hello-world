package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class VMDeleteJSONModel implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	public VMDeleteJSONModel()
	{
		super();
	}

	public VMDeleteJSONModel( String id, String name )
	{
		super();
		this.id = id;
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

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

}
