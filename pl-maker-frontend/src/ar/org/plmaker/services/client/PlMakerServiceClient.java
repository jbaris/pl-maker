package ar.org.plmaker.services.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import roboguice.inject.InjectResource;
import ar.org.plmaker.PlMakerService;
import ar.org.plmaker.activities.R;
import ar.org.plmaker.entities.Song;
import ar.org.plmaker.holder.ContextHolder;
import ar.org.plmaker.utils.AbstractResponseMapper;
import ar.org.plmaker.utils.CollectionKvmSerializable;
import ar.org.plmaker.utils.KSoap2Template;
import ar.org.plmaker.utils.RequestPropertiesSetter;

import com.google.inject.Singleton;

@Singleton
public class PlMakerServiceClient implements PlMakerService {

	@InjectResource(R.string.service_namespace)
	private String namespace;
	@InjectResource(R.string.service_url)
	private String url;
	private String real_url;
	private KSoap2Template template;

	@Override
	public boolean createPlayList(final String playListName) {
		final RequestPropertiesSetter requestProperties = new RequestPropertiesSetter() {

			@Override
			public void setValues(SoapObject request) {
				request.addProperty("playListName", playListName);
			}
		};
		return getTemplate().call("createPlayList",
				"http://impl.service.plmaker.org.ar/createPlayList",
				requestProperties, new AbstractResponseMapper<Boolean>() {
					@Override
					public Boolean mapResponse(Object response) {
						return valueOf(Boolean.class, response);
					}
				});
	}

	@Override
	public void execute(final String command, final Collection<String[]> params) {
		final RequestPropertiesSetter requestProperties = new RequestPropertiesSetter() {

			@Override
			public void setValues(SoapObject request) {
				request.addProperty("command", command);
				for (final String[] strings : params) {
					final CollectionKvmSerializable<String> serializableParams = new CollectionKvmSerializable<String>(
							"params", "item", PropertyInfo.OBJECT_CLASS);
					for (final String string : strings) {
						serializableParams.add(string);
					}
					request.addProperty(serializableParams.buildPropertyInfo());
				}
			}
		};
		getTemplate()
				.call("execute", "http://impl.service.plmaker.org.ar/execute",
						requestProperties);
	}

	@Override
	public char[] exportPlayList(final String playListName,
			final String exportType) {
		return getTemplate().call("exportPlayList", "exportPlayList",
				new RequestPropertiesSetter() {

					@Override
					public void setValues(SoapObject request) {
						request.addProperty("playListName", playListName);
						request.addProperty("exportType", exportType);
					}

				}, new AbstractResponseMapper<char[]>() {
					@Override
					public char[] mapResponse(Object response) {
						return getCharArray(response);
					}
				});
	}

	@Override
	public List<String> getExportTypes() {
		return getTemplate().call("getExportTypes",
				"http://impl.service.plmaker.org.ar/getExportTypes",
				new AbstractResponseMapper<List<String>>() {

					@Override
					public List<String> mapResponse(Object response) {
						List<String> result = null;
						if (response == null) {
							result = Collections.<String> emptyList();
						} else if (response instanceof SoapPrimitive) {
							result = new ArrayList<String>();
							result.add(response.toString());
						} else {
							result = getList(response);
						}
						return result;
					}

				});
	}

	@Override
	public Song getNextUncategorized(final String playListName) {
		final RequestPropertiesSetter requestProperties = new RequestPropertiesSetter() {

			@Override
			public void setValues(SoapObject request) {
				request.addProperty("playListName", playListName);
			}
		};
		return getTemplate().call("getNextUncategorized",
				"http://impl.service.plmaker.org.ar/getNextUncategorized",
				requestProperties, new AbstractResponseMapper<Song>() {

					@Override
					public Song mapResponse(Object response) {
						Song result;
						if (response == null) {
							result = null;
						} else {
							result = new Song();
							result.setFile(getProperty((SoapObject) response,
									"file"));
							result.setId(getProperty((SoapObject) response,
									"id", Long.class));
							result.setName(getProperty((SoapObject) response,
									"name"));
						}
						return result;
					}

				});
	}

	@Override
	public String getPathSeparator() {
		return getTemplate().call("getPathSeparator",
				"http://impl.service.plmaker.org.ar/getPathSeparator",
				new AbstractResponseMapper<String>() {

					@Override
					public String mapResponse(Object response) {
						return response.toString();
					}
				});
	}

	@Override
	public List<String> getPlayLists() {
		return getTemplate().call("getPlayLists",
				"http://impl.service.plmaker.org.ar/getPlayLists",
				new AbstractResponseMapper<List<String>>() {

					@Override
					public List<String> mapResponse(Object response) {
						List<String> result = null;
						if (response == null) {
							result = Collections.<String> emptyList();
						} else if (response instanceof SoapPrimitive) {
							result = new ArrayList<String>();
							result.add(response.toString());
						} else {
							result = getList(response);
						}
						return result;
					}

				});
	}

	@Override
	public List<String> getSongs(final String playListName) {
		final RequestPropertiesSetter requestProperties = new RequestPropertiesSetter() {

			@Override
			public void setValues(SoapObject request) {
				request.addProperty("playListName", playListName);
			}
		};
		return getTemplate().call("getSongs",
				"http://impl.service.plmaker.org.ar/getSongs",
				requestProperties, new AbstractResponseMapper<List<String>>() {

					@Override
					public List<String> mapResponse(Object response) {
						List<String> result = null;
						if (response == null) {
							result = Collections.<String> emptyList();
						} else if (response instanceof SoapPrimitive) {
							result = new ArrayList<String>();
							result.add(response.toString());
						} else {
							result = getList(response);
						}
						return result;
					}

				});
	}

	private KSoap2Template getTemplate() {
		if (template == null) {
			template = new KSoap2Template(getUrl(), namespace);
		}
		return template;
	}

	private String getUrl() {
		if (real_url == null) {
			real_url = String.format(url.replaceAll("\\$", "%s"), ContextHolder
					.getInstance().getServerAddress(), ContextHolder
					.getInstance().getServerPort());
		}
		return real_url;
	}

	@Override
	public void play(final Song song) {
		final RequestPropertiesSetter requestProperties = new RequestPropertiesSetter() {

			@Override
			public void setValues(SoapObject request) {
				final SoapObject soapObject = new SoapObject(null, "song");
				soapObject.addProperty("file", song.getFile());
				soapObject.addProperty("id", song.getId());
				soapObject.addProperty("name", song.getName());
				request.addProperty("song", soapObject);
			}
		};
		getTemplate().call("play", "http://impl.service.plmaker.org.ar/play",
				requestProperties);
	}

	@Override
	public void stop() {
		getTemplate().call("stop", "http://impl.service.plmaker.org.ar/stop");
	}

	@Override
	public boolean undoLastCommand() {
		return getTemplate().call("undoLastCommand",
				"http://impl.service.plmaker.org.ar/undoLastCommand",
				new AbstractResponseMapper<Boolean>() {

					@Override
					public Boolean mapResponse(Object response) {
						return valueOf(Boolean.class, response);
					}

				});
	}

	@Override
	public Float volumeDown() {
		return getTemplate().call("volumeDown",
				"http://impl.service.plmaker.org.ar/volumeDown",
				new AbstractResponseMapper<Float>() {

					@Override
					public Float mapResponse(Object response) {
						return valueOf(Float.class, response);
					}
				});
	}

	@Override
	public Float volumeUp() {
		return getTemplate().call("volumeUp",
				"http://impl.service.plmaker.org.ar/volumeUp",
				new AbstractResponseMapper<Float>() {

					@Override
					public Float mapResponse(Object response) {
						return valueOf(Float.class, response);
					}
				});
	}

}
