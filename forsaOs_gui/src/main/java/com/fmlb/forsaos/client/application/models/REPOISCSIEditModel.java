package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOISCSIEditModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private REPOISCSICreateModel config;

	public REPOISCSIEditModel()
	{
		super();
	}

	public REPOISCSIEditModel( String id, REPOISCSICreateModel config )
	{
		super();
		this.id = id;
		this.config = config;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public REPOISCSICreateModel getConfig()
	{
		return config;
	}

	public void setConfig( REPOISCSICreateModel config )
	{
		this.config = config;
	}
}
