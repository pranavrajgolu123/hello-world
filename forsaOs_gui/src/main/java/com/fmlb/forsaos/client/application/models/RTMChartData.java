package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class RTMChartData implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long read;

	private long write;

	private double readThrough;

	private double writeThrough;

	private double cur_total_size;

	private double cur_usage_size;

	private long timepoint;

	public RTMChartData()
	{
		super();
	}

	public RTMChartData( long read, long write, double cur_total_size, double cur_usage_size, long timepoint )
	{
		super();
		// this.read = read * 4096;
		// this.write = write * 4096;
		this.read = read;
		this.write = write;
		this.cur_total_size = cur_total_size;
		this.cur_usage_size = cur_usage_size;
		this.timepoint = timepoint * 1000;
		//this.readThrough = this.read / 60;
		//this.writeThrough = this.write / 60;
	}

	public long getRead()
	{
		return read;
	}

	public void setRead( long read )
	{
		this.read = read * 4096;
	}

	public long getWrite()
	{
		return write;
	}

	public void setWrite( long write )
	{
		this.write = write * 4096;
	}

	public long getTimepoint()
	{
		return timepoint;
	}

	public void setTimepoint( long timepoint )
	{
		this.timepoint = timepoint * 1000;
	}

	public double getCur_total_size()
	{
		return cur_total_size;
	}

	public void setCur_total_size( double cur_total_size )
	{
		this.cur_total_size = cur_total_size;
	}

	public double getCur_usage_size()
	{
		return cur_usage_size;
	}

	public void setCur_usage_size( double cur_usage_size )
	{
		this.cur_usage_size = cur_usage_size;
	}

	public double getReadThrough()
	{
		return readThrough;
	}

	public void setReadThrough( double readThrough )
	{
		this.readThrough = readThrough;
	}

	public double getWriteThrough()
	{
		return writeThrough;
	}

	public void setWriteThrough( double writeThrough )
	{
		this.writeThrough = writeThrough;
	}

}
