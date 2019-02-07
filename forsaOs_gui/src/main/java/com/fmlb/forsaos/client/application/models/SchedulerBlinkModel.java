package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class SchedulerBlinkModel implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String sid;

	private long max_blink;

	private String partition_name;

	private String name;

	public SchedulerBlinkModel()
	{
		super();
	}

	public SchedulerBlinkModel( String sid, String partition_name, long max_blink, String name )
	{
		super();
		this.sid = sid;
		this.max_blink = max_blink;
		this.partition_name = partition_name;
		this.name = name;
	}

	public long getMax_blink()
	{
		return max_blink;
	}

	public void setMax_blink( long max_blink )
	{
		this.max_blink = max_blink;
	}

	public String getPartition_name()
	{
		return partition_name;
	}

	public void setPartition_name( String partition_name )
	{
		this.partition_name = partition_name;
	}

	public String getSid()
	{
		return sid;
	}

	public void setSid( String sid )
	{
		this.sid = sid;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}
}
