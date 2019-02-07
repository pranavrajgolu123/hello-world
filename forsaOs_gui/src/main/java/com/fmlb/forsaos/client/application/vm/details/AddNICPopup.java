package com.fmlb.forsaos.client.application.vm.details;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.BridgeJSONModel;
import com.fmlb.forsaos.client.application.models.ComboBoxModel;
import com.fmlb.forsaos.client.application.models.LVNetCreateRequestModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.google.gwt.user.client.Random;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class AddNICPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialComboBox< ? extends ComboBoxModel> assignToBridge;

	private MaterialTextBox macAddress;

	private Icommand updateNICDataTableCmd;

	private List<BridgeJSONModel> bridgeModels = new ArrayList<BridgeJSONModel>();

	private VMModel vmModel;

	public AddNICPopup( UIComponentsUtil uiComponentsUtil, Icommand updateNICDataTableCmd, List<BridgeJSONModel> bridgeJSONModels, VMModel vmModel )
	{
		super( "Add NIC", "", "Add NIC", true );
		this.updateNICDataTableCmd = updateNICDataTableCmd;
		this.bridgeModels = bridgeJSONModels;
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

		MaterialRow addNICRow = new MaterialRow();

		macAddress = uiComponentsUtil.getTexBox( "MAC(Optional)", "", "s6" );

		assignToBridge = this.uiComponentsUtil.getComboBoxModelDropDownWithoutNone( this.bridgeModels, "Bridge", "s6" );
		assignToBridge.setHideSearch( true );

		addNICRow.add( assignToBridge );
		addNICRow.add( macAddress );
		getBodyPanel().add( addNICRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				MaterialLoader.loading( true, getBodyPanel() );
				BridgeJSONModel bridgeModel = ( BridgeJSONModel ) assignToBridge.getSingleValue();
				int nextInt = Random.nextInt( 1000 );
				LoggerUtil.log( "nextInt :: "+nextInt );
				LVNetCreateRequestModel lvNetCreateRequestModel = new LVNetCreateRequestModel( vmModel.getName() + "_" + "VNET" + nextInt, "lbridge", bridgeModel.getName() );
				CommonServiceProvider.getCommonService().attachNetToVm( vmModel, lvNetCreateRequestModel, new ApplicationCallBack<Boolean>()
				{
					@Override
					public void onSuccess( Boolean result )
					{
						MaterialLoader.loading( false, getBodyPanel() );
						close();
						MaterialToast.fireToast( bridgeModel.getName() + " attached..!", "rounded" );
						if ( updateNICDataTableCmd != null )
						{
							updateNICDataTableCmd.execute();
						}
					}

					@Override
					public void onFailure( Throwable caught )
					{
						MaterialLoader.loading( false, getBodyPanel() );
						super.onFailureShowErrorPopup( caught, null );
					}
				} );

			}
		} );
	}
}
