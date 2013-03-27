package ar.org.plmaker.aux.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ClassPathLoader {

	private static Logger logger = Logger.getLogger(ClassPathLoader.class);

	public static void addURL(URL u) throws IOException {
		final URLClassLoader sysLoader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		final URL urls[] = sysLoader.getURLs();
		for (final URL url : urls) {
			if (StringUtils.equalsIgnoreCase(url.toString(), u.toString())) {
				logger.debug("URL " + u + " is already in the CLASSPATH");
				return;
			}
		}
		final Class<?> sysclass = URLClassLoader.class;
		try {
			final Method method = sysclass.getDeclaredMethod("addURL",
					new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(sysLoader, new Object[] { u });
			logger.debug("URL " + u + " added to the CLASSPATH");
		} catch (final Throwable t) {
			logger.error(t);
			throw new IOException(
					"Error, could not add URL to system classloader");
		}
	}
}
