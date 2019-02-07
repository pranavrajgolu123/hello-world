package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

public class SendTestEmailModel implements Serializable
{
	
	private static final long serialVersionUID = 1L;


	private List<String> receiver ;
	
	
	private String title;
	
	private String body;
	
	private String server;
	
	public SendTestEmailModel()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public SendTestEmailModel(List<String> receiver,String title,String body,String server)
	{
		super();
		this.receiver = receiver;
		this.title=title;
		this.body=body;
		this.server=server;
	
	
	}




	public List<String> getReceiver() {
		return receiver;
	}

	public void setReceiver(List<String> receiver) {
		this.receiver = receiver;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}

