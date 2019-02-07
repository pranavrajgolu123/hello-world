package com.fmlb.forsaos.shared.application.utility;

public enum ServiceTypes
{
	SDN( 5028 ), Scheduler( 5035 ), MWatcher( 5035 ), REST( 5031 ), Config(
			5033 ), Keystone( 5032 ), Central( 5035 ), Virtualization(
					5034 ), Lobby( 5036 ), controller( 5033 ), Coremu(
							5037 ), Stats( 5035 ), Routing( 5031 ), Networks(
									5039 ), Networkd( 5040 ), Repo(
											5045 ), Management( 5041 );

	private int portNumber;

	private ServiceTypes( int portNum )
	{
		this.portNumber = portNum;
	}

	public int getPortNumber()
	{
		return portNumber;
	}

}
