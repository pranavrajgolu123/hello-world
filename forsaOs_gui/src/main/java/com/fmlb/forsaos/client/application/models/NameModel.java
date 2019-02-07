package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class NameModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public NameModel()
	{
		super();
	}

	public NameModel( String name )
	{
		super();
		this.name = name;
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
