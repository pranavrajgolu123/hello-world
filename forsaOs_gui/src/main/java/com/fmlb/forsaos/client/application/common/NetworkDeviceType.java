package com.fmlb.forsaos.client.application.common;

public enum NetworkDeviceType
{

	ETHERNET( "ethernets" ), VLAN( "vlans" ), BOND( "bonds" ), BRIDGE(
			"bridges" );

	private String value;

	private NetworkDeviceType( String value )
	{
		this.value = value;
	}

	public String getValue()
	{
		return this.value;
	}

}
