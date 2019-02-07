package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DeleteSMTPModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String server = "";

	public DeleteSMTPModel()
	{
		super();
	}

	public DeleteSMTPModel( String server )
	{
		super();
		this.server = server;
	}

	public String getServer()
	{
		return server;
	}

	public void setServer( String server )
	{
		this.server = server;
	}

}
