package com.fmlb.forsaos.client.application.common;

import java.util.List;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;

public class ErrorPopup extends CommonPopUp
{
	private String mainTitle;

	private String subTitle;

	private String buttonText;

	private boolean overlayFl;

	private MaterialLabel errorLabel;

	private List<String> errorMessages;

	public ErrorPopup( List<String> errorMessages, String mainTitle, String subTitle, String buttonText, boolean overlayFl )
	{
		super( mainTitle, subTitle, buttonText, overlayFl );
		this.mainTitle = mainTitle;
		this.subTitle = subTitle;
		this.buttonText = buttonText;
		this.overlayFl = overlayFl;
		this.errorMessages = errorMessages;
		initializeErrorPopUp();
		buttonAction();
	}

	private void initializeErrorPopUp()
	{
		MaterialIcon errorIcon=new MaterialIcon( IconType.ERROR_OUTLINE );
		errorIcon.addStyleName( resources.style().popup_error_icon() );
		getToolbar().add( errorIcon );
		getIconMaximize().setVisible( false );
		setToolbarColor( Color.RED_ACCENT_2 );
		MaterialRow errorMessageRow = new MaterialRow();
		if ( errorMessages != null && !errorMessages.isEmpty() )
		{
			for ( String msg : errorMessages )
			{
				MaterialLabel label = new MaterialLabel( msg );
				label.setTitle( msg );
				label.setGrid( "s12" );
				errorMessageRow.add( label );
			}

		}
		getBodyPanel().add( errorMessageRow );
		getButton().setBackgroundColor( Color.RED_ACCENT_2 );
	}

	private void buttonAction()
	{
		addButtonClickCommand( new Icommand()
		{

			@Override
			public void execute()
			{
				close();
			}
		} );
	}

	public String getMainTitle()
	{
		return mainTitle;
	}

	public void setMainTitle( String mainTitle )
	{
		this.mainTitle = mainTitle;
	}

	public String getSubTitle()
	{
		return subTitle;
	}

	public void setSubTitle( String subTitle )
	{
		this.subTitle = subTitle;
	}

	public String getButtonText()
	{
		return buttonText;
	}

	public void setButtonText( String buttonText )
	{
		this.buttonText = buttonText;
	}

	public boolean isOverlayFl()
	{
		return overlayFl;
	}

	public void setOverlayFl( boolean overlayFl )
	{
		this.overlayFl = overlayFl;
	}

	public MaterialLabel getErrorLabel()
	{
		return errorLabel;
	}

	public void setErrorLabel( MaterialLabel errorLabel )
	{
		this.errorLabel = errorLabel;
	}

	public List<String> getErrorMessages()
	{
		return errorMessages;
	}

	public void setErrorMessages( List<String> errorMessages )
	{
		this.errorMessages = errorMessages;
	}

}
