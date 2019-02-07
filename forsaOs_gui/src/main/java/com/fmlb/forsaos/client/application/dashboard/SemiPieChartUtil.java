package com.fmlb.forsaos.client.application.dashboard;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class SemiPieChartUtil
{

	public static JSONObject getSemiPieJSON()
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put( "type", getJSONString( "pie" ) );
		jsonObject.put( "startAngle", getJSONString( "0" ) );
		jsonObject.put( "radius", getJSONString( "90%" ) );
		jsonObject.put( "innerRadius", getJSONString( "50%" ) );

		jsonObject.put( "valueField", getJSONString( "litres" ) );
		jsonObject.put( "titleField", getJSONString( "country" ) );
		jsonObject.put( "alphaField", getJSONString( "alpha" ) );
		jsonObject.put( "labelsEnabled", getJSONString( "false" ) );
		jsonObject.put( "pullOutRadius", getJSONString( "0" ) );
		jsonObject.put( "pieY", getJSONString( "95%" ) );

		// setting the data here this needs to be passed as an argument
		JSONObject enabled = new JSONObject();
		enabled.put( "enabled", getJSONString( "true" ) );
		jsonObject.put( "responsive", enabled );
		JSONArray dataProvide = new JSONArray();
		JSONObject data1 = new JSONObject();
		data1.put( "country", getJSONString( "Lithuania" ) );
		data1.put( "litres", getJSONString( "501.9" ) );

		JSONObject data2 = new JSONObject();
		data2.put( "country", getJSONString( "Czech Republic" ) );
		data2.put( "litres", getJSONString( "301.9" ) );
		dataProvide.set( 0, data1 );
		dataProvide.set( 1, data2 );
		jsonObject.put( "dataProvider", dataProvide );

		return jsonObject;

	}

	public static JSONArray getDataProvider()
	{
		JSONArray dataProvider = new JSONArray();
		JSONObject data1 = new JSONObject();
		data1.put( "country", getJSONString( "Lithuania" ) );
		data1.put( "litres", getJSONString( "501.9" ) );

		JSONObject data2 = new JSONObject();
		data2.put( "country", getJSONString( "Czech Republic" ) );
		data2.put( "litres", getJSONString( "301.9" ) );
		dataProvider.set( 0, data1 );
		dataProvider.set( 1, data2 );
		return dataProvider;

	}

	private static JSONString getJSONString( String value )
	{
		JSONString str = new JSONString( value );
		return str;
	}
}
