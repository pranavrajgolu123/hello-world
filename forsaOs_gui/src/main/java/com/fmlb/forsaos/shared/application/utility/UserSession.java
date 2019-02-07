package com.fmlb.forsaos.shared.application.utility;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Base64;

public class UserSession
{
	private String encodedAuthorisation;

	public UserSession( String userName, String password )
	{
		this.encodedAuthorisation = "Basic " + new String( Base64.encode( ( userName + ":" + password ).getBytes() ) );
	}

	public String getEncodedAuthorisation()
	{
		return encodedAuthorisation;
	}
}
