package com.fmlb.forsaos.client.application.lem;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.LEMDeleteJSONModel;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.server.application.utility.ErrorMessages;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class DeleteLEMPopUp extends CommonPopUp
{
	private LEMModel lemModelTobeDel;

	private UIComponentsUtil uiComponentsUtil;

	private MaterialLabel deleteLemName;

	private MaterialLabel vmLabel;

	private MaterialTextBox password;

	private IcommandWithData updateLEMDataTableCmd;

	private CurrentUser currentUser;

	public DeleteLEMPopUp( LEMModel lemModel, UIComponentsUtil uiComponentsUtil, IcommandWithData updateLEMDataTableCmd, CurrentUser currentUser )
	{
		super( "Delete LEM", "", "Delete LEM", true );
		this.updateLEMDataTableCmd = updateLEMDataTableCmd;
		this.currentUser = currentUser;
		this.lemModelTobeDel = lemModel;
		this.uiComponentsUtil = uiComponentsUtil;
		LoggerUtil.log( "delete lem popo" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow deleteLEMNameRow = new MaterialRow();
		deleteLemName = this.uiComponentsUtil.getLabel( "Delete LEM named " + lemModelTobeDel.getLemName() + "? (All associated snapshots will be deleted.)", "" );

		vmLabel = this.uiComponentsUtil.getLabel( "It is assigned to the following VM(s): " + lemModelTobeDel.getVMname(), "" );

		password = this.uiComponentsUtil.getPasswordBox( "", "" );

		deleteLEMNameRow.add( deleteLemName );
		deleteLEMNameRow.add( vmLabel );
		deleteLEMNameRow.add( password );
		getBodyPanel().add( deleteLEMNameRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				if ( !password.getValue().equals( currentUser.getPassword() ) )
				{
					List<String> errorMessages = new ArrayList<String>();
					errorMessages.add(ErrorMessages.INCORRECT_PASSWORD);
					ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
					errorPopup.open();
					return;
				}
				MaterialLoader.loading( true, getBodyPanel() );
				LoggerUtil.log( "delete lem" );
				LEMDeleteJSONModel lemjsonModel = new LEMDeleteJSONModel( /* lemModelTobeDel.getId(), */ lemModelTobeDel.getLemName() );
				CommonServiceProvider.getCommonService().deleteLem( lemjsonModel, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, getBodyPanel() );
						close();
						MaterialToast.fireToast( lemjsonModel.getName() + " Deleted..!", "rounded" );
						if ( updateLEMDataTableCmd != null )
						{
							updateLEMDataTableCmd.executeWithData( true );
						}
					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, "Delete LEM failed" );
					}
				} );
			}
		} );
	}
}
