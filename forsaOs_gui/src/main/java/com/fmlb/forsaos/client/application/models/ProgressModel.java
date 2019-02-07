package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class ProgressModel implements Serializable

{
	private static final long serialVersionUID = 1L;

	private String speed = "";

	private String message = "";

	private double progress;

	public ProgressModel()
	{
		super();
	}

	public ProgressModel( String speed, String message, double progress )
	{
		super();

		this.speed = speed;
		this.message = message;
		this.progress = progress;
	}

	public String getSpeed()
	{
		return speed;
	}

	public void setSpeed( String speed )
	{
		this.speed = speed;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage( String message )
	{
		this.message = message;
	}

	public double getProgress()
	{
		return progress;
	}

	public void setProgress( double progress )
	{
		this.progress = progress;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
