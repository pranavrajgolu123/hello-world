package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class ChangePasswordModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String password;

	public ChangePasswordModel()
	{
		super();
	}

	public ChangePasswordModel( String name, String password )
	{
		super();
		this.name = name;
		this.password = password;

	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

}
