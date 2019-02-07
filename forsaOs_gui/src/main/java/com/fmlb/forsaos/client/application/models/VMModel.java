package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

import com.fmlb.forsaos.shared.application.utility.VMState;

import gwt.material.design.client.data.HasDataCategory;

public class VMModel implements HasDataCategory, Serializable
{

	private static final long serialVersionUID = 1L;

	private IdModel _id;

	private long cores;

	private Long memory;

	private long current_memory;

	private String memory_unit;

	private String slot;

	private List<String> bridges;

	private List<String> macs;

	/**
	 * LEM ids
	 */
	private List<String> disks;

	private List<LEMModel> lemModels;

	/**
	 * ISO mount path
	 */
	private List<String> cdroms;

	private String video_type;

	private String cpu_model;

	private VMState vmState;

	private String name;

	private MemorySizeType memSizeType;

	private long noOfLEMs;

	private int vcpu;

	private int sockets;

	private int threads;
	
	private int status;
	
	private String graphic_driver;
	
	private String graphic_port;
	
	private String graphic_ip;
	
	private String[] networks;

	public VMModel()
	{
		super();
	}

	public VMModel( long cores, Long memory, long current_memory, String memory_unit, String slot, List<String> bridges, List<String> macs, List<String> disks, List<LEMModel> lemModels, List<String> cdroms, String video_type, String cpu_model, VMState vmState, String name, MemorySizeType memSizeType, long noOfLEMs, String id, int vcpu, int sockets, int threads, int status )
	{
		super();
		this.cores = cores;
		this.memory = memory;
		this.current_memory = current_memory;
		this.memory_unit = memory_unit;
		this.slot = slot;
		this.bridges = bridges;
		this.macs = macs;
		this.disks = disks;
		this.lemModels = lemModels;
		this.cdroms = cdroms;
		this.video_type = video_type;
		this.cpu_model = cpu_model;
		this.vmState = vmState;
		this.name = name;
		this.memSizeType = memSizeType;
		this.noOfLEMs = noOfLEMs;
		this.vcpu = vcpu;
		this.sockets = sockets;
		this.threads = threads;
		this.status = status;
	}

	public VMModel( String vmName, long noOfCores, Long memorySize, MemorySizeType memSizeType, long noOfLEMs, String id )
	{
		super();
		this.name = vmName;
		this.cores = noOfCores;
		this.memory = memorySize;
		this.memSizeType = memSizeType;
		this.noOfLEMs = noOfLEMs;
	}

	public String getVmName()
	{
		return name;
	}

	public void setVmName( String vmName )
	{
		this.name = vmName;
	}

	public long getNoOfCores()
	{
		return cores;
	}

	public void setNoOfCores( long noOfCores )
	{
		this.cores = noOfCores;
	}

	public Long getMemorySize()
	{
		return memory;
	}

	public void setMemorySize( Long memorySize )
	{
		this.memory = memorySize;
	}

	public MemorySizeType getMemSizeType()
	{
		return memSizeType;
	}

	public void setMemSizeType( MemorySizeType memSizeType )
	{
		this.memSizeType = memSizeType;
	}

	public long getNoOfLEMs()
	{
		return noOfLEMs;
	}

	public void setNoOfLEMs( long noOfLEMs )
	{
		this.noOfLEMs = noOfLEMs;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	@Override
	public boolean equals( Object o )
	{
		VMModel lem = ( VMModel ) o;

		if ( _id.get$oid() != lem.get_id().get$oid())
			return false;

		return false;
	}

	@Override
	public int hashCode()
	{
		return this.get_id().get$oid().hashCode();
	}

	@Override
	public String getDataCategory()
	{
		return null;
	}

	public VMState getVmState()
	{
		return vmState;
	}

	public void setVmState( VMState vmState )
	{
		this.vmState = vmState;
	}

	public long getCores()
	{
		return cores;
	}

	public void setCores( long cores )
	{
		this.cores = cores;
	}

	public Long getMemory()
	{
		return memory;
	}

	public void setMemory( Long memory )
	{
		this.memory = memory;
	}

	public long getCurrent_memory()
	{
		return current_memory;
	}

	public void setCurrent_memory( long current_memory )
	{
		this.current_memory = current_memory;
	}

	public String getMemory_unit()
	{
		return memory_unit;
	}

	public void setMemory_unit( String memory_unit )
	{
		this.memory_unit = memory_unit;
	}

	public String getSlot()
	{
		return slot;
	}

	public void setSlot( String slot )
	{
		this.slot = slot;
	}

	public List<String> getBridges()
	{
		return bridges;
	}

	public void setBridges( List<String> bridges )
	{
		this.bridges = bridges;
	}

	public List<String> getMacs()
	{
		return macs;
	}

	public void setMacs( List<String> macs )
	{
		this.macs = macs;
	}

	public List<String> getDisks()
	{
		return disks;
	}

	public void setDisks( List<String> disks )
	{
		this.disks = disks;
	}

	public List<LEMModel> getLemModels()
	{
		return lemModels;
	}

	public void setLemModels( List<LEMModel> lemModels )
	{
		this.lemModels = lemModels;
	}

	public List<String> getCdroms()
	{
		return cdroms;
	}

	public void setCdroms( List<String> cdroms )
	{
		this.cdroms = cdroms;
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

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public IdModel get_id()
	{
		return _id;
	}

	public void set_id( IdModel _id )
	{
		this._id = _id;
	}

	public int getVcpu()
	{
		return vcpu;
	}

	public void setVcpu( int vcpu )
	{
		this.vcpu = vcpu;
	}

	public int getSockets()
	{
		return sockets;
	}

	public void setSockets( int sockets )
	{
		this.sockets = sockets;
	}

	public int getThreads()
	{
		return threads;
	}

	public void setThreads( int threads )
	{
		this.threads = threads;
	}
	public int getStatus()
	{
		return status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

	public String getGraphic_driver()
	{
		return graphic_driver;
	}

	public void setGraphic_driver( String graphic_driver )
	{
		this.graphic_driver = graphic_driver;
	}

	public String getGraphic_port()
	{
		return graphic_port;
	}

	public void setGraphic_port( String graphic_port )
	{
		this.graphic_port = graphic_port;
	}

	public String getGraphic_ip()
	{
		return graphic_ip;
	}

	public void setGraphic_ip( String graphic_ip )
	{
		this.graphic_ip = graphic_ip;
	}

	public String[] getNetworks()
	{
		return networks;
	}

	public void setNetworks( String[] networks )
	{
		this.networks = networks;
	}
	
}
