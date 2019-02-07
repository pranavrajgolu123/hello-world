package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import gwt.material.design.client.data.HasDataCategory;

public class UserGroupModel implements HasDataCategory, Serializable, ComboBoxModel
{

	private static final long serialVersionUID = 1L;

	private String name = "";

	private String id = "";

	private String filter = "";

	private Integer auth_code;

	public UserGroupModel()
	{
		super();
	}

	public UserGroupModel( String name, String id )
	{
		super();
		this.name = name;
		this.id = id;

	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	@Override
	public boolean equals( Object o )
	{
		UserGroupModel group = ( UserGroupModel ) o;

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
		return this.name;
	}

	@Override
	public String getId()
	{
		return this.id;
	}

	public String getFilter()
	{
		return filter;
	}

	public void setFilter( String filter )
	{
		this.filter = filter;
	}

	public Integer getAuth_code()
	{
		return auth_code;
	}

	public void setAuth_code( Integer auth_code )
	{
		this.auth_code = auth_code;
	}

}
