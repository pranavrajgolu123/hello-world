package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude( Include.NON_NULL )
public class CreateBridgeModel extends CreateNetworkModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> interfaces;

	private String id;
	
	public CreateBridgeModel()
	{
		super();
	}

	public CreateBridgeModel( List<String> interfaces )
	{
		super();
		this.interfaces = interfaces;
	}

	public List<String> getInterfaces()
	{
		return interfaces;
	}

	public void setInterfaces( List<String> interfaces )
	{
		this.interfaces = interfaces;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}
	
	

}
