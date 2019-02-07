package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

public class CloneLEMandAssisgnToVMmodel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CloneLEMModel> clone_lem;

	private String name;

	private String id;

	public CloneLEMandAssisgnToVMmodel()
	{
		super();
	}

	public CloneLEMandAssisgnToVMmodel( List<CloneLEMModel> clone_lem, String name, String id )
	{
		super();
		this.clone_lem = clone_lem;
		this.name = name;
		this.id = id;
	}

	public List<CloneLEMModel> getClone_lem()
	{
		return clone_lem;
	}

	public void setClone_lem( List<CloneLEMModel> clone_lem )
	{
		this.clone_lem = clone_lem;
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

}
