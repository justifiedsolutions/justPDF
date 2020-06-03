/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.*;
import com.justifiedsolutions.justpdf.api.content.Content;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.TextContent;
import com.justifiedsolutions.justpdf.pdf.contents.*;
import com.justifiedsolutions.justpdf.pdf.doc.PDFDocument;
import com.justifiedsolutions.justpdf.pdf.doc.PDFPage;
import com.justifiedsolutions.justpdf.pdf.filter.DeflateFilter;
import com.justifiedsolutions.justpdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.justpdf.pdf.object.PDFName;
import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import com.justifiedsolutions.justpdf.pdf.object.PDFRectangle;

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

    private final int pageNumber;
    private final float width;
    private final float height;
    private final Margin margin;
    private float remainingHeight;
    private float currentVertPos;
    private float currentSpacingAfter;
    private Header header;
    private Footer footer;
    private OutlineLayout outlineLayout;

    /**
     * Creates a new PageLayout.
     *
     * @param pdfDocument the {@link PDFDocument} the page belongs to
     * @param width       the width of the page
     * @param height      the height of the page
     * @param margin      the margin of the page
     * @param pageNumber  the page number
     */
    PageLayout(PDFDocument pdfDocument, float width, float height, Margin margin, int pageNumber) {
        this.pdfDocument = pdfDocument;
        this.width = width;
        this.height = height;
        this.pageNumber = pageNumber;

        PDFRectangle pageSize = new PDFRectangle(0, 0, width, height);
        this.pdfPage = pdfDocument.createPage(pageSize);

        this.pdfBuilder = new PDFContentStreamBuilder();
        this.pdfBuilder.addFilter(new DeflateFilter());

        this.margin = margin;
        this.remainingHeight = height - (margin.getTop() + margin.getBottom());
        float lineWidth = width - (margin.getLeft() + margin.getRight());

        currentVertPos = height - margin.getTop();
        factories.add(new TextContentLayoutFactory(lineWidth));
        factories.add(new TableLayoutFactory(margin, lineWidth));
    }

    /**
     * Sets the page header.
     *
     * @param header the header
     */
    void setHeader(Header header) {
        this.header = header;
    }

    /**
     * Sets the page footer.
     *
     * @param footer the footer
     */
    void setFooter(Footer footer) {
        this.footer = footer;
    }

    /**
     * Sets the document outline layout.
     *
     * @param outlineLayout the outline layout
     */
    void setOutlineLayout(OutlineLayout outlineLayout) {
        this.outlineLayout = outlineLayout;
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
        float layoutMinimumHeight = layout.getMinimumHeight();
        return (layoutMinimumHeight <= remainingHeight);
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

        if (!isEmpty()) {
            remainingHeight -= Math.max(currentSpacingAfter, layout.getSpacingBefore());
            currentVertPos -= Math.max(currentSpacingAfter, layout.getSpacingBefore());
        }

        updateOutline(content);

        pdfBuilder.addOperator(new PushGraphicsState());
        if (content instanceof TextContent) {
            pdfBuilder.addOperator(new BeginText());
            pdfBuilder.addOperator(new PositionText(new PDFReal(margin.getLeft()), new PDFReal(currentVertPos)));
        }

        float minHeight = layout.getMinimumHeight();
        while ((minHeight > 0) && (minHeight <= remainingHeight)) {
            ContentLine line = layout.getNextLine(currentVertPos);
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
        }
        pdfBuilder.addOperator(new PopGraphicsState());

        return layout.getRemainingContent();
    }

    /**
     * Notifies the page that no more content will be added.
     */
    void complete() throws IOException {
        drawHeader();
        drawFooter();
        drawMargin();
        drawCenterLine();
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
                pdfBuilder.addOperator(new SetFont(fontAlias, wrapper.getSize()));
            } else {
                pdfBuilder.addOperator(operator);
            }
        }
    }

    private void updateOutline(Content content) {
        if (content instanceof Outlineable) {
            Outlineable outlineable = (Outlineable) content;
            if (outlineLayout.getOutlineIds().contains(outlineable.getOutlineId())) {
                outlineLayout.setContentLocation(outlineable, pdfPage.getReference(), currentVertPos);
            }
        }
    }

    private void drawHeader() {
        if (header != null && header.isValidForPageNumber(pageNumber)) {
            drawMarginal(header, height, margin.getTop());
        }
    }

    private void drawFooter() {
        if (footer != null && footer.isValidForPageNumber(pageNumber)) {
            drawMarginal(footer, margin.getBottom(), margin.getBottom());
        }
    }

    private void drawMarginal(RunningMarginal marginal, float marginTop, float marginHeight) {
        Paragraph paragraph = marginal.getParagraph(pageNumber);
        paragraph.setKeepTogether(true);
        paragraph.setSpacingBefore(0);
        paragraph.setSpacingAfter(0);
        ContentLayout layout = getContentLayout(paragraph);
        if (layout == null) {
            return;
        }
        float contentHeight = layout.getMinimumHeight();
        float diff = (marginHeight - contentHeight) / 2f;
        if (diff < 1) { // too close to the margin
            return;
        }
        float vPos = marginTop - diff;
        pdfBuilder.addOperator(new PushGraphicsState());
        pdfBuilder.addOperator(new BeginText());
        pdfBuilder.addOperator(new PositionText(new PDFReal(margin.getLeft()), new PDFReal(vPos)));
        ContentLine line = layout.getNextLine(vPos);
        while (line != null) {
            addLine(line);
            vPos -= line.getHeight();
            line = layout.getNextLine(vPos);
        }
        pdfBuilder.addOperator(new EndText());
        pdfBuilder.addOperator(new PopGraphicsState());
    }

    private void drawMargin() {
        Object debug = System.getProperties().get("DrawMargin");
        if (debug == null) {
            return;
        }
        float llx = margin.getLeft();
        float lly = margin.getBottom();
        float urx = width - margin.getRight();
        float ury = height - margin.getTop();
        pdfBuilder.addOperator(new PushGraphicsState());
        pdfBuilder.addOperator(new SetLineWidth(new PDFReal(.5f)));
        pdfBuilder.addOperator(new CreateRectangularPath(new PDFRectangle(llx, lly, urx, ury)));
        pdfBuilder.addOperator(new StrokePath());
        pdfBuilder.addOperator(new PopGraphicsState());
    }

    private void drawCenterLine() {
        Object debug = System.getProperties().get("DrawCenterLine");
        if (debug == null) {
            return;
        }
        float lly = margin.getBottom();
        float ury = height - margin.getTop();
        pdfBuilder.addOperator(new PushGraphicsState());
        pdfBuilder.addOperator(new SetLineWidth(new PDFReal(.5f)));
        pdfBuilder.addOperator(new StartPath(new PDFReal(width / 2f), new PDFReal(ury)));
        pdfBuilder.addOperator(new AppendToPath(new PDFReal(width / 2f), new PDFReal(lly)));
        pdfBuilder.addOperator(new StrokePath());
        pdfBuilder.addOperator(new PopGraphicsState());
    }
}
