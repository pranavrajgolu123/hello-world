package com.fmlb.forsaos.client.application.models;


import java.io.Serializable;

public class AddEmailModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String email;
	
	private String level;
	
	public AddEmailModel()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public AddEmailModel(String email,String level)
	{
		super();
		this.email = email;
		this.level=level;
	
	
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}

