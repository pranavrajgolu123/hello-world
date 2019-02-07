package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DiagNVMEInfoModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String devicePath;

	private String firmware;

	private int index;

	private String modelNumber;

	private String productName;

	private String serialNumber;

	private long usedBytes;

	private long maximumLBA;

	private long physicalSize;

	private long sectorSize;

	public DiagNVMEInfoModel()
	{
		super();
	}

	public DiagNVMEInfoModel( String devicePath, String firmware, int index, String modelNumber, String productName, String serialNumber, long usedBytes, long maximumLBA, long physicalSize, long sectorSize )
	{
		super();
		this.devicePath = devicePath;
		this.firmware = firmware;
		this.index = index;
		this.modelNumber = modelNumber;
		this.productName = productName;
		this.serialNumber = serialNumber;
		this.usedBytes = usedBytes;
		this.maximumLBA = maximumLBA;
		this.physicalSize = physicalSize;
		this.sectorSize = sectorSize;
	}

	public String getDevicePath()
	{
		return devicePath;
	}

	public void setDevicePath( String devicePath )
	{
		this.devicePath = devicePath;
	}

	public String getFirmware()
	{
		return firmware;
	}

	public void setFirmware( String firmware )
	{
		this.firmware = firmware;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex( int index )
	{
		this.index = index;
	}

	public String getModelNumber()
	{
		return modelNumber;
	}

	public void setModelNumber( String modelNumber )
	{
		this.modelNumber = modelNumber;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName( String productName )
	{
		this.productName = productName;
	}

	public String getSerialNumber()
	{
		return serialNumber;
	}

	public void setSerialNumber( String serialNumber )
	{
		this.serialNumber = serialNumber;
	}

	public long getUsedBytes()
	{
		return usedBytes;
	}

	public void setUsedBytes( long usedBytes )
	{
		this.usedBytes = usedBytes;
	}

	public long getMaximumLBA()
	{
		return maximumLBA;
	}

	public void setMaximumLBA( long maximumLBA )
	{
		this.maximumLBA = maximumLBA;
	}

	public long getPhysicalSize()
	{
		return physicalSize;
	}

	public void setPhysicalSize( long physicalSize )
	{
		this.physicalSize = physicalSize;
	}

	public long getSectorSize()
	{
		return sectorSize;
	}

	public void setSectorSize( long sectorSize )
	{
		this.sectorSize = sectorSize;
	}

}
