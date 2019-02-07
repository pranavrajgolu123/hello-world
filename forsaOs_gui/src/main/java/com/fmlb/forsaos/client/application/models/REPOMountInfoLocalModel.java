package com.fmlb.forsaos.client.application.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class REPOMountInfoLocalModel extends REPOMountInfoModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fstype;

	@JsonProperty( "maj:min" )
	private String maj_min;

	private String model;

	private String rm;

	private String ro;

	private String serial;

	private String size;

	private String type;

	private String uuid;

	private String[] partitions;

	public REPOMountInfoLocalModel()
	{
		super();
	}

	public String getFstype()
	{
		return fstype;
	}

	public void setFstype( String fstype )
	{
		this.fstype = fstype;
	}

	public String getMaj_min()
	{
		return maj_min;
	}

	public void setMaj_min( String maj_min )
	{
		this.maj_min = maj_min;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel( String model )
	{
		this.model = model;
	}

	public String getRm()
	{
		return rm;
	}

	public void setRm( String rm )
	{
		this.rm = rm;
	}

	public String getRo()
	{
		return ro;
	}

	public void setRo( String ro )
	{
		this.ro = ro;
	}

	public String getSerial()
	{
		return serial;
	}

	public void setSerial( String serial )
	{
		this.serial = serial;
	}

	public String getSize()
	{
		return size;
	}

	public void setSize( String size )
	{
		this.size = size;
	}

	public String getType()
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid( String uuid )
	{
		this.uuid = uuid;
	}

	public String[] getPartitions()
	{
		return partitions;
	}

	public void setPartitions( String[] partitions )
	{
		this.partitions = partitions;
	}

}
