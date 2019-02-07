package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UserDeleteModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	// private String id;

	private String name;

	public UserDeleteModel()
	{
		// TODO Auto-generated constructor stub
	}

	public UserDeleteModel( String name )
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
