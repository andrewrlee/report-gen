package uk.co.optimisticpanda.pdfreport;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class ReportGenerator<CONTEXT> {

	private final List<ElementReplacer<CONTEXT>> replacers = new ArrayList<>();
	private final TemplateBuilder<CONTEXT> templateBuilder;
	
	public static <CONTEXT> ReportGenerator<CONTEXT> withTemplateBuilder(TemplateBuilder<CONTEXT> templateBuilder) {
		return new ReportGenerator<>(templateBuilder);
	}
	
	private ReportGenerator(TemplateBuilder<CONTEXT> templateBuilder) {
		this.templateBuilder = templateBuilder;
	}

	public ReportGenerator<CONTEXT> withReplacer(final ElementReplacer<CONTEXT> replacer) {
		this.replacers.add(replacer);	
		return this;
	}
	
	public void generate(String type, CONTEXT context, File output) throws Exception {
		String content = templateBuilder.buildContent(type, context);
		ITextRenderer renderer = new ITextRenderer();
		ReplacedElementFactory replacedElementFactory = renderer.getSharedContext().getReplacedElementFactory();
		renderer.getSharedContext().setReplacedElementFactory(new MediaReplacedElementFactory<CONTEXT>(replacedElementFactory, replacers, context));
		renderer.setDocumentFromString(content.toString());
		renderer.layout();
		renderer.createPDF(new FileOutputStream(output));
	}
}
