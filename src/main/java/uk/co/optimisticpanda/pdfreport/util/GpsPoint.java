package uk.co.optimisticpanda.pdfreport.util;

import java.util.Objects;

public class GpsPoint {

	private final double latitude;
	private final double longitude;
	
	public GpsPoint(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Double.doubleToLongBits(latitude), Double.doubleToLongBits(longitude));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GpsPoint other = (GpsPoint) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GpsPoint [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
