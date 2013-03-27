package ar.org.plmaker.server;

import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import ar.org.plmaker.aux.ProgressMonitor;
import ar.org.plmaker.context.ContextLoaderListener;

public class PlMakerServer {

	private static PlMakerServer instance;

	public static PlMakerServer getInstance() {
		if (instance == null) {
			instance = new PlMakerServer();
		}
		return instance;
	}

	private Server server;

	private PlMakerServer() {

	}

	public void start(Integer port, String musicDir,
			ProgressMonitor progressMonitor) {
		try {
			System.setProperty("musicDir", musicDir);
			server = new Server(port);
			final String WEBAPPDIR = "webapp";		
			final String CONTEXTPATH = "/";
			
			final URL warUrl = getClass().getClassLoader().getResource(WEBAPPDIR);
			final String warUrlString = warUrl.toExternalForm();
			WebAppContext ctx = new WebAppContext(warUrlString, CONTEXTPATH);
			ctx.addEventListener(new ContextLoaderListener(progressMonitor));
			server.setHandler(ctx);

//			final WebAppContext context = new WebAppContext();
//			context.addEventListener(new ContextLoaderListener(progressMonitor));
//			context.setDescriptor("/WEB-INF/web.xml");
//			context.setBaseResource(Resource.newResource(ClassLoader
//					.getSystemResource("webapp")));
//			context.setContextPath("/");
//			context.setParentLoaderPriority(true);
//			server.setHandler(context);
			
			System.setProperty("ehcache.disk.store.dir", "db/cache");
			server.start();
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public void stop() {
		try {
			if (server != null) {
				server.stop();
				server = null;
			}
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
