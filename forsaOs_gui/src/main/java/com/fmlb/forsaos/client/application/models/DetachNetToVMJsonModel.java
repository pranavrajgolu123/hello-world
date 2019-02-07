package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class DetachNetToVMJsonModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String id;

	private String net_name;

	private String net_id;

	private Integer net_idx;

	public DetachNetToVMJsonModel()
	{
		super();
	}

	public DetachNetToVMJsonModel( String name, String id, String net_name, String net_id, Integer net_idx )
	{
		super();
		this.name = name;
		this.id = id;
		this.net_name = net_name;
		this.net_id = net_id;
		this.net_idx = net_idx;
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

	public Integer getNet_idx()
	{
		return net_idx;
	}

	public void setNet_idx( Integer net_idx )
	{
		this.net_idx = net_idx;
	}

}
