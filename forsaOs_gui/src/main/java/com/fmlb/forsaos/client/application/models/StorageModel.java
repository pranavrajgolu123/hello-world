package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class StorageModel implements Serializable, ComboBoxModel
{

	private String name;

	private String id;

	public StorageModel()
	{
		super();
	}

	public StorageModel( String name, String id )
	{
		super();
		this.name = name;
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

	public void setId( String id )
	{
		this.id = id;
	}

	@Override
	public String getDisplayName()
	{
		return this.name;
	}

	@Override
	public String getId()
	{
		return this.id;
	}

}
