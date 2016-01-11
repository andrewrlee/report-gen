package uk.co.optimisticpanda.pdfreport.battle;

import static java.util.Arrays.asList;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import uk.co.optimisticpanda.pdfreport.util.GpsPoint;

public class BattleData {
	
	public Date getDate() {
		return Date.from(Instant.parse("1742-05-03T10:15:30.00Z"));
	}

	public String getName() {
		return "The Siege of Prague";
	}

	public GpsPoint getCentre() {
		return new GpsPoint(50.072115, 14.478643);
	}
	
	public List<GpsPoint> getAttackPoints() {
		return asList(
				new GpsPoint(50.078981, 14.284758),
				new GpsPoint(50.146596, 14.395709),
				new GpsPoint(50.007727, 14.550856),
				new GpsPoint(50.073282, 14.695388),
				new GpsPoint(50.128278, 14.627613));
	}
}
