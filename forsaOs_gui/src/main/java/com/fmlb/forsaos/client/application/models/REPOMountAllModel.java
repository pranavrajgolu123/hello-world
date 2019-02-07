package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOMountAllModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String id;

	private List<String> mount_list;

	public REPOMountAllModel()
	{
		super();
	}

	public REPOMountAllModel( String id, List<String> mount_list )
	{
		super();
		this.id = id;
		this.mount_list = mount_list;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public List<String> getMount_list()
	{
		return mount_list;
	}

	public void setMount_list( List<String> mount_list )
	{
		this.mount_list = mount_list;
	}

}
