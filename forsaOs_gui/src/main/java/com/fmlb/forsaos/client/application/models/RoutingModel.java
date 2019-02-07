package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class RoutingModel implements Serializable{
	
	private String destination;

	private String subnetmask;
	
	private String gateway;
	
	private static final long serialVersionUID = 1L;
	
	public RoutingModel()
	{
		super();
	}

	public RoutingModel( String destination, String subnetmask, String gateway )
	{
		super();
		this.destination = destination;
		this.subnetmask = subnetmask;
		this.gateway = gateway;
	}


	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSubnetmask() {
		return subnetmask;
	}

	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

}
