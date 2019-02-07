package com.fmlb.forsaos.client.application.models;

public enum OSFlagType
{
	EXT2( "EXT 2" ), EXT3( "EXT 3" ), EXT4( "EXT 4" ), FAT16( "FAT 16" ), FAT32(
			"FAT 32" ), HFS( "HFS" ), HFSPLUS( "HFS+" ), HFSJPLUS(
					"HFSJ+" ), NTFS( "NTFS" ), ZFS( "ZFS" ), NONE( "NONE" );

	private String value;

	private OSFlagType( String value )
	{
		this.value = value;
	}

	public String getValue()
	{
		return this.value;
	}
}
