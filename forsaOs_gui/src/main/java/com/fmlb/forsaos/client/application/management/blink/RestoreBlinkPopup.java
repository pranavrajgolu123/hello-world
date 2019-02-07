package com.fmlb.forsaos.client.application.management.blink;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.BlinkModel;
import com.fmlb.forsaos.client.application.models.RestoreBlinkModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;

public class RestoreBlinkPopup extends CommonPopUp
{
	private UIComponentsUtil uiComponentsUtil;

	private MaterialLabel restoreBlinkLable;

	private BlinkModel blinkModel;

	public RestoreBlinkPopup( BlinkModel blinkModel, UIComponentsUtil uiComponentsUtil )
	{
		super( "Restore Blink", "", "Restore Blink", true );
		this.uiComponentsUtil = uiComponentsUtil;
		this.blinkModel = blinkModel;

		initialize();
		buttonAction();
	}

	private void initialize()
	{
		MaterialRow destroyBlinkRow = new MaterialRow();

		restoreBlinkLable = this.uiComponentsUtil.getLabel( "Do you want to restore blink " + blinkModel.getName() + "?", "s12" );

		destroyBlinkRow.add( restoreBlinkLable );

		getBodyPanel().add( destroyBlinkRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				restoreBlink();
			}
		} );
	}

	private void restoreBlink()
	{
		MaterialLoader.loading( true, getBodyPanel() );
		LoggerUtil.log( "Restore Blink" );
		RestoreBlinkModel restoreBlink = new RestoreBlinkModel( blinkModel.getName() );
		CommonServiceProvider.getCommonService().restoreBlink( restoreBlink, new ApplicationCallBack<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				close();
				MaterialToast.fireToast( "Sent request to restore " +restoreBlink.getName() + ".", "rounded" );
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
