package com.fmlb.forsaos.server;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fmlb.forsaos.client.application.common.CommonService;
import com.fmlb.forsaos.client.application.common.NetworkDeviceType;
import com.fmlb.forsaos.client.application.models.*;
import com.fmlb.forsaos.server.application.services.RemoteService;
import com.fmlb.forsaos.server.application.services.RemoteServiceImpl;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.server.application.utility.ServerUtil;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.fmlb.forsaos.shared.application.utility.CommonUtil;
import com.fmlb.forsaos.shared.application.utility.ContentType;
import com.fmlb.forsaos.shared.application.utility.ErrorCodes;
import com.fmlb.forsaos.shared.application.utility.PartitionType;
import com.fmlb.forsaos.shared.application.utility.RequestMethod;
import com.fmlb.forsaos.shared.application.utility.ResourceServiceEndpoints;
import com.fmlb.forsaos.shared.application.utility.RestCallUtil;
import com.fmlb.forsaos.shared.application.utility.ServiceTypes;
import com.fmlb.forsaos.shared.application.utility.UserSession;
import com.fmlb.forsaos.shared.application.utility.VMState;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CommonServiceImpl extends RemoteServiceServlet implements CommonService
{

	// private HttpSession httpsSession;

	private static final long serialVersionUID = 1L;

	private static HashMap<String, UserSessionObject> userIdToSessionObjectMap = new HashMap<String, UserSessionObject>();

	@Override
	public UserSessionObject doLogin( CurrentUser currentUser ) throws FBException
	{
		UserSessionObject userSessionObject = new UserSessionObject( "" );
		RemoteService remoteService = new RemoteServiceImpl( new UserSession( currentUser.getUserName(), currentUser.getPassword() ), currentUser.getWindowLocation(), RequestMethod.GET, ServiceTypes.Keystone, ResourceServiceEndpoints.LOGIN, ContentType.application_json );
		try
		{
			LogLoginDataModel logLoginDataModel = new LogLoginDataModel( currentUser.getUserName() );
			remoteService.getWithRequestData( ServerUtil.getJSONStringOfObject( logLoginDataModel ), true );
			createSession( currentUser );
			userSessionObject.setSessionId( this.getThreadLocalRequest().getSession().getId() );
			userIdToSessionObjectMap.put( currentUser.getUserName(), userSessionObject );
		}
		catch ( FBException e )
		{
			invalidateSession();
			e.printStackTrace();
			throw e;
		}
		return userSessionObject;
	}

	@Override
	public Boolean logout()
	{
		invalidateSession();
		return true;
	}

	@Override
	public Boolean createBlink( CreateBlinkModel createBlink ) throws FBException
	{

		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Management, ResourceServiceEndpoints.BLINK_NEW_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( createBlink ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public List<BlinkModel> getBlink( String data ) throws FBException
	{
		List<BlinkModel> blinkModels = new ArrayList<BlinkModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Management, ResourceServiceEndpoints.BLINK_NAME_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				BlinkModel blinkModel = new BlinkModel( element.get( "vm_session" ).asBoolean(), element.get( "type" ).asInt(), element.get( "timestamp" ).asLong(), element.get( "sdn" ).asBoolean(), element.get( "keystone" ).asBoolean(), element.get( "_id" ).get( "$oid" ).asText(), element.get( "lnet" ).asBoolean(), element.get( "networkd" ).asBoolean(), element.get( "store_id" ).asText(), element.get( "name" ).asText(), element.get( "store_path" ).asText(), element.get( "schedule_id" ).asText() );
				blinkModel.setDate( ServerUtil.getFormattedDate( blinkModel.getTimestamp() ) );
				blinkModels.add( blinkModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return blinkModels;
	}

	@Override
	public ProgressModel getProgress() throws FBException
	{
		ProgressModel progressModel = null;
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Management, ResourceServiceEndpoints.BLINK_PROGRESS, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( RestCallUtil.EMPTY_BODY, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );
			JsonNode rootNode = objectMapper.readTree( jsonData );

			if ( rootNode != null )
			{
				progressModel = objectMapper.readValue( rootNode.toString(), ProgressModel.class );
			}

		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return progressModel;
	}

	@Override
	public List<String> getPartition( String data ) throws FBException
	{
		List<String> partitionNames = new ArrayList<String>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Management, ResourceServiceEndpoints.BLINK_PARTITION_NAMES, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.get( "res" );
			Iterator<JsonNode> elements = resNode.elements();

			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();

				partitionNames.add( element.get( "name" ).asText() );

			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return partitionNames;
	}

	@Override
	public Boolean restoreBlink( RestoreBlinkModel restoreBlink ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Management, ResourceServiceEndpoints.BLINK_RESTORE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( restoreBlink ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deleteBlink( DeleteBlinkModel deleteBlinkModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Management, ResourceServiceEndpoints.BLINK_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( deleteBlinkModel ), false );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public List<LEMModel> getLems( String data, boolean addDelayFl ) throws FBException
	{
		List<LEMModel> lemModels = new ArrayList<LEMModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			addDelay( addDelayFl );
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				LEMModel lemModel = new LEMModel( element.get( "name" ).asText(), "", element.get( "size" ).asLong(), MemorySizeType.B, element.get( "pbs" ).asLong(), OSFlagType.NONE, "", element.get( "_id" ).get( "$oid" ).asText(), "_", 1l, element.get( "readonly" ).asBoolean() );
				lemModels.add( lemModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return lemModels;
	}

	@Override
	public Boolean createScheduler( SchedulerModel createSchedulerModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Scheduler, ResourceServiceEndpoints.MANG_SCHEDULER_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( createSchedulerModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;

	}

	@Override
	public List<SchedulerModel> getScheduler( String data, boolean addDelayFl ) throws FBException
	{
		List<SchedulerModel> getSchedulerModels = new ArrayList<SchedulerModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;

			}
			addDelay( addDelayFl );
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Scheduler, ResourceServiceEndpoints.MANG_SCHEDULER_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );
			// System.out.println(responseData);
			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{

				JsonNode element = elements.next();
				SchedulerModel createSchedulerModel = new SchedulerModel();
				createSchedulerModel.setName( element.get( "name" ).asText() );
				createSchedulerModel.setStatus( element.get( "status" ).asInt() );
				createSchedulerModel.setTimestamp( element.get( "time" ).asLong() * 1000 );
				createSchedulerModel.setDate( ServerUtil.getFormattedDate( createSchedulerModel.getTimestamp() ) );
				getSchedulerModels.add( createSchedulerModel );

			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return getSchedulerModels;

	}

	@Override
	public Boolean deleteScheduler( SchedulerModel deleteScheduler ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.DELETE, ServiceTypes.Scheduler, ResourceServiceEndpoints.MANG_SCHEDULER_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( deleteScheduler ), true );
		System.out.println("ppppp   "+responseData);

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean pauseScheduler( SchedulerModel pauseScheduler ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Scheduler, ResourceServiceEndpoints.MANG_SCHEDULER_PAUSE, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( pauseScheduler ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean resumeScheduler( SchedulerModel resumeScheduler ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Scheduler, ResourceServiceEndpoints.MANG_SCHEDULER_RESUME, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( resumeScheduler ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean createLem( LEMJSONModel lemjsonModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( lemjsonModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean createMultiLem( CreateMultiLEMModel multiLem ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_MULTI_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( multiLem ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean createLemAndAssisnToVm( CreateLEMandAssisgnToVMmodel createLEMandAssisgnToVMmodel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_ATTACH_LEM, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( createLEMandAssisgnToVMmodel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deleteLem( LEMDeleteJSONModel lemjsonModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( lemjsonModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Map<Boolean, List<String>> deleteMultipleLem( List<LEMDeleteJSONModel> lemDeleteJSONModels ) throws FBException
	{
		Boolean success = true;

		Map<Boolean, List<String>> responseMap = new HashMap<Boolean, List<String>>();
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( SerializationFeature.INDENT_OUTPUT, true );
			StringWriter lemJsonStr = new StringWriter();
			objectMapper.writeValue( lemJsonStr, lemDeleteJSONModels );
			//System.out.println( "LEMDeleteJSONModel LIST JSON is\n" + lemJsonStr );

			String prefix = "{ \"multi\":";
			String suffix = "}";

			String requestJSON = prefix + lemJsonStr.toString() + suffix;

			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_MULTI_DELETE, ContentType.application_json );
			String responseData = remoteService.deleteRequest( requestJSON, false );
			//System.out.println( responseData );
			byte[] jsonData = responseData.getBytes();
			JsonNode rootNode = objectMapper.readTree( jsonData );

			Iterator<JsonNode> deletedLems = rootNode.get( "deleted" ).elements();
			Iterator<JsonNode> deleteFailedLems = rootNode.get( "delete_failed" ).elements();

			List<String> deleteLemIds = new ArrayList<String>();

			while ( deletedLems.hasNext() )
			{
				JsonNode element = deletedLems.next();

				deleteLemIds.add( element.asText() );
			}
			//System.out.println( deleteLemIds );

			List<String> deleteFailedMessages = new ArrayList<String>();
			while ( deleteFailedLems.hasNext() )
			{
				JsonNode element = deleteFailedLems.next();
				deleteFailedMessages.add( element.get( "message" ).asText() );
			}
			//System.out.println( deleteFailedMessages );

			responseMap.put( true, deleteLemIds );
			responseMap.put( false, deleteFailedMessages );

			if ( !success )
			{
				/*
				 * message = rootNode.get( "message" ).asText(); System.out.println( message );
				 */
				throw new FBException( "", ErrorCodes.LEMS_DELETE_FAIL );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return responseMap;
	}

	@Override
	public CapacityChartModel getCapacityChartData() throws FBException
	{
		CapacityChartModel capacityChartModel = null;
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.MWatcher, ResourceServiceEndpoints.STATS_AMPL_CUR, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( RestCallUtil.EMPTY_BODY, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );

			if ( rootNode != null )
			{
				//System.out.println( "capacity chart response " );
				//System.out.println( rootNode.toString() );
				double availableSize = rootNode.get( "available_size" ).asDouble();

				if ( availableSize < 0 )
				{
					availableSize = 0;
				}

				double curTotalSize = rootNode.get( "cur_total_size" ).asDouble();
				double rtmTotalSize = rootNode.get( "rtm_total_size" ).asDouble();
				String ampl = rootNode.get( "ampl" ).asText();

				double availablePercent = ( double ) ( ( availableSize / curTotalSize ) * 100 );
				double allocatedSize = curTotalSize - availableSize;

				double allocatedPercent = ( double ) ( ( allocatedSize / curTotalSize ) * 100 );
				double physicalSize = rtmTotalSize;
				double usagableSize = curTotalSize;

				//System.out.println( "Capacity chart coverted values " );
				//System.out.println( "availableSize " + availableSize );
				//System.out.println( "curTotalSize " + curTotalSize );
				//System.out.println( "rtmTotalSize " + rtmTotalSize );
				//System.out.println( "ampl " + ampl );
				//System.out.println( "availablePercent " + availablePercent );
				//System.out.println( "allocatedSize " + allocatedSize );
				//System.out.println( "allocatedPercent " + allocatedPercent );
				//System.out.println( "physicalSize " + physicalSize );
				//System.out.println( "usagableSize " + usagableSize );

				capacityChartModel = new CapacityChartModel( String.valueOf( availablePercent ), availableSize, String.valueOf( allocatedPercent ), allocatedSize, physicalSize, usagableSize, ServerUtil.formatDecimalToTwoPlaces( ampl ) + " X" );
				capacityChartModel.setUnAllocatedPercent( String.valueOf( 100 - allocatedPercent ) );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return capacityChartModel;
	}

	@Override
	public String getRTMUsageChartData() throws FBException
	{
		String data = "";
		ObjectMapper objectMapper = null;
		ArrayList<RTMChartData> rtmChartDataList = new ArrayList<>();
		try
		{
			objectMapper = new ObjectMapper();
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.MWatcher, ResourceServiceEndpoints.STATS_AMPL_CUR, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( RestCallUtil.EMPTY_BODY, false );
			byte[] jsonData = responseData.getBytes();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			if ( rootNode != null )
			{
				//	double curTotalSize = rootNode.get( "cur_total_size" ).asDouble();
				double physicalRTMsize = rootNode.get( "rtm_total_size" ).asDouble();
				remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.MWatcher, ResourceServiceEndpoints.STATS_AMPL_ALL, ContentType.application_json );
				responseData = remoteService.getWithRequestData( RestCallUtil.EMPTY_BODY, false );

				jsonData = responseData.getBytes();

				rootNode = objectMapper.readTree( jsonData );
				JsonNode resNode = rootNode.path( "res" );
				Iterator<JsonNode> elements = resNode.elements();
				while ( elements.hasNext() )
				{
					//System.out.println( "Next day data" );
					JsonNode element = elements.next();

					JsonNode readElem = element.get( "read" );
					Iterator<JsonNode> readArray = readElem.elements();

					JsonNode writeElem = element.get( "write" );
					Iterator<JsonNode> writeArray = writeElem.elements();

					JsonNode avail_rtm_size = element.get( "avail_rtm_size" );
					Iterator<JsonNode> avail_rtm_sizeArray = avail_rtm_size.elements();

					JsonNode timePointElem = element.get( "timepoint" );
					Iterator<JsonNode> timepointArray = timePointElem.elements();
					boolean isFirst = true;
					long prevWrite = 0;
					long prevRead = 0;
					long prevTimepoint = 0;
					while ( timepointArray.hasNext() )
					{

						if ( isFirst )
						{
							prevWrite = writeArray.next().asLong();
							prevRead = readArray.next().asLong();
							prevTimepoint = timepointArray.next().asLong();
							isFirst = false;
						}
						else
						{
							double cur_usage_size = physicalRTMsize - ( avail_rtm_sizeArray.next().asDouble() * 4096 );
							long read = readArray.next().asLong();
							long write = writeArray.next().asLong();
							long timePoint = timepointArray.next().asLong();
							long timeDiff = timePoint - prevTimepoint;
							double readThrough = ( ( read - prevRead ) * 4096 ) / ( timeDiff );
							double writeThrough = ( ( write - prevWrite ) * 4096 ) / ( timeDiff );
							if ( readThrough < 0 )
							{
								readThrough = 0;
							}
							if ( writeThrough < 0 )
							{
								writeThrough = 0;
							}
							RTMChartData rtmChartData = new RTMChartData( read, write, physicalRTMsize, cur_usage_size, timePoint );
							rtmChartData.setReadThrough( readThrough );
							rtmChartData.setWriteThrough( writeThrough );
							rtmChartDataList.add( rtmChartData );
							prevRead = read;
							prevWrite = write;
							prevTimepoint = timePoint;
						}
					}
				}
				data = objectMapper.writeValueAsString( rtmChartDataList );

			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return data;
	}

	@Override
	public SystemStatusChartModel getSystemStatusChartData() throws FBException
	{
		SystemStatusChartModel systemStatusChartModel = null;
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.MWatcher, ResourceServiceEndpoints.STATS_SYS_CURRENT, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( RestCallUtil.EMPTY_BODY, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );

			double cpuUsage = rootNode.get( "cpu_overall" ).asDouble();
			double memUsage = rootNode.get( "memory" ).asDouble();
			// TODO No data being send at the moment. Hard coding to 0;
			// double coreTemp = rootNode.get( "temp" ).asDouble();

			//writing this as CFTM service may be down
			double coreTempPer = 0;
			double higherTemp = 0;
			if ( rootNode.get( "CPU1_temperature" ) != null && rootNode.get( "CPU2_temperature" ) != null )
			{
				double cpuTemp1 = rootNode.get( "CPU1_temperature" ).asDouble();
				double cpuTemp2 = rootNode.get( "CPU2_temperature" ).asDouble();
				higherTemp = cpuTemp1;
				if ( cpuTemp2 > higherTemp )
				{
					higherTemp = cpuTemp2;
				}
				coreTempPer = ( double ) ( ( higherTemp / ApplicationConstants.MAX_TEMP ) * 100 );
			}
			systemStatusChartModel = new SystemStatusChartModel( memUsage, cpuUsage, higherTemp, coreTempPer );

		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return systemStatusChartModel;
	}

	@Override
	public List<VMModel> getVMsList( String data, boolean addDelayFl ) throws FBException
	{

		List<VMModel> vmModels = new ArrayList<VMModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			addDelay( addDelayFl );
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, true );
			//System.out.println( responseData );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );
			JsonNode rootNode = objectMapper.readTree( jsonData );

			JsonNode resNode = rootNode.path( "res" );
			VMModel[] readValue = objectMapper.readValue( resNode.toString(), VMModel[].class );
			for ( VMModel vmModel : readValue )
			{
				vmModels.add( vmModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return vmModels;

	}

	private MemorySizeType getMemorySizeType( String memType )
	{
		if ( memType.equalsIgnoreCase( MemorySizeType.MB.getValue() ) )
		{
			return MemorySizeType.MB;
		}
		else if ( memType.equalsIgnoreCase( MemorySizeType.GB.getValue() ) )
		{
			return MemorySizeType.GB;
		}
		else if ( memType.equalsIgnoreCase( MemorySizeType.TB.getValue() ) )
		{
			return MemorySizeType.TB;
		}
		else if ( memType.equalsIgnoreCase( MemorySizeType.PB.getValue() ) )
		{
			return MemorySizeType.PB;
		}
		else if ( memType.equalsIgnoreCase( MemorySizeType.B.getValue() ) )
		{
			return MemorySizeType.B;
		}
		else if ( memType.equalsIgnoreCase( MemorySizeType.b.getValue() ) )
		{
			return MemorySizeType.b;
		}
		else if ( memType.equalsIgnoreCase( MemorySizeType.KiB.getValue() ) )
		{
			return MemorySizeType.KiB;
		}
		return MemorySizeType.B;

	}

	@Override
	public Boolean createVM( VMCreateModel vmCreateModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( vmCreateModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}

		return success;
	}

	@Override
	public Boolean createVMWithLem( VMCreateWithNewLemModel vmCreateModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( vmCreateModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}

		return success;
	}

	@Override
	public Boolean createVMAttachLem( VMCreateAttachLemModel vmCreateModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( vmCreateModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}

		return success;
	}

	@Override
	public Boolean createVMCloneLem( VMCreateCloneLemModel vmCreateModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( vmCreateModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}

		return success;
	}

	@Override
	public RTMModel getRTM( String data ) throws FBException
	{

		List<RTMModel> rtmModels = new ArrayList<RTMModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.SELECTED_FIELDS_RTM;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_RTM_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );
			//System.out.println( responseData );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				RTMModel rtmModel = new RTMModel( element.get( "name" ).asText() );
				//LEMModel lemModel = new LEMModel( element.get( "name" ).asText(), "", element.get( "size" ).asLong(), MemorySizeType.B, element.get( "pbs" ).asLong(), OSFlagType.NONE, "", element.get( "_id" ).get( "$oid" ).asText(), "_", 1l, element.get( "readonly" ).asBoolean() );
				rtmModels.add( rtmModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return rtmModels.isEmpty() ? null : rtmModels.get( 0 );

	}

	@Override
	public Boolean updateLEMName( UpdateLEMNameModel updateLEMNameModel ) throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_UPDATE, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( updateLEMNameModel ), true );

		if ( responseData != null )
		{
			success = true;
		}

		return success;
	}

	@Override
	public Boolean deleteVM( VMModel vmModel, Boolean deleteLem ) throws FBException
	{
		Boolean success = false;
		Object vmDeleteJSONModel;
		if ( deleteLem )
		{
			vmDeleteJSONModel = new VMDeleteWithLEMJSONModel( vmModel.get_id().get$oid(), vmModel.getName(), deleteLem );
		}
		else
		{
			vmDeleteJSONModel = new VMDeleteJSONModel( vmModel.get_id().get$oid(), vmModel.getName() );
		}
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.DELETE, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( vmDeleteJSONModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean changeVMState( VMModel vmModel, VMState vmState ) throws FBException
	{
		Boolean success = false;
		ResourceServiceEndpoints resourceServiceEndpoints = null;
		//System.out.println( "Changing vm status of " + vmModel.getName() + " to " + vmState.getValue() );
		switch( vmState )
		{
		case START:
			resourceServiceEndpoints = ResourceServiceEndpoints.VIRT_VM_START;
			break;
		case PAUSE:
			resourceServiceEndpoints = ResourceServiceEndpoints.VIRT_VM_PAUSE;
			break;
		case RESUME:
			resourceServiceEndpoints = ResourceServiceEndpoints.VIRT_VM_RESUME;
			break;
		case SHUTDOWN:
			resourceServiceEndpoints = ResourceServiceEndpoints.VIRT_VM_SHUTDOWN;
			break;
		case POWEROFF:
			resourceServiceEndpoints = ResourceServiceEndpoints.VIRT_VM_DESTROY;
			break;
		default:
			break;
		}
		VMChangeStateJSONModel vmChangeStateJSONModel = new VMChangeStateJSONModel( vmModel.get_id().get$oid(), vmModel.getName() );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, resourceServiceEndpoints, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( vmChangeStateJSONModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean attachOrDetachISO( VMModel vmModel, String cd_path, Boolean attachISO ) throws FBException
	{
		Boolean success = false;
		ResourceServiceEndpoints resourceServiceEndpoints = null;

		if ( attachISO )
		{
			resourceServiceEndpoints = ResourceServiceEndpoints.VIRT_VM_ATTACH_CD;
		}
		else
		{
			resourceServiceEndpoints = ResourceServiceEndpoints.VIRT_VM_DETACH_CD;
		}

		VMAttachDetachISOJSONModel vmIsoModel = new VMAttachDetachISOJSONModel( vmModel.get_id().get$oid(), vmModel.getName(), cd_path );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, resourceServiceEndpoints, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( vmIsoModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean attachLemsToVm( VMModel vmModel, List<LEMModel> lems ) throws FBException
	{
		Boolean success = false;
		LEMToVMJSONModel[] lemToVMJSONModels = new LEMToVMJSONModel[lems.size()];
		for ( int i = 0; i < lems.size(); i++ )
		{
			lemToVMJSONModels[i] = new LEMToVMJSONModel( lems.get( i ).getId(), lems.get( i ).getLemName() );
		}
		AttachLEMToVMJsonModel attachLEMToVMJsonModel = new AttachLEMToVMJsonModel( lemToVMJSONModels, vmModel.getName(), vmModel.get_id().get$oid() );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_ATTACH_LEM, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( attachLEMToVMJsonModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean detachLemsToVm( VMModel vmModel, LEMModel lems, Boolean deleteLem ) throws FBException
	{
		Boolean success = false;
		DetachLEMFromVMJsonModel detachLEMFromVMJsonModel = new DetachLEMFromVMJsonModel( vmModel.getName(), vmModel.get_id().get$oid(), lems.getLemName(), lems.getId(), deleteLem );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_DETACH_LEM, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( detachLEMFromVMJsonModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean attachNetToVm( VMModel vmModel, LVNetCreateRequestModel lvNetCreateRequestModel ) throws FBException
	{
		Boolean success = false;

		try
		{
			RemoteService createAdapterService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LVNET_CREATE, ContentType.application_json );
			String createAdapterResponseData = createAdapterService.postRequest( ServerUtil.getJSONStringOfObject( lvNetCreateRequestModel ), true );

			String net_id = null;
			if ( createAdapterResponseData != null && !createAdapterResponseData.isEmpty() )
			{
				byte[] jsonData = createAdapterResponseData.getBytes();
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree( jsonData );
				net_id = rootNode.get( "id" ).asText();
			}
			else
			{
				throw new FBException( "Failed to create LVNET for interface.", ErrorCodes.GENERIC_ERROR );
			}

			AttachNetToVMJsonModel attachNetToVMJsonModel = new AttachNetToVMJsonModel( vmModel.getName(), vmModel.get_id().get$oid(), lvNetCreateRequestModel.getName(), net_id );
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_ATTACH_NET, ContentType.application_json );
			String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( attachNetToVMJsonModel ), true );

			if ( responseData != null && !responseData.isEmpty() )
			{
				success = true;
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			throw e;
		}
		return success;
	}

	@Override
	public Boolean detachNetFromVm( VMModel vmModel, NicModel nicModel ) throws FBException
	{
		Boolean success = false;
		DetachNetToVMJsonModel detachNetToVMJsonModel = new DetachNetToVMJsonModel( vmModel.getName(), vmModel.get_id().get$oid(), nicModel.getName(), nicModel.getId(), null );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_DETACH_NET, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( detachNetToVMJsonModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
			deleteAdapter( new NameModel( nicModel.getName() ) );
		}
		return success;
	}

	@Override
	public Boolean attachSDNToVm( VMModel vmModel ) throws FBException
	{
		Boolean success = false;
		AttachSDNToVMJsonModel attachSDNToVMJsonModel = new AttachSDNToVMJsonModel( vmModel.getName(), vmModel.get_id().get$oid(), "", "" );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_ATTACH_SDN, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( attachSDNToVMJsonModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean detachSDNFromVm( VMModel vmModel ) throws FBException
	{
		Boolean success = false;
		DetachSDNToVMJsonModel detachSDNToVMJsonModel = new DetachSDNToVMJsonModel( vmModel.getName(), vmModel.get_id().get$oid(), 0 );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_DETACH_SDN, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( detachSDNToVMJsonModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean updateVMDetails( VMModel vmModel, String type ) throws FBException
	{
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_UPDATE, ContentType.application_json );
		Boolean success = false;
		String responseData;
		VMUpdateModel vmUpdateModel = null;
		if ( type.equalsIgnoreCase( "advanced" ) )
		{
			vmUpdateModel = new VMUpdateModel( vmModel.getVcpu(), vmModel.getName(), vmModel.getMemory(), vmModel.getMemory_unit(), vmModel.getCores(), vmModel.getSockets(), vmModel.getThreads(), null, null, null );
		}
		else if ( type.equalsIgnoreCase( "videotype" ) )
		{
			vmUpdateModel = new VMUpdateModel( null, vmModel.getName(), null, null, null, null, null, vmModel.getVideo_type(), null, null );
		}
		else if ( type.equalsIgnoreCase( "cpumodel" ) )
		{
			vmUpdateModel = new VMUpdateModel( null, vmModel.getName(), null, null, null, null, null, null, vmModel.getCpu_model(), null );
		}
		else if ( type.equalsIgnoreCase( "graphicdriver" ) )
		{
			vmUpdateModel = new VMUpdateModel( null, vmModel.getName(), null, null, null, null, null, null, null, vmModel.getGraphic_driver() );
		}
		else
		{
			vmUpdateModel = new VMUpdateModel( vmModel.getVcpu(), vmModel.getName(), vmModel.getMemory(), vmModel.getMemory_unit(), null, null, null, null, null, null );
		}
		responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( vmUpdateModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	private CurrentUser getCurrentUserObject() throws FBException
	{
		HttpSession httpsSession = this.getThreadLocalRequest().getSession();
		if ( httpsSession != null && httpsSession.getAttribute( "currentUser" ) != null && isSessionValid( ( ( CurrentUser ) httpsSession.getAttribute( "currentUser" ) ).getUserName(), getSessionIdFromCookie() ) )
		{
			return ( CurrentUser ) httpsSession.getAttribute( "currentUser" );
		}
		else
		{
			CurrentUser currentUser = new CurrentUser();
			currentUser.setLoggedIn( false );
			invalidateSession();
			FBException fbException = new FBException( ApplicationConstants.INVALID_SESSION, 101 );
			throw fbException;
		}
	}

	private String getSessionIdFromCookie()
	{
		if ( this.getThreadLocalRequest() != null && this.getThreadLocalRequest().getCookies() != null && this.getThreadLocalRequest().getCookies().length > 0 )
		{
			for ( int i = 0; i < this.getThreadLocalRequest().getCookies().length; i++ )
			{
				if ( this.getThreadLocalRequest().getCookies()[i].getName().equalsIgnoreCase( ApplicationConstants.SESSION_ID ) )
				{
					return this.getThreadLocalRequest().getCookies()[i].getValue();
				}
			}
		}
		return "";
	}

	private void createSession( CurrentUser currentUser )
	{
		HttpSession httpsSession = this.getThreadLocalRequest().getSession( true );
		currentUser.setLoggedIn( true );
		if ( httpsSession.getAttribute( "currentUser" ) != null )
		{
			CurrentUser user = ( CurrentUser ) httpsSession.getAttribute( "currentUser" );

			if ( getSessionIdFromCookie().isEmpty() && user.getUserName().equals( currentUser.getUserName() ) )
			{
				invalidateSession();
			}
		}
		else
		{
			httpsSession.setAttribute( "currentUser", currentUser );

		}

		//System.out.println( "User logged in-------------------------------" + currentUser.getUserName() );
	}

	private void invalidateSession()
	{
		try
		{
			HttpSession httpsSession = this.getThreadLocalRequest().getSession();
			if ( httpsSession != null && httpsSession.getAttribute( "currentUser" ) != null )
			{
				( ( CurrentUser ) httpsSession.getAttribute( "currentUser" ) ).setLoggedIn( false );
				removeUserIdToSessionObject( ( ( CurrentUser ) httpsSession.getAttribute( "currentUser" ) ).getUserName(), getSessionIdFromCookie() );
				httpsSession.removeAttribute( "currentUser" );
				httpsSession.invalidate();
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private UserSession getUserSession() throws FBException
	{
		return new UserSession( getCurrentUserObject().getUserName(), getCurrentUserObject().getPassword() );
	}

	@Override
	public Boolean createRTM( String rtmName ) throws FBException
	{
		Boolean success = false;
		RTMModel rtmModel = new RTMModel( rtmName );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_RTM_CREATE, ContentType.application_json );

		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( rtmModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deleteRTM( RTMModel rtmModelTobeDel ) throws FBException
	{

		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.DELETE, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_RTM_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( rtmModelTobeDel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	public Boolean isSessionValid( String userName, String sessionId ) throws FBException
	{
		if ( userIdToSessionObjectMap != null && userIdToSessionObjectMap.containsKey( userName ) && userIdToSessionObjectMap.get( userName ).getSessionId().equalsIgnoreCase( sessionId ) )
		{
			return true;
		}
		return false;
	}

	public void removeUserIdToSessionObject( String userName, String sessionId ) throws FBException
	{
		if ( userIdToSessionObjectMap != null && userIdToSessionObjectMap.containsKey( userName ) && userIdToSessionObjectMap.get( userName ).getSessionId().equalsIgnoreCase( sessionId ) )
		{
			userIdToSessionObjectMap.remove( userName );
		}
	}

	@Override
	public CurrentUser isSessionValid( String sessionId ) throws FBException
	{
		CurrentUser currentUser = new CurrentUser();
		if ( userIdToSessionObjectMap != null )
		{
			for ( UserSessionObject usrSessionObj : userIdToSessionObjectMap.values() )
			{
				if ( usrSessionObj.getSessionId().equals( sessionId ) )
				{
					currentUser.setUserName( getCurrentUserObject().getUserName() );
					currentUser.setPassword( getCurrentUserObject().getPassword() );
					currentUser.setLoggedIn( true );
					break;
				}

			}

		}
		return currentUser;
	}

	@Override
	public Boolean updateVMName( VMModel vmModel, String new_name ) throws FBException
	{
		Boolean success = false;
		VMUpdateNameModel vmUpdateNameModel = new VMUpdateNameModel( vmModel.getName(), vmModel.get_id().get$oid(), new_name );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_UPDATE, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( vmUpdateNameModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean resetRTM() throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_RESET, ContentType.application_json );
		String responseData = remoteService.putRequest( RestCallUtil.EMPTY_BODY, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;

	}

	@Override
	public List<BridgeJSONModel> getAllBridges() throws FBException
	{
		List<BridgeJSONModel> bridgeJSONModels = new ArrayList<BridgeJSONModel>();
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_DEVICE_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( RestCallUtil.BRIDGES_FILTER_ALL, true );

			if ( responseData == null || responseData.isEmpty() )
			{
				throw new FBException( "Unable to retrive interfaces.", ErrorCodes.GENERIC_ERROR );
			}

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );
			JsonNode rootNode;
			JsonNode resNode;
			BridgeJSONModel[] readValue;

			rootNode = objectMapper.readTree( jsonData );
			resNode = rootNode.path( "res" );
			readValue = objectMapper.readValue( resNode.toString(), BridgeJSONModel[].class );
			for ( BridgeJSONModel bridgeJSONModel : readValue )
			{
				bridgeJSONModels.add( bridgeJSONModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			throw e;
		}
		return bridgeJSONModels;
	}

	@Override
	public Boolean createAdapter( LVNetCreateRequestModel lvNetCreateRequestModel ) throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LVNET_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( lvNetCreateRequestModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deleteAdapter( NameModel nameModel ) throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.DELETE, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LVNET_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( nameModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public NicModel getLvnet( String id ) throws FBException
	{
		NicModel nicModel = null;
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LVNET_GET, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( "{ \"id\":\"" + id + "\" }", true );

			if ( responseData == null || responseData.isEmpty() )
			{
				throw new FBException( "Unable to retrive interfaces.", ErrorCodes.GENERIC_ERROR );
			}
			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );
			JsonNode rootNode;

			rootNode = objectMapper.readTree( jsonData );
			nicModel = objectMapper.readValue( rootNode.toString(), NicModel.class );

			rootNode = objectMapper.readTree( jsonData );

		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			throw e;
		}
		return nicModel;
	}

	@Override
	public Boolean shutdownForsaOs() throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Networkd, ResourceServiceEndpoints.CONTROLLER_STOP_ALL, ContentType.application_json );
		String responseData = remoteService.putRequest( RestCallUtil.EMPTY_BODY, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;

	}

	@Override
	public Boolean restartForsaOs( RestartForsaOSModel restartForsaOSModel ) throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Networkd, ResourceServiceEndpoints.CONTROLLER_RESTART, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( restartForsaOSModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;

	}

	@Override
	public Boolean shutdownSystem() throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Networkd, ResourceServiceEndpoints.CONTROLLER_SYS_SHUTDOWN, ContentType.application_json );
		String responseData = remoteService.putRequest( RestCallUtil.EMPTY_BODY, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;

	}

	@Override
	public Boolean restartSystem() throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Networkd, ResourceServiceEndpoints.CONTROLLER_SYS_REBOOT, ContentType.application_json );
		String responseData = remoteService.putRequest( RestCallUtil.EMPTY_BODY, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;

	}

	@Override
	public String getFileSystemJSON( String maindirpath ) throws FBException
	{
//		System.out.println( "----------------------------------------------------------------------------------------" );
//		System.out.println( maindirpath );
		StringBuilder bigString = new StringBuilder( "" );
		File maindir = new File( maindirpath );

		if ( maindir.exists() && maindir.isDirectory() )
		{
			File arr[] = maindir.listFiles( new FileFilter()
			{
				@Override
				public boolean accept( File file )
				{
					return !file.isHidden();
				}
			} );
			bigString = bigString.append( "{" );
			recursiveGetFileTree( arr, 0, 0, bigString );
			bigString = bigString.append( "}" );
		}
		else
		{
			throw new FBException( "Unable to load file manager, no repository configured for ISO.", ErrorCodes.GENERIC_ERROR );
		}
//		System.out.println( "--------------------" );
//		System.out.println( bigString );
//		System.out.println( "----------------------------------------------------------------------------------------" );
		return bigString.toString();
	}

	public void recursiveGetFileTree( File[] arr, int index, int level, StringBuilder bigString )
	{
		if ( index == arr.length )
			return;

		if ( arr[index].isFile() )
		{
			bigString.append( "\"" + arr[index].getName() + "\" : \"file\"" );
			if ( index != arr.length - 1 )
			{
				bigString.append( ", " );
			}
			else
			{
				bigString.append( " " );
			}
		}
		else if ( arr[index].isDirectory() )
		{
			bigString.append( "\"" + arr[index].getName() + "\" : { " );

//			recursiveGetFileTree( arr[index].listFiles(), 0, level + 1, bigString );
			bigString.append( "} " );
			if ( index != arr.length - 1 )
			{
				bigString.append( ", " );
			}
			else
			{
				bigString.append( " " );
			}
		}
		recursiveGetFileTree( arr, ++index, level, bigString );
	}

	private void addDelay( boolean addDelayFl )
	{
		if ( addDelayFl )
		{
			sleep();
		}
	}

	private void sleep()
	{
		System.out.println( "Delay of 1 sec start" );
		try
		{
			Thread.sleep( 1000 );
		}
		catch ( InterruptedException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println( "Delay of 1 sec end" );
	}

	@Override
	public Boolean cloneLEM( CloneLEMModel cloneLEMModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_CLONE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( cloneLEMModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}

		return success;
	}

	@Override
	public Boolean cloneVM( CloneVMModel cloneVMModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_CLONE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( cloneVMModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}

		return success;
	}

	@Override
	public Boolean cloneLemAndAssisnToVm( CloneLEMandAssisgnToVMmodel cloneLEMandAssisgnToVMmodel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_ATTACH_LEM, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( cloneLEMandAssisgnToVMmodel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean createLEMSnapshot( LEMSnapshotModel lemSnapshotModel ) throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_SNAPSHOT_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( lemSnapshotModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean updateLEMSnapshot( UpdateLEMSnapshotModel updateLEMSnapshotModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_SNAPSHOT_UPDATE, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( updateLEMSnapshotModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean promoteLEMSnapshot( PromoteSnapshotModel promoteLEMSnapshotModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_SNAPSHOT_PROMOTE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( promoteLEMSnapshotModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean restoreLEMSnapshot( RestoreSnapshotModel restoreSnapshotModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_RESTORE_SNAPSHOT, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( restoreSnapshotModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public List<LEMSnapshotModel> getLEMSnapshots( String data, boolean addDelayFl ) throws FBException
	{
		List<LEMSnapshotModel> lemSnapshotModels = new ArrayList<LEMSnapshotModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			addDelay( addDelayFl );
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_SNAPSHOT_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );
			//System.out.println( responseData );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{

				JsonNode element = elements.next();
				LEMSnapshotModel lemSnapshotModel = new LEMSnapshotModel();
				lemSnapshotModel.setSnapshot_name( element.get( "name" ).asText() );
				lemSnapshotModel.setTimestamp( element.get( "timestamp" ).asLong() );
				lemSnapshotModel.setDate( ServerUtil.getFormattedDate( lemSnapshotModel.getTimestamp() ) );
				lemSnapshotModels.add( lemSnapshotModel );

			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return lemSnapshotModels;

	}

	@Override
	public Boolean deleteLemSnapshot( LEMSnapshotDeleteModel lemSnapshotDeleteModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.DELETE, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_SNAPSHOT_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( lemSnapshotDeleteModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean expandLEM( ExpandLEMModel expandLEMModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_LEM_EXPAND, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( expandLEMModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean expandVM( ExpandVMModel expandVMModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_RESIZE, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( expandVMModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean createGroup( CreateUserGroupModel creategroupModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_GROUP_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( creategroupModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean createSMTPConfiguration( UpdateSMTPConfigrationModel createSMTPModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Stats, ResourceServiceEndpoints.ALERT_SMTP_CONFIG, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( createSMTPModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;

	}

	@Override
	public Boolean deleteEmail( DeleteEmailModel deleteEmail ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Stats, ResourceServiceEndpoints.ALERT_DEL_USER, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( deleteEmail ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deleteSMTP( DeleteSMTPModel deleteSMTP ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Stats, ResourceServiceEndpoints.ALERT_DELETE_SMTP, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( deleteSMTP ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean sendTestEmail( SendTestEmailModel sendEmail ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Stats, ResourceServiceEndpoints.ALERT_SEND_EMAIL, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( sendEmail ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean sendAlertEmail( SendAlertAllUserModel sendAlert ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Stats, ResourceServiceEndpoints.ALERT_SEND_ALERT, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( sendAlert ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean addEmail( AddEmailModel addemailModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Stats, ResourceServiceEndpoints.ALERT_REG_USER, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( addemailModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean createActiveDirectory( CreateActiveDirectoryModel createActiveDirectoryModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_DOMAIN_JOIN, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( createActiveDirectoryModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean createUser( CreateNewUserModel createUserModel ) throws FBException
	{

		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_USER_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( createUserModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deleteUserGroup( UserGroupDeleteModel deleteGroup ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_GROUP_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( deleteGroup ), false );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;

	}

	@Override
	public Boolean deleteDomain( DeleteActiveDirectory deleteActiveDirectory ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_DOMAIN_LEAVE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( deleteActiveDirectory ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deleteUser( UserDeleteModel deleteUser ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_USER_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( deleteUser ), false );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean changePassword( ChangePasswordModel changepass ) throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_USER_UPDATE_USER, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( changepass ), true );

		if ( responseData != null )
		{
			success = true;
		}

		return success;
	}

	@Override
	public List<UserGroupModel> getGroupNames( String data ) throws FBException
	{
		List<UserGroupModel> userGroupModelList = new ArrayList<UserGroupModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_GROUP_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				UserGroupModel userGroupModels = new UserGroupModel( element.get( "name" ).asText(), element.get( "_id" ).get( "$oid" ).asText() );
				userGroupModels.setFilter( element.get( "filter" ).asText() );
				userGroupModels.setAuth_code( element.get( "auth_code" ).asInt() );
				userGroupModelList.add( userGroupModels );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return userGroupModelList;
	}

	@Override
	public List<SMTPConfigurationModel> getSMTPname( String data ) throws FBException
	{
		List<SMTPConfigurationModel> smtpConfigurationModels = new ArrayList<SMTPConfigurationModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Stats, ResourceServiceEndpoints.ALERT_LIST_SMTP, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				SMTPConfigurationModel smtpConfigurationModel = new SMTPConfigurationModel( element.get( "user" ).asText(), element.get( "psswd" ).asText(), element.get( "port" ).asText(), element.get( "_id" ).get( "$oid" ).asText(), element.get( "name" ).asText() );
				smtpConfigurationModels.add( smtpConfigurationModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return smtpConfigurationModels;
	}

	@Override
	public List<SMTPDestinationModel> getEmailAddress( String data ) throws FBException
	{
		List<SMTPDestinationModel> lemModels1 = new ArrayList<SMTPDestinationModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Stats, ResourceServiceEndpoints.ALERT_LIST_ALERT_USERS, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				SMTPDestinationModel lemModel = new SMTPDestinationModel( element.get( "email" ).asText(), element.get( "level" ).asInt(), element.get( "_id" ).get( "$oid" ).asText() );
				lemModels1.add( lemModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return lemModels1;
	}

	@Override
	public List<UserAccountModel> getUserNames( String data ) throws FBException
	{
		List<UserAccountModel> usermodel = new ArrayList<UserAccountModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_USER_LIST_USER, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();

			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				String token = element.get( "token" ).get( "$oid" ).asText();
				Boolean admin = element.get( "admin" ).asBoolean();
				Boolean token_expire = element.get( "token_expire" ).asBoolean();
				String id = element.get( "_id" ).get( "$oid" ).asText();
				String email = element.get( "email" ).asText();
				Integer auth = element.get( "auth" ).asInt();

				JsonNode groups = element.get( "groups" );
				Iterator<JsonNode> groupsArray = groups.elements();
				List<String> group = new ArrayList<String>();
				while ( groupsArray.hasNext() )
				{
					JsonNode ele = groupsArray.next();
					group.add( ele.asText() );
				}
				String psswd = element.get( "psswd" ).asText();
				String name = element.get( "name" ).asText();

				UserAccountModel userModels = new UserAccountModel( token, admin, token_expire, id, email, auth, group, psswd, name );
				usermodel.add( userModels );
			}

		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return usermodel;
	}

	@Override
	public List<ActiveDirectoryModel> getActiveDirectoryNames( String data ) throws FBException
	{
		List<ActiveDirectoryModel> activedirectory = new ArrayList<ActiveDirectoryModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_DOMAIN_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				ActiveDirectoryModel lemModel = new ActiveDirectoryModel( element.get( "ip" ).asText(), element.get( "ad_psswd" ).asText(), element.get( "_id" ).get( "$oid" ).asText(), element.get( "name" ).asText(), element.get( "ad_admin" ).asText() );
				activedirectory.add( lemModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return activedirectory;
	}

	@Override
	public List<String> getActiveDirectoryGroup( String data ) throws FBException
	{
		List<String> groupNames = new ArrayList<String>();
		try
		{
			if ( data == null )
			{
				//data = RestCallUtil.ALL_REQUEST_DATA;
				return groupNames; // This call does not accept query
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_DOMAIN_LIST_GROUP, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode groups = rootNode.get( "groups" );
			Iterator<JsonNode> groupsArray = groups.elements();
			while ( groupsArray.hasNext() )
			{
				JsonNode ele = groupsArray.next();
				groupNames.addAll( ServerUtil.getGroupNames( ele.asText() ) );

			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return groupNames;
	}

	@Override
	public SysInfoModel getSysInfoModel() throws FBException
	{
		SysInfoModel sysInfoModel = null;
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Stats, ResourceServiceEndpoints.STATS_SYSINFO, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( RestCallUtil.EMPTY_BODY, true );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );

			if ( rootNode != null )
			{
				String hostname = rootNode.get( "hostname" ).asText();
				String timeZone = rootNode.get( "timezone" ).asText();
				sysInfoModel = new SysInfoModel( hostname, timeZone );
				sysInfoModel.setVersion( rootNode.get( "version" ).asText() );

			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return sysInfoModel;
	}

	@Override
	public Boolean updateTimeZone( UpdateTimeZoneModel updateTimeZoneModel ) throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Stats, ResourceServiceEndpoints.SYS_TIMEZONE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( updateTimeZoneModel ), true );

		if ( responseData != null )
		{
			success = true;
		}

		return success;
	}

	@Override
	public Boolean updateHostName( UpdateHostNameModel updateHostNameModel ) throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Stats, ResourceServiceEndpoints.SYS_HOSTNAME, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( updateHostNameModel ), true );

		if ( responseData != null )
		{
			success = true;
		}

		return success;
	}

	@Override
	public List<LoginHistoryModel> getLoginHistoryModels( String data ) throws FBException
	{
		List<LoginHistoryModel> loginHistoryModels = new ArrayList<LoginHistoryModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Keystone, ResourceServiceEndpoints.LOGIN_HISTORY, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				LoginHistoryModel logHistoryModel = new LoginHistoryModel( element.get( "_id" ).get( "$oid" ).asText(), element.get( "timestamp" ).asLong(), element.get( "name" ).asText() );
				logHistoryModel.setDate( ServerUtil.getFormattedDate( logHistoryModel.getTimepoint() ) );
				loginHistoryModels.add( logHistoryModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return loginHistoryModels;
	}

	//REPO APIs - Start
	/** 
	 * to get repository configurations, from DB
	 */
	@Override
	public List<REPOModel> getConfigRepo( String requestData, Boolean addDelayFl ) throws FBException
	{
		ArrayList<REPOModel> repoModels = new ArrayList<REPOModel>();
		try
		{
			addDelay( addDelayFl );

			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_GET_CONFIG, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( requestData, true );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );

			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "records" );
			REPOMainModel[] readValue = objectMapper.readValue( resNode.toString(), REPOMainModel[].class );
			for ( REPOMainModel repoMainModel : readValue )
			{
				repoMainModel.setMountinfo( discoverConfig( "{\"id\":\"" + repoMainModel.get_id() + "\"}" ) );
				List<REPOModel> repoModelTemp = CommonUtil.getREPOModel( repoMainModel );
				for ( REPOModel repoModel : repoModelTemp )
				{
					repoModels.add( repoModel );
				}
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return repoModels;
	}

	/**
	 * to get REPO configurations directly from machine, get data not updated in DB as well.
	 * 
	 * @param requestJSON
	 * @return
	 * @throws FBException
	 */
	@Override
	public REPOMountInfoModel[] discoverConfig( String requestJSON ) throws FBException
	{
		REPOMountInfoModel[] readValue;
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_DISCOVER_CONFIG, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( requestJSON, true );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );

			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "available" );
			readValue = objectMapper.readValue( resNode.toString(), REPOMountInfoModel[].class );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return readValue;
	}

	/**
	 * To remove repo configured
	 * @param configId
	 * @return success or failure in boolean
	 */
	@Override
	public Boolean removeConfig( String configId ) throws FBException
	{
		Boolean success = false;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.DELETE, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_REMOVE_CONFIG, ContentType.application_json );
		String responseData = remoteService.deleteRequest( "{\"id\":\"" + configId + "\"}", true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean addConfigLocal( REPOLocalCreateModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_ADD_CONFIG, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean updateConfigLocal( REPOLocalEditModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_UPDATE_CONFIG, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public REPOLocalCreateModel getConfigLocalRepoModel( String requestData ) throws FBException
	{
		REPOLocalCreateModel[] repoMainModels = null;
		try
		{
			String responseData = getConfigRepoModel( requestData );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );

			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "records" );
			repoMainModels = objectMapper.readValue( resNode.toString(), REPOLocalCreateModel[].class );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return repoMainModels != null ? repoMainModels[0] : null;
	}

	private String getConfigRepoModel( String requestData ) throws FBException
	{
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_GET_CONFIG, ContentType.application_json );
		String responseData = remoteService.getWithRequestData( requestData, true );
		return responseData;
	}

	@Override
	public Boolean addConfigNFS( REPONFSCreateModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_ADD_CONFIG, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean updateConfigNFS( REPONFSEditModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_UPDATE_CONFIG, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public REPONFSCreateModel getConfigNFSRepoModel( String requestData ) throws FBException
	{
		REPONFSCreateModel[] repoMainModels = null;
		try
		{
			String responseData = getConfigRepoModel( requestData );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );

			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "records" );
			repoMainModels = objectMapper.readValue( resNode.toString(), REPONFSCreateModel[].class );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return repoMainModels != null ? repoMainModels[0] : null;
	}

	@Override
	public Boolean addConfigCIFS( REPOCIFSCreateModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_ADD_CONFIG, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean updateConfigCIFS( REPOCIFSEditModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_UPDATE_CONFIG, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public REPOCIFSCreateModel getConfigCIFSRepoModel( String requestData ) throws FBException
	{
		REPOCIFSCreateModel[] repoMainModels = null;
		try
		{
			String responseData = getConfigRepoModel( requestData );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );

			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "records" );
			repoMainModels = objectMapper.readValue( resNode.toString(), REPOCIFSCreateModel[].class );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return repoMainModels != null ? repoMainModels[0] : null;
	}

	@Override
	public List<String> discoverIscsiConfig( String ipAddress ) throws FBException
	{
		List<String> iqnList = new ArrayList<String>();
		String initiator = getIscsiInitiator();

		String requestData = "{\"ip\":\"" + ipAddress + "\", \"iqn\": \"" + initiator + "\"}";

		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_DISCOVER_ISCSITARGETS, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( requestData, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );

			if ( rootNode.get( "ret" ) != null )
			{
				boolean success = rootNode.get( "ret" ).asBoolean();
				if ( !success )
				{
					String message = rootNode.get( "message" ).asText();
					throw new FBException( message, ErrorCodes.FAIL_RESPONSE );
				}
			}

			Iterator<JsonNode> initiatorArray = rootNode.elements();
			while ( initiatorArray.hasNext() )
			{
				JsonNode ele = initiatorArray.next();
				iqnList.add( ele.asText() );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return iqnList;
	}

	public String getIscsiInitiator() throws FBException
	{
		List<String> iqn = new ArrayList<String>();
		try
		{

			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_GET_INITIATOR, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( "{}", true );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode initiator = rootNode.get( "initiator" );
			Iterator<JsonNode> initiatorArray = initiator.elements();
			while ( initiatorArray.hasNext() )
			{
				JsonNode ele = initiatorArray.next();
				iqn.add( ele.asText() );
			}

		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return ( iqn != null && !iqn.isEmpty() ) ? iqn.get( 0 ) : null;
	}

	@Override
	public REPOISCSICreateModel getConfigISCSIRepoModel( String requestData ) throws FBException
	{
		REPOISCSICreateModel[] repoMainModels = null;
		try
		{
			String responseData = getConfigRepoModel( requestData );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );

			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "records" );
			repoMainModels = objectMapper.readValue( resNode.toString(), REPOISCSICreateModel[].class );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return repoMainModels != null ? repoMainModels[0] : null;
	}

	@Override
	public Boolean addConfigISCSI( REPOISCSICreateModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_ADD_CONFIG, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean updateConfigISCSI( REPOISCSIEditModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_UPDATE_CONFIG, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean mountConfig( REPOMountAllModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_MOUNT_CONFIG, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean unmountConfig( REPOUnMountAllModel repoMainModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_UNMOUNT_CONFIG, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( repoMainModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean makeFs( String requestData ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Repo, ResourceServiceEndpoints.REPOMANAGEMENT_MAKEFS, ContentType.application_json );
		String responseData = remoteService.postRequest( requestData, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	//REPO APIs - End
	// This call is used to populate the networking data table.
	// The NetworkingModel has only the name and device type
	// This API cant be used to get specific device properties
	@Override
	public List<NetworkingModel> getNetworkingDevice( List<NetworkDeviceType> deviceTypes ) throws FBException
	{
		String request = null;
		List<NetworkingModel> networkingModels = new ArrayList<NetworkingModel>();
		try
		{
			for ( NetworkDeviceType deviceType : deviceTypes )
			{
				if ( deviceType.equals( NetworkDeviceType.BRIDGE ) )
				{
					request = RestCallUtil.BRIDGES_FILTER_ALL;
				}
				else if ( deviceType.equals( NetworkDeviceType.ETHERNET ) )
				{
					request = RestCallUtil.ETHERNETS_FILTER_ALL;
				}
				getNetworkingDeviceModels( deviceType, request, networkingModels );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return networkingModels;
	}

	private void getNetworkingDeviceModels( NetworkDeviceType deviceType, String request, List<NetworkingModel> networkingModels ) throws FBException, IOException, JsonProcessingException
	{
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_DEVICE_LIST, ContentType.application_json );
		String responseData = remoteService.getWithRequestData( request, true );

		byte[] jsonData = responseData.getBytes();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree( jsonData );
		JsonNode resNode = rootNode.path( "res" );
		Iterator<JsonNode> elements = resNode.elements();
		while ( elements.hasNext() )
		{
			JsonNode element = elements.next();
			List<String> interfaces = new ArrayList<String>();
			if ( deviceType.equals( NetworkDeviceType.BRIDGE ) )
			{
				JsonNode interfacesNode = element.get( "interfaces" );
				Iterator<JsonNode> iterfaceItr = interfacesNode.elements();
				while ( iterfaceItr.hasNext() )
				{
					JsonNode ele = ( JsonNode ) iterfaceItr.next();
					interfaces.add( ele.asText() );
				}
			}
			NetworkingModel networkingModel = new NetworkingModel( element.get( "name" ).asText(), deviceType, element.get( "_id" ).get( "$oid" ).asText(), interfaces );
			networkingModels.add( networkingModel );
		}
	}

	public CreateNetworkModel getNetworkDeviceCommonProperties( String deviceName, NetworkDeviceType deviceType ) throws FBException
	{
		String request = null;
		CreateNetworkModel networkModel = new CreateNetworkModel();
		try
		{
			request = RestCallUtil.getNetworkDeviceCommonProperties( deviceName );

			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_DEVICE_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( request, true );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();

				List<String> addressesList = new ArrayList<>();

				List<String> dnsSearchList = new ArrayList<>();

				List<String> dnsAddressesList = new ArrayList<>();
				Iterator<JsonNode> addressesNode = element.get( "addresses" ).elements();
				Iterator<JsonNode> dnsSearchNode = element.get( "dns_search" ).elements();
				Iterator<JsonNode> dnsAddressesNode = element.get( "dns_addresses" ).elements();

				while ( addressesNode.hasNext() )
				{
					JsonNode address = addressesNode.next();

					addressesList.add( address.asText() );
				}
				while ( dnsSearchNode.hasNext() )
				{
					JsonNode dnsSearch = dnsSearchNode.next();

					dnsSearchList.add( dnsSearch.asText() );
				}
				while ( dnsAddressesNode.hasNext() )
				{
					JsonNode dnsAddress = dnsAddressesNode.next();

					dnsAddressesList.add( dnsAddress.asText() );
				}
				networkModel = new CreateNetworkModel( element.get( "name" ).asText(), element.get( "dhcp6" ).asBoolean(), element.get( "dhcp4" ).asBoolean(), addressesList, element.get( "gateway4" ).asText(), element.get( "gateway6" ).asText(), dnsSearchList, dnsAddressesList, deviceType );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return networkModel;

	}

	@Override
	public List<InterfaceModel> getPhysicalDeviceList( String data ) throws FBException
	{
		List<InterfaceModel> physicalDeviceList = new ArrayList<InterfaceModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.EMPTY_BODY;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORD_DEVICE_LIST_PHYSICAL_DEVICE, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, true );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				physicalDeviceList.add( new InterfaceModel( element.asText(), UUID.randomUUID().toString(), "Interface" ) );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return physicalDeviceList;
	}

	@Override
	public Boolean createBridge( CreateBridgeModelJSON createBridgeModelJSON ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_DEVICE_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( createBridgeModelJSON ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean createEthernet( CreateEthernetModelJSON createEthernetModelJSON ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_DEVICE_CREATE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( createEthernetModelJSON ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean updateNetworkDevice( CreateNetworkModel networkModel ) throws FBException
	{
		Boolean success = null;
		String request = null;
		if ( networkModel.getType().equals( NetworkDeviceType.BRIDGE ) )
		{
			request = RestCallUtil.updateNetworkBridgeProperties( ServerUtil.getJSONStringOfObject( networkModel ) );
		}
		else if ( networkModel.getType().equals( NetworkDeviceType.ETHERNET ) )
		{
			request = RestCallUtil.updateNetworkEthernetProperties( ServerUtil.getJSONStringOfObject( networkModel ) );
		}

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_DEVICE_UPDATE, ContentType.application_json );
		String responseData = remoteService.putRequest( request, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	//@Override
	public Boolean tryApplyNetwork() throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_OP_TRY_APPLY, ContentType.application_json );
		String responseData = remoteService.putRequest( RestCallUtil.EMPTY_BODY, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean applyNetwork() throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_OP_APPLY, ContentType.application_json );
		String responseData = remoteService.putRequest( RestCallUtil.EMPTY_BODY, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	public Boolean networkBackup() throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_OP_BACKUP, ContentType.application_json );
		String responseData = remoteService.putRequest( RestCallUtil.EMPTY_BODY, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	public Boolean networkRestore() throws FBException
	{
		Boolean success = false;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_OP_RESTORE, ContentType.application_json );
		String responseData = remoteService.putRequest( RestCallUtil.EMPTY_BODY, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deleteNetworkingDevice( NetworkDeviceType deviceType, String deviceName ) throws FBException
	{
		Boolean success = false;
		String requestJson = null;
		if ( deviceType.equals( NetworkDeviceType.BRIDGE ) )
		{
			requestJson = RestCallUtil.getDelBridgeRequest( deviceName );
		}
		else if ( deviceType.equals( NetworkDeviceType.ETHERNET ) )
		{
			requestJson = RestCallUtil.getDelEthernetRequest( deviceName );
		}
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.DELETE, ServiceTypes.Networks, ResourceServiceEndpoints.NETWORKD_DEVICE_DELETE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( requestJson, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;

	}

	@Override
	public List<String> getManagementPartitions( String data ) throws FBException
	{
		List<String> partitions = new ArrayList<String>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Management, ResourceServiceEndpoints.MANAGEMENT_CONF_LIST_PARTITION, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );
			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{

				JsonNode element = elements.next();
				partitions.add( element.get( "mount_point" ).asText() );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return partitions;
	}

	@Override
	public DiagSystemStateModel getDiagSystemStateModel( String data ) throws FBException
	{
		DiagSystemStateModel diagSystemStateModel = new DiagSystemStateModel();
		List<DiagNVMEModel> diagNVMEModelList = new ArrayList<DiagNVMEModel>();
		DiagSystemHealthModel diagSystemHealthModel = new DiagSystemHealthModel();
		diagSystemStateModel.setSystemHealthModel( diagSystemHealthModel );
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.EMPTY_BODY;
			}
			diagSystemStateModel.getSystemHealthModel().setSysInfoModel( getSysInfoModel() );
			diagSystemStateModel.getSystemHealthModel().setSystemStatusChartModel( getSystemStatusChartData() );
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Config, ResourceServiceEndpoints.STATS_CFTM_STATE, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, true );
			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "forsaos_system_state" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				if ( element.get( "bmc" ) != null )
				{
					JsonNode jsonNode = element.get( "bmc" );
					setDiagBMCData( diagSystemStateModel.getSystemHealthModel(), jsonNode );
				}
				if ( element.get( "nvme" ) != null )
				{
					Iterator<JsonNode> jsonNodes = element.get( "nvme" ).elements();
					System.out.println( "get nvme data" );
					while ( jsonNodes.hasNext() )
					{
						JsonNode nvmeNode = jsonNodes.next();
						DiagNVMEModel diagNVMEModel = new DiagNVMEModel();
						if ( nvmeNode.get( "info" ) != null )
						{
							JsonNode nvmeInfoNode = nvmeNode.get( "info" );
							DiagNVMEInfoModel diagNVMEInfoModel = getDiagNVMEInfoModel( nvmeInfoNode );
							diagNVMEModel.setNvmeInfoModel( diagNVMEInfoModel );

						}
						if ( nvmeNode.get( "smart-log" ) != null )
						{
							JsonNode nvmeSmartLogNode = nvmeNode.get( "smart-log" );

							DiagNVMESmartLogModel diagNVMESmartLogModel = getDiagNVMESmartLogModel( nvmeSmartLogNode );
							diagNVMEModel.setNvmeSmartLogModel( diagNVMESmartLogModel );
						}
						if ( nvmeNode.get( "id-ctrl" ) != null )
						{
							JsonNode nvmeIdCntrlNode = nvmeNode.get( "id-ctrl" );
							DiagNVMEIdControlModel diagNVMEIdControlModel = getDiagNVMEIdControlModel( nvmeIdCntrlNode );
							diagNVMEModel.setNvmeIdControlModel( diagNVMEIdControlModel );
						}
						diagNVMEModelList.add( diagNVMEModel );
					}

				}
				if ( element.get( "network" ) != null )
				{
					JsonNode networkNode = element.get( "network" );
					DiagNetworkModel diagNetworkModel = objectMapper.readValue( networkNode.toString(), DiagNetworkModel.class );
					diagSystemStateModel.setDiagNetworkModel( diagNetworkModel );
				}
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		diagSystemStateModel.setNvmeList( diagNVMEModelList );
		return diagSystemStateModel;

	}

	private void setDiagBMCData( DiagSystemHealthModel diagSystemHealthModel, JsonNode jsonNode )
	{
		System.out.println( "get bmc data" );
		if ( jsonNode.get( "CPU1_temperature_current" ) != null )
		{
			diagSystemHealthModel.setCPU1_temperature_current( jsonNode.get( "CPU1_temperature_current" ).asText() );
		}
		if ( jsonNode.get( "CPU1_temperature_max" ) != null )
		{
			diagSystemHealthModel.setCPU1_temperature_max( jsonNode.get( "CPU1_temperature_max" ).asText() );
		}
		if ( jsonNode.get( "CPU2_temperature_current" ) != null )
		{
			diagSystemHealthModel.setCPU2_temperature_current( jsonNode.get( "CPU2_temperature_current" ).asText() );
		}
		if ( jsonNode.get( "CPU2_temperature_max" ) != null )
		{
			diagSystemHealthModel.setCPU2_temperature_max( jsonNode.get( "CPU2_temperature_max" ).asText() );
		}
		if ( jsonNode.get( "Fans_fan1_speed" ) != null )
		{
			diagSystemHealthModel.setFans_fan1_speed( String.valueOf( jsonNode.get( "Fans_fan1_speed" ).asInt() ) );
		}
		if ( jsonNode.get( "Fans_fan2_speed" ) != null )
		{
			diagSystemHealthModel.setFans_fan2_speed( String.valueOf( jsonNode.get( "Fans_fan2_speed" ).asInt() ) );
		}
		if ( jsonNode.get( "Fans_fan3_speed" ) != null )
		{
			diagSystemHealthModel.setFans_fan3_speed( String.valueOf( jsonNode.get( "Fans_fan3_speed" ).asInt() ) );
		}
		if ( jsonNode.get( "Fans_fan4_speed" ) != null )
		{
			diagSystemHealthModel.setFans_fan4_speed( String.valueOf( jsonNode.get( "Fans_fan4_speed" ).asInt() ) );
		}
		if ( jsonNode.get( "Fans_fan5_speed" ) != null )
		{
			diagSystemHealthModel.setFans_fan5_speed( String.valueOf( jsonNode.get( "Fans_fan5_speed" ).asInt() ) );
		}
		if ( jsonNode.get( "Fans_fan6_speed" ) != null )
		{
			diagSystemHealthModel.setFans_fan6_speed( String.valueOf( jsonNode.get( "Fans_fan6_speed" ).asInt() ) );
		}
		if ( jsonNode.get( "Fans_fan7_speed" ) != null )
		{
			diagSystemHealthModel.setFans_fan7_speed( String.valueOf( jsonNode.get( "Fans_fan7_speed" ).asInt() ) );
		}
		if ( jsonNode.get( "Fans_fan8_speed" ) != null )
		{
			diagSystemHealthModel.setFans_fan8_speed( String.valueOf( jsonNode.get( "Fans_fan8_speed" ).asInt() ) );
		}
		if ( jsonNode.get( "Memory_temperature_current" ) != null )
		{
			diagSystemHealthModel.setMemory_temperature_current( jsonNode.get( "Memory_temperature_current" ).asText() );
		}
		if ( jsonNode.get( "PowerSupply_fan_speed_minimal" ) != null )
		{
			diagSystemHealthModel.setPowerSupply_fan_speed_minimal( jsonNode.get( "PowerSupply_fan_speed_minimal" ).asText() );
		}
		if ( jsonNode.get( "PowerSupply_temperature_maximal" ) != null )
		{
			diagSystemHealthModel.setPowerSupply_temperature_maximal( jsonNode.get( "PowerSupply_temperature_maximal" ).asText() );
		}
		if ( jsonNode.get( "Voltage_12V" ) != null )
		{
			diagSystemHealthModel.setVoltage_12V( jsonNode.get( "Voltage_12V" ).asText() );
		}
		if ( jsonNode.get( "Voltage_3_3V" ) != null )
		{
			diagSystemHealthModel.setVoltage_3_3V( jsonNode.get( "Voltage_3_3V" ).asText() );
		}
		if ( jsonNode.get( "Voltage_5V" ) != null )
		{
			diagSystemHealthModel.setVoltage_5V( jsonNode.get( "Voltage_5V" ).asText() );
		}
	}

	private DiagNVMEInfoModel getDiagNVMEInfoModel( JsonNode nvmeInfoNode )
	{
		DiagNVMEInfoModel diagNVMEInfoModel = new DiagNVMEInfoModel();
		if ( nvmeInfoNode.get( "UsedBytes" ) != null )
		{
			diagNVMEInfoModel.setUsedBytes( nvmeInfoNode.get( "UsedBytes" ).asLong() );
		}
		if ( nvmeInfoNode.get( "SectorSize" ) != null )
		{
			diagNVMEInfoModel.setSectorSize( nvmeInfoNode.get( "SectorSize" ).asLong() );
		}
		if ( nvmeInfoNode.get( "ProductName" ) != null )
		{
			diagNVMEInfoModel.setProductName( nvmeInfoNode.get( "ProductName" ).asText() );
		}
		if ( nvmeInfoNode.get( "Firmware" ) != null )
		{
			diagNVMEInfoModel.setFirmware( nvmeInfoNode.get( "Firmware" ).asText() );
		}
		if ( nvmeInfoNode.get( "SerialNumber" ) != null )
		{
			diagNVMEInfoModel.setSerialNumber( nvmeInfoNode.get( "SerialNumber" ).asText() );
		}
		if ( nvmeInfoNode.get( "DevicePath" ) != null )
		{
			diagNVMEInfoModel.setDevicePath( nvmeInfoNode.get( "DevicePath" ).asText() );
		}
		if ( nvmeInfoNode.get( "ModelNumber" ) != null )
		{
			diagNVMEInfoModel.setModelNumber( nvmeInfoNode.get( "ModelNumber" ).asText() );
		}
		if ( nvmeInfoNode.get( "PhysicalSize" ) != null )
		{
			diagNVMEInfoModel.setPhysicalSize( nvmeInfoNode.get( "PhysicalSize" ).asLong() );
		}
		if ( nvmeInfoNode.get( "Index" ) != null )
		{
			diagNVMEInfoModel.setIndex( nvmeInfoNode.get( "Index" ).asInt() );
		}
		if ( nvmeInfoNode.get( "MaximiumLBA" ) != null )
		{
			diagNVMEInfoModel.setMaximumLBA( nvmeInfoNode.get( "MaximiumLBA" ).asLong() );
		}
		return diagNVMEInfoModel;
	}

	private DiagNVMESmartLogModel getDiagNVMESmartLogModel( JsonNode nvmeSmartLogNode )
	{
		DiagNVMESmartLogModel diagNVMESmartLogModel = new DiagNVMESmartLogModel();
		if ( nvmeSmartLogNode.get( "data_units_read" ) != null )
		{
			diagNVMESmartLogModel.setData_units_read( nvmeSmartLogNode.get( "data_units_read" ).asLong() );
		}
		if ( nvmeSmartLogNode.get( "data_units_written" ) != null )
		{
			diagNVMESmartLogModel.setData_units_written( nvmeSmartLogNode.get( "data_units_written" ).asLong() );
		}
		if ( nvmeSmartLogNode.get( "percent_used" ) != null )
		{
			diagNVMESmartLogModel.setPercent_used( nvmeSmartLogNode.get( "percent_used" ).asInt() );
		}
		if ( nvmeSmartLogNode.get( "temperature" ) != null )
		{
			diagNVMESmartLogModel.setTemperature( nvmeSmartLogNode.get( "temperature" ).asInt() );
		}
		if ( nvmeSmartLogNode.get( "media_errors" ) != null )
		{
			diagNVMESmartLogModel.setMedia_errors( nvmeSmartLogNode.get( "media_errors" ).asInt() );
		}
		if ( nvmeSmartLogNode.get( "power_on_hours" ) != null )
		{
			diagNVMESmartLogModel.setPower_on_hours( nvmeSmartLogNode.get( "power_on_hours" ).asInt() );
		}

		return diagNVMESmartLogModel;
	}

	private DiagNVMEIdControlModel getDiagNVMEIdControlModel( JsonNode nvmeIdCntrlNode )
	{
		DiagNVMEIdControlModel diagNVMEIdControlModel = new DiagNVMEIdControlModel();

		if ( nvmeIdCntrlNode.get( "vid" ) != null )
		{
			diagNVMEIdControlModel.setVid( nvmeIdCntrlNode.get( "vid" ).asInt() );
		}

		if ( nvmeIdCntrlNode.get( "ssvid" ) != null )
		{
			diagNVMEIdControlModel.setSsvid( nvmeIdCntrlNode.get( "ssvid" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "ieee" ) != null )
		{
			diagNVMEIdControlModel.setIeee( nvmeIdCntrlNode.get( "ieee" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "mdts" ) != null )
		{
			diagNVMEIdControlModel.setMdts( nvmeIdCntrlNode.get( "mdts" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "cntlid" ) != null )
		{
			diagNVMEIdControlModel.setCntlid( nvmeIdCntrlNode.get( "cntlid" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "ver" ) != null )
		{
			diagNVMEIdControlModel.setVer( nvmeIdCntrlNode.get( "ver" ).asLong() );
		}
		if ( nvmeIdCntrlNode.get( "wctemp" ) != null )
		{
			diagNVMEIdControlModel.setWctemp( nvmeIdCntrlNode.get( "wctemp" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "cctemp" ) != null )
		{
			diagNVMEIdControlModel.setCctemp( nvmeIdCntrlNode.get( "cctemp" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "tnvmcap" ) != null )
		{
			diagNVMEIdControlModel.setTnvmcap( nvmeIdCntrlNode.get( "tnvmcap" ).asLong() );
		}
		if ( nvmeIdCntrlNode.get( "sqes" ) != null )
		{
			diagNVMEIdControlModel.setSqes( nvmeIdCntrlNode.get( "sqes" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "cqes" ) != null )
		{
			diagNVMEIdControlModel.setCqes( nvmeIdCntrlNode.get( "cqes" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "vwc" ) != null )
		{
			diagNVMEIdControlModel.setVwc( nvmeIdCntrlNode.get( "vwc" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "nvscc" ) != null )
		{
			diagNVMEIdControlModel.setNvscc( nvmeIdCntrlNode.get( "nvscc" ).asInt() );
		}
		if ( nvmeIdCntrlNode.get( "frmw" ) != null )
		{
			diagNVMEIdControlModel.setFrmw( nvmeIdCntrlNode.get( "frmw" ).asInt() );
		}
		return diagNVMEIdControlModel;
	}

	@Override
	public List<UPSModel> getUPS( String data ) throws FBException
	{
		List<UPSModel> upsModelList = new ArrayList<UPSModel>();
		try
		{
			if ( data == null )
			{
				data = RestCallUtil.ALL_REQUEST_DATA;
			}
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.controller, ResourceServiceEndpoints.UPS_LIST, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );
			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			Iterator<JsonNode> elements = resNode.elements();
			while ( elements.hasNext() )
			{
				JsonNode element = elements.next();
				UPSModel upsModel = objectMapper.readValue( element.toString(), UPSModel.class );
				upsModelList.add( upsModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return upsModelList;
	}

	@Override
	public UPSDetailsModel getUPSDetails( String data ) throws FBException
	{
		UPSDetailsModel upsDetailsModel = null;
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.controller, ResourceServiceEndpoints.UPS_GET_STATUS, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( data, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );
			JsonNode rootNode = objectMapper.readTree( jsonData );

			if ( rootNode != null )
			{
				upsDetailsModel = objectMapper.readValue( rootNode.toString(), UPSDetailsModel.class );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return upsDetailsModel;
	}

	@Override
	public Boolean deRegisterUPS( DeregisterUPSModel deleteUps ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.controller, ResourceServiceEndpoints.UPS_UNREGISTER, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( deleteUps ), false );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean registerUPS( RegisterUPSModel registerUps ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.controller, ResourceServiceEndpoints.UPS_REGISTER, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( registerUps ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public List<String> sysBootTime() throws FBException
	{
		List<String> responseData = new ArrayList<String>();
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Stats, ResourceServiceEndpoints.STATS_SYS_BOOT_TIME, ContentType.application_json );
		String response = remoteService.getWithRequestData( "{}", false );
		byte[] jsonData = response.getBytes();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode;
		try
		{
			rootNode = objectMapper.readTree( jsonData );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		String upTime = rootNode.get( "uptime" ).asText();

		Date currentDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

		responseData.add( upTime );
		responseData.add( dateFormat.format( currentDate ) );

		return responseData;
	}

	@Override
	public List<EventLogResponseModel> getEventLog( EventLogRequestModel eventLogRequestModel ) throws FBException
	{
		List<EventLogResponseModel> eventLogResponseModels = new ArrayList<EventLogResponseModel>();
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Stats, ResourceServiceEndpoints.LOG_GET, ContentType.application_json );
			String response = remoteService.getWithRequestData( ServerUtil.getJSONStringOfObject( eventLogRequestModel ), false );
			byte[] jsonData = response.getBytes();

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true );
			JsonNode rootNode = objectMapper.readTree( jsonData );

			boolean success = rootNode.get( "ret" ).asBoolean();
			if ( !success )
			{
				String message = rootNode.get( "message" ).asText();
				throw new FBException( message, ErrorCodes.FAIL_RESPONSE );
			}

			JsonNode logNode = rootNode.get( "logs" );
			Iterator<JsonNode> initiatorArray = logNode.elements();
			while ( initiatorArray.hasNext() )
			{
				JsonNode ele = initiatorArray.next();
				EventLogResponseModel eventLogResponseModel = ServerUtil.tokenizeEventLogToModel( ele.asText() );
				eventLogResponseModels.add( eventLogResponseModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			throw e;
		}

		return eventLogResponseModels;
	}

	@Override
	public Boolean startVMMonitoring( String data ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_START_MON, ContentType.application_json );
		String responseData = remoteService.getWithRequestData( data, true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean stopVMMonitoring() throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_VM_STOP_MON, ContentType.application_json );
		String responseData = remoteService.getWithRequestData( RestCallUtil.EMPTY_BODY, true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public List<PartitionModel> listPartition( Boolean addDelayFl ) throws FBException
	{
		ArrayList<PartitionModel> partitionModels = new ArrayList<PartitionModel>();
		try
		{
			addDelay( addDelayFl );

			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Management, ResourceServiceEndpoints.MANAGEMENT_CONF_LIST_PARTITION, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( RestCallUtil.ALL_REQUEST_DATA, true );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );

			JsonNode rootNode = objectMapper.readTree( jsonData );
			JsonNode resNode = rootNode.path( "res" );
			PartitionModel[] readValue = objectMapper.readValue( resNode.toString(), PartitionModel[].class );
			for ( PartitionModel partitionModel : readValue )
			{
				partitionModels.add( partitionModel );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}
		return partitionModels;
	}

	@Override
	public Boolean createPartition( PartitionCreateModel partitionCreateModel, PartitionType partitionType ) throws FBException
	{
		Boolean success = null;
		ResourceServiceEndpoints resourceServiceEndpoint = null;
		if ( partitionType.equals( PartitionType.BLINK ) )
		{
			resourceServiceEndpoint = ResourceServiceEndpoints.MANAGEMENT_CONF_ADD_BLINK_PARTITION;
		}
		else
		{
			resourceServiceEndpoint = ResourceServiceEndpoints.MANAGEMENT_CONF_ADD_SNAPSHOT_PARTITION;
		}

//		System.out.println( partitionType.toString() );
//		System.out.println( resourceServiceEndpoint.toString() );

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Management, resourceServiceEndpoint, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( partitionCreateModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deletePartition( PartitionDeleteModel partitionDeleteModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.DELETE, ServiceTypes.Management, ResourceServiceEndpoints.MANAGEMENT_CONF_DELETE_BLINK_PARTITION, ContentType.application_json );
		String responseData = remoteService.deleteRequest( ServerUtil.getJSONStringOfObject( partitionDeleteModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}
	

	@Override
	public Boolean expandPartition( PartitionExpandModel partitionExpandModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Management, ResourceServiceEndpoints.MANAGEMENT_CONF_EXPAND_PARTITION, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( partitionExpandModel ), true );
		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public LicenseDetailsModel getLicense() throws FBException
	{
		LicenseDetailsModel license = null;
		try
		{
			RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.GET, ServiceTypes.Management, ResourceServiceEndpoints.LISCENSE_DETAILS, ContentType.application_json );
			String responseData = remoteService.getWithRequestData( RestCallUtil.EMPTY_BODY, false );

			byte[] jsonData = responseData.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false );
			JsonNode rootNode = objectMapper.readTree( jsonData );

			if ( rootNode != null )
			{

				license = objectMapper.readValue( rootNode.toString(), LicenseDetailsModel.class );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			throw new FBException( e.getMessage(), ErrorCodes.GENERIC_ERROR );
		}
		catch ( FBException e )
		{
			e.printStackTrace();
			throw e;
		}

		return license;
	}

	@Override
	public Boolean registerLicense( RegisterLicenseModel registerLicenseModel ) throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.POST, ServiceTypes.Management, ResourceServiceEndpoints.MANG_REGISTER_LICENSE, ContentType.application_json );
		String responseData = remoteService.postRequest( ServerUtil.getJSONStringOfObject( registerLicenseModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean deleteLicense() throws FBException
	{
		Boolean success = null;

		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.DELETE, ServiceTypes.Management, ResourceServiceEndpoints.MANG_DELETE_LICENSE, ContentType.application_json );
		String responseData = remoteService.deleteRequest( RestCallUtil.EMPTY_BODY, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean extendExpiry( ExtendExpiryModel expiryModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_USER_EXTEND_TOKEN, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( expiryModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean refreshUserGroup( String requestData ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_GROUP_REFRESH, ContentType.application_json );
		String responseData = remoteService.putRequest( requestData, true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean editUserGroupModel( EditUserGroupModel editUserGroupModel ) throws FBException
	{
		Boolean success = null;
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Keystone, ResourceServiceEndpoints.KEYSTONE_GROUP_UPDATE, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( editUserGroupModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

	@Override
	public Boolean updateRTMName(UpdateRTMModel updateRTMModel) throws FBException {
		Boolean success = false;
		//VMUpdateNameModel vmUpdateNameModel = new VMUpdateNameModel( vmModel.getName(), vmModel.get_id().get$oid(), new_name );
		RemoteService remoteService = new RemoteServiceImpl( getUserSession(), getCurrentUserObject().getWindowLocation(), RequestMethod.PUT, ServiceTypes.Virtualization, ResourceServiceEndpoints.VIRT_RTM_UPDATE, ContentType.application_json );
		String responseData = remoteService.putRequest( ServerUtil.getJSONStringOfObject( updateRTMModel ), true );

		if ( responseData != null && !responseData.isEmpty() )
		{
			success = true;
		}
		return success;
	}

}