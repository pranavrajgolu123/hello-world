package com.fmlb.forsaos.client.resources;

import javax.inject.Inject;

public class ResourceLoader
{
	@Inject
	ResourceLoader( AppResources appResources, LoginResources loginResources )
	{
		appResources.normalize().ensureInjected();
		appResources.style().ensureInjected();
		loginResources.style().ensureInjected();
	}
}
