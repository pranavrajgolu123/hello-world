package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class UpdateRTMModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;
	String new_name;
	public UpdateRTMModel() {
		super();
	}
	
	public UpdateRTMModel(String name, String new_name) {
		super();
		this.name = name;
		this.new_name = new_name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNew_name() {
		return new_name;
	}
	
	public void setNew_name(String new_name) {
		this.new_name = new_name;
	}

}
