package ar.org.plmaker.application;

import java.util.List;

import roboguice.application.RoboApplication;
import ar.org.plmaker.modules.PlMakerAndroidModule;

import com.google.inject.Module;

public class PlMakerApplication extends RoboApplication {

	public static final String PREFS_NAME = "PlMakerPrefs";

	@Override
	protected void addApplicationModules(List<Module> modules) {
		modules.add(new PlMakerAndroidModule());
	}

}
