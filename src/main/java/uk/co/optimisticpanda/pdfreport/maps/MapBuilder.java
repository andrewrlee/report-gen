package uk.co.optimisticpanda.pdfreport.maps;

import static java.util.Arrays.asList;
import static uk.co.optimisticpanda.pdfreport.util.GeocodeUtils.rotate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uk.co.optimisticpanda.pdfreport.util.CssColour;
import uk.co.optimisticpanda.pdfreport.util.GeocodeUtils;
import uk.co.optimisticpanda.pdfreport.util.GpsPoint;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;

public class MapBuilder {

	public enum MapType {
		SATELLITE("mapbox.satellite"), 
		RUN_BIKE_HIKE("mapbox.run-bike-hike"), 
		STREETS("mapbox.streets"), 
		STREETS_SATELLITE("mapbox.streets-satellite"), 
		COMIC("mapbox.comic"), 
		LIGHT("mapbox.light"), 
		DARK("mapbox.dark"), 
		PENCIL("mapbox.pencil");

		private String id;

		private MapType(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}
	}

	private String apiKey;

	public MapBuilder(String apiKey) {
		this.apiKey = apiKey;
	}

	public Builder newMap(MapType type, GpsPoint point) {
		return new Builder(apiKey, type, point);
	}
	
	public static class Builder {
		
		private MapType type;
		private String apiKey;
		private GpsPoint gpsPoint;
		private List<Overlay> overlays = new ArrayList<>();
		private int zoomLevel = 10;
		private int width = 800;
		private int height = 400;
		
		public Builder(String apiKey, MapType type, GpsPoint gpsPoint) {
			this.apiKey = apiKey;
			this.type = type;
			this.gpsPoint = gpsPoint;
		}

		// Higher more zoom
		public Builder zoomLevel(int zoomLevel) {
			this.zoomLevel = zoomLevel;
			return this;
		}
		
		public Builder withDimensions(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}
		
		public Builder addMarker(GpsPoint point, CssColour color, String label) {
			overlays.add(new Marker(point, color, label));
			return this;
		}


		public Builder addMarkerWithArrow(GpsPoint point, String label, CssColour color, double angle, CssColour line, CssColour fill) {
			overlays.add(new Marker(point, color, label));
			return addArrow(point, angle, line, fill, 10);
		}
		
		public Builder addPath(List<GpsPoint> points, CssColour line, CssColour fill) {
			overlays.add(new Path(points, line, fill));
			return this;
		}

		public Builder addPath(List<GpsPoint> points, CssColour line) {
			overlays.add(new Path(points, line));
			return this;
		}
		
		public Builder addArrow(GpsPoint point, double angle, CssColour line, CssColour fill) {
			return addArrow(point, angle, line, fill, 1);
		}
		
		public Builder addArrow(GpsPoint point, double angle, CssColour line, CssColour fill, int sizeMultiplier) {
			GpsPoint top = new GpsPoint(point.getLatitude() + (sizeMultiplier * 0.0008), point.getLongitude());
			GpsPoint bottom = new GpsPoint(point.getLatitude() - (sizeMultiplier * 0.0008), point.getLongitude());
			GpsPoint bottomLeft = new GpsPoint(bottom.getLatitude(), point.getLongitude()+ (sizeMultiplier * 0.0002));
			GpsPoint bottomRight = new GpsPoint(bottom.getLatitude(), point.getLongitude()- (sizeMultiplier * 0.0002));
			
			GpsPoint topLeft = new GpsPoint(top.getLatitude() - (sizeMultiplier * 0.0005) , point.getLongitude() + (sizeMultiplier * 0.0002));
			GpsPoint topRight = new GpsPoint(top.getLatitude() - (sizeMultiplier * 0.0005), point.getLongitude() - (sizeMultiplier * 0.0002));
			
			GpsPoint arrowLeft = new GpsPoint(topLeft.getLatitude() , topLeft.getLongitude() + (sizeMultiplier * 0.0004));
			GpsPoint arrowRight = new GpsPoint(topRight.getLatitude(), topRight.getLongitude() - (sizeMultiplier * 0.0004));
			
			return addPath(asList(
					rotate(point, bottomLeft, angle),
					rotate(point, bottomRight, angle),
					rotate(point, topRight, angle),
					rotate(point, arrowRight, angle),
					rotate(point, top, angle),
					rotate(point, arrowLeft, angle),
					rotate(point, topLeft, angle)), line, fill);
		}
		
		public String buildUrl() {
			String url = "https://api.mapbox.com/v4/%s/%s/%f,%f,%s/%sx%s.png?access_token=%s";
			String markersString = overlays.stream().map(Overlay::toOverlayString).collect(Collectors.joining(","));
			String s = String.format(url, type.getId(), markersString, gpsPoint.getLongitude(), gpsPoint.getLatitude(), zoomLevel, width, height, apiKey);
			System.out.println(s);
			return s;
		}
	}

	private static class Path implements Overlay {

		private List<GpsPoint> points;
		private CssColour fill;
		private CssColour line;

		public Path(List<GpsPoint> point, CssColour line, CssColour fill) {
			this.points = point;
			this.fill = line;
			this.line = fill;
		}

		public Path(List<GpsPoint> point, CssColour line) {
			this.points = point;
			this.line = line;
		}

		
		@Override
		public String toOverlayString() {
			String path = GeocodeUtils.encode(points);
			try {
				String encode = URLEncoder.encode(path, Charsets.UTF_8.name());
				if (fill == null) {
					return String.format("path-3+%s-1(%s)", line.getCode(), encode);
				}
				return String.format("path-3+%s-1+%s-1(%s)", line.getCode(), fill.getCode(), encode);
			} catch (UnsupportedEncodingException e) {
				throw Throwables.propagate(e);
			}
		}
		
	}

	
	private static class Marker implements Overlay {

		private GpsPoint point;
		private String label;
		private CssColour color;

		public Marker(GpsPoint point, CssColour color, String label) {
			this.point = point;
			this.color = color;
			this.label = label;
		}

		@Override
		public String toOverlayString() {
			return String.format("pin-l-%s+%s(%f,%f,13)", label,
					color.getCode(),
					point.getLongitude(), 
					point.getLatitude());
		}
		
	}
	
	private interface Overlay {
		String toOverlayString();
	}
}
