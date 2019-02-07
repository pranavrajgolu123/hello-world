package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class RegisterLicenseModel implements Serializable

{
	private static final long serialVersionUID = 1L;

	private String company_name;

	private String name;

	private String key;

	public RegisterLicenseModel()
	{
		super();
	}

	public RegisterLicenseModel( String name, String key, String company_name )
	{
		super();
		this.company_name = company_name;
		this.name = name;
		this.key = key;
	}

	public String getCompany_name()
	{
		return company_name;
	}

	public void setCompany_name( String company_name )
	{
		this.company_name = company_name;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey( String key )
	{
		this.key = key;
	}

}
