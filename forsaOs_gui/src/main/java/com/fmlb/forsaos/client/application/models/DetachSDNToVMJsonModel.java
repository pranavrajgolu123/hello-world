package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DetachSDNToVMJsonModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String id;

	private int index;

	public DetachSDNToVMJsonModel()
	{
		super();
	}

	public DetachSDNToVMJsonModel( String name, String id, int index )
	{
		super();
		this.name = name;
		this.id = id;
		this.index = index;
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

	public int getIndex()
	{
		return index;
	}

	public void setIndex( int index )
	{
		this.index = index;
	}

}
