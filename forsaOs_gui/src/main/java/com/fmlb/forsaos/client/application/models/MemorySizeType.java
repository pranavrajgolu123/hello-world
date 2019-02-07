package com.fmlb.forsaos.client.application.models;

public enum MemorySizeType
{

	MB( "MB" ), GB( "GB" ), TB( "TB" ), PB( "PB" ), B( "B" ), b( "b" ), KiB(
			"KiB" );

	private String value;

	private MemorySizeType( String value )
	{
		this.value = value;
	}

	public String getValue()
	{
		return this.value;
	}
}
