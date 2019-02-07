package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class VMCreateModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long vcpu;

	private String name;

	private long memory;

	private String memory_unit;

	private String graphic_port;

	private String graphic_ip = "0.0.0.0";

	private String graphic_driver = "vnc";

	private boolean tablet = true;

	private NameModel[] networks;

	private String[] cdroms;

	public VMCreateModel()
	{
		super();
	}

	public VMCreateModel( long vcpu, String name, long memory, String memory_unit, String graphic_port, NameModel[] networks, String[] cdroms )
	{
		super();
		this.vcpu = vcpu;
		this.name = name;
		this.memory = memory;
		this.memory_unit = memory_unit;
		this.graphic_port = graphic_port;
		this.networks = networks;
		this.cdroms = cdroms;
	}

	public long getVcpu()
	{
		return vcpu;
	}

	public void setVcpu( long vcpu )
	{
		this.vcpu = vcpu;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public long getMemory()
	{
		return memory;
	}

	public void setMemory( long memory )
	{
		this.memory = memory;
	}

	public String getMemory_unit()
	{
		return memory_unit;
	}

	public void setMemory_unit( String memory_unit )
	{
		this.memory_unit = memory_unit;
	}

	public boolean isTablet()
	{
		return tablet;
	}

	public void setTablet( boolean tablet )
	{
		this.tablet = tablet;
	}

	public String getGraphic_ip()
	{
		return graphic_ip;
	}

	public void setGraphic_ip( String graphic_ip )
	{
		this.graphic_ip = graphic_ip;
	}

	public String getGraphic_port()
	{
		return graphic_port;
	}

	public void setGraphic_port( String graphic_port )
	{
		this.graphic_port = graphic_port;
	}

	public String getGraphic_driver()
	{
		return graphic_driver;
	}

	public void setGraphic_driver( String graphic_driver )
	{
		this.graphic_driver = graphic_driver;
	}

	public NameModel[] getNetworks()
	{
		return networks;
	}

	public void setNetworks( NameModel[] networks )
	{
		this.networks = networks;
	}

	public String[] getCdroms()
	{
		return cdroms;
	}

	public void setCdroms( String[] cdroms )
	{
		this.cdroms = cdroms;
	}

}
