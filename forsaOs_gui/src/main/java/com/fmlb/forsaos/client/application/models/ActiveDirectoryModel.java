package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import gwt.material.design.client.data.HasDataCategory;

public class ActiveDirectoryModel implements HasDataCategory, Serializable, ComboBoxModel
{

	private static final long serialVersionUID = 1L;

	private String name = "";

	private String id = "";

	private String ip = "";

	private String ad_admin = "";

	private String ad_psswd = "";

	public ActiveDirectoryModel()
	{
		super();
	}

	public ActiveDirectoryModel( String ip, String ad_psswd, String id, String name, String ad_admin )
	{
		super();
		this.name = name;
		this.id = id;
		this.ip = ip;
		this.ad_admin = ad_admin;
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

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp( String ip )
	{
		this.ip = ip;
	}

	public String getAd_admin()
	{
		return ad_admin;
	}

	public void setAd_admin( String ad_admin )
	{
		this.ad_admin = ad_admin;
	}

	public String getAd_psswd()
	{
		return ad_psswd;
	}

	public void setAd_psswd( String ad_psswd )
	{
		this.ad_psswd = ad_psswd;
	}

	@Override
	public boolean equals( Object o )
	{
		ActiveDirectoryModel group = ( ActiveDirectoryModel ) o;

		if ( id != group.id )
			return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}

	@Override
	public String getDataCategory()
	{
		return "";
	}

	@Override
	public String getDisplayName()
	{
		return getName();
	}
}
