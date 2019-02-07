package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fmlb.forsaos.client.application.common.NetworkDeviceType;

import jsinterop.annotations.JsIgnore;

@JsonInclude( Include.NON_NULL )
public class CreateNetworkModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private Boolean dhcp6 = true;

	private Boolean dhcp4 = true;

	private List<String> addresses;

	private String gateway4;

	private String gateway6;

	private List<String> dns_search;

	private List<String> dns_addresses;
	
	private List<String> interfaces;

	@JsIgnore
	private NetworkDeviceType type;

	public CreateNetworkModel()
	{
		super();
	}

	public CreateNetworkModel( String name, Boolean dhcp6, Boolean dhcp4, List<String> addresses, String gateway4, String gateway6, List<String> dns_search, List<String> dns_addresses, NetworkDeviceType type )
	{
		super();
		this.name = name;
		this.dhcp6 = dhcp6;
		this.dhcp4 = dhcp4;
		this.addresses = addresses;
		this.gateway4 = gateway4;
		this.gateway6 = gateway6;
		this.dns_search = dns_search;
		this.dns_addresses = dns_addresses;
		this.type = type;
	}
	public CreateNetworkModel( String name, Boolean dhcp6, Boolean dhcp4, List<String> addresses, String gateway4, String gateway6, List<String> dns_search, List<String> dns_addresses, List<String> interfaces, NetworkDeviceType type )
	{
		super();
		this.name = name;
		this.dhcp6 = dhcp6;
		this.dhcp4 = dhcp4;
		this.addresses = addresses;
		this.gateway4 = gateway4;
		this.gateway6 = gateway6;
		this.dns_search = dns_search;
		this.dns_addresses = dns_addresses;
		this.interfaces = interfaces;
		this.type = type;
	}

	public CreateNetworkModel( String name )
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

	public List<String> getAddresses()
	{
		return addresses;
	}

	public void setAddresses( List<String> addresses )
	{
		this.addresses = addresses;
	}

	public String getGateway4()
	{
		return gateway4;
	}

	public void setGateway4( String gateway4 )
	{
		this.gateway4 = gateway4;
	}

	public String getGateway6()
	{
		return gateway6;
	}

	public void setGateway6( String gateway6 )
	{
		this.gateway6 = gateway6;
	}

	public List<String> getDns_search()
	{
		return dns_search;
	}

	public void setDns_search( List<String> dns_search )
	{
		this.dns_search = dns_search;
	}

	public List<String> getDns_addresses()
	{
		return dns_addresses;
	}

	public void setDns_addresses( List<String> dns_addresses )
	{
		this.dns_addresses = dns_addresses;
	}

	public NetworkDeviceType getType()
	{
		return type;
	}

	public void setType( NetworkDeviceType type )
	{
		this.type = type;
	}

	public Boolean getDhcp6()
	{
		return dhcp6;
	}

	public void setDhcp6( Boolean dhcp6 )
	{
		this.dhcp6 = dhcp6;
	}

	public Boolean getDhcp4()
	{
		return dhcp4;
	}

	public void setDhcp4( Boolean dhcp4 )
	{
		this.dhcp4 = dhcp4;
	}	
	
	public List<String> getInterfaces()
	{
		return interfaces;
	}

	public void setInterfaces( List<String> interfaces )
	{
		this.interfaces = interfaces;
	}
}
