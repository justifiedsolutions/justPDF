package com.justifiedsolutions.justpdfdriver;

import com.justifiedsolutions.justpdf.api.*;
import com.justifiedsolutions.justpdf.api.content.Cell;
import com.justifiedsolutions.justpdf.api.content.Phrase;
import com.justifiedsolutions.justpdf.api.content.Table;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SimpleTableOnlyDocumentTest {

    @Test
    public void go() throws DocumentException, IOException {

        Document document = new Document(PageSize.LETTER, new Margin(72, 72, 72, 72));

        createTable1(document);
        createTable2(document);
        createTable3(document);
        createTable4(document);
        createTable5(document);
        createTable6(document);

        document.setMetadata(Metadata.TITLE, "SimpleTableOnlyDocumentTest");
        document.setMetadata(Metadata.AUTHOR, "Jay Burgess");
        document.setMetadata(Metadata.CREATOR, "jsPDF");
        document.setMetadata(Metadata.SUBJECT, "Lorum Ipsum");

        String targetDirectoryName = (String) System.getProperties().get("TargetDirectory");
        File targetDirectory = new File(targetDirectoryName);
        File testOutputDirectory = new File(targetDirectory, "test-output");
        if (testOutputDirectory.isDirectory() || testOutputDirectory.mkdirs()) {
            File outputFile = new File(testOutputDirectory, "SimpleTableOnlyDocumentTest.pdf");
            FileOutputStream pdf = new FileOutputStream(outputFile);
            document.write(pdf);
        }

    }

    private void createTable1(Document document) throws DocumentException {
        Table table = new Table(new float[]{.25f, .25f, .25f, .25f});

        Cell cell;
        cell = table.createCell(new Phrase("Cell dp 1-1"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 1-2"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 1-3"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 1-4"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);

        cell = table.createCell(new Phrase("Cell dp 2-1"));
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 2-2"));
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setColumnSpan(2);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 2-4"));
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setPadding(4);

        cell = table.createCell(new Phrase("Cell dp 3-1"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 3-2"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 3-3"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 3-4"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);

        document.add(table);
    }

    private void createTable2(Document document) throws DocumentException {
        Table table = new Table(new float[]{.25f, .25f, .25f, .25f});
        table.setSpacingBefore(50);
        table.setWidthPercentage(100);

        Cell cell;
        cell = table.createCell(new Phrase("Cell dp 1-1"));
        cell.setBorders(Cell.Border.TOP, Cell.Border.LEFT, Cell.Border.BOTTOM);
        cell.setPadding(10);
        cell = table.createCell(new Phrase("Cell dp 1-2"));
        cell.setBorders(Cell.Border.TOP, Cell.Border.BOTTOM);
        cell.setPadding(5);
        cell = table.createCell(new Phrase("Cell dp 1-3"));
        cell.setBorders(Cell.Border.TOP, Cell.Border.BOTTOM);
        cell.setPadding(10);
        cell = table.createCell(new Phrase("Cell dp 1-4"));
        cell.setBorders(Cell.Border.TOP, Cell.Border.RIGHT, Cell.Border.BOTTOM);
        cell.setPadding(20);

        document.add(table);
    }

    private void createTable3(Document document) throws DocumentException {
        Table table = new Table(new float[]{1f / 3f, 1f / 3f, 1f / 3f});
        table.setSpacingBefore(50);
        table.setWidthPercentage(100);

        Cell cell;
        cell = table.createCell(new Phrase("Cell dp 1-1"));
        cell.setBorders(Cell.Border.TOP, Cell.Border.LEFT);
        cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
        cell = table.createCell(new Phrase("Cell dp 1-2"));
        cell.setBorders(Cell.Border.TOP);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell = table.createCell(new Phrase("Cell dp 1-3"));
        cell.setBorders(Cell.Border.TOP, Cell.Border.RIGHT);
        cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        cell = table.createCell(new Phrase("Cell dp 2-1"));
        cell.setBorders(Cell.Border.LEFT);
        cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
        cell = table.createCell(new Phrase("Cell dp 2-2"));
        cell.setBorders();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setGrayFill(.95f);
        cell.setMinimumHeight(50);
        cell = table.createCell(new Phrase("Cell dp 2-3"));
        cell.setBorders(Cell.Border.RIGHT);
        cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);

        cell = table.createCell(new Phrase("Cell dp 3-1"));
        cell.setBorders(Cell.Border.LEFT, Cell.Border.BOTTOM);
        cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
        cell = table.createCell(new Phrase("Cell dp 3-2"));
        cell.setBorders(Cell.Border.BOTTOM);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell = table.createCell(new Phrase("Cell dp 3-3"));
        cell.setBorders(Cell.Border.RIGHT, Cell.Border.BOTTOM);
        cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        document.add(table);
    }

    private void createTable4(Document document) throws DocumentException {
        Table table = new Table(new float[]{.25f, .25f, .25f, .25f});
        table.setSpacingBefore(50);
        table.setWidthPercentage(100);

        Cell cell;
        cell = table.createCell(new Phrase("Cell dp 1-1"));
        cell.setPadding(10);
        cell = table.createCell(new Phrase("Cell dp 1-2"));
        cell.setPadding(5);
        cell = table.createCell(new Phrase("Cell dp 1-3"));
        cell.setPadding(40);
        cell.setColumnSpan(2);

        document.add(table);
    }

    private void createTable5(Document document) throws DocumentException {
        Table table = new Table(new float[]{.25f, .25f, .25f, .25f});
        table.setSpacingBefore(20);

        Cell cell;
        cell = table.createCell(new Phrase("Cell dp 1-1"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 1-2"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 1-3"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 1-4"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell.setRowSpan(2);

        cell = table.createCell(new Phrase("Cell dp 2-1"));
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 2-2"));
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setColumnSpan(2);
        cell.setPadding(4);

        cell = table.createCell(new Phrase("Cell dp 3-1"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 3-2"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 3-3"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 3-4"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);

        document.add(table);
    }

    private void createTable6(Document document) throws DocumentException {
        Table table = new Table(new float[]{.25f, .25f, .25f, .25f});
        table.setSpacingBefore(100);
        table.setKeepTogether(true);

        Cell cell;
        cell = table.createCell(new Phrase("Cell dp 1-1"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 1-2"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 1-3"));
        cell.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 1-4"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell.setRowSpan(2);

        cell = table.createCell(new Phrase("Cell dp 2-1"));
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 2-2"));
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setColumnSpan(2);
        cell.setPadding(4);

        cell = table.createCell(new Phrase("Cell dp 3-1"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 3-2"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 3-3"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 3-4"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell.setRowSpan(3);

        cell = table.createCell(new Phrase("Cell dp 4-1"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 4-2"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 4-3"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);

        cell = table.createCell(new Phrase("Cell dp 5-1"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 5-2"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 5-3"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);

        cell = table.createCell(new Phrase("Cell dp 6-1"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 6-2"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 6-3"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 6-4"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);

        cell = table.createCell(new Phrase("Cell dp 7-1"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 7-2"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell = table.createCell(new Phrase("Cell dp 7-3"));
        cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setPadding(4);
        cell.setColumnSpan(2);

        document.add(table);
    }


}
