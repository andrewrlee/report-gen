package uk.co.optimisticpanda.pdfreport.fire;

import static uk.co.optimisticpanda.pdfreport.util.ClasspathTemplateProvider.classPathTemplateProvider;

import java.io.File;

import uk.co.optimisticpanda.pdfreport.ReportGenerator;
import uk.co.optimisticpanda.pdfreport.TemplateBuilder;
import uk.co.optimisticpanda.pdfreport.charts.ReplaceWithChart;
import uk.co.optimisticpanda.pdfreport.maps.MapBuilder;
import uk.co.optimisticpanda.pdfreport.maps.ReplaceWithMap;
import uk.co.optimisticpanda.pdfreport.util.MapboxApiKey;

public class GenerateFireReport {

	private static final String FIRE_REPORT_TYPE = "fire";

	public static void main(String[] args) throws Exception {

		MapBuilder mapBuilder = new MapBuilder(MapboxApiKey.get());

		TemplateBuilder<FireData> templateBuilder = TemplateBuilder
				.withTemplate(
						FIRE_REPORT_TYPE, 
						classPathTemplateProvider("/fire.html.ftl"),
						new FireTemplateProvider(mapBuilder));

		ReportGenerator<FireData> generator = ReportGenerator.withTemplateBuilder(templateBuilder)
				.withReplacer(new ReplaceWithMap<>())
				.withReplacer(new ReplaceWithChart<>(new FireChartMaker()));

		generator.generate(FIRE_REPORT_TYPE, new FireData(), new File("fire.pdf"));
	}

}
