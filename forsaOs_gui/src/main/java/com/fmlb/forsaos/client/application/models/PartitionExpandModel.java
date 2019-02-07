package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class PartitionExpandModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;

	private Integer new_stop;

	public PartitionExpandModel()
	{
		super();
	}

	public PartitionExpandModel( String name, Integer new_stop )
	{
		super();
		this.name = name;
		this.new_stop = new_stop;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Integer getNew_stop()
	{
		return new_stop;
	}

	public void setNew_stop( Integer new_stop )
	{
		this.new_stop = new_stop;
	}
}
