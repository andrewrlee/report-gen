package uk.co.optimisticpanda.pdfreport;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.base.Throwables;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateBuilder<CONTEXT> {

	private final Map<String, TemplateContext> providers = new HashMap<>();

	private TemplateBuilder() {
	}

	public static <CONTEXT> TemplateBuilder<CONTEXT> withTemplate(String templateType, Supplier<String> templateProvider, TemplateDataProvider<CONTEXT> provider) {
		return new TemplateBuilder<CONTEXT>().and(templateType, templateProvider, provider);
	}

	@SuppressWarnings("unchecked")
	public TemplateBuilder<CONTEXT> and(String templateType, Supplier<String> templateProvider, TemplateDataProvider<CONTEXT> provider) {
		providers.put(templateType, new TemplateContext((TemplateDataProvider<? super Object>) provider, templateProvider));
		return this;
	}

	public String buildContent(String templateType, CONTEXT context) throws IOException {
		TemplateContext templateContext = providers.get(templateType);
		String content = templateContext.getTemplate();
		Map<String, Object> model = templateContext.getModel(context);
		return buildHtml(templateType, content, model);
	}

	private String buildHtml(String templateName, String templateContent, final Map<String, Object> data) {
		StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
		stringTemplateLoader.putTemplate(templateName, templateContent);
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(stringTemplateLoader);

		try {
			Template template = cfg.getTemplate(templateName);
			StringWriter out = new StringWriter();
			template.process(data, out);
			return out.toString();
		} catch (IOException | TemplateException e) {
			throw Throwables.propagate(e);
		}
	}

	public static interface TemplateDataProvider<CONTEXT> {
		public Map<String, Object> get(CONTEXT context);
	}

	private static class TemplateContext {
		private final TemplateDataProvider<? super Object> dataProvider;
		private final Supplier<String> templateProvider;

		private TemplateContext(TemplateDataProvider<? super Object> dataProvider, Supplier<String> templateProvider) {
			this.dataProvider = dataProvider;
			this.templateProvider = templateProvider;
		}

		public String getTemplate() {
			return templateProvider.get();
		}

		public Map<String, Object> getModel(final Object object) {
			return dataProvider.get(object);
		}
	}
}
