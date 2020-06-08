/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.*;
import com.justifiedsolutions.justpdf.api.content.Content;
import com.justifiedsolutions.justpdf.api.content.KeepTogetherCapable;
import com.justifiedsolutions.justpdf.api.content.PageBreak;
import com.justifiedsolutions.justpdf.pdf.doc.PDFDocument;
import com.justifiedsolutions.justpdf.pdf.doc.PDFInfoDictionary;
import com.justifiedsolutions.justpdf.pdf.object.PDFDate;
import com.justifiedsolutions.justpdf.pdf.object.PDFDocEncodedString;
import com.justifiedsolutions.justpdf.pdf.object.PDFString;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Lays out a specified {@link Document} as a {@link PDFDocument}. Takes care of pagination, etc.
 */
public class DocumentLayout {
    private final Document document;
    private final PDFDocument pdfDocument = new PDFDocument();
    private final OutlineLayout outlineLayout;
    private PageLayout currentPage;
    private int currentPageNumber;

    /**
     * Creates a layout of the specified {@link Document}.
     *
     * @param document the document to layout as a PDF
     * @throws DocumentException     if there is an issue laying out the document
     * @throws IllegalStateException if there is no data in the document
     */
    public DocumentLayout(Document document) throws DocumentException {
        this.document = document;
        outlineLayout = new OutlineLayout(this.document.getOutline(), this.pdfDocument);
        layout();
    }

    /**
     * Writes the PDF to the specified {@link OutputStream}.
     *
     * @param pdfOutputStream the stream to write to
     * @throws IOException if there is an issue writing to the stream
     */
    public void write(OutputStream pdfOutputStream) throws IOException {
        pdfDocument.write(pdfOutputStream);
    }

    /**
     * Manages the layout of the document.
     *
     * @throws IllegalStateException if there is no data in the document
     */
    private void layout() throws DocumentException {
        processMetadata();
        if (document.hasContent()) {
            layoutContent();
        } else if (document.hasSections()) {
            layoutSections();
        } else {
            throw new IllegalStateException("There is no data in the document.");
        }
        outlineLayout.populatePDFOutlineDictionary();
    }

    /**
     * Processes the {@link Metadata} into a {@link PDFInfoDictionary}.
     */
    private void processMetadata() {
        for (Metadata key : document.getMetadata().keySet()) {
            String value = document.getMetadata(key);
            PDFString pdfValue = new PDFDocEncodedString(value);
            switch (key) {
                case TITLE:
                    pdfDocument.addInfo(PDFInfoDictionary.TITLE, pdfValue);
                    break;
                case AUTHOR:
                    pdfDocument.addInfo(PDFInfoDictionary.AUTHOR, pdfValue);
                    break;
                case SUBJECT:
                    pdfDocument.addInfo(PDFInfoDictionary.SUBJECT, pdfValue);
                    break;
                case KEYWORDS:
                    pdfDocument.addInfo(PDFInfoDictionary.KEYWORDS, pdfValue);
                    break;
                case CREATOR:
                    pdfDocument.addInfo(PDFInfoDictionary.CREATOR, pdfValue);
                    break;
                case PRODUCER:
                    pdfDocument.addInfo(PDFInfoDictionary.PRODUCER, pdfValue);
                    break;
                case CREATE_DATE:
                    // do nothing. CREATE_DATE passed by user is ignored and set below even if it isn't specified
                    break;
                default:
                    break;
            }
        }
        PDFDate createDate = new PDFDate();
        pdfDocument.addInfo(PDFInfoDictionary.CREATION_DATE, createDate);
        document.setMetadata(Metadata.CREATE_DATE, createDate.getValue());
    }

    private void createPage() throws IOException {
        completePage();
        float width = document.getPageSize().width();
        float height = document.getPageSize().height();
        Margin margin = document.getMargin();
        currentPage = new PageLayout(pdfDocument, width, height, margin, ++currentPageNumber);
        currentPage.setHeader(document.getHeader());
        currentPage.setFooter(document.getFooter());
        currentPage.setOutlineLayout(outlineLayout);
    }

    private void completePage() throws IOException {
        if (currentPage != null) {
            currentPage.complete();
        }
    }

    private void layoutContent() throws DocumentException {
        try {
            createPage();
            for (Content content : document.getContent()) {
                layoutContent(content);
            }
            completePage();
        } catch (IOException e) {
            throw new DocumentException("Error laying out page.", e);
        }
    }

    private void layoutContent(Content content) throws DocumentException, IOException {
        if (content instanceof PageBreak) {
            if (!currentPage.isEmpty()) {
                createPage();
            }
        } else if (currentPage.checkFit(content)) {
            Content remainder = currentPage.add(content);
            while (remainder != null) {
                createPage();
                remainder = currentPage.add(remainder);
            }
        } else if (!currentPage.isEmpty()) {
            createPage();
            layoutContent(content);
        } else if (content instanceof KeepTogetherCapable) {
            layoutKeepTogetherContent((KeepTogetherCapable) content);
        }
    }

    private void layoutKeepTogetherContent(KeepTogetherCapable ktc) throws DocumentException, IOException {
        if (ktc.isKeepTogether()) {
            ktc.setKeepTogether(false);
            layoutContent(ktc);
        } else {
            throw new DocumentException("Unable to paginate document. Content cannot fit single page.");
        }
    }

    private void layoutSections() throws DocumentException {
        try {
            createPage();
            for (Section section : document.getSections()) {
                layoutSection(section);
            }
            completePage();
        } catch (IOException e) {
            throw new DocumentException("Error laying out page.", e);
        }
    }

    private void layoutSection(Section section) throws IOException, DocumentException {
        if (section.isStartsNewPage()) {
            layoutContent(new PageBreak());
        }

        layoutContent(section.getDisplayTitle());

        for (Content content : section.getContent()) {
            layoutContent(content);
        }

        for (Section child : section.getSections()) {
            layoutSection(child);
        }
    }
}
