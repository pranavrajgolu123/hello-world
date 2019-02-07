package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UpdateLEMSnapshotModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String new_name;

	public UpdateLEMSnapshotModel()
	{
		super();
	}

	public UpdateLEMSnapshotModel( String name, String new_name )
	{
		super();
		this.name = name;
		this.new_name = new_name;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getNew_name()
	{
		return new_name;
	}

	public void setNew_name( String new_name )
	{
		this.new_name = new_name;
	}

}
