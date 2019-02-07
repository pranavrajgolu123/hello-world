package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class BridgeJSONModel implements Serializable, ComboBoxModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IdModel _id;

	private String name;

	public BridgeJSONModel()
	{
		// TODO Auto-generated constructor stub
	}

	public BridgeJSONModel( String name )
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public IdModel get_id()
	{
		return _id;
	}

	public void set_id( IdModel _id )
	{
		this._id = _id;
	}

	@Override
	public String getDisplayName()
	{
		return getName();
	}

	@Override
	public String getId()
	{
		return getId();
	}
}
