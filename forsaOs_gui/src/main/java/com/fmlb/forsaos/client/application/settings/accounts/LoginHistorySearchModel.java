package com.fmlb.forsaos.client.application.settings.accounts;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LoginHistorySearchModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private Long timestamp;
	
	private List<String> filter;


	public LoginHistorySearchModel()
	{
		super();
	}

	public LoginHistorySearchModel( String name, Long timestamp )
	{
		super();
		this.name = name;
		this.timestamp = timestamp;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp( Long timestamp )
	{
		this.timestamp = timestamp;
	}
	public List<String> getFilter()
	{
		return filter;
	}

	public void setFilter( List<String> filter )
	{
		this.filter = filter;
	}
}
