package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UpdateSMTPConfigrationModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String server;
	
	private String port;
	
	private String user;
	
	private String password;
	
	public UpdateSMTPConfigrationModel()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateSMTPConfigrationModel(String server,String port,String user,String password)
	{
		super();
		this.server = server;
		this.port=port;
		this.user = user;
		this.password=password;
	
	
	}


	


	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
