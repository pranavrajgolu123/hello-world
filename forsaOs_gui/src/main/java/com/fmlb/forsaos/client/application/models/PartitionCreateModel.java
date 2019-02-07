package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class PartitionCreateModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer start;

	private Integer stop;

	private String name;

	private Boolean delete;

	public PartitionCreateModel()
	{
		super();
	}

	public PartitionCreateModel( Integer start, Integer stop, String name, Boolean delete )
	{
		super();
		this.start = start;
		this.stop = stop;
		this.name = name;
		this.delete = delete;
	}

	public Integer getStart()
	{
		return start;
	}

	public void setStart( Integer start )
	{
		this.start = start;
	}

	public Integer getStop()
	{
		return stop;
	}

	public void setStop( Integer stop )
	{
		this.stop = stop;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Boolean getDelete()
	{
		return delete;
	}

	public void setDelete( Boolean delete )
	{
		this.delete = delete;
	}

}
