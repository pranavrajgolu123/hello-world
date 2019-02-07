package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class CreateActiveDirectoryModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String ip;

	private String ad_uname;

	private String ad_psswd;

	public CreateActiveDirectoryModel()
	{
		super();
	}

	public CreateActiveDirectoryModel( String name, String ip, String ad_uname, String ad_psswd )
	{
		super();
		this.name = name;
		this.ip = ip;
		this.ad_uname = ad_uname;
		this.ad_psswd = ad_psswd;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp( String ip )
	{
		this.ip = ip;
	}

	public String getAd_uname()
	{
		return ad_uname;
	}

	public void setAd_uname( String ad_uname )
	{
		this.ad_uname = ad_uname;
	}

	public String getAd_psswd()
	{
		return ad_psswd;
	}

	public void setAd_psswd( String ad_psswd )
	{
		this.ad_psswd = ad_psswd;
	}

}
