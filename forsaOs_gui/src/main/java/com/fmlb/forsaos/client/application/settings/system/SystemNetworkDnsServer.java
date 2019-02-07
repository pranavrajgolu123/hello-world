package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class SystemNetworkDnsServer extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;
	
	   private PlaceManager placeManager;
		
		private CurrentUser currentUser;
		
		private MaterialButton createDnsSearchBtn;
		
		 private MaterialTextBox dnsAddress1 ;
		 
		 private MaterialTextBox dnsAddress2 ;
		 
		 private MaterialTextBox dnsAddress3 ;
		 
		 private int dnsSearchCount = 0;
		 
		 private MaterialSearch dnsSearch;
		 
		  private MaterialNavBar dnsNavBarSearch;
		 
		 AppResources resources = GWT.create( AppResources.class );
		 
	  
		public SystemNetworkDnsServer(UIComponentsUtil uiComponentsUtil,PlaceManager placeManager, CurrentUser currentUser )
		{
			this.uiComponentsUtil = uiComponentsUtil;
			this.placeManager = placeManager;
			this.currentUser = currentUser;
			add(createDnsServerPanel());
		}
		 private void searchDNS() {
		    	
		    	dnsNavBarSearch=new MaterialNavBar();
				//dnsNavBarSearch.setWidth("50");
				dnsNavBarSearch.setVisible(true);
				dnsNavBarSearch.setMarginTop(-100);
				dnsSearch=new MaterialSearch();
				dnsSearch.setPlaceholder("Search DNS Address");
				dnsSearch.setBackgroundColor(Color.BLUE_LIGHTEN_5);
				dnsSearch.setIconColor(Color.BLACK);
				dnsSearch.setActive(true);
				//search.setGrid("50");
				dnsSearch.setShadow(1);
				dnsNavBarSearch.add(dnsSearch);
				
				add(dnsNavBarSearch);
				
			        //  Close Handler
				dnsSearch.addCloseHandler(event -> {
			           // navBar.setVisible(true);
			            dnsNavBarSearch.setVisible(false);
			            dnsSearchCount--;
			            MaterialToast.fireToast("DNS Search Closed");
			        });
				
				dnsSearch.addSearchFinishHandler(event -> {
		            
		            MaterialToast.fireToast("DNS Search Finished");
		        });

		    }
		    
		private MaterialPanel createDnsServerPanel()
		{
			MaterialPanel dnsPanel = new MaterialPanel();
			MaterialLabel dnsLabel = this.uiComponentsUtil.getLabel( "DNS Server", "s12", resources.style().vm_setting_header() );
			dnsPanel.add( dnsLabel );

			MaterialRow firstRow = new MaterialRow();
			firstRow.addStyleName( resources.style().vm_setting_row() );
		
			
			MaterialPanel dnsBoxwrapper = new MaterialPanel();
			dnsBoxwrapper.setGrid( "s6" );
			
	        dnsAddress1 = uiComponentsUtil.getTexBox( "", "Enter DNS Address", "s3" );
	        dnsAddress2 = uiComponentsUtil.getTexBox( "", "Enter DNS Address", "s3" );
	        dnsAddress3 = uiComponentsUtil.getTexBox( "", "Enter DNS Address", "s3" );
			
			
			firstRow.add( dnsBoxwrapper );
			firstRow.add( dnsAddress1);
			firstRow.add( dnsAddress2);
			firstRow.add( dnsAddress3);
			
			
			
		    createDnsSearchBtn = new MaterialButton();
			createDnsSearchBtn.setTitle( "Search DNS" );
			createDnsSearchBtn.setIconType( IconType.SEARCH );
			createDnsSearchBtn.setIconColor(Color.BLUE_GREY_DARKEN_4);
			createDnsSearchBtn.setBackgroundColor( Color.WHITE );
			createDnsSearchBtn.setSize(ButtonSize.MEDIUM);
			createDnsSearchBtn.setFloat(Float.RIGHT);

			
			createDnsSearchBtn.addClickHandler( new ClickHandler()
			{

				@Override
				public void onClick( ClickEvent event )
				{
	               if(dnsSearchCount == 0)
	                 {
					    searchDNS();
					    dnsSearchCount++;
	                 }
				}
			} );
			
			dnsLabel.add(createDnsSearchBtn);
			
			MaterialButton updateDnsbtn = new MaterialButton("Update DNS");
			updateDnsbtn.setWaves(WavesType.DEFAULT);
			updateDnsbtn.setTitle( "Update DNS" );
			updateDnsbtn.setMargin(19);
			//editHostnamebtn.addStyleName( resources.style().padding_left_0() );
		
			updateDnsbtn.addClickHandler( new ClickHandler()
			{
				@Override
				public void onClick( ClickEvent event )
				{
					
				}
			} );

			firstRow.add( updateDnsbtn );
			

			dnsPanel.add( firstRow );
	
			return dnsPanel;
		}
}
