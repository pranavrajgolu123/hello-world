package com.fmlb.forsaos.client.application.management.networking.detail;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.IPType;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.TextBoxWithDelete;
import com.fmlb.forsaos.client.application.models.CreateNetworkModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class UpdateNetworkingDetailV4Popup extends CommonPopUp
{

	private CreateNetworkModel networkModel;

	private UIComponentsUtil uiComponentsUtil;

	private Icommand updateNetworkDetailCmd;

	private MaterialRow updateNetworkRow;

	private MaterialLabel addresses;

	private MaterialLabel dns;

	private MaterialLabel dnsSearchDomains;

	private MaterialLabel addressesAutomatic;

	private MaterialLabel dnsAutomatic;

	private MaterialLabel dnsSearchAutomatic;

	private MaterialSwitch addressesSwitch;

	private MaterialSwitch dnsSwitch;

	private MaterialSwitch dnsSearchDmnsSwitch;

	private MaterialIcon addressAddIcon;

	private MaterialIcon dnsAddIcon;

	private MaterialIcon dnsSearchAddIcon;

	private MaterialPanel addressesPanel = new MaterialPanel();

	private MaterialPanel dnsPanel = new MaterialPanel();

	private MaterialPanel dnsSearchPanel = new MaterialPanel();

	private MaterialTextBox gateWay;

	private MaterialTextBox firstIP;

	private MaterialIntegerBox firstSubnet;

	private MaterialTextBox firstDns;

	private MaterialTextBox firstDnsSearch;

	private ArrayList<MaterialTextBox> ipList = new ArrayList<>();

	private ArrayList<MaterialIntegerBox> subnetList = new ArrayList<>();

	private ArrayList<MaterialTextBox> dnsList = new ArrayList<>();

	private ArrayList<MaterialTextBox> dnsSearchList = new ArrayList<>();

	private IPType ipType;

	AppResources resources = GWT.create( AppResources.class );

	public UpdateNetworkingDetailV4Popup( CreateNetworkModel networkModel, UIComponentsUtil uiComponentsUtil, Icommand updateNetworkDetailCmd, IPType ipType )
	{
		super( "Update Network", "", "Update Network", true );
		this.networkModel = networkModel;
		this.updateNetworkDetailCmd = updateNetworkDetailCmd;
		this.uiComponentsUtil = uiComponentsUtil;
		this.ipType = ipType;
		LoggerUtil.log( "initialize update network popop" );
		initialize();
		buttonAction();
		this.getButtonRow().addStyleName( resources.style().updateNetworkBtn() );
	}

	private void initialize()
	{
		initializeAddressPanel();
		initializeDnsPanel();
		initializeDnsSearchPanel();
		updateNetworkRow = new MaterialRow();
		addresses = uiComponentsUtil.getLabel( "Addresses", "s6" );
		addresses.addStyleName( resources.style().updateNetworkRowPenal() );
		dns = uiComponentsUtil.getLabel( "DNS", "s6" );
		dns.addStyleName( resources.style().updateNetworkRowPenal() );
		dnsSearchDomains = uiComponentsUtil.getLabel( "DNS Search Domains", "s6" );
		dnsSearchDomains.addStyleName( resources.style().updateNetworkRowPenal() );

		addressesAutomatic = uiComponentsUtil.getLabel( "Automatic", "s3" );
		addressesAutomatic.addStyleName( resources.style().updateNetworkRowPenal() );
		dnsAutomatic = uiComponentsUtil.getLabel( "Automatic", "s3" );
		dnsAutomatic.addStyleName( resources.style().updateNetworkRowPenal() );

		dnsSearchAutomatic = uiComponentsUtil.getLabel( "Automatic", "s3" );
		dnsSearchAutomatic.addStyleName( resources.style().updateNetworkRowPenal() );

		addressesSwitch = new MaterialSwitch( true );
		addressesSwitch.addStyleName( resources.style().updateNetworkRowPenal() );
		addressesSwitch.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				if ( addressesSwitch.getValue() )
				{
					addressAddIcon.setEnabled( true );
					addressesPanel.setVisible( true );
				}
				else
				{
					addressAddIcon.setEnabled( false );
					addressesPanel.setVisible( false );
				}

			}
		} );
		dnsSwitch = new MaterialSwitch( true );
		dnsSwitch.addStyleName( resources.style().updateNetworkRowPenal() );
		dnsSwitch.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				dnsSearchDmnsSwitch.setValue( !dnsSwitch.getValue() );
				if ( dnsSearchDmnsSwitch.getValue() )
				{
					dnsAddIcon.setEnabled( false );
					dnsSearchAddIcon.setEnabled( false );
					dnsPanel.setVisible( false );
					dnsSearchPanel.setVisible( false );
				}
				else
				{
					dnsAddIcon.setEnabled( true );
					dnsSearchAddIcon.setEnabled( true );
					dnsPanel.setVisible( true );
					dnsSearchPanel.setVisible( true );
				}
			}
		} );
		dnsSearchDmnsSwitch = new MaterialSwitch( true );
		dnsSearchDmnsSwitch.addStyleName( resources.style().updateNetworkRowPenal() );
		dnsSearchDmnsSwitch.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				LoggerUtil.log( "dnsSearchDmnsSwitch" + dnsSearchDmnsSwitch.getValue() );
				dnsSwitch.setValue( !dnsSearchDmnsSwitch.getValue() );
				LoggerUtil.log( "dnsSwitch" + dnsSwitch.getValue() );
				if ( dnsSwitch.getValue() )
				{
					dnsAddIcon.setEnabled( false );
					dnsSearchAddIcon.setEnabled( false );
					dnsSearchPanel.setVisible( false );
					dnsPanel.setVisible( false );
				}
				else
				{
					dnsAddIcon.setEnabled( true );
					dnsSearchAddIcon.setEnabled( true );
					dnsSearchPanel.setVisible( true );
					dnsPanel.setVisible( true );
				}

			}
		} );

		addressesSwitch.setGrid( "s2" );
		addressesSwitch.addStyleName( resources.style().updateNetworkRowSwitch() );
		dnsSwitch.setGrid( "s2" );
		dnsSwitch.addStyleName( resources.style().updateNetworkRowSwitch() );
		dnsSearchDmnsSwitch.setGrid( "s2" );
		dnsSearchDmnsSwitch.addStyleName( resources.style().updateNetworkRowSwitch() );

		addressAddIcon = new MaterialIcon( IconType.ADD );
		addressAddIcon.addStyleName( "updateNetworkRowAddIcon" );
		addressAddIcon.setGrid( "s1" );
		addressAddIcon.setEnabled( false );
		addressAddIcon.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				addressesPanel.add( getIPBoxPanel( null, null ) );
			}
		} );
		dnsAddIcon = new MaterialIcon( IconType.ADD );
		dnsAddIcon.addStyleName( "updateNetworkRowAddIcon" );
		dnsAddIcon.setGrid( "s1" );
		dnsAddIcon.setEnabled( false );
		dnsAddIcon.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				addNewDnsBox( null );
			}

		} );
		dnsSearchAddIcon = new MaterialIcon( IconType.ADD );
		dnsSearchAddIcon.addStyleName( "updateNetworkRowAddIcon" );
		dnsSearchAddIcon.setGrid( "s1" );
		dnsSearchAddIcon.setEnabled( false );
		dnsSearchAddIcon.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				addNewDnsSearchBox( null );
			}
		} );

		updateNetworkRow.add( addresses );
		updateNetworkRow.add( addressesAutomatic );
		updateNetworkRow.add( addressesSwitch );
		updateNetworkRow.add( addressAddIcon );
		updateNetworkRow.add( addressesPanel );
		updateNetworkRow.add( dns );
		updateNetworkRow.add( dnsAutomatic );
		updateNetworkRow.add( dnsSwitch );
		updateNetworkRow.add( dnsAddIcon );
		updateNetworkRow.add( dnsPanel );
		updateNetworkRow.add( dnsSearchDomains );
		updateNetworkRow.add( dnsSearchAutomatic );
		updateNetworkRow.add( dnsSearchDmnsSwitch );
		updateNetworkRow.add( dnsSearchAddIcon );
		updateNetworkRow.add( dnsSearchPanel );
		getBodyPanel().setMaxHeight( "300px" );
		getBodyPanel().setOverflow( Overflow.AUTO );
		getBodyPanel().add( updateNetworkRow );

		if ( !networkModel.getDhcp4()/* && networkModel.getAddresses() != null && !networkModel.getAddresses().isEmpty()*/ )
		{
			addressesSwitch.setValue( false );
			addressAddIcon.setEnabled( true );
			addressesPanel.setVisible( true );
			gateWay.setText( networkModel.getGateway4() );
			for ( int i = 0; i < networkModel.getAddresses().size(); i++ )
			{
				String address = networkModel.getAddresses().get( i );
				String ip = null;
				String subNetVal = null;
				if ( address.contains( "/" ) )
				{
					String[] split = address.split( "/" );
					ip = split[0];
					subNetVal = split[1];
				}
				if ( i == 0 )
				{
					firstIP.setText( ip );
					firstSubnet.setText( subNetVal );
				}
				else
				{

					addressesPanel.add( getIPBoxPanel( ip, subNetVal ) );

				}
			}
		}
		if ( !networkModel.getDhcp4() /*&& networkModel.getDns_search() != null && !networkModel.getDns_search().isEmpty()*/ )
		{
			dnsSearchDmnsSwitch.setValue( false );
			dnsSwitch.setValue( false );
			dnsAddIcon.setEnabled( true );
			dnsSearchAddIcon.setEnabled( true );
			dnsPanel.setVisible( true );
			dnsSearchPanel.setVisible( true );

			for ( int i = 0; i < networkModel.getDns_search().size(); i++ )
			{
				if ( i == 0 )
				{
					firstDnsSearch.setText( networkModel.getDns_search().get( i ) );
					//firstDns.setText( networkModel.getDns_addresses().get( i ) );
				}

				else
				{
					addNewDnsSearchBox( networkModel.getDns_search().get( i ) );
				}
			}

			for ( int i = 0; i < networkModel.getDns_addresses().size(); i++ )
			{
				if ( i == 0 )
				{
					//firstDnsSearch.setText( networkModel.getDns_search().get( i ) );
					firstDns.setText( networkModel.getDns_addresses().get( i ) );
				}

				else
				{
					addNewDnsBox( networkModel.getDns_addresses().get( i ) );
				}
			}

		}

	}

	private void initializeAddressPanel()
	{
		gateWay = uiComponentsUtil.getIP4TextBox( "Gateway", "", "s12", false );
		gateWay.addStyleName( resources.style().updateNetworkGateway() );
		firstIP = uiComponentsUtil.getIP4TextBox( "IP", "", "s10", false );
		firstIP.addStyleName( resources.style().updateNetworkIp() );
		firstSubnet = uiComponentsUtil.getIP4SubnetBox( "Subnet Prefix", "", "s2", false, firstIP );
		firstSubnet.addStyleName( resources.style().updateNetworkSubnet() );
		firstIP.addValueChangeHandler( new ValueChangeHandler<String>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				if ( firstSubnet != null && firstSubnet.getValue() != null )
				{
					firstSubnet.validate();
				}

			}
		} );
		ipList.add( firstIP );
		subnetList.add( firstSubnet );
		addressesPanel.add( gateWay );
		addressesPanel.add( firstIP );
		addressesPanel.add( firstSubnet );
		addressesPanel.setVisible( false );
	}

	private void initializeDnsPanel()
	{
		firstDns = uiComponentsUtil.getIP4TextBox( "Server", "", "s11", false );
		firstDns.addStyleName( resources.style().updateNetworkGateway() );

		dnsList.add( firstDns );
		dnsPanel.add( firstDns );
		dnsPanel.setVisible( false );
	}

	private void initializeDnsSearchPanel()
	{
		firstDnsSearch = uiComponentsUtil.getTexBox( "Search Domain", "", "s11" );
		firstDnsSearch.addStyleName( resources.style().updateNetworkGateway() );
		dnsSearchList.add( firstDnsSearch );
		dnsSearchPanel.add( firstDnsSearch );
		dnsSearchPanel.setVisible( false );
	}

	private MaterialPanel getIPBoxPanel( String ip, String subNet )
	{
		MaterialPanel ipBoxPanel = new MaterialPanel();
		MaterialTextBox ipTextBox;
		MaterialIntegerBox subnet;
		ipTextBox = uiComponentsUtil.getIP4TextBox( "IP", "", "s8", false );
		ipTextBox.addStyleName( resources.style().updateNetworkIp() );
		subnet = uiComponentsUtil.getIP4SubnetBox( "Subnet prefix", "", "s3", false, ipTextBox );
		subnet.addStyleName( resources.style().updateNetworkSubnetPrefix() );

		if ( ip != null )
		{
			ipTextBox.setText( ip );
		}
		if ( subnet != null )
		{
			subnet.setText( subNet );
		}

		ipTextBox.addValueChangeHandler( new ValueChangeHandler<String>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				if ( subnet != null && subnet.getValue() != null )
				{
					subnet.validate();
				}

			}
		} );
		MaterialIcon deleteIcon = new MaterialIcon( IconType.DELETE );
		deleteIcon.setGrid( "s1" );
		deleteIcon.addStyleName( resources.style().updateNetworkIpBtn() );
		ipBoxPanel.add( ipTextBox );
		ipBoxPanel.add( subnet );
		ipBoxPanel.add( deleteIcon );
		deleteIcon.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				ipBoxPanel.removeFromParent();
				ipList.remove( ipTextBox );
				subnetList.remove( subnet );
			}
		} );
		ipList.add( ipTextBox );
		subnetList.add( subnet );
		return ipBoxPanel;
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				if ( validate() )
				{

					networkModel.setDhcp4( addressesSwitch.getValue() );

					if ( !addressesSwitch.getValue() )
					{
						networkModel.setGateway4( gateWay.getValue() );

						networkModel.setAddresses( getAddresses() );
					}
					else
					{
						networkModel.setGateway4( "" );

						networkModel.setAddresses( new ArrayList<>() );
					}
					if ( !dnsSwitch.getValue() )
					{
						networkModel.setDns_addresses( getDns() );
					}
					else
					{
						networkModel.setDns_addresses( new ArrayList<>() );
					}
					if ( !dnsSearchDmnsSwitch.getValue() )
					{
						networkModel.setDns_search( getDnsSearchDomains() );
					}
					else
					{
						networkModel.setDns_search( new ArrayList<>() );
					}
					MaterialLoader.loading( true, getBodyPanel() );
					CommonServiceProvider.getCommonService().updateNetworkDevice( networkModel, new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							if ( result != null && result )
							{
								LoggerUtil.log( "BRIDGE updation SUCCESSFUL" );
								close();
								MaterialToast.fireToast( networkModel.getName() + " Updated..!", "rounded" );
								if ( updateNetworkDetailCmd != null )
								{
									updateNetworkDetailCmd.execute();
								}
							}

						}

						@Override
						public void onFailure( Throwable caught )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							super.onFailureShowErrorPopup( caught, null );
						}
					} );

				}
			}
		} );
	}

	private List<String> getAddresses()
	{
		List<String> addresses = new ArrayList<>();
		if ( ipList != null && !ipList.isEmpty() )
		{
			for ( int i = 0; i < ipList.size(); i++ )
			{
				String address = null;
				if ( ipList.get( i ).getValue() != null && !ipList.get( i ).getValue().trim().isEmpty() )
				{
					address = ipList.get( i ).getValue();
				}
				if ( subnetList.get( i ).getValue() != null )
				{
					address = ipList.get( i ).getValue() + "/" + subnetList.get( i ).getValue();
				}
				if ( address != null )
				{
					addresses.add( address );
				}
			}
			/*for ( MaterialTextBox ipBox : ipList )
			{
				addresses.add( ipBox.getValue() );
			}*/
		}
		return addresses;
	}

	private List<String> getDns()
	{
		List<String> addresses = new ArrayList<>();
		if ( dnsList != null && !dnsList.isEmpty() )
		{
			for ( MaterialTextBox ipBox : dnsList )
			{
				String address = null;
				if ( ipBox.getValue() != null && !ipBox.getValue().trim().isEmpty() )
				{
					address = ipBox.getValue();
				}

				if ( address != null )
				{
					addresses.add( ipBox.getValue() );
				}

			}
		}
		return addresses;
	}

	private List<String> getDnsSearchDomains()
	{
		List<String> addresses = new ArrayList<>();
		if ( dnsSearchList != null && !dnsSearchList.isEmpty() )
		{
			for ( MaterialTextBox ipBox : dnsSearchList )
			{
				String address = null;
				if ( ipBox.getValue() != null && !ipBox.getValue().trim().isEmpty() )
				{
					address = ipBox.getValue();
				}

				if ( address != null )
				{
					addresses.add( ipBox.getValue() );
				}
			}
		}
		return addresses;
	}

	@Override
	public boolean validate()
	{
		boolean valid = true;
		if ( !addressesSwitch.getValue() && gateWay.validate() )
		{
			if ( ipList != null && !ipList.isEmpty() )
			{
				for ( MaterialTextBox ipBox : ipList )
				{
					/*if ( !ipBox.validate() )
					{
						valid = false;
					}*/
				}
			}
			if ( subnetList != null && !subnetList.isEmpty() )
			{
				for ( MaterialIntegerBox subnet : subnetList )
				{
					if ( subnet.getValue() != null && !subnet.validate() )
					{
						valid = false;
					}
				}
			}
		}
		else if ( !dnsSwitch.getValue() )
		{

			if ( dnsList != null && !dnsList.isEmpty() )
			{
				for ( MaterialTextBox ipBox : dnsList )
				{
					/*if ( !ipBox.validate() )
					{
						valid = false;
					}*/
				}
			}
			if ( dnsSearchList != null && !dnsSearchList.isEmpty() )
			{
				for ( MaterialTextBox ipBox : dnsSearchList )
				{
					/*if ( !ipBox.validate() )
					{
						valid = false;
					}*/
				}
			}
		}
		else
		{
			if ( !addressesSwitch.getValue() )
			{
				if ( !gateWay.validate() )

				{
					valid = false;
				}
			}

			if ( !addressesSwitch.getValue() && ipList != null && !ipList.isEmpty() )
			{
				for ( MaterialTextBox ipBox : ipList )
				{
					/*if ( !ipBox.validate() )
					{
						valid = false;
					}*/

				}
			}
			if ( !dnsSwitch.getValue() && dnsList != null && !dnsList.isEmpty() )
			{
				for ( MaterialTextBox ipBox : dnsList )
				{
					/*if ( !ipBox.validate() )
					{
						valid = false;
					}*/

				}
			}
			if ( !dnsSearchDmnsSwitch.getValue() && dnsSearchList != null && !dnsSearchList.isEmpty() )
			{
				for ( MaterialTextBox ipBox : dnsSearchList )
				{
					/*if ( !ipBox.validate() )
					{
						valid = false;
					}*/

				}
			}
		}

		return valid;

	}

	private void addNewDnsSearchBox( String dnsSearchName )
	{
		TextBoxWithDelete dnsSearchBoxPanel = new TextBoxWithDelete( uiComponentsUtil, "Search Domain", true, null, "s11", "s1" );
		dnsSearchList.add( dnsSearchBoxPanel.getInput() );
		dnsSearchBoxPanel.addStyleName( "updateNetworkDnsAdd" );

		if ( dnsSearchName != null )
		{
			dnsSearchBoxPanel.getInput().setText( dnsSearchName );
		}
		dnsSearchBoxPanel.getDeleteIcon().addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				dnsSearchList.remove( dnsSearchBoxPanel.getInput() );
				//dnsBoxPanel.removeFromParent();
			}
		} );
		dnsSearchPanel.add( dnsSearchBoxPanel );
	}

	private void addNewDnsBox( String dnsName )
	{
		TextBoxWithDelete dnsBoxPanel = new TextBoxWithDelete( uiComponentsUtil, "Server", false, ipType, "s11", "s1" );
		dnsList.add( dnsBoxPanel.getInput() );
		dnsBoxPanel.addStyleName( "updateNetworkDnsAdd" );

		if ( dnsName != null )
		{
			dnsBoxPanel.getInput().setText( dnsName );
		}
		dnsBoxPanel.getDeleteIcon().addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				dnsList.remove( dnsBoxPanel.getInput() );
				//dnsSearchBoxPanel.removeFromParent();
			}
		} );

		dnsPanel.add( dnsBoxPanel );
	}

}
