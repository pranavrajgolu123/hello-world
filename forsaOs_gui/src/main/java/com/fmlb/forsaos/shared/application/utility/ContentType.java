package com.fmlb.forsaos.shared.application.utility;

public enum ContentType
{
	application_json( "application/json" );

	private String content;

	private ContentType( String value )
	{
		this.content = value;
	}

	public String getContentType()
	{
		return content;
	}
}
