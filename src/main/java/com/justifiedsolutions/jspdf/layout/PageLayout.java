/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.DocumentException;
import com.justifiedsolutions.jspdf.api.Margin;
import com.justifiedsolutions.jspdf.api.content.Content;
import com.justifiedsolutions.jspdf.api.content.TextContent;
import com.justifiedsolutions.jspdf.pdf.contents.*;
import com.justifiedsolutions.jspdf.pdf.doc.PDFDocument;
import com.justifiedsolutions.jspdf.pdf.doc.PDFPage;
import com.justifiedsolutions.jspdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.jspdf.pdf.object.PDFName;
import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
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

    private final float width;
    private final float height;
    private final Margin margin;
    private float remainingHeight;
    private float currentVertPos;
    private float currentSpacingAfter = 0;

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
        this.width = width;
        this.height = height;

        PDFRectangle pageSize = new PDFRectangle(0, 0, width, height);
        this.pdfPage = pdfDocument.createPage(pageSize);

        this.pdfBuilder = new PDFContentStreamBuilder();

        this.margin = margin;
        this.remainingHeight = height - (margin.getTop() + margin.getBottom());
        float lineWidth = width - (margin.getLeft() + margin.getRight());

        currentVertPos = height - margin.getTop();
        factories.add(new PhraseLayoutFactory(lineWidth));
        factories.add(new ParagraphLayoutFactory(lineWidth));

        drawMargin();
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
     * @throws DocumentException if the specified content isn't supported
     */
    boolean checkFit(Content content) throws DocumentException {
        ContentLayout layout = getContentLayout(content);
        if (layout == null) {
            throw new DocumentException("Unsupported Content type: " + content.getClass().getSimpleName());
        }
        float height = layout.getMinimumHeight();
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
        ContentLayout layout = getContentLayout(content);
        if (layout == null) {
            throw new DocumentException("Unsupported Content type: " + content.getClass().getSimpleName());
        }

        remainingHeight -= Math.max(currentSpacingAfter, layout.getSpacingBefore());
        currentVertPos -= Math.max(currentSpacingAfter, layout.getSpacingBefore());

        if (content instanceof TextContent) {
            pdfBuilder.addOperator(new PushGraphicsState());
            pdfBuilder.addOperator(new BeginText());
            pdfBuilder.addOperator(new PositionText(new PDFReal(margin.getLeft()), new PDFReal(currentVertPos)));
        }

        float minHeight = layout.getMinimumHeight();
        while ((minHeight > 0) && (minHeight <= remainingHeight)) {
            ContentLine line = layout.getNextLine();
            if (line == null) {
                break;
            }
            addLine(line);
            remainingHeight -= line.getHeight();
            currentVertPos -= line.getHeight();
            minHeight = layout.getMinimumHeight();
        }

        currentSpacingAfter = layout.getSpacingAfter();

        if (content instanceof TextContent) {
            pdfBuilder.addOperator(new EndText());
            pdfBuilder.addOperator(new PopGraphicsState());
        }

        return layout.getRemainingContent();
    }

    /**
     * Notifies the page that no more content will be added.
     */
    void complete() throws IOException {
        pdfPage.setContents(pdfBuilder.getStream());
    }

    private ContentLayout getContentLayout(Content content) {
        ContentLayout layout = null;
        for (ContentLayoutFactory factory : factories) {
            if (factory.supportsContent(content)) {
                layout = factory.getContentLayout(content);
                break;
            }
        }
        return layout;
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

    private void drawMargin() {
        float llx = margin.getLeft();
        float lly = margin.getBottom();
        float urx = width - margin.getRight();
        float ury = height - margin.getTop();
        pdfBuilder.addOperator(new PushGraphicsState());
        pdfBuilder.addOperator(new SetLineWidth(new PDFReal(.5f)));
        pdfBuilder.addOperator(new CreateRectangularPath(new PDFRectangle(llx, lly, urx, ury)));
        pdfBuilder.addOperator(new StrokePath());
        pdfBuilder.addOperator(new StartPath(new PDFReal(width / 2f), new PDFReal(ury)));
        pdfBuilder.addOperator(new AppendToPath(new PDFReal(width / 2f), new PDFReal(lly)));
        pdfBuilder.addOperator(new StrokePath());
        pdfBuilder.addOperator(new PopGraphicsState());
    }
}
