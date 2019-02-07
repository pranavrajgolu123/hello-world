package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOMountInfoModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String source;

	private String mountpoint;

	public REPOMountInfoModel()
	{
		super();
	}

	public REPOMountInfoModel( String name, String source, String mountpoint )
	{
		super();
		this.name = name;
		this.source = source;
		this.mountpoint = mountpoint;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource( String source )
	{
		this.source = source;
	}

	public String getMountpoint()
	{
		return mountpoint;
	}

	public void setMountpoint( String mountpoint )
	{
		this.mountpoint = mountpoint;
	}
}
