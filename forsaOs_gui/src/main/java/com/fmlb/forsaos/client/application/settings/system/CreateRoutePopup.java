package com.fmlb.forsaos.client.application.settings.system;

import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.RoutingModel;
import com.fmlb.forsaos.client.application.models.VMCreateModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.DialogType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogContent;
import gwt.material.design.client.ui.MaterialDialogFooter;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;

public class CreateRoutePopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialTextBox destination;
	
	private MaterialTextBox subnetmask;

	
	private MaterialTextBox gateway;


	private SystemTabs routeDataTable;

	private SystemNetworkRoutingTable systemNetwork;
	
	private IcommandWithData navigationCmd;

	private Icommand icommand;

	public CreateRoutePopup( UIComponentsUtil uiComponentsUtil,Icommand icommand)
	{
		super( "Create Route", "", "Create Route", true );
		this.icommand = icommand;
		this.uiComponentsUtil = uiComponentsUtil;
		LoggerUtil.log( "initialize create Route popop" );
		initialize();
		buttonAction();
	}

	private void initialize()
	{

		MaterialRow createRouteRow = new MaterialRow();

		destination = uiComponentsUtil.getTexBox( "Destination", "", "s6" );

		subnetmask = uiComponentsUtil.getTexBox( "Subnet Mask", "", "s6" );

		gateway = uiComponentsUtil.getTexBox( "Gateway", "", "s6" );


		createRouteRow.add( destination );
		createRouteRow.add( subnetmask );
		createRouteRow.add( gateway );
		getBodyPanel().add( createRouteRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				MaterialLoader.loading( false );
				LoggerUtil.log( "create Route" );
	
				//	createRouteOnly();
				}
		
			
		} );
	}
	
	/*private void createRouteOnly()
	{
		
		RoutingModel createRoutingodel = new RoutingModel( destination.getValue(), subnetmask.getValue(), gateway.getValue());
		CommonServiceProvider.getCommonService().getRoutes( createRoutingodel, new AsyncCallback<Boolean>()
		{
			@Override
			public void onSuccess( Boolean result )
			{
				MaterialLoader.loading( false );
				close();
				
				
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false );
				if ( caught instanceof FBException )
				{
					MaterialDialog materialDialog = uiComponentsUtil.getMaterialDialog( "Error..!", "Request Failed..! " + ( ( FBException ) caught ).getErrorMessage(), "Close" );
					materialDialog.open();
				}
				else
				{
					MaterialDialog materialDialog = uiComponentsUtil.getMaterialDialog( "Error..!", "Request Failed..!", "Close" );
					materialDialog.open();
				}
			}
		} );
	}*/
}
