package com.fmlb.forsaos.client.application.common;

import java.util.Date;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.client.base.validator.Validator;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.DatePickerContainer;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialDatePicker.MaterialDatePickerType;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class DatePicker extends Composite
{

	private MaterialPanel wrapperPanel;

	private String colSpec;

	private MaterialDatePicker materialDatePicker;

	private String datePickerLabel;

	private boolean isLabelAlignedFl = false;

	public DatePicker( String label, String colSpec )
	{
		this.datePickerLabel = label;
		this.colSpec = colSpec;
		initializeWidget();
	}

	public DatePicker( String label, String colSpec, boolean isLabelAlignedFl )
	{
		this.datePickerLabel = label;
		this.colSpec = colSpec;
		this.isLabelAlignedFl = isLabelAlignedFl;
		initializeWidget();
	}

	private void initializeWidget()
	{
		wrapperPanel = new MaterialPanel();
		wrapperPanel.setGrid( colSpec );
		if ( isLabelAlignedFl )
		{
			MaterialRow row = new MaterialRow();
			row.setGrid( "s12" );
			MaterialLabel label = new MaterialLabel( datePickerLabel );
			label.setTitle( datePickerLabel );
			label.setGrid( "s4" );
			materialDatePicker = getDatePicker( "s8" );
			row.add( label );
			row.add( materialDatePicker );
			wrapperPanel.add( row );
		}
		else
		{
			MaterialRow row = new MaterialRow();
			row.setGrid( "s12" );
			MaterialLabel label = new MaterialLabel( datePickerLabel );
			label.setTitle( datePickerLabel );
			label.setGrid( "s12" );
			materialDatePicker = getDatePicker( "s12" );
			row.add( label );
			row.add( materialDatePicker );
			wrapperPanel.add( row );
		}

		initWidget( wrapperPanel );
	}

	private MaterialDatePicker getDatePicker( String colSpec )
	{
		MaterialDatePicker materialDatePicker = new MaterialDatePicker();
		materialDatePicker.setGrid( colSpec );
		materialDatePicker.setIconType( IconType.DATE_RANGE );
		materialDatePicker.setIconColor( Color.BLACK );
		materialDatePicker.setIconPosition( IconPosition.RIGHT );

		materialDatePicker.setSelectionType( MaterialDatePickerType.YEAR_MONTH_DAY );
		materialDatePicker.setContainer( DatePickerContainer.SELF );
		materialDatePicker.setAutoClose( true );
		materialDatePicker.setDateMax( new Date() );
		//materialDatePicker.setFormat( "yyyy mm dd" );
		return materialDatePicker;
	}

	public MaterialDatePicker getMaterialDatePicker()
	{
		return materialDatePicker;
	}

}
