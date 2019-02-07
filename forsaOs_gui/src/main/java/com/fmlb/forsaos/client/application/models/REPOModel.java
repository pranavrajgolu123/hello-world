package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import gwt.material.design.client.data.HasDataCategory;

@JsonInclude( Include.NON_NULL )
public class REPOModel implements HasDataCategory, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String category;

	private String name;

	private String protocol;

	private String source;

	private String mountStatus;

	private REPOMainModel repoMainModel;

	public REPOModel()
	{
		super();
	}

	public REPOModel( String category, String name, String protocol, String source, String mountStatus, REPOMainModel repoMainModel )
	{
		super();
		this.category = category;
		this.name = name;
		this.protocol = protocol;
		this.source = source;
		this.mountStatus = mountStatus;
		this.repoMainModel = repoMainModel;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public void setProtocol( String protocol )
	{
		this.protocol = protocol;
	}

	@Override
	public String getDataCategory()
	{
		return getCategory();
	}

	public String getSource()
	{
		return source;
	}

	public void setSource( String source )
	{
		this.source = source;
	}

	public String getMountStatus()
	{
		return mountStatus;
	}

	public void setMountStatus( String mountStatus )
	{
		this.mountStatus = mountStatus;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory( String category )
	{
		this.category = category;
	}

	public REPOMainModel getRepoMainModel()
	{
		return repoMainModel;
	}

	public void setRepoMainModel( REPOMainModel repoMainModel )
	{
		this.repoMainModel = repoMainModel;
	}

}
