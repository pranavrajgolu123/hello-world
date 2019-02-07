package com.fmlb.forsaos.client.application.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.base.validator.Validator;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.html.Option;

public class SizeBox extends Composite
{
	AppResources resources = GWT.create( AppResources.class );

	private MaterialPanel panel;

	private MaterialDoubleBox sizeBox;

	private String label;

	private String placeHolder;

	private String colSpec;

	private String rowColSpec;

	private double availableSizeinBytes;

	MaterialComboBox< ? > memoryUnitComboBox;

	private int multiplicationFactor = 1;

	private Validator<Double> sizeValidator;

	public SizeBox( String label, String placeHolder, String rowColSpec, String colSpec, double availableSizeinBytes )
	{
		this.label = label;
		this.placeHolder = placeHolder;
		this.rowColSpec = rowColSpec;
		this.colSpec = colSpec;
		this.availableSizeinBytes = availableSizeinBytes;
		initializeSizeValidator();
		initializeSizeBox( label, placeHolder, rowColSpec, colSpec );
	}

	private void initializeSizeBox( String label, String placeHolder, String rowColSpec, String colSpec )
	{
		panel = new MaterialPanel();
		panel.setGrid( rowColSpec );
		sizeBox = new MaterialDoubleBox();
		sizeBox.setReturnBlankAsNull( true );
		sizeBox.setLabel( label );
		sizeBox.setTitle( label );
		sizeBox.setPlaceholder( placeHolder );
		sizeBox.setPadding( 0 );
		sizeBox.setGrid( "s8" );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			sizeBox.setGrid( colSpec );
		}

		sizeBox.setMin( "0.0" );
		sizeBox.addStyleName( resources.style().form_div() );

		memoryUnitComboBox = createMemorySizeTypeDropDown( "", "s4" );
		memoryUnitComboBox.setHideSearch( true );
		panel.add( sizeBox );
		panel.add( memoryUnitComboBox );
		validation();
		initWidget( panel );

	}

	private void initializeSizeValidator()
	{
		sizeValidator = new Validator<Double>()
		{
			@Override
			public List<EditorError> validate( Editor<Double> editor, Double value )
			{
				EditorError editorError = new EditorError()
				{

					@Override
					public void setConsumed( boolean consumed )
					{

					}

					@Override
					public boolean isConsumed()
					{
						return false;
					}

					@Override
					public Object getValue()
					{
						return null;
					}

					@Override
					public Object getUserData()
					{
						return null;
					}

					@Override
					public String getPath()
					{
						return null;
					}

					@Override
					public String getMessage()
					{
						return ApplicationConstants.NOT_ENOUGH_SPACE;
					}

					@Override
					public Editor< ? > getEditor()
					{
						return null;
					}

					@Override
					public String getAbsolutePath()
					{
						return null;
					}
				};

				List<EditorError> editorErrorsList = new ArrayList<>();
				editorErrorsList.add( editorError );

				if ( isAllocatedAboveAvailable() )
				{
					return editorErrorsList;
				}
				else
				{
					return null;
				}

			}

			@Override
			public int getPriority()
			{
				return 0;
			}
		};
	}

	private void validation()
	{
		sizeBox.addValidator(sizeValidator);
		sizeBox.addValidator( new Validator<Double>()
		{

			@Override
			public List<EditorError> validate( Editor<Double> editor, Double value )
			{
				EditorError editorError = new EditorError()
				{

					@Override
					public void setConsumed( boolean consumed )
					{

					}

					@Override
					public boolean isConsumed()
					{
						return false;
					}

					@Override
					public Object getValue()
					{
						return null;
					}

					@Override
					public Object getUserData()
					{
						return null;
					}

					@Override
					public String getPath()
					{
						return null;
					}

					@Override
					public String getMessage()
					{
						return ApplicationConstants.BLANK_FIELD_ERROR_MSG;
					}

					@Override
					public Editor< ? > getEditor()
					{
						return null;
					}

					@Override
					public String getAbsolutePath()
					{
						return null;
					}
				};

				List<EditorError> editorErrorsList = new ArrayList<>();
				editorErrorsList.add( editorError );

				if ( value == null )
				{
					return editorErrorsList;
				}
				else
				{
					return null;
				}

			}

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}
		} );
		sizeBox.addValueChangeHandler( new ValueChangeHandler<Double>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<Double> event )
			{
				sizeBox.validate();

			}
		} );

		memoryUnitComboBox.addValueChangeHandler( valueChangeEvent -> {
			sizeBox.validate();
		} );
	}

	public MaterialComboBox< ? > createMemorySizeTypeDropDown( String label, String colSpec )
	{
		HashMap<String, String> osSystemFlagOptionMap = new HashMap<String, String>();
		osSystemFlagOptionMap.put( MemorySizeType.MB.getValue(), MemorySizeType.MB.getValue() );
		osSystemFlagOptionMap.put( MemorySizeType.GB.getValue(), MemorySizeType.GB.getValue() );
		osSystemFlagOptionMap.put( MemorySizeType.TB.getValue(), MemorySizeType.TB.getValue() );
		osSystemFlagOptionMap.put( MemorySizeType.PB.getValue(), MemorySizeType.PB.getValue() );
		return getComboBox( osSystemFlagOptionMap, label, colSpec );
	}

	public MaterialComboBox< ? > getComboBox( HashMap<String, String> keyValuePair, String label, String colSpec )
	{
		MaterialComboBox< ? > materialComboBox = new MaterialComboBox<>();
		for ( String key : keyValuePair.keySet() )
		{

			Option option = new Option();
			option.setValue( keyValuePair.get( key ) );
			option.setText( key );
			option.setTitle( key );
			materialComboBox.add( option );
		}
		materialComboBox.setLabel( label );
		materialComboBox.setTitle( label );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			materialComboBox.setGrid( colSpec );
		}
		return materialComboBox;
	}

	public MaterialDoubleBox getSizeBox()
	{
		return sizeBox;
	}

	public void setSizeBox( MaterialDoubleBox sizeBox )
	{
		this.sizeBox = sizeBox;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel( String label )
	{
		this.label = label;
	}

	public String getPlaceHolder()
	{
		return placeHolder;
	}

	public void setPlaceHolder( String placeHolder )
	{
		this.placeHolder = placeHolder;
	}

	public String getColSpec()
	{
		return colSpec;
	}

	public void setColSpec( String colSpec )
	{
		this.colSpec = colSpec;
	}

	public String getRowColSpec()
	{
		return rowColSpec;
	}

	public void setRowColSpec( String rowColSpec )
	{
		this.rowColSpec = rowColSpec;
	}

	public AppResources getResources()
	{
		return resources;
	}

	public void setResources( AppResources resources )
	{
		this.resources = resources;
	}

	public MaterialPanel getPanel()
	{
		return panel;
	}

	public void setPanel( MaterialPanel panel )
	{
		this.panel = panel;
	}

	public double getAvailableSizeinBytes()
	{
		return availableSizeinBytes;
	}

	public void setAvailableSizeinBytes( double availableSizeinBytes )
	{
		this.availableSizeinBytes = availableSizeinBytes;
	}

	public MaterialComboBox< ? > getMemoryUnitComboBox()
	{
		return memoryUnitComboBox;
	}

	public void setMemoryUnitComboBox( MaterialComboBox< ? > memoryUnitComboBox )
	{
		this.memoryUnitComboBox = memoryUnitComboBox;
	}

	private boolean isAllocatedAboveAvailable()
	{
		if ( sizeBox.getValue() == null )
		{
			return false;
		}
		Double size = sizeBox.getValue() * getMultiplicationFactor();
		String memType = ( String ) memoryUnitComboBox.getSingleValue();
		if ( size != null && memType != null && Converter.converToBytes( size, memType ) > availableSizeinBytes )
		{
			return true;
		}
		return false;

	}

	public boolean validate()
	{
		return sizeBox.validate();

	}

	public int getMultiplicationFactor()
	{
		return multiplicationFactor;
	}

	public void setMultiplicationFactor( int multiplicationFactor )
	{
		this.multiplicationFactor = multiplicationFactor;
	}

	public Validator<Double> getSizeValidator()
	{
		return sizeValidator;
	}

	public void setSizeValidator( Validator<Double> sizeValidator )
	{
		this.sizeValidator = sizeValidator;
	}

}
