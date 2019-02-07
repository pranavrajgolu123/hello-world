package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class SystemMiscUploadSoftwarePack extends MaterialPanel
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialButton rollBackBtn;

	private RollBackPopup rollBackPopup;

	private MaterialPanel rollbackPanel;

	private MaterialLabel previousVersion = new MaterialLabel();

	private MaterialLabel currentVersion = new MaterialLabel();

	AppResources resources = GWT.create( AppResources.class );

	public SystemMiscUploadSoftwarePack( UIComponentsUtil uiComponentsUtil )
	{
		this.uiComponentsUtil = uiComponentsUtil;
		createFileUploadHeaderPanel();
		add( rollbackPanel );

	}

	private void createRollBackBtnAction()
	{
		rollBackPopup = new RollBackPopup( getUpdateIcommandWithData() );
		rollBackPopup.open();
	}

	private void createFileUploadHeaderPanel()
	{

		rollbackPanel = new MaterialPanel();
		MaterialLabel rollbackLabel = this.uiComponentsUtil.getLabel( "Upload Software Pack", "s12", resources.style().vm_setting_header() );
		rollbackPanel.add( rollbackLabel );

		rollBackBtn = this.uiComponentsUtil.getDataTableCreateDataItemButton( "Rollback" );
		rollBackBtn.setFloat( Float.RIGHT );
		rollBackBtn.addStyleName( resources.style().padding_left_0() );
		rollBackBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				createRollBackBtnAction();

			}
		} );
		rollbackLabel.add( rollBackBtn );

		MaterialRow firstRow = new MaterialRow();
		firstRow.addStyleName( resources.style().vm_setting_row() );

		MaterialPanel rollbackBoxwrapper = new MaterialPanel();
		rollbackBoxwrapper.setGrid( "s6" );

		firstRow.add( rollbackBoxwrapper );

		final FormPanel form = new FormPanel();
		form.setAction( "/myFormHandler" );
		form.setEncoding( FormPanel.ENCODING_MULTIPART );
		form.setMethod( FormPanel.METHOD_POST );

		// Create a FileUpload widget.
		FileUpload upload = new FileUpload();
		upload.setName( "uploadFormElement" );
		firstRow.add( upload );

		MaterialButton updateSoftwarePackbtn = new MaterialButton( "Upload" );
		updateSoftwarePackbtn.setWaves( WavesType.DEFAULT );
		updateSoftwarePackbtn.setTitle( "Update Software Pack" );
		updateSoftwarePackbtn.setIconType( IconType.PUBLISH );
		updateSoftwarePackbtn.setIconPosition( IconPosition.RIGHT );
		firstRow.add( updateSoftwarePackbtn );
		updateSoftwarePackbtn.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				form.submit();
			}
		} );

		form.addSubmitCompleteHandler( new FormPanel.SubmitCompleteHandler()
		{
			public void onSubmitComplete( SubmitCompleteEvent event )
			{
				// When the form submission is successfully completed, this event is
				// fired. Assuming the service returned a response of type text/html,
				// we can get the result text here (see the FormPanel documentation for
				// further explanation).
				//Window.alert(event.getResults());
			}
		} );

		firstRow.add( form );
		firstRow.add( currentVersion );
		firstRow.add( previousVersion );
		getUpdateIcommandWithData().executeWithData( false );
		rollbackPanel.add( firstRow );

	}

	public IcommandWithData getUpdateIcommandWithData()
	{
		IcommandWithData icommandWithData = new IcommandWithData()
		{

			@Override
			public void executeWithData( Object obj )
			{
				// Here fire the API to get the previous tha current version
				previousVersion.setValue( "Previous version:3.1.016124" );
				currentVersion.setValue( "Current version:3.1.016124" );

			}
		};
		return icommandWithData;
	}

}
