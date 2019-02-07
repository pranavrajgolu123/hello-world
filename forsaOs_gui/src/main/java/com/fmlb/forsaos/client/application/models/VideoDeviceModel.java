package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class VideoDeviceModel implements Serializable, ComboBoxModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String id;

	public VideoDeviceModel()
	{
		super();
	}

	public VideoDeviceModel( String name, String id )
	{
		super();
		this.name = name;
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

	public void setId( String id )
	{
		this.id = id;
	}

	@Override
	public String getDisplayName()
	{
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getId()
	{
		// TODO Auto-generated method stub
		return this.id;
	}

}
