package com.fmlb.forsaos.client.application.rtm;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.RTMModel;
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

public class DeleteRTMPopup extends CommonPopUp
{
	private RTMModel rtmModelTobeDel;

	private UIComponentsUtil uiComponentsUtil;

	private MaterialLabel deleteRTMName;

	private MaterialLabel warnMessage;

	private MaterialTextBox password;

	private CurrentUser currentUser;

	private Icommand initializeRTMCmd;

	public DeleteRTMPopup( RTMModel rtmModel, UIComponentsUtil uiComponentsUtil, CurrentUser currentUser, Icommand initializeRTMCmd )
	{
		super( "Delete RTM", "", "Delete RTM", true );
		this.currentUser = currentUser;
		this.rtmModelTobeDel = rtmModel;
		this.uiComponentsUtil = uiComponentsUtil;
		this.initializeRTMCmd = initializeRTMCmd;
		LoggerUtil.log( "delete lem popo" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow deleteRTMNameRow = new MaterialRow();
		deleteRTMName = this.uiComponentsUtil.getLabel( "Are you sure you want to delete the RTM?", "s12" );
		warnMessage = this.uiComponentsUtil.getLabel( "This will delete all VMs and LEMs on this Forsa OS (Schedules will be deleted automatically).", "s12" );

		password = this.uiComponentsUtil.getPasswordBox( "", "" );

		deleteRTMNameRow.add( deleteRTMName );
		deleteRTMNameRow.add( warnMessage );
		deleteRTMNameRow.add( password );
		getBodyPanel().add( deleteRTMNameRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				if ( password.validate() )
				{
					if ( !password.getValue().equals( currentUser.getPassword() ) )
					{
						List<String> errorMessages = new ArrayList<String>();
						errorMessages.add( ErrorMessages.INCORRECT_PASSWORD );
						ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
						errorPopup.open();
						return;
					}
					MaterialLoader.loading( true, getBodyPanel() );
					LoggerUtil.log( "delete rtm" );
					/*		CommonServiceProvider.getCommonService().deleteRTM( rtmModelTobeDel, new ApplicationCallBack<Boolean>()
						{
							@Override
							public void onSuccess( Boolean result )
							{
								MaterialLoader.loading( false );
								close();
								initializeRTMCmd.execute();
								MaterialToast.fireToast( rtmModelTobeDel.getName() + " Deleted..!", "rounded" );
							}
					
							@Override
							public void onFailure( Throwable caught )
							{
								super.onFailureShowErrorPopup( caught, null );
							}
						} );*/
					CommonServiceProvider.getCommonService().resetRTM( new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							initializeRTMCmd.execute();
							MaterialToast.fireToast( rtmModelTobeDel.getName() + " Deleted..!", "rounded" );
						}

						@Override
						public void onFailure( Throwable caught )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							super.onFailureShowErrorPopup( caught, null );
						}
					} );
				}
			}
		} );
	}
}
