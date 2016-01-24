package uk.co.optimisticpanda.pdfreport.charts;

import java.io.IOException;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;

import uk.co.optimisticpanda.pdfreport.ElementReplacer;

import com.google.common.base.Throwables;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;

public class ReplaceWithChart<CONTEXT> implements ElementReplacer<CONTEXT> {

	private final ChartMaker<CONTEXT> chartMaker;
	
	public ReplaceWithChart(final ChartMaker<CONTEXT> chartMaker) {
		this.chartMaker = chartMaker;
	}
	
	@Override
	public boolean shouldReplace(String nodeName, String className) {
		return "div".equals(nodeName) && "chart".equals(className);
	}

	@Override
	public ReplacedElement replace(int cssWidth, int cssHeight, Element element, CONTEXT context) {
		try {
			byte[] img = chartMaker.generate(cssWidth, cssHeight, element, context);
			Image image = Image.getInstance(img);

			FSImage fsImage = new ITextFSImage(image);
			if ((cssWidth != -1) || (cssHeight != -1)) {
				fsImage.scale(cssWidth, cssHeight);
			}
			return new ITextImageElement(fsImage);
		} catch (IOException | BadElementException e) {
			throw Throwables.propagate(e);
		}
	}
}
