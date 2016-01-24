package uk.co.optimisticpanda.pdfreport;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageTransformer<CONTEXT> {
	BufferedImage transform(CONTEXT context, BufferedImage image);
}
