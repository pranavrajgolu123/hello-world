package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import gwt.material.design.client.data.HasDataCategory;

public class LEMModel implements HasDataCategory, Serializable,ComboBoxModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String lemName;

	private String quantity;

	private Long size;

	private MemorySizeType memSizeType;

	private long sectorSize;

	private OSFlagType osFl;

	private String VMname;

	private String assignToVM;

	private long noOfsnapShots;

	private String parent;

	private String id;

	private String dnaAmplificationPercent;

	private String mode;

	private String status;

	private String avlCntrSpace;

	private boolean readonly;

	public LEMModel()
	{
		super();
	}

	public LEMModel( String lemName, String quantity, Long size, MemorySizeType memSizeType, long sectorSize, OSFlagType osFl, String assignToVM, String id, String vmName, long numbeOfSnaps, boolean readonly )
	{
		super();
		this.lemName = lemName;
		this.quantity = quantity;
		this.size = size;
		this.memSizeType = memSizeType;
		this.sectorSize = sectorSize;
		this.osFl = osFl;
		this.assignToVM = assignToVM;
		this.id = id;
		this.VMname = vmName;
		this.noOfsnapShots = numbeOfSnaps;
		this.readonly = readonly;
		if ( readonly )
		{
			setMode( "Read" );
		}
		else
		{
			setMode( "Read/Write" );
		}
	}

	public String getAvlCntrSpace()
	{
		return avlCntrSpace;
	}

	public void setAvlCntrSpace( String avlCntrSpace )
	{
		this.avlCntrSpace = avlCntrSpace;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus( String status )
	{
		this.status = status;
	}

	public String getDnaAmplificationPercent()
	{
		return dnaAmplificationPercent;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode( String mode )
	{
		this.mode = mode;
	}

	public void setDnaAmplificationPercent( String dnaAmplificationPercent )
	{
		this.dnaAmplificationPercent = dnaAmplificationPercent;
	}

	public String getParent()
	{
		return parent;
	}

	public void setParent( String parent )
	{
		this.parent = parent;
	}

	public long getNoOfsnapShots()
	{
		return noOfsnapShots;
	}

	public void setNoOfsnapShots( long noOfsnapShots )
	{
		this.noOfsnapShots = noOfsnapShots;
	}

	public String getVMname()
	{
		return VMname;
	}

	public void setVMname( String vMname )
	{
		VMname = vMname;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getLemName()
	{
		return lemName;
	}

	public void setLemName( String lemName )
	{
		this.lemName = lemName;
	}

	public String getQuantity()
	{
		return quantity;
	}

	public void setQuantity( String quantity )
	{
		this.quantity = quantity;
	}

	public Long getSize()
	{
		return size;
	}

	public void setSize( Long size )
	{
		this.size = size;
	}

	public MemorySizeType getMemSizeType()
	{
		return memSizeType;
	}

	public void setMemSizeType( MemorySizeType memSizeType )
	{
		this.memSizeType = memSizeType;
	}

	public long getSectorSize()
	{
		return sectorSize;
	}

	public void setSectorSize( long sectorSize )
	{
		this.sectorSize = sectorSize;
	}

	public OSFlagType getOsFl()
	{
		return osFl;
	}

	public void setOsFl( OSFlagType osFl )
	{
		this.osFl = osFl;
	}

	public String getAssignToVM()
	{
		return assignToVM;
	}

	public void setAssignToVM( String assignToVM )
	{
		this.assignToVM = assignToVM;
	}

	public boolean isReadonly()
	{
		return readonly;
	}

	public void setReadonly( boolean readonly )
	{
		this.readonly = readonly;
	}

	@Override
	public boolean equals( Object o )
	{
		LEMModel lem = ( LEMModel ) o;

		if ( id != lem.id )
			return false;

		return false;
	}

	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}

	@Override
	public String getDataCategory()
	{
		return null;
	}

	@Override
	public String getDisplayName()
	{
		// TODO Auto-generated method stub
		return this.lemName;
	}

}
