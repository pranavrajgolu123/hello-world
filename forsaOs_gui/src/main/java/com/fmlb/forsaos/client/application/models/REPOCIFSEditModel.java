package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOCIFSEditModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private REPOCIFSCreateModel config;

	public REPOCIFSEditModel()
	{
		super();
	}

	public REPOCIFSEditModel( String id, REPOCIFSCreateModel config )
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

	public REPOCIFSCreateModel getConfig()
	{
		return config;
	}

	public void setConfig( REPOCIFSCreateModel config )
	{
		this.config = config;
	}
}
