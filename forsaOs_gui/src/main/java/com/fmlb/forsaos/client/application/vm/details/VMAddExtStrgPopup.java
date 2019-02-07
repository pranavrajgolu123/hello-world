package com.fmlb.forsaos.client.application.vm.details;

import java.util.List;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.ComboBoxModel;
import com.fmlb.forsaos.client.application.models.StorageModel;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;

public class VMAddExtStrgPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialComboBox< ? extends ComboBoxModel> addExtndStorage;

	private MaterialLabel vmName;

	private Icommand updateAddStorageCmd;

	private List<StorageModel> storageModels;

	private VMModel vmModel;

	public VMAddExtStrgPopup( UIComponentsUtil uiComponentsUtil, Icommand updateAddStorageCmd, List<StorageModel> storageModels, VMModel vmModel )
	{
		super( "VM - Add External Storage", "", "Add Storage", true );
		this.updateAddStorageCmd = updateAddStorageCmd;
		this.storageModels = storageModels;
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

		MaterialRow addExternalStorageRow = new MaterialRow();

		vmName = uiComponentsUtil.getLabel( "VM Name : " + vmModel.getVmName(), "s6" );

		addExtndStorage = this.uiComponentsUtil.getComboBoxModelDropDown( this.storageModels, "Add External Storage", "s6" );

		addExternalStorageRow.add( vmName );
		addExternalStorageRow.add( addExtndStorage );
		getBodyPanel().add( addExternalStorageRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{

				List<StorageModel> storageModels = ( List<StorageModel> ) addExtndStorage.getValue();
				StorageModel selcted = storageModels.get( 0 );
				if ( !selcted.getName().equalsIgnoreCase( "None" ) )
				{
					/*
					LoggerUtil.log( "selected vm name " + selcted.getName() );
					MaterialLoader.loading( true );
					LoggerUtil.log( "Add NIC to VM" );
					String rtm_name = "testRTM";
					LEMJSONModel[] lemjsonModelArray = new LEMJSONModel[1];
					LEMJSONModel lemjsonModel = new LEMJSONModel( macAddress.getValue(), memorySizeType.getSelectedValue().get( 0 ).toString(), Long.valueOf( size.getValue() ), rtm_name );
					lemjsonModelArray[0] = lemjsonModel;
					CreateLEMandAssisgnToVMmodel createLEMandAssisgnToVMmodel = new CreateLEMandAssisgnToVMmodel( lemjsonModelArray, selcted.getVmName(), selcted.getId() );
					CommonServiceProvider.getCommonService().createLemAndAssisnToVm( createLEMandAssisgnToVMmodel, new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false );
							close();
							MaterialToast.fireToast( lemjsonModel.getName() + " Created..!", "rounded" );
							updateNICDataTableCmd.execute();
						}
					
						@Override
						public void onFailure( Throwable caught )
						{
							MaterialLoader.loading( false );
							if ( ( ( FBException ) caught ).getErrorCode() == ErrorCodes.DUPLICATE_LEMS )
							{
								MaterialDialog invalidUser = new MaterialDialog();
								invalidUser.setType( DialogType.FIXED_FOOTER );
								invalidUser.setDismissible( true );
								invalidUser.setInDuration( 500 );
								invalidUser.setOutDuration( 500 );
								MaterialDialogContent dialogContent = new MaterialDialogContent();
								MaterialTitle title = new MaterialTitle( "Duplicate LEM", "LEM with same name already exists, please use unique name." );
								dialogContent.add( title );
								MaterialDialogFooter footer = new MaterialDialogFooter();
								MaterialButton closeBtn = new MaterialButton( ButtonType.FLAT );
								closeBtn.setText( "Close" );
								closeBtn.addClickHandler( new ClickHandler()
								{
									@Override
									public void onClick( ClickEvent event )
									{
										invalidUser.close();
									}
								} );
								footer.add( closeBtn );
								invalidUser.add( dialogContent );
								invalidUser.add( footer );
								RootPanel.get().add( invalidUser );
								invalidUser.open();
							}
						}
					} );
					*/}
				else
				{
					/*
					
					MaterialLoader.loading( true );
					LoggerUtil.log( "create lem" );
					String rtm_name = "testRTM";
					LEMJSONModel lemjsonModel = new LEMJSONModel( macAddress.getValue(), memorySizeType.getSelectedValue().get( 0 ).toString(), Long.valueOf( size.getValue() ), rtm_name );
					CommonServiceProvider.getCommonService().createLem( lemjsonModel, new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false );
							close();
							MaterialToast.fireToast( lemjsonModel.getName() + " Created..!", "rounded" );
							updateNICDataTableCmd.execute();
						}
					
						@Override
						public void onFailure( Throwable caught )
						{
							MaterialLoader.loading( false );
							if ( ( ( FBException ) caught ).getErrorCode() == ErrorCodes.DUPLICATE_LEMS )
							{
								MaterialDialog invalidUser = new MaterialDialog();
								invalidUser.setType( DialogType.FIXED_FOOTER );
								invalidUser.setDismissible( true );
								invalidUser.setInDuration( 500 );
								invalidUser.setOutDuration( 500 );
								MaterialDialogContent dialogContent = new MaterialDialogContent();
								MaterialTitle title = new MaterialTitle( "Duplicate LEM", "LEM with same name already exists, please use unique name." );
								dialogContent.add( title );
								MaterialDialogFooter footer = new MaterialDialogFooter();
								MaterialButton closeBtn = new MaterialButton( ButtonType.FLAT );
								closeBtn.setText( "Close" );
								closeBtn.addClickHandler( new ClickHandler()
								{
									@Override
									public void onClick( ClickEvent event )
									{
										invalidUser.close();
									}
								} );
								footer.add( closeBtn );
								invalidUser.add( dialogContent );
								invalidUser.add( footer );
								RootPanel.get().add( invalidUser );
								invalidUser.open();
							}
						}
					} );
					*/}
			}
		} );
	}
}
