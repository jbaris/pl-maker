package ar.org.plmaker.modules;

import roboguice.config.AbstractAndroidModule;
import ar.org.plmaker.PlMakerService;
import ar.org.plmaker.services.client.PlMakerServiceClient;

import com.google.inject.Singleton;

public class PlMakerAndroidModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(PlMakerService.class).to(PlMakerServiceClient.class).in(
				Singleton.class);
	}

}
