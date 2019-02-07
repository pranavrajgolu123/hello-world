package com.fmlb.forsaos.client.application.common;

import java.util.Map;

import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class DataInfoPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private Map<String, String> data;

	public DataInfoPopup( String title, String buttonText, Map<String, String> data, UIComponentsUtil uiComponentsUtil )
	{
		super( title, "", buttonText, true );
		this.data = data;
		this.uiComponentsUtil = uiComponentsUtil;
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow rowContainer = new MaterialRow();
		for ( String key : data.keySet() )
		{
			String value = data.get( key );
			MaterialPanel fieldRow = new MaterialPanel();
			MaterialLabel keyLabel = this.uiComponentsUtil.getLabel( key, "s3" );
			fieldRow.add( keyLabel );
			MaterialLabel valueLabel = this.uiComponentsUtil.getLabel( value, "s9" );
			fieldRow.add( valueLabel );
			rowContainer.add( fieldRow );
			fieldRow.addStyleName( resources.style().disk_info() );
		}
		getBodyPanel().add( rowContainer );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				close();
			}
		} );
	}
}
