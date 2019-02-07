package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.UpdateHostNameModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class SystemSoftwareHostName extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialPanel hostnamePanel;

	private MaterialTextBox hostname;

	private MaterialButton updateHostnamebtn;

	private MaterialLabel hostnameLabel;

	private IcommandWithData updateHostNameCmd;

	AppResources resources = GWT.create( AppResources.class );

	public SystemSoftwareHostName( UIComponentsUtil uiComponentsUtil, IcommandWithData updateHostNameCmd )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.updateHostNameCmd = updateHostNameCmd;
		createSystemHostnamePanel();
		add( hostnamePanel );

	}

	private void createSystemHostnamePanel()
	{
		hostnamePanel = new MaterialPanel();
		hostnameLabel = this.uiComponentsUtil.getLabel( "HostName", "s12", resources.style().vm_setting_header() );

		hostnamePanel.add( hostnameLabel );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName("system_hostname_row");

		hostname = uiComponentsUtil.getTexBox( "", "Type Hostname", "s6" );
		hostname.addStyleName( "system_hostname_field" );

		firstRow.add( hostname );

		updateHostnamebtn = new MaterialButton( "Update Hostname" );
		updateHostnamebtn.setWaves( WavesType.DEFAULT );
		updateHostnamebtn.setTitle( "Update Hostname" );
		updateHostnamebtn.setMarginTop( 9 );

		updateHostnamebtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				if ( validate() )
				{
					updateHostName();
				}
			}
		} );

		firstRow.add( updateHostnamebtn );
		hostnamePanel.add( firstRow );
	}

	private void updateHostName()
	{

		MaterialLoader.loading( true );
		UpdateHostNameModel updateHostNameModel = new UpdateHostNameModel( hostname.getValue() );
		CommonServiceProvider.getCommonService().updateHostName( updateHostNameModel, new ApplicationCallBack<Boolean>()
		{

			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				if ( result && updateHostNameCmd != null )
				{
					updateHostNameCmd.executeWithData( false );
				}

			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				super.onFailureShowErrorPopup( caught, null );

			}
		} );
	}

	public MaterialLabel getHostnameLabel()
	{
		return hostnameLabel;
	}

	public boolean validate()
	{
		boolean valid = false;
		if ( hostname.validate() )
		{
			valid = true;
		}
		else
		{
			hostname.validate();
		}
		return valid;

	}
}
