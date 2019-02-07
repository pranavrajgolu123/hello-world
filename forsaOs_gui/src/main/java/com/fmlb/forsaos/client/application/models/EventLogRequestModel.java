package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class EventLogRequestModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int limit;

	private boolean no_rest;

	private boolean no_heartbeat;

	private String service;

	private String method;

	private String level;

	private Long time_start;

	private Long time_stop;

	private Integer skip;

	private String keyword;

	public EventLogRequestModel()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public EventLogRequestModel( int limit, boolean no_rest, boolean no_heartbeat, String service, String method, String level, Long time_start, Long time_stop, Integer skip, String keyword )
	{
		super();
		this.limit = limit;
		this.no_rest = no_rest;
		this.no_heartbeat = no_heartbeat;
		this.service = service;
		this.method = method;
		this.level = level;
		this.time_start = time_start;
		this.time_stop = time_stop;
		this.skip = skip;
		this.keyword = keyword;
	}

	public int getLimit()
	{
		return limit;
	}

	public void setLimit( int limit )
	{
		this.limit = limit;
	}

	public boolean isNo_rest()
	{
		return no_rest;
	}

	public void setNo_rest( boolean no_rest )
	{
		this.no_rest = no_rest;
	}

	public boolean isNo_heartbeat()
	{
		return no_heartbeat;
	}

	public void setNo_heartbeat( boolean no_heartbeat )
	{
		this.no_heartbeat = no_heartbeat;
	}

	public String getService()
	{
		return service;
	}

	public void setService( String service )
	{
		this.service = service;
	}

	public String getMethod()
	{
		return method;
	}

	public void setMethod( String method )
	{
		this.method = method;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel( String level )
	{
		this.level = level;
	}

	public Long getTime_start()
	{
		return time_start;
	}

	public void setTime_start( Long time_start )
	{
		this.time_start = time_start;
	}

	public Long getTime_stop()
	{
		return time_stop;
	}

	public void setTime_stop( Long time_stop )
	{
		this.time_stop = time_stop;
	}

	public Integer getSkip()
	{
		return skip;
	}

	public void setSkip( Integer skip )
	{
		this.skip = skip;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword( String keyword )
	{
		this.keyword = keyword;
	}
}
