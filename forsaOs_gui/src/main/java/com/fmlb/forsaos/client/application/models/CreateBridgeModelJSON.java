package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class CreateBridgeModelJSON implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CreateBridgeModel bridges;

	public CreateBridgeModelJSON()
	{
		super();
	}

	public CreateBridgeModelJSON( CreateBridgeModel bridges )
	{
		super();
		this.bridges = bridges;
	}

	public CreateBridgeModel getBridges()
	{
		return bridges;
	}

	public void setBridges( CreateBridgeModel bridges )
	{
		this.bridges = bridges;
	}

}
