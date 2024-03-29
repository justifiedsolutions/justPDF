/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdfdriver;

import com.justifiedsolutions.justpdf.api.Document;
import com.justifiedsolutions.justpdf.api.DocumentException;
import com.justifiedsolutions.justpdf.api.HorizontalAlignment;
import com.justifiedsolutions.justpdf.api.Margin;
import com.justifiedsolutions.justpdf.api.Metadata;
import com.justifiedsolutions.justpdf.api.PageSize;
import com.justifiedsolutions.justpdf.api.VerticalAlignment;
import com.justifiedsolutions.justpdf.api.content.Cell;
import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.Phrase;
import com.justifiedsolutions.justpdf.api.content.Table;
import com.justifiedsolutions.justpdf.api.font.Font;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static com.justifiedsolutions.justpdf.api.font.PDFFont.FontName.HELVETICA;
import static com.justifiedsolutions.justpdf.api.font.PDFFont.FontName.HELVETICA_BOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ComplexTableOnlyDocumentTest {

    private static final float MEDIUM_SIZE = PDFFont.DEFAULT_SIZE * .80f;
    private static final Font FONT_MEDIUM_REGULAR = new PDFFont(HELVETICA, MEDIUM_SIZE);
    private static final Font FONT_MEDIUM_BOLD = new PDFFont(HELVETICA_BOLD, MEDIUM_SIZE);

    private static final float CELL_PADDING = 7;

    @Test
    void go() throws DocumentException, IOException {

        Table table = new Table(new float[] { .63f, .37f });
        table.setKeepTogether(true);
        table.setWidthPercentage(100);
        table.setSpacingBefore(9);
        table.setSpacingAfter(9);
        table.setBorderWidth(.5f);

        addCourt(table);
        addCourtUseOnly(table);
        addCaseDetails(table);
        addFirm(table);
        addCase(table);
        addDocumentInfo(table);

        Document document = new Document(PageSize.LETTER, new Margin(108, 72, 72, 72));
        document.add(table);

        document.setMetadata(Metadata.TITLE, "ComplexTableOnlyDocumentTest");
        document.setMetadata(Metadata.AUTHOR, "Jay Burgess");
        document.setMetadata(Metadata.CREATOR, "jsPDF");
        document.setMetadata(Metadata.SUBJECT, "Lorum Ipsum");

        String targetDirectoryName = System.getProperty("TargetDirectory");
        File targetDirectory = new File(targetDirectoryName);
        File testOutputDirectory = new File(targetDirectory, "test-output");
        if (testOutputDirectory.isDirectory() || testOutputDirectory.mkdirs()) {
            File outputFile = new File(testOutputDirectory, "ComplexTableOnlyDocumentTest.pdf");
            try (OutputStream pdf = Files.newOutputStream(outputFile.toPath())) {
                document.write(pdf);
            }
        }

        assertEquals(2, table.getNumberOfColumns());
    }

    private void addCourt(final Table table) {
        final Paragraph content = new Paragraph();
        content.setLineHeight(1.2f);
        content.setFont(FONT_MEDIUM_REGULAR);

        final Chunk courtTypeChunk = new Chunk("DISTRICT COURT, ");
        content.add(courtTypeChunk);
        content.add("DENVER COUNTY, ");
        final String state = "CO";
        content.add(state);
        content.add(Chunk.LINE_BREAK);

        content.add("123 Main St.\nSuite 555\nDenver, CO 80202");
        content.add(Chunk.LINE_BREAK);
        content.add("(555) 867-5309");

        final Cell cell = table.createCell(content);
        cell.setPadding(CELL_PADDING);
    }

    private void addCourtUseOnly(final Table table) {
        Chunk triangle = new Chunk("\u0073", new PDFFont(PDFFont.FontName.ZAPFDINGBATS));
        Chunk text = new Chunk("     COURT USE ONLY     ", FONT_MEDIUM_BOLD);
        Phrase phrase = new Phrase();
        phrase.add(triangle);
        phrase.add(text);
        phrase.add(triangle);
        final Cell cell = table.createCell(phrase);
        cell.setRowSpan(2);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPaddingBottom(4);
    }

    private void addCaseDetails(final Table table) {
        final Paragraph content = new Paragraph("In re:");
        content.add(Chunk.LINE_BREAK);
        content.add("The Marriage of:");
        content.add(Chunk.LINE_BREAK);

        content.add(Chunk.LINE_BREAK);
        content.add("Petitioner: ");
        content.add(new Chunk("Jane Smith", FONT_MEDIUM_BOLD));
        content.add(Chunk.LINE_BREAK);

        content.add(Chunk.LINE_BREAK);
        content.add("Respondent");
        content.add(": ");
        content.add(new Chunk("John Smith", FONT_MEDIUM_BOLD));
        content.add(Chunk.LINE_BREAK);

        content.setFont(FONT_MEDIUM_REGULAR);
        content.setLineHeight(1.2f);

        final Cell cell = table.createCell(content);
        cell.setPadding(CELL_PADDING);
    }

    private void addFirm(final Table table) {
        final Paragraph content = new Paragraph();
        content.setFont(FONT_MEDIUM_REGULAR);
        content.setLineHeight(1.2f);
        content.add("My Firm, LLP");
        content.add(Chunk.LINE_BREAK);
        content.add("Lisa Sharky");
        content.add(", #" + "98765");
        content.add(Chunk.LINE_BREAK);
        content.add("321 High St.\nSuite 300\nDenver, CO 80202");
        content.add(Chunk.LINE_BREAK);
        content.add("Phone: " + "(555) 555-5555" + "  Fax: ");
        content.add(Chunk.LINE_BREAK);
        content.add("lisa@myfirm.com");
        content.add(Chunk.LINE_BREAK);
        content.add("Attorney for " + "Petitioner");

        final Cell cell = table.createCell(content);
        cell.setPadding(CELL_PADDING);
    }

    private void addCase(final Table table) {
        final Paragraph content = new Paragraph();
        content.setLineHeight(3f);
        content.add("Case Number: ");
        content.add("12345");
        content.add(Chunk.LINE_BREAK);
        content.add(Chunk.LINE_BREAK);
        content.add(Chunk.LINE_BREAK);
        content.add("Division: ");
        content.add("C");
        content.add(Chunk.LINE_BREAK);
        content.add(Chunk.LINE_BREAK);
        content.add(Chunk.LINE_BREAK);
        content.add("Courtroom: ");
        content.add("22A");
        content.setFont(FONT_MEDIUM_REGULAR);

        final Cell cell = table.createCell(content);
        cell.setPadding(CELL_PADDING);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
    }

    private void addDocumentInfo(final Table table) {
        final Phrase phrase = new Phrase();
        phrase.add(new Chunk("PETITIONER'S SWORN FINANCIAL STATEMENT", FONT_MEDIUM_BOLD));
        phrase.add(new Chunk(" (April 1, 2020)", FONT_MEDIUM_REGULAR));

        final Cell cell = table.createCell(phrase);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setColumnSpan(2);
        cell.setPadding(CELL_PADDING);
    }

}
