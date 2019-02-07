package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class GiveAdGroupInputModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	private String name="";
	
	public GiveAdGroupInputModel()
	{
		super();
	}
	
	public GiveAdGroupInputModel(String name)
	{
		super();
		
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
