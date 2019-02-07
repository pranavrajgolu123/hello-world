package com.fmlb.forsaos.client.application.services;

import com.fmlb.forsaos.shared.application.utility.ContentType;
import com.fmlb.forsaos.shared.application.utility.RequestMethod;
import com.fmlb.forsaos.shared.application.utility.ResourceServiceEndpoints;
import com.fmlb.forsaos.shared.application.utility.ServiceTypes;
import com.fmlb.forsaos.shared.application.utility.UserSession;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestException;

public class ResourceServiceImpl implements ResourceService
{
	private final static String uri;

	private final static int DEFAULT_TIMEOUT = 5000;

	private final UserSession userSession;

	private final RequestMethod requestMethod;

	private final ServiceTypes serviceType;

	private final ResourceServiceEndpoints endPoint;

	private final ContentType contentType;

	static
	{
		// URL to be read here from js file or property file.
		uri = "http://10.10.7.237";
	}

	public ResourceServiceImpl( UserSession userSession, RequestMethod reqMethod, ServiceTypes serviceType, ResourceServiceEndpoints endpoint, ContentType contentType )
	{
		super();
		this.userSession = userSession;
		this.requestMethod = reqMethod;
		this.endPoint = endpoint;
		this.serviceType = serviceType;
		this.contentType = contentType;
	}

	public static String getUri( ServiceTypes serviceType, ResourceServiceEndpoints endPoints )
	{
		return com.google.gwt.http.client.URL.encode( uri + ":" + serviceType.getPortNumber() + endPoints.getPath() );
	}

	@Override
	public void sendRequest( String requestData, AsyncRestRequestCallback asyncRestRequestCallback )
	{
		RequestBuilder requestBuilder = new RequestBuilder( getRequestMethod( requestMethod ), getUri( this.serviceType, this.endPoint ) );
		requestBuilder.setHeader( "Content-Type", contentType.getContentType() );
		requestBuilder.setHeader( "Authorization", userSession.getEncodedAuthorisation() );
		try
		{
			requestBuilder.sendRequest( requestData, asyncRestRequestCallback );
		}
		catch ( RequestException e )
		{
			asyncRestRequestCallback.onError( null, e );
		}
	}

	private Method getRequestMethod( RequestMethod requestMethod )
	{
		switch( requestMethod )
		{
		case GET:
			return RequestBuilder.GET;
		case POST:
			return RequestBuilder.POST;
		case PUT:
			return RequestBuilder.PUT;
		case DELETE:
			return RequestBuilder.DELETE;
		case HEAD:
			return RequestBuilder.HEAD;
		default:
			break;
		}
		return RequestBuilder.GET;
	}

}
