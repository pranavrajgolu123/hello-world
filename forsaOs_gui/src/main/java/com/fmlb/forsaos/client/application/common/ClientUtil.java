package com.fmlb.forsaos.client.application.common;

import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

import gwt.material.design.client.ui.MaterialTextBox;

public class ClientUtil
{
	public static EventBus EVENT_BUS = GWT.create( SimpleEventBus.class );

	public static boolean isValidIP4( final String ip )
	{
		boolean isValid = false;
		final String ipv4 = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		if ( ip.indexOf( "." ) > 0 )
		{
			if ( ip.matches( ipv4 ) )
			{
				isValid = true;
			}
			else
			{
				isValid = false;
			}
		}

		else
		{
			isValid = false;
		}
		return isValid;

	}

	public static boolean isValidIP4Subnet( final int subnetIP, MaterialTextBox ipBox )
	{
		boolean isValid = false;
		LoggerUtil.log( "subnet ip" + subnetIP );
		LoggerUtil.log( "last ip" + ipBox.getValue() );

		if ( subnetIP >= 8 && subnetIP <= 31 )
		{
			isValid = true;
		}
		return isValid;

	}

	public static boolean isValidIP6( final String ip )
	{
		boolean isValid = false;

		final String ipv6 = "^[0-9a-f]{1,4}:" + "[0-9a-f]{1,4}:" + "[0-9a-f]{1,4}:" + "[0-9a-f]{1,4}:" + "[0-9a-f]{1,4}:" + "[0-9a-f]{1,4}:" + "[0-9a-f]{1,4}:" + "[0-9a-f]{1,4}$";

		if ( ip.indexOf( ":" ) > 0 )
		{
			if ( ip.matches( ipv6 ) )
			{
				isValid = true;
			}
			else
			{
				isValid = false;
			}
		}

		else
		{
			isValid = false;
		}
		return isValid;
	}

	// rajashekhar named it
	public static int getDClass( String ip )
	{
		try
		{
			if ( ip != null && !ip.trim().equalsIgnoreCase( "" ) && ip.contains( "." ) )
			{
				LoggerUtil.log( "split value " + ip.split( "\\." )[3] );
				return Integer.valueOf( ip.split( "\\." )[3] );
			}
		}
		catch ( NumberFormatException e )
		{
			return -1;
		}
		return -1;
	}
}
