package com.fmlb.forsaos.shared.application.exceptions;

public class FBException extends Exception
{
	private static final long serialVersionUID = 1L;

	private String errorMessage;

	private int errorCode;

	public FBException()
	{
		super();

	}

	public FBException( String errorMessage, int errorCode )
	{
		System.out.println( "Error Message :: " + errorMessage + " Error code :: " + errorCode );
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage( String errorMessage )
	{
		this.errorMessage = errorMessage;
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode( int errorCode )
	{
		this.errorCode = errorCode;
	}

}
