package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

import gwt.material.design.client.data.HasDataCategory;

public class UserAccountModel implements HasDataCategory, Serializable
{

	private static final long serialVersionUID = 1L;

	private String token = "";

	private Boolean admin = false;

	private Boolean token_expire = false;

	private String id = "";

	private String email = "";

	private Integer auth = 0;

	private List<String> groups;

	private String psswd = "";

	private String name = "";

	public UserAccountModel()
	{
		super();
	}

	public UserAccountModel( String token, Boolean admin, Boolean token_expire, String id, String email, Integer auth, List<String> groups, String psswd, String name )
	{
		super();
		this.token = token;
		this.admin = admin;
		this.auth = auth;
		this.email = email;
		this.groups = groups;
		this.token_expire = token_expire;
		this.psswd = psswd;
		this.id = id;
		this.name = name;
	}

	public Boolean getAdmin()
	{
		return admin;
	}

	public void setAdmin( Boolean admin )
	{
		this.admin = admin;
	}

	public Boolean getToken_expire()
	{
		return token_expire;
	}

	public void setToken_expire( Boolean token_expire )
	{
		this.token_expire = token_expire;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken( String token )
	{
		this.token = token;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public Integer getAuth()
	{
		return auth;
	}

	public void setAuth( Integer auth )
	{
		this.auth = auth;
	}

	public List<String> getGroups()
	{
		return groups;
	}

	public void setGroups( List<String> groups )
	{
		this.groups = groups;
	}

	public String getPsswd()
	{
		return psswd;
	}

	public void setPsswd( String psswd )
	{
		this.psswd = psswd;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	@Override
	public boolean equals( Object o )
	{
		UserAccountModel user = ( UserAccountModel ) o;

		if ( id != user.id )
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

}
