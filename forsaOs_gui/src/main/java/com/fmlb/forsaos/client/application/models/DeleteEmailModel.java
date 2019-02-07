package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DeleteEmailModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String email = "";

	public DeleteEmailModel()
	{
		super();
	}

	public DeleteEmailModel( String email )
	{
		super();
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

}
