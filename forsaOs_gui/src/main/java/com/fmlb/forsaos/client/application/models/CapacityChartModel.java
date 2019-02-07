package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class CapacityChartModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String availablePercent;

	double availableSize;

	String allocatedPercent;

	String unAllocatedPercent;

	double allocatedSize;

	double physicalSize;

	double usagableSize;

	String ampl;

	public CapacityChartModel()
	{
		super();
	}

	public CapacityChartModel( String availablePercent, double availableSize, String allocatedPercent, double allocatedSize, double physicalSize, double usagableSize, String ampl )
	{
		super();
		this.availablePercent = availablePercent;
		this.availableSize = availableSize;
		this.allocatedPercent = allocatedPercent;
		this.allocatedSize = allocatedSize;
		this.physicalSize = physicalSize;
		this.usagableSize = usagableSize;
		this.ampl = ampl;
	}

	public String getAvailablePercent()
	{
		return availablePercent;
	}

	public void setAvailablePercent( String availablePercent )
	{
		this.availablePercent = availablePercent;
	}

	public double getAvailableSize()
	{
		return availableSize;
	}

	public void setAvailableSize( double availableSize )
	{
		this.availableSize = availableSize;
	}

	public String getAllocatedPercent()
	{
		return allocatedPercent;
	}

	public void setAllocatedPercent( String allocatedPercent )
	{
		this.allocatedPercent = allocatedPercent;
	}

	public double getAllocatedSize()
	{
		return allocatedSize;
	}

	public void setAllocatedSize( double allocatedSize )
	{
		this.allocatedSize = allocatedSize;
	}

	public double getPhysicalSize()
	{
		return physicalSize;
	}

	public void setPhysicalSize( double physicalSize )
	{
		this.physicalSize = physicalSize;
	}

	public double getUsagableSize()
	{
		return usagableSize;
	}

	public void setUsagableSize( double usagableSize )
	{
		this.usagableSize = usagableSize;
	}

	public String getAmpl()
	{
		return ampl;
	}

	public void setAmpl( String ampl )
	{
		this.ampl = ampl;
	}

	public String getUnAllocatedPercent()
	{
		return unAllocatedPercent;
	}

	public void setUnAllocatedPercent( String unAllocatedPercent )
	{
		this.unAllocatedPercent = unAllocatedPercent;
	}

}
