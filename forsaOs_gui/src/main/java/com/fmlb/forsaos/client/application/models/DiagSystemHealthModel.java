package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DiagSystemHealthModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SysInfoModel sysInfoModel;

	private SystemStatusChartModel systemStatusChartModel;

	private String CPU1_temperature_current;

	private String CPU1_temperature_max;

	private String CPU2_temperature_current;

	private String CPU2_temperature_max;

	private String Fans_fan1_speed = null;

	private String Fans_fan2_speed = null;

	private String Fans_fan3_speed = null;

	private String Fans_fan4_speed = null;

	private String Fans_fan5_speed = null;

	private String Fans_fan6_speed = null;

	private String Fans_fan7_speed = null;

	private String Fans_fan8_speed = null;

	private String Memory_temperature_current;

	private String PowerSupply_fan_speed_minimal;

	private String PowerSupply_temperature_maximal;

	private String Voltage_12V;

	private String Voltage_3_3V;

	private String Voltage_5V;

	public DiagSystemHealthModel()
	{
		super();
	}

	public DiagSystemHealthModel( String CPU1_temperature_current, String CPU1_temperature_max, String CPU2_temperature_current, String CPU2_temperature_max, String Fans_fan1_speed, String Fans_fan2_speed, String Fans_fan3_speed, String Fans_fan4_speed, String Fans_fan5_speed, String Fans_fan6_speed, String Fans_fan7_speed, String Fans_fan8_speed, String Memory_temperature_current, String PowerSupply_fan_speed_minimal, String PowerSupply_temperature_maximal, String Voltage_12V, String Voltage_3_3V, String Voltage_5V )
	{
		super();
		this.CPU1_temperature_current = CPU1_temperature_current;
		this.CPU1_temperature_max = CPU1_temperature_max;
		this.CPU2_temperature_current = CPU2_temperature_current;
		this.CPU2_temperature_max = CPU2_temperature_max;
		this.Fans_fan1_speed = Fans_fan1_speed;
		this.Fans_fan2_speed = Fans_fan2_speed;
		this.Fans_fan3_speed = Fans_fan3_speed;
		this.Fans_fan4_speed = Fans_fan4_speed;
		this.Fans_fan5_speed = Fans_fan5_speed;
		this.Fans_fan6_speed = Fans_fan6_speed;
		this.Fans_fan7_speed = Fans_fan7_speed;
		this.Fans_fan8_speed = Fans_fan8_speed;
		this.Memory_temperature_current = Memory_temperature_current;
		this.PowerSupply_fan_speed_minimal = PowerSupply_fan_speed_minimal;
		this.PowerSupply_temperature_maximal = PowerSupply_temperature_maximal;
		this.Voltage_12V = Voltage_12V;
		this.Voltage_3_3V = Voltage_3_3V;
		this.Voltage_5V = Voltage_5V;
	}

	public String getCPU1_temperature_current()
	{
		return CPU1_temperature_current;
	}

	public void setCPU1_temperature_current( String cPU1_temperature_current )
	{
		CPU1_temperature_current = cPU1_temperature_current;
	}

	public String getCPU1_temperature_max()
	{
		return CPU1_temperature_max;
	}

	public void setCPU1_temperature_max( String cPU1_temperature_max )
	{
		CPU1_temperature_max = cPU1_temperature_max;
	}

	public String getCPU2_temperature_current()
	{
		return CPU2_temperature_current;
	}

	public void setCPU2_temperature_current( String cPU2_temperature_current )
	{
		CPU2_temperature_current = cPU2_temperature_current;
	}

	public String getCPU2_temperature_max()
	{
		return CPU2_temperature_max;
	}

	public void setCPU2_temperature_max( String cPU2_temperature_max )
	{
		CPU2_temperature_max = cPU2_temperature_max;
	}

	public String getFans_fan1_speed()
	{
		return Fans_fan1_speed;
	}

	public void setFans_fan1_speed( String fans_fan1_speed )
	{
		Fans_fan1_speed = fans_fan1_speed;
	}

	public String getFans_fan2_speed()
	{
		return Fans_fan2_speed;
	}

	public void setFans_fan2_speed( String fans_fan2_speed )
	{
		Fans_fan2_speed = fans_fan2_speed;
	}

	public String getFans_fan3_speed()
	{
		return Fans_fan3_speed;
	}

	public void setFans_fan3_speed( String fans_fan3_speed )
	{
		Fans_fan3_speed = fans_fan3_speed;
	}

	public String getFans_fan4_speed()
	{
		return Fans_fan4_speed;
	}

	public void setFans_fan4_speed( String fans_fan4_speed )
	{
		Fans_fan4_speed = fans_fan4_speed;
	}

	public String getFans_fan5_speed()
	{
		return Fans_fan5_speed;
	}

	public void setFans_fan5_speed( String fans_fan5_speed )
	{
		Fans_fan5_speed = fans_fan5_speed;
	}

	public String getFans_fan6_speed()
	{
		return Fans_fan6_speed;
	}

	public void setFans_fan6_speed( String fans_fan6_speed )
	{
		Fans_fan6_speed = fans_fan6_speed;
	}

	public String getFans_fan7_speed()
	{
		return Fans_fan7_speed;
	}

	public void setFans_fan7_speed( String fans_fan7_speed )
	{
		Fans_fan7_speed = fans_fan7_speed;
	}

	public String getFans_fan8_speed()
	{
		return Fans_fan8_speed;
	}

	public void setFans_fan8_speed( String fans_fan8_speed )
	{
		Fans_fan8_speed = fans_fan8_speed;
	}

	public String getMemory_temperature_current()
	{
		return Memory_temperature_current;
	}

	public void setMemory_temperature_current( String memory_temperature_current )
	{
		Memory_temperature_current = memory_temperature_current;
	}

	public String getPowerSupply_fan_speed_minimal()
	{
		return PowerSupply_fan_speed_minimal;
	}

	public void setPowerSupply_fan_speed_minimal( String powerSupply_fan_speed_minimal )
	{
		PowerSupply_fan_speed_minimal = powerSupply_fan_speed_minimal;
	}

	public String getPowerSupply_temperature_maximal()
	{
		return PowerSupply_temperature_maximal;
	}

	public void setPowerSupply_temperature_maximal( String powerSupply_temperature_maximal )
	{
		PowerSupply_temperature_maximal = powerSupply_temperature_maximal;
	}

	public String getVoltage_12V()
	{
		return Voltage_12V;
	}

	public void setVoltage_12V( String voltage_12v )
	{
		Voltage_12V = voltage_12v;
	}

	public String getVoltage_3_3V()
	{
		return Voltage_3_3V;
	}

	public void setVoltage_3_3V( String voltage_3_3v )
	{
		Voltage_3_3V = voltage_3_3v;
	}

	public String getVoltage_5V()
	{
		return Voltage_5V;
	}

	public void setVoltage_5V( String voltage_5v )
	{
		Voltage_5V = voltage_5v;
	}

	public SysInfoModel getSysInfoModel()
	{
		return sysInfoModel;
	}

	public void setSysInfoModel( SysInfoModel sysInfoModel )
	{
		this.sysInfoModel = sysInfoModel;
	}

	public SystemStatusChartModel getSystemStatusChartModel()
	{
		return systemStatusChartModel;
	}

	public void setSystemStatusChartModel( SystemStatusChartModel systemStatusChartModel )
	{
		this.systemStatusChartModel = systemStatusChartModel;
	}

}
