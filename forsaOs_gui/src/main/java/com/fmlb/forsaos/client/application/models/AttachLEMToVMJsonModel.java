package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class AttachLEMToVMJsonModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LEMToVMJSONModel[] lems;

	private String name;

	private String id;

	public AttachLEMToVMJsonModel()
	{
		super();
	}

	public AttachLEMToVMJsonModel( LEMToVMJSONModel[] lems, String name, String id )
	{
		super();
		this.lems = lems;
		this.name = name;
		this.id = id;
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

	public LEMToVMJSONModel[] getLems()
	{
		return lems;
	}

	public void setLems( LEMToVMJSONModel[] lems )
	{
		this.lems = lems;
	}
}
