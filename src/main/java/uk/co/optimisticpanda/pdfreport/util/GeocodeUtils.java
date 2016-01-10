package uk.co.optimisticpanda.pdfreport.util;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.ArrayList;
import java.util.List;

public class GeocodeUtils {

	public static GpsPoint rotate(GpsPoint centre, GpsPoint point, double bearing) {
		double radians = toRadians(-bearing);
		
		double centreX = centre.getLongitude();
		double centreY = centre.getLatitude();
		double pointX = point.getLongitude();
		double pointY = point.getLatitude();
		
		double longitude = centreX + (pointX - centreX) * cos(radians) - (pointY - centreY) * sin(radians);
		double latitude  = centreY + (pointX - centreX) * sin(radians) + (pointY - centreY) * cos(radians);
		return new GpsPoint(latitude, longitude);			
	}
	
	/**
	 * Based on https://github.com/googlemaps/android-maps-utils/blob/master/library/src/com/google/maps/android/PolyUtil.java 
	 * Decodes an encoded path string into a sequence of LatLngs.
	 * (Apache License Version 2.0)
	 */
	public static List<GpsPoint> decode(final String encodedPath) {
		int len = encodedPath.length();

		// For speed we preallocate to an upper bound on the final length, then
		// truncate the array before returning.
		final List<GpsPoint> path = new ArrayList<>();
		int index = 0;
		int lat = 0;
		int lng = 0;

		while (index < len) {
			int result = 1;
			int shift = 0;
			int b;
			do {
				b = encodedPath.charAt(index++) - 63 - 1;
				result += b << shift;
				shift += 5;
			} while (b >= 0x1f);
			lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

			result = 1;
			shift = 0;
			do {
				b = encodedPath.charAt(index++) - 63 - 1;
				result += b << shift;
				shift += 5;
			} while (b >= 0x1f);
			lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

			path.add(new GpsPoint(lat * 1e-5, lng * 1e-5));
		}

		return path;
	}

	/**
	 * Based on https://github.com/googlemaps/android-maps-utils/blob/master/library/src/com/google/maps/android/PolyUtil.java 
	 * Encodes a sequence of LatLngs into an encoded path string.
	 * (Apache License Version 2.0)
	 */
	public static String encode(final List<GpsPoint> path) {
		long lastLat = 0;
		long lastLng = 0;

		final StringBuffer result = new StringBuffer();

		for (final GpsPoint point : path) {
			long lat = Math.round(point.getLatitude() * 1e5);
			long lng = Math.round(point.getLongitude() * 1e5);

			long dLat = lat - lastLat;
			long dLng = lng - lastLng;

			encode(dLat, result);
			encode(dLng, result);

			lastLat = lat;
			lastLng = lng;
		}
		return result.toString();
	}

	private static void encode(long v, StringBuffer result) {
		v = v < 0 ? ~(v << 1) : v << 1;
		while (v >= 0x20) {
			result.append(Character.toChars((int) ((0x20 | (v & 0x1f)) + 63)));
			v >>= 5;
		}
		result.append(Character.toChars((int) (v + 63)));
	}

}
