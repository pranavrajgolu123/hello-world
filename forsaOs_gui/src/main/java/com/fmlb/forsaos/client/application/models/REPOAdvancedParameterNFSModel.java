package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOAdvancedParameterNFSModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String read_only;

	private String vers;

	private String cache;

	private String port;

	public REPOAdvancedParameterNFSModel()
	{
		super();
	}

	public REPOAdvancedParameterNFSModel( String read_only, String vers, String cache, String port )
	{
		super();
		this.read_only = read_only;
		this.vers = vers;
		this.cache = cache;
		this.port = port;
	}

	public String getRead_only()
	{
		return read_only;
	}

	public void setRead_only( String read_only )
	{
		this.read_only = read_only;
	}

	public String getVers()
	{
		return vers;
	}

	public void setVers( String vers )
	{
		this.vers = vers;
	}

	public String getCache()
	{
		return cache;
	}

	public void setCache( String cache )
	{
		this.cache = cache;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort( String port )
	{
		this.port = port;
	}

}
