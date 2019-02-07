package com.fmlb.forsaos.client.application.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fmlb.forsaos.client.application.common.ClientUtil;
import com.fmlb.forsaos.client.application.common.Icommand;
import com.fmlb.forsaos.client.application.common.NetworkDeviceType;
import com.fmlb.forsaos.client.application.models.ComboBoxModel;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.IdModel;
import com.fmlb.forsaos.client.application.models.InterfaceModel;
import com.fmlb.forsaos.client.application.models.LEMModel;
import com.fmlb.forsaos.client.application.models.MemorySizeType;
import com.fmlb.forsaos.client.application.models.VMModel;
import com.fmlb.forsaos.client.resources.AppResources;
import com.fmlb.forsaos.server.application.utility.ApplicationConstants;
import com.fmlb.forsaos.server.application.utility.ErrorMessages;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.RootPanel;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.base.validator.Validator;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.DialogType;
import gwt.material.design.client.constants.FieldType;
import gwt.material.design.client.constants.Flex;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogContent;
import gwt.material.design.client.ui.MaterialDialogFooter;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTabItem;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.html.Option;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;

public class UIComponentsUtil
{

	AppResources resources = GWT.create( AppResources.class );

	public UIComponentsUtil()
	{
		LoggerUtil.log( "creating drop down instance" );
	}

	public MaterialListBox getListBox( HashMap<String, String> keyValuePair )
	{
		MaterialListBox materialListBox = new MaterialListBox();
		for ( String key : keyValuePair.keySet() )
		{

			Option option = new Option();
			option.setValue( keyValuePair.get( key ) );
			option.setText( key );
			option.setTitle( key );
			materialListBox.add( option );
		}
		return materialListBox;
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

	public MaterialComboBox< ? > createMemorySizeTypeDropDown( String label, String colSpec )
	{
		LoggerUtil.log( "reached sta" );
		HashMap<String, String> osSystemFlagOptionMap = new HashMap<String, String>();
		osSystemFlagOptionMap.put( MemorySizeType.MB.getValue(), MemorySizeType.MB.getValue() );
		osSystemFlagOptionMap.put( MemorySizeType.GB.getValue(), MemorySizeType.GB.getValue() );
		//		osSystemFlagOptionMap.put( MemorySizeType.TB.getValue(), MemorySizeType.TB.getValue() );
		//		osSystemFlagOptionMap.put( MemorySizeType.PB.getValue(), MemorySizeType.PB.getValue() );
		MaterialComboBox< ? > osSystemFlag = getComboBox( osSystemFlagOptionMap, label, colSpec );
		return osSystemFlag;
	}

	public MaterialComboBox< ? > createNetworkDeviceTypeDropDown( String label, String colSpec )
	{
		HashMap<String, String> networkDeviceTypeMap = new HashMap<String, String>();
		networkDeviceTypeMap.put( "Bridge", NetworkDeviceType.BRIDGE.getValue() );
		networkDeviceTypeMap.put( "Bond", NetworkDeviceType.BOND.getValue() );
		networkDeviceTypeMap.put( "Ethernet", NetworkDeviceType.ETHERNET.getValue() );
		networkDeviceTypeMap.put( "VLAN", NetworkDeviceType.VLAN.getValue() );
		MaterialComboBox< ? > osSystemFlag = getComboBox( networkDeviceTypeMap, label, colSpec );
		return osSystemFlag;
	}

	public MaterialTextBox getPasswordBox( String label, String colSpec )
	{
		MaterialTextBox password = new MaterialTextBox( "" );
		if ( label != null && !label.isEmpty() )
		{
			password.setLabel( label );
			password.setTitle( label );
		}
		else
		{
			password.setLabel( "Password Required" );
			password.setTitle( "Password Required" );
		}
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			password.setGrid( colSpec );
		}
		password.setFieldType( FieldType.ALIGNED_LABEL );
		password.setType( InputType.PASSWORD );
		password.addStyleName( resources.style().Password_Required() );
		password.addValidator( getTextBoxEmptyValidator() );
		return password;
	}

	public MaterialTextBox getDefaultPasswordBox( String label, String colSpec )
	{
		MaterialTextBox password = new MaterialTextBox();
		if ( label != null && !label.isEmpty() )
		{
			password.setLabel( label );
			password.setTitle( label );
		}
		else
		{
			password.setLabel( "Password Required" );
			password.setTitle( "Password Required" );
		}
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			password.setGrid( colSpec );
		}
		password.setFieldType( FieldType.ALIGNED_LABEL );
		password.setType( InputType.PASSWORD );
		password.addStyleName( resources.style().form_div() );
		password.addStyleName( resources.style().modify_password_input() );
		password.addValidator( getTextBoxEmptyValidator() );
		return password;
	}

	public MaterialIntegerBox getIntegerBox( String label, String placeHolder, String colSpec )
	{
		MaterialIntegerBox size = new MaterialIntegerBox();
		size.setMin( "0" );
		size.setLabel( label );
		size.setTitle( label );
		size.setPlaceholder( placeHolder );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			size.setGrid( colSpec );
		}
		size.addStyleName( resources.style().form_div() );
		size.addValidator( getIntegerBoxEmptyValidator() );
		size.addValidator( getIntegerBoxPositiveValidator() );
		return size;
	}

	public MaterialIntegerBox getIntegerBoxIncludeZero( String label, String placeHolder, String colSpec )
	{
		MaterialIntegerBox size = new MaterialIntegerBox();
		size.setMin( "0" );
		size.setLabel( label );
		size.setTitle( label );
		size.setPlaceholder( placeHolder );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			size.setGrid( colSpec );
		}
		size.addStyleName( resources.style().form_div() );
		size.addValidator( getIntegerBoxEmptyValidator() );
		size.addValidator( getIntegerBoxPositiveValidatorIncludeZero() );
		return size;
	}

	public MaterialIntegerBox getIntegerBoxIncludeNullAndZero( String label, String placeHolder, String colSpec )
	{
		MaterialIntegerBox size = new MaterialIntegerBox();
		size.setMin( "0" );
		size.setLabel( label );
		size.setTitle( label );
		size.setPlaceholder( placeHolder );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			size.setGrid( colSpec );
		}
		size.addStyleName( resources.style().form_div() );
		size.addValidator( getIntegerBoxPositiveValidatorIncludeZero() );
		return size;
	}

	public MaterialTextBox getTexBox( String label, String placeHolder, String colSpec )
	{
		MaterialTextBox size = new MaterialTextBox();
		size.setLabel( label );
		size.setTitle( label );
		size.setPlaceholder( placeHolder );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			size.setGrid( colSpec );
		}
		size.addStyleName( resources.style().form_div() );
		size.addValidator( getTextBoxEmptyValidator() );
		// size.addValidator( getInvalidCharacterValidator() );
		return size;
	}

	public MaterialTextArea getTextArea( String label, String placeHolder, String colSpec )
	{
		MaterialTextArea size = new MaterialTextArea();
		size.setLabel( label );
		size.setTitle( label );
		size.setPlaceholder( placeHolder );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			size.setGrid( colSpec );
		}
		size.addStyleName( resources.style().form_div() );
		size.addValidator( getTextBoxEmptyValidator() );
		return size;
	}

	public MaterialLabel getLabel( String text, String colSpec )
	{
		MaterialLabel label = new MaterialLabel( text );
		label.setTitle( text );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			label.setGrid( colSpec );
		}
		return label;
	}

	public MaterialLabel getLabel( String text, String colSpec, String className )
	{
		MaterialLabel label = new MaterialLabel( text );
		label.setTitle( text );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			label.setGrid( colSpec );
		}
		if ( className != null && !className.isEmpty() )
		{
			label.addStyleName( className );
		}
		return label;
	}

	public MaterialDialog getMaterialDialog( String header, String message, String buttonText, Icommand icmd )
	{
		MaterialDialog matrailDialog = new MaterialDialog();
		matrailDialog.setType( DialogType.FIXED_FOOTER );
		matrailDialog.setDismissible( true );
		matrailDialog.setInDuration( 500 );
		matrailDialog.setOutDuration( 500 );
		matrailDialog.setHeight( "15rem" );
		MaterialDialogContent dialogContent = new MaterialDialogContent();
		MaterialTitle title = new MaterialTitle( header, message );
		dialogContent.add( title );
		MaterialDialogFooter footer = new MaterialDialogFooter();
		MaterialButton closeBtn = new MaterialButton( ButtonType.FLAT );
		closeBtn.setText( buttonText );
		closeBtn.setTitle( buttonText );
		closeBtn.addClickHandler( new ClickHandler()
		{

			@Override
			public void onClick( ClickEvent event )
			{
				if ( icmd != null )
				{
					icmd.execute();
				}
				matrailDialog.close();

			}
		} );
		footer.add( closeBtn );
		matrailDialog.add( dialogContent );
		matrailDialog.add( footer );
		matrailDialog.setTitle( header );
		RootPanel.get().add( matrailDialog );
		return matrailDialog;
	}

	public MaterialComboBox< ? > getAssisgnToVMDropDown( List<VMModel> vmModels, String label, String colSpec )
	{

		MaterialComboBox<VMModel> assignToVMListBox = new MaterialComboBox<>();
		assignToVMListBox.setLabel( label );
		assignToVMListBox.setTitle( label );

		VMModel noneModel = new VMModel();
		noneModel.setVmName( "None" );
		IdModel idModel = new IdModel();
		idModel.set$oid( Random.nextInt() + "" + Random.nextDouble() );
		noneModel.set_id( idModel );
		assignToVMListBox.addItem( noneModel.getVmName(), noneModel );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			assignToVMListBox.setGrid( colSpec );
		}
		for ( VMModel vmModel : vmModels )
		{
			assignToVMListBox.addItem( vmModel.getVmName(), vmModel );
		}

		return assignToVMListBox;
	}

	public MaterialComboBox< ? > getAssisgnToInterfaceDropDown( List<InterfaceModel> interfaceModels, String label, String colSpec )
	{

		MaterialComboBox<InterfaceModel> assignToInterfaceListBox = new MaterialComboBox<>();
		assignToInterfaceListBox.setTitle( label );

		if ( colSpec != null && !colSpec.isEmpty() )
		{
			assignToInterfaceListBox.setGrid( colSpec );
		}
		for ( InterfaceModel interfaceModel : interfaceModels )
		{
			assignToInterfaceListBox.addItem( interfaceModel.getName(), interfaceModel );
		}

		assignToInterfaceListBox.setAllowBlank( false );
		return assignToInterfaceListBox;
	}

	/**
	 * @param lemModels
	 * @param label
	 * @param colSpec
	 * @return
	 */
	public MaterialComboBox< ? > getAssisgnToLEMDropDown( List<LEMModel> lemModels, String label, String colSpec, boolean addNoneFl )
	{

		MaterialComboBox<LEMModel> assignToVMListBox = new MaterialComboBox<>();
		assignToVMListBox.setTitle( label );
		assignToVMListBox.setLabel( label );
		if ( addNoneFl )
		{
			LEMModel noneModel = new LEMModel();
			noneModel.setLemName( "None" );
			noneModel.setId( Random.nextInt() + "" + Random.nextDouble() );
			assignToVMListBox.addItem( noneModel.getLemName(), noneModel );
		}
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			assignToVMListBox.setGrid( colSpec );
		}
		for ( LEMModel lemModel : lemModels )
		{
			assignToVMListBox.addItem( lemModel.getLemName(), lemModel );
		}

		assignToVMListBox.addValidator( new Validator<List<LEMModel>>()
		{

			@Override
			public List<EditorError> validate( Editor<List<LEMModel>> editor, List<LEMModel> value )
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

				if ( value == null || value.isEmpty() || value.get( 0 ).getLemName().equalsIgnoreCase( "none" ) )
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
		} );

		return assignToVMListBox;
	}

	public MaterialComboBox< ? extends ComboBoxModel> getComboBoxModelDropDown( List< ? extends ComboBoxModel> comboBoxeModels, String label, String colSpec )
	{

		MaterialComboBox<ComboBoxModel> comboBoxModelListBox = new MaterialComboBox<>();
		comboBoxModelListBox.setTitle( label );
		comboBoxModelListBox.setLabel( label );

		ComboBoxModel noneModel = new ComboBoxModel()
		{

			@Override
			public String getDisplayName()
			{
				// TODO Auto-generated method stub
				return "None";
			}

			@Override
			public String getId()
			{
				return Random.nextInt() + "" + Random.nextDouble();
			}

		};
		comboBoxModelListBox.addItem( noneModel.getDisplayName(), noneModel );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			comboBoxModelListBox.setGrid( colSpec );
		}
		for ( ComboBoxModel comboBoxeModel : comboBoxeModels )
		{
			comboBoxModelListBox.addItem( comboBoxeModel.getDisplayName(), comboBoxeModel );
		}

		return comboBoxModelListBox;
	}

	public MaterialComboBox< ? extends ComboBoxModel> getComboBoxModelDropDownWithoutNone( List< ? extends ComboBoxModel> comboBoxeModels, String label, String colSpec )
	{

		MaterialComboBox<ComboBoxModel> comboBoxModelListBox = new MaterialComboBox<>();
		comboBoxModelListBox.setTitle( label );
		comboBoxModelListBox.setLabel( label );

		if ( colSpec != null && !colSpec.isEmpty() )
		{
			comboBoxModelListBox.setGrid( colSpec );
		}
		for ( ComboBoxModel comboBoxeModel : comboBoxeModels )
		{
			comboBoxModelListBox.addItem( comboBoxeModel.getDisplayName(), comboBoxeModel );
		}

		return comboBoxModelListBox;
	}

	public MaterialTextBox getDetailNameEdit( String name )
	{
		MaterialTextBox nameEditableField = new MaterialTextBox( "" );
		// nameEditableField.setIconType( IconType.EDIT );
		// nameEditableField.setIconPosition( IconPosition.RIGHT );
		nameEditableField.setText( name );
		nameEditableField.setTitle( name );
		nameEditableField.setReadOnly( true );
		nameEditableField.setToggleReadOnly( true );
		nameEditableField.addStyleName( resources.style().nameEditIcon() );
		return nameEditableField;

	}

	public MaterialTextBox getDetailVmNameEdit( String name )
	{
		MaterialTextBox nameEditableField = new MaterialTextBox( "" );
		// nameEditableField.setIconType( IconType.EDIT );
		// nameEditableField.setIconPosition( IconPosition.RIGHT );
		nameEditableField.setText( name );
		nameEditableField.setTitle( name );
		nameEditableField.setReadOnly( true );
		nameEditableField.setToggleReadOnly( true );
		nameEditableField.addStyleName( resources.style().VM_nameEditIcon() );
		return nameEditableField;

	}

	public void getPaginationOptions( MaterialDataTable< ? > table, MaterialDataPager< ? > pager )
	{
		pager.setLimitOptions( 10, 20, 30, 40, 50 );
		table.setVisibleRange( 1, 10 );
		table.add( pager );
	}

	public void getPaginationOptions2( MaterialDataTable< ? > table, MaterialDataPager< ? > pager )
	{
		pager.setLimitOptions( 20, 40, 60, 80, 100 );
		table.setVisibleRange( 1, 20 );
		table.add( pager );
	}

	public MaterialButton getDataTableCreateDataItemButton( String text )
	{
		MaterialButton createDataItemBtn = new MaterialButton( text );
		createDataItemBtn.setTitle( text );
		createDataItemBtn.addStyleName( resources.style().create_data_item() );
		createDataItemBtn.setIconPosition( IconPosition.RIGHT );
		return createDataItemBtn;

	}

	public MaterialButton getDeleteMultipleDataItemIcon()
	{
		MaterialButton deleteMultipleDataItemIcon = new MaterialButton( "", IconType.DELETE );
		deleteMultipleDataItemIcon.setTitle( "Delete" );
		deleteMultipleDataItemIcon.addStyleName( resources.style().deleteMultipleDataItemIcon() );
		deleteMultipleDataItemIcon.setEnabled( false );
		return deleteMultipleDataItemIcon;
	}

	public MaterialButton getSettingsMultipleItemIcon()
	{
		MaterialButton settingsMultipleDataItemIcon = new MaterialButton( "", IconType.SETTINGS );
		settingsMultipleDataItemIcon.setTitle( "Settings" );
		settingsMultipleDataItemIcon.addStyleName( resources.style().settingsMultipleDataItemIcon() );
		settingsMultipleDataItemIcon.setEnabled( false );
		return settingsMultipleDataItemIcon;
	}

	public MaterialLabel getDataGridHeaderLabel( String text )
	{
		MaterialLabel headerLabel = new MaterialLabel( text );
		headerLabel.setTitle( text );
		headerLabel.addStyleName( resources.style().data_grid_header_txt() );
		return headerLabel;
	}

	public MaterialLabel getDataGridBlackHeaderLabel( String text )
	{
		MaterialLabel headerLabel = new MaterialLabel( text, Color.BLACK );
		headerLabel.setTitle( text );
		headerLabel.addStyleName( resources.style().data_grid_header_txt() );
		return headerLabel;

	}

	public MaterialButton getGridActionButton( String title, IconType icontype )
	{
		MaterialButton gridActionButton = new MaterialButton();
		gridActionButton.setTitle( title );
		gridActionButton.setIconType( icontype );
		gridActionButton.addStyleName( resources.style().grid_action_btn() );
		return gridActionButton;

	}

	public MaterialLink getMaterialLink( String text, MaterialIcon materialIcon )
	{
		MaterialLink materialLink = new MaterialLink( text, materialIcon );
		materialLink.setIconPosition( IconPosition.LEFT );
		return materialLink;
	}

	public MaterialLink getMaterialLink( String text )
	{
		MaterialLink materialLink = new MaterialLink( text );
		return materialLink;
	}

	/*
	 * public MaterialIcon getDeleteSelectedRowItemsIcon(MaterialDataTable<?> table)
	 * { MaterialIcon deleteMultipleItems = new MaterialIcon(IconType.DELETE);
	 * deleteMultipleItems.setEnabled(false); table.addRowSelectHandler(new
	 * RowSelectHandler<?>() { }); return deleteMultipleItems; }
	 */

	public MaterialRadioButton getMaterialRadioButton( String label, String groupName, String colSpec )
	{
		MaterialRadioButton materialRadioButton = new MaterialRadioButton( groupName, label );
		return materialRadioButton;
	}

	public MaterialTabItem getTabItem( Flex flex, WavesType wavesType )
	{
		MaterialTabItem tabItem = new MaterialTabItem();
		tabItem.setFlex( flex );
		tabItem.setWaves( wavesType );
		return tabItem;
	}

	// href should not contain space
	public MaterialLink getTabLink( String header, String href, Color textColor )
	{
		MaterialLink settingsTabLink = new MaterialLink( header );
		settingsTabLink.setTextColor( textColor );
		settingsTabLink.setHref( "#" + href );
		return settingsTabLink;
	}

	public MaterialRow getSizeBox( String label, String placeHolder, String colSpec )
	{
		MaterialRow row = new MaterialRow();
		MaterialDoubleBox sizeBox = new MaterialDoubleBox();
		sizeBox.setLabel( label );
		sizeBox.setTitle( label );
		sizeBox.setPlaceholder( placeHolder );
		sizeBox.setGrid( "s3" );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			sizeBox.setGrid( colSpec );
		}

		sizeBox.setMin( "0.0" );
		MaterialComboBox< ? > memoryUnitComboBox = createMemorySizeTypeDropDown( "", "s3" );
		row.add( sizeBox );
		row.add( memoryUnitComboBox );
		return row;
	}

	public Validator<String> getTextBoxEmptyValidator()
	{
		Validator<String> emptyValidator = new Validator<String>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<String> editor, String value )
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
						return ErrorMessages.CANNOT_BE_EMPTY;
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

				if ( value == null || value.trim().equalsIgnoreCase( "" ) || value.trim().isEmpty() )
				{
					return editorErrorsList;
				}
				else
				{
					return null;
				}

			}
		};
		return emptyValidator;
	}

	public Validator<Integer> getIntegerBoxEmptyValidator()
	{
		Validator<Integer> emptyValidator = new Validator<Integer>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<Integer> editor, Integer value )
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
						return "Cannot be Empty";
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
		};
		return emptyValidator;
	}

	private Validator<Integer> getIntegerBoxPositiveValidator()
	{
		Validator<Integer> emptyValidator = new Validator<Integer>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<Integer> editor, Integer value )
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
						return "Cannot be less than 1";
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

				if ( value != null && value < 1 )
				{
					return editorErrorsList;
				}
				else
				{
					return null;
				}

			}
		};
		return emptyValidator;
	}

	private Validator<Integer> getIntegerBoxPositiveValidatorIncludeZero()
	{
		Validator<Integer> emptyValidator = new Validator<Integer>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<Integer> editor, Integer value )
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
						return "Cannot be less than 0";
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

				if ( value != null && value < 0 )
				{
					return editorErrorsList;
				}
				else
				{
					return null;
				}

			}
		};
		return emptyValidator;
	}

	public boolean checkInputText( String value )
	{
		if ( value != null && !value.isEmpty() && !value.matches( "^[a-zA-Z0-9_-]*$" ) )
		{
			return true;
		}
		return false;
	}

	public Validator<String> getInvalidCharacterValidator()
	{
		Validator<String> emptyValidator = new Validator<String>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<String> editor, String value )
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
						return ErrorMessages.INVALID_TEXT_INPUT;
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

				if ( checkInputText( value ) )
				{
					return editorErrorsList;
				}
				else
				{
					return null;
				}

			}
		};
		return emptyValidator;
	}

	public MaterialDropDown getVMSettingsDropDown( String activatorStr, boolean multiSelection )
	{
		MaterialDropDown materialDropDown = new MaterialDropDown( activatorStr );
		materialDropDown.setStyleName( activatorStr );
		materialDropDown.setConstrainWidth( false );
		materialDropDown.setBelowOrigin( true );

		MaterialLink start = getMaterialLink( "Start", new MaterialIcon( IconType.PLAY_CIRCLE_FILLED ) );
		MaterialLink pause = getMaterialLink( "Pause", new MaterialIcon( IconType.PAUSE ) );
		MaterialLink resume = getMaterialLink( "Resume", new MaterialIcon( IconType.PLAY_CIRCLE_OUTLINE ) );
		MaterialLink shutdown = getMaterialLink( "Shutdown", new MaterialIcon( IconType.STOP ) );
		MaterialLink powerOff = getMaterialLink( "Power Off", new MaterialIcon( IconType.POWER_SETTINGS_NEW ) );

		materialDropDown.add( start );
		materialDropDown.add( pause );
		materialDropDown.add( resume );
		materialDropDown.add( shutdown );
		materialDropDown.add( powerOff );

		if ( multiSelection )
		{
			MaterialLink delete = getMaterialLink( "Delete", new MaterialIcon( IconType.POWER_SETTINGS_NEW ) );
			materialDropDown.add( delete );
		}

		return materialDropDown;
	}

	public void enableDisablePowerIconMenus( VMModel vmModel, MaterialDropDown settingsItemLevelDropDown )
	{
		if ( vmModel.getStatus() == 2 )
		{
			settingsItemLevelDropDown.getWidget( 0 ).setVisible( false );
			settingsItemLevelDropDown.getWidget( 1 ).setVisible( true );
			settingsItemLevelDropDown.getWidget( 2 ).setVisible( false );
			settingsItemLevelDropDown.getWidget( 3 ).setVisible( true );
			settingsItemLevelDropDown.getWidget( 4 ).setVisible( true );

		}
		else if ( vmModel.getStatus() == 3 )
		{
			settingsItemLevelDropDown.getWidget( 0 ).setVisible( false );
			settingsItemLevelDropDown.getWidget( 1 ).setVisible( false );
			settingsItemLevelDropDown.getWidget( 2 ).setVisible( true );
			settingsItemLevelDropDown.getWidget( 3 ).setVisible( false );
			settingsItemLevelDropDown.getWidget( 4 ).setVisible( false );
		}
		else
		{
			settingsItemLevelDropDown.getWidget( 0 ).setVisible( true );
			settingsItemLevelDropDown.getWidget( 1 ).setVisible( false );
			settingsItemLevelDropDown.getWidget( 2 ).setVisible( false );
			settingsItemLevelDropDown.getWidget( 3 ).setVisible( false );
			settingsItemLevelDropDown.getWidget( 4 ).setVisible( false );
		}
	}

	public Validator<String> getEmailValidator()
	{
		Validator<String> emailValidator = new Validator<String>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<String> editor, String value )
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
						return ErrorMessages.INVALID_EMAIL_ID;
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

				if ( checkForValidEmailId( value ) )
				{
					return editorErrorsList;
				}
				else
				{
					return null;
				}

			}
		};
		return emailValidator;
	}

	public boolean checkForValidEmailId( String value )
	{
		if ( value != null && !value.isEmpty() && !value.matches( "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$" ) )
		{
			return true;
		}
		return false;
	}

	public MaterialTextBox getEmailBox( String label, String placeHolder, String colSpec )
	{
		MaterialTextBox emailBox = new MaterialTextBox();
		emailBox.setLabel( label );
		emailBox.setTitle( label );
		emailBox.setPlaceholder( placeHolder );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			emailBox.setGrid( colSpec );
		}
		emailBox.addStyleName( resources.style().form_div() );
		emailBox.addValidator( getTextBoxEmptyValidator() );
		emailBox.addValidator( getEmailValidator() );
		return emailBox;
	}

	public Validator<String> getPasswordValidator( CurrentUser currentUser )
	{
		Validator<String> passwordValidator = new Validator<String>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<String> editor, String value )
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
						return ErrorMessages.INCORRECT_PASSWORD;
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

				if ( value != null && !currentUser.getPassword().equals( value ) )
				{
					return editorErrorsList;
				}
				else
				{
					return null;
				}

			}
		};
		return passwordValidator;
	}

	public MaterialTextBox getIP4TextBox( String label, String placeHolder, String colSpec, boolean isMandatory )
	{
		MaterialTextBox ipV4Box = new MaterialTextBox();
		ipV4Box.setLabel( label );
		ipV4Box.setTitle( label );
		ipV4Box.setPlaceholder( placeHolder );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			ipV4Box.setGrid( colSpec );
		}
		ipV4Box.addStyleName( resources.style().form_div() );
		ipV4Box.addValidator( getTextBoxIP4Validator() );
		if ( isMandatory )
		{
			ipV4Box.addValidator( getTextBoxEmptyValidator() );
		}
		ipV4Box.addValueChangeHandler( new ValueChangeHandler<String>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				ipV4Box.validate();

			}
		} );
		return ipV4Box;
	}

	private Validator<String> getTextBoxIP4Validator()
	{
		Validator<String> ipV4Validator = new Validator<String>()
		{

			@Override
			public int getPriority()
			{
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<String> editor, String value )
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
						return ErrorMessages.ENTER_VALID_IPV4_ADDRESS;
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

				if ( value != null && !value.trim().equalsIgnoreCase( "" ) && !value.trim().isEmpty() && !ClientUtil.isValidIP4( value ) )
				{

					return editorErrorsList;

				}
				else
				{
					return null;
				}

			}
		};
		return ipV4Validator;
	}

	private Validator<Integer> getTextBoxIP4SubnetValidator( MaterialTextBox ipBox )
	{
		Validator<Integer> ipV4SubnetValidator = new Validator<Integer>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<Integer> editor, Integer value )
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
						return ErrorMessages.ENTER_VALID_IPV4_SUBNET;
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

				if ( value != null && !ClientUtil.isValidIP4Subnet( value, ipBox ) )
				{

					return editorErrorsList;

				}
				else
				{
					return null;
				}

			}

		};

		return ipV4SubnetValidator;
	}

	public MaterialIntegerBox getIP4SubnetBox( String label, String placeHolder, String colSpec, boolean isMandatory, MaterialTextBox ipBox )
	{
		MaterialIntegerBox ipV4SubnetBox = new MaterialIntegerBox();
		ipV4SubnetBox.setLabel( label );
		ipV4SubnetBox.setTitle( label );
		ipV4SubnetBox.setPlaceholder( placeHolder );
		ipV4SubnetBox.setMin( "8" );
		ipV4SubnetBox.setMax( "31" );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			ipV4SubnetBox.setGrid( colSpec );
		}
		ipV4SubnetBox.addStyleName( resources.style().form_div() );
		LoggerUtil.log( "last IP at 890 uicomponentutirl " + ipBox.getValue() );
		ipV4SubnetBox.addValidator( getTextBoxIP4SubnetValidator( ipBox ) );
		if ( isMandatory )
		{
			ipV4SubnetBox.setAllowBlank( false );
		}
		ipV4SubnetBox.addValueChangeHandler( new ValueChangeHandler<Integer>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<Integer> event )
			{
				ipV4SubnetBox.validate();

			}
		} );
		return ipV4SubnetBox;
	}

	public MaterialTextBox getIP6TextBox( String label, String placeHolder, String colSpec, boolean isMandatory )
	{
		MaterialTextBox ipV6Box = new MaterialTextBox();
		ipV6Box.setLabel( label );
		ipV6Box.setTitle( label );
		ipV6Box.setPlaceholder( placeHolder );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			ipV6Box.setGrid( colSpec );
		}
		ipV6Box.addStyleName( resources.style().form_div() );
		ipV6Box.addValidator( getTextBoxIP6Validator() );
		if ( isMandatory )
		{
			ipV6Box.addValidator( getTextBoxEmptyValidator() );
		}
		ipV6Box.addValueChangeHandler( new ValueChangeHandler<String>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				ipV6Box.validate();

			}
		} );
		return ipV6Box;
	}

	private Validator<String> getTextBoxIP6Validator()
	{
		Validator<String> ipV6Validator = new Validator<String>()
		{

			@Override
			public int getPriority()
			{
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<String> editor, String value )
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
						return ErrorMessages.ENTER_VALID_IPV6_ADDRESS;
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

				if ( value != null && !value.trim().equalsIgnoreCase( "" ) && !value.trim().isEmpty() && !ClientUtil.isValidIP6( value ) )
				{

					return editorErrorsList;

				}
				else
				{
					return null;
				}

			}
		};
		return ipV6Validator;
	}

	private Validator<Integer> getTextBoxIP6SubnetValidator( MaterialTextBox ipBox )
	{
		Validator<Integer> ipV6SubnetValidator = new Validator<Integer>()
		{

			@Override
			public int getPriority()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<EditorError> validate( Editor<Integer> editor, Integer value )
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
						return ErrorMessages.ENTER_VALID_IPV4_SUBNET;
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

				if ( value != null && !ClientUtil.isValidIP4Subnet( value, ipBox ) )
				{

					return editorErrorsList;

				}
				else
				{
					return null;
				}

			}

		};

		return ipV6SubnetValidator;
	}

	public MaterialIntegerBox getIP6SubnetBox( String label, String placeHolder, String colSpec, boolean isMandatory, MaterialTextBox ipBox )
	{
		MaterialIntegerBox ipV6SubnetBox = new MaterialIntegerBox();
		ipV6SubnetBox.setLabel( label );
		ipV6SubnetBox.setTitle( label );
		ipV6SubnetBox.setPlaceholder( placeHolder );
		ipV6SubnetBox.setMin( "8" );
		ipV6SubnetBox.setMax( "31" );
		if ( colSpec != null && !colSpec.isEmpty() )
		{
			ipV6SubnetBox.setGrid( colSpec );
		}
		ipV6SubnetBox.addStyleName( resources.style().form_div() );
		LoggerUtil.log( "last IP at 890 uicomponentutirl " + ipBox.getValue() );
		//ipV6SubnetBox.addValidator( getTextBoxIP4SubnetValidator( ipBox ) );
		if ( isMandatory )
		{
			ipV6SubnetBox.setAllowBlank( false );
		}
		ipV6SubnetBox.addValueChangeHandler( new ValueChangeHandler<Integer>()
		{

			@Override
			public void onValueChange( ValueChangeEvent<Integer> event )
			{
				ipV6SubnetBox.validate();

			}
		} );
		return ipV6SubnetBox;
	}

	public MaterialLabel getErrorLabel( String errorMessage, String colSpec )
	{
		MaterialLabel errorLabel = new MaterialLabel( errorMessage );
		errorLabel.addStyleName( resources.style().rtm_error_label() );
		errorLabel.setGrid( colSpec );
		errorLabel.setVisible( false );
		return errorLabel;
	}

	public void addTextBoxValidator( MaterialTextBox textBox )
	{
		textBox.addValidator( getInvalidCharacterValidator() );
		textBox.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				textBox.validate();
			}
		} );
		textBox.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				textBox.validate();
			}
		} );
	}

	public void addEmptyTextValidation( MaterialTextBox textBox )
	{
		textBox.addValidator( getTextBoxEmptyValidator() );
		textBox.addKeyUpHandler( new KeyUpHandler()
		{
			@Override
			public void onKeyUp( KeyUpEvent event )
			{
				textBox.validate();
			}
		} );
		textBox.addValueChangeHandler( new ValueChangeHandler<String>()
		{
			@Override
			public void onValueChange( ValueChangeEvent<String> event )
			{
				textBox.validate();
			}
		} );
	}

	public MaterialCollectionItem getMaterialCollectionItem( String colItemRowSpec, String colItemLbl, String colSecLbl )
	{
		MaterialCollectionItem collectionItem = new MaterialCollectionItem();
		collectionItem.setGrid( colItemRowSpec );
		collectionItem.add( getLabel( colItemLbl, "", resources.style().displayInline() ) );
		MaterialCollectionSecondary collectionSecondary = new MaterialCollectionSecondary();
		collectionSecondary.add( getLabel( colSecLbl, "", resources.style().displayInline() ) );
		collectionItem.add( collectionSecondary );
		return collectionItem;
	}

	public MaterialDatePicker getDatePicker( String placeHolder )
	{
		MaterialDatePicker datePicker = new MaterialDatePicker( placeHolder );
		datePicker.setAutoClose( true );
		datePicker.setFilterStyle( "dpMobile" );
		datePicker.setDetectOrientation( true );
		return datePicker;
	}
}
