package uk.co.optimisticpanda.pdfreport.charts;

import java.awt.image.BufferedImage;

import org.w3c.dom.Element;

public interface ChartMaker<CONTEXT> {

	BufferedImage generate(int cssWidth, int cssHeight, Element element, CONTEXT context);	

}
