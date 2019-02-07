package com.fmlb.forsaos.client.application.lem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class DeleteLEMsPopUp extends CommonPopUp
{

	private List<LEMModel> lemToBeDeleted = new ArrayList<LEMModel>();

	private UIComponentsUtil uiComponentsUtil;

	private MaterialLabel deleteLemName;

	private MaterialLabel lemNames;

	private MaterialTextBox password;

	private IcommandWithData updateLEMDataTableCmd;

	private CurrentUser currentUser;

	public DeleteLEMsPopUp( List<LEMModel> lemToBeDeleted, UIComponentsUtil uiComponentsUtil, IcommandWithData updateLEMDataTableCmd, CurrentUser currentUser )
	{
		super( "Delete LEMs", "", "Delete LEMs", true );
		this.updateLEMDataTableCmd = updateLEMDataTableCmd;
		this.currentUser = currentUser;
		this.lemToBeDeleted = lemToBeDeleted;
		this.uiComponentsUtil = uiComponentsUtil;
		LoggerUtil.log( "delete lems" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow deleteLEMsRow = new MaterialRow();
		deleteLemName = this.uiComponentsUtil.getLabel( "Are you sure you want to destroy these LEMs? (All associated snapshots will be deleted.)", "" );

		StringBuilder lemLabelText = new StringBuilder();
		for ( LEMModel lemModel : this.lemToBeDeleted )
		{
			lemLabelText.append( lemModel.getLemName() );
			lemLabelText.append( "; " );
		}
		lemNames = this.uiComponentsUtil.getLabel( lemLabelText.toString(), "" );

		password = this.uiComponentsUtil.getPasswordBox( "", "" );

		deleteLEMsRow.add( deleteLemName );
		deleteLEMsRow.add( lemNames );
		deleteLEMsRow.add( password );
		getBodyPanel().add( deleteLEMsRow );
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
					errorMessages.add( ErrorMessages.INCORRECT_PASSWORD );
					ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
					errorPopup.open();
					return;
				}
				MaterialLoader.loading( true, getBodyPanel() );
				LoggerUtil.log( "delete lem" );
				List<LEMDeleteJSONModel> lemDeleteJSONModels = new ArrayList<LEMDeleteJSONModel>();
				for ( LEMModel lemModel : lemToBeDeleted )
				{
					LEMDeleteJSONModel lemjsonModel = new LEMDeleteJSONModel( /* lemModel.getId(), */ lemModel.getLemName() );
					lemDeleteJSONModels.add( lemjsonModel );
				}
				CommonServiceProvider.getCommonService().deleteMultipleLem( lemDeleteJSONModels, new ApplicationCallBack<Map<Boolean, List<String>>>()
				{
					@Override
					public void onSuccess( Map<Boolean, List<String>> result )
					{
						MaterialLoader.loading( false, getBodyPanel() );
						close();

						List<String> deleteLemNames = result.get( true );
						List<String> deleteFailedLemMessages = result.get( false );

						for ( String deleledLem : deleteLemNames )
						{
							for ( LEMModel lemModel : lemToBeDeleted )
							{
								if ( deleledLem.equalsIgnoreCase( lemModel.getId() ) )
								{
									MaterialToast.fireToast( lemModel.getLemName() + " Deleted..!", "rounded" );
									break;
								}
							}
						}

						List<String> errorMsgs = new ArrayList<String>();
						for ( String deleteFailedLemMsg : deleteFailedLemMessages )
						{
							errorMsgs.add( deleteFailedLemMsg );
						}
						if ( errorMsgs != null && errorMsgs.size() > 0 )
						{
							ErrorPopup errorPopup = new ErrorPopup( errorMsgs, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
							errorPopup.open();
						}

						updateLEMDataTableCmd.executeWithData( true );
					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, "Delete LEMs failed" );
					}
				} );
			}
		} );
	}

}
