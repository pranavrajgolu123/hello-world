package com.fmlb.forsaos.client.application.management.partition;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.PartitionDeleteModel;
import com.fmlb.forsaos.client.application.models.PartitionModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class DeletePartitionPopup extends CommonPopUp
{

	private UIComponentsUtil uiComponentsUtil;

	private MaterialRow createPartitionRow;

	private IcommandWithData icommand;

	AppResources resources = GWT.create( AppResources.class );

	private PartitionModel partitionModel;

	private MaterialCheckBox deletePart;

	private MaterialLabel deleteSubLabel;

	private MaterialLabel deleteDataConfirm;

	private MaterialTextBox password;

	private CurrentUser currentUser;

	public DeletePartitionPopup( UIComponentsUtil uiComponentsUtil, IcommandWithData icommand, PartitionModel partitionModel, CurrentUser currentUser )
	{
		super( "Delete Partition", "", "Delete Partition", true );
		this.icommand = icommand;
		this.uiComponentsUtil = uiComponentsUtil;
		this.partitionModel = partitionModel;
		this.currentUser = currentUser;
		initialize();
		buttonAction();
	}

	@SuppressWarnings( "unchecked" )
	private void initialize()
	{
		createPartitionRow = new MaterialRow();

		deleteSubLabel = new MaterialLabel( "Do you want to delete " + partitionModel.getName() + "?" );

		deletePart = new MaterialCheckBox( "Erase the data" );
		deletePart.addValueChangeHandler( new ValueChangeHandler<Boolean>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event )
			{
				if ( event.getValue() )
				{
					deleteDataConfirm.setVisible( true );
				}
				else
				{
					deleteDataConfirm.setVisible( false );
				}
			}
		} );

		deleteDataConfirm = new MaterialLabel( "The data on the partition will be erased." );
		deleteDataConfirm.setVisible( false );
		deleteDataConfirm.setFontSize( "0.9em" );
		deleteDataConfirm.setTextColor( Color.RED );

		createPartitionRow.add( deleteSubLabel );
		createPartitionRow.add( deletePart );
		createPartitionRow.add( deleteDataConfirm );

		password = this.uiComponentsUtil.getPasswordBox( "", "" );
		password.setFocus( true );
		password.addValidator( this.uiComponentsUtil.getPasswordValidator( currentUser ) );

		createPartitionRow.add( password );

		getBodyPanel().add( createPartitionRow );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{
			@Override
			public void execute()
			{
				if ( validate() )
				{
					MaterialLoader.loading( true, getBodyPanel() );
					Boolean delete_part = deletePart.getValue();
					PartitionDeleteModel partitionDeleteModel = new PartitionDeleteModel( partitionModel.getName(), delete_part );
					CommonServiceProvider.getCommonService().deletePartition( partitionDeleteModel, new ApplicationCallBack<Boolean>()
					{
						@Override
						public void onSuccess( Boolean result )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							icommand.executeWithData( true );
						}

						@Override
						public void onFailure( Throwable caught )
						{
							MaterialLoader.loading( false, getBodyPanel() );
							close();
							super.onFailureShowErrorPopup( caught, null );
						}
					} );
				}
			}
		} );
	}

	@Override
	public boolean validate()
	{
		boolean valid = false;
		if ( password.validate() )
		{
			valid = true;
		}
		return valid;
	}
}
