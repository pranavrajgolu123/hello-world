package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class VMDeleteWithLEMJSONModel implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private Boolean delete_lem;

	public VMDeleteWithLEMJSONModel()
	{
		super();
	}

	public VMDeleteWithLEMJSONModel( String id, String name, Boolean delete_lem )
	{
		super();
		this.id = id;
		this.name = name;
		this.delete_lem = delete_lem;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
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

	public Boolean getDelete_lem()
	{
		return delete_lem;
	}

	public void setDelete_lem( Boolean delete_lem )
	{
		this.delete_lem = delete_lem;
	}
}
