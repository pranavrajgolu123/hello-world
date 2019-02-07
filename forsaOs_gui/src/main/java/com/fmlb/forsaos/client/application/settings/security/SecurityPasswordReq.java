package com.fmlb.forsaos.client.application.settings.security;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;


public class SecurityPasswordReq extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;
	
	private PlaceManager placeManager;
	
	private CurrentUser currentUser;
	
	private IcommandWithData navigationCmd;
	

	private MaterialCheckBox eventbox1;

	private MaterialCheckBox eventbox2;
	

	
	AppResources resources = GWT.create( AppResources.class );
	
	public SecurityPasswordReq(UIComponentsUtil uiComponentsUtil,PlaceManager placeManager, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		add(createPasswordPanel());
	}
	public MaterialPanel createPasswordPanel()
	{
		MaterialPanel passPanel = new MaterialPanel();
		MaterialLabel passLable = this.uiComponentsUtil.getLabel( "Require Password Confermation", "s12", resources.style().vm_setting_header() );
		passPanel.add( passLable );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( resources.style().vm_setting_row() );
	
		
		MaterialPanel dnsBoxwrapper = new MaterialPanel();
		dnsBoxwrapper.setGrid( "s6" );
		
		
        eventbox1 = new MaterialCheckBox("Delete LEM");
		
		eventbox1.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				
				
				boolean eventbox1 = event.getValue();
				
				if (eventbox1==true)
				{
					
					
				} 
                   
			}

		});
	

		eventbox2 = new MaterialCheckBox("Delete RTM");
		
		eventbox2.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				
				
				boolean eventbox2 = event.getValue();
				
				if (eventbox2==true) 
				{
					
					
				} 
			}

		});
		firstRow.add( dnsBoxwrapper );
		firstRow.add( eventbox1);
		firstRow.add( eventbox2);
		
		MaterialButton updateCommitBtn = new MaterialButton("Commit Changes");
		updateCommitBtn.setWaves(WavesType.DEFAULT);
		updateCommitBtn.setTitle( "Commit Changes" );
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