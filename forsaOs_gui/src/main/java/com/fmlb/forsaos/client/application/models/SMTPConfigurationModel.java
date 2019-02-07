package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import gwt.material.design.client.data.HasDataCategory;

public class SMTPConfigurationModel implements HasDataCategory, Serializable

{
	private static final long serialVersionUID = 1L;

	private String name = "";

	private String port = "";

	private String psswd = "";

	private String user = "";

	private String id = "";

	public SMTPConfigurationModel()
	{
		super();
	}

	public SMTPConfigurationModel( String user, String psswd, String port, String id, String name )
	{
		super();
		this.name = name;
		this.port = port;
		this.user = user;
		this.psswd = psswd;
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

	public String getPort()
	{
		return port;
	}

	public void setPort( String port )
	{
		this.port = port;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser( String user )
	{
		this.user = user;
	}

	public String getPsswd()
	{
		return psswd;
	}

	public void setPsswd( String psswd )
	{
		this.psswd = psswd;
	}

	@Override
	public boolean equals( Object o )
	{
		SMTPConfigurationModel group = ( SMTPConfigurationModel ) o;

		if ( id != group.id )
			return false;

		return false;
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
	public int hashCode()
	{
		return this.getId().hashCode();
	}

	@Override
	public String getDataCategory()
	{
		return "";
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
