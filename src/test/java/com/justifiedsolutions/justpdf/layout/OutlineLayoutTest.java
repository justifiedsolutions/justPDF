/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.Document;
import com.justifiedsolutions.justpdf.api.Margin;
import com.justifiedsolutions.justpdf.api.Outline;
import com.justifiedsolutions.justpdf.api.PageSize;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.pdf.doc.PDFDocument;
import com.justifiedsolutions.justpdf.pdf.doc.PDFOutlineDictionary;
import com.justifiedsolutions.justpdf.pdf.doc.PDFPage;
import com.justifiedsolutions.justpdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.justpdf.pdf.object.PDFRectangle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OutlineLayoutTest {

    @Test
    void populatePDFOutlineDictionaryTopEntryNoLoc() {
        Document document = new Document(PageSize.LETTER, new Margin(72, 72, 72, 72));
        PDFDocument pdfDocument = new PDFDocument();
        Outline outline = document.getOutline();
        Paragraph paragraph = new Paragraph();
        outline.createEntry(paragraph);
        OutlineLayout layout = new OutlineLayout(outline, pdfDocument);
        assertThrows(IllegalStateException.class, layout::populatePDFOutlineDictionary);
    }

    @Test
    void populatePDFOutlineDictionaryChildEntryNoLoc() {
        Document document = new Document(PageSize.LETTER, new Margin(72, 72, 72, 72));
        PDFDocument pdfDocument = new PDFDocument();
        Outline outline = document.getOutline();
        Paragraph p1 = new Paragraph();
        p1.setOutlineText("foo");
        Paragraph p2 = new Paragraph();
        p2.setOutlineText("bar");
        Outline.Entry e1 = outline.createEntry(p1);
        e1.createEntry(p2);
        OutlineLayout layout = new OutlineLayout(outline, pdfDocument);
        PDFPage pdfPage = pdfDocument.createPage(new PDFRectangle(0, 0, 600, 600));
        PDFIndirectObject.Reference pageReference = pdfPage.getReference();
        layout.setContentLocation(p1, pageReference, 350);
        assertThrows(IllegalStateException.class, layout::populatePDFOutlineDictionary);
    }

    @Test
    void setContentLocationTwice() {
        Outline outline = mock(Outline.class);
        PDFDocument pdfDocument = mock(PDFDocument.class);
        PDFOutlineDictionary outlineDictionary = mock(PDFOutlineDictionary.class);
        when(pdfDocument.getOutline()).thenReturn(outlineDictionary);

        Paragraph paragraph = new Paragraph();
        paragraph.setOutlineText("foo");
        Outline.Entry entry = mock(Outline.Entry.class);
        when(entry.getEntries()).thenReturn(List.of());
        when(entry.getOutlineId()).thenReturn(paragraph.getOutlineId());
        when(outline.getEntries()).thenReturn(List.of(entry));

        OutlineLayout layout = new OutlineLayout(outline, pdfDocument);

        PDFIndirectObject.Reference page1 = mock(PDFIndirectObject.Reference.class);
        PDFIndirectObject.Reference page2 = mock(PDFIndirectObject.Reference.class);

        layout.setContentLocation(paragraph, page1, 150);
        layout.setContentLocation(paragraph, page2, 600);
        layout.populatePDFOutlineDictionary();

        verify(outlineDictionary).createItem("foo", page1, 150);
        verify(outlineDictionary, never()).createItem("foo", page2, 600);
    }

    @Test
    void setContentLocationNotInEntries() {
        Document document = new Document(PageSize.LETTER, new Margin(72, 72, 72, 72));
        PDFDocument pdfDocument = new PDFDocument();
        Outline outline = document.getOutline();
        Paragraph p1 = new Paragraph();
        p1.setOutlineText("foo");
        OutlineLayout layout = new OutlineLayout(outline, pdfDocument);
        PDFPage pdfPage = pdfDocument.createPage(new PDFRectangle(0, 0, 600, 600));
        PDFIndirectObject.Reference pageReference = pdfPage.getReference();
        assertThrows(IllegalArgumentException.class, () -> layout.setContentLocation(p1, pageReference, 350));
    }
}