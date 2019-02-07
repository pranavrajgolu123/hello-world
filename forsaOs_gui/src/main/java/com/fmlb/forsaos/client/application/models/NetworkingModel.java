package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

import com.fmlb.forsaos.client.application.common.NetworkDeviceType;

import gwt.material.design.client.data.HasDataCategory;

public class NetworkingModel implements HasDataCategory, Serializable, ComboBoxModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private NetworkDeviceType type;

	private String id;

	private List<String> interfaces;

	public NetworkingModel()
	{
		super();
	}

	public NetworkingModel( String name, NetworkDeviceType type, String id, List<String> interfaces )
	{
		super();
		this.name = name;
		this.type = type;
		this.id = id;
		this.interfaces = interfaces;
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
		return id;
	}

	public NetworkDeviceType getType()
	{
		return type;
	}

	public void setType( NetworkDeviceType type )
	{
		this.type = type;
	}

	@Override
	public String getDataCategory()
	{
		return null;
	}

	@Override
	public boolean equals( Object o )
	{
		NetworkingModel networkingModel = ( NetworkingModel ) o;

		if ( id != networkingModel.id )
			return false;

		return false;
	}

	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}

	public List<String> getInterfaces()
	{
		return interfaces;
	}

	public void setInterfaces( List<String> interfaces )
	{
		this.interfaces = interfaces;
	}
}
