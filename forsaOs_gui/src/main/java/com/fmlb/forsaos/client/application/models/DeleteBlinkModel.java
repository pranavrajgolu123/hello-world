package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DeleteBlinkModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	// private String id;

	private String name="";

	public DeleteBlinkModel()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public DeleteBlinkModel( String name )
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}
}
