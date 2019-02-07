package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class IdModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String $oid;

	public IdModel()
	{
		super();
	}

	public IdModel( String $oid )
	{
		super();
		this.$oid = $oid;
	}

	public String get$oid()
	{
		return $oid;
	}

	public void set$oid( String $oid )
	{
		this.$oid = $oid;
	}

}
