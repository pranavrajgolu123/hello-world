package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LVNetCreateRequestModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String type;

	@JsonProperty( "interface" )
	private String _interface;

	public LVNetCreateRequestModel()
	{
		super();
	}

	public LVNetCreateRequestModel( String name, String type, String _interface )
	{
		super();
		this.name = name;
		this.type = type;
		this._interface = _interface;
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

	public String getInterface()
	{
		return _interface;
	}

	public void setInterface( String _interface )
	{
		this._interface = _interface;
	}

}
