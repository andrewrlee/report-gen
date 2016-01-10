package uk.co.optimisticpanda.pdfreport.util;

import java.util.function.Supplier;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.Resources;

public class ClasspathTemplateProvider {

	public static Supplier<String> classPathTemplateProvider(String templateLocation) {
		return () -> {
			try {
				return Resources.toString(ClasspathTemplateProvider.class.getResource(templateLocation).toURI().toURL(), Charsets.UTF_8);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}

}
