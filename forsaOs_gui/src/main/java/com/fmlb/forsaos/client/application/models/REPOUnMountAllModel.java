package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOUnMountAllModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String id;

	private List<String> unmount_list;

	public REPOUnMountAllModel()
	{
		super();
	}

	public REPOUnMountAllModel( String id, List<String> unmount_list )
	{
		super();
		this.id = id;
		this.unmount_list = unmount_list;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public List<String> getUnmount_list()
	{
		return unmount_list;
	}

	public void setUnmount_list( List<String> unmount_list )
	{
		this.unmount_list = unmount_list;
	}

}
