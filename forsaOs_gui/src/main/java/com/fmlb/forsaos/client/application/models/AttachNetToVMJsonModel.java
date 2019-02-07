package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class AttachNetToVMJsonModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String id;

	private String net_name;

	private String net_id;

	public AttachNetToVMJsonModel()
	{
		super();
	}

	public AttachNetToVMJsonModel( String name, String id, String net_name, String net_id )
	{
		super();
		this.name = name;
		this.id = id;
		this.net_name = net_name;
		this.net_id = net_id;
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

	public String getNet_name()
	{
		return net_name;
	}

	public void setNet_name( String net_name )
	{
		this.net_name = net_name;
	}

	public String getNet_id()
	{
		return net_id;
	}

	public void setNet_id( String net_id )
	{
		this.net_id = net_id;
	}

}
