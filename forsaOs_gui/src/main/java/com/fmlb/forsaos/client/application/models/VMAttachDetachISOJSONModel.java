package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class VMAttachDetachISOJSONModel implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String cd_path;

	public VMAttachDetachISOJSONModel()
	{
		super();
	}

	public VMAttachDetachISOJSONModel( String id, String name, String cd_path )
	{
		super();
		this.id = id;
		this.name = name;
		this.cd_path = cd_path;
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

	public String getCd_path()
	{
		return cd_path;
	}

	public void setCd_path( String cd_path )
	{
		this.cd_path = cd_path;
	}
}
