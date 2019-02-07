package com.fmlb.forsaos.server.application.services;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpDeleteWithEntity extends HttpEntityEnclosingRequestBase
{

	public static final String METHOD_NAME = "DELETE";

	public String getMethod()
	{
		return METHOD_NAME;
	}

	public HttpDeleteWithEntity( final String uri )
	{
		super();
		setURI( URI.create( uri ) );
	}

	public HttpDeleteWithEntity( final URI uri )
	{
		super();
		setURI( uri );
	}

	public HttpDeleteWithEntity()
	{
		super();
	}

}
