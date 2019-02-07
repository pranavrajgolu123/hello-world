package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class EditUserGroupModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String new_name = null;

	private Boolean update_user = null;

	private Integer auth_code = null;

	public EditUserGroupModel()
	{
		super();
	}

	public EditUserGroupModel( String name, String new_name, Boolean update_user, Integer auth_code )
	{
		super();
		this.name = name;
		this.new_name = new_name;
		this.update_user = update_user;
		this.auth_code = auth_code;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getNew_name()
	{
		return new_name;
	}

	public void setNew_name( String new_name )
	{
		this.new_name = new_name;
	}

	public Boolean getUpdate_user()
	{
		return update_user;
	}

	public void setUpdate_user( Boolean update_user )
	{
		this.update_user = update_user;
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
