package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import gwt.material.design.client.data.HasDataCategory;

public class BlinkModel implements HasDataCategory, Serializable
{
	private static final long serialVersionUID = 1L;

	private Boolean vm_session = false;

	private Integer type;

	private long timestamp;

	private Boolean sdn = false;

	private Boolean keystone = false;

	private String id = "";

	private Boolean lnet = false;

	private Boolean networkd = false;

	private String store_id = "";

	private String name = "";

	private String store_path = "";

	private String schedule_id = "";

	private String date;

	public BlinkModel()
	{
		super();
	}

	public BlinkModel( Boolean vm_session, Integer type, long timestamp, Boolean sdn, Boolean keystone, String id, Boolean lnet, Boolean networkd, String store_id, String name, String store_path, String schedule_id )
	{
		super();
		this.vm_session = vm_session;
		this.type = type;
		this.timestamp = timestamp * 1000;
		this.sdn = sdn;
		this.keystone = keystone;
		this.id = id;
		this.lnet = lnet;
		this.networkd = networkd;
		this.store_id = store_id;
		this.name = name;
		this.store_path = store_path;
		this.schedule_id = schedule_id;

	}

	public Boolean getVm_session()
	{
		return vm_session;
	}

	public void setVm_session( Boolean vm_session )
	{
		this.vm_session = vm_session;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType( Integer type )
	{
		this.type = type;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp( long timestamp )
	{
		this.timestamp = timestamp;
	}

	public Boolean getSdn()
	{
		return sdn;
	}

	public void setSdn( Boolean sdn )
	{
		this.sdn = sdn;
	}

	public Boolean getKeystone()
	{
		return keystone;
	}

	public void setKeystone( Boolean keystone )
	{
		this.keystone = keystone;
	}

	public Boolean getLnet()
	{
		return lnet;
	}

	public void setLnet( Boolean lnet )
	{
		this.lnet = lnet;
	}

	public Boolean getNetworkd()
	{
		return networkd;
	}

	public void setNetworkd( Boolean networkd )
	{
		this.networkd = networkd;
	}

	public String getStore_id()
	{
		return store_id;
	}

	public void setStore_id( String store_id )
	{
		this.store_id = store_id;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getStore_path()
	{
		return store_path;
	}

	public void setStore_path( String store_path )
	{
		this.store_path = store_path;
	}

	public String getSchedule_id()
	{
		return schedule_id;
	}

	public void setSchedule_id( String schedule_id )
	{
		this.schedule_id = schedule_id;
	}

	@Override
	public boolean equals( Object o )
	{
		BlinkModel blink = ( BlinkModel ) o;

		if ( id != blink.id )
			return false;

		return false;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate( String date )
	{
		this.date = date;
	}

	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}

	@Override
	public String getDataCategory()
	{
		return null;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
