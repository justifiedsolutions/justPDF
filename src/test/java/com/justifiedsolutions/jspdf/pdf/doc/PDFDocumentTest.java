/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.doc;

import com.justifiedsolutions.jspdf.pdf.font.PDFFont;
import com.justifiedsolutions.jspdf.pdf.font.PDFFontType1;
import com.justifiedsolutions.jspdf.pdf.object.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class PDFDocumentTest {

    @Test
    void write() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFDocument doc = new PDFDocument();
        doc.addInfo(PDFInfoDictionary.AUTHOR, new PDFDocEncodedString("Jason Burgess"));
        doc.addInfo(PDFInfoDictionary.TITLE, new PDFDocEncodedString("jspdf Test Document"));
        doc.addInfo(PDFInfoDictionary.CREATION_DATE, new PDFDate());
        PDFFont font = PDFFontType1.getInstance(PDFFontType1.FontName.HELVETICA_BOLD);
        PDFIndirectObject.Reference fontReference = doc.addFont(font);

        for (int i = 0; i < 4; i++) {
            PDFPage page = doc.createPage(new PDFRectangle(0, 0, 612, 792));
            PDFName fontName = page.addFontReference(fontReference);
            PDFString text = new PDFString("Hello World: " + i);
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            content.writeBytes("BT\n72 720 Td\n".getBytes(StandardCharsets.US_ASCII));
            fontName.writeToPDF(content);
            content.writeBytes(" 24 Tf\n".getBytes(StandardCharsets.US_ASCII));
            text.writeToPDF(content);
            content.writeBytes("Tj\nET".getBytes(StandardCharsets.US_ASCII));
            PDFStream contents = new PDFStream(content.toByteArray());
            page.setContents(contents);
        }

        doc.write(actual);
    }
}