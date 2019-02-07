package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class CreateEthernetModelJSON implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CreateEthernetModel ethernets;

	public CreateEthernetModelJSON()
	{
		super();
	}

	public CreateEthernetModelJSON( CreateEthernetModel ethernets )
	{
		super();
		this.ethernets = ethernets;
	}

	public CreateEthernetModel getEthernets()
	{
		return ethernets;
	}

	public void setEthernets( CreateEthernetModel ethernets )
	{
		this.ethernets = ethernets;
	}

}
