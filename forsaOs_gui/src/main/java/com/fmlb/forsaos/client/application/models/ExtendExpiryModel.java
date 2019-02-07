package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class ExtendExpiryModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;

	private String id;

	private Integer month;

	private Integer day;

	private Integer hour;

	public ExtendExpiryModel()
	{
		super();
	}

	public ExtendExpiryModel( String name, String id, Integer month, Integer day, Integer hour )
	{
		super();
		this.name = name;
		this.id = id;
		this.month = month;
		this.day = day;
		this.hour = hour;
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
}
