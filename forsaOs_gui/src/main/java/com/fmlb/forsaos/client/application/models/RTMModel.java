package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class RTMModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;

	public RTMModel()
	{
		super();
	}

	public RTMModel( String name )
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
