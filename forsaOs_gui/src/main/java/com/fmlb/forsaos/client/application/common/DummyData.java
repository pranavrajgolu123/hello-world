package com.fmlb.forsaos.client.application.common;

import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class DummyData
{

	static LEMModel model = null;

	public static JSONObject getCapacityChartData()
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put( "ret", getJSONString( "true" ) );
		jsonObject.put( "available_size", new JSONNumber( 1911260446720.000000 ) );
		jsonObject.put( "message", getJSONString( "" ) );
		jsonObject.put( "dup", new JSONNumber( 0 ) );

		jsonObject.put( "uniq", new JSONNumber( 0 ) );
		jsonObject.put( "cur_total_size", new JSONNumber( 1924145348608.000000 ) );
		jsonObject.put( "rtm_total_size", new JSONNumber( 1924145348608.00 ) );
		jsonObject.put( "ampl", new JSONNumber( 1.000000 ) );
		LoggerUtil.log( jsonObject.toString() );
		return jsonObject;

	}

	private static JSONString getJSONString( String value )
	{
		JSONString str = new JSONString( value );
		return str;
	}

	public static LEMModel getLEMModel()
	{
		return model;
	}

	public static void setLEMModel( LEMModel lemModel )
	{
		model = lemModel;
	}
}
