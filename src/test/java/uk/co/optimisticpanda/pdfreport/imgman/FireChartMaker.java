package uk.co.optimisticpanda.pdfreport.imgman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.Chart;
import org.knowm.xchart.Series;
import org.knowm.xchart.SeriesMarker;
import org.w3c.dom.Element;

import uk.co.optimisticpanda.pdfreport.charts.ChartMaker;

import com.google.common.base.Throwables;

public class FireChartMaker implements ChartMaker<FireData> {

	@Override
	public byte[] generate(int cssWidth, int cssHeight, Element element, FireData fireData) {
		if (element.getAttribute("data-chart-type").equals("water-drop-1")) {
			return waterDropChart("Water Drop 1", fireData.getHeatAtWaterDrop1OverTime());
		}
		if (element.getAttribute("data-chart-type").equals("water-drop-2")) {
			return waterDropChart("Water Drop 2", fireData.getHeatAtWaterDrop2OverTime());
		}
		
		throw new IllegalStateException("Unknown chart type");
	}

	private byte[] waterDropChart(String title, List<Double> data) {
		List<Double> xData = getXAccess();
		List<Double> yData = data;
		Chart chart = new Chart(1200, 800);
		chart.setChartTitle(title);
		chart.setXAxisTitle("Time (Seconds)");
		chart.setYAxisTitle("Temperature (Celsius)");
		chart.getStyleManager().setYAxisMin(0.0);
		chart.getStyleManager().setYAxisMax(550.0);
		chart.getStyleManager().setXAxisMin(0.0);
		chart.getStyleManager().setXAxisMax(600.0);
		chart.getStyleManager().setXAxisTickMarkSpacingHint(60);
		
		chart.getStyleManager().setLegendVisible(false);
		chart.getStyleManager().setPlotGridLinesVisible(false);

		// Series
		Series series = chart.addSeries("0", xData, yData);
		series.setMarker(SeriesMarker.NONE);

		try {
			return BitmapEncoder.getBitmapBytes(chart, BitmapFormat.PNG);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	private List<Double> getXAccess() {
		List<Double> result = new ArrayList<>();
		double d = 0;
		for (int i = 0; i < 600; i++) {
			result.add(d);
			d += 01;
		}
		return result;
	}
}
