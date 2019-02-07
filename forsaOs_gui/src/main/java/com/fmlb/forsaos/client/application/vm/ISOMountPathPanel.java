package com.fmlb.forsaos.client.application.vm;

import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;

public class ISOMountPathPanel extends Composite
{
	AppResources resources = GWT.create( AppResources.class );

	private MaterialPanel panel;

	private MaterialIcon deleteRecord;
	
	private MaterialIcon editRecord;

	private UIComponentsUtil uiComponentsUtil;

	private String isoFileName;
	
	private MaterialLabel isoFileNameLabel;

	private CreateVMPopup createVMPopup;

	private int index;
	
	private String vmName;

	public ISOMountPathPanel( UIComponentsUtil uiComponentsUtil, CreateVMPopup createVMPopup, int index, String vmName, String isoFileName )
	{
		super();
		this.uiComponentsUtil = uiComponentsUtil;
		this.isoFileName = isoFileName;
		this.createVMPopup = createVMPopup;
		this.index = index;
		this.vmName = vmName;
		initialize();
		initWidget( panel );
	}

	private void initialize()
	{
		panel = new MaterialPanel();
		panel.addStyleName( resources.style().isoPathBody() );
		
		isoFileNameLabel = new MaterialLabel( isoFileName );
		//isoFileNameLabel.setFlexWrap( FlexWrap.WRAP );
		//isoFileNameLabel.setWidth( "450px" );
		
		editRecord = new MaterialIcon( IconType.EDIT, Color.BLUE );
		editRecord.addStyleName( resources.style().isoEditBtn() );
		editRecord.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				createVMPopup.editISOPath(index);
			}
		} );
		
		deleteRecord = new MaterialIcon( IconType.DELETE, Color.BLUE );
		deleteRecord.addStyleName( resources.style().isoDeleteBtn() );
		deleteRecord.addClickHandler( new ClickHandler()
		{
			@Override
			public void onClick( ClickEvent event )
			{
				createVMPopup.deleteISOPanel( index );
			}
		} );
		panel.add( isoFileNameLabel );
		panel.add( editRecord );
		panel.add( deleteRecord );
	}

	public AppResources getResources()
	{
		return resources;
	}

	public void setResources( AppResources resources )
	{
		this.resources = resources;
	}

	public String getLabel()
	{
		return isoFileName;
	}

	public void setLabel( String label )
	{
		this.isoFileName = label;
		isoFileNameLabel.setText( label );
	}

	public void setIndex( int index )
	{
		this.index = index;
	}
	
	public int getIndex()
	{
		return index;
	}
}
