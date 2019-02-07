package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class SendAlertAllUserModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String subject;
	
	private String msg;
	
	private String level;
	
	public SendAlertAllUserModel()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public SendAlertAllUserModel(String subject,String msg,String level)
	{
		super();
		this.subject = subject;
		this.msg=msg;
		this.level=level;
	
	
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
