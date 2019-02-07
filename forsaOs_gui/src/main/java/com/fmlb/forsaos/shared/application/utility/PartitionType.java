package com.fmlb.forsaos.shared.application.utility;

public enum PartitionType
{
	PERSISTENT(0), BLINK(1), SNAPSHOT(2);
	
	private int type;

	private PartitionType( int type )
	{
		this.type = type;
	}

	public int getType()
	{
		return type;
	}
}
