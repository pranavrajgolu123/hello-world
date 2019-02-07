package com.fmlb.forsaos.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface AppResources extends ClientBundle
{
	interface Normalize extends CssResource
	{
	}

	interface Style extends CssResource
	{
		String semiPieChart();

		String RTMUsageChartContainer();

		String cpuUsage();

		String memoryUsage();

		String coreTempUsage();

		String popButton();

		String mainPanel();

		String displayInline();

		String lemDetail();

		String noMargin();

		String LEMDetailChartContainer();

		String nameEditIcon();

		String capacityChartPanel();

		String rtmUsageChartPanel();

		String rtmPerformanceChartPanel();

		String i2_logo();

		String i2_logo_bar();

		String left_nav();

		String active();

		String collapse_icon();

		String nav_brand_title();

		String nav_notification();

		String nav_account();

		String nav_account_arrow();

		String footer_box();

		String footer_txt();

		String create_data_item();

		String deleteMultipleDataItemIcon();

		String data_grid_header_txt();

		String data_delete_btn();

		String form_div();

		String lem_detail_column();

		String lem_detail_row();

		String lem_expand_btn();

		String lem_delete_btn();

		String vm_edit_btn();

		String clone_lem_box();

		String data_body_row();

		String clone_lem_btn();

		String grid_action_btn();

		String clone_Detail();

		String left_menu_dashboard_icon();

		String left_menu_management_icon();

		String left_menu_virtualization_icon();

		String left_menu_diagnostics_icon();

		String left_menu_reporting_icon();

		String left_menu_eventLog_icon();

		String left_menu_settings_icon();

		String popup_btn_row();

		String popup_btn();

		String popup_data_header();

		String popup_data_value();

		String action_item_bar();

		String action_status();

		String action_status_switch();

		String detail_header();

		String dashbaord_chart_row();

		String chart_penal_label();

		String dashbaord_physicalLabel();

		String dashbaord_physicalValue();

		String dashbaord_usableLabel();

		String dashbaord_usableValue();

		String dashbaord_system_status_Label();

		String dashbaord_userOptions();

		String settingsMultipleDataItemIcon();

		String vm_information_row();

		String vm_information_edit();

		String VM_Detail();

		String vm_setting_row();

		String vm_setting_header();

		String vm_enable_boot();

		String vm_tabs();

		String vm_tab_waves();

		String rtm_detail();

		String rtm_detail_penal();

		String rtm_detail_row();

		String rtm_detail_list();

		String create_rtm_panel();

		String create_rtm_filed();

		String create_rtm_btn();

		String rtm_error_label();

		String popup_checkbox_panel();

		String popup_checkbox_panel_body();

		String padding_left_0();

		String margin_top_10();

		String Password_Required();

		String no_padding();

		String power_action_dropdown();

		String setting_action_dropdown();

		String vm_details_setting_row();

		String vm_setting_inform_datail_column();

		String error_popup_icon();

		String modal_vm_name_row();

		String modal_vm_add_lem_dropdown();

		String modal_vm_add_lem_label();

		String create_VM_Network_penal();

		String create_VM_add_network();

		String create_VM_add_network_icon();

		String create_VM_Network_body();

		String create_vm_netwrok_delete();

		String VM_nameEditIcon();

		String vm_power_btn();

		String nameEditIcon_row();

		String graphic_Driver_Selection();

		String vnc_Panel();

		String popup_error_icon();

		//		String create_VM_network_bridgeComboBox();

		String vm_details_setting_spiceLabel();

		String vm_details_clone_vm();

		String vm_expand_btn();

		String expand_vm_size();

		String expand_vm_force_expand();

		String expand_size_row();

		String create_vm_ael_dropdown();

		String expand_vm_select_lem();

		String vm_keyboard_panel();

		String virtuale_row();

		String expand_vm_force_error_txt();

		String vm_console_panel();

		String system_tabs();

		String account_tabs();

		String account_detail();

		String account_detail_row();

		String modify_password_input();

		String change_password_btn();

		String margin_5();

		String restore_snapshot_alert_msg();

		String showPassword();

		String pathLoadData();

		String advanceSettingPanelBody();

		String cifsRow();

		String repoCifsReadOnly();

		String repoPortNumber();

		String iscsiAdvanceSettingPanelBody();

		String nfsPathLoadData();

		String nfsReadOnly();

		String bridgeSelectInterfacesPenal();

		String bridgeSelectInterfacesCheckbox();

		String updateNetworkRowPenal();

		String updateNetworkRowSwitch();

		String updateNetworkGateway();

		String updateNetworkIp();

		String updateNetworkSubnet();

		String updateNetworkIpBtn();

		String updateNetworkSubnetPrefix();

		String bridgeSelectInterfacesError();

		String updateNetworkBtn();

		String repoDeleteBtn();

		String repoDeleteErrorMsg();

		String disk_info();

		String iscsiUserName();

		String iscsiUserPassword();

		String repoPopupBtnRow();

		String repoNfsPopupRow();

		String createVmPopupRow();

		String cifsPopupErrorMsg();

		String isoDeleteBtn();

		String isoEditBtn();

		String isoPathBody();

		String w3LightGrey();

		String w3RoundLarge();

		String w3Container();

		String w3Blue();

		String NVMEdetailRow();

		String statusHeader();

		String progressPenal();

		String smartLogBtn();

		String idControlBtn();

		String fanIcon();

		String marginRight10();

		String sendAlertBtn();

		String upsMacAddressName();

		String upsTotalTxt();

		String upsTotalCount();

		String upsRegisterBtn();

		String footerDate();

		String eventBtn();

		String eventErrorMsg();

		String eventFilterPenal();

		String dateToFilterPanel();

		String emptyDataPenal();

		String restoreBtn();

		String blinkDetailsPanel();

		String permissionLabel();

		String permissionHeaderTxt();

		String activeCheckboxPanel();

		String newRadioBtn();

		String vmDetailsGridLastTd();

		String networkUpdateBtn();

		String InterfacesCheckBox();

		String InterfacesHeaderTxt();

		String performanceHeaderPenal();

		String performanceChartPenal();
		
		String expandAllocationTxt();
		
		String allocationInput();
		
		String expandPartitionName();
		
		String expandPartitionTxt();
		
		String userGroupUpdateUsers();
		
		String createNewUserAdminCheckbox();
	}

	@Source( "css/normalize.gss" )
	Normalize normalize();

	@Source( "css/style.gss" )
	Style style();

	@Source( "images/i2Logo.png" )
	ImageResource i2Logo();

	@Source( "images/formulusLogo.png" )
	ImageResource formulusLogo();

	@Source( "images/i2Logo_small.png" )
	ImageResource i2Logo_small();

	@Source( "images/Forsa_Login_Logo.png" )
	ImageResource ForsaOSLogo();

	@Source( "images/Forsa_Dashboard_Logo.png" )
	ImageResource Forsa_Dashboard_Logo();

}