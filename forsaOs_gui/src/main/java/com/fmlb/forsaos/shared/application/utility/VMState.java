package com.fmlb.forsaos.shared.application.utility;

public enum VMState
{
	START( "start" ), PAUSE( "pause" ), RESUME( "resume" ), SHUTDOWN(
			"shutdown" ), POWEROFF( "poweroff" ), DESTROY( "destroy" );

	String value;

	private VMState( String value )
	{
		this.value = value;
	}

	public String getValue()
	{
		return this.value;
	}

	public static VMState getVMState( String value )
	{
		String sValue = value.toLowerCase();
		switch( sValue )
		{
		case "start":
			return VMState.START;
		case "pause":
			return VMState.PAUSE;
		case "resume":
			return VMState.RESUME;
		case "shutdown":
			return VMState.SHUTDOWN;
		case "power off":
			return VMState.POWEROFF;
		case "destroy":
			return VMState.DESTROY;
		default:
			return null;
		}
	}
}
