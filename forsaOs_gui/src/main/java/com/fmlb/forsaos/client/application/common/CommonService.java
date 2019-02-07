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
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath( "common" )
public interface CommonService extends RemoteService
{
	UserSessionObject doLogin( CurrentUser cuurentUser ) throws FBException;

	List<LEMModel> getLems( String data, boolean addDelayFl ) throws FBException;

	Boolean createLem( LEMJSONModel lemjsonModel ) throws FBException;

	Boolean createMultiLem( CreateMultiLEMModel multiLem ) throws FBException;

	Boolean createLemAndAssisnToVm( CreateLEMandAssisgnToVMmodel createLEMandAssisgnToVMmodel ) throws FBException;

	Boolean deleteLem( LEMDeleteJSONModel lemjsonModel ) throws FBException;

	Map<Boolean, List<String>> deleteMultipleLem( List<LEMDeleteJSONModel> lemDeleteJSONModels ) throws FBException;

	CapacityChartModel getCapacityChartData() throws FBException;

	String getRTMUsageChartData() throws FBException;

	SystemStatusChartModel getSystemStatusChartData() throws FBException;

	List<VMModel> getVMsList( String data, boolean addDelayFl ) throws FBException;

	Boolean createVM( VMCreateModel vmCreateModel ) throws FBException;

	Boolean createVMWithLem( VMCreateWithNewLemModel vmCreateModel ) throws FBException;

	Boolean createVMAttachLem( VMCreateAttachLemModel vmCreateModel ) throws FBException;

	Boolean createVMCloneLem( VMCreateCloneLemModel vmCreateModel ) throws FBException;

	RTMModel getRTM( String data ) throws FBException;
	
	Boolean updateRTMName( UpdateRTMModel updateRTMModel ) throws FBException;

	Boolean updateLEMName( UpdateLEMNameModel updateLEMNameModel ) throws FBException;

	Boolean deleteVM( VMModel vmModel, Boolean deleteLem ) throws FBException;

	Boolean changeVMState( VMModel vmModel, VMState vmState ) throws FBException;

	Boolean attachOrDetachISO( VMModel vmModel, String cd_path, Boolean attachISO ) throws FBException;

	Boolean attachLemsToVm( VMModel vmModel, List<LEMModel> lems ) throws FBException;

	Boolean detachLemsToVm( VMModel vmModel, LEMModel lems, Boolean deleteLem ) throws FBException;

	Boolean attachNetToVm( VMModel vmModel, LVNetCreateRequestModel lvNetCreateRequestModel ) throws FBException;

	Boolean detachNetFromVm( VMModel vmModel, NicModel nicModel ) throws FBException;

	Boolean attachSDNToVm( VMModel vmModel ) throws FBException;

	Boolean detachSDNFromVm( VMModel vmModel ) throws FBException;

	Boolean updateVMDetails( VMModel vmModel, String type ) throws FBException;

	Boolean logout();

	Boolean createRTM( String rtmName ) throws FBException;

	Boolean deleteRTM( RTMModel rtmModelTobeDel ) throws FBException;

	CurrentUser isSessionValid( String sessionId ) throws FBException;

	Boolean updateVMName( VMModel vmModel, String new_name ) throws FBException;

	Boolean resetRTM() throws FBException;

	List<BridgeJSONModel> getAllBridges() throws FBException;

	Boolean createAdapter( LVNetCreateRequestModel lvNetCreateRequestModel ) throws FBException;

	Boolean deleteAdapter( NameModel nameModel ) throws FBException;

	NicModel getLvnet( String id ) throws FBException;

	Boolean shutdownForsaOs() throws FBException;

	Boolean shutdownSystem() throws FBException;

	Boolean restartSystem() throws FBException;

	Boolean restartForsaOs( RestartForsaOSModel restartForsaOSModel ) throws FBException;

	String getFileSystemJSON( String dirPath ) throws FBException;

	Boolean cloneLEM( CloneLEMModel cloneLEMModel ) throws FBException;

	Boolean cloneVM( CloneVMModel cloneVMModel ) throws FBException;

	Boolean cloneLemAndAssisnToVm( CloneLEMandAssisgnToVMmodel cloneLEMandAssisgnToVMmodel ) throws FBException;

	Boolean createLEMSnapshot( LEMSnapshotModel lemSnapshotModel ) throws FBException;

	Boolean expandLEM( ExpandLEMModel expandLEMModel ) throws FBException;

	Boolean expandVM( ExpandVMModel expandVMModel ) throws FBException;

	Boolean updateLEMSnapshot( UpdateLEMSnapshotModel updateLEMSnapshotModel ) throws FBException;

	Boolean restoreLEMSnapshot( RestoreSnapshotModel restoreSnapshotModel ) throws FBException;

	Boolean promoteLEMSnapshot( PromoteSnapshotModel promoteSnapshotModel ) throws FBException;

	Boolean deleteLemSnapshot( LEMSnapshotDeleteModel lemSnapshotDeleteModel ) throws FBException;

	List<LEMSnapshotModel> getLEMSnapshots( String data, boolean addDelayFl ) throws FBException;

	Boolean createGroup( CreateUserGroupModel creategroupModel ) throws FBException;

	Boolean createSMTPConfiguration( UpdateSMTPConfigrationModel createSMTPModel ) throws FBException;

	Boolean addEmail( AddEmailModel addemailModel ) throws FBException;

	Boolean createActiveDirectory( CreateActiveDirectoryModel createActiveDirectoryModel ) throws FBException;

	Boolean createUser( CreateNewUserModel createUserModel ) throws FBException;

	Boolean deleteUserGroup( UserGroupDeleteModel deleteGroup ) throws FBException;

	Boolean deleteDomain( DeleteActiveDirectory deleteActiveDirectory ) throws FBException;

	Boolean deleteUser( UserDeleteModel deleteUser ) throws FBException;

	Boolean changePassword( ChangePasswordModel changepass ) throws FBException;

	List<UserGroupModel> getGroupNames( String data ) throws FBException;

	List<SMTPConfigurationModel> getSMTPname( String data ) throws FBException;

	List<SMTPDestinationModel> getEmailAddress( String data ) throws FBException;

	List<UserAccountModel> getUserNames( String data ) throws FBException;

	List<ActiveDirectoryModel> getActiveDirectoryNames( String data ) throws FBException;

	List<String> getActiveDirectoryGroup( String data ) throws FBException;

	SysInfoModel getSysInfoModel() throws FBException;

	Boolean updateTimeZone( UpdateTimeZoneModel updateTimeZoneModel ) throws FBException;

	Boolean updateHostName( UpdateHostNameModel updateHostNameModel ) throws FBException;

	List<LoginHistoryModel> getLoginHistoryModels( String data ) throws FBException;

	//REPO APIs - Start
	List<REPOModel> getConfigRepo( String requestData, Boolean addDelayFl ) throws FBException;

	Boolean removeConfig( String configId ) throws FBException;

	REPOMountInfoModel[] discoverConfig( String requestJSON ) throws FBException;

	Boolean addConfigLocal( REPOLocalCreateModel repoMainModel ) throws FBException;

	Boolean updateConfigLocal( REPOLocalEditModel repoMainModel ) throws FBException;

	REPOLocalCreateModel getConfigLocalRepoModel( String requestData ) throws FBException;

	Boolean addConfigNFS( REPONFSCreateModel repoMainModel ) throws FBException;

	Boolean updateConfigNFS( REPONFSEditModel repoMainModel ) throws FBException;

	REPONFSCreateModel getConfigNFSRepoModel( String requestData ) throws FBException;

	Boolean addConfigCIFS( REPOCIFSCreateModel repoMainModel ) throws FBException;

	Boolean updateConfigCIFS( REPOCIFSEditModel repoMainModel ) throws FBException;

	REPOCIFSCreateModel getConfigCIFSRepoModel( String requestData ) throws FBException;

	List<String> discoverIscsiConfig( String ipAddress ) throws FBException;

	Boolean addConfigISCSI( REPOISCSICreateModel repoMainModel ) throws FBException;

	Boolean updateConfigISCSI( REPOISCSIEditModel repoMainModel ) throws FBException;

	REPOISCSICreateModel getConfigISCSIRepoModel( String requestData ) throws FBException;

	Boolean mountConfig( REPOMountAllModel repoMountAllModel ) throws FBException;

	Boolean unmountConfig( REPOUnMountAllModel repoUnMountAllModel ) throws FBException;

	Boolean makeFs( String requestData ) throws FBException;

	//REPO APIs - End

	List<NetworkingModel> getNetworkingDevice( List<NetworkDeviceType> deviceTypes ) throws FBException;

	List<InterfaceModel> getPhysicalDeviceList( String data ) throws FBException;

	Boolean createBridge( CreateBridgeModelJSON createBridgeModel ) throws FBException;

	Boolean updateNetworkDevice( CreateNetworkModel networkModel ) throws FBException;

	Boolean createEthernet( CreateEthernetModelJSON createEthernetModelJSON ) throws FBException;

	Boolean applyNetwork() throws FBException;

	Boolean deleteNetworkingDevice( NetworkDeviceType deviceType, String deviceName ) throws FBException;

	CreateNetworkModel getNetworkDeviceCommonProperties( String deviceName, NetworkDeviceType deviceType ) throws FBException;

	List<String> getManagementPartitions( String data ) throws FBException;

	DiagSystemStateModel getDiagSystemStateModel( String data ) throws FBException;

	List<BlinkModel> getBlink( String data ) throws FBException;

	Boolean createBlink( CreateBlinkModel createBlink ) throws FBException;

	Boolean deleteBlink( DeleteBlinkModel deleteBlinkModel ) throws FBException;

	Boolean restoreBlink( RestoreBlinkModel restoreBlink ) throws FBException;

	ProgressModel getProgress() throws FBException;

	List<String> getPartition( String data ) throws FBException;

	Boolean createScheduler( SchedulerModel createSchedulerModel ) throws FBException;

	List<SchedulerModel> getScheduler( String data, boolean addDelayFl ) throws FBException;

	Boolean deleteScheduler( SchedulerModel deleteScheduler ) throws FBException;

	Boolean pauseScheduler( SchedulerModel pauseScheduler ) throws FBException;

	Boolean resumeScheduler( SchedulerModel resumeScheduler ) throws FBException;

	Boolean deleteEmail( DeleteEmailModel deleteEmail ) throws FBException;

	Boolean deleteSMTP( DeleteSMTPModel deleteSMTP ) throws FBException;

	Boolean sendTestEmail( SendTestEmailModel sendEmail ) throws FBException;

	Boolean sendAlertEmail( SendAlertAllUserModel sendAlert ) throws FBException;

	List<UPSModel> getUPS( String data ) throws FBException;

	UPSDetailsModel getUPSDetails( String data ) throws FBException;

	Boolean deRegisterUPS( DeregisterUPSModel deleteUps ) throws FBException;

	Boolean registerUPS( RegisterUPSModel registerUps ) throws FBException;

	List<String> sysBootTime() throws FBException;

	List<EventLogResponseModel> getEventLog( EventLogRequestModel eventLogRequestModel ) throws FBException;

	Boolean startVMMonitoring( String data ) throws FBException;

	Boolean stopVMMonitoring() throws FBException;

	List<PartitionModel> listPartition( Boolean addDelayFl ) throws FBException;

	Boolean createPartition( PartitionCreateModel partitionCreateModel, PartitionType partitionType ) throws FBException;

	Boolean deletePartition( PartitionDeleteModel partitionDeleteModel ) throws FBException;
	
	Boolean expandPartition( PartitionExpandModel partitionExpandModel ) throws FBException;

	Boolean registerLicense( RegisterLicenseModel registerLicenseModel ) throws FBException;

	Boolean deleteLicense() throws FBException;

	LicenseDetailsModel getLicense() throws FBException;

	Boolean extendExpiry( ExtendExpiryModel expiryModel ) throws FBException;

	Boolean refreshUserGroup( String requestData ) throws FBException;

	Boolean editUserGroupModel( EditUserGroupModel editUserGroupModel ) throws FBException;
}
