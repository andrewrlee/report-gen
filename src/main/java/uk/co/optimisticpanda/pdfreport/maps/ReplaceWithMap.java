package uk.co.optimisticpanda.pdfreport.maps;

import static com.google.common.base.Preconditions.checkState;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;

import uk.co.optimisticpanda.pdfreport.ElementReplacer;

import com.google.common.base.Throwables;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;

public class ReplaceWithMap<CONTEXT> implements ElementReplacer<CONTEXT> {

	@Override
	public boolean shouldReplace(String nodeName, String className) {
		return "div".equals(nodeName) && "map".equals(className);
	}

	@Override
	public ReplacedElement replace(int cssWidth, int cssHeight, Element element, CONTEXT context) {
		checkState(element.hasAttribute("data-src"), "no `data-src`");
		try {
			BufferedImage img = ImageIO.read(new URL(element.getAttribute("data-src")));
			ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			ImageIO.write(img, "png", tmp);
			final Image image = Image.getInstance(tmp.toByteArray());

			final FSImage fsImage = new ITextFSImage(image);
			if ((cssWidth != -1) || (cssHeight != -1)) {
				System.out.println(cssWidth + " " + cssHeight);
				fsImage.scale(cssWidth, cssHeight);
			}
			return new ITextImageElement(fsImage);
		} catch (IOException | BadElementException e) {
			throw Throwables.propagate(e);
		}
	}
}
