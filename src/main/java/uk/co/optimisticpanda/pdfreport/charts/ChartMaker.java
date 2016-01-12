package uk.co.optimisticpanda.pdfreport.charts;

import org.w3c.dom.Element;

public interface ChartMaker<CONTEXT> {

	byte[] generate(int cssWidth, int cssHeight, Element element, CONTEXT context);	

}
