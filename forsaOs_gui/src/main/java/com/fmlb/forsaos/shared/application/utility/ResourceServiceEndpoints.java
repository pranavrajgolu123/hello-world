package com.fmlb.forsaos.shared.application.utility;

public enum ResourceServiceEndpoints
{
	/**
	 * URI: /config/mongo/get Description: Get current mongo config Method: GET
	 */
	STATS_SYS_CURRENT( "/stats/sys/current" ),

	CONFIG_MONGO_GET( "/config/mongo/get" ),

	STATS_AMPL_CUR( "/stats/ampl/cur" ),

	STATS_AMPL_ALL( "/stats/ampl/all" ),

	STATS_AMPL_HIST( "/stats/ampl/hist" ),

	MANG_SCHEDULER_CREATE( "/scheduler/rest/register" ),

	MANG_SCHEDULER_LIST( "/scheduler/rest/list" ),

	MANG_SCHEDULER_DELETE( "/scheduler/rest/delete" ),

	MANG_SCHEDULER_RESUME( "/scheduler/rest/resume" ),

	MANG_SCHEDULER_PAUSE( "/scheduler/rest/pause" ),

	LOG_GET( "/log/get" ),

	VIRT_VM_LIST( "/virt/vm/list" ),

	VIRT_VM_CREATE( "/virt/vm/create" ),

	VIRT_RTM_CREATE( "/virt/rtm/create" ),

	VIRT_RTM_DELETE( "/virt/rtm/delete" ),

	VIRT_RTM_LIST( "/virt/rtm/list" ),
	
	VIRT_RTM_UPDATE("/virt/rtm/update"),

	VIRT_LEM_LIST( "/virt/lem/list" ),

	VIRT_LEM_CREATE( "/virt/lem/create" ),

	VIRT_LEM_MULTI_CREATE( "/virt/lem/multi_create" ),

	VIRT_LEM_DELETE( "/virt/lem/delete" ),

	VIRT_LEM_MULTI_DELETE( "/virt/lem/multi_delete" ),

	VIRT_LEM_UPDATE( "/virt/lem/update" ),
	
	VIRT_VM_DELETE( "/virt/vm/delete" ),

	VIRT_VM_START( "/virt/vm/start" ),

	VIRT_VM_PAUSE( "/virt/vm/pause" ),

	VIRT_VM_RESUME( "/virt/vm/resume" ),

	VIRT_VM_SHUTDOWN( "/virt/vm/shutdown" ),

	VIRT_VM_DESTROY( "/virt/vm/destroy" ),

	VIRT_VM_ATTACH_CD( "/virt/vm/attach_cd" ),

	VIRT_VM_DETACH_CD( "/virt/vm/detach_cd" ),

	VIRT_VM_ATTACH_LEM( "/virt/vm/attach_lem" ),

	VIRT_VM_DETACH_LEM( "/virt/vm/detach_lem" ),

	VIRT_VM_ATTACH_NET( "/virt/vm/attach_network" ),

	VIRT_VM_DETACH_NET( "/virt/vm/detach_network" ),

	VIRT_VM_ATTACH_SDN( "/virt/vm/attach_sdn" ),

	VIRT_VM_DETACH_SDN( "/virt/vm/detach_sdn" ),

	VIRT_VM_UPDATE( "/virt/vm/update" ),

	VIRT_RESET( "/virt/reset" ),

	NETWORKD_DEVICE_LIST( "/networkd/device/list" ),

	/**
	 * Not being used for now
	 */
	VIRT_VM_SAVE( "/virt/vm/save" ),

	VIRT_LVNET_CREATE( "/virt/lvnet/create" ),

	VIRT_LVNET_DELETE( "/virt/lvnet/delete" ),

	VIRT_LVNET_GET( "/virt/lvnet/get" ),

	CONTROLLER_STOP_ALL( "/controller/stop_all" ),

	CONTROLLER_RESTART( "/controller/restart" ),

	CONTROLLER_SYS_SHUTDOWN( "/controller/sys/shutdown" ),

	CONTROLLER_SYS_REBOOT( "/controller/sys/reboot" ),

	KEYSTONE_USER_EXTEND_TOKEN( "/keystone/user/extend_token" ),

	VIRT_LEM_CLONE( "/virt/lem/clone" ),

	VIRT_VM_CLONE( "/virt/vm/clone" ),

	VIRT_LEM_SNAPSHOT_CREATE( "/virt/lem/snapshot_create" ),

	VIRT_LEM_EXPAND( "/virt/lem/expand" ),

	VIRT_VM_RESIZE( "/virt/vm/resize" ),

	VIRT_LEM_SNAPSHOT_UPDATE( "/virt/lem/snapshot_update" ),

	VIRT_SNAPSHOT_LIST( "/virt/snapshot/list" ),

	VIRT_LEM_RESTORE_SNAPSHOT( "/virt/lem/snapshot_restore" ),

	VIRT_LEM_SNAPSHOT_DELETE( "/virt/lem/snapshot_delete" ),

	VIRT_LEM_SNAPSHOT_PROMOTE( "/virt/lem/snapshot_promote" ),

	//REPO APIs - Start
	REPOMANAGEMENT_GET_CONFIG( "/repomanagement/get_config" ),

	REPOMANAGEMENT_DISCOVER_CONFIG( "/repomanagement/discover_config" ),

	REPOMANAGEMENT_REMOVE_CONFIG( "/repomanagement/remove_config" ),

	REPOMANAGEMENT_ADD_CONFIG( "/repomanagement/add_config" ),

	REPOMANAGEMENT_UPDATE_CONFIG( "/repomanagement/update_config" ),

	REPOMANAGEMENT_GET_INITIATOR( "/repomanagement/get_initiator" ),

	REPOMANAGEMENT_DISCOVER_ISCSITARGETS(
			"/repomanagement/discover_iscsiTargets" ),

	REPOMANAGEMENT_MOUNT_CONFIG( "/repomanagement/mount_config" ),

	REPOMANAGEMENT_UNMOUNT_CONFIG( "/repomanagement/unmount_config" ),
	//REPO APIs - End

	KEYSTONE_GROUP_REFRESH( "/keystone/group/refresh" ),

	KEYSTONE_GROUP_LIST( "/keystone/group/list" ),

	KEYSTONE_GROUP_CREATE( "/keystone/group/create" ),

	KEYSTONE_USER_CREATE( "/keystone/user/create" ),

	KEYSTONE_USER_LIST_USER( "/keystone/user/list_user" ),

	KEYSTONE_GROUP_DELETE( "/keystone/group/delete" ),

	KEYSTONE_USER_DELETE( "/keystone/user/delete" ),

	KEYSTONE_DOMAIN_JOIN( "/keystone/domain/join" ),

	KEYSTONE_DOMAIN_LIST( "/keystone/domain/list" ),

	KEYSTONE_DOMAIN_LEAVE( "/keystone/domain/leave" ),

	KEYSTONE_USER_UPDATE_USER( "/keystone/user/update_user" ),

	KEYSTONE_DOMAIN_LIST_GROUP( "/keystone/domain/list_group" ),

	KEYSTONE_GROUP_UPDATE( "/keystone/group/update" ),

	ALERT_REG_USER( "/alert/reg_user" ),

	ALERT_LIST_ALERT_USERS( "/alert/list_alert_users" ),

	ALERT_LIST_SMTP( "/alert/list_smtp" ),

	ALERT_SMTP_CONFIG( "/alert/smtp_config" ),

	ALERT_SEND_EMAIL( "/alert/send_email" ),

	ALERT_DEL_USER( "/alert/del_user" ),

	ALERT_DELETE_SMTP( "/alert/delete_smtp" ),

	ALERT_SEND_ALERT( "/alert/send_alert" ),

	STATS_SYSINFO( "/stats/sysinfo" ),

	SYS_TIMEZONE( "/sys/timezone" ),

	SYS_HOSTNAME( "/sys/hostname" ),

	LOGIN_HISTORY( "/login_history" ),

	LOGIN( "/login" ),

	NETWORD_DEVICE_LIST_PHYSICAL_DEVICE(
			"/networkd/device/list_physical_device" ),

	NETWORKD_DEVICE_CREATE( "/networkd/device/create" ),

	NETWORKD_DEVICE_UPDATE( "/networkd/device/update" ),

	NETWORKD_DEVICE_DELETE( "/networkd/device/delete" ),

	NETWORKD_OP_TRY_APPLY( "/networkd/op/try_apply" ),

	NETWORKD_OP_APPLY( "/networkd/op/apply" ),

	NETWORKD_OP_BACKUP( "/networkd/op/backup" ),

	NETWORKD_OP_RESTORE( "/networkd/op/restore" ),

	MANAGEMENT_CONF_LIST_PARTITION( "/management/conf/list_partition" ),

	STATS_CFTM_STATE( "/stats/cftm/state" ),

	BLINK_NAME_LIST( "/management/blink/list" ),

	BLINK_NEW_CREATE( "/management/blink/create" ),

	BLINK_DELETE( "/management/blink/delete" ),

	BLINK_RESTORE( "/management/blink/restore" ),

	BLINK_PROGRESS( "/management/blink/get_progress" ),

	BLINK_PARTITION_NAMES( "/management/conf/list_partition" ),

	REPOMANAGEMENT_MAKEFS( "/repomanagement/makefs" ),

	UPS_LIST( "/ups/list" ),

	UPS_GET_STATUS( "/ups/get_status" ),

	STATS_SEND_TESTEMAIL( " " ),

	UPS_UNREGISTER( "/ups/unregister" ),

	UPS_REGISTER( "/ups/register" ),

	STATS_SYS_BOOT_TIME( "/stats/sys_boot_time" ),

	VIRT_VM_START_MON( "/virt/vm/start_mon" ),

	VIRT_VM_STOP_MON( "/virt/vm/stop_mon" ),

	MANAGEMENT_CONF_ADD_BLINK_PARTITION(
			"/management/conf/add_blink_partition" ),

	MANAGEMENT_CONF_ADD_SNAPSHOT_PARTITION(
			"/management/conf/add_snapshot_partition" ),

	MANAGEMENT_CONF_DELETE_BLINK_PARTITION(
			"/management/conf/delete_blink_partition" ),

	MANAGEMENT_CONF_EXPAND_PARTITION( "/management/conf/expand_partition" ),

	MANG_DELETE_LICENSE( "/management/license/delete" ),

	MANG_REGISTER_LICENSE( "/management/license/register" ),

	LISCENSE_DETAILS( "/management/license/get" );

	private String path;

	private ResourceServiceEndpoints( String pathValue )
	{
		this.path = pathValue;
	}

	public String getPath()
	{
		return path;
	}

}
