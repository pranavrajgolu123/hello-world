package com.fmlb.forsaos.client.application.common;

import com.fmlb.forsaos.client.resources.AppResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class ProgressBar extends Composite
{
	MaterialPanel wrapperDiv;

	MaterialRow labelRow;

	MaterialPanel progressMainDiv;

	MaterialPanel progressSubDiv;

	MaterialLabel leftProgressLabel;

	MaterialLabel rightProgressLabel;

	MaterialLabel inlineProgressLabel;

	String leftLabel;

	String rightLabel;

	String inlineLabel;

	String progressPer;

	String height;

	AppResources resources = GWT.create( AppResources.class );

	public ProgressBar( String progressPer, String leftLabel, String rightLabel, String inlineLabel, String height )
	{
		this.progressPer = progressPer;
		this.leftLabel = leftLabel;
		this.rightLabel = rightLabel;
		this.inlineLabel = inlineLabel;
		this.height = height;

		wrapperDiv = new MaterialPanel();
		progressMainDiv = new MaterialPanel();
		progressMainDiv.addStyleName( resources.style().w3LightGrey() );
		progressMainDiv.addStyleName( resources.style().w3RoundLarge() );

		inlineProgressLabel = new MaterialLabel();

		progressSubDiv = new MaterialPanel();
		progressSubDiv.setWidth( progressPer );
		progressSubDiv.setHeight( height );
		progressSubDiv.addStyleName( resources.style().w3Container() );
		progressSubDiv.addStyleName( resources.style().w3Blue() );
		progressSubDiv.addStyleName( resources.style().w3RoundLarge() );

		labelRow = new MaterialRow();
		if ( this.leftLabel != null && this.rightLabel != null )
		{
			leftProgressLabel = new MaterialLabel();
			leftProgressLabel.setText( this.leftLabel );
			leftProgressLabel.setGrid( "s6" );
			rightProgressLabel = new MaterialLabel();
			rightProgressLabel.setText( this.rightLabel );
			rightProgressLabel.setGrid( "s6" );

			labelRow.add( leftProgressLabel );
			labelRow.add( rightProgressLabel );
		}
		else if ( this.leftLabel != null )
		{
			leftProgressLabel = new MaterialLabel();
			leftProgressLabel.setText( this.leftLabel );
			leftProgressLabel.setGrid( "s12" );
			labelRow.add( leftProgressLabel );
		}
		else if ( this.rightLabel != null )
		{
			rightProgressLabel = new MaterialLabel();
			rightProgressLabel.setText( this.rightLabel );
			rightProgressLabel.setGrid( "s12" );
			labelRow.add( rightProgressLabel );
		}
		wrapperDiv.add( labelRow );
		wrapperDiv.add( progressMainDiv );
		progressMainDiv.add( progressSubDiv );
		if ( this.inlineLabel != null )
		{
			inlineProgressLabel.setText( this.inlineLabel );
			progressSubDiv.add( inlineProgressLabel );
		}
		wrapperDiv.addStyleName( resources.style().progressPenal() );
		labelRow.addStyleName("progressPercentageRow");
		initWidget( wrapperDiv );

	}

	public void setData( String progressPer, String leftLabel, String rightLabel, String inlineLabel, String height )
	{
		this.progressPer = progressPer;
		this.leftLabel = leftLabel;
		this.rightLabel = rightLabel;
		this.inlineLabel = inlineLabel;
		this.height = height;
		progressSubDiv.setWidth( progressPer );
		progressSubDiv.setHeight( height );
		if ( this.leftLabel != null && this.rightLabel != null )
		{
			leftProgressLabel.setText( this.leftLabel );
			rightProgressLabel.setText( this.rightLabel );
		}
		else if ( this.leftLabel != null )
		{
			leftProgressLabel.setText( this.leftLabel );
		}
		else if ( this.rightLabel != null )
		{
			rightProgressLabel.setText( this.rightLabel );
		}
		if ( this.inlineLabel != null )
		{
			inlineProgressLabel.setText( this.inlineLabel );
		}

	}
}
