package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

public class DiagRAMErrorHistoryModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DiagRAMErrorModel> ramErrorModelList;

	public List<DiagRAMErrorModel> getRamErrorModelList()
	{
		return ramErrorModelList;
	}

	public void setRamErrorModelList( List<DiagRAMErrorModel> ramErrorModelList )
	{
		this.ramErrorModelList = ramErrorModelList;
	}
}
