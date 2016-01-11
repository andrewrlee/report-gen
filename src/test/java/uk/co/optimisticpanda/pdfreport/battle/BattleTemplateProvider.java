package uk.co.optimisticpanda.pdfreport.battle;

import static uk.co.optimisticpanda.pdfreport.util.CssColour.RED;

import java.util.Map;

import uk.co.optimisticpanda.pdfreport.TemplateBuilder.TemplateDataProvider;
import uk.co.optimisticpanda.pdfreport.maps.MapBuilder;
import uk.co.optimisticpanda.pdfreport.maps.MapBuilder.MapType;

import com.google.common.collect.ImmutableMap;

public class BattleTemplateProvider implements TemplateDataProvider<BattleData>{

	private final MapBuilder mapBuilder;

	public BattleTemplateProvider(MapBuilder mapBuilder) {
		this.mapBuilder = mapBuilder;
	}

	@Override
	public Map<String, Object> get(BattleData context) {
		
		String mapUrl = mapBuilder.newMap(MapType.RUN_BIKE_HIKE, context.getCentre())
				.zoomLevel(11)
				.addMarkerWithArrow(context.getAttackPoints().get(0), "fire-station", RED, 90.0, RED, RED)
				.addMarkerWithArrow(context.getAttackPoints().get(1), "fire-station", RED, 180.0, RED, RED)
				.addMarkerWithArrow(context.getAttackPoints().get(2), "fire-station", RED, 320.0, RED, RED)
				.addMarkerWithArrow(context.getAttackPoints().get(3), "fire-station", RED, 270.0, RED, RED)
				.addMarkerWithArrow(context.getAttackPoints().get(4), "fire-station", RED, 220.0, RED, RED)
				.withDimensions(1200, 800)
				.buildUrl();
		
		return ImmutableMap.<String,Object>builder()
				.put("title", context.getName())
				.put("date", context.getDate())
				.put("map", mapUrl)
				.put("mapDescription", "The City and it's attackers")
				.build();
	}

}
