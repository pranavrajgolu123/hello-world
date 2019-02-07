package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class PartitionModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer type;

	private Integer start;

	private Integer stop;

	private String mount_point;

	private IdModel _id;

	private String store_id;

	private String name;

	private Integer part_num;

	private String part_id;

	public PartitionModel()
	{
		super();
	}

	public PartitionModel( Integer type, Integer start, Integer stop, String mount_point, IdModel _id, String store_id, String name, Integer part_num, String part_id )
	{
		super();
		this.type = type;
		this.start = start;
		this.stop = stop;
		this.mount_point = mount_point;
		this._id = _id;
		this.store_id = store_id;
		this.name = name;
		this.part_num = part_num;
		this.part_id = part_id;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType( Integer type )
	{
		this.type = type;
	}

	public Integer getStart()
	{
		return start;
	}

	public void setStart( Integer start )
	{
		this.start = start;
	}

	public Integer getStop()
	{
		return stop;
	}

	public void setStop( Integer stop )
	{
		this.stop = stop;
	}

	public String getMount_point()
	{
		return mount_point;
	}

	public void setMount_point( String mount_point )
	{
		this.mount_point = mount_point;
	}

	public IdModel get_id()
	{
		return _id;
	}

	public void set_id( IdModel _id )
	{
		this._id = _id;
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

	public Integer getPart_num()
	{
		return part_num;
	}

	public void setPart_num( Integer part_num )
	{
		this.part_num = part_num;
	}

	public String getPart_id()
	{
		return part_id;
	}

	public void setPart_id( String part_id )
	{
		this.part_id = part_id;
	}

}
