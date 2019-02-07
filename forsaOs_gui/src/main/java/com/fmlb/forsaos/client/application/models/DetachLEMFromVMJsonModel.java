package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DetachLEMFromVMJsonModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String id;

	private String lem_name;

	private String lem_id;

	private Boolean delete_lem;

	public DetachLEMFromVMJsonModel()
	{
		super();
	}

	public DetachLEMFromVMJsonModel( String name, String id, String lem_name, String lem_id, Boolean delete_lem )
	{
		super();
		this.name = name;
		this.id = id;
		this.lem_name = lem_name;
		this.lem_id = lem_id;
		this.delete_lem = delete_lem;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getLem_name()
	{
		return lem_name;
	}

	public void setLem_name( String lem_name )
	{
		this.lem_name = lem_name;
	}

	public String getLem_id()
	{
		return lem_id;
	}

	public void setLem_id( String lem_id )
	{
		this.lem_id = lem_id;
	}

	public Boolean getDelete_lem()
	{
		return delete_lem;
	}

	public void setDelete_lem( Boolean delete_lem )
	{
		this.delete_lem = delete_lem;
	}

}
