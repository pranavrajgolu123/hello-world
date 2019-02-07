package com.fmlb.forsaos.client.application.common;

import java.util.List;
import java.util.Map;

import com.fmlb.forsaos.client.application.models.ActiveDirectoryModel;
import com.fmlb.forsaos.client.application.models.AddEmailModel;
import com.fmlb.forsaos.client.application.models.BlinkModel;
import com.fmlb.forsaos.client.application.models.BridgeJSONModel;
import com.fmlb.forsaos.client.application.models.CapacityChartModel;
import com.fmlb.forsaos.client.application.models.ChangePasswordModel;
import com.fmlb.forsaos.client.application.models.CloneLEMModel;
import com.fmlb.forsaos.client.application.models.CloneLEMandAssisgnToVMmodel;
import com.fmlb.forsaos.client.application.models.CloneVMModel;
import com.fmlb.forsaos.client.application.models.CreateActiveDirectoryModel;
import com.fmlb.forsaos.client.application.models.CreateBlinkModel;
import com.fmlb.forsaos.client.application.models.CreateBridgeModelJSON;
import com.fmlb.forsaos.client.application.models.CreateEthernetModelJSON;
import com.fmlb.forsaos.client.application.models.CreateLEMandAssisgnToVMmodel;
import com.fmlb.forsaos.client.application.models.CreateMultiLEMModel;
import com.fmlb.forsaos.client.application.models.CreateNetworkModel;
import com.fmlb.forsaos.client.application.models.CreateNewUserModel;
import com.fmlb.forsaos.client.application.models.CreateUserGroupModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.DeleteActiveDirectory;
import com.fmlb.forsaos.client.application.models.DeleteBlinkModel;
import com.fmlb.forsaos.client.application.models.DeleteEmailModel;
import com.fmlb.forsaos.client.application.models.DeleteSMTPModel;
import com.fmlb.forsaos.client.application.models.DeregisterUPSModel;
import com.fmlb.forsaos.client.application.models.DiagSystemStateModel;
import com.fmlb.forsaos.client.application.models.EditUserGroupModel;
import com.fmlb.forsaos.client.application.models.EventLogRequestModel;
import com.fmlb.forsaos.client.application.models.EventLogResponseModel;
import com.fmlb.forsaos.client.application.models.ExpandLEMModel;
import com.fmlb.forsaos.client.application.models.ExpandVMModel;
import com.fmlb.forsaos.client.application.models.ExtendExpiryModel;
import com.fmlb.forsaos.client.application.models.InterfaceModel;
import com.fmlb.forsaos.client.application.models.LEMDeleteJSONModel;
import com.fmlb.forsaos.client.application.models.LEMJSONModel;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.LEMSnapshotDeleteModel;
import com.fmlb.forsaos.client.application.models.LEMSnapshotModel;
import com.fmlb.forsaos.client.application.models.LVNetCreateRequestModel;
import com.fmlb.forsaos.client.application.models.LicenseDetailsModel;
import com.fmlb.forsaos.client.application.models.LoginHistoryModel;
import com.fmlb.forsaos.client.application.models.NameModel;
import com.fmlb.forsaos.client.application.models.NetworkingModel;
import com.fmlb.forsaos.client.application.models.NicModel;
import com.fmlb.forsaos.client.application.models.PartitionCreateModel;
import com.fmlb.forsaos.client.application.models.PartitionDeleteModel;
import com.fmlb.forsaos.client.application.models.PartitionExpandModel;
import com.fmlb.forsaos.client.application.models.PartitionModel;
import com.fmlb.forsaos.client.application.models.ProgressModel;
import com.fmlb.forsaos.client.application.models.PromoteSnapshotModel;
import com.fmlb.forsaos.client.application.models.REPOCIFSCreateModel;
import com.fmlb.forsaos.client.application.models.REPOCIFSEditModel;
import com.fmlb.forsaos.client.application.models.REPOISCSICreateModel;
import com.fmlb.forsaos.client.application.models.REPOISCSIEditModel;
import com.fmlb.forsaos.client.application.models.REPOLocalCreateModel;
import com.fmlb.forsaos.client.application.models.REPOLocalEditModel;
import com.fmlb.forsaos.client.application.models.REPOModel;
import com.fmlb.forsaos.client.application.models.REPOMountAllModel;
import com.fmlb.forsaos.client.application.models.REPOMountInfoModel;
import com.fmlb.forsaos.client.application.models.REPONFSCreateModel;
import com.fmlb.forsaos.client.application.models.REPONFSEditModel;
import com.fmlb.forsaos.client.application.models.REPOUnMountAllModel;
import com.fmlb.forsaos.client.application.models.RTMModel;
import com.fmlb.forsaos.client.application.models.RegisterLicenseModel;
import com.fmlb.forsaos.client.application.models.RegisterUPSModel;
import com.fmlb.forsaos.client.application.models.RestartForsaOSModel;
import com.fmlb.forsaos.client.application.models.RestoreBlinkModel;
import com.fmlb.forsaos.client.application.models.RestoreSnapshotModel;
import com.fmlb.forsaos.client.application.models.SMTPConfigurationModel;
import com.fmlb.forsaos.client.application.models.SMTPDestinationModel;
import com.fmlb.forsaos.client.application.models.SchedulerModel;
import com.fmlb.forsaos.client.application.models.SendAlertAllUserModel;
import com.fmlb.forsaos.client.application.models.SendTestEmailModel;
import com.fmlb.forsaos.client.application.models.SysInfoModel;
import com.fmlb.forsaos.client.application.models.SystemStatusChartModel;
import com.fmlb.forsaos.client.application.models.UPSDetailsModel;
import com.fmlb.forsaos.client.application.models.UPSModel;
import com.fmlb.forsaos.client.application.models.UpdateHostNameModel;
import com.fmlb.forsaos.client.application.models.UpdateLEMNameModel;
import com.fmlb.forsaos.client.application.models.UpdateLEMSnapshotModel;
import com.fmlb.forsaos.client.application.models.UpdateRTMModel;
import com.fmlb.forsaos.client.application.models.UpdateSMTPConfigrationModel;
import com.fmlb.forsaos.client.application.models.UpdateTimeZoneModel;
import com.fmlb.forsaos.client.application.models.UserAccountModel;
import com.fmlb.forsaos.client.application.models.UserDeleteModel;
import com.fmlb.forsaos.client.application.models.UserGroupDeleteModel;
import com.fmlb.forsaos.client.application.models.UserGroupModel;
import com.fmlb.forsaos.client.application.models.UserSessionObject;
import com.fmlb.forsaos.client.application.models.VMCreateAttachLemModel;
import com.fmlb.forsaos.client.application.models.VMCreateCloneLemModel;
import com.fmlb.forsaos.client.application.models.VMCreateModel;
import com.fmlb.forsaos.client.application.models.VMCreateWithNewLemModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.fmlb.forsaos.shared.application.utility.PartitionType;
import com.fmlb.forsaos.shared.application.utility.VMState;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CommonServiceAsync
{
	void doLogin( CurrentUser cuurentUser, AsyncCallback<UserSessionObject> callback );

	void getLems( String data, boolean addDelayFl, AsyncCallback<List<LEMModel>> callback );

	void createLem( LEMJSONModel lemjsonModel, AsyncCallback<Boolean> callback );

	void createMultiLem( CreateMultiLEMModel multiLem, AsyncCallback<Boolean> callback );

	void createLemAndAssisnToVm( CreateLEMandAssisgnToVMmodel createLEMandAssisgnToVMmodel, AsyncCallback<Boolean> callback );

	void deleteLem( LEMDeleteJSONModel lemjsonModel, AsyncCallback<Boolean> callback );

	void deleteMultipleLem( List<LEMDeleteJSONModel> lemDeleteJSONModels, AsyncCallback<Map<Boolean, List<String>>> callback );

	void getCapacityChartData( AsyncCallback<CapacityChartModel> callback );

	void getRTMUsageChartData( AsyncCallback<String> callback );

	void getSystemStatusChartData( AsyncCallback<SystemStatusChartModel> callback );

	void getVMsList( String data, boolean addDelayFl, AsyncCallback<List<VMModel>> callback );

	void createVM( VMCreateModel vmCreateModel, AsyncCallback<Boolean> callback );

	void createVMWithLem( VMCreateWithNewLemModel vmCreateModel, AsyncCallback<Boolean> callback );

	void createVMAttachLem( VMCreateAttachLemModel vmCreateModel, AsyncCallback<Boolean> callback );

	void createVMCloneLem( VMCreateCloneLemModel vmCreateModel, AsyncCallback<Boolean> callback );

	void getRTM( String data, AsyncCallback<RTMModel> callback );
	
	void updateRTMName( UpdateRTMModel updateRTMModel,AsyncCallback<Boolean> asyncCallback );

	void updateLEMName( UpdateLEMNameModel updateLEMNameModel, AsyncCallback<Boolean> callback );

	void deleteVM( VMModel vmModel, Boolean deleteLem, AsyncCallback<Boolean> callback );

	void changeVMState( VMModel vmModel, VMState vmState, AsyncCallback<Boolean> callback );

	void attachOrDetachISO( VMModel vmModel, String cd_path, Boolean attachISO, AsyncCallback<Boolean> callback );

	void attachLemsToVm( VMModel vmModel, List<LEMModel> lems, AsyncCallback<Boolean> callback );

	void detachLemsToVm( VMModel vmModel, LEMModel lems, Boolean deleteLem, AsyncCallback<Boolean> callback );

	void attachNetToVm( VMModel vmModel, LVNetCreateRequestModel lvNetCreateRequestModel, AsyncCallback<Boolean> callback );

	void detachNetFromVm( VMModel vmModel, NicModel nicModel, AsyncCallback<Boolean> callback );

	void attachSDNToVm( VMModel vmModel, AsyncCallback<Boolean> callback );

	void detachSDNFromVm( VMModel vmModel, AsyncCallback<Boolean> callback );

	void updateVMDetails( VMModel vmModel, String type, AsyncCallback<Boolean> callback );

	void logout( AsyncCallback<Boolean> callback );

	void createRTM( String rtmName, AsyncCallback<Boolean> asyncCallback );

	void deleteRTM( RTMModel rtmModelTobeDel, AsyncCallback<Boolean> asyncCallback );

	void isSessionValid( String sessionId, AsyncCallback<CurrentUser> asyncCallback );

	void updateVMName( VMModel vmModel, String new_name, AsyncCallback<Boolean> asyncCallback );

	void resetRTM( AsyncCallback<Boolean> asyncCallback );

	void getAllBridges( AsyncCallback<List<BridgeJSONModel>> asyncCallback );

	void createAdapter( LVNetCreateRequestModel lvNetCreateRequestModel, AsyncCallback<Boolean> asyncCallback );

	void deleteAdapter( NameModel nameModel, AsyncCallback<Boolean> asyncCallback );

	void getLvnet( String id, AsyncCallback<NicModel> callBack );

	void shutdownForsaOs( AsyncCallback<Boolean> asyncCallback );

	void shutdownSystem( AsyncCallback<Boolean> asyncCallback );

	void restartSystem( AsyncCallback<Boolean> asyncCallback );

	void restartForsaOs( RestartForsaOSModel restartForsaOSModel, AsyncCallback<Boolean> asyncCallback );

	void getFileSystemJSON( String dirPath, AsyncCallback<String> asyncCallback );

	void cloneLEM( CloneLEMModel cloneLEMModel, AsyncCallback<Boolean> asyncCallback );

	void cloneVM( CloneVMModel cloneLEMModel, AsyncCallback<Boolean> asyncCallback );

	void cloneLemAndAssisnToVm( CloneLEMandAssisgnToVMmodel cloneLEMandAssisgnToVMmodel, AsyncCallback<Boolean> asyncCallback );

	void createLEMSnapshot( LEMSnapshotModel lemSnapshotModel, AsyncCallback<Boolean> asyncCallback );

	void expandLEM( ExpandLEMModel expandLEMModel, AsyncCallback<Boolean> asyncCallback );

	void expandVM( ExpandVMModel expandVMModel, AsyncCallback<Boolean> asyncCallback );

	void updateLEMSnapshot( UpdateLEMSnapshotModel updateLEMSnapshotModel, AsyncCallback<Boolean> asyncCallback );

	void promoteLEMSnapshot( PromoteSnapshotModel promoteSnapshotModel, AsyncCallback<Boolean> asyncCallback );

	void restoreLEMSnapshot( RestoreSnapshotModel restoreSnapshotModel, AsyncCallback<Boolean> asyncCallback );

	void getLEMSnapshots( String data, boolean addDelayFl, AsyncCallback<List<LEMSnapshotModel>> asyncCallback );

	void deleteLemSnapshot( LEMSnapshotDeleteModel lemSnapshotDeleteModel, AsyncCallback<Boolean> asyncCallback );

	void getActiveDirectoryNames( String data, AsyncCallback<List<ActiveDirectoryModel>> asyncCallback );

	void getGroupNames( String data, AsyncCallback<List<UserGroupModel>> callback );

	void getSMTPname( String data, AsyncCallback<List<SMTPConfigurationModel>> callback );

	void getEmailAddress( String data, AsyncCallback<List<SMTPDestinationModel>> callback );

	void getUserNames( String data, AsyncCallback<List<UserAccountModel>> callback );

	void getActiveDirectoryGroup( String data, AsyncCallback<List<String>> asyncCallback );

	void createGroup( CreateUserGroupModel creategroupModel, AsyncCallback<Boolean> callback );

	void createSMTPConfiguration( UpdateSMTPConfigrationModel createSMTPModel, AsyncCallback<Boolean> callback );

	void addEmail( AddEmailModel addemailModel, AsyncCallback<Boolean> callback );

	void createUser( CreateNewUserModel createUserModel, AsyncCallback<Boolean> callback );

	void createActiveDirectory( CreateActiveDirectoryModel createActiveDirectoryModel, AsyncCallback<Boolean> callback );

	void deleteUserGroup( UserGroupDeleteModel deleteGroup, AsyncCallback<Boolean> callback );

	void deleteDomain( DeleteActiveDirectory deleteActiveDirectory, AsyncCallback<Boolean> callback );

	void deleteUser( UserDeleteModel deleteUser, AsyncCallback<Boolean> callback );

	void changePassword( ChangePasswordModel changepass, AsyncCallback<Boolean> callback );

	void getSysInfoModel( AsyncCallback<SysInfoModel> callback );

	void updateTimeZone( UpdateTimeZoneModel updateTimeZoneModel, AsyncCallback<Boolean> callback );

	void updateHostName( UpdateHostNameModel updateHostNameModel, AsyncCallback<Boolean> callback );

	void getLoginHistoryModels( String data, AsyncCallback<List<LoginHistoryModel>> callback );

	//REPO APIs - Start
	void getConfigRepo( String requestData, Boolean addDelayFl, AsyncCallback<List<REPOModel>> asyncCallback );

	void removeConfig( String configId, AsyncCallback<Boolean> asyncCallback );

	void discoverConfig( String requestJSON, AsyncCallback<REPOMountInfoModel[]> asyncCallback );

	void addConfigLocal( REPOLocalCreateModel repoMainModel, AsyncCallback<Boolean> asyncCallback );

	void updateConfigLocal( REPOLocalEditModel repoMainModel, AsyncCallback<Boolean> asyncCallback );

	void getConfigLocalRepoModel( String requestData, AsyncCallback<REPOLocalCreateModel> asyncCallback );

	void addConfigNFS( REPONFSCreateModel repoMainModel, AsyncCallback<Boolean> asyncCallback );

	void updateConfigNFS( REPONFSEditModel repoMainModel, AsyncCallback<Boolean> asyncCallback );

	void getConfigNFSRepoModel( String requestData, AsyncCallback<REPONFSCreateModel> asyncCallback );

	void addConfigCIFS( REPOCIFSCreateModel repoMainModel, AsyncCallback<Boolean> asyncCallback );

	void updateConfigCIFS( REPOCIFSEditModel repoMainModel, AsyncCallback<Boolean> asyncCallback );

	void getConfigCIFSRepoModel( String requestData, AsyncCallback<REPOCIFSCreateModel> asyncCallback );

	void discoverIscsiConfig( String ipAddress, AsyncCallback<List<String>> asyncCallback );

	void addConfigISCSI( REPOISCSICreateModel repoMainModel, AsyncCallback<Boolean> asyncCallback );

	void updateConfigISCSI( REPOISCSIEditModel repoMainModel, AsyncCallback<Boolean> asyncCallback );

	void getConfigISCSIRepoModel( String requestData, AsyncCallback<REPOISCSICreateModel> asyncCallback );

	void mountConfig( REPOMountAllModel repoMountAllModel, AsyncCallback<Boolean> asyncCallback );

	void unmountConfig( REPOUnMountAllModel unMountAllModel, AsyncCallback<Boolean> asyncCallback );

	void makeFs( String requestData, AsyncCallback<Boolean> asyncCallback );

	//REPO APIs - End

	void getNetworkingDevice( List<NetworkDeviceType> deviceTypes, AsyncCallback<List<NetworkingModel>> asyncCallback );

	void getPhysicalDeviceList( String data, AsyncCallback<List<InterfaceModel>> asyncCallback );

	void createBridge( CreateBridgeModelJSON createBridgeModel, AsyncCallback<Boolean> asyncCallback );

	void updateNetworkDevice( CreateNetworkModel networkModel, AsyncCallback<Boolean> asyncCallback );

	void createEthernet( CreateEthernetModelJSON createEthernetModelJSON, AsyncCallback<Boolean> asyncCallback );

	void applyNetwork( AsyncCallback<Boolean> asyncCallback );

	void deleteNetworkingDevice( NetworkDeviceType deviceType, String deviceName, AsyncCallback<Boolean> asyncCallback );

	void getNetworkDeviceCommonProperties( String deviceName, NetworkDeviceType deviceType, AsyncCallback<CreateNetworkModel> asyncCallback );

	void getManagementPartitions( String data, AsyncCallback<List<String>> asyncCallback );

	void getDiagSystemStateModel( String data, AsyncCallback<DiagSystemStateModel> asyncCallback );

	void getBlink( String data, AsyncCallback<List<BlinkModel>> callback );

	void createBlink( CreateBlinkModel createBlink, AsyncCallback<Boolean> callback );

	void deleteBlink( DeleteBlinkModel deleteBlinkModel, AsyncCallback<Boolean> callback );

	void restoreBlink( RestoreBlinkModel restoreBlink, AsyncCallback<Boolean> callback );

	void getProgress( AsyncCallback<ProgressModel> callback );

	void getPartition( String data, AsyncCallback<List<String>> asyncCallback );

	void createScheduler( SchedulerModel createScheduler, AsyncCallback<Boolean> callback );

	void getScheduler( String data, boolean addDelayFl, AsyncCallback<List<SchedulerModel>> asyncCallback );

	void deleteScheduler( SchedulerModel deleteScheduler, AsyncCallback<Boolean> asyncCallback );

	void pauseScheduler( SchedulerModel pauseScheduler, AsyncCallback<Boolean> callback );

	void resumeScheduler( SchedulerModel resumeScheduler, AsyncCallback<Boolean> callback );

	void deleteEmail( DeleteEmailModel deleteEmail, AsyncCallback<Boolean> callback );

	void deleteSMTP( DeleteSMTPModel deleteSMTP, AsyncCallback<Boolean> callback );

	void sendTestEmail( SendTestEmailModel sendEmail, AsyncCallback<Boolean> callback );

	void sendAlertEmail( SendAlertAllUserModel sendAlert, AsyncCallback<Boolean> callback );

	void getUPS( String data, AsyncCallback<List<UPSModel>> asyncCallback );

	void getUPSDetails( String data, AsyncCallback<UPSDetailsModel> asyncCallback );

	void deRegisterUPS( DeregisterUPSModel deleteUps, AsyncCallback<Boolean> callback );

	void registerUPS( RegisterUPSModel registerUps, AsyncCallback<Boolean> asyncCallback );

	void sysBootTime( AsyncCallback<List<String>> callback );

	void getEventLog( EventLogRequestModel eventLogRequestModel, AsyncCallback<List<EventLogResponseModel>> asyncCallback );

	void startVMMonitoring( String data, AsyncCallback<Boolean> asyncCallback );

	void stopVMMonitoring( AsyncCallback<Boolean> asyncCallback );

	void listPartition( Boolean addDelayFl, AsyncCallback<List<PartitionModel>> asyncCallback );

	void createPartition( PartitionCreateModel partitionCreateModel, PartitionType partitionType, AsyncCallback<Boolean> callback );

	void deletePartition( PartitionDeleteModel partitionDeleteModel, AsyncCallback<Boolean> callback );

	void expandPartition( PartitionExpandModel partitionExpandModel, AsyncCallback<Boolean> callback );

	void registerLicense( RegisterLicenseModel registerLicenseModel, AsyncCallback<Boolean> callback );

	void deleteLicense( AsyncCallback<Boolean> asyncCallback );

	void getLicense( AsyncCallback<LicenseDetailsModel> callback );

	void extendExpiry( ExtendExpiryModel expiryModel, AsyncCallback<Boolean> asyncCallback );

	void refreshUserGroup( String requestData, AsyncCallback<Boolean> asyncCallback );

	void editUserGroupModel( EditUserGroupModel editUserGroupModel, AsyncCallback<Boolean> asyncCallback );
}
