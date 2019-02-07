package com.fmlb.forsaos.shared.application.utility;

import org.apache.http.HttpStatus;

import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class RestCallUtil
{
	public static String ALL_REQUEST_DATA = "{ \"filter\" : [\"ALL\"], \"query\" : { } }";

	public static String SELECTED_FIELDS_LEM = "{ \"filter\":[\"_id\",\"name\",\"size\",\"rtm_id\"], \"query\":{ } }";

	public static String SELECTED_FIELDS_RTM = "{ \"filter\":[\"name\"], \"query\":{ } }";

	public static String VM_LEM_MAPPING = "{ \"filter\":[\"_id\", \"name\", \"disks\"], \"query\":{ } }";

	public static String BRIDGES_FILTER_ALL = "{  \"bridges\" : {   \"filter\" : [\"ALL\"]}}";

	public static String ETHERNETS_FILTER_ALL = "{\"ethernets\":{\"filter\" : [\"ALL\"]}}";

	public static String EMPTY_BODY = "{}";

	public static boolean isRequestSucess( Response response )
	{
		boolean isSuccess = false;
		if ( response.getStatusCode() == HttpStatus.SC_OK )
		{
			JSONObject responseBody = new JSONObject( JsonUtils.safeEval( response.getText() ) );
			// if the ret attribute is true then authentication successful
			// if the message is empty then no error else display error message
			LoggerUtil.log( responseBody.get( "message" ).toString() );
			LoggerUtil.log( String.valueOf( responseBody.get( "message" ).toString().length() ) );
			if ( "true".equals( responseBody.get( "ret" ).toString() ) && !( responseBody.get( "message" ) instanceof JSONNull ) && responseBody.get( "message" ).toString().length() == 2 )
			{
				isSuccess = true;
			}
			else
			{
				isSuccess = false;
				LoggerUtil.log( responseBody.get( "message" ).toString() );
			}
		}
		return isSuccess;
	}

	public static JSONString getJSONString( String value )
	{
		JSONString str = new JSONString( value );
		return str;
	}

	public static JSONNumber getJSONNumber( Double value )
	{
		JSONNumber num = new JSONNumber( value );
		return num;
	}

	public static JSONNumber getJSONNumber( Long value )
	{
		JSONNumber num = new JSONNumber( value );
		return num;
	}

	public static String getQueryRequest( String query )
	{
		return "{ \"filter\" : [\"ALL\"], \"query\" : " + query + " }";
	}

	public static String getDelBridgeRequest( String bridgeName )
	{
		return "{  \"bridges\" : {   \"name\" : " + "\"" + bridgeName + "\"" + "}}";
	}
	
	public static String getDelEthernetRequest( String ethernetName )
	{
		return "{  \"ethernets\" : {   \"name\" : " + "\"" + ethernetName + "\"" + "}}";
	}

	public static String getNetworkDeviceCommonProperties( String deviceName )
	{
		return "{\"common\":{\"filter\" : [\"ALL\"],\"query\":{\"name\" :\"" + deviceName + "\"}}}";
	}

	public static String getNetworkBridgeProperties( String deviceName )
	{
		return "{\"bridges\":{\"filter\" : [\"ALL\"],\"query\":{\"name\" :\"" + deviceName + "\"}}}";
	}

	public static String updateNetworkBridgeProperties( String bridgeProperties )
	{
		return "{\"bridges\":" + bridgeProperties + "}";
	}
	
	public static String updateNetworkEthernetProperties( String ethernetProperties )
	{
		return "{\"ethernets\":" + ethernetProperties + "}";
	}

	public static String getRawString( JSONObject jsonObj, String propertyName )
	{
		LoggerUtil.log( "get string value of property " + propertyName );
		if ( jsonObj.get( propertyName ) != null )
		{
			LoggerUtil.log( "get vaslue of property " + propertyName + " is not null" );
			LoggerUtil.log( "get vaslue of property " + propertyName + " is" + jsonObj.get( propertyName ).isString().stringValue() );
			return jsonObj.get( propertyName ).isString().stringValue();
		}
		return null;
	}

	public static double getRawDouble( JSONObject jsonObj, String propertyName )
	{
		LoggerUtil.log( "get string value of property " + propertyName );
		if ( jsonObj.get( propertyName ) != null )
		{
			LoggerUtil.log( "get vaslue of property " + propertyName + " is not null" );
			LoggerUtil.log( "get vaslue of property " + propertyName + " is" + jsonObj.get( propertyName ).isNumber().doubleValue() );
			return jsonObj.get( propertyName ).isNumber().doubleValue();
		}
		return 0d;
	}

	public static boolean getRawBoolean( JSONObject jsonObj, String propertyName )
	{
		LoggerUtil.log( "get string value of property " + propertyName );
		if ( jsonObj.get( propertyName ) != null )
		{
			LoggerUtil.log( "get vaslue of property " + propertyName + " is not null" );
			LoggerUtil.log( "get vaslue of property " + propertyName + " is" + jsonObj.get( propertyName ).isBoolean().booleanValue() );
			return jsonObj.get( propertyName ).isBoolean().booleanValue();
		}
		return false;
	}
}
