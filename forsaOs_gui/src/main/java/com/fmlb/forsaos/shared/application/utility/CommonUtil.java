package com.fmlb.forsaos.shared.application.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.models.REPOMainModel;
import com.fmlb.forsaos.client.application.models.REPOModel;
import com.fmlb.forsaos.client.application.models.REPOMountInfoModel;

public class CommonUtil
{
	public static String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

	public static String SYS_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String EVENT_LOG_REG_EX = "-*\\[(.+)\\]\\s\\[(.+)\\]\\s\\[(.+)\\]\\s\\[(.+)\\]\\s\\[(.+)\\]\\s*:\\s(.+)";

	public static final int EVENT_LOG_FETCH_COUNT = 100;

	public static String CELSIUS_SYMBOL = "\u00b0" + "C";

	public static List<REPOModel> getREPOModel( REPOMainModel repoMainModel )
	{
		List<REPOModel> repoModels = new ArrayList<REPOModel>();
		if ( repoMainModel.getMountinfo() == null )
		{
			REPOModel repoModel = new REPOModel( repoMainModel.getName(), null, repoMainModel.getProtocol(), null, null, repoMainModel );
			repoModels.add( repoModel );
		}
		else
		{
			for ( REPOMountInfoModel repoMountInfoModel : repoMainModel.getMountinfo() )
			{
				String mountStatus = repoMountInfoModel.getMountpoint().equals( "" ) ? "Not Mounted" : "Mounted";
				REPOModel repoModel = new REPOModel( repoMainModel.getName(), repoMountInfoModel.getName(), repoMainModel.getProtocol(), repoMountInfoModel.getSource(), mountStatus, repoMainModel );
				repoModels.add( repoModel );
			}
		}
		return repoModels;
	}

	public static HashMap<String, String> getEventLogLevels()
	{
		HashMap<String, String> eventLogLevels = new HashMap<String, String>();
		eventLogLevels.put( "ALL", "all" );
		eventLogLevels.put( "INFO", "info" );
		eventLogLevels.put( "WARNING", "warning" );
		eventLogLevels.put( "ERROR", "error" );
		eventLogLevels.put( "FATAL", "fatal" );
		return eventLogLevels;
	}

	public static HashMap<String, String> getEventLogServices()
	{
		HashMap<String, String> eventLogServices = new HashMap<String, String>();
		eventLogServices.put( "ALL", "all" );
		eventLogServices.put( "ALERT", "alert" );
		eventLogServices.put( "CENTRAL", "central" );
		eventLogServices.put( "CONTROLLER", "controller" );
		eventLogServices.put( "KEYSTONE", "keystone" );
		eventLogServices.put( "MANAGEMENT", "management" );
		eventLogServices.put( "MWATCHER", "mwatcher" );
		eventLogServices.put( "SOFTWARE DEFINED NETWORK", "naas" );
		eventLogServices.put( "NETWORKD", "networkd" );
		eventLogServices.put( "NODE", "node" );
		eventLogServices.put( "SCHEDULER", "scheduler" );
		eventLogServices.put( "VIRTUALIZATION", "virt" );
		eventLogServices.put( "UPS", "ups" );
		return eventLogServices;
	}

}
