package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LEMJSONModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String unit;

	private Long size;

	private String rtm_name;

	public LEMJSONModel()
	{
		// TODO Auto-generated constructor stub
	}

	public LEMJSONModel( String name, String unit, Long size, String rtm_name )
	{
		super();
		this.name = name;
		this.unit = unit;
		this.size = size;
		this.rtm_name = rtm_name;
	}

	public LEMJSONModel( String id, String name, String unit, Long size, String rtm_name )
	{
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.size = size;
		this.rtm_name = rtm_name;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit( String unit )
	{
		this.unit = unit;
	}

	public Long getSize()
	{
		return size;
	}

	public void setSize( Long size )
	{
		this.size = size;
	}

	public String getRtm_name()
	{
		return rtm_name;
	}

	public void setRtm_name( String rtm_name )
	{
		this.rtm_name = rtm_name;
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
