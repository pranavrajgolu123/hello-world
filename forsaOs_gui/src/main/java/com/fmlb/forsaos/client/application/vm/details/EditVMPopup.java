package com.fmlb.forsaos.client.application.vm.details;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.fmlb.forsaos.client.application.common.ApplicationCallBack;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class EditVMPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialIntegerBox vcpu;

	private MaterialIntegerBox size;

	private MaterialComboBox< ? > memorySizeType;
	
	private MaterialCheckBox advancedsettings;

	private MaterialIntegerBox cpuCores;
	
	private MaterialIntegerBox sockets;
	
	private MaterialIntegerBox threads;
	
	private MaterialPanel advancedSettingsPanel;
	
	private VMModel vmModel;
	
	private long coresOldValue;
	
	private int threadOldValue;
	
	private int socketsOldValue;
	
	AppResources resources = GWT.create( AppResources.class );

	public EditVMPopup( UIComponentsUtil uiComponentsUtil, VMModel vmModel )
	{
		super( "Edit VM", "", "Update", true );
		
		coresOldValue = vmModel.getCores();
		threadOldValue = vmModel.getThreads();
		socketsOldValue = vmModel.getSockets();
		
		
		this.uiComponentsUtil = uiComponentsUtil;
		this.vmModel = vmModel;
		LoggerUtil.log( "initialize edit VM popop" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow updateVMRow = new MaterialRow();

		vcpu = uiComponentsUtil.getIntegerBox( "VCPU", "", "s6" );
		vcpu.setValue( vmModel.getVcpu() );
		size = uiComponentsUtil.getIntegerBox( "Memory", "", "s4" );
		size.setValue( (int)(long)vmModel.getMemory() );
		memorySizeType = uiComponentsUtil.createMemorySizeTypeDropDown( "", "s2" );
		memorySizeType.setHideSearch( true );

		updateVMRow.add( vcpu );
		updateVMRow.add( size );
		updateVMRow.add( memorySizeType );
		
		createAdvancedSettingsCheckbox();
		updateVMRow.add( advancedsettings );
		
		attachAdvancedOptions();
		
		updateVMRow.add( advancedSettingsPanel );
		advancedSettingsPanel.setVisible( false );

		getBodyPanel().add( updateVMRow );
	}

	private void attachAdvancedOptions()
	{
		cpuCores = uiComponentsUtil.getIntegerBox( "CPU Cores", "", "s4" );
		cpuCores.addStyleName( resources.style().padding_left_0() );
		cpuCores.setValue( (int)(long)vmModel.getCores() );
		sockets = uiComponentsUtil.getIntegerBox( "Sockets", "", "s4" );
		sockets.addStyleName( resources.style().padding_left_0() );
		sockets.setValue( vmModel.getSockets() );
		threads = uiComponentsUtil.getIntegerBox( "Threads", "", "s4" );
		threads.addStyleName( resources.style().padding_left_0() );
		threads.setValue( vmModel.getThreads() );
		
		advancedSettingsPanel = new MaterialPanel();

		advancedSettingsPanel.add( cpuCores );
		advancedSettingsPanel.add( sockets );
		advancedSettingsPanel.add( threads );
		advancedSettingsPanel.addStyleName( resources.style().popup_checkbox_panel_body() );
	}


	private void createAdvancedSettingsCheckbox()
	{
		advancedsettings = new MaterialCheckBox( "Advanced Settings" );
		advancedsettings.setGrid( "s12" );
		advancedsettings.addStyleName( resources.style().popup_checkbox_panel() );
		advancedsettings.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				boolean advancedSettingsEnabled = event.getValue();
				if ( advancedSettingsEnabled )
				{
					advancedSettingsPanel.setVisible( true );
				}
				else
				{
					advancedSettingsPanel.setVisible( false );
				}
			}
		} );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				MaterialLoader.loading( true );
				LoggerUtil.log( "Update VM" );

				vmModel.setVcpu( vcpu.getValue() );
				vmModel.setMemory( Long.valueOf( size.getValue() ) );
				vmModel.setMemory_unit( memorySizeType.getSelectedValue().get( 0 ).toString() );
				
				if ( advancedsettings.getValue() )
				{
					vmModel.setCores( cpuCores.getValue() );
					vmModel.setThreads( threads.getValue() );
					vmModel.setSockets( sockets.getValue() );
					updateVMWithAdvancedSettings();
				}
				else
				{
					vmModel.setCores( coresOldValue );
					vmModel.setThreads(threadOldValue);
					vmModel.setSockets(socketsOldValue);
					updateVM();
				}
			}
		} );
	}

	private void updateVM()
	{
		CommonServiceProvider.getCommonService().updateVMDetails( vmModel, "general", new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				close();
				MaterialToast.fireToast( vmModel.getName() + " Updated..!", "rounded" );
			}
			@Override
			public void onFailure( Throwable caught )
			{
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
		
	}

	private void updateVMWithAdvancedSettings()
	{
		CommonServiceProvider.getCommonService().updateVMDetails( vmModel, "advanced", new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				close();
				MaterialToast.fireToast( vmModel.getName() + " Updated..!", "rounded" );
			}
			@Override
			public void onFailure( Throwable caught )
			{
				vmModel.setCores( coresOldValue );
				vmModel.setThreads(threadOldValue);
				vmModel.setSockets(socketsOldValue);
				super.onFailureShowErrorPopup( caught, null );
			}
		} );
	}
}
