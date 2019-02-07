package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOParameterLocalModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fs;

	private String[] devices_list;

	public REPOParameterLocalModel()
	{
		super();
	}

	public REPOParameterLocalModel( String fs, String[] devices_list )
	{
		super();
		this.fs = fs;
		this.devices_list = devices_list;
	}

	public String getFs()
	{
		return fs;
	}

	public void setFs( String fs )
	{
		this.fs = fs;
	}

	public String[] getDevices_list()
	{
		return devices_list;
	}

	public void setDevices_list( String[] devices_list )
	{
		this.devices_list = devices_list;
	}

}
