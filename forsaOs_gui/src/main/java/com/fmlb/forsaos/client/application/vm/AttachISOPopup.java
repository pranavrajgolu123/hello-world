package com.fmlb.forsaos.client.application.vm;

import java.util.ArrayList;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ApplicationCallBack;
import com.fmlb.forsaos.client.application.common.CommonPopUp;
import com.fmlb.forsaos.client.application.common.ErrorPopup;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.application.utility.CommonServiceProvider;
import com.fmlb.forsaos.client.application.utility.LoggerUtil;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.shared.application.exceptions.FBException;
import com.fmlb.forsaos.shared.application.utility.ErrorCodes;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;

public class AttachISOPopup extends CommonPopUp
{
	private static final String isoDirPath = "/var/local/repos/iso";

	private MaterialPanel isoPanel;

	private JavaScriptObject browser;

	private AttachISOPopup attachISOPopup;

	private CreateVMPopup createVMPopup;

	private boolean isNew;

	private int index;

	private List<ISOMountPathPanel> isoMountPathPanels;

	private VMModel vmModel;

	private boolean fromDetailPage;

	private Icommand updateTableEvent;

	public AttachISOPopup( CreateVMPopup createVMPopup, boolean isNew, int index, List<ISOMountPathPanel> isoMountPathPanels, VMModel vmModel, boolean fromDetailPage, Icommand updateTableEvent )
	{
		super( "Attach ISO Path ", "( Double click to select the file )", "", true );

		this.isNew = isNew;
		this.index = index;
		this.vmModel = vmModel;
		this.updateTableEvent = updateTableEvent;
		this.fromDetailPage = fromDetailPage;
		this.attachISOPopup = this;
		this.createVMPopup = createVMPopup;
		this.isoMountPathPanels = isoMountPathPanels;

		getButton().setVisible( false );

		exportSetISOPath();

		exportGetNewFileTree();

		isoPanel = new MaterialPanel();

		isoPanel.setId( "iso-browser" );
		isoPanel.setGrid( "s12" );
		isoPanel.addStyleName( "iso-browser" );

		MaterialLoader.loading( true, getBodyPanel() );
		isoPanel.addAttachHandler( new Handler()
		{
			@Override
			public void onAttachOrDetach( AttachEvent event )
			{
				if ( event.isAttached() )
				{
					String selectedDirPath = isoDirPath;
					getFileTree( selectedDirPath, true );
				}
				else
				{
					MaterialLoader.loading( false, getBodyPanel() );
					onISOLoad( null, true, browser );
				}
			}
		} );

		addCloseHandler( new CloseHandler<Boolean>()
		{
			@Override
			public void onClose( CloseEvent<Boolean> event )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				getBodyPanel().remove( isoPanel );
				onISOLoad( null, true, browser );
			}
		} );

		getBodyPanel().add( isoPanel );
	}

	private void getFileTree( String selectedDirPath, boolean firstRequest )
	{
		if ( !firstRequest )
		{
			MaterialLoader.loading( true, getBodyPanel() );
		}
		CommonServiceProvider.getCommonService().getFileSystemJSON( selectedDirPath, new ApplicationCallBack<String>()
		{
			@Override
			public void onSuccess( String result )
			{

				MaterialLoader.loading( false, getBodyPanel() );
				if ( firstRequest )
				{
					browser = onISOLoad( result, false, browser );
				}
				else
				{
					updateNewFileTree( result );
				}
			}

			@Override
			public void onFailure( Throwable caught )
			{
				MaterialLoader.loading( false, getBodyPanel() );
				super.onFailureShowErrorPopup( caught, null );
				close();
			}
		} );
	}

	public static native JavaScriptObject onISOLoad( String json, boolean destroyFl, JavaScriptObject browser ) /*-{
		return $wnd.isoBrowser(json, destroyFl, browser);
	}-*/;

	public native void updateNewFileTree( String dir ) /*-{
		$wnd.updateNewFileTree(dir);
	}-*/;

	public void getNewFileTree( String newFile )
	{
		LoggerUtil.log( newFile );
		newFile = newFile.replace( "\\", "/" );
		LoggerUtil.log( newFile );
		getFileTree( isoDirPath + "/" + newFile, false );
	}

	public void setISOPath( String isoPath )
	{
		LoggerUtil.log( "" + isoPath );
		if ( !isoPath.substring( isoPath.length() - 3, isoPath.length() ).equalsIgnoreCase( "iso" ) )
		{
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add( "Please select ISO file." );
			ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
			errorPopup.open();
			return;
		}
		isoPath = isoDirPath + "/" + isoPath.replace( "\\", "/" );
		LoggerUtil.log( "" + isoPath );
		if ( isoMountPathPanels != null && isoMountPathPanels.size() > 0 )
		{
			for ( ISOMountPathPanel isoMountPathPanel : isoMountPathPanels )
			{
				if ( isoPath.equalsIgnoreCase( isoMountPathPanel.getLabel() ) && index != isoMountPathPanel.getIndex() )
				{
					List<String> errorMessages = new ArrayList<String>();
					errorMessages.add( "Please attach unique ISO files." );
					ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
					errorPopup.open();
					return;
				}
			}
		}
		if ( fromDetailPage )
		{
			if ( vmModel.getCdroms() != null && vmModel.getCdroms().size() > 0 )
			{
				for ( String cdrom : vmModel.getCdroms() )
				{
					if ( isoPath.equalsIgnoreCase( cdrom ) )
					{
						List<String> errorMessages = new ArrayList<String>();
						errorMessages.add( "Please attach unique ISO files." );
						ErrorPopup errorPopup = new ErrorPopup( errorMessages, ApplicationConstants.ERROR_POPUP_HEADER, "", ApplicationConstants.CLOSE, true );
						errorPopup.open();
						return;
					}
				}
			}
			MaterialLoader.loading( true, getBodyPanel() );
			CommonServiceProvider.getCommonService().attachOrDetachISO( vmModel, isoPath, true, new ApplicationCallBack<Boolean>()
			{
				@Override
				public void onSuccess( Boolean result )
				{
					MaterialLoader.loading( false, getBodyPanel() );
					if ( result )
					{
						if ( updateTableEvent != null )
						{
							updateTableEvent.execute();
							close();
						}
					}
					else
					{
						super.onFailureShowErrorPopup( new FBException( "Unable to attach ISO to the VM.", ErrorCodes.GENERIC_ERROR ), null );
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
		else
		{
			if ( isNew )
			{
				createVMPopup.setISOPath( isoPath );
			}
			else
			{
				createVMPopup.updateISOPath( isoPath, index );
			}
			close();
		}
	}

	public native void exportSetISOPath() /*-{
		var that = this;
		$wnd.setISOPath = $entry(function(isoPath) {
			that.@com.fmlb.forsaos.client.application.vm.AttachISOPopup::setISOPath(Ljava/lang/String;)(isoPath);
		});
	}-*/;

	public native void exportGetNewFileTree() /*-{
		var that = this;
		$wnd.getNewFileTree = $entry(function(isoPath) {
			that.@com.fmlb.forsaos.client.application.vm.AttachISOPopup::getNewFileTree(Ljava/lang/String;)(isoPath);
		});
	}-*/;
}
