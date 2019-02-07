package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class CurrentUser implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isLoggedIn = false;

	private String userName = "";

	private String password = "";

	private String windowLocation = "";

	public CurrentUser()
	{
		super();
	}

	public CurrentUser( boolean isLoggedIn, String userName, String password, String windowLocation )
	{
		super();
		this.isLoggedIn = isLoggedIn;
		this.userName = userName;
		this.password = password;
		this.windowLocation = windowLocation;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName( String userName )
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public boolean isLoggedIn()
	{
		return isLoggedIn;
	}

	public void setLoggedIn( boolean isLoggedIn )
	{
		this.isLoggedIn = isLoggedIn;
	}

	public String getWindowLocation()
	{
		return windowLocation;
	}

	public void setWindowLocation( String windowLocation )
	{
		this.windowLocation = "http://"+windowLocation;
	}

}
