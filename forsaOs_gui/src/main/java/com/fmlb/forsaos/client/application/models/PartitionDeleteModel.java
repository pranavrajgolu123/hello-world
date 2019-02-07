package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class PartitionDeleteModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private Boolean delete_part;

	public PartitionDeleteModel()
	{
		super();
	}

	public PartitionDeleteModel( String name, Boolean delete_part )
	{
		super();
		this.name = name;
		this.delete_part = delete_part;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Boolean getDelete_part()
	{
		return delete_part;
	}

	public void setDelete_part( Boolean delete_part )
	{
		this.delete_part = delete_part;
	}

}
