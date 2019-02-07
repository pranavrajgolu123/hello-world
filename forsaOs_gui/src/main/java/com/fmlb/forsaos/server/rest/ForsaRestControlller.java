package com.fmlb.forsaos.server.rest;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fmlb.forsaos.server.websocket.BlinkStartWebsocketEndpointHandler;
import com.fmlb.forsaos.server.websocket.BlinkStopWebsocketEndpointHandler;
import com.fmlb.forsaos.server.websocket.DashboardWebsocketEndpointHandler;
import com.fmlb.forsaos.server.websocket.DiagnosticWebsocketEndpointHandler;
import com.fmlb.forsaos.server.websocket.UPSWebsocketEndpointHandler;
import com.fmlb.forsaos.server.websocket.VMStatsWebsocketEndpointHandler;

@RestController( )
@RequestMapping( "/realtime" )
public class ForsaRestControlller
{

	@RequestMapping( value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
	public String test()
	{
		return "hi";
	}

	@RequestMapping( value = "/bmc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public void diagnosticData( @RequestBody String message )
	{
		//System.out.println( "sending data to diagnostic page " + message );
		DiagnosticWebsocketEndpointHandler.getInstance().broadcastData( message );
	}

	@RequestMapping( value = "/dashboard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public void dashBoardData( @RequestBody String message )
	{
		//System.out.println( "sending data to dashboard page " + message );
		DashboardWebsocketEndpointHandler.getInstance().broadcastData( message );
	}

	@RequestMapping( value = "/ups", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public void upsData( @RequestBody String message )
	{
		//System.out.println( "sending data to ups page " + message );
		UPSWebsocketEndpointHandler.getInstance().broadcastData( message );
	}

	/*@RequestMapping( value = "/alerts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public void alerts( @RequestBody String message )
	{
		//System.out.println( "sending data to alerts page " + message );
		AlertsWebsocketEndpointHandler.getInstance().broadcastData( message );
	}*/

	@RequestMapping( value = "/blink_start", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public void blinkStart( @RequestBody String message )
	{
		System.out.println( new Date() + " BLINK STARTED" + message );
		BlinkStartWebsocketEndpointHandler.getInstance().broadcastData( message );
	}

	@RequestMapping( value = "/blink_stop", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public void blinkStop( @RequestBody String message )
	{
		System.out.println( new Date() + " BLINK STOPPED" + message );
		BlinkStopWebsocketEndpointHandler.getInstance().broadcastData( message );
	}

	@RequestMapping( value = "/vmstats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public void vmStats( @RequestBody String message )
	{
		//System.out.println( "sending data to blibk stop page " + message );
		VMStatsWebsocketEndpointHandler.getInstance().broadcastData( message );
	}

}
