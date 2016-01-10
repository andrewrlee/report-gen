package uk.co.optimisticpanda.pdfreport.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Throwables;

public class MapboxApiKey {

	public static String get() {
		final Properties properties = new Properties();
		try (InputStream stream = MapboxApiKey.class.getResourceAsStream("/run.properties")) {
			properties.load(stream);
			return properties.getProperty("mapbox-api-key");
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}
