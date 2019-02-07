package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DiagNVMEModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DiagNVMEInfoModel nvmeInfoModel;

	private DiagNVMEIdControlModel nvmeIdControlModel;

	private DiagNVMESmartLogModel nvmeSmartLogModel;

	private DiagNVMEErrorLogModel nvmeErrorLogModel;

	public DiagNVMEInfoModel getNvmeInfoModel()
	{
		return nvmeInfoModel;
	}

	public void setNvmeInfoModel( DiagNVMEInfoModel nvmeInfoModel )
	{
		this.nvmeInfoModel = nvmeInfoModel;
	}

	public DiagNVMEIdControlModel getNvmeIdControlModel()
	{
		return nvmeIdControlModel;
	}

	public void setNvmeIdControlModel( DiagNVMEIdControlModel nvmeIdControlModel )
	{
		this.nvmeIdControlModel = nvmeIdControlModel;
	}

	public DiagNVMESmartLogModel getNvmeSmartLogModel()
	{
		return nvmeSmartLogModel;
	}

	public void setNvmeSmartLogModel( DiagNVMESmartLogModel nvmeSmartLogModel )
	{
		this.nvmeSmartLogModel = nvmeSmartLogModel;
	}

	public DiagNVMEErrorLogModel getNvmeErrorLogModel()
	{
		return nvmeErrorLogModel;
	}

	public void setNvmeErrorLogModel( DiagNVMEErrorLogModel nvmeErrorLogModel )
	{
		this.nvmeErrorLogModel = nvmeErrorLogModel;
	}

}
