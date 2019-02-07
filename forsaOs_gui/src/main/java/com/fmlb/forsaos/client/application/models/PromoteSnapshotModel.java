package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class PromoteSnapshotModel implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String lem_name;

	public PromoteSnapshotModel()
	{
		super();
	}

	public PromoteSnapshotModel( String name, String lem_name )
	{
		super();
		this.name = name;
		this.lem_name = lem_name;
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
}
