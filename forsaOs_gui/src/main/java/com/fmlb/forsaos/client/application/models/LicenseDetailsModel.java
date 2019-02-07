package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class LicenseDetailsModel implements Serializable

{
	private static final long serialVersionUID = 1L;

	private String expiration_date = "";

	private String name = "";

	private String message = "";

	private String company_name = "";

	private String serial = "";

	private String license = "";

	private String type = "";

	private String activation = "";

	private String validation = "";

	public LicenseDetailsModel()
	{
		super();
	}

	public LicenseDetailsModel( String expiration_date, String name, String message, String company_name, String activation, String serial, String validation, String license, String type )
	{
		super();

		this.expiration_date = expiration_date;
		this.message = message;
		this.activation = activation;
		this.validation = validation;
		this.name = name;
		this.company_name = company_name;
		this.serial = serial;
		this.license = license;
		this.type = type;

	}

	public String getExpiration_date()
	{
		return expiration_date;
	}

	public void setExpiration_date( String expiration_date )
	{
		this.expiration_date = expiration_date;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage( String message )
	{
		this.message = message;
	}

	public String getCompany_name()
	{
		return company_name;
	}

	public void setCompany_name( String company_name )
	{
		this.company_name = company_name;
	}

	public String getSerial()
	{
		return serial;
	}

	public void setSerial( String serial )
	{
		this.serial = serial;
	}

	public String getActivation()
	{
		return activation;
	}

	public void setActivation( String activation )
	{
		this.activation = activation;
	}

	public String getValidation()
	{
		return validation;
	}

	public void setValidation( String validation )
	{
		this.validation = validation;
	}

	public String getLicense()
	{
		return license;
	}

	public void setLicense( String license )
	{
		this.license = license;
	}

	public String getType()
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
