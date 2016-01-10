package uk.co.optimisticpanda.pdfreport.util;


import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.pdfreport.util.GeocodeUtils.rotate;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class GeocodeUtilsTest {

	@Test
	public void test() {
		assertThat(rotate(new GpsPoint(3.0, 0.0), new GpsPoint(3.0, 2.0), 90)).isEqualTo(new GpsPoint(5.0, 2));
	}

	
}
