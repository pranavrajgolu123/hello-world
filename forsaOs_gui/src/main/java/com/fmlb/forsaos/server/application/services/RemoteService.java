package com.fmlb.forsaos.server.application.services;

import com.fmlb.forsaos.shared.application.exceptions.FBException;

public interface RemoteService
{
	//String getRequest( boolean retCheck ) throws FBException;

	String postRequest( String requestJson, boolean retCheck ) throws FBException;

	String getWithRequestData( String data, boolean retCheck ) throws FBException;

	String deleteRequest( String requestJson, boolean retCheck ) throws FBException;

	String putRequest( String requestJson, boolean retCheck ) throws FBException;
}
