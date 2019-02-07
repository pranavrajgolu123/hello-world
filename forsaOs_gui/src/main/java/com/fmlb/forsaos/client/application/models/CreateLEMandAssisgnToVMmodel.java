package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class CreateLEMandAssisgnToVMmodel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LEMJSONModel[] create_lem;

	private String name;

	private String id;

	public CreateLEMandAssisgnToVMmodel()
	{
		super();
	}

	public CreateLEMandAssisgnToVMmodel( LEMJSONModel[] create_lem, String name, String id )
	{
		super();
		this.create_lem = create_lem;
		this.name = name;
		this.id = id;
	}

	public LEMJSONModel[] getCreate_lem()
	{
		return create_lem;
	}

	public void setCreate_lem( LEMJSONModel[] create_lem )
	{
		this.create_lem = create_lem;
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
