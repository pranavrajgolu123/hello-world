package com.fmlb.forsaos.client.application.settings.security;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class SecurityMisc extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;
	
	private PlaceManager placeManager;
	
	private CurrentUser currentUser;
	
	private IcommandWithData navigationCmd;
	
	private MaterialIntegerBox sessiontime;

	
	AppResources resources = GWT.create( AppResources.class );
	
	public SecurityMisc(UIComponentsUtil uiComponentsUtil,PlaceManager placeManager, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		initialize();
	}
	public void initialize() {
		
		add(createsessionRow());
		
	}


	private MaterialPanel createsessionRow() {
		
		
		MaterialPanel passPanel = new MaterialPanel();
		MaterialLabel passLable = this.uiComponentsUtil.getLabel( "Security Settings", "s12", resources.style().vm_setting_header() );
		passPanel.add( passLable );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( resources.style().vm_setting_row() );
	
		
		MaterialPanel dnsBoxwrapper = new MaterialPanel();
		dnsBoxwrapper.setGrid( "s6" );
		
		
		sessiontime = uiComponentsUtil.getIntegerBox("Session timeout    (minutes 0 to disable)", "min", "s3");
		sessiontime.addStyleName(resources.style().displayInline());
	

		
		firstRow.add( dnsBoxwrapper );
		firstRow.add(sessiontime);
		
		
		MaterialButton updateCommitBtn = new MaterialButton("Submit");
		updateCommitBtn.setWaves(WavesType.DEFAULT);
		updateCommitBtn.setTitle( "Submit" );
		updateCommitBtn.setFloat(Float.LEFT);
		updateCommitBtn.setMargin(19);
		//editHostnamebtn.addStyleName( resources.style().padding_left_0() );
	
		updateCommitBtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				
			}
		} );

		firstRow.add( updateCommitBtn );
		

		passPanel.add( firstRow );
		
		
		
		return passPanel;
		
	}
	
}