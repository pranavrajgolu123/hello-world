package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UserSessionObject implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sessionId = "";

	
	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId( String sessionId )
	{
		this.sessionId = sessionId;
	}

	public UserSessionObject()
	{
		super();
	}
	public UserSessionObject( String sessionId )
	{
		super();
		this.sessionId = sessionId;
	}

}
