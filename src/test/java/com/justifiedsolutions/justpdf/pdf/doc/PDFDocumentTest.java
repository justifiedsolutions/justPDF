/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.contents.*;
import com.justifiedsolutions.justpdf.pdf.filter.DeflateFilter;
import com.justifiedsolutions.justpdf.pdf.font.PDFFont;
import com.justifiedsolutions.justpdf.pdf.font.PDFFontType1;
import com.justifiedsolutions.justpdf.pdf.object.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PDFDocumentTest {

    @Test
    public void write() throws IOException {
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
            PDFContentStreamBuilder builder = new PDFContentStreamBuilder();
            builder.addFilter(new DeflateFilter());
            builder.addOperator(new SetLineWidth(new PDFReal(.5f)));
            builder.addOperator(new CreateRectangularPath(new PDFRectangle(70, 718, 235, 740)));
            builder.addOperator(new StrokePath());
            builder.addOperator(new BeginText());
            builder.addOperator(new SetFont(fontName, new PDFReal(24)));
            builder.addOperator(new SetLeading(new PDFReal(28.8f)));
            builder.addOperator(new PositionText(new PDFReal(72), new PDFReal(720)));
            builder.addOperator(new ShowText(new PDFString("Hello World: " + i)));
            builder.addOperator(new MoveToNextLine());
            builder.addOperator(new ShowText(new PDFString("Nice to see you!")));
            builder.addOperator(new EndText());
            PDFStream contentStream = builder.getStream();
            page.setContents(contentStream);
        }

        assertTrue(font instanceof PDFFontType1);

        doc.write(actual);

        String targetDirectoryName = System.getProperty("TargetDirectory");
        File targetDirectory = new File(targetDirectoryName);
        File testOutputDirectory = new File(targetDirectory, "test-output");
        if (testOutputDirectory.isDirectory() || testOutputDirectory.mkdirs()) {
            File outputFile = new File(testOutputDirectory, "PDFDocumentTest.pdf");
            try (OutputStream pdf = Files.newOutputStream(outputFile.toPath())) {
                actual.writeTo(pdf);
            }
        }
    }
}