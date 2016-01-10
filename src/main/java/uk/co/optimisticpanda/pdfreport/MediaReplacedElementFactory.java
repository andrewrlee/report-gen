package uk.co.optimisticpanda.pdfreport;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

public class MediaReplacedElementFactory<CONTEXT> implements ReplacedElementFactory {
	private final ReplacedElementFactory superFactory;
	private final List<ElementReplacer<CONTEXT>> elementReplacers = new ArrayList<>();
	private CONTEXT context;
	
	public MediaReplacedElementFactory(ReplacedElementFactory superFactory, List<ElementReplacer<CONTEXT>> replacers, CONTEXT context) {
		this.superFactory = superFactory;
		this.context = context;
		this.elementReplacers.addAll(replacers);
	}

	@Override
	public ReplacedElement createReplacedElement(LayoutContext layoutContext,
			BlockBox blockBox, UserAgentCallback userAgentCallback,
			int cssWidth, int cssHeight) {
		Element element = blockBox.getElement();
		if (element == null) {
			return null;
		}
		for (ElementReplacer<CONTEXT> elementReplacer : this.elementReplacers) {
			if (elementReplacer.shouldReplace(element)) {
				return elementReplacer.replace(cssWidth, cssHeight, element, context);
			}
		}
		return this.superFactory.createReplacedElement(layoutContext, blockBox,
				userAgentCallback, cssWidth, cssHeight);
	}

	@Override
	public void reset() {
		this.superFactory.reset();
	}

	@Override
	public void remove(Element e) {
		this.superFactory.remove(e);
	}

	@Override
	public void setFormSubmissionListener(FormSubmissionListener listener) {
		this.superFactory.setFormSubmissionListener(listener);
	}
}