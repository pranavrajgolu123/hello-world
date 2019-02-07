package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class VMUpdateModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer vcpu;

	private String name;

	private Long memory;

	private String memory_unit;

	private Long cores;

	private Integer sockets;

	private Integer threads;
	
	private String video_type;
	
	private String cpu_model;
	
	private String graphic_driver;

	public VMUpdateModel()
	{
		super();
	}

	public VMUpdateModel( Integer vcpu, String name, Long memory, String memory_unit, Long cores, Integer sockets, Integer threads, String video_type, String cpu_model, String graphic_driver )
	{
		super();
		this.vcpu = vcpu;
		this.name = name;
		this.memory = memory;
		this.memory_unit = memory_unit;
		this.cores = cores;
		this.sockets = sockets;
		this.threads = threads;
		this.video_type = video_type;
		this.cpu_model = cpu_model;
		this.graphic_driver = graphic_driver;
	}

	public Integer getVcpu()
	{
		return vcpu;
	}

	public void setVcpu( Integer vcpu )
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

	public Long getMemory()
	{
		return memory;
	}

	public void setMemory( Long memory )
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

	public Long getCores()
	{
		return cores;
	}

	public void setCores( Long cores )
	{
		this.cores = cores;
	}

	public Integer getSockets()
	{
		return sockets;
	}

	public void setSockets( Integer sockets )
	{
		this.sockets = sockets;
	}

	public Integer getThreads()
	{
		return threads;
	}

	public void setThreads( Integer threads )
	{
		this.threads = threads;
	}

	public String getVideo_type()
	{
		return video_type;
	}

	public void setVideo_type( String video_type )
	{
		this.video_type = video_type;
	}

	public String getCpu_model()
	{
		return cpu_model;
	}

	public void setCpu_model( String cpu_model )
	{
		this.cpu_model = cpu_model;
	}

	public String getGraphic_driver()
	{
		return graphic_driver;
	}

	public void setGraphic_driver( String graphic_driver )
	{
		this.graphic_driver = graphic_driver;
	}
}
