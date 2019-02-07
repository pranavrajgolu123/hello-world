package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NicModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String slot;

	private String mac;

	private String type;
	
	private String name;
	
	@JsonProperty( "interface" )
	private String _interface;
	
	private String id;
	
	private String bridge_name;
	

	public NicModel()
	{
		super();
	}

	public NicModel( String slot, String mac, String type, String name, String _interface, String id, String bridge_name )
	{
		super();
		this.slot = slot;
		this.mac = mac;
		this.type = type;
		this.name = name;
		this._interface = _interface;
		this.id = id;
		this.bridge_name = bridge_name;
	}

	public String getSlot()
	{
		return slot;
	}

	public void setSlot( String slot )
	{
		this.slot = slot;
	}
	
	public String getMac()
	{
		return mac;
	}

	public void setMac( String mac )
	{
		this.mac = mac;
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

	public String get_interface()
	{
		return _interface;
	}

	public void set_interface( String _interface )
	{
		this._interface = _interface;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getBridge_name()
	{
		return bridge_name;
	}

	public void setBridge_name( String bridge_name )
	{
		this.bridge_name = bridge_name;
	}
}
