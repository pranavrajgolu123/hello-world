package com.fmlb.forsaos.client.application.common;

import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.google.gwt.i18n.client.NumberFormat;

public class Converter
{

	public static String getFormatSize( double size )
	{
		// LoggerUtil.log( size.toString() );
		double divisor = 1;
		String[] scaleLabels =
		{ "B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB" };
		int x = 0;
		while ( ( size / divisor ) >= 1024 )
		{
			divisor = divisor * 1024;
			x++;
		}
		return NumberFormat.getFormat( "#,##0.##" ).format( size / divisor ) + " " + scaleLabels[x];
		// return String.valueOf(Math.floor(size / divisor)) + " " + scaleLabels[x];
		// return String.format("%.2f", (size / divisor)) + " " + scaleLabels[x];
	}

	public static String readableFileSize( Double size )
	{
		if ( size <= 0 )
			return "0";
		final String[] units = new String[]
		{ "B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB" };
		int digitGroups = ( int ) ( Math.log10( size ) / Math.log10( 1024 ) );
		LoggerUtil.log( "format indexcx " + String.valueOf( digitGroups ) );
		return NumberFormat.getFormat( "#,##0.##" ).format( size / Math.pow( 1024, digitGroups ) ) + " " + units[digitGroups];
	}

	public static String formatDecimal( Double val )
	{
		return NumberFormat.getFormat( "#,##0.##" ).format( val );
	}

	public static String getFormatKiBSize( double size, String value )
	{
		double divisor = 1;
		String[] scaleLabels =
		{ "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB" };
		int x = 0;
		while ( ( size / divisor ) >= 1024 )
		{
			divisor = divisor * 1024;
			x++;
		}
		return NumberFormat.getFormat( "#,##0.##" ).format( size / divisor ) + " " + scaleLabels[x];
	}

	public static double converToBytes( double size, String type )
	{
		double bytes = 0;
		if ( type.equalsIgnoreCase( MemorySizeType.MB.getValue() ) )
		{
			bytes = size * 1048576.00;
		}
		else if ( type.equalsIgnoreCase( MemorySizeType.GB.getValue() ) )
		{
			bytes = size * 1073741824.00;

		}
		else if ( type.equalsIgnoreCase( MemorySizeType.PB.getValue() ) )
		{
			bytes = size * 1.407374884e+14;
		}
		else if ( type.equalsIgnoreCase( MemorySizeType.TB.getValue() ) )
		{
			bytes = size * 1099511627776.00;
		}
		return bytes;
	}
}
