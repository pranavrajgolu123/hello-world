package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiagSystemStateModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IdModel _id;

	private DiagNetworkModel diagNetworkModel;

	private List<DiagNVMEModel> nvmeList=new ArrayList<DiagNVMEModel>();

	private DiagSystemHealthModel systemHealthModel;

	private String date;

	public IdModel get_id()
	{
		return _id;
	}

	public void set_id( IdModel _id )
	{
		this._id = _id;
	}

	@Override
	public boolean equals( Object o )
	{
		DiagSystemStateModel model = ( DiagSystemStateModel ) o;

		if ( _id.get$oid() != model.get_id().get$oid() )
			return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		return this.get_id().get$oid().hashCode();
	}

	public DiagNetworkModel getDiagNetworkModel()
	{
		return diagNetworkModel;
	}

	public void setDiagNetworkModel( DiagNetworkModel diagNetworkModel )
	{
		this.diagNetworkModel = diagNetworkModel;
	}

	public List<DiagNVMEModel> getNvmeList()
	{
		return nvmeList;
	}

	public void setNvmeList( List<DiagNVMEModel> nvmeList )
	{
		this.nvmeList = nvmeList;
	}

	public DiagSystemHealthModel getSystemHealthModel()
	{
		return systemHealthModel;
	}

	public void setSystemHealthModel( DiagSystemHealthModel systemHealthModel )
	{
		this.systemHealthModel = systemHealthModel;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate( String date )
	{
		this.date = date;
	}

}
