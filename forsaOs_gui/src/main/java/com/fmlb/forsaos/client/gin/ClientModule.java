package com.fmlb.forsaos.client.gin;

import com.fmlb.forsaos.client.application.ApplicationModule;
import com.fmlb.forsaos.client.application.models.CurrentUser;
import com.fmlb.forsaos.client.application.utility.UIComponentsUtil;
import com.fmlb.forsaos.client.place.NameTokens;
import com.fmlb.forsaos.client.resources.ResourceLoader;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.shared.proxy.RouteTokenFormatter;

public class ClientModule extends AbstractPresenterModule
{
	@Override
	protected void configure()
	{
		// install(new DefaultModule.Builder().build());
		install( new DefaultModule.Builder().tokenFormatter( RouteTokenFormatter.class ).build() );
		install( new ApplicationModule() );

		bind( ResourceLoader.class ).asEagerSingleton();
		bind( CurrentUser.class ).asEagerSingleton();
		bind( UIComponentsUtil.class ).asEagerSingleton();
		// bind(RestCallUtil.class).asEagerSingleton();
		System.out.println( "client module" );
		// DefaultPlaceManager Places
		bindConstant().annotatedWith( DefaultPlace.class ).to( NameTokens.LOGIN );
		bindConstant().annotatedWith( ErrorPlace.class ).to( NameTokens.LOGIN );
		bindConstant().annotatedWith( UnauthorizedPlace.class ).to( NameTokens.LOGIN );
	}
}
