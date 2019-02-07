package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class SystemStatusChartModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double memUsage;

	private double cpuUsage;

	private double coreTemp;

	private double coreTempPerc;

	public SystemStatusChartModel()
	{
		super();
	}

	public SystemStatusChartModel( double memUsage, double cpuUsage, double coreTemp, double coreTempPerc )
	{
		super();
		this.memUsage = memUsage;
		this.cpuUsage = cpuUsage;
		this.coreTemp = coreTemp;
		this.coreTempPerc = coreTempPerc;
	}

	public double getMemUsage()
	{
		return memUsage;
	}

	public void setMemUsage( double memUsage )
	{
		this.memUsage = memUsage;
	}

	public double getCpuUsage()
	{
		return cpuUsage;
	}

	public void setCpuUsage( double cpuUsage )
	{
		this.cpuUsage = cpuUsage;
	}

	public double getCoreTemp()
	{
		return coreTemp;
	}

	public void setCoreTemp( double coreTemp )
	{
		this.coreTemp = coreTemp;
	}

	public double getCoreTempPerc()
	{
		return coreTempPerc;
	}

	public void setCoreTempPerc( double coreTempPerc )
	{
		this.coreTempPerc = coreTempPerc;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
