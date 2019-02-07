package com.fmlb.forsaos.shared.application.exceptions;

public enum WebsocketEndpoints
{
	END_POINT( "webSocketService/status" ),

	DASHBOARD_END_POINT( "webSocketService/dashboard" ),

	DIAGNOSTIC_END_POINT( "webSocketService/diagnostic" ),
	
	UPS_END_POINT( "webSocketService/ups" ),
	
	ALERTS_END_POINT( "webSocketService/alerts" ),
	
	VM_STATS_END_POINT( "webSocketService/vmstats" ),
	
	BLINK_START_END_POINT( "webSocketService/blink_start" ),
	
	BLINK_STOP_END_POINT( "webSocketService/blink_stop" );

	private String endpointValue;

	private WebsocketEndpoints( String endpointValue )
	{
		this.endpointValue = endpointValue;
	}

	public String getEndpointValue()
	{
		return endpointValue;
	}

}
