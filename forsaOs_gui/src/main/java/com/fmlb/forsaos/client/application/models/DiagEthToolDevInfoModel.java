package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiagEthToolDevInfoModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String driver;

	private String version;

	@JsonProperty( "firmware-version" )
	private String firmware_version;

	@JsonProperty( "expansion-rom-version" )
	private String expansion_rom_version;

	@JsonProperty( "bus-info" )
	private String bus_info;

	@JsonProperty( "supports-statistics" )
	private String supports_statistics;

	@JsonProperty( "supports-test" )
	private String supports_test;

	@JsonProperty( "supports-eeprom-access" )
	private String supports_eeprom_access;

	@JsonProperty( "supports-register-dump" )
	private String supports_register_dump;

	@JsonProperty( "supports-priv-flags" )
	private String supports_priv_flags;

	public DiagEthToolDevInfoModel()
	{
		super();
	}

	public DiagEthToolDevInfoModel( String driver, String version, String firmware_version, String expansion_rom_version, String bus_info, String supports_statistics, String supports_test, String supports_eeprom_access, String supports_register_dump, String supports_priv_flags )
	{
		super();
		this.driver = driver;
		this.version = version;
		this.firmware_version = firmware_version;
		this.expansion_rom_version = expansion_rom_version;
		this.bus_info = bus_info;
		this.supports_statistics = supports_statistics;
		this.supports_test = supports_test;
		this.supports_eeprom_access = supports_eeprom_access;
		this.supports_register_dump = supports_register_dump;
		this.supports_priv_flags = supports_priv_flags;
	}

	public String getDriver()
	{
		return driver;
	}

	public void setDriver( String driver )
	{
		this.driver = driver;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion( String version )
	{
		this.version = version;
	}

	public String getFirmware_version()
	{
		return firmware_version;
	}

	public void setFirmware_version( String firmware_version )
	{
		this.firmware_version = firmware_version;
	}

	public String getExpansion_rom_version()
	{
		return expansion_rom_version;
	}

	public void setExpansion_rom_version( String expansion_rom_version )
	{
		this.expansion_rom_version = expansion_rom_version;
	}

	public String getBus_info()
	{
		return bus_info;
	}

	public void setBus_info( String bus_info )
	{
		this.bus_info = bus_info;
	}

	public String getSupports_statistics()
	{
		return supports_statistics;
	}

	public void setSupports_statistics( String supports_statistics )
	{
		this.supports_statistics = supports_statistics;
	}

	public String getSupports_test()
	{
		return supports_test;
	}

	public void setSupports_test( String supports_test )
	{
		this.supports_test = supports_test;
	}

	public String getSupports_eeprom_access()
	{
		return supports_eeprom_access;
	}

	public void setSupports_eeprom_access( String supports_eeprom_access )
	{
		this.supports_eeprom_access = supports_eeprom_access;
	}

	public String getSupports_register_dump()
	{
		return supports_register_dump;
	}

	public void setSupports_register_dump( String supports_register_dump )
	{
		this.supports_register_dump = supports_register_dump;
	}

	public String getSupports_priv_flags()
	{
		return supports_priv_flags;
	}

	public void setSupports_priv_flags( String supports_priv_flags )
	{
		this.supports_priv_flags = supports_priv_flags;
	}

}
