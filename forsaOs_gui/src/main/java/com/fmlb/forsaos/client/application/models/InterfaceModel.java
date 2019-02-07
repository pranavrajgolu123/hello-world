package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import gwt.material.design.client.data.HasDataCategory;

public class InterfaceModel implements HasDataCategory, Serializable
{

	private static final long serialVersionUID = 1L;

	private String name;

	private String id;

	private String type;

	public InterfaceModel()
	{
		super();
	}

	public InterfaceModel( String name, String id, String type )
	{
		super();
		this.name = name;
		this.id = id;
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
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
	public String getDataCategory()
	{
		return null;
	}

	@Override
	public boolean equals( Object o )
	{
		InterfaceModel interfaceModel = ( InterfaceModel ) o;

		if ( id != interfaceModel.id )
			return false;

		return false;
	}

	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}

}
