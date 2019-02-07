package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class ExpandVMModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String unit;

	private String lem_name;

	private long new_size;

	private boolean force_expand = false;

	public ExpandVMModel()
	{
		super();
	}

	public ExpandVMModel( String name, String unit, String lem_name, long new_size, boolean force_expand )
	{
		super();
		this.name = name;
		this.lem_name = lem_name;
		this.unit = unit;
		this.new_size = new_size;
		this.force_expand = force_expand;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getLem_name()
	{
		return lem_name;
	}

	public void setLem_name( String lem_name )
	{
		this.lem_name = lem_name;
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

	public boolean isForce_expand()
	{
		return force_expand;
	}

	public void setForce_expand( boolean force_expand )
	{
		this.force_expand = force_expand;
	}

}
