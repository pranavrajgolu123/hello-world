package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class ISOMountPathModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;

	public ISOMountPathModel()
	{
		super();
	}

	public ISOMountPathModel( String path )
	{
		super();
		this.path = path;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath( String path )
	{
		this.path = path;
	}

}
