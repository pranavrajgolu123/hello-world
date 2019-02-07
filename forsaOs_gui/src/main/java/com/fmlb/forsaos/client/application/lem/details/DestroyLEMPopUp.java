package com.fmlb.forsaos.client.application.lem.details;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class DestroyLEMPopUp extends CommonPopUp
{

	private LEMModel lemModel;

	private UIComponentsUtil uiComponentsUtil;

	private MaterialLabel deleteLEMName;

	private MaterialTextBox passwordBox;

	public DestroyLEMPopUp( LEMModel lemModel, UIComponentsUtil uiComponentsUtil )
	{
		super( "Destroy LEM", "", "Destroy", true );
		this.lemModel = lemModel;
		this.uiComponentsUtil = uiComponentsUtil;
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow destroyLEMRow = new MaterialRow();

		deleteLEMName = this.uiComponentsUtil.getLabel( "Delete LEM named " + this.lemModel.getLemName() + " ?", "s12" );

		passwordBox = this.uiComponentsUtil.getPasswordBox( "", "s12" );
		destroyLEMRow.add( deleteLEMName );
		destroyLEMRow.add( passwordBox );
		getBodyPanel().add( destroyLEMRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				LoggerUtil.log( "destroy lem" );

			}
		} );
	}

}
