package uk.co.optimisticpanda.pdfreport.imgman;

import static java.util.Arrays.asList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import uk.co.optimisticpanda.pdfreport.util.GpsPoint;

public class FireData {
	
	public Date getDate() {
		return Date.from(Instant.parse("2007-12-03T10:15:30.00Z"));
	}

	public String getId() {
		return "071c5e9e-90b4-458b-98b0-b0d380b09b61";
	}

	public double getWindBearing() {
		return 0.0;
	}
	
	public GpsPoint getSuspectedSource() {
		return new GpsPoint(39.4888, -120.8496);
	}
	
	public List<GpsPoint> getFireLine() {
		return asList(
				new GpsPoint(39.493379, -120.850072),
				new GpsPoint(39.491243, -120.850501),
				new GpsPoint(39.489810, -120.849332),
				new GpsPoint(39.488527, -120.846199));
	}
	
	public List<GpsPoint> getPoints() {
		return asList(
				new GpsPoint(39.4916, -120.8492),
				new GpsPoint(39.4896, -120.8474),
				new GpsPoint(39.4864, -120.8510),
				new GpsPoint(39.4916, -120.8492));
	}
	
	public List<Double> getHeatAtWaterDrop1OverTime() {
		Random random = new Random();
		List<Double> result =  new ArrayList<>();
		int multiplier = 200;
		for (int i = 0; i < 600; i++) {
			if (i > 450 && i < 550) {
				multiplier -= 1;
			}
			result.add((random.nextDouble() * multiplier) + 75);
		}
		return result;
	}
	
	public List<Double> getHeatAtWaterDrop2OverTime() {
		Random random = new Random();
		List<Double> result =  new ArrayList<>();
		int multiplier = 125;
		for (int i = 0; i < 600; i++) {
			if (i > 450) {
				multiplier -= 2;
			}else  {
				multiplier += 1;
			}
			result.add((random.nextDouble() * multiplier) + 15);
		}
		return result;
	}
}
