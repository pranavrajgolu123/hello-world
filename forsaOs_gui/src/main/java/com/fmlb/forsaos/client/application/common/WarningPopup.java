package com.fmlb.forsaos.client.application.common;

import java.util.List;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;

public class WarningPopup extends CommonPopUp
{
	private String mainTitle;

	private String subTitle;

	private String buttonText;

	private boolean overlayFl;

	private MaterialLabel warningLabel;

	private List<String> warningMessages;

	public WarningPopup( List<String> warningMessages, String mainTitle, String subTitle, String buttonText, boolean overlayFl )
	{
		super( mainTitle, subTitle, buttonText, overlayFl );
		this.mainTitle = mainTitle;
		this.subTitle = subTitle;
		this.buttonText = buttonText;
		this.overlayFl = overlayFl;
		this.warningMessages = warningMessages;
		initializeWarningPopup();
		buttonAction();
	}

	private void initializeWarningPopup()
	{
		MaterialIcon warningIcon=new MaterialIcon( IconType.WARNING );
		warningIcon.addStyleName( resources.style().popup_error_icon() );
		getToolbar().add( warningIcon );
		getIconMaximize().setVisible( false );
		setToolbarColor( Color.ORANGE_ACCENT_2 );
		MaterialRow warningMessageRow = new MaterialRow();
		if ( warningMessages != null && !warningMessages.isEmpty() )
		{
			for ( String msg : warningMessages )
			{
				MaterialLabel label = new MaterialLabel( msg );
				label.setTitle( msg );
				label.setGrid( "s12" );
				warningMessageRow.add( label );
			}

		}
		getBodyPanel().add( warningMessageRow );
		getButton().setBackgroundColor( Color.ORANGE_ACCENT_2 );
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

	public MaterialLabel getWarningLabel()
	{
		return warningLabel;
	}

	public void setWarningLabel( MaterialLabel warningLabel )
	{
		this.warningLabel = warningLabel;
	}

	public List<String> getwarningMessages()
	{
		return warningMessages;
	}

	public void setWarningMessages( List<String> warningMessages )
	{
		this.warningMessages = warningMessages;
	}

}
