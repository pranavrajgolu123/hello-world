package com.fmlb.forsaos.server.application.utility;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fmlb.forsaos.client.application.models.EventLogResponseModel;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.fmlb.forsaos.shared.application.utility.CommonUtil;
import com.fmlb.forsaos.shared.application.utility.ErrorCodes;

public class ServerUtil
{
	public static String getJSONStringOfObject( Object object ) throws FBException
	{
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( SerializationFeature.INDENT_OUTPUT, true );
			StringWriter lemJsonStr = new StringWriter();
			objectMapper.writeValue( lemJsonStr, object );
//			System.out.println( "Generated JSON Object : \n" + lemJsonStr );
			return lemJsonStr.toString();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			throw new FBException( "Unable to Parse Object to JSON", ErrorCodes.OBJECT_TO_JSON_PARSE_ERROR );
		}
	}

	public static String getFormattedDate( Long millisceonds )
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat( CommonUtil.DATE_TIME_FORMAT );
		return simpleDateFormat.format( new Date( millisceonds ) );
	}

	public static List<String> getGroupNames( String input )
	{
		List<String> groupNames = new ArrayList<>();

		if ( input != null && !input.isEmpty() )
		{
			if ( input.contains( "," ) )
			{
				String[] split = input.split( "," );
				for ( int i = 0; i < split.length; i++ )
				{
					String token = split[i];
					if ( token.startsWith( "CN=" ) )
					{
						String groupName = token.substring( 3, token.length() );
						groupNames.add( groupName );
						break;// Assumption only first CN is the group name
					}
				}
			}
			else if ( input.contains( "CN=" ) )
			{
				String groupName = input.substring( 3, input.length() );
				groupNames.add( groupName );
			}
		}
		return groupNames;

	}

	public static String formatDecimalToTwoPlaces( String number )
	{
		String formattedValue = number;
		try
		{
			Double value = Double.valueOf( number );
			DecimalFormat df2 = new DecimalFormat( ".##" );
			formattedValue = df2.format( value );
		}
		catch ( NumberFormatException e )
		{
			e.printStackTrace();
		}
		return formattedValue;
	}

	public static EventLogResponseModel tokenizeEventLogToModel( String logRecord )
	{
		Pattern pattern = Pattern.compile( CommonUtil.EVENT_LOG_REG_EX );
		Matcher matcher = pattern.matcher( logRecord );
		EventLogResponseModel eventLogResponseModel = null;
		if ( matcher.matches() )
		{
			eventLogResponseModel = new EventLogResponseModel( matcher.group( 1 ), matcher.group( 2 ), matcher.group( 3 ), matcher.group( 4 ), matcher.group( 5 ), matcher.group( 6 ) );
		}
		else
		{
			eventLogResponseModel = new EventLogResponseModel();
			System.out.println( logRecord );
			eventLogResponseModel.setDesc( "<failed to parse log entry> - logRecord" );
		}
		return eventLogResponseModel;
	}

}
