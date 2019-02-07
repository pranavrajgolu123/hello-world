package com.fmlb.forsaos.client.application.vm.details;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.ComboBoxModel;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.fmlb.forsaos.client.application.common.ApplicationCallBack;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class UnAssisgnLEM extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialComboBox< ? extends ComboBoxModel> addLEM;

	private MaterialLabel removeLEM;

	private MaterialLabel disclaimer;

	private Icommand unassignLEMCmd;

	private LEMModel lemModel;

	private VMModel vmModel;

	public UnAssisgnLEM( UIComponentsUtil uiComponentsUtil, Icommand unassignLEMCmd, VMModel vmModel, LEMModel lemModel )
	{
		super( "Unassign LEM", "", "Delete", true );
		this.unassignLEMCmd = unassignLEMCmd;
		this.lemModel = lemModel;
		this.vmModel = vmModel;
		String subTitle = "";
		setSubTitle( subTitle );
		this.uiComponentsUtil = uiComponentsUtil;
		LoggerUtil.log( "initialize add NIC  popop" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow unAssignLEM = new MaterialRow();

		removeLEM = uiComponentsUtil.getLabel( "Remove LEM " + lemModel.getLemName() + " from VM?", "s12" );

		disclaimer = uiComponentsUtil.getLabel( "This will not destroy the LEM or the data on it.", "s12" );

		unAssignLEM.add( removeLEM );
		unAssignLEM.add( disclaimer );
		getBodyPanel().add( unAssignLEM );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				MaterialLoader.loading( true );
				CommonServiceProvider.getCommonService().detachLemsToVm( vmModel, lemModel, false, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false );
						MaterialToast.fireToast( "Detached LEM Successfully..!", "rounded" );
						close();
						if ( unassignLEMCmd != null )
						{
							unassignLEMCmd.execute();
						}
					}

					@Override
					public void onFailure( Throwable caught )
					{
						super.onFailureShowErrorPopup( caught, null );
					}
				} );
			}
		} );
	}
}
