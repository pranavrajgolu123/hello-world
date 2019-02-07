package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOParameterCIFSModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String usr;

	private String pwd;

	private String ip;

	private String domain;

	private String path;
	
	private REPOAdvancedParameterCIFSModel advanced;

	public REPOParameterCIFSModel()
	{
		super();
	}

	public REPOParameterCIFSModel( String usr, String pwd, String ip, String domain, String path, REPOAdvancedParameterCIFSModel advanced )
	{
		super();
		this.usr = usr;
		this.pwd = pwd;
		this.ip = ip;
		this.domain = domain;
		this.path = path;
		this.advanced = advanced;
	}

	public String getUsr()
	{
		return usr;
	}

	public void setUsr( String usr )
	{
		this.usr = usr;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd( String pwd )
	{
		this.pwd = pwd;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp( String ip )
	{
		this.ip = ip;
	}

	public String getDomain()
	{
		return domain;
	}

	public void setDomain( String domain )
	{
		this.domain = domain;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath( String path )
	{
		this.path = path;
	}

	public REPOAdvancedParameterCIFSModel getAdvanced()
	{
		return advanced;
	}

	public void setAdvanced( REPOAdvancedParameterCIFSModel advanced )
	{
		this.advanced = advanced;
	}
}
