package com.fmlb.forsaos.server.application.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.fmlb.forsaos.shared.application.utility.ContentType;
import com.fmlb.forsaos.shared.application.utility.ErrorCodes;
import com.fmlb.forsaos.shared.application.utility.RequestMethod;
import com.fmlb.forsaos.shared.application.utility.ResourceServiceEndpoints;
import com.fmlb.forsaos.shared.application.utility.ServiceTypes;
import com.fmlb.forsaos.shared.application.utility.UserSession;

public class RemoteServiceImpl implements RemoteService
{
	private final static int DEFAULT_TIMEOUT = 5000;

	private final UserSession userSession;

	private final RequestMethod requestMethod;

	private final ServiceTypes serviceType;

	private final ResourceServiceEndpoints endPoint;

	private final ContentType contentType;

	private final String URI;

	public RemoteServiceImpl( UserSession userSession, String URI, RequestMethod reqMethod, ServiceTypes serviceType, ResourceServiceEndpoints endpoint, ContentType contentType )
	{
		super();
		this.userSession = userSession;
		this.URI = URI;
		this.requestMethod = reqMethod;
		this.endPoint = endpoint;
		this.serviceType = serviceType;
		this.contentType = contentType;
	}

	public String getUri( ServiceTypes serviceType, ResourceServiceEndpoints endPoints )
	{
		String myString = URI + ":" + serviceType.getPortNumber() + endPoints.getPath();
		//System.out.println( "URL : " + myString );
		byte ptext[] = myString.getBytes();
		String value = "";
		try
		{
			value = new String( ptext, "UTF-8" );
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
		return value;
	}

	/*@Override
	public String getRequest( boolean retCheck ) throws FBException
	{

		String jsonResponse = null;
		GetMethod getMethod = null;
		try
		{
			HttpClient httpClient = new HttpClient();
			httpClient.setConnectionTimeout( 0 );
			httpClient.setTimeout( 100000000 );
			getMethod = new GetMethod( getUri( this.serviceType, this.endPoint ) );
			getMethod.setRequestHeader( "Content-Type", contentType.getContentType() );
			getMethod.setRequestHeader( "Authorization", userSession.getEncodedAuthorisation() );

			int statusCode = httpClient.executeMethod( getMethod );
			if ( statusCode != 200 )
			{
				throw new FBException( "Request Failed", ErrorCodes.HTTP_STATUS_NOT_OK );
			}
			jsonResponse = getMethod.getResponseBodyAsString();

			if ( retCheck )
			{
				ObjectMapper objectMapper = new ObjectMapper();
				byte[] jsonData = jsonResponse.getBytes();
				JsonNode rootNode = null;
				try
				{
					rootNode = objectMapper.readTree( jsonData );
				}
				catch ( IOException e )
				{
					throw new FBException( "JSON response parse error", ErrorCodes.INCORRECT_JSON_RESPONSE );
				}

				boolean success = rootNode.get( "ret" ).asBoolean();
				if ( !success )
				{
					String message = rootNode.get( "message" ).asText();
					throw new FBException( message, ErrorCodes.FAIL_RESPONSE );
				}
			}

			return jsonResponse;
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			throw new FBException( "GENERAL ERROR", ErrorCodes.GENERIC_ERROR );
		}
		finally
		{
			if ( getMethod != null )
				getMethod.releaseConnection();
		}
	}*/

	@Override
	public String getWithRequestData( String data, boolean retCheck ) throws FBException
	{
		HttpGetWithEntity httpEntity = null;
		try
		{
			StringEntity input = new StringEntity( data );
			org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();
			httpEntity = new HttpGetWithEntity();
			httpEntity.setURI( new URI( getUri( this.serviceType, this.endPoint ) ) );
			httpEntity.setHeader( "Authorization", userSession.getEncodedAuthorisation() );
			httpEntity.setHeader( "Content-Type", contentType.getContentType() );
			httpEntity.setEntity( input );
			HttpResponse response = httpClient.execute( httpEntity );
			int statusCode = response.getStatusLine().getStatusCode();
			if ( statusCode != 200 )
			{
				throw new FBException( "Request Failed", ErrorCodes.HTTP_STATUS_NOT_OK );
			}
			BufferedReader br = new BufferedReader( new InputStreamReader( ( response.getEntity().getContent() ) ) );
			String output = "";
			StringBuilder responseData = new StringBuilder( "" );
			while ( ( output = br.readLine() ) != null )
			{
				responseData.append( output );
			}
			httpClient.getConnectionManager().shutdown();

			if ( retCheck )
			{
				ObjectMapper objectMapper = new ObjectMapper();
				byte[] jsonData = responseData.toString().getBytes();
				JsonNode rootNode = null;
				try
				{
					rootNode = objectMapper.readTree( jsonData );
				}
				catch ( IOException e )
				{
					throw new FBException( "JSON response parse error", ErrorCodes.INCORRECT_JSON_RESPONSE );
				}

				boolean success = rootNode.get( "ret" ).asBoolean();
				if ( !success )
				{
					String message = rootNode.get( "message" ).asText();
					throw new FBException( message, ErrorCodes.FAIL_RESPONSE );
				}
			}

			return responseData.toString();
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			throw new FBException( "General Error", ErrorCodes.GENERIC_ERROR );
		}
		finally
		{
			if ( httpEntity != null )
			{
				httpEntity.releaseConnection();
			}
		}
	}

	@Override
	public String postRequest( String requestJson, boolean retCheck ) throws FBException
	{

		String jsonResponse = null;
		PostMethod postMethod = null;
		try
		{
			HttpClient httpClient = new HttpClient();
			postMethod = new PostMethod( getUri( this.serviceType, this.endPoint ) );
			postMethod.setRequestHeader( "Content-Type", contentType.getContentType() );
			postMethod.setRequestHeader( "Authorization", userSession.getEncodedAuthorisation() );
			postMethod.setRequestBody( requestJson );

			int statusCode = httpClient.executeMethod( postMethod );
			if ( statusCode == 200 )
			{
				jsonResponse = postMethod.getResponseBodyAsString();
			}
			else
			{
				throw new FBException( "Request Failed", ErrorCodes.HTTP_STATUS_NOT_OK );
			}

			if ( retCheck )
			{
				ObjectMapper objectMapper = new ObjectMapper();
				byte[] jsonData = jsonResponse.getBytes();
				JsonNode rootNode = null;
				try
				{
					rootNode = objectMapper.readTree( jsonData );
				}
				catch ( IOException e )
				{
					throw new FBException( "JSON response parse error", ErrorCodes.INCORRECT_JSON_RESPONSE );
				}

				boolean success = rootNode.get( "ret" ).asBoolean();
				if ( !success )
				{
					String message = rootNode.get( "message" ).asText();
					throw new FBException( message, ErrorCodes.FAIL_RESPONSE );
				}
			}

			return jsonResponse;
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			throw new FBException( "GENERAL ERROR", ErrorCodes.GENERIC_ERROR );
		}
		finally
		{
			if ( postMethod != null )
				postMethod.releaseConnection();
		}
	}

	@Override
	public String deleteRequest( String requestJson, boolean retCheck ) throws FBException
	{
		HttpDeleteWithEntity httpDeleteWithEntity = null;
		try
		{
			StringEntity input = new StringEntity( requestJson );
			org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();
			httpDeleteWithEntity = new HttpDeleteWithEntity();
			httpDeleteWithEntity.setURI( new URI( getUri( this.serviceType, this.endPoint ) ) );
			httpDeleteWithEntity.setHeader( "Authorization", userSession.getEncodedAuthorisation() );
			httpDeleteWithEntity.setHeader( "Content-Type", contentType.getContentType() );
			httpDeleteWithEntity.setEntity( input );
			HttpResponse response = httpClient.execute( httpDeleteWithEntity );
			int statusCode = response.getStatusLine().getStatusCode();
			if ( statusCode != 200 )
			{
				throw new FBException( "Request Failed", ErrorCodes.HTTP_STATUS_NOT_OK );
			}
			BufferedReader br = new BufferedReader( new InputStreamReader( ( response.getEntity().getContent() ) ) );
			String output = "";
			StringBuilder responseData = new StringBuilder( "" );
			while ( ( output = br.readLine() ) != null )
			{
				responseData.append( output );
			}
			httpClient.getConnectionManager().shutdown();

			//System.out.println( "jsonResponse \n" + responseData.toString() );

			if ( retCheck )
			{
				ObjectMapper objectMapper = new ObjectMapper();
				byte[] jsonData = responseData.toString().getBytes();
				JsonNode rootNode = null;
				try
				{
					rootNode = objectMapper.readTree( jsonData );
				}
				catch ( IOException e )
				{
					throw new FBException( "JSON response parse error", ErrorCodes.INCORRECT_JSON_RESPONSE );
				}

				boolean success = rootNode.get( "ret" ).asBoolean();
				if ( !success )
				{
					String message = rootNode.get( "message" ).asText();
					throw new FBException( message, ErrorCodes.FAIL_RESPONSE );
				}
			}

			return responseData.toString();
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			throw new FBException( "General Error", ErrorCodes.GENERIC_ERROR );
		}
		finally
		{
			if ( httpDeleteWithEntity != null )
			{
				httpDeleteWithEntity.releaseConnection();
			}
		}
	}

	@Override
	public String putRequest( String requestJson, boolean retCheck ) throws FBException
	{
		String jsonResponse = null;
		PutMethod putMethod = null;
		try
		{
			HttpClient httpClient = new HttpClient();
			putMethod = new PutMethod( getUri( this.serviceType, this.endPoint ) );
			putMethod.setRequestHeader( "Content-Type", contentType.getContentType() );
			putMethod.setRequestHeader( "Authorization", userSession.getEncodedAuthorisation() );
			putMethod.setRequestBody( requestJson );

			int statusCode = httpClient.executeMethod( putMethod );
			if ( statusCode == 200 )
			{
				jsonResponse = putMethod.getResponseBodyAsString();
			}
			else
			{
				throw new FBException( "Request Failed", ErrorCodes.HTTP_STATUS_NOT_OK );
			}
			//System.out.println( "jsonResponse \n" + jsonResponse );

			if ( retCheck )
			{
				ObjectMapper objectMapper = new ObjectMapper();
				byte[] jsonData = jsonResponse.getBytes();
				JsonNode rootNode = null;
				try
				{
					rootNode = objectMapper.readTree( jsonData );
				}
				catch ( IOException e )
				{
					throw new FBException( "JSON response parse error", ErrorCodes.INCORRECT_JSON_RESPONSE );
				}

				boolean success = rootNode.get( "ret" ).asBoolean();
				if ( !success )
				{
					String message = rootNode.get( "message" ).asText();
					throw new FBException( message, ErrorCodes.FAIL_RESPONSE );
				}
			}

			return jsonResponse;
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			throw new FBException( "GENERAL ERROR", ErrorCodes.GENERIC_ERROR );
		}
		finally
		{
			if ( putMethod != null )
				putMethod.releaseConnection();
		}
	}

}
