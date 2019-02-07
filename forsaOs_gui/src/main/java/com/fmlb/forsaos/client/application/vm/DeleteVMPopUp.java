package com.fmlb.forsaos.client.application.vm;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.server.application.utility.ErrorMessages;
import com.google.gwt.core.client.GWT;

import gwt.material.design.client.constants.FlexWrap;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class DeleteVMPopUp extends CommonPopUp
{
	private List<VMModel> vmModels;

	private UIComponentsUtil uiComponentsUtil;

	private MaterialLabel deleteVmName;

	private MaterialCheckBox destroyLems;

	private MaterialTextBox password;

	private IcommandWithData updateVMDataTableCmd;

	private CurrentUser currentUser;

	AppResources resources = GWT.create( AppResources.class );

	public DeleteVMPopUp( List<VMModel> vmModels, UIComponentsUtil uiComponentsUtil, IcommandWithData updateVMDataTableCmd, CurrentUser currentUser )
	{
		super( "Delete VM(s)", "", "Delete VM", true );
		this.updateVMDataTableCmd = updateVMDataTableCmd;
		this.currentUser = currentUser;
		this.vmModels = vmModels;
		this.uiComponentsUtil = uiComponentsUtil;
		LoggerUtil.log( "delete VM popo" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow deleteLEMNameRow = new MaterialRow();
		String deleteText = "Delete VM ? (Schedules will be updated automatically)";
		if ( vmModels.size() > 1 )
		{
			String vmNames = "";
			for ( VMModel vmModel : vmModels )
			{
				vmNames = vmNames + vmModel.getName() + ", ";
			}
			deleteText = "Delete VMs named " + vmNames.substring( 0, ( vmNames.length() - 2 ) ) + " ? (Schedules will be updated automatically)";
		}
		else
		{
			deleteText = "Delete VM named " + vmModels.get( 0 ).getName() + " ? (Schedules will be updated automatically)";
		}
		deleteVmName = this.uiComponentsUtil.getLabel( deleteText, "" );
		deleteVmName.setFlexWrap( FlexWrap.WRAP );

		destroyLems = new MaterialCheckBox( "Delete Lems" );
		destroyLems.addStyleName( resources.style().margin_top_10() );

		password = this.uiComponentsUtil.getPasswordBox( "", "" );
		deleteLEMNameRow.add( deleteVmName );
		deleteLEMNameRow.add( destroyLems );
		deleteLEMNameRow.add( password );

		getBodyPanel().add( deleteLEMNameRow );
	}

	private int counter = 0;

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				if ( destroyLems.getValue() && !password.getValue().equals( currentUser.getPassword() ) )
				{
					List<String> errorMessages = new ArrayList<String>();
					errorMessages.add(ErrorMessages.INCORRECT_PASSWORD);
					ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
					errorPopup.open();
					return;
				}
				MaterialLoader.loading( true );
				LoggerUtil.log( "delete vm" );
				int vmCounts = vmModels.size();
				counter = 0;
				for ( VMModel vmModel : vmModels )
				{
					CommonServiceProvider.getCommonService().deleteVM( vmModel, destroyLems.getValue(), new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{
							MaterialToast.fireToast( vmModel.getName() + " Deleted..!", "rounded" );
							if ( updateVMDataTableCmd != null && ++counter == vmCounts )
							{
								MaterialLoader.loading( false );
								close();
								updateVMDataTableCmd.executeWithData( true );
							}
						}

						@Override
						public void onFailure( Throwable caught )
						{
							if ( vmCounts == 1 )
							{
								super.onFailureShowErrorPopup( caught, "Unable to Delete VM " + vmModel.getName() );
							}
							else
							{
								MaterialToast.fireToast( "Unable to Delete VM " + vmModel.getName(), "rounded" );
							}
							if ( updateVMDataTableCmd != null && ++counter == vmCounts )
							{
								MaterialLoader.loading( false );
								close();
								updateVMDataTableCmd.executeWithData( true );
							}
						}
					} );
				}
			}
		} );
	}
}
