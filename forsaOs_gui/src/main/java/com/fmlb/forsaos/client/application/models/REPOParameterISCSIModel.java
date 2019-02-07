package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOParameterISCSIModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String usr;

	private String pwd;

	private String ip;

	private String iqn;

	private REPOAdvancedParameterISCSIModel advanced;

	public REPOParameterISCSIModel()
	{
		super();
	}

	public REPOParameterISCSIModel( String usr, String pwd, String ip, String iqn, REPOAdvancedParameterISCSIModel advanced )
	{
		super();
		this.usr = usr;
		this.pwd = pwd;
		this.ip = ip;
		this.iqn = iqn;
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

	public String getIqn()
	{
		return iqn;
	}

	public void setIqn( String iqn )
	{
		this.iqn = iqn;
	}

	public REPOAdvancedParameterISCSIModel getAdvanced()
	{
		return advanced;
	}

	public void setAdvanced( REPOAdvancedParameterISCSIModel advanced )
	{
		this.advanced = advanced;
	}
}
