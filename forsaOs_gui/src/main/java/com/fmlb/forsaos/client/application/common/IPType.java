package com.fmlb.forsaos.client.application.common;

public enum IPType
{

	IP_V4( "IP_V4" ), IP_V6( "IP_V6" );

	private String value;

	private IPType( String value )
	{
		this.value = value;
	}

	public String getValue()
	{
		return this.value;
	}

}
