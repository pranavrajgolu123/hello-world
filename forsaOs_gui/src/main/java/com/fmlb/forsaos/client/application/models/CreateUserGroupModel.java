package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class CreateUserGroupModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private Boolean local = false;

	private String domain_name;

	private int auth = 0;

	public CreateUserGroupModel()
	{
		super();
	}

	public CreateUserGroupModel( String name, Boolean local, String domain_name )
	{
		super();
		this.name = name;
		this.local = local;
		this.domain_name = domain_name;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Boolean getLocal()
	{
		return local;
	}

	public void setLocal( Boolean local )
	{
		this.local = local;
	}

	public String getDomain_name()
	{
		return domain_name;
	}

	public void setDomain_name( String domain_name )
	{
		this.domain_name = domain_name;
	}

	public int getAuth()
	{
		return auth;
	}

	public void setAuth( int auth )
	{
		this.auth = auth;
	}

}
