package com.fmlb.forsaos.client.application.vm.details;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class AddLEMPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialComboBox< ? > addLEM;

	private MaterialLabel vmName;

	private MaterialLabel vmName2;

	private MaterialLabel vmAddLem;

	private Icommand updateLEMDataTableCmd;

	private List<LEMModel> lemModels = new ArrayList<LEMModel>();

	private VMModel vmModel;

	AppResources resources = GWT.create( AppResources.class );

	public AddLEMPopup( VMModel vmModel, UIComponentsUtil uiComponentsUtil, Icommand updateLEMDataTableCmd, List<LEMModel> lemModels )
	{
		super( "Add LEM", "", "Add LEM", true );
		this.updateLEMDataTableCmd = updateLEMDataTableCmd;
		this.lemModels = lemModels;
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
		addNICRow.addStyleName( resources.style().modal_vm_name_row() );

		vmName = uiComponentsUtil.getLabel( "VM Name: ", "s2" );

		vmName2 = uiComponentsUtil.getLabel( vmModel.getName(), "s10" );

		MaterialRow addLEMRow = new MaterialRow();

		vmAddLem = uiComponentsUtil.getLabel( "LEM", "s2" );
		vmAddLem.addStyleName( resources.style().modal_vm_add_lem_label() );
		addLEM = this.uiComponentsUtil.getAssisgnToLEMDropDown( this.lemModels, "", "s10", true );
		addLEM.addStyleName( resources.style().modal_vm_add_lem_dropdown() );

		addNICRow.add( vmName );
		addNICRow.add( vmName2 );
		addLEMRow.add( vmAddLem );
		addLEMRow.add( addLEM );
		getBodyPanel().add( addNICRow );
		getBodyPanel().add( addLEMRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				List<LEMModel> lemModels = ( List<LEMModel> ) addLEM.getValue();
				for ( LEMModel lemModel : lemModels )
				{
					if ( lemModel.getLemName().equals( "None" ) )
					{
						MaterialDialog materialDialog = uiComponentsUtil.getMaterialDialog( "Error..!", "Please select valid LEM..!", "Close", null );
						materialDialog.open();
						return;
					}
				}
				if ( !lemModels.isEmpty() )
				{
					MaterialLoader.loading( true, getBodyPanel() );
					CommonServiceProvider.getCommonService().attachLemsToVm( vmModel, lemModels, new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							MaterialToast.fireToast( "Attached LEM Successfully..!", "rounded" );
							if ( updateLEMDataTableCmd != null )
							{
								updateLEMDataTableCmd.execute();
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
			}
		} );
	}
}
