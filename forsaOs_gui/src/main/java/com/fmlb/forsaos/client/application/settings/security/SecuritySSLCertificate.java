package com.fmlb.forsaos.client.application.settings.security;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.SecurityCertificateModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class SecuritySSLCertificate extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;
	
	private PlaceManager placeManager;
	
	private CurrentUser currentUser;
	
	private IcommandWithData navigationCmd;
	
	
	private List<SecurityCertificateModel> certificateModel = new ArrayList<SecurityCertificateModel>();

	private MaterialDataTable<SecurityCertificateModel> materialDataTable;

	private ListDataSource<SecurityCertificateModel> dataSource;
	
	private MaterialDataPager<SecurityCertificateModel> pagerGroup;

	private MaterialLabel userGroupLbl;
	

	
	AppResources resources = GWT.create( AppResources.class );
	
	public SecuritySSLCertificate(UIComponentsUtil uiComponentsUtil,PlaceManager placeManager, CurrentUser currentUser )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
		initialization();
	}
	public void initialization()
	{
		add(uploadCertificatePack());
		add(createcertificatePanel());
	
	}
	public MaterialPanel uploadCertificatePack()
	{
		MaterialPanel uploadCertificateSoftwarePanel = new MaterialPanel();
		MaterialLabel uploadLable = this.uiComponentsUtil.getLabel( "Upload Certificate Pack", "s12", resources.style().vm_setting_header() );
		uploadCertificateSoftwarePanel.add( uploadLable );
		
		
		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( resources.style().vm_setting_row() );

		MaterialPanel rollbackBoxwrapper =new MaterialPanel();
		rollbackBoxwrapper.setGrid( "s6" );
		
		firstRow.add( rollbackBoxwrapper);
		
		final FormPanel form = new FormPanel();
	    form.setAction("/myFormHandler");
	    form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);

	   
	    // Create a FileUpload widget.
	    FileUpload upload = new FileUpload();
	    upload.setName("uploadFormElement");
	    firstRow.add(upload);
	    
	    MaterialButton updateSoftwarePackbtn = new MaterialButton("Upload");
		updateSoftwarePackbtn.setWaves(WavesType.DEFAULT);
		updateSoftwarePackbtn.setTitle( "Update Software Pack" );
		updateSoftwarePackbtn.setIconType(IconType.PUBLISH);
		updateSoftwarePackbtn.setIconPosition(IconPosition.RIGHT);
	    firstRow.add(updateSoftwarePackbtn);
		updateSoftwarePackbtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				form.submit();
			}
		} );

	    form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
	        public void onSubmitComplete(SubmitCompleteEvent event) {
	          // When the form submission is successfully completed, this event is
	          // fired. Assuming the service returned a response of type text/html,
	          // we can get the result text here (see the FormPanel documentation for
	          // further explanation).
	          //Window.alert(event.getResults());
	        }
	      });
	     
	    firstRow.add(form);
		
		uploadCertificateSoftwarePanel.add( firstRow );

		return uploadCertificateSoftwarePanel;
	}
	private MaterialPanel createcertificatePanel()
	{
		
		MaterialPanel certificatePanel = new MaterialPanel();
		

		materialDataTable = new MaterialDataTable<SecurityCertificateModel>();
		materialDataTable.addStyleName( "datatablebottompanel" );
		materialDataTable.addAttachHandler( new Handler()
		{
			

			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					materialDataTable.setShadow( 1 );
					materialDataTable.setRowHeight( 10 );
					materialDataTable.setHeight( "calc(20vh - 50px)" );
					materialDataTable.getTableIcon().setVisible( false );
					materialDataTable.getScaffolding().getInfoPanel().clear();
					userGroupLbl = uiComponentsUtil.getDataGridBlackHeaderLabel( "Current Certificates" );
					materialDataTable.getScaffolding().getInfoPanel().add( userGroupLbl );
					//materialDataTable.getScaffolding().getToolPanel().add( searchLink );
					materialDataTable.getStretchIcon().setVisible( false );
					//materialDataTable.getScaffolding().getToolPanel().add( materialDataTable.getColumnMenuIcon() );
				}
			}
		} );

		materialDataTable.addColumn( new TextColumn<SecurityCertificateModel>()
		{
			@Override
			public Comparator< ? super RowComponent<SecurityCertificateModel>> sortComparator()
			{
				return ( o1, o2 ) -> o1.getData().getCertificate().toString().compareToIgnoreCase( o2.getData().getCertificate().toString() );
			}

			@Override
			public String getValue( SecurityCertificateModel object )
			{
				return object.getCertificate().toString();
			}
		}, "File name" );
		
	

		certificatePanel.add( materialDataTable );
		
		materialDataTable.addColumnSortHandler( event -> {
			materialDataTable.getView().refresh();
		} );
	
		
		return certificatePanel;
	}
	
}