package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import gwt.material.design.client.data.HasDataCategory;

public class LoginHistoryModel implements HasDataCategory, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long timepoint;

	private String name;

	private String id;

	private String date;

	public LoginHistoryModel()
	{
		super();
	}

	public LoginHistoryModel( String id, long timepoint, String name )
	{
		super();
		this.id = id;
		this.timepoint = timepoint * 1000;
		this.name = name;
	}

	public long getTimepoint()
	{
		return timepoint;
	}

	public void setTimepoint( long timepoint )
	{
		this.timepoint = timepoint * 1000;
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

	public String getDate()
	{
		return date;
	}

	public void setDate( String date )
	{
		this.date = date;
	}

	@Override
	public boolean equals( Object o )
	{
		LoginHistoryModel loginHistoryModel = ( LoginHistoryModel ) o;

		if ( id != loginHistoryModel.id )
			return false;

		return false;
	}

	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}

	@Override
	public String getDataCategory()
	{
		return null;
	}

}
