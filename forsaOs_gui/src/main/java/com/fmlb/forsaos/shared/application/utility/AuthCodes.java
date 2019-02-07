package com.fmlb.forsaos.shared.application.utility;

public enum AuthCodes
{

	NAAS_MANAGEMENT_AUTH_CODE( 2 ),

	MANAGEMENT_AUTH_CODE( 4 ),

	VIRTUALIZATION_MANAGEMENT_AUTH_CODE( 8 ),

	CENTRAL_MANAGEMENT_AUTH_CODE( 16 ),

	NODE_MANAGEMENT_AUTH_CODE( 32 ),

	NETWORKING_MANAGEMENT_AUTH_CODE( 64 ),

	CONTROLLER_MANAGEMENT_AUTH_CODE( 128 );

	private int authValue;

	private AuthCodes( int authValue )
	{
		this.authValue = authValue;
	}

	public int getValue()
	{
		return authValue;
	}

}
