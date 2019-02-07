package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import gwt.material.design.client.data.HasDataCategory;

public class SMTPDestinationModel implements HasDataCategory, Serializable

{
	private static final long serialVersionUID = 1L;

	private String email = "";

	private Integer level = 0;

	private String id = "";

	public SMTPDestinationModel()
	{
		super();
	}

	public SMTPDestinationModel( String email, Integer level, String id )
	{
		super();
		this.email = email;
		this.level = level;
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel( Integer level )
	{
		this.level = level;
	}

	@Override
	public boolean equals( Object o )
	{
		SMTPDestinationModel group = ( SMTPDestinationModel ) o;

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
