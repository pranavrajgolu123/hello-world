package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class ExpandLEMModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String unit;

	private long new_size;

	public ExpandLEMModel()
	{
		super();
	}

	public ExpandLEMModel( String name, String unit, long new_size )
	{
		super();
		this.name = name;
		this.unit = unit;
		this.new_size = new_size;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit( String unit )
	{
		this.unit = unit;
	}

	public long getNew_size()
	{
		return new_size;
	}

	public void setNew_size( long new_size )
	{
		this.new_size = new_size;
	}

}
