package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import gwt.material.design.client.data.HasDataCategory;
@JsonInclude( Include.NON_NULL )
public class SchedulerModel implements HasDataCategory, Serializable, ComboBoxModel
{

	private static final long serialVersionUID = 1L;

	private String name;

	private long hour;

	private long min;

	private long sec;

	private String ip;

	private String uri;

	private String port;

	private int month;

	private int year;

	private int day;
	
	private Integer repeat_hour;
	
	private Integer repeat_day;

	SchedulerBlinkModel data;

	private String type;

	private Integer status;

	private Boolean offline;
	
	private Long timestamp;

	@JsonIgnore
	private String date;

	public SchedulerModel()
	{
		super();
	}

	public SchedulerModel( String name )
	{

		super();
		this.name = name;
	}
	
	public SchedulerModel( String name, Boolean offline )
	{

		super();
		this.name = name;
		this.offline=offline;
	}

	public SchedulerModel( String name, long hour, long min, String ip, String uri, int month, String port, long sec, int year, SchedulerBlinkModel data, int day)
	{
		super();
		this.name = name;
		this.hour = hour;
		this.min = min;
		this.sec = sec;
		this.ip = ip;
		this.uri = uri;
		this.port = port;
		this.month = month;
		this.year = year;
		this.data = data;
		this.day = day;
 
	}

	public String getType()
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus( Integer status )
	{
		this.status = status;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public long getHour()
	{
		return hour;
	}

	public void setHour( long hour )
	{
		this.hour = hour;
	}

	public long getMin()
	{
		return min;
	}

	public void setMin( long min )
	{
		this.min = min;
	}

	public long getSec()
	{
		return sec;
	}

	public void setSec( long sec )
	{
		this.sec = sec;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp( String ip )
	{
		this.ip = ip;
	}

	public String getUri()
	{
		return uri;
	}

	public void setUri( String uri )
	{
		this.uri = uri;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort( String port )
	{
		this.port = port;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth( int month )
	{
		this.month = month;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear( int year )
	{
		this.year = year;
	}

	public int getDay()
	{
		return day;
	}

	public void setDay( int day )
	{
		this.day = day;
	}

	public SchedulerBlinkModel getData()
	{
		return data;
	}

	public void setData( SchedulerBlinkModel data )
	{
		this.data = data;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate( String date )
	{
		this.date = date;
	}

	public Integer getRepeat_hour()
	{
		return repeat_hour;
	}

	public void setRepeat_hour( Integer repeat_hour )
	{
		this.repeat_hour = repeat_hour;
	}

	public Integer getRepeat_day()
	{
		return repeat_day;
	}

	public void setRepeat_day( Integer repeat_day )
	{
		this.repeat_day = repeat_day;
	}

	public Boolean getOffline()
	{
		return offline;
	}

	public void setOffline( Boolean offline )
	{
		this.offline = offline;
	}

	@Override
	public String getDataCategory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName()
	{
		return null;
	}

	@Override
	public String getId()
	{
		return null;
	}

	public Long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp( Long timestamp )
	{
		this.timestamp = timestamp;
	}

}
