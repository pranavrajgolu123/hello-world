package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.List;

public class DiagNetworkModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DiagNetEthToolScanModel> ethtool_scan;

	public DiagNetworkModel()
	{
		super();
	}

	public DiagNetworkModel( List<DiagNetEthToolScanModel> ethtool_scan )
	{
		super();
		this.ethtool_scan = ethtool_scan;
	}

	public List<DiagNetEthToolScanModel> getEthtool_scan()
	{
		return ethtool_scan;
	}

	public void setEthtool_scan( List<DiagNetEthToolScanModel> ethtool_scan )
	{
		this.ethtool_scan = ethtool_scan;
	}

}
