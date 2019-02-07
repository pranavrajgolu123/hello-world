package com.fmlb.forsaos.client.application.management.repo;

import java.util.List;

import com.fmlb.forsaos.client.application.common.IcommandWithData;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.models.REPOModel;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;

import gwt.material.design.client.data.DataView;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.component.CategoryComponent.OrphanCategoryComponent;
import gwt.material.design.client.data.factory.CategoryComponentFactory;
import gwt.material.design.client.ui.table.MaterialDataTable;

public class CustomCategoryFactory extends CategoryComponentFactory
{
	private List<REPOModel> repoModels;
	
	private IcommandWithData icommandWithData;
	
	private UIComponentsUtil uiComponentsUtil;
	
	private MaterialDataTable<REPOModel> materialDataTable;

	private CurrentUser currentUser;

	public CustomCategoryFactory( UIComponentsUtil uiComponentsUtil, List<REPOModel> repoModels, IcommandWithData icommandWithData, MaterialDataTable<REPOModel> materialDataTable, CurrentUser currentUser )
	{
		super();
		this.repoModels = repoModels;
		this.uiComponentsUtil = uiComponentsUtil;
		this.icommandWithData = icommandWithData;
		this.materialDataTable = materialDataTable;
		this.currentUser = currentUser;
	}

	@Override
	public CategoryComponent generate( DataView dataView, String categoryName )
	{
		CategoryComponent category = super.generate( dataView, categoryName );

		if ( !( category instanceof OrphanCategoryComponent ) )
		{
			category = new CustomCategoryComponent( uiComponentsUtil, dataView, categoryName, repoModels, icommandWithData, materialDataTable, currentUser );
		}
		return category;
	}

	public List<REPOModel> getRepoModels()
	{
		return repoModels;
	}

	public void setRepoModels( List<REPOModel> repoModels )
	{
		this.repoModels = repoModels;
	}
}
