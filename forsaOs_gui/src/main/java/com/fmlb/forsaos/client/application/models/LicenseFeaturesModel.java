package com.fmlb.forsaos.client.application.models;

public class LicenseFeaturesModel {
      
	private String feature;

	private String status;
	
	public LicenseFeaturesModel()
	{
		super();
	}
	
	public LicenseFeaturesModel(String feature,String status)
	{
		super();
		this.feature = feature;
		this.status = status;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
