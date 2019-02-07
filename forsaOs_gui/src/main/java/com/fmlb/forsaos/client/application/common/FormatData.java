package com.fmlb.forsaos.client.application.common;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

public class FormatData
{
	private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
	static
	{
		suffixes.put( 1_000L, " K" );
		suffixes.put( 1_000_000L, " Mn" );
		suffixes.put( 1_000_000_000L, " Bn" );
	}

	public static String formatDouble( Double val )
	{
		String tileValue = "";
		tileValue = val.longValue() > 1000 ? formatLong( val.longValue() ) : String.valueOf( Math.round( val * 100D ) / 100D );
		String fomattedValue = String.valueOf( tileValue );
		return fomattedValue;
	}

	public static String formatLong( long value )
	{
		// Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
		if ( value == Long.MIN_VALUE )
			return formatLong( Long.MIN_VALUE + 1 );
		if ( value < 0 )
			return "-" + formatLong( -value );
		if ( value < 1000 )
			return Long.toString( value ); // deal with easy case

		Entry<Long, String> e = suffixes.floorEntry( value );
		Long divideBy = e.getKey();
		String suffix = e.getValue();

		double truncated = value / ( divideBy / 1d ); // the number part of the output times 10
		return ( Math.round( truncated * 1000.0 ) / 1000.0 ) + 0 + suffix;
	}
}
