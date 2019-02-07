package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class EventLogResponseModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String service_name;

	private String logging_id;

	private String timestamp;

	private String level;

	private String method;

	private String desc;

	public EventLogResponseModel()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public EventLogResponseModel( String service_name, String logging_id, String timestamp, String level, String method, String desc )
	{
		super();
		this.service_name = service_name;
		this.logging_id = logging_id;
		this.timestamp = timestamp;
		this.level = level;
		this.method = method;
		this.desc = desc;
	}

	public String getService_name()
	{
		return service_name;
	}

	public void setService_name( String service_name )
	{
		this.service_name = service_name;
	}

	public String getLogging_id()
	{
		return logging_id;
	}

	public void setLogging_id( String logging_id )
	{
		this.logging_id = logging_id;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp( String timestamp )
	{
		this.timestamp = timestamp;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel( String level )
	{
		this.level = level;
	}

	public String getMethod()
	{
		return method;
	}

	public void setMethod( String method )
	{
		this.method = method;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc( String desc )
	{
		this.desc = desc;
	}

}
