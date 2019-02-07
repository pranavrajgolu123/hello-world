package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class LEMToVMJSONModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	public LEMToVMJSONModel()
	{
		super();
	}

	public LEMToVMJSONModel( String id, String name )
	{
		super();
		this.id = id;
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

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

}
