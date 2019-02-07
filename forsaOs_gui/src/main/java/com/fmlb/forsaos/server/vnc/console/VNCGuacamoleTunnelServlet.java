package com.fmlb.forsaos.server.vnc.console;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.GuacamoleSocket;
import org.apache.guacamole.net.GuacamoleTunnel;
import org.apache.guacamole.net.InetGuacamoleSocket;
import org.apache.guacamole.net.SimpleGuacamoleTunnel;
import org.apache.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import org.apache.guacamole.servlet.GuacamoleHTTPTunnelServlet;

public class VNCGuacamoleTunnelServlet extends GuacamoleHTTPTunnelServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected GuacamoleTunnel doConnect( HttpServletRequest request ) throws GuacamoleException
	{
		String parms = "";
		Map<String, String> reqParams = new HashMap<String, String>();
		try
		{
			GuacamoleConfiguration config = new GuacamoleConfiguration();
			if ( "POST".equalsIgnoreCase( request.getMethod() ) )
			{
				parms = request.getReader().lines().collect( Collectors.joining( System.lineSeparator() ) );
				String[] tokens = parms.split( "&" );
				for ( String token : tokens )
				{
					System.out.println( token );
					String[] params = token.split( "=" );

					reqParams.put( params[0], params[1] );
				}
			}
			System.out.println( parms );
			
			String hostname = reqParams.get( "hostname" );
			if ( hostname.equals( "127.0.0.1" ) )
			{
//				hostname = "10.10.5.202";
				hostname = "10.10.7.237";
			}
			
			config.setProtocol( "vnc" );
			config.setParameter( "hostname", reqParams.get( "configHostname" ) );
			config.setParameter( "port", reqParams.get( "configPort" ) );
			config.setParameter( "password", reqParams.get( "password" ) );

			GuacamoleSocket socket = new ConfiguredGuacamoleSocket( new InetGuacamoleSocket( hostname, 4822 ), config );

			return new SimpleGuacamoleTunnel( socket );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return null;
	}
}
