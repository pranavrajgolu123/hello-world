package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOAdvancedParameterISCSIModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String read_only;

	public REPOAdvancedParameterISCSIModel()
	{
		super();
	}

	public REPOAdvancedParameterISCSIModel( String read_only )
	{
		super();
		this.read_only = read_only;
	}

	public String getRead_only()
	{
		return read_only;
	}

	public void setRead_only( String read_only )
	{
		this.read_only = read_only;
	}
}
