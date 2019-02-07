package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DiagNetEthToolScanModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String interface_dev;

	private String mac_address;

	private String operstate;

	private String carrier;

	private String carrier_up_count;

	private String carrier_down_count;

	private String carrier_changes;

	private int ethtool_total_errors;

	private DiagEthToolErrorModel ethtool_error;

	private DiagEthToolDevInfoModel ethtool_devinfo;

	public DiagNetEthToolScanModel()
	{
		super();
	}

	public DiagNetEthToolScanModel( String interface_dev, String mac_address, String operstate, String carrier, String carrier_up_count, String carrier_down_count, String carrier_changes, int ethtool_total_errors, DiagEthToolErrorModel ethtool_error, DiagEthToolDevInfoModel ethtool_devinfo )
	{
		super();
		this.interface_dev = interface_dev;
		this.mac_address = mac_address;
		this.operstate = operstate;
		this.carrier = carrier;
		this.carrier_up_count = carrier_up_count;
		this.carrier_down_count = carrier_down_count;
		this.carrier_changes = carrier_changes;
		this.ethtool_total_errors = ethtool_total_errors;
		this.ethtool_error = ethtool_error;
		this.ethtool_devinfo = ethtool_devinfo;
	}

	public int getEthtool_total_errors()
	{
		return ethtool_total_errors;
	}

	public void setEthtool_total_errors( int ethtool_total_errors )
	{
		this.ethtool_total_errors = ethtool_total_errors;
	}

	public String getInterface_dev()
	{
		return interface_dev;
	}

	public void setInterface_dev( String interface_dev )
	{
		this.interface_dev = interface_dev;
	}

	public String getMac_address()
	{
		return mac_address;
	}

	public void setMac_address( String mac_address )
	{
		this.mac_address = mac_address;
	}

	public String getOperstate()
	{
		return operstate;
	}

	public void setOperstate( String operstate )
	{
		this.operstate = operstate;
	}

	public String getCarrier()
	{
		return carrier;
	}

	public void setCarrier( String carrier )
	{
		this.carrier = carrier;
	}

	public String getCarrier_up_count()
	{
		return carrier_up_count;
	}

	public void setCarrier_up_count( String carrier_up_count )
	{
		this.carrier_up_count = carrier_up_count;
	}

	public String getCarrier_down_count()
	{
		return carrier_down_count;
	}

	public void setCarrier_down_count( String carrier_down_count )
	{
		this.carrier_down_count = carrier_down_count;
	}

	public String getCarrier_changes()
	{
		return carrier_changes;
	}

	public void setCarrier_changes( String carrier_changes )
	{
		this.carrier_changes = carrier_changes;
	}

	public DiagEthToolErrorModel getEthtool_error()
	{
		return ethtool_error;
	}

	public void setEthtool_error( DiagEthToolErrorModel ethtool_error )
	{
		this.ethtool_error = ethtool_error;
	}

	public DiagEthToolDevInfoModel getEthtool_devinfo()
	{
		return ethtool_devinfo;
	}

	public void setEthtool_devinfo( DiagEthToolDevInfoModel ethtool_devinfo )
	{
		this.ethtool_devinfo = ethtool_devinfo;
	}

}
