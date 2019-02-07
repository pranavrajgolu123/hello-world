package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DiagRAMErrorModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IdModel _id;

	private String errorAddr;

	private long timeStamp;

	private int socket;

	private int cpu;

	private int imc;

	private int channel;

	private int mode;

	private int type;

	public IdModel get_id()
	{
		return _id;
	}

	public void set_id( IdModel _id )
	{
		this._id = _id;
	}

	public String getErrorAddr()
	{
		return errorAddr;
	}

	public void setErrorAddr( String errorAddr )
	{
		this.errorAddr = errorAddr;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp( long timeStamp )
	{
		this.timeStamp = timeStamp;
	}

	public int getSocket()
	{
		return socket;
	}

	public void setSocket( int socket )
	{
		this.socket = socket;
	}

	public int getCpu()
	{
		return cpu;
	}

	public void setCpu( int cpu )
	{
		this.cpu = cpu;
	}

	public int getImc()
	{
		return imc;
	}

	public void setImc( int imc )
	{
		this.imc = imc;
	}

	public int getChannel()
	{
		return channel;
	}

	public void setChannel( int channel )
	{
		this.channel = channel;
	}

	public int getMode()
	{
		return mode;
	}

	public void setMode( int mode )
	{
		this.mode = mode;
	}

	public int getType()
	{
		return type;
	}

	public void setType( int type )
	{
		this.type = type;
	}

}
