package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class GroupModel implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private String group_name;
	
	private String group_id;
	
	private boolean local;
	
	public GroupModel()
	{
		super();
	}
	
	public GroupModel(String group_id)
	{
		super();
		this.group_id=group_id;
	
	}


	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

/*	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}*/
	
}
