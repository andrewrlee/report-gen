package uk.co.optimisticpanda.pdfreport;

import java.awt.image.BufferedImage;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.Chart;
import org.knowm.xchart.Series;
import org.knowm.xchart.SeriesMarker;
import org.w3c.dom.Element;

import uk.co.optimisticpanda.pdfreport.charts.ChartMaker;

public class FireChartMaker implements ChartMaker<FireData> {

	@Override
	public BufferedImage generate(int cssWidth, int cssHeight, Element element, FireData fireData) {
		double[] xData = new double[] { 0.0, 0.1, 0.2, 0.3, 0.4, 0.5 };
		double[][] yData = new double[][] {{ 2.0, 1.0, 0.0, 0.5, 0.7, 0.8 }};
	    Chart chart = new Chart(600, 400);
	    chart.setChartTitle("Chart");
	    chart.setXAxisTitle("Time");
	    chart.setYAxisTitle("Problems");

	    chart.getStyleManager().setLegendVisible(false);
	    chart.getStyleManager().setPlotGridLinesVisible(false);
	        
	    // Series
	    for (int i = 0; i < yData.length; i++) {
	      Series series;
	        series = chart.addSeries(" " + i, xData, yData[i]);
	      series.setMarker(SeriesMarker.NONE);
	    }

	    return BitmapEncoder.getBufferedImage(chart);
	}

}
