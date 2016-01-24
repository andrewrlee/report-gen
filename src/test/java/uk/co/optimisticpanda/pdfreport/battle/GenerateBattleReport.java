package uk.co.optimisticpanda.pdfreport.battle;

import static uk.co.optimisticpanda.pdfreport.util.ClasspathTemplateProvider.classPathTemplateProvider;

import java.io.File;

import uk.co.optimisticpanda.pdfreport.ReportGenerator;
import uk.co.optimisticpanda.pdfreport.TemplateBuilder;
import uk.co.optimisticpanda.pdfreport.maps.MapBuilder;
import uk.co.optimisticpanda.pdfreport.maps.ReplaceWithMap;
import uk.co.optimisticpanda.pdfreport.util.MapboxApiKey;

public class GenerateBattleReport {

	private static final String BATTLE_REPORT_TYPE = "battle";

	public static void main(String[] args) throws Exception {

		MapBuilder mapBuilder = new MapBuilder(MapboxApiKey.get());

		TemplateBuilder<BattleData> templateBuilder = TemplateBuilder
				.withTemplate(
						BATTLE_REPORT_TYPE, 
						classPathTemplateProvider("/battle.html.ftl"),
						new BattleTemplateProvider(mapBuilder));

		ReportGenerator<BattleData> generator = ReportGenerator.withTemplateBuilder(templateBuilder)
				.withReplacer(new ReplaceWithMap<>((ctx, image) -> image));

		generator.generate(BATTLE_REPORT_TYPE, new BattleData(), new File("battle.pdf"));
	}

}
