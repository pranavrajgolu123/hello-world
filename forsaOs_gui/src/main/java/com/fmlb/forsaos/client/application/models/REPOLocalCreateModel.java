package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOLocalCreateModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _id;

	private String name;

	private String protocol;

	private Boolean automount;
	
	private String purpose;

	private REPOMountInfoLocalModel[] mountinfo;

	private REPOParameterLocalModel parameters;

	public REPOLocalCreateModel()
	{
		super();
	}
	
	public REPOLocalCreateModel( String _id, String name, String protocol, Boolean automount, REPOMountInfoLocalModel[] mountinfo, REPOParameterLocalModel parameters, String purpose )
	{
		super();
		this._id = _id;
		this.name = name;
		this.protocol = protocol;
		this.automount = automount;
		this.mountinfo = mountinfo;
		this.parameters = parameters;
		this.purpose = purpose;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public void setProtocol( String protocol )
	{
		this.protocol = protocol;
	}

	public String get_id()
	{
		return _id;
	}

	public void set_id( String _id )
	{
		this._id = _id;
	}

	public REPOMountInfoLocalModel[] getMountinfo()
	{
		return mountinfo;
	}

	public void setMountinfo( REPOMountInfoLocalModel[] mountinfo )
	{
		this.mountinfo = mountinfo;
	}

	public Boolean getAutomount()
	{
		return automount;
	}

	public void setAutomount( Boolean automount )
	{
		this.automount = automount;
	}

	public REPOParameterLocalModel getParameters()
	{
		return parameters;
	}

	public void setParameters( REPOParameterLocalModel parameters )
	{
		this.parameters = parameters;
	}

	public String getPurpose()
	{
		return purpose;
	}

	public void setPurpose( String purpose )
	{
		this.purpose = purpose;
	}
}
