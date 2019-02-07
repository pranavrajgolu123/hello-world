package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class CreateBlinkModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name ="";
	
	private String partition_name="";

	
	public CreateBlinkModel()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateBlinkModel(String name,String partition_name)
	{
		super();
		this.name=name;
		this.partition_name=partition_name;
	}

    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartition_name() {
		return partition_name;
	}

	public void setPartition_name(String partition_name) {
		this.partition_name = partition_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}

