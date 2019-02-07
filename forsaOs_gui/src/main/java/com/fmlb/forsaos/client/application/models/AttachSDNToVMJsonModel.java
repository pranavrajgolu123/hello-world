package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class AttachSDNToVMJsonModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String id;

	private String subnet_name;

	private String subnet_id;

	public AttachSDNToVMJsonModel()
	{
		super();
	}

	public AttachSDNToVMJsonModel( String name, String id, String subnet_name, String subnet_id )
	{
		super();
		this.name = name;
		this.id = id;
		this.subnet_name = subnet_name;
		this.subnet_id = subnet_id;
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

	public String getSubnet_name()
	{
		return subnet_name;
	}

	public void setSubnet_name( String subnet_name )
	{
		this.subnet_name = subnet_name;
	}

	public String getSubnet_id()
	{
		return subnet_id;
	}

	public void setSubnet_id( String subnet_id )
	{
		this.subnet_id = subnet_id;
	}

}
