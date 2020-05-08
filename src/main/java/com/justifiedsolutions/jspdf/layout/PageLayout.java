/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.DocumentException;
import com.justifiedsolutions.jspdf.api.Margin;
import com.justifiedsolutions.jspdf.api.content.Content;
import com.justifiedsolutions.jspdf.pdf.contents.GraphicsOperator;
import com.justifiedsolutions.jspdf.pdf.contents.PDFContentStreamBuilder;
import com.justifiedsolutions.jspdf.pdf.contents.SetFont;
import com.justifiedsolutions.jspdf.pdf.doc.PDFDocument;
import com.justifiedsolutions.jspdf.pdf.doc.PDFPage;
import com.justifiedsolutions.jspdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.jspdf.pdf.object.PDFName;
import com.justifiedsolutions.jspdf.pdf.object.PDFRectangle;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Lays out content on a {@link PDFPage}.
 */
class PageLayout {
    private final PDFDocument pdfDocument;
    private final PDFPage pdfPage;
    private final PDFContentStreamBuilder pdfBuilder;

    private final Set<ContentLayoutFactory> factories = new HashSet<>();

    private float remainingHeight;

    /**
     * Creates a new PageLayout.
     *
     * @param pdfDocument the {@link PDFDocument} the page belongs to
     * @param width       the width of the page
     * @param height      the height of the page
     * @param margin      the margin of the page
     */
    PageLayout(PDFDocument pdfDocument, float width, float height, Margin margin) {
        this.pdfDocument = pdfDocument;

        PDFRectangle pageSize = new PDFRectangle(0, 0, width, height);
        this.pdfPage = pdfDocument.createPage(pageSize);

        this.pdfBuilder = new PDFContentStreamBuilder();

        this.remainingHeight = height;
        float lineWidth = width - (margin.getLeft() + margin.getRight());

        factories.add(new PhraseLayoutFactory(lineWidth));
    }

    /**
     * Checks to see if there has been any content added to the page.
     *
     * @return true if there has been no content added to the page
     */
    boolean isEmpty() {
        return pdfBuilder.isEmpty();
    }

    /**
     * Checks to see if the specified {@link Content} will fit on the unfilled portion of the page.
     *
     * @param content the content to check
     * @return true if the content will fit
     */
    boolean checkFit(Content content) {
        float height = Float.MAX_VALUE;
        for (ContentLayoutFactory factory : factories) {
            if (factory.supportsContent(content)) {
                ContentLayout layout = factory.getContentLayout(content);
                height = layout.getMinimumHeight();
                break;
            }
        }
        return (height <= remainingHeight);
    }

    /**
     * Adds the {@link Content} to the {@link PDFPage}. This method should only be called after a call to {@link
     * #checkFit(Content)}.
     *
     * @param content the content to add
     * @return any remaining content that couldn't fit on this page
     * @throws DocumentException if the specified content isn't supported
     */
    Content add(Content content) throws DocumentException {
        ContentLayout layout = null;
        for (ContentLayoutFactory factory : factories) {
            if (factory.supportsContent(content)) {
                layout = factory.getContentLayout(content);
                break;
            }
        }
        if (layout == null) {
            throw new DocumentException("Unsupported Content type: " + content.getClass());
        }

        float minHeight = layout.getMinimumHeight();
        while ((minHeight > 0) && (minHeight <= remainingHeight)) {
            ContentLine line = layout.getNextLine();
            if (line == null) {
                break;
            }
            addLine(line);
            remainingHeight -= line.getHeight();
            minHeight = layout.getMinimumHeight();
        }

        return layout.getRemainingContent();
    }

    /**
     * Notifies the page that no more content will be added.
     */
    void complete() throws IOException {
        pdfPage.setContents(pdfBuilder.getStream());
    }

    private void addLine(ContentLine line) {
        for (GraphicsOperator operator : line.getOperators()) {
            if (operator instanceof FontWrapperOperator) {
                PDFFontWrapper wrapper = ((FontWrapperOperator) operator).getFontWrapper();
                PDFIndirectObject.Reference reference = pdfDocument.addFont(wrapper.getFont());
                PDFName fontAlias = pdfPage.addFontReference(reference);
                operator = new SetFont(fontAlias, wrapper.getSize());
            }
            pdfBuilder.addOperator(operator);
        }
    }
}
