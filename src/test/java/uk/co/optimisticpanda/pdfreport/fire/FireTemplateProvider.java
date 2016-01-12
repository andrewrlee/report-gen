package uk.co.optimisticpanda.pdfreport.fire;

import static uk.co.optimisticpanda.pdfreport.util.CssColour.BLACK;
import static uk.co.optimisticpanda.pdfreport.util.CssColour.BLUE;
import static uk.co.optimisticpanda.pdfreport.util.CssColour.GREEN;
import static uk.co.optimisticpanda.pdfreport.util.CssColour.RED;
import static uk.co.optimisticpanda.pdfreport.util.CssColour.WHITE;

import java.util.Map;

import uk.co.optimisticpanda.pdfreport.TemplateBuilder.TemplateDataProvider;
import uk.co.optimisticpanda.pdfreport.maps.MapBuilder;
import uk.co.optimisticpanda.pdfreport.maps.MapBuilder.MapType;

import com.google.common.collect.ImmutableMap;

public class FireTemplateProvider implements TemplateDataProvider<FireData>{

	private final MapBuilder mapBuilder;

	public FireTemplateProvider(MapBuilder mapBuilder) {
		this.mapBuilder = mapBuilder;
	}

	@Override
	public Map<String, Object> get(FireData context) {
		
		String mapUrl = mapBuilder.newMap(MapType.SATELLITE, context.getSuspectedSource())
				.zoomLevel(16)
				.addMarker(context.getPoints().get(0), BLUE, "1")
				.addMarker(context.getPoints().get(1), BLUE, "2")
				.addMarker(context.getPoints().get(2), GREEN, "campsite")
				.addPath(context.getFireLine(), RED)
				.withDimensions(1200, 800)
				.addArrow(context.getSuspectedSource(), context.getWindBearing(), WHITE, BLACK)
				.buildUrl();
		
		return ImmutableMap.<String,Object>builder()
				.put("title", "Forest Fire Report")
				.put("date", context.getDate())
				.put("id", context.getId())
				.put("location", context.getSuspectedSource())
				.put("map", mapUrl)
				.put("mapDescription", "Fire line, prevailing wind, water drops and wildfire station")
				.build();
	}

}
