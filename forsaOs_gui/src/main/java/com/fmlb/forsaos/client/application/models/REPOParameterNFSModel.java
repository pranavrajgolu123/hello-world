package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOParameterNFSModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip;

	private String path;

	private REPOAdvancedParameterNFSModel advanced;

	public REPOParameterNFSModel()
	{
		super();
	}

	public REPOParameterNFSModel( String ip, String path, REPOAdvancedParameterNFSModel advanced )
	{
		super();
		this.ip = ip;
		this.path = path;
		this.advanced = advanced;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp( String ip )
	{
		this.ip = ip;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath( String path )
	{
		this.path = path;
	}

	public REPOAdvancedParameterNFSModel getAdvanced()
	{
		return advanced;
	}

	public void setAdvanced( REPOAdvancedParameterNFSModel advanced )
	{
		this.advanced = advanced;
	}

}
