package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import gwt.material.design.client.data.HasDataCategory;

@JsonInclude( Include.NON_NULL )
public class LEMSnapshotModel implements HasDataCategory, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String snapshot_name;

	private String path;

	private long timestamp;

	@JsonIgnore
	private String date;

	public LEMSnapshotModel()
	{
		super();
	}

	public LEMSnapshotModel( String id, String name, String snapshot_name, String path, long timestamp )
	{
		super();
		this.id = id;
		this.name = name;
		this.snapshot_name = snapshot_name;
		this.path = path;
		this.timestamp = timestamp * 1000;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getSnapshot_name()
	{
		return snapshot_name;
	}

	public void setSnapshot_name( String snapshot_name )
	{
		this.snapshot_name = snapshot_name;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath( String path )
	{
		this.path = path;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp( long timestamp )
	{
		this.timestamp = timestamp * 1000;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate( String date )
	{
		this.date = date;
	}

	@Override
	public String getDataCategory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals( Object o )
	{
		LEMSnapshotModel lem = ( LEMSnapshotModel ) o;

		if ( id != lem.id )
			return false;

		return false;
	}

	@Override
	public int hashCode()
	{
		int result = Integer.parseInt( this.id );
		result = 31 * result;
		return result;
	}
}
