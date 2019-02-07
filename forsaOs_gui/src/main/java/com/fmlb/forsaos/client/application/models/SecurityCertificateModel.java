package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class SecurityCertificateModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String certificate;

	public SecurityCertificateModel() {
		
		super();
		
	}
	
	public SecurityCertificateModel(String certificate) 
	{
		super();
		this.certificate=certificate;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

}
