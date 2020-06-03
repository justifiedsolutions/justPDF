package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.Document;
import com.justifiedsolutions.justpdf.api.Margin;
import com.justifiedsolutions.justpdf.api.Outline;
import com.justifiedsolutions.justpdf.api.PageSize;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.pdf.doc.PDFDocument;
import com.justifiedsolutions.justpdf.pdf.doc.PDFPage;
import com.justifiedsolutions.justpdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.justpdf.pdf.object.PDFRectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OutlineLayoutTest {

    @Test
    public void populatePDFOutlineDictionaryTopEntryNoLoc() {
        Document document = new Document(PageSize.LETTER, new Margin(72, 72, 72, 72));
        PDFDocument pdfDocument = new PDFDocument();
        Outline outline = document.getOutline();
        Paragraph paragraph = new Paragraph();
        outline.createEntry(paragraph);
        OutlineLayout layout = new OutlineLayout(outline, pdfDocument);
        assertThrows(IllegalStateException.class, layout::populatePDFOutlineDictionary);
    }

    @Test
    public void populatePDFOutlineDictionaryChildEntryNoLoc() {
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
    public void setContentLocationNotInEntries() {
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