package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class CreateNewUserModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;

	private String psswd;

	private String email;

	private String group_name = null;

	private Boolean admin;

	private Boolean token_expire;

	private Integer month;

	private Integer day;

	private Integer hour;

	private Integer auth = null;

	public CreateNewUserModel()
	{
		super();
	}

	public CreateNewUserModel( String name, String psswd, String email, String group_name, Boolean admin, Boolean token_expire, Integer month, Integer day, Integer hour )
	{
		super();
		this.name = name;
		this.psswd = psswd;
		this.email = email;
		this.group_name = group_name;
		this.admin = admin;
		this.token_expire = token_expire;
		this.month = month;
		this.day = day;
		this.hour = hour;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getPsswd()
	{
		return psswd;
	}

	public void setPsswd( String psswd )
	{
		this.psswd = psswd;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public String getGroup_name()
	{
		return group_name;
	}

	public void setGroup_name( String group_name )
	{
		this.group_name = group_name;
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

	public Integer getMonth()
	{
		return month;
	}

	public void setMonth( Integer month )
	{
		this.month = month;
	}

	public Integer getDay()
	{
		return day;
	}

	public void setDay( Integer day )
	{
		this.day = day;
	}

	public Integer getHour()
	{
		return hour;
	}

	public void setHour( Integer hour )
	{
		this.hour = hour;
	}

	public Integer getAuth()
	{
		return auth;
	}

	public void setAuth( Integer auth )
	{
		this.auth = auth;
	}

}
