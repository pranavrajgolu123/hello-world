package com.fmlb.forsaos.client.application.utility;

import com.fmlb.forsaos.client.application.common.CommonService;
import com.fmlb.forsaos.client.application.common.CommonServiceAsync;
import com.google.gwt.core.client.GWT;

public class CommonServiceProvider
{

	private static CommonServiceAsync commonService;

	public static CommonServiceAsync getCommonService()
	{
		if ( commonService == null )
		{
			commonService = GWT.create( CommonService.class );
		}
		return commonService;
	}
}
