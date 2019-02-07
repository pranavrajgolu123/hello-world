package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class CloneLEMModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;

	private String clone_name;

	private int instances;

	public CloneLEMModel()
	{
		super();
	}

	public CloneLEMModel( String name, String clone_name, int instances )
	{
		super();
		this.name = name;
		this.clone_name = clone_name;
		this.instances = instances;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getClone_name()
	{
		return clone_name;
	}

	public void setClone_name( String clone_name )
	{
		this.clone_name = clone_name;
	}

	public int getInstances()
	{
		return instances;
	}

	public void setInstances( int instances )
	{
		this.instances = instances;
	}

}
