package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import gwt.material.design.client.data.HasDataCategory;

public class Person implements HasDataCategory, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private String propertyname;

	private String type;

	private String requiredtoenter;

	private String requiredtoexit;

	public Person( int id, String propertyname, String type, String requiredtoenter, String requiredtoexit )
	{
		this.id = id;
		this.propertyname = propertyname;
		this.type = type;
		this.requiredtoenter = requiredtoenter;
		this.requiredtoexit = requiredtoexit;
	}

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getPropertyname()
	{
		return propertyname;
	}

	public void setPropertyname( String propertyname )
	{
		this.propertyname = propertyname;
	}

	public String getType()
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
	}

	public String getRequiredtoenter()
	{
		return requiredtoenter;
	}

	public void setRequiredtoenter( String requiredtoenter )
	{
		this.requiredtoenter = requiredtoenter;
	}

	public String getRequiredtoexit()
	{
		return requiredtoexit;
	}

	public void setRequiredtoexit( String requiredtoexit )
	{
		this.requiredtoexit = requiredtoexit;
	}

	@Override
	public String getDataCategory()
	{
		return getType();
	}

	@Override
	public boolean equals( Object o )
	{
		if ( this == o )
			return true;
		if ( o == null || getClass() != o.getClass() )
			return false;

		Person person = ( Person ) o;

		if ( id != person.id )
			return false;
		if ( requiredtoenter != null ? !requiredtoenter.equals( person.requiredtoenter ) : person.requiredtoenter != null )
			return false;
		return type != null ? type.equals( person.type ) : person.type == null;
	}

	@Override
	public int hashCode()
	{
		int result = id;
		result = 31 * result + ( type != null ? type.hashCode() : 0 );
		result = 31 * result + ( requiredtoenter != null ? requiredtoenter.hashCode() : 0 );
		return result;
	}

	public String getEmail()
	{
		return requiredtoexit;
	}

	public void setEmail( String email )
	{
		this.requiredtoexit = email;
	}
}