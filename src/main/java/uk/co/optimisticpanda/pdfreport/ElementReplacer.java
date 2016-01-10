package uk.co.optimisticpanda.pdfreport;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;

public interface ElementReplacer<CONTEXT> {
	
	default boolean shouldReplace(Element element) {
		String nodeName = element.getNodeName();
		String className = element.getAttribute("class");
		return shouldReplace(nodeName, className);
	}

	boolean shouldReplace(String nodeName, String className);

	ReplacedElement replace(int cssWidth, int cssHeight, Element element, CONTEXT context);
}