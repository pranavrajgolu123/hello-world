package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class CreateMultiLEMModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LEMJSONModel[] multi;

	public CreateMultiLEMModel()
	{
		super();
	}

	public CreateMultiLEMModel( LEMJSONModel[] multi )
	{
		super();
		this.multi = multi;
	}

	public LEMJSONModel[] getMulti()
	{
		return multi;
	}

	public void setMulti( LEMJSONModel[] multi )
	{
		this.multi = multi;
	}
}
