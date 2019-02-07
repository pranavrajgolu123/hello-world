package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DiagEthToolErrorModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rx_crc_errors;

	private String rx_missed_errors;

	private String tx_aborted_errors;

	private String tx_carrier_errors;

	private String tx_window_errors;

	private String rx_long_length_errors;

	private String rx_short_length_errors;

	private String rx_align_errors;

	private String rx_errors;

	private String tx_errors;

	private String rx_length_errors;

	private String rx_over_errors;

	private String rx_frame_errors;

	private String rx_fifo_errors;

	private String tx_fifo_errors;

	private String tx_heartbeat_errors;

	public DiagEthToolErrorModel()
	{
		super();
	}

	public DiagEthToolErrorModel( String rx_crc_errors, String rx_missed_errors, String tx_aborted_errors, String tx_carrier_errors, String tx_window_errors, String rx_long_length_errors, String rx_short_length_errors, String rx_align_errors, String rx_errors, String tx_errors, String rx_length_errors, String rx_over_errors, String rx_frame_errors, String rx_fifo_errors, String tx_fifo_errors, String tx_heartbeat_errors )
	{
		super();
		this.rx_crc_errors = rx_crc_errors;
		this.rx_missed_errors = rx_missed_errors;
		this.tx_aborted_errors = tx_aborted_errors;
		this.tx_carrier_errors = tx_carrier_errors;
		this.tx_window_errors = tx_window_errors;
		this.rx_long_length_errors = rx_long_length_errors;
		this.rx_short_length_errors = rx_short_length_errors;
		this.rx_align_errors = rx_align_errors;
		this.rx_errors = rx_errors;
		this.tx_errors = tx_errors;
		this.rx_length_errors = rx_length_errors;
		this.rx_over_errors = rx_over_errors;
		this.rx_frame_errors = rx_frame_errors;
		this.rx_fifo_errors = rx_fifo_errors;
		this.tx_fifo_errors = tx_fifo_errors;
		this.tx_heartbeat_errors = tx_heartbeat_errors;
	}

	public String getRx_crc_errors()
	{
		return rx_crc_errors;
	}

	public void setRx_crc_errors( String rx_crc_errors )
	{
		this.rx_crc_errors = rx_crc_errors;
	}

	public String getRx_missed_errors()
	{
		return rx_missed_errors;
	}

	public void setRx_missed_errors( String rx_missed_errors )
	{
		this.rx_missed_errors = rx_missed_errors;
	}

	public String getTx_aborted_errors()
	{
		return tx_aborted_errors;
	}

	public void setTx_aborted_errors( String tx_aborted_errors )
	{
		this.tx_aborted_errors = tx_aborted_errors;
	}

	public String getTx_carrier_errors()
	{
		return tx_carrier_errors;
	}

	public void setTx_carrier_errors( String tx_carrier_errors )
	{
		this.tx_carrier_errors = tx_carrier_errors;
	}

	public String getTx_window_errors()
	{
		return tx_window_errors;
	}

	public void setTx_window_errors( String tx_window_errors )
	{
		this.tx_window_errors = tx_window_errors;
	}

	public String getRx_long_length_errors()
	{
		return rx_long_length_errors;
	}

	public void setRx_long_length_errors( String rx_long_length_errors )
	{
		this.rx_long_length_errors = rx_long_length_errors;
	}

	public String getRx_short_length_errors()
	{
		return rx_short_length_errors;
	}

	public void setRx_short_length_errors( String rx_short_length_errors )
	{
		this.rx_short_length_errors = rx_short_length_errors;
	}

	public String getRx_align_errors()
	{
		return rx_align_errors;
	}

	public void setRx_align_errors( String rx_align_errors )
	{
		this.rx_align_errors = rx_align_errors;
	}

	public String getRx_errors()
	{
		return rx_errors;
	}

	public void setRx_errors( String rx_errors )
	{
		this.rx_errors = rx_errors;
	}

	public String getTx_errors()
	{
		return tx_errors;
	}

	public void setTx_errors( String tx_errors )
	{
		this.tx_errors = tx_errors;
	}

	public String getRx_length_errors()
	{
		return rx_length_errors;
	}

	public void setRx_length_errors( String rx_length_errors )
	{
		this.rx_length_errors = rx_length_errors;
	}

	public String getRx_over_errors()
	{
		return rx_over_errors;
	}

	public void setRx_over_errors( String rx_over_errors )
	{
		this.rx_over_errors = rx_over_errors;
	}

	public String getRx_frame_errors()
	{
		return rx_frame_errors;
	}

	public void setRx_frame_errors( String rx_frame_errors )
	{
		this.rx_frame_errors = rx_frame_errors;
	}

	public String getRx_fifo_errors()
	{
		return rx_fifo_errors;
	}

	public void setRx_fifo_errors( String rx_fifo_errors )
	{
		this.rx_fifo_errors = rx_fifo_errors;
	}

	public String getTx_fifo_errors()
	{
		return tx_fifo_errors;
	}

	public void setTx_fifo_errors( String tx_fifo_errors )
	{
		this.tx_fifo_errors = tx_fifo_errors;
	}

	public String getTx_heartbeat_errors()
	{
		return tx_heartbeat_errors;
	}

	public void setTx_heartbeat_errors( String tx_heartbeat_errors )
	{
		this.tx_heartbeat_errors = tx_heartbeat_errors;
	}

}
